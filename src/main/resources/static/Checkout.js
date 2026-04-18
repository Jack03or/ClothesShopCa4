const checkoutForm = document.getElementById("checkout-form");
const checkoutItems = document.getElementById("checkout-items");
const checkoutSubtotal = document.getElementById("checkout-subtotal");
const checkoutDiscount = document.getElementById("checkout-discount");
const checkoutTotal = document.getElementById("checkout-total");
const checkoutMessage = document.getElementById("checkout-message");
const checkoutBackButton = document.getElementById("checkout-back-button");
const checkoutLogoutButton = document.getElementById("checkout-logout-button");

function getCheckoutUsername() {
    return sessionStorage.getItem("loggedInUsername");
}

function requireCheckoutLogin() {
    const username = getCheckoutUsername();

    if (!username) {
        window.location.href = "/";
        return null;
    }

    return username;
}

function logoutCheckoutUser() {
    sessionStorage.removeItem("loggedInUsername");
    sessionStorage.removeItem("loggedInRole");
    window.location.href = "/";
}

function formatCheckoutPrice(value) {
    return "EUR" + Number(value).toFixed(2);
}

function showCheckoutMessage(message, isError) {
    checkoutMessage.textContent = message;
    checkoutMessage.classList.remove("hidden");
    checkoutMessage.classList.toggle("error", isError);
}

async function loadSavedAccountDetails() {
    const checkoutUsername = getCheckoutUsername();

    if (!checkoutUsername) {
        return;
    }

    const response = await fetch("/users/details?username=" + encodeURIComponent(checkoutUsername));
    const details = await response.json();

    if (!details) {
        return;
    }

    document.getElementById("full-name").value = details.cardHolderName || "";
    document.getElementById("address-line").value = details.shippingAddress || "";
    document.getElementById("city").value = details.city || "";
    document.getElementById("country").value = details.country || "";
    document.getElementById("card-name").value = details.cardHolderName || "";
    document.getElementById("card-number").value = "";
}

async function loadCheckoutSummary() {
    const checkoutUsername = getCheckoutUsername();

    if (!checkoutUsername) {
        window.location.href = "/";
        return;
    }

    const response = await fetch("/cart?username=" + checkoutUsername);
    const cart = await response.json();

    checkoutItems.innerHTML = "";

    if (!cart || !cart.items || cart.items.length === 0) {
        checkoutItems.innerHTML = "<p>Your cart is empty.</p>";
        checkoutSubtotal.textContent = "EUR0.00";
        checkoutDiscount.textContent = "EUR0.00";
        checkoutTotal.textContent = "EUR0.00";
        return;
    }

    cart.items.forEach((item) => {
        const line = document.createElement("div");
        line.className = "checkoutItemRow";
        const itemTotal = Number(item.price) * item.quantity;

        line.innerHTML = `
            <span>${item.productTitle} (${item.size}) x ${item.quantity}</span>
            <span>${formatCheckoutPrice(itemTotal)}</span>
        `;

        checkoutItems.appendChild(line);
    });

    checkoutSubtotal.textContent = formatCheckoutPrice(cart.subtotal);
    checkoutDiscount.textContent = formatCheckoutPrice(cart.discountAmount);
    checkoutTotal.textContent = formatCheckoutPrice(cart.totalPrice);
}

checkoutForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const checkoutUsername = getCheckoutUsername();

    if (!checkoutUsername) {
        window.location.href = "/";
        return;
    }

    const response = await fetch("/checkout?username=" + checkoutUsername, {
        method: "POST"
    });

    const result = await response.text();
    const success = result.toLowerCase().includes("successfully");
    showCheckoutMessage(result, !success);

    if (success) {
        checkoutForm.reset();
        window.location.href = "/OrderComplete.html";
    }
});

checkoutBackButton.addEventListener("click", () => {
    window.location.href = "/Cart.html";
});

checkoutLogoutButton.addEventListener("click", () => {
    logoutCheckoutUser();
});

if (requireCheckoutLogin()) {
    loadSavedAccountDetails();
    loadCheckoutSummary();
}
