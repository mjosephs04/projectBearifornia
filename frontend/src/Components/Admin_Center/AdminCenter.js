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
            <div className='pageTitle'>
                <h1>Admin Center</h1>
            </div>
            <br/>
            <div className='inputContainer1'>
                <p>Create Clerk Account</p>
            </div>
            <div className='inputContainer2'>
                <p>Reset User Passwords</p>
            </div>
            <div className='button'>
                <Link to='/add-room'>
                    <img className='clerkCreationButton' src={clerkCreationButton}/>
                </Link>
            </div>
            <div className='button'>
                <Link to='/admin-center/reset-password'>
                    <img className='clerkCreationButton' src={resetPassButton}/>
                </Link>
            </div>
        </div>
    );

}

export default AdminCenter