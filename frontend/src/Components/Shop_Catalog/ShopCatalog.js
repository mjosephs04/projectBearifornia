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
        <div>
            <Layout/>

            <div className="catalogCatalog">
                <div className="itemCatalog">
                    <img src={goldChainIMG} alt="Product 1"/>
                    <h2>Ernesto's Links</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img src={sunglassesIMG} alt="Product 2"/>
                    <h2>Temu Sunglasses</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img src="https://th.bing.com/th/id/OIP.g_QVsYF4dq3CETnUauXsaQHaHa?rs=1&pid=ImgDetMain"
                         alt="Product 2"/>
                    <h2>Watermelon Shampoo</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://i5.walmartimages.com/seo/Garnier-Fructis-Plumping-Treat-Conditioner-with-Watermelon-Extract-11-8-fl-oz_b9cd0c48-9fdc-45f1-af48-5c86b233a7c4.4840ede7cc165057054e09f8f787a8ca.jpeg"
                        alt="Product 2"/>
                    <h2>Watermelon Conditioner</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://target.scene7.com/is/image/Target/GUEST_6239f58b-a793-4fb8-b3d4-ad32adcf6060?wid=400&hei=400&qlt=80&fmt=webp"
                        alt="Product 2"/>
                    <h2>Watermelon Body Wash</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://target.scene7.com/is/image/Target/GUEST_beb94141-a023-454d-8a3c-675b3e01184f?wid=400&hei=400&qlt=80&fmt=webp"
                        alt="Product 2"/>
                    <h2>Watermelon Lotion</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img src="https://www.gosupps.com/media/catalog/product/5/1/51hpizJAqcL.jpg" alt="Product 2"/>
                    <h2>Watermelon Towel</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://grizzshopping.com/cdn/shop/files/Watermelon-Watercolor-Print-Pattern-Bathrobe.jpg?v=1701627785"
                        alt="Product 2"/>
                    <h2>Watermelon Bathrobe</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://target.scene7.com/is/image/Target/GUEST_0d63b17d-4cfb-44cd-92dc-489aef503496?wid=400&hei=400&qlt=80&fmt=webp"
                        alt="Product 2"/>
                    <h2>Watermelon Chips</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://m.media-amazon.com/images/I/51py2SdmrSL._SX300_SY300_QL70_FMwebp_.jpg"
                        alt="Product 2"/>
                    <h2>Watermelon Candy</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcR3GCj5HoGbS6T3DatTYIVT7XMS6ZYx_AVBil95eR67Yi1kGofpREs5ElgMsy5NQai9eQZeYI1MuteMvidNMYH-5RyE83JEzbehTpSg_13xoEac8wlSD3zxzoqHifv1NPTgLt-Jvkw&usqp=CAc"
                        alt="Product 2"/>
                    <h2>Water</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcR9Hjw-XjTL-0g82sMOC7JrTBYMjDPVd72uxee7Q39UV0RB_nGxUdFw7_CLssFnBOudkivWIjkRLWlGIKxd6iUPYPE1UPHkOhLvlIG9u8t6MQOXy5IZqXQ0TfLo8uvbCA&usqp=CAc
    alcoholic beverages -"
                        alt="Product 2"/>
                    <h2>Pina colada Smoothie</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQCfd-4Ztx7PrKLs1UgpTb_Ce2sfYDfnzrMVVEpx4asi2Mp4ncFlg0NxQX9FWNfJCSSvWrS5Rgo3oLlVfDC9043U5rbWFm5OSSTSIHB6dm3N5zsH4XxyDBvfBlXN1rtI-XY90FvihHsnQ&usqp=CAc"
                        alt="Product 2"/>
                    <h2>Bear Hat</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQ9SL9we7x-qoK1EaaPOWgwi8SGhZutGshu21oSRk5_B4oC6BpDnSi4JC4dzq_1Lwg3YsYTlti4O3jzPNYkoV9TI5mXAvCE8i0azLl-AWRTPDTtkgAP1YfsyAo7yLwsi-0EFIBlTaI&usqp=CAc"
                        alt="Product 2"/>
                    <h2>Bear Swimsuit</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://m.media-amazon.com/images/I/81Q05pa-wBL.jpg"
                        alt="Product 2"/>
                    <h2>Non-Descript Bear Graham Cracker</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
                <div className="itemCatalog">
                    <img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSpGWEFkkt3D7DMlpo5tSXG6u8mRQbUUJv_uA&s"
                        alt="Product 2"/>
                    <h2>Texas Keychain</h2>
                    <Link to='/product-details'>
                        <button>View Details</button>
                    </Link>
                </div>
            </div>
        </div>
    );

}

export default ShopCatalog