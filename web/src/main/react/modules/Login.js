import React from 'react'
import {
    Button,
    Form,
    Input,
    Select,
    Checkbox,
    Modal
} from 'antd';
import './login.css';
//var md5 = require('js-md5');
const FormItem = Form.Item;
const Option = Select.Option;
let Login = React.createClass({
    getInitialState() {
        return {
            ischecked: false,
            roles: [{
                id: '请选择角色',
                name: '请选择角色'
            }]
        }
    },
    handleSubmit(e) {
        e.preventDefault();
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                console.log('Errors in form!!!');
                return;
            }
            var params = values;
            //params.password = md5(values.password);
            if (params.roleId == "请选择角色") {
                Utils.ajaxData({
                    url: '/getByUserPassRolesList.htm',
                    contentType: 'application/x-www-form-urlencoded',
                    data: {
                        username: values.username,
                        password: values.password
                    },
                    callback: (result) => {
                        if (result.code == 200) {
                            
                            params.roleId = result.msg[0].id;
//                          params.roleId = '1';
                            this.login(params);
                        }
                        else {
                            Modal.error({
                                title: result.msg,
                                onOk: () => {
                                    this.setState({
                                        roles: [{
                                            id: '请选择角色',
                                            name: '请选择角色'
                                        }]
                                    })
                                }
                            });
                        }
                    }
                });
            }
            else {
                //params.roleId = this.state.roles[0].id;
                this.login(params);
            }

        });
    },
    login(params) {
        Utils.ajaxData({
            url: '/ajaxLoginProcess.htm',
            contentType: 'application/x-www-form-urlencoded',
            data: params,
            callback: function (result) {
                if (result.code == 200) {
                    if (params.isRembPwd) {
                        Cookies.set("username", params.username); 
                    } else {
                        Cookies.set("username", ""); 
                    }
                    localStorage.isLogin = true;
                    localStorage.Token = result.Token;
                    location.reload();
                } else {
                    Modal.error({
                        title: result.msg
                    });
                }
            }
        });
    },
    getRolesList(e, callback) {
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                console.log('Errors in form!!!');
                return;
            }
            var formData = this.props.form.getFieldsValue();
            if (e) {
                formData[e.target.name] = e.target.value;
            }

            Utils.ajaxData({
                url: '/getByUserPassRolesList.htm',
                contentType: 'application/x-www-form-urlencoded',
                data: {
                    username: formData.username,
                    password: formData.password
                },
                callback: (result) => {
                    if (result.code == 200) {
                        this.setState({
                            roles: result.msg
                        })
                    }
                    else {
                        if(!this.state.ischecked){//验证是否已经弹出错误提示框，防止二次弹出
                            Modal.error({
                                title: result.msg,
                                onOk: () => {
                                    this.setState({
                                        roles: [{
                                            id: '请选择角色',
                                            name: '请选择角色'
                                        }],
                                        ischecked:false 
                                    });
                                },
                            });
                            this.setState({
                                ischecked:true 
                            });
                        } 
                    }
                }
            });
        });
    },
    getRoleOptions(roles) {
        return roles.map((item, i) => {
            return <Option key={item.id}>{item.name}</Option>
        })
    },
    componentDidMount() {
        var username = Cookies.get("username"); 
        if (username) {
            this.props.form.setFieldsValue({ username });
            //this.getRolesList(); 
        }
    },
    render() { 
        const {
            getFieldProps
        } = this.props.form;
        return (
            <div>
                <div className="g-hd">
                    <div className="m-hd f-cb">
                        <span className="u-logo"></span>
                    </div>
                </div>
                <div className="g-loginbox">
                    <div className="g-bd">
                        <div className="m-loginbg" style={{ height: document.body.clientHeight - 46 }}>
                        </div>
                        <div className="m-bgwrap" style={{ cursor: "pointer" }}></div>
                        <div className="m-loginboxbg" ></div>
                        <div className="m-loginbox">
                            <div className="lbinner" id="J_body_bg">
                                <div className="login-form">
                                    <div className="login-hd">用户登录 <span className="ml10">User Login</span></div>
                                    <div className="login_input">
                                        <Form inline-block onSubmit={this.handleSubmit} form={this.props.form}>
                                            <FormItem hasFeedback> 
                                                <Input type="text" className="ipt ipt-user" name="username" autoComplete="off"
                                                    {...getFieldProps('username', {
                                                        onBlur: this.getRolesList,
                                                        rules: [{
                                                            required: true,
                                                            message: '请输入账户名'
                                                        }],
                                                        trigger: 'onBlur'
                                                    })
                                                    }
                                                    placeholder = "账户名" />
                                            </FormItem>
                                            <FormItem > 
                                                <Input type="password" className="ipt ipt-pwd" name="password" autoComplete="off"
                                                    {...getFieldProps('password', {
                                                        onBlur: this.getRolesList,
                                                        rules: [{
                                                            required: true,
                                                            whitespace: false,
                                                            message: '请输入密码'
                                                        }],
                                                        trigger: 'onBlur'
                                                    })
                                                    }
                                                    placeholder="密码"/>
                                            </FormItem>
                                            <FormItem >
                                                <Select id="select" placeholder="请选择角色"   className="input_role"  {...getFieldProps('roleId', { initialValue: "请选择角色" }) } >
                                                    {this.getRoleOptions(this.state.roles) }
                                                </Select>
                                            </FormItem>
                                            <FormItem>
                                                <Checkbox {...getFieldProps('isRembPwd', { initialValue: 1 }) } defaultChecked={true} className="rem-checkbox">记住账户</Checkbox>
                                            </FormItem>
                                            <Button type="primary" size="large" className="ant-input u-loginbtn" htmlType="submit">登录</Button>
                                        </Form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
})

Login = Form.create()(Login);
export default Login;