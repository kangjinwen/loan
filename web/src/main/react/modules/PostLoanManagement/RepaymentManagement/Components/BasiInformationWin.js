//查看基本信息
import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
import BorrowingNeeds from '../../../CommonForm/BorrowingNeeds';
const NavLine = require("./../../../utils/NavLine");
var NewEvaOrg = require("../../../CommonForm/NewEvaOrg");
var NewQueryOrg = require("../../../CommonForm/NewQueryOrg");
var BasiInformation = require("../../../CommonForm/BasiInformation");
var HousingInformation = require("../../../CommonForm/HousingInformation");
var UploadPhoto = require("../../../CommonForm/UploadPhoto");
var UploadPic = require("../../../CommonForm/UploadPic");
var PersonnelType = require("../../../CommonForm/PersonnelType");
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var AddlWin = React.createClass({
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
    },
    navLineData1: {
        "房产信息": "#b1",
        "借款需求": "#b4",
    },
    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = (
            <button key="back" className="ant-btn" onClick={this.handleCancel}>关 闭</button>
        )
            ;
        var idData = props.id
        var canEditBasicInfo = props.canEditBasicInfo;
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="基本信息" key="1">
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData1) }>
                                <div className="col-22 navLine-wrap-left" >
                                    <div id="b1">
                                        <h2>房产信息</h2>
                                        <HousingInformation ref="HousingInformation" canEdit={canEditBasicInfo}/> </div>
                                    <div id="b4">
                                        <h2>借款需求</h2>
                                        <BorrowingNeeds ref="BorrowingNeeds" canEdit={false} record={props.record}/>
                                    </div>
                                </div>
                                <div className="navLine-wrap-right" >
                                    <NavLine navLineData={this.navLineData1} activeItem="#b1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default AddlWin;