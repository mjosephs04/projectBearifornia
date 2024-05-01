import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./ModifyReservation.css"
import {Link, useNavigate} from "react-router-dom";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
const ModifyReservation = (props) =>{
    const date = new Date();
    const [checkInDate, setCheckInDate] = useState('');
    const [checkOutDate, setCheckOutDate] = useState('');
    const navigate = useNavigate();

    const onButtonClick = () => {
        const payload = [
        ]
        console.log(payload);
        axios.post('http://localhost:8080/api/notARealFunction', payload)
            .then(response =>{
                if(response.status !== 200){
                    throw Error("Network error");
                }
                if(response.status === 200){
                    navigate('/confirmation');
                }
                console.log(response);
                return response.data;
            }).catch(error => {
            console.log(error);
        })
    };


    return (
        <div>
            <Layout/>
            <div className="modifyReservationContainer">
                <h1>Modify reservation</h1>
                <h2>Reservation #1</h2>
                <div className="form-group">
                    <label htmlFor="end-date">Start Date:</label>
                    <DatePicker
                        placeholderText="mm-dd-yyyy"
                        selected={checkInDate}
                        onChange={date => setCheckInDate(date)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="end-date">End Date:</label>
                    <DatePicker
                        placeholderText="mm-dd-yyyy"
                        selected={checkOutDate}
                        onChange={date => setCheckOutDate(date)}
                    />
                </div>
                <button className='modifyReservationButton' onClick={onButtonClick}>Modify Reservation</button>
            </div>
        </div>
    );

}

export default ModifyReservation