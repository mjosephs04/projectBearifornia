
import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'
import "./LoginPage.css"
import {Link} from "react-router-dom";
const LoginPage = (props) =>{
    const [usernameError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const onButtonClick = () => {
        //PUT THE LOGIN METHOD HERE MY BOAAAAA
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
                />
                <label className="errorLabel">{usernameError}</label>
            </div>
            <br/>
            <div className='inputContainer'>
                <input
                    //value={email}
                    placeholder="Enter your password here"
                    className={'inputBox'}
                    type="password"
                />
                <label className="errorLabel">{passwordError}</label>
            </div>
            <br/>
            <div className={'inputContainer'}>
                <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Log in'}/>
            </div>
            <div className={'inputContainer'}>
                <Link to='/create-account'>
                    <input className={'inputButton'} type="button2"  value={'New Account'}/>
                </Link>
            </div>


        </div>
    );

}

export default LoginPage