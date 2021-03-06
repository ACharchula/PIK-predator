import React from 'react';
import ReactDOM from 'react-dom';
import './index.scss';
import App from './components/App/App';
import * as serviceWorker from './serviceWorker';


//redux things
import { createStore, applyMiddleware, compose } from 'redux'
import { Provider } from 'react-redux'
import promiseMiddleware from 'redux-promise'
import thunk from 'redux-thunk'
import rootReducer from './redux/reducers'
import Auth from './authentication/Auth';

import { loadState, saveState } from './localStorage';

const auth = new Auth();

const persistedState = loadState();

const store = createStore(
    rootReducer,
    persistedState,
    compose(
        applyMiddleware(thunk, promiseMiddleware ),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    )
)

store.subscribe(()=> {
    saveState({
        login: store.getState().login,
        cart: store.getState().cart,
        filter: store.getState().filter
    });
});



ReactDOM.render(
    <Provider store={store}>
            <App auth={auth}/>
    </Provider>
    , document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

export default store;