const accountForm = document.getElementById("account-form");
const accountUsernameInput = document.getElementById("account-username");
const accountEmailInput = document.getElementById("account-email");
const accountAddressInput = document.getElementById("account-address");
const accountCityInput = document.getElementById("account-city");
const accountCountryInput = document.getElementById("account-country");
const accountPaymentMethodInput = document.getElementById("account-payment-method");
const accountCardHolderInput = document.getElementById("account-card-holder");
const accountMessageBox = document.getElementById("account-message-box");
const accountBackButton = document.getElementById("account-back-button");
const accountLogoutButton = document.getElementById("account-logout-button");

function getAccountUsername() {
    return sessionStorage.getItem("loggedInUsername");
}

function requireAccountLogin() {
    const username = getAccountUsername();

    if (!username) {
        window.location.href = "/";
        return null;
    }

    return username;
}

function logoutAccountUser() {
    sessionStorage.removeItem("loggedInUsername");
    sessionStorage.removeItem("loggedInRole");
    window.location.href = "/";
}

function showAccountMessage(message, isError = false) {
    accountMessageBox.textContent = message;
    accountMessageBox.classList.remove("hidden");
    accountMessageBox.classList.toggle("error", isError);
}

async function loadAccountDetails() {
    const username = getAccountUsername();

    if (!username) {
        window.location.href = "/";
        return;
    }

    const response = await fetch("/users/details?username=" + encodeURIComponent(username));
    const details = await response.json();

    if (!details) {
        showAccountMessage("Account details could not be loaded.", true);
        return;
    }

    accountUsernameInput.value = details.username || "";
    accountEmailInput.value = details.email || "";
    accountAddressInput.value = details.shippingAddress || "";
    accountCityInput.value = details.city || "";
    accountCountryInput.value = details.country || "";
    accountPaymentMethodInput.value = details.paymentMethod || "";
    accountCardHolderInput.value = details.cardHolderName || "";
}

accountForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const username = getAccountUsername();

    if (!username) {
        window.location.href = "/";
        return;
    }

    const response = await fetch("/users/details", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            shippingAddress: accountAddressInput.value,
            city: accountCityInput.value,
            country: accountCountryInput.value,
            paymentMethod: accountPaymentMethodInput.value,
            cardHolderName: accountCardHolderInput.value
        })
    });

    const result = await response.text();
    showAccountMessage(result, !result.toLowerCase().includes("successfully"));
});

accountBackButton.addEventListener("click", () => {
    window.location.href = "/CustomerProducts.html";
});

accountLogoutButton.addEventListener("click", () => {
    logoutAccountUser();
});

if (requireAccountLogin()) {
    loadAccountDetails();
}
