import React from 'react'
import Layout from '../Layout/Layout'
import './Shop-Home.css'
import homeSlide from '../../assets/shop-home.jpeg'
import { Link } from 'react-router-dom';

const ShopHome = () => {
    return(
        <div>
            <Layout />
            <img className='home-slide' src={homeSlide} />

            <Link to='/'>
                <button className='shop-now-button' >Shop Now</button>
            </Link>
            {/*<button className='shop-now-button'>Shop Now</button>*/}
        </div>
    );
}

export default ShopHome;