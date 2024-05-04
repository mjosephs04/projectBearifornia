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
    const [reservationData, setReservationData] = useState([]);
    const [room, setRoom] = useState([]);


    // const handleCheckInDateChange = (date, index) => {
    //     const newCheckInDates = [...checkInDates];
    //     newCheckInDates[index] = date;
    //     setCheckInDates(newCheckInDates);
    // };
    //
    // const handleCheckOutDateChange = (date, index) => {
    //     const newCheckOutDates = [...checkOutDates];
    //     newCheckOutDates[index] = date;
    //     setCheckOutDates(newCheckOutDates);
    // };

    const onButtonClick = (data, room) => {
        // Parsing dates to yyyy-mm-dd format
        var parsedStartDate = new Date(checkInDate).toISOString().split('T')[0];
        var parsedEndDate = new Date(checkOutDate).toISOString().split('T')[0];
        var roomNum = room.roomNumber.toString();

        console.log('New Start Date:', parsedStartDate);
        console.log('New End Date:', parsedEndDate);
        const payload = [
            parsedStartDate,
            parsedEndDate,
            roomNum,
            data.startDay,
            data.endDay
        ]
        console.log(payload);
        axios.patch('http://localhost:8080/api/reservations/updateRes', payload)
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
        axios.get('http://localhost:8080/api/reservations/showAllMyReservations')
            .then(response =>{
                if(response.status !== 200){
                    throw Error("Network error");
                }
                setReservationData(response.data);
                response.data.forEach()
                setRoom(response.data.room);
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
            <div className="centerHeader">
                <h1>Modify reservation</h1>
            </div>
            {reservationData.map((data, index) => (
                <div key={index} className="modifyReservationContainer">
                    <h2>Room Number: {data.room.roomNumber}</h2>
                    <p>Start Date: {data.startDay}</p>
                    <p>End Date: {data.endDay}</p>
                    <div className="form-group">
                        <label htmlFor={`start-date-${index}`}>Modify Start Date:</label>
                        <DatePicker
                            placeholderText={data.startDay}
                            selected={checkInDate}
                            onChange={date => setCheckInDate(date)}
                            id={`start-date-${index}`}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor={`end-date-${index}`}>Modify End Date:</label>
                        <DatePicker
                            placeholderText={data.endDay}
                            selected={checkOutDate}
                            onChange={date => setCheckOutDate(date)}
                            id={`end-date-${index}`}
                        />
                    </div>
                    <Link to='/confirmation'>
                        <button className='modifyReservationButton' onClick={() => onButtonClick(data, data.room)}>Modify Reservation</button>
                    </Link>
                </div>
            ))}
            {/*<div className="modifyReservationContainer">*/}
            {/*    <h2>Room Number: {room.roomNumber}</h2>*/}
            {/*    <p>Start Date: {reservationData.startDay}</p>*/}
            {/*    <p>End Date: {reservationData.endDay}</p>*/}
            {/*    <div className="form-group">*/}
            {/*        <label htmlFor="start-date">Modify Start Date:</label>*/}
            {/*        <DatePicker*/}
            {/*            placeholderText={reservationData.startDay}*/}
            {/*            selected={checkInDate}*/}
            {/*            onChange={date => setCheckInDate(date)}*/}
            {/*        />*/}
            {/*    </div>*/}
            {/*    <div className="form-group">*/}
            {/*        <label htmlFor="end-date">Modify End Date:</label>*/}
            {/*        <DatePicker*/}
            {/*            placeholderText={reservationData.endDay}*/}
            {/*            selected={checkOutDate}*/}
            {/*            onChange={date => setCheckOutDate(date)}*/}
            {/*        />*/}
            {/*    </div>*/}
            {/*    <Link to='/confirmation'>*/}
            {/*        <button className='modifyReservationButton' onClick={onButtonClick}>Modify Reservation</button>*/}
            {/*    </Link>*/}
            {/*</div>*/}
        </div>
    );

}

export default ModifyReservation