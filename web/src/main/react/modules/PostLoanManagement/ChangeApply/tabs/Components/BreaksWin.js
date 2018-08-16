//罚息减免申请
import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
const NavLine = require("./../../../../utils/NavLine");
import Break from './Break';
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var BreaksWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            idData: selectRecord ? selectRecord.id : null,
            formData: {}
        };
    },
    handleCancel() {
        this.props.hideModal();
        this.changeTabState();
    },
    handleTabClick(key) {
        this.setState({
            activekey: key
        })
    },
    changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },

    //非空验证
    validateTab1() {
        var A = false;
        this.refs.Break.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                A = true;
            }
        })
        return A
    },
    handleOk() {
        if (!this.validateTab1()) {
            return
        }
    },
    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleOk}>
                提交
            </button>
        ];

        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1000"  footer={modalBtns} >
                <div style={{ position: "relative" }}>
                    <div className="col-22 navLine-wrap-left" >
                        <div id="s1">
                            <h2>基本信息</h2>
                            <Break ref="Break" canEdit={!props.canEdit} />
                        </div>
                    </div>
                </div>
            </Modal>
        );
    }
});
export default BreaksWin;