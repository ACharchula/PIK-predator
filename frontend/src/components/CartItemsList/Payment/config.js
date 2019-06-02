const supportedPaymentMethods = [
    {
      supportedMethods: 'basic-card',
    }
  ];
  const paymentDetails = {
    total: {
      label: 'Total',
      amount:{
        currency: 'USD',
        value: 0
      }
    }
  };
  // Options isn't required.
  const options = {};

  const getConfig = (target) => {
    const auth = 'Bearer '.concat(localStorage.getItem('id')); 
    console.log(auth);
    const paymentRequest = new PaymentRequest(
        supportedPaymentMethods, paymentDetails, options);
    paymentRequest.show()
    .then((paymentResponse) => {
      // Close the payment request UI.
      return paymentResponse.complete()
      .then(() => {
        fetch(`https://pik-predator.herokuapp.com/users/2/cart/checkout`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': auth,
          },
          body: JSON.stringify({
            firstName: target.firstName.value,
            lastName: target.lastName.value,
            email: target.email.value,
            street: target.street.value,
            houseNumber: target.houseNumber.value,
            localNumber: target.localNumber.value,
            postalCode: target.postalCode.value,
            city: target.city.value,
            paymentMethod: "VISA"
          })
        })
      });
    })
    .catch((err) => {
      console.error('Payment Request API error: ', err);
    });
  }

  export default getConfig;