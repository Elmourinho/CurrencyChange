import React, { useState, useEffect, useContext } from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import axios from 'axios';

import { store } from '../../store';

const Converter = () => {
	const [ toCurrency, setToCurrency ] = useState('gbp');
	const [ fromCurrency, setFromCurrency ] = useState('eur');
	const [ toCurrencyValue, setToCurrencyValue ] = useState(1);
	const [ fromCurrencyValue, setFromCurrencyValue ] = useState(1);
	const [ isToTriggered, setIsToTriggered ] = useState(false);
	const [ isUserTriggered, setIsUserTriggered ] = useState(true);

	const { dispatch } = useContext(store);

	useEffect(
		() => {
			if (isUserTriggered) {
				if (fromCurrency === toCurrency) {
					if (isToTriggered) {
						setFromCurrencyValue(toCurrencyValue);
					} else {
						setToCurrencyValue(fromCurrencyValue);
					}
					setIsUserTriggered(false);
				} else {
					axios
						.get('http://localhost:8080/rates', {
							params: {
								from: fromCurrency,
								to: toCurrency
							}
						})
						.then((response) => {
							if (isToTriggered) {
								setFromCurrencyValue(parseFloat(toCurrencyValue * (1 / response.data.rate)).toFixed(2));
							} else {
								setToCurrencyValue(parseFloat(fromCurrencyValue * response.data.rate).toFixed(2));
							}
							setIsUserTriggered(false);

							console.log('Response is', response.data);
							dispatch({ type: 'fetch', data: response.data });
						})
						.catch((error) => {
							console.log('Error is', error);
						});
				}
			}
		},
		[ isUserTriggered, isToTriggered, fromCurrency, toCurrency ]
	);

	const handleCurrencyValueChange = (e) => {
		if (e.target.name === 'toCurrency') {
			setToCurrencyValue(e.target.value);
			setIsToTriggered(true);
		} else {
			setFromCurrencyValue(e.target.value);
			setIsToTriggered(false);
		}
		setIsUserTriggered(true);
	};

	const handleCurrencyOptionChange = (e) => {
		if (e.target.name === 'toCurrency') {
			setToCurrency(e.target.value);
		} else {
			setFromCurrency(e.target.value);
		}
		setIsUserTriggered(true);
	};

	return (
		<div className="col-6 mt-5">
			<Form.Group as={Row}>
				<Col md="6">
					<Form.Control
						onChange={handleCurrencyValueChange}
						value={fromCurrencyValue}
						name="fromCurrency"
						type="text"
					/>
				</Col>
				<Col md="6">
					<Form.Control
						as="select"
						value={fromCurrency}
						onChange={handleCurrencyOptionChange}
						name="fromCurrency"
					>
						<option value="eur">EUR</option>
						<option value="usd">USD</option>
						<option value="gbp">GBP</option>
					</Form.Control>
				</Col>
			</Form.Group>
			<Form.Group as={Row}>
				<Col md="6">
					<Form.Control
						onChange={handleCurrencyValueChange}
						value={toCurrencyValue}
						name="toCurrency"
						type="text"
					/>
				</Col>
				<Col md="6">
					<Form.Control
						as="select"
						value={toCurrency}
						onChange={handleCurrencyOptionChange}
						name="toCurrency"
					>
						<option value="eur">EUR</option>
						<option value="usd">USD</option>
						<option value="gbp">GBP</option>
					</Form.Control>
				</Col>
			</Form.Group>
		</div>
	);
};

export default Converter;
