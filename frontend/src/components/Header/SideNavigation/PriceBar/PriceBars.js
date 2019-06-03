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
        let pos=-1;
        if(this.props.filter.filters[0]!==null) {
            pos = this.props.filter.filters.map(function (e) {
                return e.property;
            }).indexOf('priceFrom');
        }
        if(pos!==-1) this.props.removeFilter(pos,'priceFrom');
        this.props.addFilter({property:'priceFrom', value: fieldVal});
    }

    handleChange2(event) {
        let fieldVal = event.target.value;
        let pos=-1;
        if(this.props.filter.filters[0]!==null) {
            pos = this.props.filter.filters.map(function (e) {
                return e.property;
            }).indexOf('priceTo');
        }
        if(pos!==-1) this.props.removeFilter(pos,'priceTo');
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
                        aria-label="priceFrom"
                        aria-describedby="price-from"
                        onChange={this.handleChange.bind(this)}
                    />
                    {"Maximum price"}
                    <FormControl
                        style={{backgroundColor: "#191919", borderColor: "#202020"}}
                        placeholder="Name your maximum price.."
                        aria-label="priceTo"
                        aria-describedby="price-to"
                        onChange={this.handleChange2.bind(this)}
                    />
                </InputGroup>
            </div>
        );

    }
}


const mapStateToProps = (state) => {
    return {
        filter: state.filter
    }
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators({ addFilter,removeFilter,clearFilters }, dispatch);
}

export default connect(
    mapStateToProps, mapDispatchToProps
)(SearchBar);
