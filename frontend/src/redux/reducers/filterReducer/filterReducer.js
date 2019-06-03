import {
    ADD_FILTER_TO_FILTERS_CONTAINER, CLEAR_FILTERS, REMOVE_FILTER_FROM_FILTERS_CONTAINER, FILTER_PRODUCTS
} from '../../actions';

export const initialState = {
    filters:[],
    hardDrives:0,
    processors:0,
    displays:0

};

export default function filterReducer(state = initialState, action) {
    const { filter, index, property } = action;
    let filters = state.filters.slice(0);
    switch (action.type) {
        case ADD_FILTER_TO_FILTERS_CONTAINER:
            filters.push(filter);
            switch(filter.property){
                case 'processor': {
                    ++state.processors;
                    filter.property+=state.processors;
                    break;
                }
                case 'hardDriveType':{
                    ++state.hardDrives;
                    filter.property+=state.hardDrives;
                    break;
                }
                case 'displayResolution':{
                    ++state.displays;
                    filter.property+=state.displays;
                    break;
                }
                default: break;
            }
            return {
                ...state,
                filters
            };
        case REMOVE_FILTER_FROM_FILTERS_CONTAINER:
            switch(property){
                case 'processor': {
                    --state.processors;
                    break;
                }
                case 'hardDriveType':{
                    --state.hardDrives;
                    break;
                }
                case 'displays':{
                    --state.displays;
                    break;
                }
                default: break;
            }
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
        case REMOVE_FILTER_FROM_FILTERS_CONTAINER:
            filters.splice(index, 1);
            return {
                ...state,
                filters
            };
        case FILTER_PRODUCTS:
            return {
                ...state
            };
        default:
            return {
                ...state
            };
    }
}
