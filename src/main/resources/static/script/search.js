let select = document.getElementById("actors")
let options = select.querySelectorAll("option");
let input = document.querySelector("#findActors");
let disabledOption = options[0];
input.addEventListener(("input"), () => {
    let text = input.value;
    options.forEach((option) => {
        console.log(option.textContent.includes(text));
        if(option.textContent.includes(text) && option !== disabledOption) {
            disabledOption.after(option);
        }
    })
})