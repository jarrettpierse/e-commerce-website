function hideProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/product/hide?id=" + id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify());
    document.getElementById("hide-buttons-"+ id).innerHTML = "<button class='btn btn-info' onclick='unhideProduct(" + id + ")'> Unhide </button>"
}

function unhideProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/product/unhide?id=" + id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify());
    document.getElementById("hide-buttons-"+ id).innerHTML = "<button class='btn btn-info' onclick='hideProduct(" + id + ")'> Hide </button>"
}