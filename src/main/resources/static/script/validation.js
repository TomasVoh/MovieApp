const emailInput = document.querySelector("#email");
const passwordInput = document.querySelector("#password");
emailInput.classList.add("is-invalid");
passwordInput.classList.add("is-invalid");
emailInput.addEventListener("input", () => {
    const emailValue = emailInput.value;
    if(emailValue.includes("@") && emailValue !== "") {
        emailInput.classList.replace("is-invalid", "is-valid");
    }
})

passwordInput.addEventListener("input", () => {
    const passwordValue = emailInput.value;
    if(passwordValue !== "") {
        passwordInput.classList.replace("is-invalid", "is-valid");
    }
})