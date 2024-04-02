import React from 'react';
import './App.css'
import Home from './Components/Home/Home'
import Reserve from './Components/Reserve/Reserve'
import UrbanElegance from './Components/Urban_Elegance/UrbanElegance'
import NatureRetreat from './Components/Nature-Retreat/Nature-Retreat'
import Vintage from './Components/Vintage/Vintage'
import {Route, Routes} from "react-router-dom";

const App = () => {
    return (
        <>
            <Routes>
                <Route exact path="/" element={<Home />} />
                <Route exact path='/reserve' element={<Reserve />}/>
                <Route exact path='/reserve/urban-elegance' element={<UrbanElegance />}/>
                <Route exact path='/reserve/nature-retreat' element={<NatureRetreat />}/>
                <Route exact path='/reserve/old-vintage' element={<Vintage />}/>
            </Routes>
        </>
    );

};

export default App;