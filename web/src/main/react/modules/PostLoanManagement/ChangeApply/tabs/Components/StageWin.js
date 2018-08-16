//展期申请
import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
const NavLine = require("./../../../../utils/NavLine");
import Stage from './Stage';
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var StageWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            idData: selectRecord ? selectRecord.id : null,
            formData: {},
            loading: false
        };
    },
    handleCancel() {
        if (this.refs.Stage) {
            this.refs.Stage.resetFields();
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
    //非空验证
    validateTab1() {
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
    handleOk() {
        var selectRecord = this.props.record;
        if (this.validateTab1()) {
            var state = this.state;
            var props = this.props;
            var Stage = this.refs.Stage;
            var StageData = Stage.getFieldsValue();
            var postdata = {
                projectId: selectRecord.projectId,
                extensionCount: StageData.extensionCount,//展期期限
                extensionAmount: StageData.extensionAmount,//展期金额
                extensionFee: StageData.extensionFee,//展期费
                extensionRate: StageData.extensionRate,//展期利率
                extensionReturnfeeRate: StageData.extensionReturnfeeRate,//展期返佣点费
                remark1: StageData.remark1,//备注
                extendedFlag: StageData.extendedFlag//申请类型
            }

            Utils.ajaxSubmit({
                me: this,
                url: '/modules/LoanChange/extensionApplicationSubmit.htm',
                method: 'post',
                data: {
                    processInstanceId: selectRecord.processInstanceId,
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
    },

    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <Button key="button" className="ant-btn ant-btn-primary" onClick={this.handleOk}>
                提 交
            </Button>
        ];
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1000"  footer={modalBtns} >
                <div style={{ position: "relative" }}>
                    <div className="col-22 navLine-wrap-left" >
                        <div id="s1">
                            <h2>基本信息</h2>
                            <Stage ref="Stage" canEdit={!props.canEdit} />
                        </div>
                    </div>
                </div>
            </Modal>
        );
    }
});
export default StageWin;