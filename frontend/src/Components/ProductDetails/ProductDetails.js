import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ProductDetails.css"
import {Link} from "react-router-dom";
import { useLocation, useParams } from 'react-router-dom'


// Assets
import goldChainIMG from '../../assets/GoldChain.png'
import sunglassesIMG from '../../assets/Sunglasses.png'
import toothbrushIMG from '../../assets/Toothbrush.png'
import toothpasteIMG from '../../assets/Toothpaste.png'
import playstationIMG from '../../assets/Playstation.png'
const ProductDetails = (props) =>{

    // State to store the fetched data
    const [productName, setProductName] = useState('');
    const [inStock, setInStock] = useState(0);
    const [description, setDescription] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [imgURL, setImgURL] = useState('');
    const [price, setPrice] = useState(0.00);
    const increment = () => setQuantity(quantity + 1);
    const decrement = () => setQuantity(quantity > 1 ? quantity - 1 : 1);
    const location = useLocation()
    const params = useParams()

    const fetchData = (props) => {
        console.log(params)
        axios.get('http://localhost:8080/api/products/' + params.name) // Have to change the axios.get ask Mark about this
            .then(response => {
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                console.log(response.data)

                return response.data;
            })
            .then(data => {
                setImgURL(data.imageURL);
                setProductName(data.productName);
                setInStock(data.productStock);
                setPrice(data.productPrice);
                setDescription(data.productDescription);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    };
    // Fetch data when the component mounts
    useEffect(() => {
        fetchData();
    }, []); // Empty dependency array ensures useEffect runs only once on mount


    return (
        <div class="Container1">
            <Layout/>


            <div className="product">
                {/*<h2>{product.name} - ${product.price.toFixed(2)}</h2>*/}
                <div className="controls">
                    <button onClick={decrement}>-</button>
                    <span className="quantity">{quantity}</span>
                    <button onClick={increment}>+</button>
                </div>
                {/*<button onClick={() => addToCart(product, quantity)} className="add-to-cart">Add to Cart</button>*/}
                <button>Add to Cart</button>
            </div>

            <div className="image-container">
                <img src={imgURL}></img>
            </div>


            <Link to='/catalog'>
                <button className='Back-Button'>Go Back</button>
            </Link>

            <div className="name-container">
                <p>{productName}</p>
            </div>


            <div className="availability-container">
                <p>Number available: {inStock}</p>
            </div>


            <div className="price-container">
                <p>${price}</p>
            </div>

            <div className="description-container">
                <p>{description}</p>
            </div>


        </div>

    );

}

export default ProductDetails