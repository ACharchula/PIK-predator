import { combineReducers } from 'redux';
import login from './loginReducer';
import cart from './cartReducer/cartReducer';
import filter from  './filterReducer/filterReducer'

const rootReducer = combineReducers({
    login,
    cart,
    filter
});

export default rootReducer;