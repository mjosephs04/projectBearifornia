
import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./GuestCenter.css"
import bedroomButton from "../../assets/76244.png";
import checkinoutButton from "../../assets/95145-200.png"
import modifyInfoButton from "../../assets/bookmark.png"
import {Link} from "react-router-dom";
const GuestCenter = (props) =>{
    const [usernameError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const onButtonClick = () => {
        //PUT THE LOGIN METHOD HERE MY BOAAAAA
    }

    return (
        <div>
            <Layout/>
            <div className='pageTitle'>
                <h1>Guest Center</h1>
            </div>
            <br/>
            <div className='inputContainer1Clerk'>
                <p>Modify Reservation</p>
            </div>
            <div className='button'>
                <Link to='/modify-reservation'>
                    <img className='bedroomButton' src={bedroomButton}/>
                </Link>
            </div>
        </div>
    );

}

export default GuestCenter