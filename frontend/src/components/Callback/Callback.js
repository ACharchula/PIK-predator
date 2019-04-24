import React, { Component } from 'react';
import loading from './loading.svg';
import './Callback.scss';

class Callback extends Component {
  render() {

    return (
      <div className="callback">
        <img src={loading} alt="loading"/>
      </div>
    );
  }
}

export default Callback;