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
            <br/>
            <div className='inputContainer'>

                <input
                    //value={email}
                    placeholder="Enter your username here"
                    className={'inputBox'}
                    onChange={input => setUsername(input.target.value)}
                />
            </div>
            <br/>
            <div className='inputContainer'>
                <input
                    //value={email}
                    placeholder="Enter your password here"
                    className={'inputBox'}
                    type="password"
                    onChange={input => setPassword(input.target.value)}
                />
            </div>
            <br/>
            <div className={'inputContainer'}>
                <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Create account'}/>
            </div>


        </div>
    );

}

export default CreateAccount