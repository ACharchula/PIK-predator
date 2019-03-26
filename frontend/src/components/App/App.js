import React, {Component} from 'react';
import './App.css';

class App extends Component {

    state = {};

    componentDidMount() {
        setInterval(this.hello, 250);
    }

    hello = () => {
        fetch('/api/hello')
            .then(response => response.text())
            .then(message => {
                this.setState({message: message});
            });
    };

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <h1 className="App-title">{this.state.message}</h1>
                </header>
                <p>IT is test</p>
            </div>
        );
    }
}

export default App;
