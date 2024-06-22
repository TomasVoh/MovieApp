const canvas = document.querySelector("canvas");
const ctx = canvas.getContext("2d");
const p = document.querySelectorAll("p");
p.forEach((p, index) => {
    let genrePercent = Number(p.textContent.split(" ")[1].replace("%", "").replace(",", "."));
    const y = 250 - genrePercent * 2.5;
    ctx.rect(index * 120, y, 100, genrePercent * 2.5);
    ctx.fillStyle = "#ebeb05";
    ctx.fill();
    ctx.fillStyle = "white";
    ctx.font = "12px Poppins";
    ctx.fillText(p.textContent, index * 120, 270);
})