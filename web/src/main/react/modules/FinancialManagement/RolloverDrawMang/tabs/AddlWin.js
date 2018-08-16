import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
import RollProcessInformation from '../../../CommonForm/RollProcessInformation';
import RenewaFeesWin from './RenewaFeesWin';
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            formData: {}
        };
    },
    handleCancel() {
        if (this.refs.RollProcessInformation && this.props.canEdit) {
            this.refs.RollProcessInformation.resetFields();
        }
        this.refs.RenewaFeesWin.resetFields();
        this.props.hideModal();
        this.changeTabState()
    },
    changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },
    componentWillReceiveProps(nextProps) {
        this.refs.NewQueryOrg && this.refs.NewQueryOrg.setFieldsValue(nextProps.formData)
    },

    handleTabClick(key) {
        this.setState({
            activekey: key
        }, () => {
        })
    },

    //整体提交数据
    handleOk() {
        var me = this;
        var validation2 = this.refs.RenewaFeesWin.validateFields;
        validation2((errors, values) => {
            if (!!errors) {
                me.handleTabClick("1");
            } else {
                var RollProcessInformation = this.refs.RollProcessInformation;
                if (!RollProcessInformation) {
                    me.handleTabClick("2");
                    return;
                }
                var validation3 = this.refs.RollProcessInformation.validateFields;
                validation3((errors, values) => {
                    if (!!errors) {
                        me.handleTabClick("2");
                        return
                    } else {
                        var state = this.state;
                        var props = this.props;
                        var selectRecord = props.record;
                        var commentData = {};
                        var pubProcessBranching = {};
                        var remarkData = this.refs.RollProcessInformation.getFieldsValue();
                        var Charge = this.refs.RenewaFeesWin.getFieldsValue();
                        pubProcessBranching.deductTime=Charge.deductTime//划扣日期
                        pubProcessBranching.id = selectRecord.id;
                        var postdata = {
                            pubProcessBranching,
                            remarkComment: remarkData.remarkComment,
                            consultId: selectRecord.consultId,
                            nextStep: remarkData.nextStep,
                            processStateCode: selectRecord.processStateCode,
                            projectId: selectRecord.projectId,
                            processInstanceId: selectRecord.newProcessInstanceId,
                        };
                        commentData.comment = remarkData.nextStep;
                        Utils.ajaxData({
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
        })
    },
    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary" disabled={!props.canEdit}  onClick={this.handleOk}>
                提 交
            </button>
        ];
        if (!props.canEdit) {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
            ];
        }
        return (
            <Modal title={props.title} visible={props.visible} destroyInactiveTabPane onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="划扣操作" key="1">
                        <RenewaFeesWin ref="RenewaFeesWin" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                    <TabPane tab="流程信息" key="2">
                        <RollProcessInformation ref="RollProcessInformation" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default ReviewWin;
