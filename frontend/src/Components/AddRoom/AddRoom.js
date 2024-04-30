import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./AddRoom.css"
import {Link, useNavigate} from "react-router-dom";
const AddRoom = (props) =>{

    const [error, setError] = useState(false)
    const [roomNumber, setRoomNumber] = useState([])
    const [roomCost, setRoomCost] = useState([])
    const [roomType, setRoomType] = useState([])
    const [numBeds, setNumBeds] = useState([])
    const [qualityLevels, setQualityLevel] = useState([])
    const [bedType, setBedType] = useState([])
    const [smokingStatus, setSmokingStatus] = useState([])
    const navigate = useNavigate();

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
            <br/>


            <div className='addRoomContainer_01'>
                <label className="LabelStyle_01">

                    Select Room Type

                    <select value={roomType} className="selectStyle_01" onChange={evt =>setRoomType(evt.target.value)}>

                        <option value="Nature Retreat">Nature Retreat</option>

                        <option value="Urban Elegance">Urban Elegance</option>

                        <option value="Vintage Charm">Vintage Charm</option>

                    </select>

                </label>
            </div>


            <br/>
            <br/>


            <div className='addRoomContainer_02'>
                <label className="LabelStyle_02">

                    Select Number of Beds

                    <select value={numBeds} className="selectStyle_02" onChange={evt =>setNumBeds(evt.target.value)}>

                        <option value="1">One Bed</option>

                        <option value="2">Two Beds</option>

                    </select>

                </label>
            </div>


            <br/>
            <br/>


            <div className='addRoomContainer_03'>
                <label className="LabelStyle_03">

                    Select Quality Level

                    <select value={qualityLevels} className="selectStyle_03" onChange={evt =>setQualityLevel(evt.target.value)}>

                        <option value="Executive Level">Executive Level</option>
                        <option value="Business Level">Business Level</option>
                        <option value="Comfort Level">Comfort Level</option>
                        <option value="Economy Level">Economy Level</option>

                    </select>

                </label>
            </div>

            <br/>
            <br/>


            <div className='addRoomContainer_04'>
                <label className="LabelStyle_04">

                    Select Bed Type

                    <select value={bedType} className="selectStyle_04" onChange={evt =>setBedType(evt.target.value)}>

                        <option value="Twin">Twin</option>
                        <option value="Full">Full</option>
                        <option value="Queen">Queen</option>
                        <option value="King">Kind</option>

                    </select>

                </label>
            </div>



            <br/>
            <br/>


            <div className='addRoomContainer_05'>
                <label className="labelStyle_05">
                    Select Smoking Status
                    <select value={smokingStatus} className="selectStyle_05" onChange={evt =>setSmokingStatus(evt.target.value)}>
                        <option value="Smoking">Smoking</option>
                        <option value="No Smoking">No Smoking</option>
                    </select>
                </label>
            </div>


            <br/>
            <br/>


            <button className='add-room-button' onClick={onButtonClick}>Add Room</button>

        </div>
    );

}

export default AddRoom