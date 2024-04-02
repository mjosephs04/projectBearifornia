import React, { useState } from 'react';
import './dropdownMenu.css'

const DropdownMenu = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };

    return (
        <div className="login" onClick={toggleDropdown}>
            <button className="login-button" >Login</button>
            {isOpen && (
                <div className="dropdown-content">
                    <a href="#">Login for guest</a>
                    <a href="#">Login for employee</a>
                </div>
            )}
        </div>
    );
};

export default DropdownMenu;