function placeOrder() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/place-order");
    xhr.send();
    window.location.replace("/order-success");
}
