import React from 'react';
import LoginDropdown from './LoginDropdown';
import { shallow, mount } from 'enzyme';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';

import {library} from '@fortawesome/fontawesome-svg-core'
import { faUser } from '@fortawesome/free-solid-svg-icons'

library.add(faUser);

class mockedAuth{
    getLogin = () => {};
    login = () => {};
    logout = () => {};
}


describe('LoginDropdown', () => {
    const initialState = { login:{login_status: "logged_out"}};
    const mockStore = configureStore();
    let store, wrapper;
    const mAuth = new mockedAuth();

    beforeEach(() => {
        store = mockStore(initialState);
        wrapper = mount(<Provider store={store}><LoginDropdown auth={mAuth} /></Provider>);
    });

    afterEach(()=>{
        wrapper.unmount();
    })

    it('renders loginDropdown', ()=>{
        expect(wrapper.length).toEqual(1);
    });

    it('renders the icon', ()=> {
        expect(wrapper.find('.loginDrop').length).toEqual(1);
    });

    it('Finds button login', ()=> {
        expect(wrapper.findWhere(x => x.text() === "Log in").find('button').length).toEqual(1);
        expect(wrapper.find("button").length).toEqual(1);
    });

    it('Changes state to logged_in, finds logout button', ()=> {
        wrapper.unmount();
        store = mockStore({login:{login_status: "logged_in"}});
        wrapper = mount(<Provider store={store}><LoginDropdown LoginDropdown auth={mAuth}/></Provider>);
        expect(wrapper.find("button").length).toEqual(2);
        expect(wrapper.findWhere(x => x.text() === "Log out").find('button').length).toEqual(1);
    });

    it('renders log in button when logged out', () =>
    {
        if(store.getState().login.login_status === "logged_out")
        { expect(wrapper.findWhere(x => x.text() === "Log in").find('button').length).toEqual(1); }
    });



})
