const customerProductList = document.getElementById("customer-product-list");
const customerLogoutButton = document.getElementById("customer-logout-button");
const viewCartButton = document.getElementById("view-cart-button");
const applyFiltersButton = document.getElementById("apply-filters-button");
const clearFiltersButton = document.getElementById("clear-filters-button");
const searchTitleInput = document.getElementById("search-title");
const searchManufacturerInput = document.getElementById("search-manufacturer");
const filterCategoryInput = document.getElementById("filter-category");
const filterSizeInput = document.getElementById("filter-size");
const sortByInput = document.getElementById("sort-by");
const sortDirectionInput = document.getElementById("sort-direction");
const CURRENT_USERNAME = "customer";

function buildStockText(variants) {
    return variants
        .map((variant) => variant.size + ": " + variant.stockQuantity)
        .join(", ");
}

function buildSearchUrl() {
    const params = new URLSearchParams();

    if (searchTitleInput.value.trim() !== "") {
        params.append("title", searchTitleInput.value.trim());
    }

    if (searchManufacturerInput.value.trim() !== "") {
        params.append("manufacturer", searchManufacturerInput.value.trim());
    }

    if (filterCategoryInput.value !== "") {
        params.append("category", filterCategoryInput.value);
    }

    if (filterSizeInput.value !== "") {
        params.append("size", filterSizeInput.value);
    }

    if (sortByInput.value !== "") {
        params.append("sortBy", sortByInput.value);
        params.append("sortDirection", sortDirectionInput.value);
    }

    const queryString = params.toString();

    if (queryString === "") {
        return "/products/all";
    }

    return "/products/search?" + queryString;
}

async function addToCart(selectedVariantId) {
    if (selectedVariantId === "") {
        alert("Please select a size first.");
        return;
    }

    const response = await fetch("/cart/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: CURRENT_USERNAME,
            productVariantId: Number(selectedVariantId),
            quantity: 1
        })
    });

    const result = await response.text();
    alert(result);
}

async function loadReviews(productId, reviewContainer) {
    const response = await fetch("/reviews/product?productId=" + productId);
    const reviews = await response.json();

    reviewContainer.innerHTML = "";

    if (reviews.length === 0) {
        reviewContainer.innerHTML = "<p>No reviews yet.</p>";
        return;
    }

    reviews.forEach((review) => {
        const reviewItem = document.createElement("div");
        reviewItem.className = "reviewItem";
        reviewItem.innerHTML = `
            <p><strong>${review.username}</strong> - Rating: ${review.rating}/5</p>
            <p>${review.comment}</p>
        `;
        reviewContainer.appendChild(reviewItem);
    });
}

async function loadCustomerProducts() {
    const response = await fetch(buildSearchUrl());
    const products = await response.json();

    customerProductList.innerHTML = "";

    if (products.length === 0) {
        customerProductList.innerHTML = "<p>No products match your search.</p>";
        return;
    }

    products.forEach((product) => {
        const card = document.createElement("div");
        card.className = "customerProductCard";

        let sizeOptions = "<option value=''>Select size</option>";

        product.variants.forEach((variant) => {
            sizeOptions = sizeOptions + "<option value='" + variant.id + "'>" + variant.size + " (" + variant.stockQuantity + ")</option>";
        });

        card.innerHTML = `
            <div class="customerProductImage">${product.imageUrl}</div>
            <div class="customerProductBody">
                <h3>${product.title}</h3>
                <p><strong>Manufacturer:</strong> ${product.manufacturer}</p>
                <p><strong>Category:</strong> ${product.category}</p>
                <p><strong>Price:</strong> €${product.price}</p>
                <p><strong>Sizes:</strong> ${buildStockText(product.variants)}</p>
                <label class="sizeLabel">Choose Size</label>
                <select class="productSizeSelect">${sizeOptions}</select>
                <button class="submit-button addCartButton" type="button">Add To Cart</button>

                <div class="reviewSection">
                    <h4>Leave A Review</h4>
                    <select class="reviewRatingSelect">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    <textarea class="reviewCommentInput" placeholder="Write your comment here"></textarea>
                    <button class="submit-button addReviewButton" type="button">Submit Review</button>
                </div>

                <div class="reviewList">
                    <h4>Reviews</h4>
                    <div class="productReviewContainer"></div>
                </div>
            </div>
        `;

        const sizeSelect = card.querySelector(".productSizeSelect");
        const addCartButton = card.querySelector(".addCartButton");
        const reviewRatingSelect = card.querySelector(".reviewRatingSelect");
        const reviewCommentInput = card.querySelector(".reviewCommentInput");
        const addReviewButton = card.querySelector(".addReviewButton");
        const reviewContainer = card.querySelector(".productReviewContainer");

        addCartButton.addEventListener("click", () => {
            addToCart(sizeSelect.value);
        });

        addReviewButton.addEventListener("click", async () => {
            const response = await fetch("/reviews/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    productId: product.id,
                    username: CURRENT_USERNAME,
                    rating: Number(reviewRatingSelect.value),
                    comment: reviewCommentInput.value
                })
            });

            const result = await response.text();
            alert(result);

            if (result.toLowerCase().includes("successfully")) {
                reviewCommentInput.value = "";
                reviewRatingSelect.value = "1";
                loadReviews(product.id, reviewContainer);
            }
        });

        customerProductList.appendChild(card);
        loadReviews(product.id, reviewContainer);
    });
}

function clearFilters() {
    searchTitleInput.value = "";
    searchManufacturerInput.value = "";
    filterCategoryInput.value = "";
    filterSizeInput.value = "";
    sortByInput.value = "";
    sortDirectionInput.value = "asc";
    loadCustomerProducts();
}

customerLogoutButton.addEventListener("click", () => {
    window.location.href = "/";
});

viewCartButton.addEventListener("click", () => {
    window.location.href = "/Cart.html";
});

applyFiltersButton.addEventListener("click", loadCustomerProducts);
clearFiltersButton.addEventListener("click", clearFilters);

loadCustomerProducts();
