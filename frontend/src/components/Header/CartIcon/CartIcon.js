import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Link} from "react-router-dom";
import { linkStyle } from '../Header';
import {connect} from "react-redux";
import './CartIcon.scss';

const CartIcon = ({cart}) => {
    return (
        <div className="cart-icon">
            <Link to="/cart" style={linkStyle}>
                {cart !== undefined &&cart.products.length > 0 ?
                    <div className="cart-counter">
                         <p>{cart.products.length}</p>
                    </div>
                    :null }
                <FontAwesomeIcon icon="shopping-cart" className="icon"/>
            </Link>
        </div>
    );
};

const mapStateToProps = (state) => {
    return  {
        cart: state.cart
    }
}

export default connect( mapStateToProps ) (CartIcon);
