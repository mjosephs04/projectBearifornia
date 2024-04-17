import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'

//assets
import goldChainIMG from '../../assets/GoldChain.png'
import sunglassesIMG from '../../assets/Sunglasses.png'
import toothbrushIMG from '../../assets/Toothbrush.png'
import toothpasteIMG from '../../assets/Toothpaste.png'
import playstationIMG from '../../assets/Playstation.png'

import "./ShopCatalog.css"
import {Link} from "react-router-dom";

const ShopCatalog = () =>{

    return (
        <div className="image-row">
            <Layout/>

            <div className="image-container">
                <img src={goldChainIMG} alt="Image 1"/>
                <p>Tupac's Gold Chain</p>
                <button>More Details</button>
            </div>
            <div className="image-container">
                <img src={sunglassesIMG} alt="Image 2"/>
                <p>Sunglasses</p>
                <button>More Details</button>
            </div>
            <div className="image-container">
                <img src={toothbrushIMG} alt="Image 3"/>
                <p>Philips Sonicare Toothbrush</p>
                <button>More Details</button>
            </div>
            <div className="image-container">
                <img src={toothpasteIMG} alt="Image 4"/>
                <p>Generic Toothpaste</p>
                <button>More Details</button>
            </div>
            <div className="image-container">
                <img src={playstationIMG} alt="Image 4"/>
                <p>Playstation 5 (Slim)</p>
                <button>More Details</button>
            </div>
        </div>
    );

}

export default ShopCatalog