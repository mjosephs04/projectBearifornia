import React, { useState } from 'react';
import './dropdownMenu.css'
import {Link} from "react-router-dom"

const DropdownMenu = () => {

    return (
        <div className="login">
            <Link to='/login-page'>
                <button className="login-button">Login</button>
            </Link>
        </div>
    );
};

export default DropdownMenu;