const app = new Vue({
	el: '#app',
	data: {
		promoImages: [],
		products: [],
		cart: [],
	},
	components: {
		'store-header': httpVueLoader('/components/store-header.vue'),
		'product-order-block': httpVueLoader('/components/product-order-block.vue')
	},
	computed: {
		totalCartItems() {
			if (this.cart)
				return this.cart.reduce((total, item) => total + item.quantity, 0);
		}
	},
	methods: {
		fetchPromoImages() {
			fetch('/api/admin/promotions', {
				method: 'GET',
			})
				.then(res => res.json())
				.then(data => {
					this.promoImages = data;
				})
				.catch(console.error);
		},
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
		openPage() {
			window.location.href = '/cart.html'
		},
		scrollToProducts() {
			// 1. 透過 id 找到我們要捲動到的目標元素
			const targetElement = document.getElementById('product-list-section');

			// 2. 檢查元素是否存在，以防萬一
			if (targetElement) {
				// 3. 呼叫瀏覽器內建的 scrollIntoView 方法
				targetElement.scrollIntoView({
					// 關鍵！設定 behavior 為 'smooth' 來啟用平滑捲動
					behavior: 'smooth'
				});
			}
		},
	},
	mounted() {
		this.fetchPromoImages();
		this.query();
		this.getCart();
	}
});