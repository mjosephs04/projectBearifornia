import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./AdminCenter.css"
import clerkCreationButton from "../../assets/76244.png";
import resetPassButton from "../../assets/resetPassButton.png"
import {Link} from "react-router-dom";
const AdminCenter = (props) =>{
    const [usernameError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const onButtonClick = () => {
        //PUT THE LOGIN METHOD HERE
    }

    return (
        <div>
            <Layout/>
            <div className='pageTitle_01'>
                <h1>Admin Center</h1>
            </div>
            <br/>
            <div className='inputContainer_01'>
                <p>Create Clerk Account</p>
            </div>
            <div className='inputContainer_02'>
                <p>Reset User Passwords</p>
            </div>
            <div className='button'>
                <Link to='/admin-center/create-clerk'>
                    <img className='adminButtons' src={clerkCreationButton}/>
                </Link>
            </div>
            <div className='button'>
                <Link to='/admin-center/reset-password'>
                    <img className='adminButtons' src={resetPassButton}/>
                </Link>
            </div>
        </div>
    );

}

export default AdminCenter