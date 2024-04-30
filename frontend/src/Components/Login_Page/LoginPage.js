import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./LoginPage.css"
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
const LoginPage = (props) =>{
    const [error, setError] = useState(false)
    const [username, setUsername] = useState([])
    const [password, setPassword] = useState([])
    const [response, setResponse] = useState([])
    const navigate = useNavigate();

    const onButtonClick = () => {
        console.log(username);
        console.log(password);

        const payload = [
            username,
            password
        ]

        axios.post('http://localhost:8080/api/auth/login', payload)
            .then(response =>{
                console.log(response);
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
                }
                return response.data;
            }).then(data => {
                console.log(data[1]);
                // setResponse(response)
                if(data[1] == "CLERK"){

                    navigate('/clerk-center');


                }else if(data[1] == "ADMIN"){
                    navigate('/admin-center');
                }else{

                }
            }).catch(error => {
                console.log("Error with authentication" + error);
                setError(true);
            });
        };


    return (
        <div>
            <Layout/>
            <div className="boxLoginPage">
                <div className="loginAligner">
                    <h1>Hotel Bearifornia Login Page</h1>
                    <input
                        //value={email}
                        placeholder="Enter your username here"
                        className={'inputBoxLogin'}
                        onChange={evt => setUsername(evt.target.value)}
                    />
                    <input
                        //value={email}
                        placeholder="Enter your password here"
                        className={'inputBoxLogin'}
                        type="password"
                        onChange={evt => setPassword(evt.target.value)}
                    />
                    <input className={'inputButton'} type="buttonLogin" onClick={onButtonClick} value={'Log in'}/>
                    <Link to='/create-account'>
                        <input className={'inputButton'} type="button2Login" value={'New Account'}/>
                    </Link>
                </div>
            </div>

            {error && (
                <h1 className='login-failed'>Login Failed: Username or Password was incorrect</h1>
            )}


        </div>
    );

}

export default LoginPage