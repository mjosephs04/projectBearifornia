import React, { useState } from 'react';
import './dropdownMenu.css'
import {Link} from "react-router-dom"

const DropdownMenu = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };

    return (
        <div className="login" onClick={toggleDropdown}>
            <Link to='login-page'>
                <button className="login-button">Login</button>
            </Link>
        </div>
    );
};

export default DropdownMenu;