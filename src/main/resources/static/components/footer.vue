<template>
    <div>
        <b-navbar toggleable="lg" type="dark" variant="info">

            <b-navbar-brand href="/html/admin/index.html">後台管理</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item href="/html/admin/admin-promotion-image.html">上方商品圖片管理</b-nav-item>
                    <b-nav-item href="/html/admin/admin-product.html">商品管理</b-nav-item>
                    <b-nav-item href="/html/admin/index.html">訂單管理</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      who: "Hello world",
    };
  },
};
</script>

<style scoped>
/* 'scoped' 屬性是一個強大的功能，
 它會確保這裡寫的 CSS 只對這個元件生效，不會污染到其他頁面。
*/
.b-navbar {
    border-radius: 5px;
}
</style>