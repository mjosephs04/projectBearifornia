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
    const [roomNumber, setRoomNumber] = useState(-1);
    const [reservationData, setReservationData] = useState([])
    const [room, setRoom] = useState([])

    const onButtonClick = () => {
        const payload = [
        ]
        console.log(payload);
        axios.post('http://localhost:8080/api/reservations/updateRes', payload)
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
            <div className="modifyReservationContainer">
                <h1>Modify reservation</h1>
                <h2>Room Number: {room.roomNumber}</h2>
                <div className="form-group">
                    <label htmlFor="end-date">Start Date:</label>
                    <DatePicker
                        placeholderText={reservationData.startDay}
                        selected={checkInDate}
                        onChange={date => setCheckInDate(date)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="end-date">End Date:</label>
                    <DatePicker
                        placeholderText={reservationData.endDay}
                        selected={checkOutDate}
                        onChange={date => setCheckOutDate(date)}
                    />
                </div>
                <Link to='/confirmation'>
                    <button className='modifyReservationButton' >Modify Reservation</button>
                </Link>

            </div>
        </div>
    );

}

export default ModifyReservation