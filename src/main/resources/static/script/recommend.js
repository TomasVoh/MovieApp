const btn = document.getElementById("recommendBtn");
const recommend = document.querySelector(".recommend");
btn.addEventListener('click', () => {
    recommend.innerHTML = "";
    fetch("http://localhost:8080/recommend/new").then((res) => {
        return res.json();
    }).then((list) => {
        list.forEach((movie) => {
            appendMovie(movie);
        })
    })
})

function appendMovie(movie) {
    let div = document.createElement("div");
    let movieRef = document.createElement("a");
    div.classList.add("text-center", "my-2")
    movieRef.classList.add("ref");
    movieRef.setAttribute("href", "movie/" + movie.id);
    movieRef.textContent = movie.name;
    div.append(movieRef);
    recommend.append(div);
}