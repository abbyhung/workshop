const cart = new Vue({
	el: '#app',
	data: {
		fields: [
			{
				key: 'name',
				label: '商品名稱',
				thStyle: { width: '55%' }
			},
			{
				key: 'unitprice',
				label: '售價',
				thStyle: { width: '15%' },
				class: 'text-center'
			},
			{
				key: 'quantity',
				label: '數量',
				thStyle: { width: '15%' },
				class: 'text-center'
			},
			{
				key: 'subtotal',
				label: '小計',
				thStyle: { width: '15%' },
				class: 'text-center'
			},
		],
		order: {},
	},
	components: {
		'store-header': httpVueLoader('/components/store-header.vue'),
	},
	computed: {
	},
	methods: {
		query() {
			const orderId = getUrlParameter('orderId');

			const url = `/api/orders/query?orderId=${orderId}`;
			fetch(url)
				.then(response => {
					if (!response.ok) {
						throw new Error('查詢失敗，HTTP 狀態碼：' + response.status);
					}
					return response.json();
				})
				.then(data => {
					if (data && data.length > 0)
						this.order = data[0];
				})
				.catch(error => {
					console.error('查詢訂單時發生錯誤:', error);
					alert('查詢訂單失敗！');
				});
		},
		print() {
			window.print();
		},
		returnHomePage() {
			window.location.href = '/index.html'
		},
	},
	mounted() {
		this.query();
	}
});