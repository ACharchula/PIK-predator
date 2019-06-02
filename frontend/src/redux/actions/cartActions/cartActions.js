import axios from "axios";

export const ADD_PRODUCT_TO_CART = 'ADD_PRODUCT_TO_CART';
export const GET_PRODUCTS = 'GET_PRODUCTS';
export const REMOVE_PRODUCT_FROM_CART = 'REMOVE_PRODUCT_FROM_CART';
export const CLEAR_CART = 'CLEAR_CART';
export const SET_CART = 'SET_CART';


export const addToCart = (product) => {
    return dispatch => {
        if (localStorage.getItem("isLoggedIn") && localStorage.getItem("isLoggedIn") === "true") {
            const author = 'Bearer '.concat(localStorage.getItem('id'));

            let productsIds = [];
            productsIds.push(product.productId);

            axios({
                method: 'post',
                url: 'https://pik-predator.herokuapp.com/users/'+localStorage.getItem("userId")+'/cart',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': author,
                },
                data: [product.productId]
            }).then(response => {
                dispatch(addToReduxCart(product));
            });
        } else dispatch(addToReduxCart(product));
    };
};

export const addToReduxCart = (product) => {
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

export const removeFromReduxCart = (index) => {
    return {
        type: REMOVE_PRODUCT_FROM_CART,
        index
    }
};

export const removeProductFromCart = (index,product) => {
    return dispatch => {
        if (localStorage.getItem("isLoggedIn") && localStorage.getItem("isLoggedIn") === "true") {
            const author = 'Bearer '.concat(localStorage.getItem('id'));

            const url = 'https://pik-predator.herokuapp.com/users/'+localStorage.getItem("userId")+'/cart/'.concat(product.productId);

            axios({
                method: 'delete',
                url: url,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': author,
                },
            }).then(response => {
                dispatch(removeFromReduxCart(index));
            });
        } else dispatch(removeFromReduxCart(index));
    };
};

export const clearReduxCart = () => {
    return {
        type: CLEAR_CART
    }
};


export const clearCart = () => {
    return dispatch => {
        if (localStorage.getItem("isLoggedIn") && localStorage.getItem("isLoggedIn") === "true") {
            const author = 'Bearer '.concat(localStorage.getItem('id'));

            const url = 'https://pik-predator.herokuapp.com/users/'+localStorage.getItem("userId")+'/cart/';

            axios({
                method: 'delete',
                url: url,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': author,
                },
            }).then(response => {
                dispatch(clearReduxCart());
            });
        } else dispatch(clearReduxCart());
    }
};

export const setCart = (cartProducts) => {
    return {
        type: SET_CART,
        cartProducts
    }
}