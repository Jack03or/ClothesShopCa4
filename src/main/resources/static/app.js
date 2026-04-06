const panelTitle = document.getElementById("panel-title");
const panelSubtitle = document.getElementById("panel-subtitle");
const messageBox = document.getElementById("message-box");

const forms = {
    register: document.getElementById("register-form"),
    "customer-login": document.getElementById("customer-login-form"),
    "admin-login": document.getElementById("admin-login-form")
};

const viewConfig = {
    register: {
        title: "Customer Registration",
        subtitle: "Create a customer account by sending the form data to the register endpoint."
    },
    "customer-login": {
        title: "Customer Login",
        subtitle: "Log in using a customer username and password."
    },
    "admin-login": {
        title: "Admin Login",
        subtitle: "Log in using an administrator username and password."
    }
};

function showView(viewName) {
    Object.values(forms).forEach((form) => form.classList.add("hidden"));
    forms[viewName].classList.remove("hidden");

    panelTitle.textContent = viewConfig[viewName].title;
    panelSubtitle.textContent = viewConfig[viewName].subtitle;
    hideMessage();
}

function showMessage(message, isError = false) {
    messageBox.textContent = message;
    messageBox.classList.remove("hidden");
    messageBox.classList.toggle("error", isError);
}

function hideMessage() {
    messageBox.classList.add("hidden");
    messageBox.classList.remove("error");
    messageBox.textContent = "";
}

async function sendRequest(url, bodyData) {
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(bodyData)
    });

    return response.text();
}

document.querySelectorAll(".action-card").forEach((button) => {
    button.addEventListener("click", () => {
        showView(button.dataset.view);
    });
});

forms.register.addEventListener("submit", async (event) => {
    event.preventDefault();

    const bodyData = {
        username: document.getElementById("register-username").value,
        email: document.getElementById("register-email").value,
        password: document.getElementById("register-password").value,
        role: "CUSTOMER"
    };

    const result = await sendRequest("/users/register", bodyData);
    showMessage(result, !result.toLowerCase().includes("successfully"));
    if (result.toLowerCase().includes("successfully")) {
        forms.register.reset();
    }
});

forms["customer-login"].addEventListener("submit", async (event) => {
    event.preventDefault();

    const bodyData = {
        username: document.getElementById("customer-login-username").value,
        password: document.getElementById("customer-login-password").value,
        role: "CUSTOMER"
    };

    const result = await sendRequest("/users/login", bodyData);
    showMessage(result, !result.toLowerCase().includes("login successful"));
});

forms["admin-login"].addEventListener("submit", async (event) => {
    event.preventDefault();

    const bodyData = {
        username: document.getElementById("admin-login-username").value,
        password: document.getElementById("admin-login-password").value,
        role: "ADMIN"
    };

    const result = await sendRequest("/users/login", bodyData);
    showMessage(result, !result.toLowerCase().includes("login successful"));

    if (result.toLowerCase().includes("login successful")) {
        window.location.href = "/AdminProducts.html";
    }
});
