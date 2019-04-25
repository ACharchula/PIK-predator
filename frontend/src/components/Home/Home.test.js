import React from 'react';
import Home from './Home';
import { mount } from 'enzyme';

import { library } from '@fortawesome/fontawesome-svg-core'
import { faBars, faShoppingCart, faUser, faSearch } from '@fortawesome/free-solid-svg-icons'

library.add(faBars, faShoppingCart, faUser, faSearch);



describe('Home', () => {
    const initialState = { products:[{
            "name": "Asus Vivobook",
            "url": "https://www.asus.com/websites/global/products/5hcFTTB98JtqhrE6/img/common/response/asus-vivobook.png",
            "cpu": "Intel core i5-8250U"
    }]};
    let wrapper;

    beforeEach(() => {
        wrapper = mount(<Home />);
    });

    test('Products are set properly', () => {
        wrapper.setState(initialState);
        expect(wrapper.length).toBe(1);
        expect(wrapper.contains(initialState)).toBe(true);
    });

    afterEach(() => {
        wrapper.unmount();
    });

} );

