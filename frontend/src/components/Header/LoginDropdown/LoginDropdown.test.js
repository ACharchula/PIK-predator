import React from 'react';
import LoginDropdown from './LoginDropdown';
import { mount } from 'enzyme';
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

    it('renders log in button when logged out', () => {
        if(store.getState().login.login_status === "logged_out") {
            expect(wrapper.findWhere(x => x.text() === "Log in").find('button').length).toEqual(1);
        }
    });

});
