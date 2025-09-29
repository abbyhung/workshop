const app = new Vue({
	el: '#product-info',
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
		selected: [],
	},
	components: {
		'store-header': httpVueLoader('/components/store-header.vue'),
		'product-order-block': httpVueLoader('/components/product-order-block.vue')
	},
	computed: {
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
		products() {
			if (!this.tableData || this.tableData.length === 0) {
				return [];
			}
			
			if(this.selected.length == 0) return this.tableData;
			
			return this.tableData.filter(product => {
            // 3. 檢查目前商品的 category 是否存在於 selected 陣列中
            return this.selected.includes(product.category);
        });
			
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
		getCart() {
			const cartJson = localStorage.getItem('cart');

			// 如果 localStorage 中有資料...
			if (cartJson) {
				// ...就把它解析成 JavaScript 陣列，並存入 this.cart
				this.cart = JSON.parse(cartJson);
				console.log('購物車載入成功:', this.cart);
			}

		},
		openPage() {
			window.location.href = '/cart.html'
		},
		categoryChange(val) {
			console.log('');
		},
	},
	mounted() {
		this.query();
	}
});