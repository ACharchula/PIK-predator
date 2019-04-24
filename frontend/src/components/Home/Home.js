import React, { Component } from 'react'
import JSON from '../../products.json';
import Product from '../Product/Product';

import './Home.scss';

class Home extends Component {

    constructor(props) {
        super(props);

        this.state = {
            products: JSON
        }
    }

    getProducts = () => {
        return this.state.products.map((product, i) => {
            return (
                    <Product product={product} key={i}  />
            )
        })
    };


    render() {
        return (
            <div >
                <div className="Products">
                    {this.getProducts()}
                </div>
            </div>
        )
    }
}


export default Home;