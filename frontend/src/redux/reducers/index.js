import { combineReducers } from 'redux';
import login from './loginReducer';
import cart from './cartReducer/cartReducer';

const rootReducer = combineReducers({
    login,
    cart
});

export default rootReducer;