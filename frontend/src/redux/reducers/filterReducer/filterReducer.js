import {
    ADD_FILTER_TO_FILTERS_CONTAINER, CLEAR_FILTERS, REMOVE_FILTER_FROM_FILTERS_CONTAINER
} from '../../actions';

export const initialState = {
    filters:[],
    products:[]
};

export default function filterReducer(state = initialState, action) {
    const { filter, index } = action;
    let filters = state.filters.slice(0);
    switch (action.type) {
        case ADD_FILTER_TO_FILTERS_CONTAINER:
            filters.push(filter);
            console.log(filter);
            return {
                ...state,
                filters
            };
        case REMOVE_FILTER_FROM_FILTERS_CONTAINER:
            filters.splice(index, 1);
            return {
                ...state,
                filters
            };
        case CLEAR_FILTERS:
            return {
                ...state,
                filters:[]
            };
        default:
            return {
                ...state
            };
    }
}
