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
const loyaltyStatusText = document.getElementById("loyalty-status-text");
const toggleLoyaltyButton = document.getElementById("toggle-loyalty-button");

function getCurrentUsername() {
    return sessionStorage.getItem("loggedInUsername");
}

function getUsernameFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get("username");
}

function ensureCustomerSession() {
    const username = getCurrentUsername();
    const usernameFromUrl = getUsernameFromUrl();

    if (!username && usernameFromUrl) {
        sessionStorage.setItem("loggedInUsername", usernameFromUrl);
        sessionStorage.setItem("loggedInRole", "CUSTOMER");
        return usernameFromUrl;
    }

    return username;
}

function requireCustomerLogin() {
    const username = ensureCustomerSession();

    if (!username) {
        window.location.href = "/";
        return null;
    }

    return username;
}

function logoutUser() {
    sessionStorage.removeItem("loggedInUsername");
    sessionStorage.removeItem("loggedInRole");
    window.location.href = "/";
}

async function loadCurrentUser() {
    const currentUsername = getCurrentUsername();

    if (!currentUsername) {
        return null;
    }

    const response = await fetch("/users/all");
    const users = await response.json();

    for (let i = 0; i < users.length; i++) {
        if (users[i].username === currentUsername) {
            return users[i];
        }
    }

    return null;
}

async function loadLoyaltyStatus() {
    const user = await loadCurrentUser();

    if (!user) {
        loyaltyStatusText.textContent = "Loyalty Card: Not available";
        toggleLoyaltyButton.textContent = "Add Loyalty Card";
        return;
    }

    if (user.hasLoyaltyCard) {
        loyaltyStatusText.textContent = "Loyalty Card: Active (10% discount applies)";
        toggleLoyaltyButton.textContent = "Remove Loyalty Card";
    } else {
        loyaltyStatusText.textContent = "Loyalty Card: Not added";
        toggleLoyaltyButton.textContent = "Add Loyalty Card";
    }
}

async function toggleLoyaltyCard() {
    const user = await loadCurrentUser();
    const currentUsername = getCurrentUsername();

    if (!user || !currentUsername) {
        window.location.href = "/";
        return;
    }

    const response = await fetch("/users/loyalty", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: currentUsername,
            hasLoyaltyCard: !user.hasLoyaltyCard
        })
    });

    const result = await response.text();
    alert(result);
    await loadLoyaltyStatus();
}

function buildStockText(variants) {
    return variants
        .map((variant) => variant.size + ": " + variant.stockQuantity)
        .join(", ");
}

function buildStars(rating) {
    let stars = "";

    for (let i = 1; i <= 5; i++) {
        if (i <= rating) {
            stars = stars + "★";
        } else {
            stars = stars + "☆";
        }
    }

    return stars;
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
    const currentUsername = getCurrentUsername();

    if (!currentUsername) {
        window.location.href = "/";
        return;
    }

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
            username: currentUsername,
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
            <p><strong>${review.username}</strong></p>
            <p class="reviewStars">${buildStars(review.rating)}</p>
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
                    <div class="starRating">
                        <button class="starButton active" type="button" data-rating="1">★</button>
                        <button class="starButton" type="button" data-rating="2">★</button>
                        <button class="starButton" type="button" data-rating="3">★</button>
                        <button class="starButton" type="button" data-rating="4">★</button>
                        <button class="starButton" type="button" data-rating="5">★</button>
                    </div>
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
        const starButtons = card.querySelectorAll(".starButton");
        const reviewCommentInput = card.querySelector(".reviewCommentInput");
        const addReviewButton = card.querySelector(".addReviewButton");
        const reviewContainer = card.querySelector(".productReviewContainer");
        let selectedRating = 1;

        addCartButton.addEventListener("click", () => {
            addToCart(sizeSelect.value);
        });

        starButtons.forEach((button) => {
            button.addEventListener("click", () => {
                selectedRating = Number(button.dataset.rating);

                starButtons.forEach((starButton) => {
                    if (Number(starButton.dataset.rating) <= selectedRating) {
                        starButton.classList.add("active");
                    } else {
                        starButton.classList.remove("active");
                    }
                });
            });
        });

        addReviewButton.addEventListener("click", async () => {
            const currentUsername = getCurrentUsername();

            if (!currentUsername) {
                window.location.href = "/";
                return;
            }

            const response = await fetch("/reviews/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    productId: product.id,
                    username: currentUsername,
                    rating: selectedRating,
                    comment: reviewCommentInput.value
                })
            });

            const result = await response.text();
            alert(result);

            if (result.toLowerCase().includes("successfully")) {
                reviewCommentInput.value = "";
                selectedRating = 1;
                starButtons.forEach((starButton) => {
                    if (Number(starButton.dataset.rating) <= selectedRating) {
                        starButton.classList.add("active");
                    } else {
                        starButton.classList.remove("active");
                    }
                });
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
    logoutUser();
});

viewCartButton.addEventListener("click", () => {
    window.location.href = "/Cart.html";
});

applyFiltersButton.addEventListener("click", loadCustomerProducts);
clearFiltersButton.addEventListener("click", clearFilters);
toggleLoyaltyButton.addEventListener("click", toggleLoyaltyCard);

if (requireCustomerLogin()) {
    loadLoyaltyStatus();
    loadCustomerProducts();
}
