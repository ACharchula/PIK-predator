export const LOGIN_REQUEST = 'LOGIN_REQUEST'
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS'
export const LOGIN_FAILURE = 'LOGIN_FAILURE'

export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS'
export const LOGOUT_FAILURE = 'LOGOUT_FAILURE'


export const requestLogin = () => {
    return {
        type: LOGIN_REQUEST
    }
}

export const successfulLogin = () => {
    return {
        type: LOGIN_SUCCESS
    }
}

export const failedLogin = () => {
    return {
        type: LOGIN_FAILURE
    }
}

export const successfulLogout = () => {
    return {
        type: LOGOUT_SUCCESS
    }
}

export const failedLogout = () => {
    return {
        type: LOGOUT_FAILURE
    }
}