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
        let queryString="";
        if(this.props.filter.filters!==[]) this.props.filter.filters.forEach(function addToString(item,index,array) {
            console.log(item);
            if(item!==null&&item!==undefined&&item.value!=='') {
                let tempString="";
                for (var p in item) {
                    tempString+="=";
                    if (item.hasOwnProperty(p)) {
                        tempString += item[p]
                    }
                }
                if(index===0) queryString='?'+tempString.substr(1);
                else queryString=queryString+'&'+tempString.substr(1);
                console.log(queryString);
            }
        });
        if(queryString!=="") axios.get(`https://pik-predator.herokuapp.com/catalog${queryString}`)
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
