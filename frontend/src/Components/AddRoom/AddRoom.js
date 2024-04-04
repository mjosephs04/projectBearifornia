import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./AddRoom.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
const AddRoom = (props) =>{

    const [error, setError] = useState(false)
    // const [response, setRoomNumber] = useState([])
    // const [password, setRoomCost] = useState([])
    // const [response, setRoomType] = useState([])
    // const [response, setNumBeds] = useState([])
    // const [response, setQualityLevel] = useState([])
    // const [response, setBedType] = useState([])
    // const [response, setSmokingStatus] = useState([])
    const navigate = useNavigate();

    const onButtonClick = () => {
        // ADDING LINKING WITH BACKEND
        // After a button is clicked there is a way to clear the text in the fields
        // This is what I found

        function ClearTextFieldExample() {
            const [inputValue, setInputValue] = useState(''); // Initial state is an empty string

            const handleChange = (event) => {
                setInputValue(event.target.value); // Update state with input field's current value
            };

            const handleClear = () => {
                setInputValue(''); // Clear the input field by setting its state back to an empty string
            };

            return (
                <div>
                    <input
                        type="text"
                        value={inputValue} // The input is controlled by React state
                        onChange={handleChange} // Update state upon input change
                    />
                    <button onClick={handleClear}>Clear</button> {/* On button click, clear the input */}
                </div>
            );
        }
    };


    return (
        <div>
            <Layout/>
            <br/>
            <div className='inputContainer1'>

                <input
                    //value={email}
                    placeholder="Enter Room Number"
                    className={'inputBox'}
                    //onChange={evt => setRoomNumber(evt.target.value)}
                />
            </div>
            <br/>

            <div className='inputContainer2'>
                <input
                    //value={email}
                    placeholder="Enter Room Cost"
                    className={'inputBox'}
                    //onChange={evt => setRoomCost(evt.target.value)}
                />
            </div>
            <br/>

            <div className='inputContainer3'>
                <input
                    //value={email}
                    placeholder="Enter Room Type"
                    className={'inputBox'}
                    //onChange={evt => setRoomType(evt.target.value)}
                />
            </div>
            <br/>


            <div className='inputContainer4'>
                <input
                    //value={email}
                    placeholder="Enter Number of Beds"
                    className={'inputBox'}
                    //onChange={evt => setNumBeds(evt.target.value)}
                />
            </div>
            <br/>

            <div className='inputContainer5'>
                <input
                    //value={email}
                    placeholder="Enter Quality Level"
                    className={'inputBox'}
                    //onChange={evt => setQualityLevel(evt.target.value)}
                />
            </div>
            <br/>

            <div className='inputContainer6'>
                <input
                    //value={email}
                    placeholder="Enter Bed Type"
                    className={'inputBox'}
                    //onChange={evt => setBedType(evt.target.value)}
                />
            </div>
            <br/>

            <div className='inputContainer7'>
                <input
                    //value={email}
                    placeholder="Smoking (true or false)"
                    className={'inputBox'}
                    //onChange={evt => setSmokingStatus(evt.target.value)}
                />
            </div>
            <br/>

            <div className={'inputContainer8'}>
                <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Add Room'}/>
            </div>

            {error && (
                <h1 className='add-room-failed'>Adding Room Failed: Incorrect parameters or Unavailable</h1>
            )}


        </div>
    );

}

export default AddRoom