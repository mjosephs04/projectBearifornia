
import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ClerkCenter.css"
import bedroomButton from "../../assets/76244.png";
import checkinoutButton from "../../assets/95145-200.png"
import {Link} from "react-router-dom";
const ClerkCenter = (props) =>{
    const [usernameError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const onButtonClick = () => {
        //PUT THE LOGIN METHOD HERE MY BOAAAAA
    }

    return (
        <div>
            <Layout/>
            <div className='pageTitle'>
                <h1>Clerk Center</h1>
            </div>
            <br/>
            <div className='inputContainer1'>
                <p>Add Room</p>
            </div>
            <div className='inputContainer2'>
                <p>Check In/Out</p>
            </div>
            <div className='button'>
                <Link to='/add-room'>
                    <img className='bedroomButton' src={bedroomButton}/>
                </Link>
            </div>
            <div className='button'>
                <img className='bedroomButton' src={checkinoutButton}/>
            </div>
        </div>
    );

}

export default ClerkCenter