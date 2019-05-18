import React from 'react';
import './CartItem.scss';

const CartItem = ({product}) => {
    return (
        <div>
            <h1>{product.model}</h1>
        </div>
    );
};

export default CartItem;
