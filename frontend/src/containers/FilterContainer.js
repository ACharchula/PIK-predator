import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from "redux";
import { removeFilter, clearFilters } from "../redux/actions";

import Product from '../components/Product/Product';

const FilterContainer = (props) => {
    return (
        <div>
            <CartItemsList products={props.cart.products} removeItem={props.removeFilter} clearCart={props.clearFilters}/>
        </div>
    );
};

const mapStateToProps = (state) => {
    return {
        login: state.login,
        cart: state.cart
    };
};

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators({ removeFilter, clearFilters }, dispatch);
}

export default connect(
    mapStateToProps, mapDispatchToProps
)(FilterContainer);

