export const ADD_PRODUCT_TO_CART = 'ADD_PRODUCT_TO_CART';
export const GET_PRODUCTS = 'GET_PRODUCTS';
export const REMOVE_PRODUCT_FROM_CART = 'REMOVE_PRODUCT_FROM_CART';
export const CLEAR_CART = 'CLEAR_CART';


export const addToCart = (product) => {
    return {
        type: ADD_PRODUCT_TO_CART,
        product
    }
};

export const getProducts = () => {
    return {
        type: GET_PRODUCTS
    }
};

export const removeProductFromCart = (index) => {
    return {
        type: REMOVE_PRODUCT_FROM_CART,
        index
    }
};

export const clearCart = () => {
    return {
        type: CLEAR_CART
    }
}