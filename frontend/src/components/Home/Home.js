import React, { Component } from 'react'
import JSON from '../../products.json';
import axios from 'axios';
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
        axios.get('http://localhost:8080/catalog/all')
            .then(response=> {
                if (response.data.message) {
                    console.log(
                        `Got ${Object.entries(response.data.message).length} breeds`
                    )
                }
                console.log(response.data)
                const notebooks = response.data;
                console.log(notebooks)
                this.setState({products: notebooks});

                }

            )
        axios.get("http://localhost:8080/api/hello")
            .then(response =>{
                console.log(
                    response.data
                )
            })
        console.log(this.state.products)
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
