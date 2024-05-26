const btn = document.getElementById("recommendBtn");
const main = document.querySelector("main");
btn.addEventListener('click', () => {
    fetch("http://localhost:8080/recommend/new").then((res) => {
        return res.json();
    }).then((list) => {
        console.log(list[0]);
    })
})