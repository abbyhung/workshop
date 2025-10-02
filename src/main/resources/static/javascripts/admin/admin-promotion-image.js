const adminApp = new Vue({
	el: '#admin-promotion-image',
	data: {
		newPromoImage: {
			imageUrl: '',
			displayOrder: 0
		},
		promoImages: [],
	},
	components: {
		// 註冊一個名為 'admin-header' 的元件，
		// 它的內容來自 httpVueLoader 非同步載入的 HTML 檔案
		'admin-header': httpVueLoader('/components/admin-header.vue')
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
		addPromoImage() {
            fetch('/api/admin/promotions', {
                // 2. 指定 HTTP 方法為 POST，代表要「新增」資源
                method: 'POST',
                // 3. 設定 Headers，告訴後端我們傳送的是 JSON 格式的資料
                headers: {
                    'Content-Type': 'application/json',
                },
                // 4. 將 this.newPromoImage 這個 JavaScript 物件轉換成 JSON 字串，並放入請求的 body 中
                body: JSON.stringify(this.newPromoImage)
            })
            .then(async response => {
					if (response.ok) {
						return response.text();
					} else {
						const errorData = await response.json();
						throw new Error(errorData.message || '發生未知錯誤');
					}
				})
            .then(savedImage => {
                // 6. 成功後續處理
                console.log('新增成功:', savedImage);
                alert('新增成功！');

                // 刷新畫面上的圖片列表
                this.fetchPromoImages();

                // 清空表單，方便使用者繼續新增下一張
                this.newPromoImage.imageUrl = '';
                this.newPromoImage.displayOrder = 0;
            })
            .catch(error => {
                // 7. 捕獲並在 Console 中印出任何過程中發生的錯誤
                console.error('新增圖片時發生錯誤:', error);
            });
		},
		deletePromoImage(id) {
            fetch(`/api/admin/promotions/${id}`, {
                method: 'DELETE',
            })
            .then(response => {
                                // 3. 檢查後端的回應是否成功
                if (!response.ok) {
                    alert('刪除失敗！');
                    throw new Error('Network response was not ok');
                }

                // 刷新畫面上的圖片列表
                this.fetchPromoImages();
                
            })
            .catch(error => {
                // 7. 捕獲並在 Console 中印出任何過程中發生的錯誤
                console.error('刪除圖片時發生錯誤:', error);
            });
		}
	},
	mounted() {
		this.fetchPromoImages();
	}
});