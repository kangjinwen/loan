import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
import ProcessInformation from './ProcessInformation';
const NavLine = require("./../../../utils/NavLine");
import Charge_xh from '../../../CommonForm/Charge_xh';
const TabPane = Tabs.TabPane;
import HistoryApprove from '../../../CommonForm/HistoryApprove';
var UploadALL = require("../../../CommonForm/UploadALL");
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
        if (this.refs.ProcessInformation) {
            this.refs.ProcessInformation.resetFields();
        }
        this.props.hideModal();
        this.changeTabState()
    },
    changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },
    handleTabClick(key) {
        this.setState({
            activekey: key
        })
    },
    componentWillReceiveProps(nextProps) {
        this.refs.NewQueryOrg && this.refs.NewQueryOrg.setFieldsValue(nextProps.formData)
    },
    //整体提交数据
    handleOk() {
        var me = this;
        var ProcessInformation = this.refs.ProcessInformation;
        if (!ProcessInformation) {
            me.handleTabClick("2");
            return;
        }
        var validation3 = this.refs.ProcessInformation.validateFields;
        validation3((errors, values) => {
            if (!!errors) {
                me.handleTabClick("2");
                return
            } else {
                var state = this.state;
                var props = this.props;
                var selectRecord = props.record;
                var postdata = {};
                var commentData = {};
                var housLowerCost = {};
                var remarkData = this.refs.ProcessInformation.getFieldsValue();
                postdata.housLowerCost = housLowerCost

                commentData.comment = remarkData.nextStep;
                postdata.remarkComment = remarkData.remarkComment;
                postdata.consultId = selectRecord.consultId;
                postdata.nextStep = remarkData.nextStep;
                postdata.processStateCode = selectRecord.processStateCode;
                postdata.projectId = selectRecord.projectId;
                postdata.processInstanceId = selectRecord.processInstanceId;
                postdata.oldProcessInstanceId = selectRecord.oldProcessInstanceId;
                Utils.ajaxData({
                    url: '/modules/workflow/controller/ProcessTaskController/completeTaskReturnFee.htm',
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


    },
    downLoadAll() {//附件全部下载
        var type = "FILE_DOWNLOAD";
        var record = this.props.record;
        var processInstanceId = record.processInstanceId;
        var projectId = record.projectId; 
        window.location.href = '/modules/common/BatchFileDownloadAction/downloadZip.htm?search={"projectId" : ' + projectId + ', "type":"' + type + '", "processInstanceId":"' + processInstanceId + '"}';
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
        if (props.title == "退费" && state.activekey == "4") {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
                <button key="submit" className="ant-btn" onClick={this.downLoadAll}>附件全部下载</button>,
                <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                    提 交
                </Button>
            ];
        }
        var selectRecord = props.record;
        var idData
        if (selectRecord && selectRecord.processInstanceId) {
            idData = selectRecord.processInstanceId;
        }
        return (
            <Modal title={props.title} visible={props.visible} destroyInactiveTabPane onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="下户费退费" key="1">
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                                <div className="col-22 navLine-wrap-left" >
                                    <Charge_xh ref="Charge_xh" selectRecord={this.props.record} canEdit={props.canEdit} />
                                </div>
                                <div className="navLine-wrap-right" >
                                    <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="附件上传" key="4">
                        <UploadALL ref="UploadALL" idData={idData} canEdit={props.canEdit} selectRecord={props.record} title={props.title}/>
                    </TabPane>
                    <TabPane tab="审批历史" key="3">
                        <HistoryApprove record={props.record}/>
                    </TabPane>
                    <TabPane tab="流程信息" key="2">
                        <ProcessInformation ref="ProcessInformation" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default ReviewWin;
