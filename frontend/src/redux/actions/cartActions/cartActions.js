export const ADD_PRODUCT_TO_CART = 'ADD_PRODUCT_TO_CART';
export const GET_PRODUCTS = 'GET_PRODUCTS';


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