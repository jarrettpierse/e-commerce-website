function sendEmail() {
  var xhr = new XMLHttpRequest();
  xhr.onload = sentEmail();
  xhr.open(
    "POST",
    "/admin/email?email=" +
      document.getElementById("email").value +
      "&subject=" +
      document.getElementById("subject").value +
      "&message=" +
      document.getElementById("message").value
  );
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.send();
}
function sentEmail() { 
}