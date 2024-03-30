
import React from 'react'
import slider1 from "../../assets/slider-2.jpg";
import Logo from "../../bear_logo.png";
import DropdownMenu from "../dropdownMenu/dropdownMenu";
import './Layout.css'
import {Link} from "react-router-dom";

const Layout = () => {
    return (
        <div className="container">
            <Link to='/' className="title-color">
                <div className="logo">
                    <img src={Logo} alt="Hotel Bearifornia Logo"/>
                </div>
                <div className="title">
                    Hotel Bearifornia
                </div>
            </Link>

            <div className='home-link'>
                Reserve Room
            </div>
            <div className="login-drop"><DropdownMenu/></div>

        </div>
    );
}

export default Layout