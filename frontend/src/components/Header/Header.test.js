import React from 'react';
import Header from './Header';
import { shallow,mount } from 'enzyme';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';

import { library } from '@fortawesome/fontawesome-svg-core'
import { faBars, faShoppingCart, faUser, faSearch } from '@fortawesome/free-solid-svg-icons'

library.add(faBars, faShoppingCart, faUser, faSearch);

describe('Header', () => {
    //let wrapper;
    const initialState = { login:{login_status: "logged_out"}};
    const mockStore = configureStore();
    let store, wrapper;

    beforeEach(() => {
        store = mockStore(initialState);
        wrapper = mount( <Provider store={store}><BrowserRouter><Header /></BrowserRouter></Provider>);
    });

    it('renders header', ()=>{
       expect(wrapper.length).toEqual(1);
    });

    it('renders loginDropdown', () => {
        // console.log(wrapper.debug());
        expect(wrapper.find('.loginDrop').length).toEqual(1);
    })


})
