import React from 'react';
import Callback from './Callback';
import { shallow } from 'enzyme';
import loading from "./loading.svg";


describe('Callback', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(
            <Callback />
        );
    });

    test('loading', () => {
        expect(
            wrapper.contains(<img src={loading} alt="loading"/>
            )
        ).toBe(true);
    })

})
