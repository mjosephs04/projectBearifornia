import React, {useState} from "react";
import axios from 'axios'
import './DateSelector.css'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const DateSelector = (props) => {
    const date = new Date();
    const [checkInDate, setCheckInDate] = useState('');
    const [checkOutDate, setCheckOutDate] = useState('');
    const [selectedRoom, setSelectedRoom] = useState('single');
    const [smoking, setSmoking] = useState(false);

    const checkAvailability = () => {
        console.log(props.parameter)
        const payload = [
            smoking,
            selectedRoom,
            props.parameter,
            checkInDate,
            checkOutDate
        ]
        console.log(payload);
        axios.post('http://localhost:8080/api/reservations/checkForRooms', payload)
            .then(response =>{
                console.log(response);
                return response;
            })
    }

    return(
        <div>
            <p className='check-in-label'>Check-in Date:</p>
            <div className='check-in-date'>
                <DatePicker wrapperClassName='date-test1' selected={date} onChange={date => setCheckInDate(date)}/>
            </div>

            <p className='check-out-label'>Check-out Date:</p>
            <div className='check-out-date'>
                <DatePicker selected={date} onChange={date => setCheckOutDate(date)}/>
            </div>

            <div>
                <label className='select-room-label'>Select Room Type:</label>
                <select className="room-select-dropdown" value={selectedRoom} onChange={change => setSelectedRoom(change.target.value)}>
                    <option value='single'>Single</option>
                    <option value='double'>Double</option>
                    <option value='family'>Family</option>
                </select>
            </div>

            <div className='smoking-picker'>
                <label>
                    <input
                        type="radio"
                        name="options"
                        value="smoking"
                        onClick={event => setSmoking(true)}
                    />
                    Smoking
                </label>
                <label>
                    <input
                        type="radio"
                        name="options"
                        value="no-smoking"
                        onClick={event => setSmoking(false)}
                    />
                    Non-Smoking
                </label>
            </div>

            <div className='button-container'>
                <button onClick={checkAvailability}>Check Availability</button>
            </div>


        </div>
    );
}

export default DateSelector