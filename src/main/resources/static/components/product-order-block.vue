<template>
	<div>
		<b-card>
			<b-form>
				<b-form-group label="商品名稱:" label-for="product-name">
					<b-form-input id="product-name" v-model="detail.name" readonly></b-form-input>
				</b-form-group>

				<b-form-group label="售價:" label-for="product-price">
					<b-form-input id="product-price" v-model.number="detail.price" type="number" readonly></b-form-input>
				</b-form-group>

				<b-form-group label="數量:" label-for="product-quantity">
					<b-form-input id="product-quantity" v-model.number="orderQuantity" type="number" 
						min="1" :max="detail.totalStock"></b-form-input>
				</b-form-group>

				<b-form-group label="尚餘庫存:" label-for="product-totalStock">
					<b-form-input id="product-totalStock" v-model.number="detail.totalStock" type="number"
						readonly></b-form-input>
				</b-form-group>

				<b-button variant="warning" @click="addToCart()">加入購物車</b-button>
				<b-button variant="danger" @click="openCart();">直接購買</b-button>
			</b-form>
		</b-card>
	</div>
</template>

<script>

debugger;
module.exports = {
	props: {
		productId: {
			type: [Number, String], // productId 可以是數字或字串
			required: true         // 設定為必須傳入
		}
	},
	data: function () {
		return {
			detail: {
				name: '商品載入中...',
				price: 0,
				totalStock: 0,
			},
			orderQuantity: 1,
		};
	},
	// 2. 新增 methods 區塊
	methods: {
		// 根據 productId 獲取商品詳細資料
		fetchProductDetail() {
			var vm = this;
			// 使用 URLSearchParams 來安全地建立查詢字串
			const params = new URLSearchParams();
			params.append('productId', vm.productId);

			fetch(`/api/admin/products/queryOne?${params.toString()}`)
				.then(response => response.json())
				.then(data => {
					if (data && data.length > 0) {
						vm.detail.name = data[0].name;
						vm.detail.price = data[0].price;
						vm.detail.totalStock = data[0].totalStock;
						vm.detail.category = data[0].totalStock;
					}
				})
				.catch(error => console.error('查詢失敗:', error));

		},
		addToCart() {
			if (this.detail.totalStock < this.orderQuantity) {
				alert(`「${this.detail.name}」庫存不足！`);
				return false;
			}

			// 1. 從 localStorage 中讀取現有的購物車資料
			//    如果 localStorage 中沒有 'cart'，預設為一個空陣列 []
			const cartJson = localStorage.getItem('cart');
			let cart = cartJson ? JSON.parse(cartJson) : [];

			// 2. 檢查購物車中是否已經有這個商品 (依 productId 查詢)
			const existingProductIndex = cart.findIndex(item => item.productId == this.productId);

			if (existingProductIndex > -1) {
				// 情況一：購物車中「已存在」此商品，則新增數量
				console.log('商品已存在，新增數量');
				cart[existingProductIndex].quantity += this.orderQuantity;
			} else {
				// 情況二：購物車中「不存在」此商品，則新增一筆紀錄
				console.log('商品不存在，新增一筆紀錄');
				const newItem = {
					productId: this.productId,
					name: this.detail.name,
					price: this.detail.price,
					quantity: this.orderQuantity
				};
				cart.push(newItem);
			}

			// 3. 將更新後的購物車資料，存回 localStorage
			//    localStorage 只能儲存字串，所以需要用 JSON.stringify() 轉換
			localStorage.setItem('cart', JSON.stringify(cart));

			// 4. (可選) 給予使用者回饋
			alert(`已將 ${this.orderQuantity} 件「${this.detail.name}」加入購物車！`);
			console.log('目前的購物車內容:', cart);
			this.$emit('cart-updated', cart);
			return true;
		},
		// 直接購買-回傳到index打開購物車畫面
		openCart() {
			if (this.addToCart())
				this.$emit('cart-open', null);
		},
	},
	mounted() {
		this.fetchProductDetail();
	}
};
</script>

<style scoped></style>