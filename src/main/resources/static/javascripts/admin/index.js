const adminApp = new Vue({
	el: '#admin',
	data: {
		orderId: '',
		custNumber: '',
		tableData: [],
		unship: true,
		baseUrl: window.location.origin,
	},
	components: {
		// 註冊一個名為 'admin-header' 的元件，
		// 它的內容來自 httpVueLoader 非同步載入的 HTML 檔案
		'admin-header': httpVueLoader('/components/admin-header.vue')
	},
	methods: {
		query() {
			var vm = this;

			// 1. 建立 URLSearchParams 物件
			const params = new URLSearchParams();
			params.append('unship', vm.unship);

			// 如果沒有任何查詢條件，則不發送請求
			if (params.toString().length === 0) {
				alert('請至少提供一個查詢條件！');
				return;
			}

			// 3. 組合出最終的 URL
			//    假設 Controller 的基礎路徑是 /api/orders
			const url = `/api/admin/orders/query?${params.toString()}`;
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
		doShip(item) {
			var vm = this;
			const url = `/api/admin/orders/ship?id=${item.orderId}`;

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
					alert('訂單出貨成功！');
					this.query();
				})
				.catch(error => {
					alert('訂單出貨時發生錯誤:' + error);
				});
		},
	},
	mounted() {
		this.query();
	}
});