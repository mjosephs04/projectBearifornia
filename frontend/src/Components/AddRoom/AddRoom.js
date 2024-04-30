import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./AddRoom.css"
import {Link} from "react-router-dom";
const AddRoom = (props) =>{

    const [error, setError] = useState(false)
    const [roomNumber, setRoomNumber] = useState([])
    const [roomCost, setRoomCost] = useState([])
    const [roomType, setRoomType] = useState([])
    const [numBeds, setNumBeds] = useState([])
    const [qualityLevels, setQualityLevel] = useState([])
    const [bedType, setBedType] = useState([])
    const [smokingStatus, setSmokingStatus] = useState([])

    const onButtonClick = () => {
        const payload = [
            roomNumber,
            roomCost,
            roomType,
            numBeds,
            qualityLevels,
            bedType,
            smokingStatus
        ]
        console.log(payload);
        axios.post('http://localhost:8080/api/addRoom', payload)
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
            <br/>

            <input
                className='room-number-input'
                placeholder="Enter Room Number"
                className={'inputBox'}
                onChange={evt => setRoomNumber(evt.target.value)}
            />
            <br/>


            <input
                className='room-cost-input'
                placeholder="Enter Room Cost"
                className={'inputBox'}
                onChange={evt => setRoomCost(evt.target.value)}
            />

            <br/>

                <input
                    //value={email}
                    placeholder="Enter Room Type"
                    className={'inputBox'}
                    onChange={evt => setRoomType(evt.target.value)}
                />
            <br/>

                <input
                    //value={email}
                    placeholder="Enter Number of Beds"
                    className={'inputBox'}
                    onChange={evt => setNumBeds(evt.target.value)}
                />

            <br/>

                <input
                    //value={email}
                    placeholder="Enter Quality Level"
                    className={'inputBox'}
                    onChange={evt => setQualityLevel(evt.target.value)}
                />

            <br/>

                <input
                    //value={email}
                    placeholder="Enter Bed Type"
                    className={'inputBox'}
                    onChange={evt => setBedType(evt.target.value)}
                />
            <br/>

                <input
                    //value={email}
                    placeholder="Smoking (true or false)"
                    className={'inputBox'}
                    onChange={evt => setSmokingStatus(evt.target.value)}
                />

            <br/>

            <button className='add-room-button' onClick={onButtonClick}>Add Room</button>

        </div>
    );

}

export default AddRoom