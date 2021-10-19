function editState(id, state) {
  let urlParams = "?id=" + id + "&" + "state=" + state;
  const request = new XMLHttpRequest();
  request.open("POST", "/admin/edit-order/change-state" + urlParams);
  request.send();
  request.onload = () => {
    console.log(request.responseText);
    $("#success-modal").modal("toggle");
    setTimeout(() => window.location.replace("/admin"), 1500);
  };
}

function emailCustomer(id) {
  window.location.replace(`/admin/email/customer?orderId=${id}`)
}