import React from 'react';
import './CartItem.scss';
import '../CartItemsList.scss';
import Image from 'react-bootstrap/Image';
import Button from "react-bootstrap/Button";


const CartItem = ({product, index, removeItem}) => {
    return (
        <div className="cart-item">
            <div className="cart-product-details">
                <div className="product-image" >
                    <Image src={product.imageUrl} fluid/>
                </div>
                <div className="product-details-price">
                    <p>{product.model} {product.processor}/{product.ramSize}/{product.hardDriveSize}/{product.operatingSystem}</p>
                    <p>Price: {product.price} PLN</p>
                </div>
            </div>
            <Button className="clear-button remove-button" size="sm" variant="danger" onClick={()=>removeItem(index,product)}>X</Button>
        </div>
    );
};

export default CartItem;
