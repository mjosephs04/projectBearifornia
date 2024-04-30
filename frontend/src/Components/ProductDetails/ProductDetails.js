import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ProductDetails.css"
import {Link} from "react-router-dom";

// Assets
import goldChainIMG from '../../assets/GoldChain.png'
import sunglassesIMG from '../../assets/Sunglasses.png'
import toothbrushIMG from '../../assets/Toothbrush.png'
import toothpasteIMG from '../../assets/Toothpaste.png'
import playstationIMG from '../../assets/Playstation.png'
const ProductDetails = (props) =>{

    // State to store the fetched data
    const [productListingData, setproductListingData] = useState([]);

    const [quantity, setQuantity] = useState(1);

    const increment = () => setQuantity(quantity + 1);
    const decrement = () => setQuantity(quantity > 1 ? quantity - 1 : 1);


    const fetchData = () => {
        axios.get('http://localhost:8080/rooms/nature-retreat') // Have to change the axios.get ask Mark about this
            .then(response => {
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                return response.data;
            })
            .then(data => {
                setproductListingData(data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    };
    // Fetch data when the component mounts
    useEffect(() => {
        fetchData();
    }, []); // Empty dependency array ensures useEffect runs only once on mount




    // This will link the functionality of adding an item to the cart
    // API Call
    const onButtonClick = () => {

    };


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
                <img src={playstationIMG}></img>

                {/*This is what will grab the data from the backend*/}
                {/*<img src={productListingData.imageURL}></img>*/}

                {/*<button>Add To Cart</button>*/}
            </div>


            <Link to='/catalog'>
                <button className='Back-Button'>Go Back</button>
            </Link>

            <div className="name-container">
                <p>Name of Product: API call to get product name</p>

                {/*This is what will grab the data from the backend*/}
                {/*{productListingData.productName}*/}
            </div>


            <div className="availability-container">
                <p>Items available: API call to get accurate count</p>

                {/*This is what will grab the data from the backend*/}
                {/*{productListingData.productAvailability}*/}
            </div>


            <div className="price-container">
                <p>$$$</p>

                {/*This is what will grab the data from the backend*/}
                {/*{productListingData.productPrice}*/}
            </div>

            <div className="description-container">
                <p>This will be an api call from the back end to get the correct description for the item</p>
                {/*This is what will grab the data from the backend*/}
                {/*{productListingData.productDescription}*/}
            </div>


        </div>

    );

}

export default ProductDetails