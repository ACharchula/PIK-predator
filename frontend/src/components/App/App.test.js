import React from 'react';
import App from './App';
import { shallow } from 'enzyme';
import {Route, Switch} from "react-router";
import LoginPage from "../LoginPage/LoginPage";


describe('App', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = shallow(
      <App />
    );
  });

  test('should be right name', () => {
    expect(
      wrapper.contains(<p>IT is test</p>)
    ).toBe(true);
  })

})

