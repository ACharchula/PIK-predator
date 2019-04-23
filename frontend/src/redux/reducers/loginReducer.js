import {
    LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT_FAILURE, LOGOUT_SUCCESS
} from '../actions';

export default function(state={}, action) {

    switch (action.type) {
        case LOGIN_REQUEST:
            return {
                ...state,
                login_status: "request"
            }
        case LOGIN_SUCCESS:
            return {
                ...state,
                login_status: "logged_in"
            }
        case LOGIN_FAILURE:
            return {
                ...state,
                login_status: "logged_out"
            }
        case LOGOUT_SUCCESS:
            return {
                ...state,
                login_status: "logged_out"
            }
        case LOGOUT_FAILURE:
            return {
                ...state
            };
        default:
            return {
                ...state
            };
    }
}
