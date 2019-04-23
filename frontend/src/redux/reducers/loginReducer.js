import {
     LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT_SUCCESS
} from '../actions';

export default function(state={}, action) {

    switch (action.type) {
        case LOGIN_SUCCESS:
            return {
                ...state,
                login_status: "logged_in"
            };
        case LOGIN_FAILURE:
            return {
                ...state,
                login_status: "logged_out"
            };
        case LOGOUT_SUCCESS:
            return {
                ...state,
                login_status: "logged_out"
            };
        default:
            return {
                ...state
            };
    }
}
