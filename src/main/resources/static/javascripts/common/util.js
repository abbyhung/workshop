ELEMENT.locale(ELEMENT.lang.zhTW);

/**
 * 從目前網址的查詢字串中獲取指定的參數值
 * @param {string} paramName - 要獲取的參數名稱 (例如 "productId")
 * @returns {string|null} 參數的值，如果不存在則回傳 null
 */
function getUrlParameter(paramName) {
    // 1. 建立一個 URLSearchParams 物件，並傳入目前視窗的查詢字串
    const urlParams = new URLSearchParams(window.location.search);

    // 2. 使用 .get() 方法來取得特定參數的值
    const paramValue = urlParams.get(paramName);

    return paramValue;
}

function cloneObj(obj) {
    return structuredClone(obj);
}