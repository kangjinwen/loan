import React from 'react';
import {Icon, Modal, Tooltip } from "antd";
var Reflux = require('reflux');
var reqwest = require('reqwest');
var AppActions = require('./actions/AppActions');
var EditPasswordWin = require('./EditPasswordWin');
var UserMessageStore = require('./stores/UserMessageStore');
const Top = React.createClass({
  mixins: [
    Reflux.connect(UserMessageStore, "userMessage")
  ],
  getInitialState() {
    return {
      userMessage: {},
      formData: {},
    };
  },
  toggleMenu() {
    this.props.toggleMenu();
  },
  handleClick(e) {
    Utils.ajaxData({
      url: '/ajaxChangeLoginProcess.htm',
      method: 'get',
      data: {
        role: e
      },
      type: 'json',
      callback: (result) => {
        location.reload()
      }
    });
  },
  signOut(e) {
    localStorage.clear();
    location.reload()
  },
  showModal() {
    this.setState({
      visible: true
    });
  },
  handleOk() {
    var me = this;
    var validation = this.refs.EditPasswordWin.validateFields;
    validation((errors, values) => {
      if (!!errors) {
        return;
      } else {         
        Utils.ajaxData({
          url: '/modules/system/modifyPassword.htm',
          method: 'post',
          data: {
            user: JSON.stringify(this.refs.EditPasswordWin.getFieldsValue())
          },
          type: 'json',
          callback: (result) => {
            if (result.code == 200) {
              Modal.success({
                title: result.msg,
                onOk() {
                  me.setState({
                    visible: false
                  }); 
                  me.signOut();
                }
              });
            }
            else {
              Modal.error({
                title: result.msg
              });
            }
          }
        });
      }
    });

  },
  handleCancel() {
    this.refs.EditPasswordWin.resetFields();
    this.setState({
      visible: false
    });
  }, 
  componentDidMount() {
    AppActions.initUserMessageStore();
  },
  render() {
    var fold = this.props.fold;
    var me = this;
    var formData = this.state.formData;
    this.props.setRoleName(this.state.userMessage.rolename);
    var modalBtns = [
      <button key="back" type="button" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
      <button key="button" type="button" className="ant-btn ant-btn-primary" loading={this.state.loading}
        onClick={this.handleOk}>
        提 交
      </button>
    ];
    var userMessage = this.state.userMessage;

    var roleList;
    if (userMessage.roleList) {
      roleList = userMessage.roleList.map((role) => {
        return <a key={role.id} onClick={this.handleClick.bind(this, role.id) }>{role.name}</a>
      });
    }
    var toggleRole = (<div> {roleList} </div>);

    return (
      <div className="main-header">
        <div className="logo">
          <span className="logo-mini"></span>
          <span className="logo-lg">logo</span>
        </div>
        <div className="navbar navbar-static-top">
          <a href="#" className="sidebar-toggle" onClick={this.toggleMenu}>
            <Icon type={`${fold ? 'menu-unfold' : 'menu-fold'}`}/><span className="system-name">房贷管理系统</span>
          </a>
          <div className="navbar-custom-menu">
            <div className="fn-right right-block">
              欢迎您，{ userMessage.name }
              <a onClick={this.signOut}>
                <Icon type="logout"/> 注销
              </a>
              <a><i className="anticon anticon-edit"></i><span style={{ display: "inline-block", marginLeft: '5px' }}
                onClick={this.showModal.bind(this, userMessage.id) }>修改密码</span></a>
                <a className="">{localStorage.rolename}</a>
              <Tooltip placement="bottom" title={toggleRole}>
                <a className="">[切换角色]</a>
              </Tooltip>
            </div>
            <Modal title={"修改密码"} visible={this.state.visible} onCancel={this.handleCancel} width="500" footer={modalBtns}>
              <EditPasswordWin ref="EditPasswordWin"/>
            </Modal>
          </div>
        </div>

      </div>
    )
  }
});
export default Top;