function changeQuantity(id) {
    var record = {
        quantity: document.getElementById("quantity_input_" + id).value,
    };
    var xhr = new XMLHttpRequest();
    xhr.onload = addedToCart;
    xhr.open("POST", "/cart/changeQuantity?id=" + id + "&quantity=" + document.getElementById("quantity_input_" + id).value);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(record));

    xhr.onload = () => {
      var xml = new XMLHttpRequest();
      xml.open("GET", "/fragments/cartSummary");
      xml.send();
      xml.addEventListener("load", () => reqListener(xml.responseText));
    }
}

function removeProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cart/removeProduct?id=" + id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
    $("#product_" + id).remove();

    xhr.onload = () => {
      var xml = new XMLHttpRequest();
      xml.open("GET", "/fragments/cartSummary");
      xml.send();
      xml.addEventListener("load", () => reqListener(xml.responseText));
    }
}

function addedToCart() {
    var record = JSON.parse(this.responseText);
    document.getElementById("response").innerHTML = "Added " + document.getElementById("quantity_input").value + " units to your cart.";
}

function incrementValue(id) {
    var c = parseInt(document.getElementById("quantity_input_" + id).value);
    c++;
    document.getElementById("quantity_input_" + id).value = c;
    document.getElementById("quantity_input_" + id).innerHTML = c;

    changeQuantity(id);
  }
  
  function decrementValue(id) {
    var c = parseInt(document.getElementById("quantity_input_" + id).value);
    c--;

    if(c < 1){
        alert("Quantity cannot go less than 1. Please use the remove button.")
        return;
    }
    
    document.getElementById("quantity_input_" + id).value = c;
    document.getElementById("quantity_input_" + id).innerHTML = c;

    changeQuantity(id);
  }

  function reqListener(text) {
      var div = document.querySelector("#totalCost");
      div.innerHTML = text;
  }
