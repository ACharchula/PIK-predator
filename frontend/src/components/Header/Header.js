import React, {Component} from 'react'
import './Header.scss'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from 'react-bootstrap/FormControl'
import SideNavigation from './SideNavigation/SideNavigation'
import Button from 'react-bootstrap/Button'

import { connect } from 'react-redux';

const linkStyle = {textDecoration:"none", margin:"auto 0", padding:"0"};

export class Header extends Component {

    login = () => {
        if (localStorage.getItem('isLoggedIn') === null) {
            this.props.auth.login();
        }
    };

    logout = () => {
        if (localStorage.getItem('isLoggedIn') === 'true') {
            this.props.auth.logout()
        }
    };

    render() {

        return (
            <header className="App-header">
                <Container className="custom-container">
                    <Row>
                        <Col className="icon">
                            <SideNavigation {...this.props} />
                            <FontAwesomeIcon icon="bars" onClick={this.props.onOpenNav}/>
                        </Col>
                        <Col className="justify-content-center d-flex">
                            <Link to="/" style={linkStyle}>
                                <p className="logo">PREDATOR</p>
                            </Link>
                        </Col>
                        <Col className="justify-content-end d-flex">
                            <InputGroup size="sm" className="searchBar" style={{maxWidth: "250px"}}>
                                <FormControl
                                    style={{backgroundColor: "#191919", borderColor: "#202020"}}
                                    placeholder="What are you searching for ..."
                                    aria-label="Search"
                                    aria-describedby="search-bar"
                                />
                                <InputGroup.Append>
                                    <InputGroup.Text id="search-bar">
                                        <FontAwesomeIcon icon="search"/>
                                    </InputGroup.Text>
                                </InputGroup.Append>
                            </InputGroup>

                            <div className="loginDrop">
                                <div className="loginIcon">
                                    <FontAwesomeIcon icon="user"/>
                                </div>
                                <div className="dropDown">
                                    { this.props.login.login_status !== undefined && this.props.login.login_status === "logged_in" ?
                                        <Button onClick={this.logout} variant="secondary">Log out</Button>:
                                        <Button onClick={this.login} variant="secondary">Log in</Button>
                                    }
                                    { this.props.login.login_status !== undefined && this.props.login.login_status === "logged_in" ?
                                        <Button onClick={this.props.auth.getLogin} variant="secondary">Profile</Button>:
                                        null
                                    }

                                </div>

                            </div>

                            <FontAwesomeIcon icon="shopping-cart" className="icon"/>
                        </Col>
                    </Row>
                </Container>
            </header>
        )
    }
}

const mapStateToProps = (state) => {
    return  {
        login: state.login
    }
}

export default connect( mapStateToProps ) (Header);