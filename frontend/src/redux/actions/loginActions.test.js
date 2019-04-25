import * as actions from '../actions';

describe('login actions', () => {

    it('should create an action to change login status when login success', () => {
        const expectedAction = {
            type: actions.LOGIN_SUCCESS
        };

        expect(actions.successfulLogin()).toEqual(expectedAction);
    });

    it('should create an action to change login status when logout success', () => {
        const expectedAction = {
            type: actions.LOGOUT_SUCCESS
        };

        expect(actions.successfulLogout()).toEqual(expectedAction);
    });

    it('should create an action to change login status when success', () => {
        const expectedAction = {
            type: actions.LOGIN_FAILURE
        };

        expect(actions.failedLogin()).toEqual(expectedAction);
    })

});