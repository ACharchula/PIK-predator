import React from 'react';
import axios from 'axios/index';
import './UserProfile.scss';
import OrderItem from './OrderItem/OrderItem'

class UserProfile extends React.Component {
    constructor(...args) {
        super(...args);
        this.state = { data: [] };
    }

    componentDidMount() {
        const auth = 'Bearer '.concat(localStorage.getItem('id')); 
        axios.get(`https://pik-predator.herokuapp.com/users/2/orders`, { headers: { Authorization: auth }})
        .then(response => { this.setState({data: response.data})});
    }

      render() {
          const orders = this.state.data.map( (row) => {
              return (
                    <div key={row.orderId}>
                        <div className="flex">
                            <h4>Order ID: {row.orderId}</h4> 
                            <h5>Realization date: {row.date}</h5>
                        </div>
                        {row.products.map( (row) => {return( <OrderItem key={row.productId} product={row}/>)})}
                    </div>
              );
          });

        return (
            <div className="ProfileView">
                {orders}
            </div>
    );}
};

export default UserProfile;