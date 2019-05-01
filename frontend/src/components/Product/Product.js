import React from 'react'
import './Product.scss';
import Image from 'react-bootstrap/Image';

export const Product = (props) => {
    return (
        <div className="product-tile">
            <div className="img-wrapper">
                <Image src={props.product.imageUrl} fluid />
                <p>

                </p>
            </div>

            <div className="product-details">
                <p>

                </p>
                <h4>
                    {props.product.producer} {props.product.model}
                </h4>
                <p>
                    Price: {props.product.price} zl
                </p>
            </div>

        </div>
    )
}

export default Product