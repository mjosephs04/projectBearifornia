
import React from 'react'
import Layout from '../Layout/Layout.js'
import room1 from '../../assets/room1.jpg'
import './Reserve.css'
import {Link} from "react-router-dom";

const Reserve = () => {

    return (
        <div>
            <Layout />

            <Link to='/reserve/urban-elegance' className='fixing-color'>
                <img className='room1-img' src={room1} alt="room1"/>
                <div className='urban-elegance-label'>Urban Elegance - $199/night</div>
                <button className='urban-learn-more'>Learn More</button>
            </Link>
        </div>
    );
}

export default Reserve