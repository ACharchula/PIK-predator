import React from 'react'
import './Header.scss'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from 'react-bootstrap/FormControl'
import SideNavigation from './SideNavigation/SideNavigation'

export const Header = (props) => {

    return(
        <div className="App-header">

            

            <Container className="custom-container">
                <Row>
                    <Col className="icon">
                        <SideNavigation {...props} />
                        <FontAwesomeIcon icon="bars" onClick={props.onOpenNav} />
                    </Col>
                    <Col className="justify-content-center d-flex">
                        <p className="logo">PREDATOR</p>
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
                        <FontAwesomeIcon icon="user" className="icon" />
                        <FontAwesomeIcon icon="shopping-cart" className="icon" />
                    </Col>
                </Row>
            </Container>

        </div>
    )
}

export default Header