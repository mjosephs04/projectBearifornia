import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import './Component.css'

const Component =  () => {
    // State to store the fetched data
    const [jsonData, setJsonData] = useState([]);

    // Function to fetch data from the API
    const fetchData = () => {
        axios.get('http://ec2-18-188-149-151.us-east-2.compute.amazonaws.com:8080/api/runFunction')
            .then(response => {
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                return response.data;
            })
            .then(data => {
                // console.log(data);
                setJsonData(data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    };

    // Fetch data when the component mounts
    useEffect(() => {
        fetchData();
    }, []); // Empty dependency array ensures useEffect runs only once on mount

    return (
        <div className="table-container">
            <h2>Data Table</h2>
            <table className="table">
                <thead>
                <tr>
                    <th>Room Number</th>
                    <th>cost</th>
                    <th>roomType</th>
                    <th>numBeds</th>
                    <th>quality</th>
                    <th>bedType</th>
                    <th>smoking</th>
                </tr>
                </thead>
                <tbody>
                {jsonData.map((item, index) => (
                    <tr key={index}>
                        <td>{item.roomNumber}</td>
                        <td>{item.cost}</td>
                        <td>{item.typeOfRoom}</td>
                        <td>{item.numOfBeds}</td>
                        <td>{item.qualityLevel}</td>
                        <td>{item.smokingAllowed}</td>
                        <td>{item.smokingStatus}</td>

                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Component;
