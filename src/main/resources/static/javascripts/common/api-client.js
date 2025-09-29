const apiClient = {

    /**
     * 執行 GET 請求
     * @param {string} endpoint - API 的端點路徑 (例如 "/admin/products/queryOne")
     * @param {object} [params={}] - 要附加到 URL 的查詢參數物件 (可選)
     * @returns {Promise<any>} 解析後的 JSON 資料
     */
    get: function(endpoint, params = {}) {
        // 1. 建立 URLSearchParams 來安全地處理查詢參數
        const urlParams = new URLSearchParams();
        
        // 將 params 物件中的 key-value-pair 加入到查詢字串中
        Object.keys(params).forEach(key => {
            if (params[key] !== null && params[key] !== undefined) {
                urlParams.append(key, params[key]);
            }
        });

        const queryString = urlParams.toString();
        // 如果有查詢字串，則在 endpoint 後面加上 '?'
        const url = queryString ? `${endpoint}?${queryString}` : endpoint;

        // 2. 執行 fetch
        return fetch(url)
            .then(async response => {
                // 3. 統一檢查 HTTP 狀態是否成功
                if (!response.ok) {
                    // 如果不成功，嘗試解析後端回傳的錯誤 JSON body
                    const errorData = await response.json().catch(() => ({ 
                        message: `請求失敗，HTTP 狀態碼：${response.status}` 
                    }));
                    // 拋出一個帶有後端訊息的錯誤，這樣 .catch() 才能接到
                    throw new Error(errorData.message || '發生未知錯誤');
                }
                // 4. 如果成功，解析 JSON body 並回傳
                return response.json();
            });
    },

    /**
     * 未來可以繼續新增 POST, PUT, DELETE 等方法
     * post: function(endpoint, body) { ... },
     * delete: function(endpoint) { ... },
     */
};