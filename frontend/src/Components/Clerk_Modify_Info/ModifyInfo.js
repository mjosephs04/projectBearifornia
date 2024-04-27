import Layout from '../Layout/Layout'
import React, {useEffect, useState} from 'react'
import axios from 'axios'

import "./ModifyInfo.css"
import {Link} from "react-router-dom";
import goldChainIMG from "../../assets/GoldChain.png";

const ModifyInfo = () =>{

    return (
        <div>
            <Layout/>
            <div className='inputContainerModify'>
                <input
                    placeholder="Enter new username"
                    className={'inputBox'}
                />
            </div>
            <div className='inputContainerModify'>
                <input
                    placeholder="Enter new password"
                    className={'inputBox'}
                />
            </div>
            <div className={'inputContainerModify'}>
                <input className={'input'} type="button" value={'Save Changes'}/>
            </div>
        </div>
    );

}

export default ModifyInfo