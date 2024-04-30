import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ConfirmationLanding.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import thumbsUp from '../../assets/dec71f1888f42c9f92c55889564d64ab_256.png'

const ConfirmationLanding = (props) => {


    return (
        <div>
            <Layout/>
            <div className='box'>
                <div className='confirmation-label'>
                    <p>Your request has been made!</p>
                </div>
                <br/>
                <Link to='/'>
                    <div className='return-label'>Click to go home!</div>
                </Link>
                <div className='image-containerConfirm'>
                    <img src={thumbsUp}/>
                </div>
            </div>
        </div>
    );

}

export default ConfirmationLanding