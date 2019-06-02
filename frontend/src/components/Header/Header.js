import React from 'react'
import './Header.scss'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {Link} from 'react-router-dom';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import SideNavigation from './SideNavigation/SideNavigation'
import LoginDropdown from './LoginDropdown/LoginDropdown';
import CartIcon from './CartIcon/CartIcon';
import SearchBar from './SearchBar/SearchBar'

export const linkStyle = {textDecoration: "none", margin: "auto 0", padding: "0"};


export function Header(props) {
    return (
        <header className="App-header">
            <Container style={{marginTop: "0"}}>
                <Row>
                    <Col className="icon">
                        <SideNavigation {...props} />
                        <FontAwesomeIcon icon="bars" onClick={props.onOpenNav}/>
                    </Col>
                    <Col className="justify-content-center d-flex">
                        <Link to="/" style={linkStyle}>
                            <p className="logo">PREDATOR</p>
                        </Link>
                    </Col>
                    <Col className="justify-content-end d-flex">
                        <SearchBar  {...props} />
                        <LoginDropdown id="login-dropdown" auth={props.auth}/>
                        <CartIcon/>
                    </Col>
                </Row>
            </Container>
        </header>
    )
}

export default Header;