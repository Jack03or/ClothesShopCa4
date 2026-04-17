const productForm = document.getElementById("product-form");
const productMessageBox = document.getElementById("product-message-box");
const productList = document.getElementById("product-list");
const viewCustomersButton = document.getElementById("view-customers-button");
const loadProductsButton = document.getElementById("load-products-button");
const backButton = document.getElementById("back-button");

function showProductMessage(message, isError = false) {
    productMessageBox.textContent = message;
    productMessageBox.classList.remove("hidden");
    productMessageBox.classList.toggle("error", isError);
    productMessageBox.scrollIntoView({ behavior: "smooth", block: "center" });
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

function buildVariantText(variants) {
    let variantText = "";

    for (let i = 0; i < variants.length; i++) {
        if (i > 0) {
            variantText = variantText + ", ";
        }

        variantText = variantText + variants[i].size + ": " + variants[i].stockQuantity;
    }

    return variantText;
}

function buildVariantOptions(variants) {
    let variantOptions = "";

    for (let i = 0; i < variants.length; i++) {
        variantOptions = variantOptions + "<option value='" + variants[i].id + "'>" + variants[i].size + "</option>";
    }

    return variantOptions;
}

async function addWholesalerStock(productVariantId, quantity) {
    const response = await fetch("/products/wholesaler-stock", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            productVariantId: Number(productVariantId),
            quantity: Number(quantity)
        })
    });

    return response.text();
}

async function loadProducts() {
    const response = await fetch("/products/all");
    const products = await response.json();

    productList.innerHTML = "";

    if (products.length === 0) {
        productList.innerHTML = "<p>No products added yet.</p>";
        return;
    }

    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        const item = document.createElement("div");
        item.className = "productRow";

        item.innerHTML = `
            <span>${product.title}</span>
            <span>${product.manufacturer}</span>
            <span>${product.category}</span>
            <span>EUR${product.price}</span>
            <span>${product.imageUrl}</span>
            <span>${buildVariantText(product.variants)}</span>
            <div class="stockActionBox">
                <select class="stockVariantSelect">${buildVariantOptions(product.variants)}</select>
                <input type="number" min="1" value="1" class="stockQuantityInput">
                <button type="button" class="submit-button stockUpdateButton">Add Stock</button>
            </div>
        `;

        const stockVariantSelect = item.querySelector(".stockVariantSelect");
        const stockQuantityInput = item.querySelector(".stockQuantityInput");
        const stockUpdateButton = item.querySelector(".stockUpdateButton");

        stockUpdateButton.addEventListener("click", async () => {
            const result = await addWholesalerStock(stockVariantSelect.value, stockQuantityInput.value);
            showProductMessage(result, !result.toLowerCase().includes("updated"));

            if (result.toLowerCase().includes("updated")) {
                loadProducts();
            }
        });

        productList.appendChild(item);
    }
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

viewCustomersButton.addEventListener("click", () => {
    window.location.href = "/AdminCustomers.html";
});

backButton.addEventListener("click", () => {
    window.location.href = "/";
});

loadProducts();
