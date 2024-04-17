import React from 'react';
import './App.css'
import Home from './Components/Home/Home'
import Reserve from './Components/Reserve/Reserve'
import UrbanElegance from './Components/Urban_Elegance/UrbanElegance'
import NatureRetreat from './Components/Nature-Retreat/Nature-Retreat'
import Vintage from './Components/Vintage/Vintage'
import LoginPage from './Components/Login_Page/LoginPage'
import CreateAccount from './Components/Create_Account/CreateAccount'
import ClerkCenter from './Components/Clerk_Center/ClerkCenter'
import AddRoom from './Components/AddRoom/AddRoom'
import ReservationLanding from './Components/ReservationLanding/ReservationLanding'
import ShopCatalog from './Components/Shop_Catalog/ShopCatalog'
import {Route, Routes} from "react-router-dom";
// import ConfirmationLanding from "./Components/ConfirmationLanding/ConfirmationLanding";
const App = () => {
    return (
        <>
            <Routes>
                <Route exact path="/" element={<Home />} />
                <Route exact path='/reserve' element={<Reserve />}/>
                <Route exact path='/reserve/urban-elegance' element={<UrbanElegance />}/>
                <Route exact path='/reserve/nature-retreat' element={<NatureRetreat />}/>
                <Route exact path='/reserve/old-vintage' element={<Vintage />}/>
                <Route exact path='/login-page' element={<LoginPage />}/>
                <Route exact path='/create-account' element={<CreateAccount />}/>
                <Route exact path='/clerk-center' element={<ClerkCenter />}/>
                <Route exact path='/add-room' element={<AddRoom />}/>
                <Route exact path='/reservation' element={<ReservationLanding />}/>
                <Route exact path='/shop-catalog' element={<ShopCatalog />}/>
                {/*<Route exact path='/confirmation' element={<ConfirmationLanding />}/>*/}
            </Routes>
        </>
    );

};

export default App;