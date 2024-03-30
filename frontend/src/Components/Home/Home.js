
import React, {useEffect, useState} from 'react';
import './Home.css'
import slider1 from '../../assets/slider-2.jpg'
import axios from 'axios';
import Layout from "../Layout/Layout";
import { Link } from 'react-router-dom';


const Home = () => {

    const [imageUrl, setImageUrl] = useState('');

    useEffect(() => {
        // Make a GET request to your Spring Boot REST API to fetch the image URL
        axios.get('http://localhost:8080/api/img')
            .then(response => {
                console.log(response.data);
                setImageUrl(response.data); // Assuming the API returns the image URL as plain text
            })
            .catch(error => {
                console.error('Error fetching image:', error);
            });
    }, []);

    return (
        <div>
            <div>
                <Layout />
                <img className='home-banner' src={imageUrl} alt="Reserve Now!"/>
                <h1 className='text-overlay'>Reserve Now</h1>
                <Link to='/reserve'>
                    {/*<button className='reserve-button' onClick={Clicked}>Reserve</button>*/}
                </Link>
                {/*<button className='reserve-button' onClick={Clicked}>Reserve</button>*/}
            </div>
        </div>
    );
};

export default Home