const orderProductsButton = document.getElementById("order-products-button");
const orderLogoutButton = document.getElementById("order-logout-button");

orderProductsButton.addEventListener("click", () => {
    window.location.href = "/CustomerProducts.html";
});

orderLogoutButton.addEventListener("click", () => {
    window.location.href = "/";
});
