import React, { useState, useContext, useEffect } from 'react';
import Chart from 'react-google-charts';
import { store } from '../../store';

const RANGE_5MIN = '5';
const RANGE_60MIN = '60';
const RANGE_DAILY = 'daily';

const CandlestickChart = () => {
	let data = [
		[
			{
				type: 'string',
				id: 'Date'
			},
			{
				type: 'number'
			},
			{
				type: 'number'
			},
			{
				type: 'number'
			},
			{
				type: 'number'
			}
		]
	];
	const chartStore = useContext(store);

	const [ isFiveMin, setIsFiveMin ] = useState(true);
	const [ isSixtyMin, setIsSixtyMin ] = useState('');
	const [ isDaily, setIsDaily ] = useState('');
	const [ timeIntervalType, setIntervalType ] = useState('chartInterval5Min');
	const [ chartData, setChartData ] = useState(data);

	const options = {
		title: 'Currency chart',
		legend: 'none',
		candlestick: {
			fallingColor: { strokeWidth: 0, fill: '#a52714' }, // red
			risingColor: { strokeWidth: 0, fill: '#0f9d58' } // green
		}
	};

	useEffect(
		() => {
			if (chartStore.state[timeIntervalType]) {
				chartStore.state[timeIntervalType].forEach((row) => {
					data.push([ row.date, row.low, row.open, row.close, row.high ]);
				});
				setChartData(data);
			}
		},
		[ chartStore.state, timeIntervalType ]
	);

	const setActive = (value) => {
		switch (value) {
			case RANGE_5MIN:
				setIsFiveMin(true);
				setIntervalType('chartInterval5Min');
				setIsSixtyMin('');
				setIsDaily('');
				break;
			case RANGE_60MIN:
				setIsFiveMin('');
				setIntervalType('chartInterval60Min');
				setIsSixtyMin(true);
				setIsDaily('');
				break;
			case RANGE_DAILY:
				setIsFiveMin('');
				setIntervalType('chartIntervalDaily');
				setIsSixtyMin('');
				setIsDaily(true);
				break;
			default:
				break;
		}
	};

	return (
		<div className="col-12 mt-5">
			<div className="d-flex justify-content-between pl-5 pr-5 ml-4 mr-4">
				<button
					className={`btn btn-outline-secondary ${isFiveMin && 'active'}`}
					onClick={() => setActive(RANGE_5MIN)}
				>
					5 min
				</button>
				<button
					className={`btn btn-outline-secondary ${isSixtyMin && 'active'}`}
					onClick={() => setActive(RANGE_60MIN)}
				>
					60 min
				</button>
				<button
					className={`btn btn-outline-secondary ${isDaily && 'active'}`}
					onClick={() => setActive(RANGE_DAILY)}
				>
					Daily
				</button>
			</div>
			<Chart chartType="CandlestickChart" width="100%" height="800px" data={chartData} options={options} />
		</div>
	);
};

export default CandlestickChart;
