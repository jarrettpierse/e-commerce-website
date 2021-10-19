function sendEmail() {
  var record = {
    name: document.getElementById("name").value,
    email: document.getElementById("email").value,
    quantity: document.getElementById("message").value,
  };
  var xhr = new XMLHttpRequest();
  console.log(record.email);
  xhr.onload = sentEmail();
  xhr.open(
    "POST",
    "/about/email?name=" +
      document.getElementById("name").value +
      "&email=" +
      document.getElementById("email").value +
      "&message=" +
      document.getElementById("message").value
  );
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send(JSON.stringify(record));
}

function sentEmail() {
    
}