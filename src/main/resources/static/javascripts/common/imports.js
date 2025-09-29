// 檔案路徑: src/main/resources/static/js/common/imports.js

// 將所有共用的 <link> 和 <script> 標籤定義成一個巨大的字串
const commonResources = `
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <link type="text/css" rel="stylesheet" href="https://unpkg.com/bootstrap-vue@2.23.5/dist/bootstrap-vue.css" />

	<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"
            integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+"
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/vue@2.7.16/dist/vue.js"></script>
    
    <script src="https://unpkg.com/bootstrap-vue@2.23.1/dist/bootstrap-vue.js"></script>
    
    <script src="https://cdn.jsdelivr.net/npm/http-vue-loader@1.4.2/src/httpVueLoader.js"></script>
    
    <script src="https://cdn.jsdelivr.net/npm/element-ui@2.15.14/lib/index.js"></script>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/element-ui/2.15.14/locale/zh-TW.min.js"></script>
`;

// 使用 document.write 將上面的字串內容直接寫入到 HTML 的 <head> 中
// 這會確保在解析頁面其他部分之前，所有 CSS 和 JS 函式庫都已經被宣告載入
document.write(commonResources);