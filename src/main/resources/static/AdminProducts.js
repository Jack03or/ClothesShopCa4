const productForm = document.getElementById("product-form");
const productMessageBox = document.getElementById("product-message-box");
const productList = document.getElementById("product-list");
const loadProductsButton = document.getElementById("load-products-button");
const backButton = document.getElementById("back-button");

function showProductMessage(message, isError = false) {
    productMessageBox.textContent = message;
    productMessageBox.classList.remove("hidden");
    productMessageBox.classList.toggle("error", isError);
}

function createVariant(size, stockQuantity) {
    return {
        size: size,
        stockQuantity: Number(stockQuantity)
    };
}

function buildVariants() {
    return [
        createVariant("XS", document.getElementById("stock-xs").value),
        createVariant("S", document.getElementById("stock-s").value),
        createVariant("M", document.getElementById("stock-m").value),
        createVariant("L", document.getElementById("stock-l").value),
        createVariant("XL", document.getElementById("stock-xl").value)
    ];
}

async function loadProducts() {
    const response = await fetch("/products/all");
    const products = await response.json();

    productList.innerHTML = "";

    if (products.length === 0) {
        productList.innerHTML = "<p>No products added yet.</p>";
        return;
    }

    products.forEach((product) => {
        const item = document.createElement("div");
        item.className = "productRow";

        const variantText = product.variants
            .map((variant) => variant.size + ": " + variant.stockQuantity)
            .join(", ");

        item.innerHTML = `
            <span>${product.title}</span>
            <span>${product.manufacturer}</span>
            <span>${product.category}</span>
            <span>€${product.price}</span>
            <span>${product.imageUrl}</span>
            <span>${variantText}</span>
        `;

        productList.appendChild(item);
    });
}

productForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const productData = {
        title: document.getElementById("product-title").value,
        manufacturer: document.getElementById("product-manufacturer").value,
        price: Number(document.getElementById("product-price").value),
        category: document.getElementById("product-category").value,
        imageUrl: document.getElementById("product-image").value,
        variants: buildVariants()
    };

    const response = await fetch("/products/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(productData)
    });

    const result = await response.text();
    showProductMessage(result, !result.toLowerCase().includes("successfully"));

    if (result.toLowerCase().includes("successfully")) {
        productForm.reset();
        loadProducts();
    }
});

loadProductsButton.addEventListener("click", loadProducts);

backButton.addEventListener("click", () => {
    window.location.href = "/";
});

loadProducts();
