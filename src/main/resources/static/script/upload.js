const input = document.getElementById("upload");
const form = document.getElementById("uploadForm");

input.addEventListener("change", () => {
    let end = input.value.split(".")[1];
    if(end !== "xlsx") {
        console.log("neodpovída požadovánému formátu");
        return;
    }
    form.submit();
})