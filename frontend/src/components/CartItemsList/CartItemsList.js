import React from 'react';
import './CartItemsList.scss';
import CartItem from './CartItem/CartItem';
import Button from "react-bootstrap/Button";


const CartItemsList = (props) => {

    let total = 0;
    const products = props.products.map( (product, index) => {
       total += product.price;
       return <CartItem key={index} product={product} index={index} removeItem={props.removeItem}/>
    });

    return (
        <div className="products-wrapper">
            <div className="cart-header">
                <p>Your Cart</p>
                { products.length > 0 ?
                    <Button className="clear-button" size="sm" variant="danger" onClick={props.clearCart}>Clear Cart</Button> :
                    null }
                <p>Total: {total} PLN</p>
            </div>
            {products}
        </div>
    );
};

export default CartItemsList;
