import React, {Component} from 'react';
import './App.css';
import axios from 'axios';

import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'



class App extends Component {

    state = {};

    componentDidMount() {
        setInterval(this.hello, 250);
    }

    hello = () => {
        axios.get('/api/hello')
            .then(response => response.data)
            .then(message => {
                this.setState({message: message});
            });
    };

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <h1 className="App-title">{this.state.message}</h1>
                </header>
                <p>IT is test</p>
                <Container>
                    <Row>
                        <Col>1</Col>
                        <Col>2</Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default App;
