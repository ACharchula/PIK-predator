import React, {Component} from 'react';
import './App.scss';
import axios from 'axios';

import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Header from '../Header/Header'

//fontawesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faBars, faShoppingCart, faUser, faSearch } from '@fortawesome/free-solid-svg-icons'

library.add(faBars, faShoppingCart, faUser, faSearch);

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
                <Header>
                    <h1 className="App-title">{this.state.message}</h1>
                </Header>
                
                <Container>
                    <p>IT is test</p>
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
