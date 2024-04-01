
import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'

const NatureRetreat = () => {

    // State to store the fetched data
    const [listingData, setListingData] = useState([]);

    // Function to fetch data from the API
    const fetchData = () => {
        axios.get('http://localhost:8080/rooms/nature-retreat')
            .then(response => {
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                return response.data;
            })
            .then(data => {
                console.log(data);
                setListingData(data);
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
        <div>
            <Layout/>

            <div className='listing-name'>
                {listingData.name}
            </div>

            <div className='listing-name'>
                {listingData.description}
            </div>

            <div className='listing-name'>
                {listingData.cost}
            </div>


        </div>
    );

}

export default NatureRetreat;