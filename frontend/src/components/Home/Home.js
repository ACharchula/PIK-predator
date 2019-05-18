import React, { Component } from 'react'
import JSON from '../../products.json';
import axios from 'axios';
import Product from '../Product/Product';

import './Home.scss';

class Home extends Component {

    constructor(props) {
        super(props);

        this.state = {
            products: []
        }
    }

    componentDidMount() {
        this.getProducts();
    }

    getProducts = () => {
        axios.get('https://pik-predator.herokuapp.com/catalog/all')
            .then(response => this.setState({products: response.data}));
    };

    renderProducts = () => {
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
                    {this.renderProducts()}
                </div>
            </div>
        )
    }
}


export default Home;
