import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';

const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
const NavLine = require("./../../../utils/NavLine");
import HistoryApprove from '../../../CommonFormMJS/HistoryApprove';
var NewEvaOrg = require("../../../CommonFormMJS/NewEvaOrg");
var HousingInformation = require("../../../CommonFormMJS/HousingInformation");
var PersonnelType = require("../../../CommonFormMJS/PersonnelType");
var UploadPic = require("../../../CommonFormMJS/UploadPic");
var ProcessInformation = require("../../../CommonFormMJS/ProcessInformation");
var UploadALL = require("../../../CommonFormMJS/UploadALL");
import BorrowingNeeds from '../../../CommonFormMJS/BorrowingNeeds';
var RealEstate = require("../../../CommonFormMJS/RealEstate");
var MarriageInformation = require("../../../CommonFormMJS/MarriageInformation");
var CreditInformat = require("../../../CommonFormMJS/CreditInformat");
var RiskControl = require("../../../CommonFormMJS/RiskControl");
var BorrowFace = require("../../../CommonFormMJS/BorrowFace");
import BasiInformationMJS from './BasiInformationMJS';
var roleId = window.roleId;
var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            activekey: "1",
            formData: {},
            idData: "",
            loading: false,
            faceTrialVisiblel: true,
        };
    },
    handleCancel() {
        if (this.refs.BasiInformationMJS) {
            this.refs.BasiInformationMJS.resetFields();
        }
        if (this.refs.ProcessInformation && this.props.canEdit) {
            this.refs.ProcessInformation.resetFields();
        }
        if (this.refs.BasiInformation) {
            this.refs.BasiInformation.resetFields();
        }
        if (this.refs.HousingInformation) {
            this.refs.HousingInformation.resetFields();
        }
        if (this.refs.BorrowingNeeds) {
            this.refs.BorrowingNeeds.resetFields();
        }
        if (this.refs.PersonnelType) {
            this.refs.PersonnelType.resetFields();
        }

        this.props.hideModal();
        this.setState(this.getInitialState());
    },
    navLineData: {
        "房产信息": "#s1",
        "借款需求": "#s3",
    },
    navLineData2: {
        "房产信息": "#a1",
        "婚姻信息": "#a2",
        "征信信息": "#a3",
        "风控信息": "#a4",
        "借款需求": "#a5",
    },
    handleOk() {
        var props = this.props;
        if (props.title == "新增申请") {
            this.handleOk1();
        }
    },
    getDataSet() {
        //人员类型
        var data = this.refs.PersonnelType.getFieldsValue();
        var personnelType = [];
        data.keys.forEach(item => {
            let obj = {};
            if (data[item + "id"]) {
                obj.id = data[item + "id"];
            }
            obj.type = data[item + "type"];
            obj.personName = data[item + "personName"];
            obj.personNumber = data[item + "personNumber"];
            obj.personPhone = data[item + "personPhone"];
            personnelType.push(obj);
        });
        return { personnelType }
    },
    validateTab1() {//验证第一个tab页 
        var me = this;
        var A = false;
        var C = false;
        this.refs.HousingInformation.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                A = true;
            }
        })
        this.refs.BorrowingNeeds.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                C = true;
            }
        })
        this.handleTabClick("1");
        return A && C
    },
    transFormPersonData(data) {//人员类型
        var formData = {};
        var keys = [];
        data.forEach(item => {
            let k = item.id;//id值 需要更改
            keys.push(k);
            formData[k + 'type'] = item.type;
            formData[k + 'personName'] = item.personName;
            formData[k + 'personNumber'] = item.personNumber;
            formData[k + 'personPhone'] = item.personPhone;
            formData[k + 'id'] = item.id;
        })
        formData["keys"] = keys;
        return formData;
    },
    handleOk1() {//新增申请提交

        if (this.validateTab1()) {
            var state = this.state;
            var props = this.props;
            var creditConsultFrom = {};
            var housPropertyInformation = this.refs.HousingInformation.getFieldsValue();
            var plBorrowRequirement = this.refs.BorrowingNeeds.getFieldsValue();
            housPropertyInformation.id = this.state.idData;
            creditConsultFrom.housPropertyInformation = housPropertyInformation;
            plBorrowRequirement.repaymentRate = plBorrowRequirement.repaymentRate * 100 / 10000;//底点利率
            plBorrowRequirement.singleRate = plBorrowRequirement.singleRate * 100 / 10000;//成单利率
            var repaymentRate = plBorrowRequirement.repaymentRate;
            var singleRate = plBorrowRequirement.singleRate;
            if (singleRate < repaymentRate) {
                Modal.error({
                    title: '成单利率要大于等于底点利率',
                    onOk: () => {
                        this.handleTabClick("1");
                    }
                });
                return;
            }
            creditConsultFrom.plBorrowRequirement = plBorrowRequirement;
            creditConsultFrom.nextStep = "1";
            creditConsultFrom.commit = 1;
            creditConsultFrom.customerId = this.props.record.id;
            if (this.props.title == "新增申请") {
                creditConsultFrom.consultId = housPropertyInformation.consultId
            } else {
                creditConsultFrom.consultId = props.record.consultId
            }
            Utils.ajaxSubmit({
                me: this,
                url: '/modules/common/action/plConsultAction/addConsult.htm',
                method: 'post',
                data: {
                    creditConsultFrom: JSON.stringify(creditConsultFrom)
                },
                type: 'json',
                callback: (result) => {
                    if (result.code == 200) {
                        Modal.success({
                            title: result.msg,
                            onOk: () => {
                                this.handleCancel();
                            }
                        });
                    } else {
                        Modal.error({
                            title: result.msg,
                        });
                    }
                }
            });

        }
    },
    handleTabClick(key) {
        var selectedrecord = this.props.record;
        this.setState({
            activekey: key
        }, () => {
            if (key == 2) {
                Utils.ajaxData({
                    url: '/modules/common/PubCustomerAction/getPubCustomerByProcessInstanceId.htm',
                    data: {
                        processInstanceId: selectedrecord.processInstanceId,
                        customerId: selectedrecord.id
                    },
                    callback: (result) => {
                        this.refs.BasiInformationMJS.setFieldsValue(result.data);
                    }
                });
            }
            if (key == 3) {
                Utils.ajaxData({
                    url: '/modules/audit/HousFaceTrialAction/getFaceAuditInfo.htm',
                    data: {
                        processInstanceId: selectedrecord.processInstanceId
                    },
                    callback: (result) => {
                        var housFaceTrial = result.data.housFaceTrial;
                        var housMarriageInformation = result.data.housMarriageInformation;
                        var housControlInformation = result.data.housControlInformation
                        if (housMarriageInformation) {
                            if (housMarriageInformation.maritalStatus) {
                                housMarriageInformation.maritalStatus = String(housMarriageInformation.maritalStatus);
                            } else {
                                housMarriageInformation.maritalStatus = ""
                            }
                        }
                        if (housFaceTrial) {
                            if (housFaceTrial.propertyProperties) {
                                housFaceTrial.propertyProperties = String(housFaceTrial.propertyProperties);
                            } else {
                                housFaceTrial.propertyProperties = ""
                            }
                            if (housFaceTrial.propertyListedProof) {
                                housFaceTrial.propertyListedProof = String(housFaceTrial.propertyListedProof);
                            } else {
                                housFaceTrial.propertyListedProof = ""
                            }
                            if (housFaceTrial.planningPurposes) {
                                housFaceTrial.planningPurposes = String(housFaceTrial.planningPurposes);
                            } else {
                                housFaceTrial.planningPurposes = ""
                            }
                            if (housFaceTrial.keyDiskQuery) {
                                housFaceTrial.keyDiskQuery = String(housFaceTrial.keyDiskQuery);
                            } else {
                                housFaceTrial.keyDiskQuery = ""
                            }
                        }
                        result.data.borrow.singleRate = result.data.borrow.singleRate * 1000000 / 10000;
                        result.data.borrow.repaymentRate = result.data.borrow.repaymentRate * 1000000 / 10000
                        this.refs.BorrowFace.setFieldsValue(result.data.borrow);
                        this.refs.RealEstate.setFieldsValue(result.data.housFaceTrial);
                        if (result.data.housMarriageInformation) {
                            var housMarriageInformation = result.data.housMarriageInformation;
                            housMarriageInformation["documentsTime" + housMarriageInformation.maritalStatus] = housMarriageInformation["documentsTime"];
                        }
                        this.refs.MarriageInformation.setFieldsValue(housMarriageInformation);
                        this.refs.CreditInformat.setFieldsValue(result.data.housCreditInformation);
                        this.refs.RiskControl.setFieldsValue(result.data.housControlInformation);
                    }
                });
            }
        })
    },
    render() {
        var props = this.props;
        var state = this.state;
        var selectRecord = props.record;
        var modalBtns = [
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                提 交
            </Button>
        ];
        if (props.title == "查看贷款信息") {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
            ]
        }
        var roleId = window.roleId;
        var addForm;
        if (props.title == "新增申请") {
            addForm = (
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="房产评估" key="1">
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                                <div className="col-22 navLine-wrap-left" >
                                    <div id="s1">
                                        <h2>房产信息</h2>
                                        <HousingInformation ref="HousingInformation" canEdit={props.canEdit} record={props.record} title={props.title}/>
                                    </div>
                                    <div id="s3">
                                        <h2>借款需求</h2>
                                        <BorrowingNeeds ref="BorrowingNeeds" canEdit={props.canEdit} record={props.record} title={props.title}/>
                                    </div>
                                </div>
                                <div className="navLine-wrap-right" >
                                    <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="客户信息" key="2">
                        <BasiInformationMJS ref="BasiInformationMJS" canEdit={false} record={props.record} title={props.title}/>
                    </TabPane>
                </Tabs>
            )
        }
        else {//查看贷款信息
            var idData 
            if (props.record && props.record.processInstanceId) {
                idData = selectRecord.processInstanceId;
            }
            var tabFace = (
                <TabPane tab="复审" key="3">
                    <div style={{ position: "relative" }}>
                        <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData2) }>
                            <div className="col-22 navLine-wrap-left" >
                                <div id="a1">
                                    <h2>房产信息</h2>
                                    <RealEstate ref="RealEstate" canEdit={props.canEdit} record={props.record}  selectRecord={props.record}/>
                                </div>
                                <div id="a2" style={{ marginTop: "10px" }}>
                                    <h2>婚姻信息</h2>
                                    <MarriageInformation ref="MarriageInformation" canEdit={props.canEdit} />
                                </div>
                                <div id="a3" style={{ marginTop: "10px" }}>
                                    <h2>征信信息</h2>
                                    <CreditInformat ref="CreditInformat" canEdit={props.canEdit} />
                                </div>
                                <div id="a4" style={{ marginTop: "10px" }}>
                                    <h2>风控信息</h2>
                                    <RiskControl ref="RiskControl" canEdit={props.canEdit} record={props.record} hideModal={this.props.hideModal}/>
                                </div>
                                <div id="a5">
                                    <h2>借款需求</h2>
                                    <BorrowFace ref="BorrowFace" canEdit={props.canEdit} record={props.record}/>
                                </div>
                            </div>
                            <div className="navLine-wrap-right" >
                                <NavLine navLineData={this.navLineData2} activeItem="#s1" ref="NavLine"/>
                            </div>
                        </div>
                    </div>
                </TabPane>
            );
            addForm = (
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="房产评估" key="1">
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                                <div className="col-22 navLine-wrap-left" >
                                    <div id="s1">
                                        <h2>房产信息</h2>
                                        <HousingInformation ref="HousingInformation" canEdit={props.canEdit} record={props.record} title={props.title}/>
                                    </div>
                                    <div id="s3">
                                        <h2>借款需求</h2>
                                        <BorrowingNeeds ref="BorrowingNeeds" canEdit={props.canEdit} record={props.record} title={props.title}/>
                                    </div>
                                </div>
                                <div className="navLine-wrap-right" >
                                    <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="客户信息" key="2">
                        <BasiInformationMJS ref="BasiInformationMJS" canEdit={false} record={props.record} title={props.title}/>
                    </TabPane>
                    {tabFace}
                    <TabPane tab="附件上传" key="5">
                        <UploadALL ref="UploadALL" idData={idData} canEdit={props.canEdit} selectRecord={props.record} title={props.title}/>
                    </TabPane>
                    <TabPane tab="审批历史" key="4">
                        <HistoryApprove ref="HistoryApprove" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>

                </Tabs>
            )
        }
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                {addForm}
            </Modal>
        );
    }
});
export default ReviewWin;
