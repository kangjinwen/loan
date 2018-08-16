import React from 'react';
import {
    Button,
} from 'antd';
var RealEstate = require("../../../CommonFormMJS/RealEstate");
var MarriageInformation = require("../../../CommonFormMJS/MarriageInformation");
var CreditInformat = require("../../../CommonFormMJS/CreditInformat");
var RiskControl = require("../../../CommonFormMJS/RiskControl");
var UploadPhoto = require("../../../CommonFormMJS/UploadPhoto");
var BorrowFace = require("../../../CommonFormMJS/BorrowFace");
const NavLine = require("./../../../utils/NavLine");
var Tab4 = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            idData: selectRecord ? selectRecord.id : null,
            borrowId: selectRecord ? selectRecord.id : null,
            formData: {}
        };
    },
    navLineData3: {
        "房产信息": "#a1",
        "婚姻信息": "#a2",
        "征信信息": "#a3",
        "风控信息": "#a4",
        "借款需求": "#a5",
    },
    componentWillReceiveProps(nextProps) {
        if (nextProps.record && nextProps.record != this.props.record) {
            // if (nextProps.visible == false) {
            //     this.refs.RealEstate.resetFields();
            //     this.refs.MarriageInformation.resetFields();
            //     this.refs.CreditInformat.resetFields();
            //     this.refs.RiskControl.resetFields();
            //     this.refs.BorrowFace.resetFields();
            // } else {
            this.refs.RealEstate.resetFields();
            this.refs.MarriageInformation.resetFields();
            this.refs.CreditInformat.resetFields();
            this.refs.RiskControl.resetFields();
            this.refs.BorrowFace.resetFields();
            this.queryFormData(nextProps.record);
            // }
        }
    },
    componentDidMount() {
        if (this.props.record) {
            this.queryFormData(this.props.record);
        }
    },
    queryFormData(record) {
        var borrowId = this.props.borrowId
        Utils.ajaxData({
            url: '/modules/audit/HousFaceTrialAction/getFaceAuditInfo.htm',
            data: {
                processInstanceId: record.processInstanceId
            },
            callback: (result) => {
                var housFaceTrial = result.data.housFaceTrial;
                var housMarriageInformation = result.data.housMarriageInformation;
                var housControlInformation = result.data.housControlInformation

                if (housFaceTrial) {
                    if (housFaceTrial.propertyProperties) {
                        housFaceTrial.propertyProperties = String(housFaceTrial.propertyProperties);
                    }
                    if (housFaceTrial.propertyListedProof) {
                        housFaceTrial.propertyListedProof = String(housFaceTrial.propertyListedProof);
                    } else {
                        housFaceTrial.propertyListedProof = null
                    }
                    if (housFaceTrial.planningPurposes) {
                        housFaceTrial.planningPurposes = String(housFaceTrial.planningPurposes);
                    }
                    // else {
                    //     housFaceTrial.planningPurposes = null
                    // }
                    if (housFaceTrial.keyDiskQuery) {
                        housFaceTrial.keyDiskQuery = String(housFaceTrial.keyDiskQuery);
                    } else {
                        housFaceTrial.keyDiskQuery = null
                    }
                }
                if (housMarriageInformation) {
                    if (housMarriageInformation.maritalStatus) {
                        housMarriageInformation.maritalStatus = String(housMarriageInformation.maritalStatus);
                        var housMarriageInformation = result.data.housMarriageInformation;
                        housMarriageInformation["documentsTime" + housMarriageInformation.maritalStatus] = housMarriageInformation["documentsTime"];
                    }
                }
                if (housControlInformation) {
                    housControlInformation.followEnterprisesCategory = String(housControlInformation.followEnterprisesCategory);
                }
                result.data.borrow.singleRate = result.data.borrow.singleRate * 1000000 / 10000;
                result.data.borrow.repaymentRate = result.data.borrow.repaymentRate * 1000000 / 10000
                this.refs.BorrowFace.setFieldsValue(result.data.borrow);
                //this.refs.BorrowFace.setFieldsValue({id:borrowId})
                this.refs.RealEstate.setFieldsValue(result.data.housFaceTrial);
                // this.refs.RealEstate.setFieldsValue({propertyAddressId:result.data.housOritoInformation.propertyAddressId});
                // this.refs.RealEstate.setFieldsValue({propertyAddress:result.data.housOritoInformation.propertyAddress});
                // this.refs.RealEstate.setFieldsValue({propertyProperties:result.data.housPropertyInformation.propertyProperties});

                this.refs.MarriageInformation.setFieldsValue(housMarriageInformation);
                this.refs.CreditInformat.setFieldsValue(result.data.housCreditInformation);
                this.refs.RiskControl.setFieldsValue(result.data.housControlInformation);
            }
        });
    },
    render() {
        var props = this.props;
        return (
            <div style={{ position: "relative" }}>
                <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData3) }>
                    <div className="col-22 navLine-wrap-left" >
                        <div id="a1">
                            <h2>房产信息</h2>
                            <RealEstate ref="RealEstate" canEdit={props.canEdit} record={props.record}  selectRecord={props.record}/>
                        </div>
                        <div id="a2" style={{ marginTop: "10px" }}>
                            <h2>婚姻信息</h2>
                            <MarriageInformation ref="MarriageInformation" canEdit={props.canEdit} />
                            <UploadPhoto style={{ marginLeft: 25 }} title="婚姻证明" btype="MARRIAGE" canEdit={props.canEdit} selectRecord={props.record}/>
                        </div>
                        <div id="a3" style={{ marginTop: "10px" }}>
                            <h2>征信信息</h2>
                            <CreditInformat ref="CreditInformat" canEdit={props.canEdit} />
                            <UploadPhoto  title="征信资料" btype="CREDIT" canEdit={props.canEdit} selectRecord={props.record}/>
                        </div>
                        <div id="a4" style={{ marginTop: "10px" }}>
                            <h2>风控信息</h2>
                            <RiskControl ref="RiskControl" canEdit={props.canEdit} record={props.record} hideModal={this.props.hideModal}/>
                            <UploadPhoto  title="风控单" btype="RISK" canEdit={props.canEdit} selectRecord={props.record}/>
                        </div>
                        <div id="a5">
                            <h2>借款需求</h2>
                            <BorrowFace ref="BorrowFace" canEdit={props.canEdit} record={props.record}/>
                        </div>
                    </div>
                    <div className="navLine-wrap-right" >
                        <NavLine navLineData={this.navLineData3} activeItem="#a1" ref="NavLine"/>
                    </div>
                </div>
            </div>
        )
    }
});
export default Tab4;