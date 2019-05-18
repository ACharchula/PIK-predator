import React, { Component } from 'react'
import axios from 'axios/index';

//redux
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { addToCart } from "../../../redux/actions";

//bootstrap
import Button from "react-bootstrap/Button";

import './ProductView.scss';

class ProductView extends Component {

    constructor(props) {
        super(props);
        this.state = {
            description: "",
            price: 0,
            imageUrl: "",
            processor: "",
            processorClock: "",
            type: "",
            manufacturer: "",
            operatingSystem: "",
            portTypes: [],
            hardDriveType: "",
            hardDriveSize: 0,
            graphicCard: "",
            graphicVRAM: 0,
            itemDimensions: "",
            ramType: "",
            ramSize: 0,
            weight: 0.0,
            displayType: "",
            displayResolution: "",
            screenSize: "",
            battery: "",
            camera: "",
            color: "",
            warranty: "",
            quantityInMagazine: 0,
            model: ""
        };

    }


    componentDidMount() {
        const id = this.props.match.params.id;
        this.getProductInfo(id)
    }

    getProductInfo(id) {
        axios.get(`https://pik-predator.herokuapp.com/catalog/${id}`)
        .then(response => {

            this.setState({
                description: response.data.description,
                price: response.data.price,
                imageUrl: response.data.imageUrl,
                processor: response.data.processor,
                processorClock: response.data.processorClock,
                type: response.data.type,
                manufacturer: response.data.manufacturer,
                operatingSystem: response.data.operatingSystem,
                portTypes: response.data.portTypes,
                hardDriveType: response.data.hardDriveType,
                hardDriveSize: response.data.hardDriveSize,
                graphicCard: response.data.graphicCard,
                graphicVRAM: response.data.graphicVRAM,
                itemDimensions: response.data.itemDimensions,
                ramType: response.data.ramType,
                ramSize: response.data.ramSize,
                weight: response.data.weight,
                displayType: response.data.displayType,
                displayResolution: response.data.displayResolution,
                screenSize: response.data.screenSize,
                battery: response.data.battery,
                camera: response.data.camera,
                color: response.data.color,
                warranty: response.data.warranty,
                quantityInMagazine: response.data.quantityInMagazine,
                model: response.data.model
            })
        })
    }

    addProductToCart = () => {
        this.props.addToCart(this.state);
    };

    render() {
        return (
            <div className="ProductView">
                <div className="left-column">
                    <img src={this.state.imageUrl} alt={"Image of " + this.state.model}/>
                </div>
                <div className="right-column">
                    <div className="product-description">
                        <h1>{this.state.manufacturer}</h1>
                        <h2>{this.state.model}</h2>
                        <p>{this.state.description}</p>
                    </div>
                    <div className="product-price">
                        <span>{this.state.price} PLN</span>
                        <Button className="cart-btn" variant={"danger"} onClick={this.addProductToCart}>Add to cart</Button>
                    </div>
                </div>

                <table className="table table-hover specs">
                <tbody>
                    <tr>
                        <td>Product type: </td>
                        <td>{this.state.type}</td>
                    </tr>
                    <tr>
                        <td>CPU: </td>
                        <td>{this.state.processor}</td>
                    </tr>
                    <tr>
                        <td>CPU clock: </td>
                        <td>{this.state.processorClock}</td>
                    </tr>
                    <tr>
                        <td>Operating system: </td>
                        <td>{this.state.operatingSystem}</td>
                    </tr>
                    <tr>
                        <td>Hard drive type: </td>
                        <td>{this.state.hardDriveType}</td>
                    </tr>
                    <tr>
                        <td>Hard drive size: </td>
                        <td>{this.state.hardDriveSize}</td>
                    </tr>
                    <tr>
                        <td>Graphic card:</td>
                        <td>{this.state.graphicCard}</td>
                    </tr>
                    <tr>
                        <td>Graphic card VRAM:</td>
                        <td>{this.state.graphicVRAM} GB</td>
                    </tr>
                    <tr>
                        <td>Item dimensions: </td>
                        <td>{this.state.itemDimensions} mm</td>
                    </tr>
                    <tr>
                        <td>Ram: </td>
                        <td>{this.state.ramType}</td>
                    </tr>
                    <tr>
                        <td>Ram size: </td>
                        <td>{this.state.ramSize} GB</td>
                    </tr>
                    <tr>
                        <td>Weight:  </td>
                        <td>{this.state.weight} kg</td>
                    </tr>
                    <tr>
                        <td>Display: </td>
                        <td>{this.state.displayType}</td>
                    </tr>
                    <tr>
                        <td>Resolution: </td>
                        <td>{this.state.displayResolution}</td>
                    </tr>
                    <tr>
                        <td>Screen size: </td>
                        <td>{this.state.screenSize}"</td>
                    </tr>
                    <tr>
                        <td>Battery: </td>
                        <td>{this.state.battery}</td>
                    </tr>
                    <tr>
                        <td>Camera: </td>
                        <td>{this.state.camera}</td>
                    </tr>
                    <tr>
                        <td>Color: </td>
                        <td>{this.state.color}</td>
                    </tr>
                    <tr>
                        <td>Warranty: </td>
                        <td>{this.state.warranty}</td>
                    </tr>
                    <tr>
                        <td>Quantity in magazine: </td>
                        <td>{this.state.quantityInMagazine}</td>
                    </tr>
                </tbody>
                </table>
            </div>
        )
    }
}
//PORTS HAS TO BE ADDED

const mapStateToProps = (state) => {
    return {
        login: state.login,
        cart: state.cart
    }
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators({ addToCart }, dispatch);
}


export default connect(mapStateToProps, mapDispatchToProps)(ProductView);
