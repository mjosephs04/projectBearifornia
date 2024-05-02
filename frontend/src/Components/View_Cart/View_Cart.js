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
                    <p>Subtotal: $131.00</p>
                    <p>Estimated tax: $1.00</p>
                    <p>Total: $1320.99</p>
                    <button className='addToBillButton'>Add to bill</button>
                </div>
                <div className="flexbox">
                    <div className="item">
                        <div className="image-containerCart">
                            <img src={goldChainIMG} alt="Image 1"/>
                            <p>Ernesto's Links</p>
                            <p className="quantity">Quantity: 1</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-containerCart">
                            <img src="https://th.bing.com/th/id/OIP.g_QVsYF4dq3CETnUauXsaQHaHa?rs=1&pid=ImgDetMain"
                                 alt="Image 1"/>
                            <p>Watermelon Shampoo</p>
                            <p className="quantity">Quantity: 2</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-containerCart">
                            <img src="https://i5.walmartimages.com/seo/Garnier-Fructis-Plumping-Treat-Conditioner-with-Watermelon-Extract-11-8-fl-oz_b9cd0c48-9fdc-45f1-af48-5c86b233a7c4.4840ede7cc165057054e09f8f787a8ca.jpeg"
                                 alt="Image 1"/>
                            <p>Watermelon Conditioner</p>
                            <p className="quantity">Quantity: 1</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-containerCart">
                            <img src="https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcRt1Zxwhuxt_HtJNaMZjJE6U-PsvNCwpWD-BDrLCvbAQ4uj8Y4N3HJYudLOra2MlxEpRg0VEEXSZv-puv3SLtPcK4-ID2g38PuuTZicIVWBIpXuKGjueHF4FdLDkSdO7A&usqp=CAc"
                                 alt="Image 1"/>
                            <p>Bear Costume</p>
                            <p className="quantity">Quantity: 1</p>
                        </div>
                    </div>
                    <div className="item">
                        <div className="image-containerCart">
                            <img src="https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQCfd-4Ztx7PrKLs1UgpTb_Ce2sfYDfnzrMVVEpx4asi2Mp4ncFlg0NxQX9FWNfJCSSvWrS5Rgo3oLlVfDC9043U5rbWFm5OSSTSIHB6dm3N5zsH4XxyDBvfBlXN1rtI-XY90FvihHsnQ&usqp=CAc"
                                 alt="Image 1"/>
                            <p>Bear Hat</p>
                            <p className="quantity">Quantity: 1</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}

export default View_Cart