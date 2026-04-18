const adminCustomerList = document.getElementById("admin-customer-list");
const adminPurchaseHistory = document.getElementById("admin-purchase-history");
const adminCustomersBackButton = document.getElementById("admin-customers-back-button");
const adminCustomersLogoutButton = document.getElementById("admin-customers-logout-button");

function requireAdminLogin() {
    const username = sessionStorage.getItem("loggedInUsername");
    const role = sessionStorage.getItem("loggedInRole");

    if (!username || role !== "ADMIN") {
        window.location.href = "/";
        return false;
    }

    return true;
}

function logoutAdminUser() {
    sessionStorage.removeItem("loggedInUsername");
    sessionStorage.removeItem("loggedInRole");
    window.location.href = "/";
}

function formatAdminPrice(value) {
    return "EUR" + Number(value).toFixed(2);
}

async function loadPurchaseHistory(username) {
    const response = await fetch("/admin/purchase-history?username=" + encodeURIComponent(username));
    const orders = await response.json();

    adminPurchaseHistory.innerHTML = "";

    if (orders.length === 0) {
        adminPurchaseHistory.innerHTML = "<p>No purchase history found for this customer.</p>";
        return;
    }

    for (let i = 0; i < orders.length; i++) {
        const order = orders[i];
        const orderCard = document.createElement("div");
        orderCard.className = "adminHistoryCard";

        let itemsHtml = "";

        for (let j = 0; j < order.items.length; j++) {
            const item = order.items[j];
            itemsHtml = itemsHtml + `
                <div class="adminHistoryItem">
                    <span>${item.productTitle} (${item.size})</span>
                    <span>${item.quantity} x ${formatAdminPrice(item.price)}</span>
                </div>
            `;
        }

        orderCard.innerHTML = `
            <h3>Order #${order.id}</h3>
            <p><strong>Date:</strong> ${order.createdAt}</p>
            <p><strong>Subtotal:</strong> ${formatAdminPrice(order.subtotalPrice)}</p>
            <p><strong>Discount:</strong> ${formatAdminPrice(order.discountAmount)}</p>
            <p><strong>Total:</strong> ${formatAdminPrice(order.totalPrice)}</p>
            <div class="adminHistoryItems">${itemsHtml}</div>
        `;

        adminPurchaseHistory.appendChild(orderCard);
    }
}

async function loadCustomers() {
    const response = await fetch("/admin/customers");
    const customers = await response.json();

    adminCustomerList.innerHTML = "";

    if (customers.length === 0) {
        adminCustomerList.innerHTML = "<p>No customer accounts found.</p>";
        adminPurchaseHistory.innerHTML = "<p>Select a customer to view purchase history.</p>";
        return;
    }

    adminPurchaseHistory.innerHTML = "<p>Select a customer to view purchase history.</p>";

    for (let i = 0; i < customers.length; i++) {
        const customer = customers[i];
        const customerCard = document.createElement("div");
        customerCard.className = "adminCustomerCard";
        customerCard.innerHTML = `
            <h3>${customer.username}</h3>
            <p><strong>Email:</strong> ${customer.email}</p>
            <p><strong>Loyalty Card:</strong> ${customer.loyaltyCard ? "Yes" : "No"}</p>
            <p><strong>Orders:</strong> ${customer.orderCount}</p>
            <button type="button" class="submit-button viewHistoryButton">View Purchase History</button>
        `;

        const viewHistoryButton = customerCard.querySelector(".viewHistoryButton");
        viewHistoryButton.addEventListener("click", () => {
            loadPurchaseHistory(customer.username);
        });

        adminCustomerList.appendChild(customerCard);
    }
}

adminCustomersBackButton.addEventListener("click", () => {
    window.location.href = "/AdminProducts.html";
});

adminCustomersLogoutButton.addEventListener("click", () => {
    logoutAdminUser();
});

if (requireAdminLogin()) {
    loadCustomers();
}
