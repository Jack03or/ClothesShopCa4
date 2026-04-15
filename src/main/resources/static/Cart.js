const cartItemsContainer = document.getElementById("cart-items");
const cartSubtotal = document.getElementById("cart-subtotal");
const cartTotal = document.getElementById("cart-total");
const cartBackButton = document.getElementById("cart-back-button");
const cartLogoutButton = document.getElementById("cart-logout-button");
const cartCheckoutButton = document.getElementById("cart-checkout-button");

function getCartUsername() {
    return sessionStorage.getItem("loggedInUsername");
}

function requireCartLogin() {
    const username = getCartUsername();

    if (!username) {
        window.location.href = "/";
        return null;
    }

    return username;
}

function logoutCartUser() {
    sessionStorage.removeItem("loggedInUsername");
    sessionStorage.removeItem("loggedInRole");
    window.location.href = "/";
}

function formatPrice(value) {
    return "€" + Number(value).toFixed(2);
}

async function loadCart() {
    const cartUsername = getCartUsername();

    if (!cartUsername) {
        window.location.href = "/";
        return;
    }

    const response = await fetch("/cart?username=" + cartUsername);
    const cart = await response.json();

    cartItemsContainer.innerHTML = "";

    if (!cart || !cart.items || cart.items.length === 0) {
        cartItemsContainer.innerHTML = "<div class='cartItemCard'><h3>Your cart is empty.</h3></div>";
        cartSubtotal.textContent = "€0.00";
        cartTotal.textContent = "€0.00";
        return;
    }

    let subtotal = 0;

    cart.items.forEach((item) => {
        const itemTotal = Number(item.price) * item.quantity;
        subtotal += itemTotal;

        const cartCard = document.createElement("div");
        cartCard.className = "cartItemCard";
        cartCard.innerHTML = `
            <div class="cartItemImage">${item.imageUrl}</div>
            <div class="cartItemDetails">
                <h3>${item.productTitle}</h3>
                <p><strong>Manufacturer:</strong> ${item.manufacturer}</p>
                <p><strong>Size:</strong> ${item.size}</p>
                <p><strong>Price:</strong> ${formatPrice(item.price)}</p>
                <div class="cartControlRow">
                    <label>Qty</label>
                    <input type="number" min="1" value="${item.quantity}" class="cartQtyInput">
                    <button class="submit-button smallCartButton updateCartButton" type="button">Update</button>
                    <button class="action-card smallCartButton removeCartButton" type="button">Remove</button>
                </div>
            </div>
            <div class="cartItemTotal">${formatPrice(itemTotal)}</div>
        `;

        const qtyInput = cartCard.querySelector(".cartQtyInput");
        const updateButton = cartCard.querySelector(".updateCartButton");
        const removeButton = cartCard.querySelector(".removeCartButton");

        updateButton.addEventListener("click", async () => {
            await fetch("/cart/update", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: cartUsername,
                    cartItemId: item.id,
                    quantity: Number(qtyInput.value)
                })
            });

            loadCart();
        });

        removeButton.addEventListener("click", async () => {
            await fetch("/cart/remove?username=" + cartUsername + "&cartItemId=" + item.id, {
                method: "DELETE"
            });

            loadCart();
        });

        cartItemsContainer.appendChild(cartCard);
    });

    cartSubtotal.textContent = formatPrice(subtotal);
    cartTotal.textContent = formatPrice(subtotal);
}

cartBackButton.addEventListener("click", () => {
    window.location.href = "/CustomerProducts.html";
});

cartLogoutButton.addEventListener("click", () => {
    logoutCartUser();
});

cartCheckoutButton.addEventListener("click", () => {
    window.location.href = "/Checkout.html";
});

if (requireCartLogin()) {
    loadCart();
}
