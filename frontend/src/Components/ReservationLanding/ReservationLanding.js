import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ReservationLanding.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';

const ReservationLanding = (props) => {

    const onButtonClick = () => {
        //do something
    };

    return (
        <div>
            <Layout/>
            <br/>
            <div className='inputContainer'>

                <input
                    // //value={email}
                    placeholder="Enter your name here"
                    className={'inputBox'}
                    // onChange={evt => setUsername(evt.target.value)}
                />
            </div>
            <br/>
            <div className={'inputContainer'}>
                <Link to='/confirmation'>
                    <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Reserve Now'}/>
                </Link>
            </div>


        </div>
    );

}

export default ReservationLanding