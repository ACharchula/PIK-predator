import React from 'react';
import './CartItemsList.scss';
import CartItem from './CartItem/CartItem';
import Button from "react-bootstrap/Button";


const CartItemsList = (props) => {

    const products = props.products.map( (product, index) => {
       return <CartItem key={index} product={product} index={index} removeItem={props.removeItem}/>
    });

    return (
        <div>
            <h1>Your Cart</h1>
            {products}
            { products.length > 0 ?
                <Button size="sm" variant="danger" onClick={props.clearCart}>Clear Cart</Button> :
                null }
        </div>
    );
};

export default CartItemsList;
