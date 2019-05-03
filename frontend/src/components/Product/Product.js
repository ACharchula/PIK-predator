import React from 'react'
import './Product.scss';
import Image from 'react-bootstrap/Image';
import { Link } from 'react-router-dom'

export const Product = (props) => {
    return (
        <Link to={{pathname: '/product/'.concat(props.product.productId)}}>
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
        </Link>
    )
}

export default Product