import history from './History';
import auth0 from 'auth0-js';
import { failedLogin, successfulLogin, successfulLogout } from "../redux/actions";
import store from '../index.js';

export default class Auth {
  accessToken;
  idToken;
  expiresAt;
  userProfile;

  auth = new auth0.WebAuth({
    domain: 'pik-predator.eu.auth0.com',
    clientID: 'cCHK0Y5vavf8LS1Yqt2ZX4KgVCzIpMNf',
    redirectUri: 'http://localhost:3000/callback',
    responseType: 'token id_token',
    scope: 'openid profile'
  });

  login = () => {
    this.auth.authorize();
  }

  handleAuthentication = () => {
    this.auth.parseHash((err, authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        this.setSession(authResult);
        store.dispatch(successfulLogin());
      } else if (err) {
        history.replace('/');
        store.dispatch(failedLogin());
        alert(`Error: ${err.error}. Check the console for further details.`);
      }
    });
  }

  getAccessToken = () => {
    return this.accessToken;
  }

  getIdToken = () => {
    return this.idToken;
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
         console.log(err);
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
    console.log("after dispatch: " + localStorage.getItem('state'));
  }


  isAuthenticated = () => {
    // Check whether the current time is past the
    // access token's expiry time
    let expiresAt = this.expiresAt;
    return new Date().getTime() < expiresAt;
  }

  getLogin = () => {
    
    if(this.accessToken){
      this.auth.client.userInfo(this.accessToken, (err, profile) => {
        if(profile){
          this.userProfile = profile;
        }
        console.log(this.userProfile);
      });
    } 

  }
}