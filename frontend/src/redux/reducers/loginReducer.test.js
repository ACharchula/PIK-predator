import rootReducer from '../reducers';
import {initialState} from './loginReducer';
import {
    LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT_SUCCESS
} from '../actions';


describe('login reducer', () => {
    it('should return the initial state', () => {
        expect(rootReducer(undefined, {})).toMatchSnapshot()
    });

    it('should handle LOGIN_SUCCESS', () => {
        expect(
            rootReducer(initialState,
                {
                    type: 'LOGIN_SUCCESS'
                })
        ).toMatchSnapshot()
    });


    it('should handle LOGIN_FAILURE', () => {
        expect(
            rootReducer(initialState,
                {
                    type: 'LOGIN_FAILURE'
                })
        ).toMatchSnapshot()
    });

    it('should handle LOGOUT_SUCCESS', () => {
        expect(
            rootReducer(initialState,
                {
                    type: 'LOGOUT_SUCCESS'
                })
        ).toMatchSnapshot()
    })
});