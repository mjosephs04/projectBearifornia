import React, {useState} from "react";
import './DateSelector.css'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const DateSelector = () => {
    const [date, setDate] = useState(new Date());

    const [selectedRoom, setSelectedRoom] = useState('single');

    const handleRoomChange = (e) => {
        setSelectedRoom(e.target.value);
    };
    return(
        <div>
            <p className='check-in-label'>Check-in Date:</p>
            <div className='check-in-date'>
                <DatePicker wrapperClassName='date-test1' selected={date} onChange={date => setDate(date)}/>
            </div>

            <p className='check-out-label'>Check-out Date:</p>
            <div className='check-out-date'>
                <DatePicker selected={date} onChange={date => setDate(date)}/>
            </div>

            <div>
                <label className='select-room-label'>Select Room Type:</label>
                <select className="room-select-dropdown" value={selectedRoom} onChange={handleRoomChange}>
                    <option>Single</option>
                    <option>Double</option>
                    <option>Family</option>
                </select>
            </div>
        </div>
    );
}

export default DateSelector