export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';


export const successfulLogin = () => {
    return {
        type: LOGIN_SUCCESS
    }
};

export const failedLogin = () => {
    return {
        type: LOGIN_FAILURE
    }
};

export const successfulLogout = () => {
    return {
        type: LOGOUT_SUCCESS
    }
};
