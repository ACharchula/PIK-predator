import {
    ADD_PRODUCT_TO_CART, GET_PRODUCTS, REMOVE_PRODUCT_FROM_CART, CLEAR_CART, SET_CART
} from '../../actions';

export const initialState = {
    products:[]
};

export default function cartReducer(state = initialState, action) {
    const { product, index, cartProducts } = action;

    let products = state.products.slice(0);
    switch (action.type) {
        case ADD_PRODUCT_TO_CART:
            products.push(product);
            return {
                ...state,
                products
            };
        case GET_PRODUCTS:
            return {
                ...state
            };
        case REMOVE_PRODUCT_FROM_CART:
            products.splice(index, 1);
            return {
                ...state,
                products
            };
        case CLEAR_CART:
            return {
                ...state,
                products:[]
            };
        case SET_CART:
            return {
                ...state,
                products: cartProducts
            };
        default:
            return {
                ...state
            };
    }
}
