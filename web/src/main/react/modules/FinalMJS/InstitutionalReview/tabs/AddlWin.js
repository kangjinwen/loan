
import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
import HistoryApprove from '../../../CommonFormMJS/HistoryApprove';
import ProcessInformation from '../../../CommonFormMJS//ProcessInformation';
const NavLine = require("./../../../utils/NavLine");
import ResultApprove from '../../../CommonFormMJS/ResultApprove';
var HousingInformation = require("../../../CommonFormMJS/HousingInformation");
var BorrowingNeeds = require("../../../CommonFormMJS/BorrowingNeeds");
var Tab4 = require("./Tab4");
var UploadALL = require("../../../CommonFormMJS/UploadALL");
import BasiInformationMJS from '../../../CustomerManageMJS/CustMang/tabs/BasiInformationMJS';
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var AddlWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            idData: selectRecord ? selectRecord.id : null,
            borrowId: selectRecord ? selectRecord.id : null,
            formData: {},
            loading: false,
            resultApprove:{}
        };
    },
    handleCancel() {
        if (this.refs.BasiInformationMJS) {
            this.refs.BasiInformationMJS.resetFields();
        }
        if (this.refs.BorrowingNeeds) {
            this.refs.BorrowingNeeds.resetFields();
        }
        if (this.refs.HousingInformation) {
            this.refs.HousingInformation.resetFields();
        }
        // var tab4 = this.refs.tab4
        if (this.refs.ProcessInformation && this.props.canEdit) {
            this.refs.ProcessInformation.resetFields();
        }
        if (this.refs.HousesValueQuickly) {
            this.refs.HousesValueQuickly.resetFields();
        }
        if (this.refs.NextDoorInformation) {
            this.refs.NextDoorInformation.resetFields();
        }
        if (this.refs.DataListing) {
            this.refs.DataListing.resetFields();
        }
        if (this.refs.RealtorInformation) {
            this.refs.RealtorInformation.resetFields();
        }
        this.props.hideModal();
        this.changeTabState();
    },
    validateTab1() {//验证第一个tab页
        var BasiInformation = this.refs.BasiInformation;
        var me = this;
        var A = false;
        BasiInformation.validateFields((errors, values) => {
            if (!!errors) {
                console.log('Errors in form!!!');
                return;
            } else {
                A = true;
            }
        })
        return A
    },
    // validateTab4() {//验证第4个tab页
    //     // var tab4 = this.refs.tab4;
    //     // var RealEstate = tab4.refs.RealEstate;
    //     // var MarriageInformation = tab4.refs.MarriageInformation
    //     // var CreditInformat = tab4.refs.CreditInformat
    //     // var RiskControl = tab4.refs.RiskControl
    //     // var BorrowFace = tab4.refs.BorrowFace
    //     var me = this;
    //     var B = false;
    //     var C = false;
    //     var D = false;
    //     var E = false;
    //     var F = false;
    //     RealEstate.validateFields((errors, values) => {
    //         if (!!errors) {
    //             console.log('Errors in form!!!');
    //             return;
    //         } else {
    //             B = true;
    //         }
    //     })
    //     MarriageInformation.validateFields((errors, values) => {
    //         if (!!errors) {
    //             console.log('Errors in form!!!');
    //             return;
    //         } else {
    //             C = true;
    //         }
    //     })
    //     CreditInformat.validateFields((errors, values) => {
    //         if (!!errors) {
    //             console.log('Errors in form!!!');
    //             return;
    //         } else {
    //             D = true;
    //         }
    //     })
    //     RiskControl.validateFields((errors, values) => {
    //         if (!!errors) {
    //             console.log('Errors in form!!!');
    //             return;
    //         } else {
    //             E = true;
    //         }
    //     })
    //     BorrowFace.validateFields((errors, values) => {
    //         if (!!errors) {
    //             console.log('Errors in form!!!');
    //             return;
    //         } else {
    //             F = true;
    //         }
    //     })
    //     return B && C && D && E && F
    // },
    handleOk() {
        var me = this;
        // var tab4 = this.refs.tab4;
        // if (!tab4) {//判断第4个TAB是否有值
        //     me.handleTabClick("4");
        //     return;
        // } else {
        //     if (!this.validateTab4()) {//验证第4个TAB页
        //         me.handleTabClick("4");
        //         return
        //     } else {
                var ProcessInformation = this.refs.ProcessInformation;
                if (!ProcessInformation) {//判断第5个TAB是否有值
                    me.handleTabClick("5");
                    return;
                } else {
                    var validation6 = this.refs.ProcessInformation.validateFields;
                    validation6((errors, values) => {//验证第5个TAB页
                        if (!!errors) {
                            me.handleTabClick("5");
                            return
                        } else {
                            var state = this.state;
                            var props = this.props;
                            var selectRecord = props.record;
                            var commentData = {};
                            var remarkData = this.refs.ProcessInformation.getFieldsValue();
                            // var housFaceTrial = tab4.refs.RealEstate.getFieldsValue();
                            // var housMarriageInformation = tab4.refs.MarriageInformation.getFieldsValue();
                            // var housCreditInformation = tab4.refs.CreditInformat.getFieldsValue();
                            //验证多选
                            // var photoMatches = housCreditInformation.photoMatchesIdCard;//证件照片身份证
                            // var photoMatchesMarriage = housCreditInformation.photoMatchesMarriageCertificate;//证件照片结婚证
                            // var photoMatchesDivorce = housCreditInformation.photoMatchesDivorceCertificate;//证件照片离婚证
                            // var photo;
                            // var informaitonIdCard = housCreditInformation.identityInformaitonIdCard;//证件信息身份证
                            // var informaitonMarriage = housCreditInformation.identityInformaitonMarriageCertificate;//证件信息结婚证
                            // var informaitonDivorce = housCreditInformation.identityInformaitonDivorceCertificate;//证件信息离婚证
                            // var informaitonAccount = housCreditInformation.identityInformaitonAccountBook;//证件信息户口本
                            // var informaiton;
                            // var housControlInformation = tab4.refs.RiskControl.getFieldsValue();
                            // var borrow = tab4.refs.BorrowFace.getFieldsValue();
                            // borrow.singleRate = borrow.singleRate * 100 / 10000;
                            // borrow.repaymentRate = borrow.repaymentRate * 100 / 10000;
                            // housCreditInformation.goodCredit = housCreditInformation.goodCredit ? 1 : 0;
                            // housCreditInformation.currentOverdue = housCreditInformation.currentOverdue ? 1 : 0;
                            // housCreditInformation.securedLoan = housCreditInformation.securedLoan ? 1 : 0;
                            // housCreditInformation.badDebt = housCreditInformation.badDebt ? 1 : 0;
                            // housCreditInformation.nearlyTwpYears = housCreditInformation.nearlyTwpYears ? 1 : 0;
                            // housCreditInformation.photoMatchesIdCard = housCreditInformation.photoMatchesIdCard ? 1 : 0;
                            // housCreditInformation.photoMatchesMarriageCertificate = housCreditInformation.photoMatchesMarriageCertificate ? 1 : 0;
                            // housCreditInformation.photoMatchesDivorceCertificate = housCreditInformation.photoMatchesDivorceCertificate ? 1 : 0;
                            // housCreditInformation.identityInformaitonIdCard = housCreditInformation.identityInformaitonIdCard ? 1 : 0;
                            // housCreditInformation.identityInformaitonMarriageCertificate = housCreditInformation.identityInformaitonMarriageCertificate ? 1 : 0;
                            // housCreditInformation.identityInformaitonDivorceCertificate = housCreditInformation.identityInformaitonDivorceCertificate ? 1 : 0;
                            // housCreditInformation.identityInformaitonAccountBook = housCreditInformation.identityInformaitonAccountBook ? 1 : 0;
                            commentData.comment = remarkData.nextStep;
                            // 处理证件日期转换 start
                            // housMarriageInformation["documentsTime"] = housMarriageInformation["documentsTime" + housMarriageInformation.maritalStatus];
                            // 处理证件日期转换 end
                            var postdata = {
                                // housCreditInformation,
                                //                                 // borrow,
                                //                                 // housFaceTrial,
                                //                                 // housMarriageInformation,
                                //                                 // housControlInformation,
                                housingValueFaster: remarkData.housingValueFaster,
                                remarkComment: remarkData.remarkComment,
                                receiveType: remarkData.receiveType,
                                amountComment: remarkData.amountComment,
                                householdAssignee: remarkData.householdAssignee,
                                consultId: selectRecord.consultId,
                                nextStep: remarkData.nextStep,
                                processStateCode: selectRecord.processStateCode,
                                projectId: selectRecord.projectId,
                                processInstanceId: selectRecord.processInstanceId,
                            }
                            Utils.ajaxSubmit({
                                me: me,
                                url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                                method: 'post',
                                data: {
                                    taskId: this.props.record.taskId,
                                    serviceVariables: JSON.stringify(postdata),
                                    processVariables: JSON.stringify(commentData)
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
                    })
                }
        //     }
        // }
    },
    //提交转换查询机构数据
    transformQueryData(data) {
        var formData = [];
        data.keys.forEach(item => {
            let obj = {};
            //是建委
            if (item == 1) {
                obj.id = data[item + "id"];
                obj.institutionType = item;
                obj.remark = data[item + "remark"];
                obj.mortgage = data[item + "mortgage"] ? 1 : 0;
                obj.seizure = data[item + "seizure"] ? 1 : 0;
                obj.businessOccupancy = data[item + "businessOccupancy"] ? 1 : 0;
                obj.netSignedOccupancy = data[item + "netSignedOccupancy"] ? 1 : 0;
                obj.hochstbetragshypothek = data[item + "hochstbetragshypothek"] ? 1 : 0;
            }
            //是安融
            else if (item == 4) {
                obj.id = data[item + "id"];
                obj.institutionType = item;
                obj.remark = data[item + "remark"];
                obj.legalProcessPerformed = data[item + "legalProcessPerformed"] ? 1 : 0;
                obj.affiliate = data[item + "affiliate"] ? 1 : 0;
            }
            else {
                obj.id = data[item + "id"];
                obj.institutionType = item;
                obj.remark = data[item + "remark"];
                obj.legalProcessPerformed = data[item + "legalProcessPerformed"] ? 1 : 0;
            }
            formData.push(obj)
        });
        return formData
    },
    handleSave2() {
        var state = this.state;
        var props = this.props;
        var selectRecord = props.record;
        // var tab4 = this.refs.tab4
        // var housFaceTrial = tab4.refs.RealEstate.getFieldsValue();
        // var housMarriageInformation = tab4.refs.MarriageInformation.getFieldsValue();
        // var borrow = tab4.refs.BorrowFace.getFieldsValue();
        // var housCreditInformation = tab4.refs.CreditInformat.getFieldsValue();
        // var housControlInformation = tab4.refs.RiskControl.getFieldsValue();
        // borrow.singleRate = borrow.singleRate * 100 / 10000;
        // borrow.repaymentRate = borrow.repaymentRate * 100 / 10000;
        // housCreditInformation.goodCredit = housCreditInformation.goodCredit ? 1 : 0;
        // housCreditInformation.currentOverdue = housCreditInformation.currentOverdue ? 1 : 0;
        // housCreditInformation.securedLoan = housCreditInformation.securedLoan ? 1 : 0;
        // housCreditInformation.badDebt = housCreditInformation.badDebt ? 1 : 0;
        // housCreditInformation.nearlyTwpYears = housCreditInformation.nearlyTwpYears ? 1 : 0;
        // housCreditInformation.photoMatchesIdCard = housCreditInformation.photoMatchesIdCard ? 1 : 0;
        // housCreditInformation.photoMatchesMarriageCertificate = housCreditInformation.photoMatchesMarriageCertificate ? 1 : 0;
        // housCreditInformation.photoMatchesDivorceCertificate = housCreditInformation.photoMatchesDivorceCertificate ? 1 : 0;
        // housCreditInformation.identityInformaitonIdCard = housCreditInformation.identityInformaitonIdCard ? 1 : 0;
        // housCreditInformation.identityInformaitonMarriageCertificate = housCreditInformation.identityInformaitonMarriageCertificate ? 1 : 0;
        // housCreditInformation.identityInformaitonDivorceCertificate = housCreditInformation.identityInformaitonDivorceCertificate ? 1 : 0;
        // housCreditInformation.identityInformaitonAccountBook = housCreditInformation.identityInformaitonAccountBook ? 1 : 0;
        // housMarriageInformation["documentsTime"] = housMarriageInformation["documentsTime" + housMarriageInformation.maritalStatus];
        // var repaymentRate = borrow.repaymentRate;
        // var singleRate = borrow.singleRate;
        // if (singleRate < repaymentRate) {
        //     Modal.error({
        //         title: '成单利率要大于等于底点利率',
        //         onOk: () => {
        //             this.handleTabClick("4");
        //         }
        //     });
        //     return;
        // }
        var postdata = {
            // housCreditInformation,
            // housFaceTrial,
            // borrow,
            // housMarriageInformation,
            // housControlInformation,
            consultId: selectRecord.consultId,
            processStateCode: selectRecord.processStateCode,
            projectId: selectRecord.projectId,
            processInstanceId: selectRecord.processInstanceId
        };
        Utils.ajaxData({
            url: '/modules/audit/HousFaceTrialAction/saveFaceAuditDraft.htm'
            , method: 'post'
            , data: {
                params: JSON.stringify(postdata)
            }
            , type: 'json'
            , callback: (result) => {
                if (result.code == 200) {
                    Modal.success({
                        title: result.msg
                    });
                    // this.refs.tab4.queryFormData(props.record)

                }
                else if (result.code == 400) {
                    Modal.error({
                        title: result.msg
                    });
                }
            }
        });
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
        })
        if(key == 7){
            this.queryReslutApprove()
        }
    },
    changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },
    navLineData1: {
        "房产信息": "#s1",
        "借款需求": "#s4",
    },
    navLineData2: {
        "下户信息": "#s1",
        "资料清单": "#s2",
        "房屋快出值信息": "#s3",
        "房屋核行信息": "#s4",
    },
    queryReslutApprove(){
        let {processInstanceId} = this.props.record;
        Utils.ajaxSubmit({
            me: this,
            url: '/modules/common/PubApprovalResultsAction/queryPubApprovalResults.htm',
            method: 'post',
            data: {
                processInstanceId
            },
            type: 'json',
            callback: (response) => {
                if(response.code==200){
                    this.setState({
                        resultApprove:response
                    })
                }else{
                    this.setState({
                        resultApprove:{}
                    })
                }
            }
        });
    },
    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                提 交
            </Button>
        ];
        if (!props.canEdit) {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返回</button>
            ]
        }
        // if (props.title == "处理" && state.activekey == 4) {
        //     modalBtns = [
        //         <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
        //         <button key="submit" className="ant-btn" onClick={this.handleSave2}>保存草稿</button>,
        //         <button key="button" className="ant-btn ant-btn-primary" disabled={!props.canEdit}  onClick={this.handleOk}>
        //             提 交
        //         </button>
        //     ];
        // }
        var canEditNeed = canEditNeed;
        var canEditBasicInfo = props.canEditBasicInfo;
        var selectRecord = props.record;
        var idData
        if (selectRecord && selectRecord.processInstanceId) {
            idData = selectRecord.processInstanceId;
        }
        var props = this.props
        var borrowId = props.borrowId
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="房产评估" key="1">
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData1) }>
                                <div className="col-22 navLine-wrap-left" >
                                    <div id="s1">
                                        <h2>房产信息</h2>
                                        <HousingInformation ref="HousingInformation" canEdit={false} record={props.record} title={props.title}/>
                                    </div>
                                    <div id="s3">
                                        <h2>借款需求</h2>
                                        <BorrowingNeeds ref="BorrowingNeeds" canEdit={false} record={props.record} title={props.title}/>
                                    </div>
                                </div>
                                <div className="navLine-wrap-right" >
                                    <NavLine navLineData={this.navLineData1} activeItem="#s1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="客户信息" key="2">
                        <BasiInformationMJS ref="BasiInformationMJS" canEdit={false} record={props.record} title={props.title}/>
                    </TabPane>
                    <TabPane tab="附件上传" key="6">
                        <UploadALL ref="UploadALL" idData={idData} canEdit={props.canEdit} selectRecord={props.record} title={props.title}/>
                    </TabPane>
                    <TabPane tab="审批结果" key="7">
                        <ResultApprove ref="ResultApprove" selectRecord={props.record} canEdit={false} resultApprove={state.resultApprove}/>
                    </TabPane>
                    <TabPane tab="审批历史" key="3">
                        <HistoryApprove ref="HistoryApprove" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                    {/*<TabPane tab="复审" key="4">*/}
                        {/*<Tab4 record={props.record}  ref="tab4" canEdit={false} hideModal={this.props.hideModal} borrowId={borrowId} visible={props.visible}/>*/}
                    {/*</TabPane>*/}
                    <TabPane tab="流程信息" key="5">
                        <ProcessInformation ref="ProcessInformation" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default AddlWin;
