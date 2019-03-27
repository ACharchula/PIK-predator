import React , { Component } from 'react';
import Header from '../../components/Header/Header';
// import './layout.scss';
class Layout extends Component {

    state = {
        showNav: false,
    }

    toggleSideNav = (status) => {
        this.setState({
            showNav: status
        })
    }


    render() {
        return(
            <div>
                <Header
                    showNav={this.state.showNav}
                    onHideNav={()=>this.toggleSideNav(false)}
                    onOpenNav={()=>this.toggleSideNav(true)}
                />
                {this.props.children}
            </div>
        );
    }
}

export default Layout;