import React , { Component } from 'react';
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

// import './layout.scss';
class Layout extends Component {

    state = {
        showNav: false,
    };

    toggleSideNav = (status) => {
        this.setState({
            showNav: status
        })
    };


    render() {
        return(
            <div >
                <Header auth={this.props.auth}
                    showNav={this.state.showNav}
                    onHideNav={()=>this.toggleSideNav(false)}
                    onOpenNav={()=>this.toggleSideNav(true)}
                />
                <main>
                    {this.props.children}
                </main>
                <Footer/>
            </div>
        );
    }
}

export default Layout;