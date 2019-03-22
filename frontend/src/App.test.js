import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Adapter from 'enzyme-adapter-react-16';
import { shallow, configure } from 'enzyme';

configure({adapter: new Adapter()});

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

