function createAccount() {
  
  const record = {
    firstname: document.getElementById("firstname").value,
    lastname: document.getElementById("lastname").value,
    email: document.getElementById("email").value,
    username: document.getElementById("username").value,
    password: document.getElementById("password").value,
    phone: document.getElementById("phone").value,
    address1: document.getElementById("address1").value,
    address2: document.getElementById("address2").value,
    city: document.getElementById("towncity").value,
    county: document.getElementById("county").value,
    country: document.getElementById("country").value,
    postalCode: document.getElementById("postcode").value
  };

  let urlParams = `?${Object.keys(record).map(e => `${e}=${record[e]}`).join('&')}`;
  
  const request = new XMLHttpRequest();
  request.open("POST", `/account/add${urlParams}`);
  request.send();
  request.onload = () => {
    console.log(request.responseText);
    if (request.status == 200) {
      $('#success-modal').modal('toggle');
      setTimeout(() => window.location.replace('/login'), 3000);
    }
    else {
      const error_message = JSON.parse(request.responseText).message;
      document.querySelector('#failure-error-message').textContent = error_message;
      $('#failure-modal').modal('toggle');
    }
  }
}

(function () {
  // Fetch all the forms we want to apply custom Bootstrap validation styles to.
  const form = Array.prototype.slice.call(document.querySelectorAll('.needs-validation'))[0]
  document.querySelector('#submit-button').onclick = (event) => {
      // If any form has an error, prevent all forms from submitting.
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      } else {
        createAccount()
      }
      form.classList.add('was-validated')
    }
})()
