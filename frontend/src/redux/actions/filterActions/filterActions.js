export const ADD_FILTER_TO_FILTERS_CONTAINER = 'ADD_FILTER_TO_FILTERS_CONTAINER';
//export const GET_PRODUCTS = 'GET_PRODUCTS';
export const REMOVE_FILTER_FROM_FILTERS_CONTAINER = 'REMOVE_FILTERS_FROM_FILTERS_CONTAINER';
export const CLEAR_FILTERS = 'CLEAR_FILTERS';

export const addFilter = (filter) => {
    return {
        type: ADD_FILTER_TO_FILTERS_CONTAINER,
        filter
    }
};

export const removeFilter = (index,property) => {
    return {
        type: REMOVE_FILTER_FROM_FILTERS_CONTAINER,
        index,property
    }
};

export const clearFilters = () => {
    return {
        type: CLEAR_FILTERS
    }
};

