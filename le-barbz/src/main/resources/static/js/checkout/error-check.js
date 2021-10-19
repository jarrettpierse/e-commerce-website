function errorChecking() {
    var x = document.forms["OrderForm"]["cardNum"].value;
    var y = document.forms["OrderForm"]["expNum"].value;
    var z = document.forms["OrderForm"]["cvvNum"].value;

    if (x.match(/[^$,.\d]/) || x.length != 16) {
        alert("Card Number is Invalid!");
        return false;
    }
    else if (y.length > 5) {
        alert("Expiration Number is Invalid!");
        return false;
    }
    else if (z.match(/[^$,.\d]/) || z.length != 3) {
        alert("CVV is Invalid!");
        return false;
    }
    else {
        window.location.replace("/order-success");
        return true;
    }
}