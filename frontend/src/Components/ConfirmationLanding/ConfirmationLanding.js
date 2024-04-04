import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ConfirmationLanding.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';

const ConfirmationLanding = (props) => {


    return (
        <div>
            <Layout/>
            <div className='confirmation-label'>Reservation has been made!</div>
            <br/>
            <div className='return-label'>Click on the Bear to go back to home page!</div>

        </div>
    );

}

export default ConfirmationLanding