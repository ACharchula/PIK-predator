import React from 'react'
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


const linkStyle = {textDecoration:"none", margin:"auto 0", padding:"0"};

export const Header = (props) => {

    function authenticate() {
        if (localStorage.getItem('isLoggedIn') === null) {
            props.auth.login()
        } else if (localStorage.getItem('isLoggedIn') === 'true') {
            props.auth.logout()
        }
    }

    const logout = () => {
        if(localStorage.getItem('isLoggedIn') === 'true') {
            props.auth.logout()
        }
    }

    return(
        <header className="App-header">
            <Container className="custom-container">
                <Row>
                    <Col className="icon">
                        <SideNavigation {...props} />
                        <FontAwesomeIcon icon="bars" onClick={props.onOpenNav} />
                    </Col>
                    <Col className="justify-content-center d-flex">
                        <Link to="/" style={linkStyle} >
                            <p className="logo">PREDATOR</p>
                        </Link>
                    </Col>
                    <Col className="justify-content-end d-flex">
                        <InputGroup size="sm" className="searchBar" style={{ maxWidth: "250px" }}>
                            <FormControl
                                style={{ backgroundColor: "#191919", borderColor: "#202020" }}
                                placeholder="What are you searching for ..."
                                aria-label="Search"
                                aria-describedby="search-bar"
                            />
                            <InputGroup.Append>
                                <InputGroup.Text id="search-bar">
                                    <FontAwesomeIcon icon="search" />
                                </InputGroup.Text>
                            </InputGroup.Append>
                        </InputGroup>

                        <div className="loginDrop">
                            <div className="loginIcon">
                                <Link to="/login" style={linkStyle}>
                                    <FontAwesomeIcon icon="user" />
                                </Link>
                            </div>
                            <div className="dropDown">
                                <Button onClick={authenticate} variant="secondary">Log in</Button>
                                <Button onClick={logout} variant="secondary">Log out</Button>
                                <Button onClick={props.auth.getLogin} variant="secondary">Profile</Button>
                            </div>

                        </div>

                        <FontAwesomeIcon icon="shopping-cart" className="icon" />
                    </Col>
                </Row>
            </Container>
        </header>
    )
}

export default Header