import React from 'react'
import Layout from '../Layout/Layout.js'
import urbanElegance from '../../assets/urban-elegance.jpg'
import natureRetreat from '../../assets/nature-retreat.jpg'
import './Reserve.css'
import {Link} from "react-router-dom";

const Reserve = () => {

    return (
        <div>
            <Layout />

            <Link to='/reserve/urban-elegance' className='fixing-color'>
                <img className='urban-elegance-img' src={urbanElegance} alt="room1"/>
                <div className='urban-elegance-label'>Urban Elegance - $199/night</div>
                <button className='urban-learn-more'>Learn More</button>
            </Link>

            <Link to='/reserve/nature-retreat' className='fixing-color'>
                <img className='nature-retreat-img' src={natureRetreat} alt="room1"/>
                <div className='nature-retreat-label'>Nature Retreat - $249/night</div>
                <button className='nature-retreat-button'>Learn More</button>
            </Link>
        </div>
    );
}

export default Reserve