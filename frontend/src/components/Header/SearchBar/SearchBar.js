import React, {Component} from 'react';
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from 'react-bootstrap/FormControl'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {addFilter} from "../../../redux/actions";
import {removeFilter} from "../../../redux/actions";
import {clearFilters} from "../../../redux/actions";


import './SearchBar.scss';


class SearchBar extends Component{

    handleChange(event) {
        let fieldVal = event.target.value;
        let pos=-1;
        if(this.props.filter.filters[0]!==null) {
            pos = this.props.filter.filters.map(function (e) {
                return e.property;
            }).indexOf('query');
        }
        if(pos!==-1) this.props.removeFilter(pos,'query');
        this.props.addFilter({property:'query', value: fieldVal});
    }

    render()  {
        return(
            <div className="SearchBar">
                <InputGroup size="sm" className="searchBar" style={{maxWidth: "250px"}}>
                    <FormControl
                        style={{backgroundColor: "#191919", borderColor: "#202020"}}
                        placeholder="What are you searching for ..."
                        aria-label="Search"
                        aria-describedby="search-bar"
                        onChange={this.handleChange.bind(this)}
                    />
                    <InputGroup.Append>
                        <InputGroup.Text id="search-bar">
                            <FontAwesomeIcon icon="search"/>
                        </InputGroup.Text>
                    </InputGroup.Append>
                </InputGroup>
            </div>
        );

    }
}


const mapStateToProps = (state) => {
    return {
        login: state.login,
        cart: state.cart,
        filter: state.filter
    }
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators({ addFilter,removeFilter,clearFilters }, dispatch);
}

export default connect(
    mapStateToProps, mapDispatchToProps
)(SearchBar);
