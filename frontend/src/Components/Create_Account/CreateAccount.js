import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./CreateAccount.css"
const CreateAccount = (props) =>{
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const onButtonClick = () => {
        const payload = [
            username,
            password
        ]
        console.log(username);
        console.log(password);
        console.log(payload);

        axios.post('http://localhost:8080/api/register/createAccount', payload)
            .then(response => {
                console.log(response);
                if (response.status !== 200) {
                    throw new Error('Network response was not ok: ' + response.data);
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