// store.js
import React, { createContext, useReducer } from 'react';

const initialState = {};
const store = createContext(initialState);
const { Provider } = store;

const FETCH_DATA = 'fetch';

const StateProvider = ({ children }) => {
	const [ state, dispatch ] = useReducer((state, action) => {
		switch (action.type) {
			case FETCH_DATA:
				return action.data;
			default:
				throw new Error();
		}
	}, initialState);

	return <Provider value={{ state, dispatch }}>{children}</Provider>;
};

export { store, StateProvider };
