import React, { Component } from 'react'
import axios from 'axios';
import { Link } from 'react-router-dom'

import './ProductView.scss';

class ProductView extends Component {

    state = {
        description: "",//
        price: 0,//
        imageUrl: "",//
        processor: "",//
        processorClock: "",//
        type: "",//
        manufacturer: "",//
        operatingSystem: "",//
        portTypes: [],
        hardDriveType: "",//
        hardDriveSize: 0,//
        graphicCard: "", //
        graphicVRAM: 0, //
        itemDimensions: "", //
        ramType: "",//
        ramSize: 0,//
        weight: 0.0, //
        displayType: "",//
        displayResolution: "",//
        screenSize: "",//
        battery: "",
        camera: "",
        color: "",
        warranty: "",
        quantityInMagazine: 0,
        model: ""
    }

    componentDidMount() {
        const id = this.props.match.params.id;
        this.getProductInfo(id)
    }

    getProductInfo(id) {
        axios.get(`https://pik-predator.herokuapp.com/catalog/${id}`)
        .then(response => {
            const descriptionResult = response.data.description;
            const priceResult = response.data.price;
            const imageUrlResult = response.data.imageUrl;
            const processorResult = response.data.processor;
            const processorClockResult = response.data.processorClock;
            const typeResult = response.data.type;
            const manufacturerResult = response.data.manufacturer;
            const operatingSystemResult = response.data.operatingSystem;
            const portTypesResult = response.data.portTypes;
            const hardDriveTypeResult = response.data.hardDriveType;
            const hardDriveSizeResult = response.data.hardDriveSize;
            const graphicCardResult = response.data.graphicCard;
            const graphicVRAMResult = response.data.graphicVRAM;
            const itemDimensionsResult = response.data.itemDimensions;
            const ramTypeResult = response.data.ramType;
            const ramSizeResult = response.data.ramSize;
            const weightResult = response.data.weight;
            const displayTypeResult = response.data.displayType;
            const displayResolutionResult = response.data.displayResolution;
            const screenSizeResult = response.data.screenSize;
            const batteryResult = response.data.battery;
            const cameraResult = response.data.camera;
            const colorResult = response.data.color;
            const warrantyResult = response.data.warranty;
            const quantityInMagazineResult = response.data.quantityInMagazine;
            const modelResult = response.data.model;

            this.setState({
                description: descriptionResult,
                price: priceResult,
                imageUrl: imageUrlResult,
                processor: processorResult,
                processorClock: processorClockResult,
                type: typeResult,
                manufacturer: manufacturerResult,
                operatingSystem: operatingSystemResult,
                portTypes: portTypesResult,
                hardDriveType: hardDriveTypeResult,
                hardDriveSize: hardDriveSizeResult,
                graphicCard: graphicCardResult,
                graphicVRAM: graphicVRAMResult,
                itemDimensions: itemDimensionsResult,
                ramType: ramTypeResult,
                ramSize: ramSizeResult,
                weight: weightResult,
                displayType: displayTypeResult,
                displayResolution: displayResolutionResult,
                screenSize: screenSizeResult,
                battery: batteryResult,
                camera: cameraResult,
                color: colorResult,
                warranty: warrantyResult,
                quantityInMagazine: quantityInMagazineResult,
                model: modelResult
            })
        })
    }

    render() {
        return (
            <div className="ProductView">
                <div className="left-column">
                    <img src={this.state.imageUrl} alt=""></img>
                </div>
                <div className="right-column">
                    <div className="product-description">
                        <h1>{this.state.manufacturer}</h1>
                        <h2>{this.state.model}</h2>
                        <p>{this.state.description}</p>
                    </div>
                    <div className="product-price">
                        <span>{this.state.price} PLN</span>
                        <Link to="#" className="cart-btn">Add to cart</Link>
                    </div>
                </div>

                <table className="table table-hover specs">
                <tbody>
                    <tr>
                        <td>Product type: </td>
                        <td>{this.state.type}</td>
                    </tr>
                    <tr>
                        <td>Processor: </td>
                        <td>{this.state.processorClock}</td>
                    </tr>
                    <tr>
                        <td>Proccessor clock: </td>
                        <td>{this.state.type}</td>
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
                        <td>Warriany: </td>
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

export default ProductView;
