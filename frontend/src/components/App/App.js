import React, {Component} from 'react';
import './App.scss';
// import axios from 'axios';
import { Router, Route, Switch } from 'react-router-dom';
import Container from 'react-bootstrap/Container'
import Layout from '../Layout/Layout'

import Home from '../Home/Home';
import Callback from'../Callback/Callback';
import history from '../../authentication/History';
import ProductView from '../ProductView/ProductView';

//fontawesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faBars, faShoppingCart, faUser, faSearch } from '@fortawesome/free-solid-svg-icons'

library.add(faBars, faShoppingCart, faUser, faSearch);

class App extends Component {

    state = {};

    componentDidMount() {
        if (localStorage.getItem('isLoggedIn') === 'true') {
          this.props.auth.renewSession();
        }
    }

    handleAuthentication = (nextState, replace) => {
        if (/access_token|id_token|error/.test(nextState.location.hash)) {
          this.props.auth.handleAuthentication();
        }
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
        <div className="App" >
            <Router history={history}>
                <Layout auth={this.props.auth}>
                    <Container>
                        <Switch>
                            <Route path="/" exact component={Home}/>
                            <Route path="/callback" exact render={(props) => {
                                this.handleAuthentication(props);
                                return <Callback {...props} /> 
                                }}/>
                            <Route path="/product/:id" exact component={ProductView}/>
                        </Switch>
                        <p>IT is test</p>
                        
                    </Container>
                </Layout>
            </Router>
        </div>
        );
    }
}

export default App;
