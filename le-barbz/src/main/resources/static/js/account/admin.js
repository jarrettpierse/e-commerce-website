function createAdmin() {
  const record = {
    firstname: document.getElementById("firstname").value,
    lastname: document.getElementById("lastname").value,
    email: document.getElementById("email").value,
    username: document.getElementById("username").value,
    password: document.getElementById("password").value,
    phone: document.getElementById("phone").value,
  };

  let urlParams = `?${Object.keys(record)
    .map((e) => `${e}=${record[e]}`)
    .join("&")}`;

  const request = new XMLHttpRequest();
  request.open("POST", `/admin/add${urlParams}`);
  request.send();
  request.onload = () => {
    console.log(request.responseText);
    if (request.status == 200) {
      $("#success-modal").modal("toggle");
      setTimeout(() => window.location.replace("/login"), 3000);
    } else {
      const error_message = JSON.parse(request.responseText).message;
      document.querySelector(
        "#failure-error-message"
      ).textContent = error_message;
      $("#failure-modal").modal("toggle");
    }
  };
}

(function () {
  // Fetch all the forms we want to apply custom Bootstrap validation styles to.
  const form = Array.prototype.slice.call(
    document.querySelectorAll(".needs-validation")
  )[0];

  document.querySelector("#submit-button").onclick = (event) => {
    // If any form has an error, prevent all forms from submitting.
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      createAdmin();
    }
    form.classList.add("was-validated");
  };
})();

function openOrders(orderStatus) {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", "/admin/order-history?status=" + orderStatus);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send();
  xhr.addEventListener("load", () => reqListener(xhr.responseText));
}

function viewOrder(id) {
  var xml = new XMLHttpRequest();
  xml.open("GET", "/admin/view-order/" + id);
  xml.send();
}


function reqListener(text) {
  var div = document.querySelector("#orderHistoryView");
  div.innerHTML = text;
}
