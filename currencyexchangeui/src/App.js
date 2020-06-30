import React from 'react';
import Converter from './components/converter/Converter';
import CandlestickChart from './components/graph/CandlestickChart';
import {StateProvider} from './store.js';

function App() {
    return (
        <StateProvider>
            <div className="container-fluid">
                <h1 className="display-4 text-center">Currency Exchange</h1>
                <Converter/>
                <CandlestickChart/>
            </div>
        </StateProvider>
    );
}

export default App;
