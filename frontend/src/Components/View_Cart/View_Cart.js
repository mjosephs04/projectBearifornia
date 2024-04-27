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
                <div className="receipt">
                    <h1>Receipt of cart</h1>
                    <p>Account name: Jan Jasa</p>
                    <p>Using card ending with: 1234</p>
                    <p>Subtotal: $131</p>
                    <p>Tax: 12.5%</p>
                    <p>Total: $10.00</p>
                </div>
                <div className="flexbox">
                    <div className="item">
                        <div className="image-container">
                            <img src={goldChainIMG} alt="Image 1"/>
                            <p>Tupac's Gold Chain og</p>
                            <p className="quantity">Quantity: 1</p>
                        </div>
                    </div>

                    <div className="item">
                        <div className="image-container">
                            <img src={goldChainIMG} alt="Image 1"/>
                            <p>Tupac's Gold Chain1</p>
                            <p className="quantity">Quantity: 2</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-container">
                            <img src={goldChainIMG} alt="Image 1"/>
                            <p>Tupac's Gold Chain2</p>
                            <p className="quantity">Quantity: 1</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-container">
                            <img src={goldChainIMG} alt="Image 1"/>
                            <p>Tupac's Gold Chain3</p>
                            <p className="quantity">Quantity: 2</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-container">
                            <img src={goldChainIMG} alt="Image 1"/>
                            <p>Tupac's Gold Chain3</p>
                            <p className="quantity">Quantity: 2</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}

export default View_Cart