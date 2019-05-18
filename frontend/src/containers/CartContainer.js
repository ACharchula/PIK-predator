import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from "redux";
import { removeProductFromCart, clearCart } from "../redux/actions";

import CartItemsList from '../components/CartItemsList/CartItemsList';

const CartContainer = (props) => {
    return (
        <div>
            <CartItemsList products={props.cart.products} removeItem={props.removeProductFromCart} clearCart={props.clearCart}/>
        </div>
    );
};

const mapStateToProps = (state) => {
    return {
        login: state.login,
        cart: state.cart
    };
};

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators({ removeProductFromCart, clearCart }, dispatch);
}

export default connect(
    mapStateToProps, mapDispatchToProps
)(CartContainer);

