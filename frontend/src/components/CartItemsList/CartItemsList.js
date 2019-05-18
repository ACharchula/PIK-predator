import React from 'react';
import './CartItemsList.scss';
import CartItem from './CartItem/CartItem';


const CartItemsList = (props) => {

    const products = props.products.map( (product, index) => {
       return <CartItem key={index} product={product}/>
    });

    return (
        <div>
            {products}
        </div>
    );
};

export default CartItemsList;
