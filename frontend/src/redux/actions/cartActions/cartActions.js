import axios from "axios";

export const ADD_PRODUCT_TO_CART = 'ADD_PRODUCT_TO_CART';
export const GET_PRODUCTS = 'GET_PRODUCTS';
export const REMOVE_PRODUCT_FROM_CART = 'REMOVE_PRODUCT_FROM_CART';
export const CLEAR_CART = 'CLEAR_CART';
export const SET_CART = 'SET_CART';


export const addToCart = (product) => {
    return dispatch => {
        if (localStorage.getItem("isLoggedIn") && localStorage.getItem("isLoggedIn") === "true") {
            console.log('true');
            const author = 'Bearer '.concat(localStorage.getItem('id'));

            let productsIds = [];
            productsIds.push(product.id);

            axios({
                method: 'post',
                url: 'https://pik-predator.herokuapp.com/users/2/cart',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': author,
                },
                data: productsIds
            }).then(response => {
                console.log(response.data);
                dispatch(addToReduxCart(product));
            });
        }
    };
};

export const addToReduxCart = (product) => {
    return {
        type: ADD_PRODUCT_TO_CART,
        product
    }
}

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
};

export const setCart = (cartProducts) => {
    return {
        type: SET_CART,
        cartProducts
    }
}