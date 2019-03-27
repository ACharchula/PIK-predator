import React from 'react'
import './Header.scss'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from 'react-bootstrap/FormControl'


export const Header = (props) => {
    return(
        <div className="App-header">
            <Container>
                <Row>
                    <Col xs={4}>
                        <Row>
                            <FontAwesomeIcon icon="bars" className="icon"/>
                            <p className="logo">PREDATOR</p>
                        </Row>
                    </Col>
                    <Col xs={6}>
                        <InputGroup className="searchBar">
                            <FormControl
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
                    </Col>
                    <Col>
                        <FontAwesomeIcon icon="user" className="icon"/>
                        <FontAwesomeIcon icon="shopping-cart" className="icon"/>
                    </Col>
                    
                </Row>
                
            </Container>

        </div>
    )
}

export default Header