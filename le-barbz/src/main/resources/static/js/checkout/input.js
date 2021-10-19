;(function () {
  'use strict'
  
  // Hide the shipping address input if it is the same as the billing address
  $('#same-address').click((e) => {
    const shippingAddress = document.querySelector('#shipping-address')
    if (shippingAddress.style.display === 'none')
      shippingAddress.style.display = 'initial'
    else shippingAddress.style.display = 'none'
  })
})()
