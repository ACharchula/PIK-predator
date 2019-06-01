import React, { Component } from 'react'
import axios from 'axios';
import Product from '../Product/Product';

import './Home.scss';
import {connect} from "react-redux";

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

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.filter !== this.props.filter) {
            this.filterProducts();
        }
    }

    filterProducts = () => {
        let tempString="";
        if(this.props.filter.filters!==[]) this.props.filter.filters.forEach(function addToString(item,index,array) {
            console.log(item);
            if(item!==null&&item!==undefined&&item.value!=='') {
                for (var p in item) {
                    tempString+="=";
                    if (item.hasOwnProperty(p)) {
                        tempString += item[p]
                    }
                }
                tempString='?'+tempString.substr(1);
                console.log(tempString);
            }
        });
        if(tempString!=="") axios.get(`https://pik-predator.herokuapp.com/catalog${tempString}`)
            .then(response => this.setState({products: response.data}));
        else this.getProducts();
        console.log(this.products);
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


//export default Home;

const mapStateToProps = (state) => {
    return {
        filter: state.filter,
    }
}

export default connect(mapStateToProps)(Home);
