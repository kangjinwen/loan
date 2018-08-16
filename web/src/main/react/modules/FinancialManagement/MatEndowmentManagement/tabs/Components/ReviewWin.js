import React from 'react';
import {
    Button,
    Modal,

} from 'antd';

const objectAssign = require('object-assign');
const NavLine = require("./../../../../utils/NavLine");
import MatEndowInformation from './MatEndowInformation';
var BasiInformation = require("../../../../CustomerServiceTrial/MatEndowmentApplication/tabs/Components/BasiInformation");
var ProcessInformation = require("../../../../CommonForm/ProcessInformation");

var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            formData: {}
        };
    },
    handleCancel() {
        if (this.refs.ProcessInformation) {
            this.refs.ProcessInformation.resetFields();
        }
        this.refs.BasiInformation.resetFields();

        this.props.hideModal();
    },
    navLineData: {
        "基本信息": "#s1",
        "垫资信息": "#s2",
        "流程信息": "#s3",
    },
    handleOk(isvalid1) {
        var me = this;
        var validation2 = this.refs.BasiInformation.validateFields;
        validation2((errors, values) => {
            if (!!errors || !isvalid1) {
                return
            } else {
               
                var validation3 = this.refs.ProcessInformation.validateFields;
                validation3((errors, values) => {
                    if (!!errors) { 
                        return
                    } else {
                        var state = this.state;
                        var props = this.props;
                        var selectRecord = props.record;
                        var postdata = {};
                        var commentData = {};
                        var ProcessInformation = this.refs.ProcessInformation.getFieldsValue(); 
                        postdata.processInstanceId=selectRecord.newProcessInstanceId;
                        postdata.processStateCode=selectRecord.processStateCode;
                        postdata.consultId=selectRecord.consultId;
                        postdata.projectId=selectRecord.projectId;
                        postdata.nextStep=ProcessInformation.nextStep;
                        postdata.remarkComment=ProcessInformation.remarkComment;
                        commentData.comment=ProcessInformation.nextStep;
                        delete ProcessInformation.bankCard;
                        delete ProcessInformation.advanceApplyAmount;
                        delete ProcessInformation.accountHolder;
                        delete ProcessInformation.openingBank;
                        delete ProcessInformation.nextStep;
                        delete ProcessInformation.remarkComment;   
                        postdata.housAdvanceApply = ProcessInformation;                      
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
        var button;
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
            <Modal title={props.title} visible={props.visible}  onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
              
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                                <div className="col-22 navLine-wrap-left" >

                                    <div id="s1">
                                        <h2>基本信息</h2>
                                        <BasiInformation ref="BasiInformation" canEdit={props.canEdit}/>
                                      
                                    </div>
                                    <div id="s2">
                                        <h2>垫资信息</h2>
                                        <MatEndowInformation ref="MatEndowInformation" canEdit={props.canEdit} record={props.record}/>
                                    </div>
                                     <div id="s3">
                                        <h2>流程信息</h2>
                                        <ProcessInformation ref="ProcessInformation" canEdit={props.canEdit} record={props.record}/>
                                    </div>
                                </div>
                                <div className="navLine-wrap-right" >
                                    <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                                </div>
                            </div>
                        </div>       
            </Modal>
        );
    }
});
export default ReviewWin;
