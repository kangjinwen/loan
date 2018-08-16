import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
const NavLine = require("./../../../../utils/NavLine");
var NewQueryOrg = require("./../../../../CommonForm/NewQueryOrg");
var UploadPhoto = require("./../../../../CommonForm/UploadPhoto");
import Stage from './Stage';
import Disposal from './Disposal';
import Break from './Break';
import Advance from './Advance';

import ProcessInformation from './ProcessInformation';
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
        if (this.refs.ProcessInformation && this.props.canEdit) {
            this.refs.ProcessInformation.resetFields();
        }
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
    validateTab1() {//验证展期tab页 
        var me = this;
        var A = false;
        this.refs.Stage.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                A = true;
            }
        })
        return A
    },
    validateTab2() {//验证提前还款审批tab页 
        var me = this;
        var B = false;
        this.refs.Advance.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                B = true;
            }
        })
        return B
    },
    validateTab3() {//验证房屋处置审批tab页 
        var me = this;
        var C = false;
        this.refs.Disposal.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                C = true;
            }
        })
        return C
    },
    validateTab4() {//验证tab页 
        var me = this;
        var D = false;
        this.refs.Break.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                D = true;
            }
        })
        return D
    },
    handleOk() {
        var selectRecord = this.props.record;
        if (this.validateTab1()) {
            var state = this.state;
            var props = this.props;
            var ProcessInformation = this.refs.ProcessInformation;
            var Stage = this.refs.Stage;//取到LoanFormInfo中的Dom
            if (!ProcessInformation) {
                this.handleTabClick("2");
                return;
            }
            ProcessInformation.validateFields((errors, values) => {
                if (!!errors) {
                    this.handleTabClick("2");
                    return;
                } else {
                    var remarkData = ProcessInformation.getFieldsValue();
                    var StageData = Stage.getFieldsValue();
                    var commentData = {};
                    commentData.comment = remarkData.nextStep;
                    var postdata = {
                        remarkComment: remarkData.remarkComment,
                        consultId: selectRecord.consultid,
                        nextStep: remarkData.nextStep,
                        processStateCode: selectRecord.processStateCode,
                        projectId: selectRecord.projectId,
                        processInstanceId: selectRecord.processInstanceId//流程id
                    }
                    Utils.ajaxData({
                        url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                        method: 'post',
                        data: {
                            taskId: selectRecord.taskId,
                            serviceVariables: JSON.stringify(postdata),
                            processVariables: JSON.stringify(commentData),

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

        }
    },
    //发起第三方接口
    showModalCheck(title, canEdit) {
        var record = this.props.record
        this.setState({
            title,
            visibleCheck: true,
            canEdit: canEdit,
            record: record,
        });
    },
    showModalResult(title, canEdit) {
        var record = this.props.record
        this.setState({
            title,
            visibleResult: true,
            canEdit: canEdit,
            record: record,
        });
    },
    renderWinContent() {
        var props = this.props;
        var record = props.record;
        var winContent;
        //当第一次进入 没有选中列表的值
        if (!record) {
            return null
        }
        switch (record.branchingProcessType) {
            case 6:
                { // 展期审批
                    winContent = (<div>
                        <div id="s2" style={{ marginTop: 8 }}>
                            <h2>展期信息</h2>
                            <Stage ref="Stage" canEdit={!props.canEdit} />
                        </div>
                        <div id= "s3" >
                            <h2>信息筛查</h2>
                            {this.props.canEdit ? <NewQueryOrg ref="NewQueryOrg" canEdit={!props.canEdit} record={props.record}/> : <NewQueryOrg ref="NewQueryOrg" canEdit={props.canEdit} record={props.record}/>}
                            {/* <div className="actionBtns" style={{ marginBottom: 26, marginLeft: 25, marginTop: 25 }}>
                                <button className="ant-btn"   onClick={this.showModalCheck.bind(this, '发起第三方查询', true) }>
                                    发起第三方查询
                                </button>
                                <button className="ant-btn"   onClick={this.showModalResult.bind(this, '查询结果', true) }>
                                    查询结果
                                </button>
                            </div>*/}
                            <UploadPhoto style={{ marginLeft: 25 }} title="查档说明" btype="WHTHIN" canEdit={false} selectRecord={props.record}/>
                        </div >
                    </div>
                    )
                    break;
                }
            case 1:
                { // 提前还款审批
                    winContent = <Advance ref="Advance" canEdit={!props.canEdit} />
                    break;
                }
            case 5:
                { // 房屋处置审批
                    winContent = <Disposal ref="Disposal" canEdit={!props.canEdit} />
                    break;
                }

            case 2:
                { //罚息减免申请
                    winContent = <Break ref="Break" canEdit={!props.canEdit} />
                    break;
                }
        }
        return winContent
    },
    handleAdvancedRepay() {
        var selectRecord = this.props.record;
        if (this.validateTab2()) {
            var state = this.state;
            var props = this.props;
            var ProcessInformation = this.refs.ProcessInformation;
            //var LoanFormInfo = this.refs.LoanFormInfo;//取到LoanFormInfo中的Dom
            if (!ProcessInformation) {
                this.handleTabClick("2");
                return;
            }
            ProcessInformation.validateFields((errors, values) => {
                if (!!errors) {
                    this.handleTabClick("2");
                    return;
                } else {
                    var remarkData = ProcessInformation.getFieldsValue();
                    var Advance = this.refs.Advance.getFieldsValue();//
                    var commentData = {};
                    commentData.comment = remarkData.nextStep;
                    var postdata = {
                        realPrepaymentPenalty: Advance.realPrepaymentPenalty,//提前还款实际违约金额
                        remarkComment: remarkData.remarkComment,
                        consultId: selectRecord.consultId,
                        nextStep: remarkData.nextStep,
                        processInstanceId: selectRecord.processInstanceId,
                        projectId: selectRecord.projectId,
                        branchingProcessId: selectRecord.branchingProcessId//流程id
                    }
                    Utils.ajaxData({
                        url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                        method: 'post',
                        data: {
                            taskId: selectRecord.taskId,
                            serviceVariables: JSON.stringify(postdata)
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
        }
    },
    render() {
        var props = this.props;
        var state = this.state;
        var selectRecord = props.record;

        //当第一次进入 没有选中列表的值
        if (!selectRecord) {
            return null
        }
        //默认是展期
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleOk}>
                提交
            </button>
        ];
        //提前还款
        if (selectRecord.branchingProcessTypeText == '提前还款') {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
                <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleAdvancedRepay}>
                    提交
                </button>
            ];
        }

        //已处理显示返回按钮
        if (props.title == '查看详情') {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返回</button>
            ]
        }

        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="基本信息" key="1" >
                        <div className="col-22 navLine-wrap-left" >
                            <div id="s1">
                                {this.renderWinContent() }
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="处理意见" key="2">
                        <ProcessInformation ref="ProcessInformation" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default AddlWin;