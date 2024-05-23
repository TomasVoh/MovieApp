const btn = document.querySelector("button");
const main = document.querySelector("main");
btn.addEventListener('click', () => {
    fetch("http://localhost:8080/recommend/new").then((res) => {
        console.log(res.json());
    })
})