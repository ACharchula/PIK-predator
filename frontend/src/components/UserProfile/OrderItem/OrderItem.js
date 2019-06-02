import React from 'react';
import './OrderItem.scss';
import Image from 'react-bootstrap/Image';


const CartItem = ({product}) => {
    return (
        <div className="cart-item">
            <div className="cart-product-details">
                <div className="product-image" >
                    <Image src={product.imageUrl} fluid/>
                </div>
                <div className="product-details-price">
                    <p>{product.manufacturer} {product.model}</p>
                    <p>Price: {product.price} PLN</p>
                </div>
            </div>
        </div>
    );
};

export default CartItem;