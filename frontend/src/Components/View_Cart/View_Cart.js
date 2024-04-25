import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'

//assets
import goldChainIMG from '../../assets/GoldChain.png'
import sunglassesIMG from '../../assets/Sunglasses.png'
import toothbrushIMG from '../../assets/Toothbrush.png'
import toothpasteIMG from '../../assets/Toothpaste.png'
import playstationIMG from '../../assets/Playstation.png'

import "./View_Cart.css"
import {Link} from "react-router-dom";

const View_Cart = () =>{

    return (
        <div className="image-row">
            <Layout/>

            <div className="box">
                <div className="item">
                    <div className="image-container">
                        <img src={goldChainIMG} alt="Image 1"/>
                        <p>Tupac's Gold Chain og</p>
                        <p class="quantity">fdsaio</p>
                    </div>
                </div>

                <div className="item">
                    <div className="image-container">
                        <img src={goldChainIMG} alt="Image 1"/>
                        <p>Tupac's Gold Chain test1</p>
                    </div>
                </div>
                <div className="item">
                    <div className="image-container">
                        <img src={goldChainIMG} alt="Image 1"/>
                        <p>Tupac's Gold Chain test2</p>
                    </div>
                </div>
                <div className="item">
                    <div className="image-container">
                        <img src={goldChainIMG} alt="Image 1"/>
                        <p>Tupac's Gold Chain test3</p>
                    </div>
                </div>

            </div>
        </div>
    );

}

export default View_Cart