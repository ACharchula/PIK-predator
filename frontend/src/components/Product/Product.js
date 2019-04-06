import React from 'react'
import './Product.scss';
import Image from 'react-bootstrap/Image';

export const Product = (props) => {
    return (
            <div className="product-tile">
                <div className="img-wrapper">
                    <Image src={props.product.url} fluid />
                </div>
                <div className="product-details">
                    <h4>
                        {props.product.name}
                    </h4>
                    <p>
                        CPU: {props.product.cpu}
                    </p>
                </div>
                
            </div>
    )
}

export default Product