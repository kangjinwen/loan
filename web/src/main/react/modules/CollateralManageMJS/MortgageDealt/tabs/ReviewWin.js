import React from 'react';
import {Button, Modal, Tabs,} from 'antd';
import ResultApprove from '../../../CommonFormMJS/ResultApprove';
import HistoryApprove from '../../../CommonFormMJS/HistoryApprove';
import BorrowingNeeds from '../../../CommonFormMJS/BorrowingNeeds';
import BasiInformationMJS from '../../../CustomerManageMJS/CustMang/tabs/BasiInformationMJS';

const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
const NavLine = require("./../../../utils/NavLine");
var NewEvaOrg = require("../../../CommonFormMJS/NewEvaOrg");
var HousingInformation = require("../../../CommonFormMJS/HousingInformation");
var PersonnelType = require("../../../CommonFormMJS/PersonnelType");
var UploadPic = require("../../../CommonFormMJS/UploadPic");
var ProcessInformation = require("../../../CommonFormMJS/ProcessInformation");
var UploadALL = require("../../../CommonFormMJS/UploadALL");
var roleId = window.roleId;
var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            activekey: "1",
            formData: {},
            idData: "",
            loading: false,
            resultApprove: {}
        };
    },
    handleCancel() {
        if (this.refs.ProcessInformation && this.props.canEdit) {
            this.refs.ProcessInformation.resetFields();
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
    /*changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },*/
    navLineData: {
        "房产信息": "#s1",
        "借款需求": "#s3",
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
        return {personnelType}
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
    transformeData(data) {//机构赋值
        var formData = {};
        var keys = [];
        data.forEach(item => {
            let k = item.assessmentAgencies;
            keys.push(k);
            formData[k + 'assessedValue'] = item.assessedValue;
            formData[k + 'id'] = item.id;
        })
        formData["keys"] = keys;
        return formData;
    },

    handleOk() {//新增申请提交

        var me = this;
        var selectRecord = this.props.record;
        let ProcessInformationEl = this.refs.ProcessInformation;
        // 只有审批意见为通过时候执行校验,否则全部提交
        if (!ProcessInformationEl) {
            me.handleTabClick("3");
            return;
        }
        let nextStep = this.refs.ProcessInformation.getFieldsValue().nextStep
        if (nextStep === "pass") {
            // ######################业务逻辑###################

            //if (this.validateTab1()) {
            var ProcessInformation = this.refs.ProcessInformation;
            if (!ProcessInformation) {
                me.handleTabClick("3");
                return;
            }
            var validation3 = this.refs.ProcessInformation.validateFields;
            validation3((errors, values) => {
                if (!!errors) {
                    me.handleTabClick("3");
                    return
                } else {
                    var state = this.state;
                    var props = this.props;
                    var creditConsultFrom = {};
                    var commentData = {};
                    var remarkData = this.refs.ProcessInformation.getFieldsValue();
                    creditConsultFrom.consultId = selectRecord.consultId
                    creditConsultFrom.processStateCode = selectRecord.processStateCode;
                    creditConsultFrom.projectId = selectRecord.projectId;
                    creditConsultFrom.processInstanceId = selectRecord.processInstanceId;
                    creditConsultFrom.nextStep = remarkData.nextStep;
                    creditConsultFrom.remarkComment = remarkData.remarkComment;
                    creditConsultFrom.customerId = this.props.record.id;
                    commentData.comment = remarkData.nextStep;
                    Utils.ajaxSubmit({
                        me: this,
                        url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                        method: 'post',
                        data: {
                            taskId: this.props.record.taskId,
                            serviceVariables: JSON.stringify(creditConsultFrom),
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
            });
            //}

            // ######################业务逻辑###################
        } else {
            this.refs.ProcessInformation.validateFields(errors => {
                if (!!errors) {

                } else {
                    let serviceVariables = {
                        remarkComment: this.refs.ProcessInformation.getFieldsValue().remarkComment,
                        nextStep,
                        consultId: selectRecord.consultId,
                        processStateCode: selectRecord.processStateCode,
                        projectId: selectRecord.projectId,
                        processInstanceId: selectRecord.processInstanceId,
                    }
                    Utils.ajaxSubmit({
                        me: this,
                        url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                        method: 'post',
                        data: {
                            taskId: this.props.record.taskId,
                            serviceVariables: JSON.stringify(serviceVariables),
                            processVariables: JSON.stringify({comment: nextStep})
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

        if (key == 7) {
            this.queryReslutApprove()
        }
    },
    queryReslutApprove() {
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
                if (response.code == 200) {
                    this.setState({
                        resultApprove: response
                    })
                } else {
                    this.setState({
                        resultApprove: {}
                    })
                }
            }
        });
    },
    render() {
        var props = this.props;
        var state = this.state;
        var selectRecord = props.record;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}
                    onClick={this.handleOk}>
                提 交
            </Button>
        ];
        var idData
        if (props.record && props.record.processInstanceId) {
            idData = selectRecord.processInstanceId;
        }
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="1200"
                   footer={modalBtns}>
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="房产评估" key="1">
                        <div style={{position: "relative"}}>
                            <div className="navLine-wrap"
                                 onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData)}>
                                <div className="col-22 navLine-wrap-left">
                                    <div id="s1">
                                        <h2>房产信息</h2>
                                        <HousingInformation ref="HousingInformation" canEdit={false}
                                                            record={props.record} title={props.title}/>
                                    </div>
                                    <div id="s3">
                                        <h2>借款需求</h2>
                                        <BorrowingNeeds ref="BorrowingNeeds" canEdit={false} record={props.record}
                                                        title={props.title}/>
                                    </div>
                                </div>
                                <div className="navLine-wrap-right">
                                    <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="客户信息" key="2">
                        <BasiInformationMJS ref="BasiInformationMJS" canEdit={false} record={props.record}
                                            title={props.title}/>
                    </TabPane>
                    <TabPane tab="附件上传" key="4">
                        <UploadALL ref="UploadALL" idData={idData} canEdit={props.canEdit} canUpload={props.canUpload}
                                   selectRecord={props.record} title={props.title}/>
                    </TabPane>
                    <TabPane tab="审批结果" key="7">
                        <ResultApprove ref="ResultApprove" selectRecord={props.record} canEdit={false}
                                       resultApprove={state.resultApprove}/>
                    </TabPane>
                    <TabPane tab="审批历史" key="5">
                        <HistoryApprove ref="HistoryApprove" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                    <TabPane tab="流程信息" key="3">
                        <ProcessInformation ref="ProcessInformation" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default ReviewWin;
