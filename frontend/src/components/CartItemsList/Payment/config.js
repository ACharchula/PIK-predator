import {clearCart} from "../../../redux/actions";
import store from '../../../index';

const supportedPaymentMethods = [
    {
      supportedMethods: 'basic-card',
    }
  ];
  // Options isn't required.
  const options = {};

  const getConfig = (target, total) => {
    const paymentDetails = {
      total: {
        label: 'Total',
        amount:{
          currency: 'PLN',
          value: total
        }
      }
    };
    const auth = 'Bearer '.concat(localStorage.getItem('id')); 
    const paymentRequest = new PaymentRequest(
        supportedPaymentMethods, paymentDetails, options);
    paymentRequest.show()
    .then((paymentResponse) => {
      store.dispatch(clearCart());

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
        }).then(response => {
          fetch(`https://pik-predator.herokuapp.com/orders/${response.headers.get('Location')}`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'Authorization': auth,
            }
          });
        })
      });
    })
    .catch((err) => {
      console.error('Payment Request API error: ', err);
    });
  }

  export default getConfig;