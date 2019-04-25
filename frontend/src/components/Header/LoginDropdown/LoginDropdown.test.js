import React from 'react';
import LoginDropdown from './LoginDropdown';
import { shallow, mount } from 'enzyme';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import {library} from '@fortawesome/fontawesome-svg-core'
import { faUser } from '@fortawesome/free-solid-svg-icons'

library.add(faUser);


describe('LoginDropdown', () => {
    const initialState = { login:{login_status: "logged_out"}};
    const mockStore = configureStore();
    let store, wrapper;

    beforeEach(() => {
        store = mockStore(initialState);
        wrapper = mount(<Provider store={store}><LoginDropdown /></Provider>);
    });

    it('renders loginDropdown', ()=>{
        expect(wrapper.length).toEqual(1);
    });

    it('renders the icon', ()=> {
        expect(wrapper.find('.loginDrop').length).toEqual(1);
    });


    /*afterEach(() => {
        wrapper.unmount();
    });*/

})
