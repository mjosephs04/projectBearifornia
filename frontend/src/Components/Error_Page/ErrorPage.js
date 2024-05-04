import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ErrorPage.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import sadFace from '../../assets/6463415344ea290c3bb31cbe_18.png'

const ConfirmationLanding = (props) => {


    return (
        <div>
            <Layout/>
            <div className='box'>
                <div className='confirmation-label'>
                    <p>An error has occured. Please try again!</p>
                </div>
                <br/>
                <Link to='/'>
                    <div className='return-label'>Click to go home!</div>
                </Link>
                <div className='image-containerConfirm'>
                    <img src={sadFace}/>
                </div>
            </div>
        </div>
    );

}

export default ConfirmationLanding