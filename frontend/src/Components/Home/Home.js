
import React, {useEffect, useState} from 'react';
import './Home.css'
import homeBanner from '../../assets/homepage-banner.jpg'
import axios from 'axios';
import Layout from "../Layout/Layout";
import { Link } from 'react-router-dom';


const Home = () => {

    return (
        <div>
            <div>
                <Layout />
                <img className='home-banner' src={homeBanner} alt="Reserve Now!"/>
                <h1 className='text-overlay'>Reserve Now</h1>
                <Link to='/reserve'>
                    <button className='reserve-button' >Reserve</button>
                </Link>
                {/*<button className='reserve-button' onClick={Clicked}>Reserve</button>*/}
            </div>
        </div>
    );
};

export default Home