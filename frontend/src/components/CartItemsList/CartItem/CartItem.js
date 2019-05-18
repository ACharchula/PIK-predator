import React from 'react';
import './CartItem.scss';
import Button from "react-bootstrap/Button";


const CartItem = ({product, index, removeItem}) => {
    return (
        <div className="cart-item">
            <h1>{product.model}</h1>
            <Button size="sm" variant="danger" onClick={()=>removeItem(index)}>Remove product</Button>
        </div>
    );
};

export default CartItem;
