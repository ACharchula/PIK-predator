export const ADD_PRODUCT_TO_CART = 'ADD_PRODUCT_TO_CART';


export const addToCart = (product) => {
    return {
        type: ADD_PRODUCT_TO_CART,
        product
    }
};
