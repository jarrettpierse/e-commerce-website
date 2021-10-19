const deleteFromWishlist = (id) => {
  const path = `/wishlist/remove?id=${id}`

  const xhr = new XMLHttpRequest()
  xhr.open("POST", path)
  xhr.setRequestHeader("Content-Type", "application/json")
  xhr.send()
  xhr.onload = removeProductFromDom(id)
}

const removeProductFromDom = (id) => {
  const domElement = document.querySelector(`#product-${id}`)
  domElement.parentElement.removeChild(domElement)
}