const customerProductList = document.getElementById("customer-product-list");
const customerLogoutButton = document.getElementById("customer-logout-button");

function buildStockText(variants) {
    return variants
        .map((variant) => variant.size + ": " + variant.stockQuantity)
        .join(", ");
}

async function loadCustomerProducts() {
    const response = await fetch("/products/all");
    const products = await response.json();

    customerProductList.innerHTML = "";

    if (products.length === 0) {
        customerProductList.innerHTML = "<p>No products available yet.</p>";
        return;
    }

    products.forEach((product) => {
        const card = document.createElement("div");
        card.className = "customerProductCard";
        card.innerHTML = `
            <div class="customerProductImage">${product.imageUrl}</div>
            <div class="customerProductBody">
                <h3>${product.title}</h3>
                <p><strong>Manufacturer:</strong> ${product.manufacturer}</p>
                <p><strong>Category:</strong> ${product.category}</p>
                <p><strong>Price:</strong> €${product.price}</p>
                <p><strong>Sizes:</strong> ${buildStockText(product.variants)}</p>
            </div>
        `;

        customerProductList.appendChild(card);
    });
}

customerLogoutButton.addEventListener("click", () => {
    window.location.href = "/";
});

loadCustomerProducts();
