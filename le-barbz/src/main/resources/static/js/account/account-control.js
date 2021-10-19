function modifyAccount() {
  const record = {
    firstname: document.getElementById("firstname").value,
    lastname: document.getElementById("lastname").value,
    email: document.getElementById("email").value,
    phone: document.getElementById("phone").value,
    address1: document.getElementById("homeaddress1").value,
    address2: document.getElementById("homeaddress2").value,
    city: document.getElementById("city").value,
    county: document.getElementById("county").value,
    country: document.getElementById("country").value,
    postalCode: document.getElementById("postcode").value
  };

  let urlParams = `?${Object.keys(record).map(e => `${e}=${record[e]}`).join('&')}`;
  const request = new XMLHttpRequest();
  request.open("POST", `/account/modify${urlParams}`);
  request.send();
  request.onload = () => {
    console.log(request.responseText);
    if (request.status == 200) {
      $('#success-modal').modal('toggle');
      setTimeout(() => window.location.replace('/account/view-account'), 1500);
    }
    else {
      const error_message = JSON.parse(request.responseText).message;
      document.querySelector('#failure-error-message').textContent = error_message;
      $('#failure-modal').modal('toggle');
    }
  }
}

function editState(id) {
  let urlParams = "?id=" + id;
  const request = new XMLHttpRequest();
  request.open("POST", "/account/order-details/close-order" + urlParams);
  request.send();
  request.onload = () => {
    console.log(request.responseText);
    $("#success-modal").modal("toggle");
    setTimeout(() => window.location.replace("/account/orders"), 3000);
  };
}



(function () {
  // Fetch all the forms we want to apply custom Bootstrap validation styles to.
  const form = Array.prototype.slice.call(document.querySelectorAll('.needs-validation'))[0]
  document.querySelector('#applyChanges').onclick = (event) => {
      // If any form has an error, prevent all forms from submitting.
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      } else {
        modifyAccount()
      }
      form.classList.add('was-validated')
    }
})()
