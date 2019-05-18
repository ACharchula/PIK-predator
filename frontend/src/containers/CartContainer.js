import React from 'react';
import {connect} from 'react-redux';

import CartItemsList from '../components/CartItemsList/CartItemsList';

const CartContainer = ({login, cart}) => {
    return (
        <div>
            <h1>Your Cart</h1>
            <CartItemsList products={cart.products}/>
        </div>
    );
};

function mapStateToProps(state) {
    return {
        login: state.login,
        cart: state.cart
    };
}

export default connect(
    mapStateToProps,
)(CartContainer);

