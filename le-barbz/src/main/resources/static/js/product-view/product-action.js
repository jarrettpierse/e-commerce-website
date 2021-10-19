function addToCart(id) {
    var record = {
        quantity: document.getElementById("quantity_input").value,
    };
    var xhr = new XMLHttpRequest();
    xhr.onload = addedToCart();
    xhr.open("POST", "/cart/add?id=" + id + "&quantity=" + document.getElementById("quantity_input").value);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(record));
}

function addedToCart() {
    document.getElementById("cart").innerHTML = "Added to cart";
}

function addToWishlist(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/wishlist/add?id=" + id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify());
    document.getElementById("response").innerHTML = "Added to wishlist.";
    document.getElementById("wishlist-buttons").innerHTML = "<button class='btn btn-primary' id='wishlist' onclick='removeFromWishlist(" + id + ")'> Remove from wishlist </button>"
}

function removeFromWishlist(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/wishlist/remove?id=" + id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify());
    document.getElementById("response").innerHTML = "Removed from wishlist.";
    document.getElementById("wishlist-buttons").innerHTML = "<button class=\"btn btn-primary\" id=\"wishlist\" onclick='addToWishlist(" + id + ")'> Add to wishlist </button>"

}
