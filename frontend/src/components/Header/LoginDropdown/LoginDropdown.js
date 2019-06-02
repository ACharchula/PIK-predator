import React, {Component} from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Button from "react-bootstrap/Button";
import {Link} from "react-router-dom";

import { connect } from 'react-redux';
import './LoginDropdown.scss';


class LoginDropdown extends  Component {



    login = () => {
        if (localStorage.getItem('isLoggedIn') === null) {
            this.props.auth.login();
        }
    };

    logout = () => {
        if (localStorage.getItem('isLoggedIn') === 'true') {
            this.props.auth.logout();
        }
    };

    render() {
        return(
            <div className="loginDrop">
                <div id="loginIcon">
                    <FontAwesomeIcon icon="user"/>
                </div>
                <div className="dropDown">
                    { this.props.login.login_status !== undefined && this.props.login.login_status === "logged_in" ?
                        <Button onClick={this.logout} variant="secondary">Log out</Button>:
                        <Button onClick={this.login} variant="secondary">Log in</Button>
                    }
                    { this.props.login.login_status !== undefined && this.props.login.login_status === "logged_in" ?
                        <Link to="/profile" ><Button variant="secondary">Profile</Button></Link>:
                        null
                    }

                </div>

            </div>);
    }
}

const mapStateToProps = (state) => {
    return  {
        login: state.login
    }
}

export default connect( mapStateToProps ) (LoginDropdown);
