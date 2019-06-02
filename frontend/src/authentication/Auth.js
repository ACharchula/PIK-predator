import history from './History';
import auth0 from 'auth0-js';
import {clearCart, failedLogin, successfulLogin, successfulLogout, setCart} from "../redux/actions";
import store from '../index.js';
import axios from "axios";

export default class Auth {
    accessToken;
    idToken;
    expiresAt;
    userProfile;
    user_id;

    auth = new auth0.WebAuth({
        domain: 'pik-predator.eu.auth0.com',
        clientID: 'cCHK0Y5vavf8LS1Yqt2ZX4KgVCzIpMNf',
        redirectUri: (process.env.NODE_ENV === 'production') ? 'https://pik-predator.herokuapp.com/callback' : 'http://localhost:3000/callback',
        responseType: 'token id_token',
        scope: 'openid profile',
        audience: 'https://pik-predator.herokuapp.com/'
    });

    login = () => {
        this.auth.authorize();
    }

    handleAuthentication = () => {
        this.auth.parseHash((err, authResult) => {
            if (authResult && authResult.accessToken && authResult.idToken) {
                this.setSession(authResult);
                store.dispatch(successfulLogin());
                localStorage.setItem('id', this.getAccessToken());
                this.getUserId();
                if (store.getState().cart && store.getState().cart.products) {
                    const author = 'Bearer '.concat(localStorage.getItem('id'));

                    if(store.getState().cart.products.length > 0){
                        const productIds = store.getState().cart.products.map(product => {
                            return parseInt(product.productId);
                        });
                        axios({
                            method:'post',
                            url: 'https://pik-predator.herokuapp.com/users/'+localStorage.getItem("userId")+'/cart',
                            headers:{
                                'Content-Type': 'application/json',
                                'Authorization': author,
                            },
                            data: productIds
                        }).then(()=>this.getProducts(author));
                    }else {
                        this.getProducts(author);
                    }

                }

            } else if (err) {
                history.replace('/');
                store.dispatch(failedLogin());
                alert(`Error: ${err.error}. Check the console for further details.`);
            }
        });
    }

    getProducts = (author) => {
        axios({
            method: 'get',
            url: 'https://pik-predator.herokuapp.com/users/'+localStorage.getItem("userId")+'/cart',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': author,
            },
        }).then(response => {

            const previousCart = response.data;
            store.dispatch(setCart(previousCart));
        }).catch( error => {
        });
    };

    getAccessToken = () => {
        return this.accessToken;
    }

    getIdToken = () => {
        return this.idToken;
    }

    getUserId = () => {
        return this.user_id;
    }

    setSession = (authResult) => {
        // Set isLoggedIn flag in localStorage
        localStorage.setItem('isLoggedIn', 'true');

        // Set the time that the access token will expire at
        let expiresAt = (authResult.expiresIn * 1000) + new Date().getTime();
        this.accessToken = authResult.accessToken;
        this.idToken = authResult.idToken;
        this.expiresAt = expiresAt;
        // navigate to the home route
        history.replace('/');
    }

    renewSession = () => {
        this.auth.checkSession({}, (err, authResult) => {
            if (authResult && authResult.accessToken && authResult.idToken) {
                this.setSession(authResult);
            } else if (err) {
                this.logout();
                alert(`Could not get a new token (${err.error}: ${err.error_description}).`);
            }
        });
    }

    logout = () => {
        // Remove tokens and expiry time
        this.accessToken = null;
        this.idToken = null;
        this.expiresAt = 0;

        // Remove isLoggedIn flag from localStorage
        localStorage.removeItem('isLoggedIn');

        this.auth.logout({
            return_to: window.location.origin
        });

        // navigate to the home route
        history.replace('/');
        store.dispatch(successfulLogout());
        store.dispatch(clearCart());
    };


    isAuthenticated = () => {
        // Check whether the current time is past the
        // access token's expiry time
        let expiresAt = this.expiresAt;
        return new Date().getTime() < expiresAt;
    }

    getUserId = () => {

        if (this.accessToken) {
            this.auth.client.userInfo(this.accessToken, (err, profile) => {
                if (profile) {
                    this.userProfile = profile;
                    localStorage.setItem('userId', profile.sub)
                }
            });
        }

    }
}