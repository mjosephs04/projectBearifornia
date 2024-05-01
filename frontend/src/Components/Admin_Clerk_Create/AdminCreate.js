import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./AdminCreate.css"
const AdminCreate = (props) =>{
    const [name, setName] = useState('')
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const onButtonClick = () => {
        const payload = [
            name,
            username,
            password,
            "CLERK"
        ]
        console.log(name);
        console.log(username);
        console.log(password);
        console.log(payload);

        axios.post('http://localhost:8080/api/register/createClerk', payload)
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
            <div className='centerHeader'>
                <h1>Clerk Creation</h1>
            </div>
            <div className='inputContainer_AdminCreate_01'>

                <input
                    //value={email}
                    placeholder="Enter legal name"
                    className={'inputBox_AdminCreate'}
                    onChange={input => setName(input.target.value)}
                />
            </div>
            <div className='inputContainer_AdminCreate_01'>

                <input
                    //value={email}
                    placeholder="Enter clerk username"
                    className={'inputBox_AdminCreate'}
                    onChange={input => setUsername(input.target.value)}
                />
            </div>
            <br/>
            <div className='inputContainer_AdminCreate_01'>
                <input
                    //value={email}
                    placeholder="Enter default password"
                    className={'inputBox_AdminCreate'}
                    type="password"
                    onChange={input => setPassword(input.target.value)}
                />
            </div>
            <br/>
            <div className={'inputContainer_AdminCreate_01'}>
                {/*<input className={'inputButton'} type="button" onClick={onButtonClick} value={'Create account'}/>*/}
                <button className='button_AdminCreate_01' onClick={onButtonClick}>Create Clerk Account</button>
            </div>


        </div>
    );

}

export default AdminCreate