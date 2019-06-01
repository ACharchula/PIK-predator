import React, {Component} from 'react';
import './CartItemsList.scss';
import CartItem from './CartItem/CartItem';
import Button from "react-bootstrap/Button";
import PaymentDialog from './Payment/PaymentDialog';
import ButtonToolbar from 'react-bootstrap/ButtonToolbar';

class CartItemsList extends React.Component {
    constructor(...args) {
        super(...args);
        this.state = { modalShow: false };
    }
    
      render() {
        let modalClose = () => this.setState({ modalShow: false });
        
        let total = 0;
        const products = this.props.products.map( (product, index) => {
           total += product.price;
           return <CartItem key={index} product={product} index={index} removeItem={this.props.removeItem}/>
        });

        return (
            <div className="products-wrapper">
                <div className="cart-header">
                    <p>Your Cart</p>
                    { products.length > 0 ?
                        <Button className="clear-button" size="sm" variant="danger" onClick={this.props.clearCart}>Clear Cart</Button> :
                        null }
                    <p>Total: {total} PLN</p>
                    { products.length > 0 ?
                        <ButtonToolbar>
                            <Button className="clear-button" size="sm"variant="danger"onClick={() => this.setState({ modalShow: true })}>Pay</Button>
                            <PaymentDialog show={this.state.modalShow} onHide={modalClose}/>
                        </ButtonToolbar> :
                    null }
                </div>
                {products}
            </div>
    );}
};

export default CartItemsList;
