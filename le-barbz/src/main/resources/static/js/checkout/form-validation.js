;(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to.
  const forms = Array.prototype.slice.call(document.querySelectorAll('.needs-validation'))

  // Loop over the forms and prevent submission if any are in error.
  forms.forEach((form) => {
    form.addEventListener(
      'submit',
      (event) => {
        // If any form has an error, prevent all forms from submitting.
        if (!forms.map(f => f.checkValidity()).reduce((a, b) => a || b, false)) {
          event.preventDefault()
          event.stopPropagation()
        }

        forms.forEach(f => f.classList.add('was-validated'))
      },
      false,
    )
  })
})()
