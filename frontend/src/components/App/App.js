import React, {Component} from 'react';
import './App.scss';
import axios from 'axios';

import Container from 'react-bootstrap/Container'
import Layout from '../Layout/Layout'

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
                <Layout>
                    <Container>
                        <br></br>
                        <br></br>
                        <br></br>
                        <br></br>
                        <br></br>
                        <br></br>
                        <br></br>
                        <br></br>
                        <p>IT is test</p>
                        
                    </Container>
                </Layout>
                
               
            </div>
        );
    }
}

export default App;
