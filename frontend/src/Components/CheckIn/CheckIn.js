import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./CheckIn.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
const CheckIn = (props) =>{

    const [username, setUsername] = useState('')

    return(
        <div>
            <Layout/>
            <br/>
            <br/>

            <input
                className='room-number-input'
                placeholder="Enter Username"
                className={'inputBox'}
                onChange={evt => setUsername(evt.target.value)}
            />
            <br/>

            <Link to='/confirmation'>
                <button className='checkIn-button'>Check-In</button>
            </Link>
            <Link to='/confirmation'>
                <button className='checkOut-button'>Check-Out</button>
            </Link>


        </div>
    );

}

export default CheckIn