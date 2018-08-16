import React from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import App from './modules/App';
import Login from './modules/Login'
import './modules/utils/Utils.js';
import reqwest from 'reqwest';
/*登录状态*/
let Home = Login;
if (localStorage.isLogin) {
    let url = '/modules/system/getUserMessage.htm';
    if (process.env.mode === 'dev') {
    	url = '/api' + url;
    }
	reqwest({
            url: url,
            method: 'get',
            success: (result) => {
                 if(result.name){
					 Home = App;
					 localStorage.isReceiveOrder = result.isReceiveOrder?result.isReceiveOrder:1;//接单设置
					 localStorage.rolename=result.rolename;
					 window.roleId=result.nid;
                  	 window.rolename=result.rolename;
				 }
				 else {
					localStorage.clear();
				 }
				 ReactDOM.render(<Home />, document.getElementById('app'));
            },
        });

}

else ReactDOM.render(<Home />, document.getElementById('app'));
