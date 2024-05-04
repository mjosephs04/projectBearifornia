import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./DeleteReservation.css"
import {Link, useNavigate} from "react-router-dom";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';



const DeleteReservation = (props) =>{
    const navigate = useNavigate();
    const [reservationData, setReservationData] = useState([])
    const [room, setRoom] = useState([])

    const onButtonClick = () => {
        // Parsing dates to yyyy-mm-dd format
        var roomNum = room.roomNumber.toString();


        const payload = [
            reservationData.startDay,
            reservationData.endDay,
            roomNum
        ]
        console.log(payload);
        axios.post('http://localhost:8080/api/reservations/deleteRes', payload)
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

    const getUserReservations = () => {
        axios.get('http://localhost:8080/api/reservations/showMyReservations')
            .then(response =>{
                if(response.status !== 200){
                    throw Error("Network error");
                }
                setReservationData(response.data);
                setRoom(response.data.room)
                console.log(response);
                return response.data;
            }).catch(error => {
            console.log(error);
        })
    };

    useEffect(() => {
        getUserReservations();
    }, []); // Empty dependency array ensures useEffect runs only once on mount

    return (
        <div>
            <Layout/>
            <div className="deleteReservationContainer">
                <h1>Delete reservation</h1>
                <h2>Room Number: {room.roomNumber}</h2>
                <div className="form-group">
                    <label htmlFor="start-date">Start Date: {reservationData.startDay}</label>
                </div>

                <div className="form-group">
                    <label htmlFor="end-date">End Date: {reservationData.endDay}</label>
                </div>

                <Link to='/confirmation'>
                    <button className='deleteReservationButton' onClick={onButtonClick}>Delete Reservation</button>
                </Link>
            </div>
        </div>
    );

}

export default DeleteReservation