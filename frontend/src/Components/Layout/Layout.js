import React, {useEffect, useState} from 'react'
import Logo from "../../bear_logo.png";
import DropdownMenu from "../dropdownMenu/dropdownMenu";
import './Layout.css'
import {Link} from "react-router-dom";
import axios from 'axios'
import { useNavigate } from 'react-router-dom';

const Layout = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userType, setUserType] = useState('');
    const navigate = useNavigate();

    const checkLoggedIn = () => {
        axios.get('http://localhost:8080/api/register/LOGGED-IN')
            .then(response =>{
                setIsLoggedIn(response.data);
                console.log(response);
                return response.data;
            }).catch(error => {
            console.log("Error: " + error);
        })
    };

    useEffect(() => {
        checkLoggedIn();
        getUserType();
    }, []); // Empty dependency array ensures useEffect runs only once on mount

    const logOut = () => {
        axios.get('http://localhost:8080/api/register/logOut')
            .then(response =>{
                checkLoggedIn();
                return response.data;
            }).catch(error => {
            console.log("Error: " + error);
        })
    }

    const getUserType = () => {
        axios.get('http://localhost:8080/api/register/getUserType')
            .then(response =>{
                setUserType(response.data);
                return response.data;
            }).catch(error => {
            console.log("Error: " + error);
        })
    }

    const logOut1 = () => {
        logOut();
        navigate('/');
    }

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
                <Link to='/reserve'>
                    Reserve Room
                </Link>
            </div>
            <div className='shop-link-layout'>
                <Link to='/catalog'>
                    Catalog
                </Link>
            </div>
            <div className='view-cart-link-layout'>
                <Link to='/view-cart'>
                    View Cart
                </Link>
            </div>
            <div className='view-cart-link-layout'>
                <Link to='/view-cart'>
                    View Cart
                </Link>
            </div>
            <div className='view-bill-link-layout'>
                <Link to='/view-bill'>
                    View Bill
                </Link>
            </div>

            {userType !== null && userType == "GUEST" &&
                <div className='center-link-layout'>
                    <Link to='/guest-center'>
                        Guest Center
                    </Link>
                </div>
            }

            {userType !== null && userType == "ADMIN" &&
                <div className='center-link-layout'>
                    <Link to='/admin-center'>
                        Admin Center
                    </Link>
                </div>
            }

            {userType !== null && userType == "CLERK" &&
                <div className='center-link-layout'>
                    <Link to='/clerk-center'>
                        Clerk Center
                    </Link>
                </div>
            }

            {!isLoggedIn && (
                <div className="login-drop"><DropdownMenu/></div>
            )}

            {isLoggedIn && (
                <div>
                    <button className='log-out-button' onClick={logOut1}>Log Out</button>
                </div>
            )}



        </div>
    );
}

export default Layout