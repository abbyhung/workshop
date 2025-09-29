const cart = new Vue({
	el: '#app',
	data: {
		cart: [],
		// 定義 b-table 的欄位
		fields: [
			{
				key: 'name',
				label: '商品名稱',
				thStyle: { width: '50%' } // 1. 設定表頭寬度
			},
			{
				key: 'price',
				label: '售價',
				thStyle: { width: '15%' },
				class: 'text-center'       // 2. 讓儲存格內容靠右
			},
			{
				key: 'quantity',
				label: '數量',
				thStyle: { width: '10%' },
				class: 'text-center'      // 2. 讓儲存格內容置中
			},
			{
				key: 'subtotal',
				label: '小計',
				thStyle: { width: '15%' }, // (我稍微調整了%讓總和為100%)
				class: 'text-center'
			},
			{
				key: 'actions',
				label: '移除',
				thStyle: { width: '10%' },
				class: 'text-center'
			}
		],
		order: {},
	},
	components: {
		'store-header': httpVueLoader('/components/store-header.vue'),
	},
	computed: {
		// 計算總金額
		totalAmount() {
			return this.cart.reduce((total, item) => total + (item.price * item.quantity), 0);
		},
		validationStates() {
			// 姓名驗證：長度 > 0
			const isNameValid = this.order.custName && this.order.custName.length > 0;

			// 取貨店名驗證：長度 > 0
			const isShipValid = this.order.custShip && this.order.custShip.length > 0;

			// 電話驗證：使用正規表示式 (Regular Expression)
			// ^09: 開頭必須是 09
			// \d{8}: 後面必須跟著 8 個數字 (\d)
			// $: 結尾
			const phoneRegex = /^09\d{8}$/;
			const isNumberValid = this.order.custNumber && phoneRegex.test(this.order.custNumber);

			return {
				custName: isNameValid,
				custShip: isShipValid,
				custNumber: isNumberValid
			}
		}
	},
	methods: {
		query() {
			var vm = this;
			// 使用 URLSearchParams 來安全地建立查詢字串
			const params = new URLSearchParams();

			// 只將有值的參數加入
			params.append('name', "%%");
			params.append('description', "%%");
			params.append('category', "%%");
			params.append('published', true);

			fetch(`/api/admin/products/query?${params.toString()}`)
				.then(response => response.json())
				.then(data => {
					vm.products = data;
				})
				.catch(error => console.error('查詢失敗:', error));
		},
		getCart() {
			const cartJson = localStorage.getItem('cart');

			// 如果 localStorage 中有資料...
			if (cartJson) {
				// ...就把它解析成 JavaScript 陣列，並存入 this.cart
				this.cart = JSON.parse(cartJson);
				console.log('購物車載入成功:', this.cart);
			}

		},
		// 將目前的購物車狀態存回 localStorage
		saveCart() {
			localStorage.setItem('cart', JSON.stringify(this.cart));
			// 觸發事件通知父層更新（例如更新右下角的浮動按鈕）
			this.$emit('cart-updated', this.cart);
		},
		// 更新品項數量
		updateItemQuantity(item) {
			// 確保數量至少為 1
			if (item.quantity < 1) {
				item.quantity = 1;
			}
			this.saveCart();
		},
		// 移除品項
		removeItem(productIdToRemove) {
			if (confirm('您確定要從購物車移除此商品嗎？')) {
				// 使用 filter 回傳一個不包含被刪除商品的新陣列
				this.cart = this.cart.filter(item => item.productId !== productIdToRemove);
				this.saveCart();
			}
		},
		submitOrder() {
			// 在提交前，檢查所有驗證狀態是否都為 true
			const isFormValid = Object.values(this.validationStates).every(state => state === true);

			if (!isFormValid) {
				alert('表單資料有誤或不完整，請檢查紅色欄位！');
				return; // 中止提交
			}

			var vm = this;

			const orderDetails = this.cart.map(cartItem => {
				// 針對購物車中的每一個 item，都建立一個新格式的物件
				return {
					orderId: null, // 在建立訂單時，orderId 通常還不存在，所以設為 null
					productId: cartItem.productId,
					name: cartItem.name,

					// 將 cartItem 的 price 欄位，對應到新物件的 unitprice 欄位
					unitprice: cartItem.price,

					quantity: cartItem.quantity
				};
			});

			params = {
				'orderId': null,
				'date': null,
				'custName': vm.order.custName,
				'custNumber': vm.order.custNumber,
				'custShip': vm.order.custShip,
				'comment': vm.order.comment ? vm.order.comment : "",
				'totalAmount': vm.totalAmount,
				'details': orderDetails,
			};

			fetch(`/api/orders/order`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(params)
			})
				.then(response => {
					if (!response.ok) {
						throw new Error('請求失敗，HTTP 狀態碼：' + response.status);
					}
					return response.text();
				})
				.then(successMessage => {
					alert("訂購成功!");
					localStorage.setItem('cart', []);
					window.location.href = '/order-info.html?orderId=' + successMessage;
				})
				.catch(error => {
					alert('訂購時發生錯誤:' + error);
				});
		},
	},
	mounted() {
		this.getCart();
	}
});