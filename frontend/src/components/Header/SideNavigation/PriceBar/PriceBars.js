import React, {Component} from 'react';
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from 'react-bootstrap/FormControl'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {addFilter} from "../../../../redux/actions";
import {removeFilter} from "../../../../redux/actions";
import {clearFilters} from "../../../../redux/actions";


import './PriceBar.scss';


class SearchBar extends Component{

    handleChange(event) {
        let fieldVal = event.target.value;
        let pos;
        if(this.props.filter.filters[0]!==null) {
            pos = this.props.filter.filters.map(function (e) {
                return e.property;
            }).indexOf('priceFrom');
        }
        this.props.removeFilter(pos);
        this.props.addFilter({property:'priceFrom', value: fieldVal});
    }

    handleChange2(event) {
        let fieldVal = event.target.value;
        let pos;
        if(this.props.filter.filters[0]!==null) {
            pos = this.props.filter.filters.map(function (e) {
                return e.property;
            }).indexOf('priceTo');
        }
        this.props.removeFilter(pos);
        this.props.addFilter({property:'priceTo', value: fieldVal});
    }

    render()  {
        return(
            <div className="PriceBars">
                <InputGroup size="sm" className="priceBar" style={{maxWidth: "250px"}}>
                    {"Minimum price"}
                    <FormControl
                        style={{backgroundColor: "#191919", borderColor: "#202020"}}
                        placeholder="Name your minimum price.."
                        aria-label="Search"
                        aria-describedby="search-bar"
                        onChange={this.handleChange.bind(this)}
                    />
                    {"Maximum price"}
                    <FormControl
                        style={{backgroundColor: "#191919", borderColor: "#202020"}}
                        placeholder="Name your maximum price.."
                        aria-label="Search"
                        aria-describedby="search-bar"
                        onChange={this.handleChange2.bind(this)}
                    />

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
