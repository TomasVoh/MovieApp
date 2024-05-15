const emailInput = document.querySelector("#email");
const passwordInput = document.querySelector("#password");
emailInput.addEventListener("input", () => {
    const emailValue = emailInput.value;
    if(emailValue.includes("@") && emailValue !== "") {
        emailInput.classList.add("is-valid");
        emailInput.classList.remove("is-invalid")
    } else {
        emailInput.classList.add("is-invalid")
        emailInput.classList.remove("is-valid")
    }
})

passwordInput.addEventListener("input", () => {
    const passwordValue = passwordInput.value;
    if(passwordValue !== "") {
        passwordInput.classList.add("is-valid");
        passwordInput.classList.remove("is-invalid");
    } else {
        passwordInput.classList.add("is-invalid");
        passwordInput.classList.remove("is-valid");
    }
})