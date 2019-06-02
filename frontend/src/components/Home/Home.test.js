import React from 'react';
import Home from './Home';
import { mount } from 'enzyme';
import {BrowserRouter, MemoryRouter} from 'react-router-dom';
import { Provider } from 'react-redux';
import { library } from '@fortawesome/fontawesome-svg-core'
import { faBars, faShoppingCart, faUser, faSearch } from '@fortawesome/free-solid-svg-icons'
import configureStore from "redux-mock-store";

library.add(faBars, faShoppingCart, faUser, faSearch);



describe('Home', () => {
    const initialState = {
        products:[{
            "manufacturer": "Asus",
            "model": "Vivobook",
            "imageUrl": "https://www.asus.com/websites/global/products/5hcFTTB98JtqhrE6/img/common/response/asus-vivobook.png",
            "processor": "Intel core i5-8250U"
        }],
    filter:{filters:[]}};
    const mockStore = configureStore();
    let wrapper,store;

    beforeEach(() => {
        store=mockStore(initialState);
        wrapper = mount(
            <Provider store={store}>
            <MemoryRouter>
                <Home />
            </MemoryRouter>
            </Provider>);
    });

    test('Products are set properly', () => {
        wrapper.setState(initialState);
        expect(wrapper.length).toBe(1);
        expect(wrapper.state().filter.filters).toEqual([]);
    });

    afterEach(() => {
        wrapper.unmount();
    });

} );

