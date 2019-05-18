import {
    ADD_PRODUCT_TO_CART
} from '../../actions';

export const initialState = {
    products:[]
};

export default function cartReducer(state = initialState, action) {

    switch (action.type) {
        case ADD_PRODUCT_TO_CART:
            const { product } = action;
            let products = state.products.slice(0);// copy an array
            products.push(product);
            return {
                ...state,
                products
            };
        default:
            return {
                ...state
            };
    }
}
