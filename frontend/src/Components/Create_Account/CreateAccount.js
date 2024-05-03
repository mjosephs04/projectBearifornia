import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./CreateAccount.css"
import {useNavigate} from "react-router-dom";
const CreateAccount = (props) =>{
    const [name, setName] = useState('')
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate();

    const onButtonClick = () => {
        const payload = [
            name,
            username,
            password,
            "GUEST"
        ]
        const loginPayload = [
            username,
            password
        ]
        console.log(name);
        console.log(username);
        console.log(password);
        console.log(payload);

        axios.post('http://localhost:8080/api/register/createGuest', payload)
            .then(response => {
                console.log(response);
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                if(response.status === 200){
                    axios.post('http://localhost:8080/api/auth/login', loginPayload)
                        .then(response =>{
                            console.log(response);
                            if (response.status !== 200) {
                                throw new Error('Network response was not ok: ' + response.data);
                            }
                            return response.data;
                        }).then(data => {
                        console.log(data[1]);
                        // setResponse(response)
                        navigate('/guest-center');
                    }).catch(error => {
                        console.log("Error with authentication" + error);
                    });
                }
                return response.data;
            }).catch(error => {
                console.log("Error with authentication" + error);
            });
    }

    return (
        <div>
            <Layout/>
            <div className='box'>
                <div className='loginAligner'>
                    <h1>Create your Bearifornia account</h1>
                    <input
                        //value={email}
                        placeholder="Enter legal name here"
                        className={'inputBoxLogin'}
                        onChange={input => setName(input.target.value)}
                    />
                    <input
                        //value={email}
                        placeholder="Enter new username here"
                        className={'inputBoxLogin'}
                        onChange={input => setUsername(input.target.value)}
                    />
                    <input
                        //value={email}
                        placeholder="Enter your password here"
                        className={'inputBoxLogin'}
                        type="password"
                        onChange={input => setPassword(input.target.value)}
                    />
                    <input className={'inputButton'} type="buttonLogin" onClick={onButtonClick} value={'Create account'}/>
                </div>
            </div>
            <br/>


        </div>
    );

}

export default CreateAccount