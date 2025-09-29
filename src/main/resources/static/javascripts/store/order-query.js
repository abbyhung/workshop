const cart = new Vue({
	el: '#app',
	data: {
		orderId: '',
		custNumber: '',
		tableData: [],
	},
	components: {
		'store-header': httpVueLoader('/components/store-header.vue'),
	},
	computed: {
	},
	methods: {
		query() {
			var vm = this;

			// 1. 建立 URLSearchParams 物件
			const params = new URLSearchParams();

			// 2. 根據傳入的條件，動態地加入參數
			//    這樣可以處理只傳入 orderId 或只傳入 custNumber 的情況
			if (vm.orderId) {
				params.append('orderId', vm.orderId);
			}
			if (vm.custNumber) {
				params.append('custNumber', vm.custNumber);
			}

			// 如果沒有任何查詢條件，則不發送請求
			if (params.toString().length === 0) {
				alert('請至少提供一個查詢條件！');
				return;
			}

			// 3. 組合出最終的 URL
			//    假設 Controller 的基礎路徑是 /api/orders
			const url = `/api/orders/query?${params.toString()}`;
			fetch(url)
				.then(response => {
					if (!response.ok) {
						throw new Error('查詢失敗，HTTP 狀態碼：' + response.status);
					}
					return response.json();
				})
				.then(data => {
					vm.tableData = data;
				})
				.catch(error => {
					// 6. 捕捉任何網路錯誤或請求失敗的情況
					console.error('查詢訂單時發生錯誤:', error);
					alert('查詢訂單失敗！');
				});
		},
		deleteOrder(item) {
			var vm = this;
			const url = `/api/orders/deleteOrder?id=${item.orderId}`;

			fetch(url, {
				method: 'POST'
			})
				.then(async response => {
					if (response.ok) {
						return response.text();
					} else {
						const errorData = await response.json();
						throw new Error(errorData.message || '發生未知錯誤');
					}
				})
				.then(successMessage => {
					// 只有在 response.ok 為 true 時，才會執行到這裡
					console.log('後端回應:', successMessage);
					alert('刪除訂單成功！');
					vm.query(); // 重新整理
				})
				.catch(error => {
					alert('刪除訂單時發生錯誤:' + error);
				});
		},
	},
	mounted() {
	}
});