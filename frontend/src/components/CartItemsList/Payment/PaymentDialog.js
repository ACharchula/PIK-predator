import React, {Component} from 'react';
import Button from "react-bootstrap/Button";
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import getConfig from './config'

class PaymentDialog extends React.Component {

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.onHide();
        console.log(this.props)
        getConfig(event.target);
    }

    render() {
        return (
            <Modal
                {...this.props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Payment
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={e => this.handleSubmit(e)}>
                        <Form.Label>First name</Form.Label>
                        <Form.Control name="firstName" type="text" placeholder="Enter first name" /> <br/>
                        <Form.Label>Last name</Form.Label>
                        <Form.Control name="lastName" type="text" placeholder="Enter last name" /> <br/>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control name="email" type="email" placeholder="Enter email" />
                        </Form.Group>
                        <Form.Label>Street</Form.Label>
                        <Form.Control name="street" type="text" placeholder="Enter street" /> <br/>
                        <Form.Label>House number</Form.Label>
                        <Form.Control name="houseNumber" type="text" placeholder="Enter house number" /> <br/>
                        <Form.Label>Local number</Form.Label>
                        <Form.Control name="localNumber" type="text" placeholder="Enter local number" /> <br/>
                        <Form.Label>Postal code</Form.Label>
                        <Form.Control name="postalCode" type="text" placeholder="Enter postal code" /> <br/>
                        <Form.Label>City</Form.Label>
                        <Form.Control name="city" type="text" placeholder="Enter city" /> <br/>
                        <Button type="submit" variant="primary" >Pay</Button>
                        <Button variant="secondary" onClick={this.props.onHide}>Close</Button>
                    </Form>
                </Modal.Body>
            </Modal>
        );
    }
}

export default PaymentDialog;