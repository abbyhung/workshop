const adminApp = new Vue({
	el: '#admin-product',
	data: {
		message: {},
		tableData: [],
		categoryList: [],

		params: {
			name: '',
			description: '',
			category: '',
			published: true,
		},
		showRestockModal: false,
		showEditProductModal: false,
		showStockRecordModal: false,

		editRequest: {},
		request: {
			name: '',
			description: '',
			price: 0,
			cost: 0,
			quantity: 0,
			productId: null,
			category: '',
			published: true,
		},
		actionType: '',

		stockRecord: [],
	},
	components: {
		// 註冊一個名為 'admin-header' 的元件，
		// 它的內容來自 httpVueLoader 非同步載入的 HTML 檔案
		'admin-header': httpVueLoader('/components/admin-header.vue'),
	},
	computed: {
		// 建立一個 computed 物件來存放所有欄位的驗證狀態
		validationState() {
			if (this.actionType == 'new') {
				return {
					name: this.editRequest["name"] && this.editRequest["name"].length > 0 ? true : false,
					category: this.editRequest["category"] && this.editRequest["category"].length > 0 ? true : false,
					description: this.editRequest["description"] && this.editRequest["description"].length > 0 ? true : false,
					price: this.editRequest.price > 0 ? true : false,
					cost: this.editRequest.cost > 0 ? true : false,
					quantity: this.editRequest.quantity > 0 ? true : false
				}
			}
			return {
				name: this.editRequest["name"] && this.editRequest["name"].length > 0 ? true : false,
				category: this.editRequest["category"] && this.editRequest["category"].length > 0 ? true : false,
				description: this.editRequest["description"] && this.editRequest["description"].length > 0 ? true : false,
				price: this.editRequest.price > 0 ? true : false,
			}
		},
		validationState2() {
			return {
				cost: this.editRequest.cost > 0 ? true : false,
				quantity: this.editRequest.quantity > 0 ? true : false
			}
		},

		filterCategorys() {
			if (!this.tableData || this.tableData.length === 0) {
				return [];
			}
			// 先取得不重複的分類
			const distinctCategories = [...new Set(this.tableData.map(item => item.category))];

			// 再轉換成 Element UI 需要的格式
			return distinctCategories.map(category => ({
				text: category,
				value: category
			}));
		},
	},
	methods: {
		query() {
			var vm = this;
			// 使用 URLSearchParams 來安全地建立查詢字串
			const params = new URLSearchParams();

			// 只將有值的參數加入
			params.append('name', "%" + vm.params.name + "%");
			params.append('description', "%" + vm.params.description + "%");
			params.append('category', "%" + vm.params.category + "%");
			params.append('published', vm.params.published);

			fetch(`/api/admin/products/query?${params.toString()}`)
				.then(response => response.json())
				.then(data => {
					vm.tableData = data;
				})
				.catch(error => console.error('查詢失敗:', error));
		},
		filterHandler(value, row, column) {
			const property = column['property'];
			return row[property] === value;
		},
		openModal(action, item) {
			var vm = this;
			vm.actionType = action;
			vm.editRequest = cloneObj(vm.request);

			if (action == 'edit') {
				vm.editRequest.name = item.name;
				vm.editRequest.description = item.description;
				vm.editRequest.price = item.price;
				vm.editRequest.productId = item.productId;
				vm.editRequest.category = item.category;
			}
			vm.showEditProductModal = !vm.showEditProductModal;
		},
		openRestockModal(item) {
			var vm = this;
			vm.actionType = "restock";
			vm.editRequest = cloneObj(vm.request);

			vm.editRequest.name = item.name;
			vm.editRequest.description = item.description;
			vm.editRequest.price = item.price;
			vm.editRequest.productId = item.productId;
			vm.editRequest.category = item.category;
			vm.showRestockModal = !vm.showRestockModal;
		},
		checkFormValidity() {
			const isFormValid = Object.values(this.validationState).every(state => state === true);
			if (!isFormValid) {
				alert('表單資料有誤，請檢查！');
				return false; // 驗證失敗，中止提交
			}
			return true;
		},
		checkFormValidity2() {
			const isFormValid = Object.values(this.validationState2).every(state => state === true);
			if (!isFormValid) {
				alert('表單資料有誤，請檢查！');
				return false; // 驗證失敗，中止提交
			}
			return true;
		},
		handleOk(bvModalEvent) {
			// Prevent modal from closing
			bvModalEvent.preventDefault()
			// Trigger submit handler
			this.handleSubmit()
		},
		handleSubmit() {
			// Exit when the form isn't valid
			if (!this.checkFormValidity()) {
				return
			}
			var vm = this;
			var action = 'create'
			if (this.actionType == 'edit') {
				action = 'update'
			}
			fetch(`/api/admin/products/${action}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(vm.editRequest)
			})
				.then(response => {
					// 5. 檢查後端的回應是否成功 (HTTP 狀態碼為 2xx)
					if (!response.ok) {
						// 如果不成功 (例如後端驗證失敗回傳 400)，則拋出錯誤
						throw new Error('請求失敗，HTTP 狀態碼：' + response.status);
					}
					// 6. 讀取純文字的回應，因為後端回傳的是 "更新成功" 字串
					return response.text();
				})
				.then(successMessage => {
					// 7. 成功後續處理
					console.log('後端成功回應:', successMessage); // 會印出 "更新成功"
					alert('商品新增成功！');
					// 在這裡可以接續處理，例如：
					// vm.resetForm(); // 清空表單
					vm.query(); // 重新整理商品列表
					vm.showEditProductModal = !vm.showEditProductModal;
				})
				.catch(error => {
					// 8. 捕獲並處理任何過程中發生的錯誤
					console.error('新增商品時發生錯誤:', error);
					alert('新增商品失敗，請查看 Console 中的詳細錯誤訊息。');
				});
		},
		setProductPublish(item) {
			var vm = this;
			vm.editRequest.name = item.name;
			vm.editRequest.description = item.description;
			vm.editRequest.price = item.price;
			vm.editRequest.productId = item.productId;
			vm.editRequest.category = item.category;
			fetch(`/api/admin/products/setProductPublish`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(vm.editRequest)
			})
				.then(response => {
					// 5. 檢查後端的回應是否成功 (HTTP 狀態碼為 2xx)
					if (!response.ok) {
						// 如果不成功 (例如後端驗證失敗回傳 400)，則拋出錯誤
						throw new Error('請求失敗，HTTP 狀態碼：' + response.status);
					}
					// 6. 讀取純文字的回應，因為後端回傳的是 "更新成功" 字串
					return response.text();
				})
				.then(successMessage => {
					// 7. 成功後續處理
					console.log('後端成功回應:', successMessage); // 會印出 "更新成功"
					alert('操作成功！');
					vm.query(); // 重新整理商品列表
				})
				.catch(error => {
					// 8. 捕獲並處理任何過程中發生的錯誤
					console.error('新增商品時發生錯誤:', error);
					alert('新增商品失敗，請查看 Console 中的詳細錯誤訊息。');
				});
		},
		deleteProduct(productId) {
			var vm = this;
			const url = `/api/admin/products/deleteProduct?id=${productId}`;

			// 2. 發送 POST 請求
			fetch(url, {
				method: 'POST'
			})
				.then(response => {
					// 5. 檢查後端的回應是否成功 (HTTP 狀態碼為 2xx)
					if (!response.ok) {
						// 如果不成功 (例如後端驗證失敗回傳 400)，則拋出錯誤
						throw new Error('請求失敗，HTTP 狀態碼：' + response.status);
					}
					// 6. 讀取純文字的回應，因為後端回傳的是 "更新成功" 字串
					return response.text();
				})
				.then(successMessage => {
					// 7. 成功後續處理
					console.log('後端成功回應:', successMessage); // 會印出 "更新成功"
					alert('操作成功！');
					vm.query(); // 重新整理商品列表
				})
				.catch(error => {
					// 8. 捕獲並處理任何過程中發生的錯誤
					console.error('新增商品時發生錯誤:', error);
					alert('新增商品失敗，請查看 Console 中的詳細錯誤訊息。');
				});
		},
		restock(bvModalEvent) {
			// Prevent modal from closing
			bvModalEvent.preventDefault()
			// Exit when the form isn't valid
			if (!this.checkFormValidity2()) {
				return
			}
			var vm = this;
			fetch(`/api/admin/products/restock`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(vm.editRequest)
			})
				.then(response => {
					// 5. 檢查後端的回應是否成功 (HTTP 狀態碼為 2xx)
					if (!response.ok) {
						// 如果不成功 (例如後端驗證失敗回傳 400)，則拋出錯誤
						throw new Error('請求失敗，HTTP 狀態碼：' + response.status);
					}
					// 6. 讀取純文字的回應，因為後端回傳的是 "更新成功" 字串
					return response.text();
				})
				.then(successMessage => {
					// 7. 成功後續處理
					console.log('後端成功回應:', successMessage); // 會印出 "更新成功"
					alert('操作成功！');
					vm.showRestockModal = !vm.showRestockModal;
					vm.query(); // 重新整理商品列表
				})
				.catch(error => {
					// 8. 捕獲並處理任何過程中發生的錯誤
					console.error('新增商品時發生錯誤:', error);
					alert('新增商品失敗，請查看 Console 中的詳細錯誤訊息。');
				});
		},
		openStockRecordModal(productId) {
			var vm = this;
			// 使用 URLSearchParams 來安全地建立查詢字串
			const params = new URLSearchParams();
			params.append('productId', productId);

			fetch(`/api/admin/products/queryStockRecord?${params.toString()}`)
				.then(response => response.json())
				.then(data => {
					vm.stockRecord = data;
					vm.showStockRecordModal = !vm.showStockRecordModal;
				})
				.catch(error => console.error('查詢失敗:', error));
		},
	},
	mounted() {
		this.query();
	}
});