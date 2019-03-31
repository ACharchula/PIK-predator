import React, {Component} from 'react';
import './App.scss';
// import axios from 'axios';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Container from 'react-bootstrap/Container'
import Layout from '../Layout/Layout'

import Home from '../Home/Home';
import LoginPage from '../LoginPage/LoginPage';

//fontawesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faBars, faShoppingCart, faUser, faSearch } from '@fortawesome/free-solid-svg-icons'

library.add(faBars, faShoppingCart, faUser, faSearch);

class App extends Component {

    state = {};

    componentDidMount() {
        //setInterval(this.hello, 250);
    }

    // hello = () => {
    //     axios.get('/api/hello')
    //     .then(response => response.data)
    //     .then(message => {
    //     this.setState({message: message});
    //         });
    // };

    render() {
        return (
        <div className="App">
            <BrowserRouter>
            <Layout>
                    <Container>
                        <Switch>
                            <Route path="/" exact component={Home}/>
                            <Route path="/login" exact component={LoginPage}/>
                        </Switch>
                        <p>IT is test</p>
                        
                    </Container>
                </Layout>
            </BrowserRouter>
                
               
            </div>
        );
    }
}

export default App;
