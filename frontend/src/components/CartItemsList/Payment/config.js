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
            'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlJVRkVSamMxT1RsR1JVSkZSamcyUmtWQk5qWXlRekUzTUVVM01EVXpSamsxTVVZMVJqSXpNQSJ9.eyJpc3MiOiJodHRwczovL3Bpay1wcmVkYXRvci5ldS5hdXRoMC5jb20vIiwic3ViIjoiS0lBMU1EaVdSdUM5ZUIydHZFeDlMcEZoZzdsTVIzMFJAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vcGlrLXByZWRhdG9yLmhlcm9rdWFwcC5jb20vIiwiaWF0IjoxNTU5MzM3MDQ0LCJleHAiOjE1NTk0MjM0NDQsImF6cCI6IktJQTFNRGlXUnVDOWVCMnR2RXg5THBGaGc3bE1SMzBSIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.oke0a0dCdhFyTpDRoQgTCDK_F54OI8p2VhzteHjIwxwMnUZDDr9Mo2M-k9NppvyV1paC42G9THywj0k8yPm6g4v5VTfduKOzYdJuxoHKoVz4b6OE-N-ZzlaRV9mC7ASHIiTeK6KPxBry5Vi_NQep6I5crWZ8SCFfSsGQh9JQf7YSYFoCKQxkUn1gFYyoW27equpQZfC4hWMcOlU1mrRwONbM-clHNKkLD0rUJqQQ_IkUHZLUyY2AF3D7b2-PsR9CCYPpwNBLTs0zAObttQ-QjEykxoxQT_emf9jxOZ8y8vTHU7K06PoZrn0SHGuvWqb0a7DApb_xN9TfGJUYTjVAvA',
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