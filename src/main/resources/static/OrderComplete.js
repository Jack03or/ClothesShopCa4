const orderProductsButton = document.getElementById("order-products-button");
const orderLogoutButton = document.getElementById("order-logout-button");

function requireOrderCompleteLogin() {
    const username = sessionStorage.getItem("loggedInUsername");

    if (!username) {
        window.location.href = "/";
    }
}

orderProductsButton.addEventListener("click", () => {
    window.location.href = "/CustomerProducts.html";
});

orderLogoutButton.addEventListener("click", () => {
    sessionStorage.removeItem("loggedInUsername");
    sessionStorage.removeItem("loggedInRole");
    window.location.href = "/";
});

requireOrderCompleteLogin();
