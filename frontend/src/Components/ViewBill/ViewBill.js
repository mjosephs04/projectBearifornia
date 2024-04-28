import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'

import "./ViewBill.css"
import {Link} from "react-router-dom";

const ViewBill = () =>{

    // State to store the fetched data
    const [receiptData, setReceiptData] = useState([]);
    const fetchData = () => {
        axios.get('http://localhost:8080/rooms/nature-retreat') // Have to change the axios.get ask Mark about this
            .then(response => {
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                return response.data;
            })
            .then(data => {
                setReceiptData(data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    };
    // Fetch data when the component mounts
    useEffect(() => {
        fetchData();
    }, []); // Empty dependency array ensures useEffect runs only once on mount



    // This will link the functionality of paying for the total bill,
    // API Call
    const onButtonClick = () => {

    };



    return (
        <div>
            <Layout/>

            <div className="box_ViewBill">
                <div className="receipt_ViewBill">
                    <h1>Receipt of Stay</h1>

                    <div className="accountname_container">
                        <p>Name of Account: API call to get account name</p>

                        {/*This is what will grab the data from the backend*/}
                        {/*{setReceiptData.accountName}*/}
                    </div>


                    <p>Account name: Jan Jasa</p>

                    <div className="cardinfo_container">
                        <p>Card Info: API call to get card info</p>

                        {/*This is what will grab the data from the backend*/}
                        {/*{setReceiptData.cardInfo}*/}
                    </div>
                    <p>Using card ending with: 1234</p>

                    <div className="shoptotal_container">
                        <p>Subtotal: API call to get shop total</p>

                        {/*This is what will grab the data from the backend*/}
                        {/*{setReceiptData.shopTotal}*/}
                    </div>
                    <p>Shopping Total: $132.00</p>

                    <div className="staytotal_container">
                        <p>Cost of Stay: API call to get room costs</p>

                        {/*This is what will grab the data from the backend*/}
                        {/*{setReceiptData.shopTotal}*/}
                    </div>
                    <p>Reservation Cost: $1500.00</p>


                    <div className="tax_container">
                        <p>Estimated Tax: API call to get calculated tax</p>

                        {/*This is what will grab the data from the backend*/}
                        {/*{setReceiptData.taxEstimate}*/}
                    </div>
                    <p>Estimated tax: $23.60</p>

                    <div className="totalbill_container">
                        <p>Total Bill: API call to get calculated total with tax</p>

                        {/*This is what will grab the data from the backend*/}
                        {/*{setReceiptData.total}*/}
                    </div>
                    <p>Total: $1523.60</p>


                    <button className='payTotalBillButton' onClick={onButtonClick}>Pay Now</button>
                </div>
            </div>
        </div>
    );

}

export default ViewBill