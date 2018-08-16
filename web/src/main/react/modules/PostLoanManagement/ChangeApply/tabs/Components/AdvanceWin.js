//提前还款申请
import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
const NavLine = require("./../../../../utils/NavLine");
import Advance from './Advance';
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var AdvanceWin = React.createClass({
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
        if (this.refs.Advance) {
            this.refs.Advance.resetFields();
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
        this.refs.Advance.validateFields((errors, values) => {
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
            var Advance = this.refs.Advance;
            var AdvanceData = Advance.getFieldsValue();
            var makeRepaymentTime;
            AdvanceData.makeRepaymentTime == null ? makeRepaymentTime = "" : makeRepaymentTime = DateFormat.formatTime("yyyy-MM-dd 00:00:00", AdvanceData.makeRepaymentTime);
            // if (!(typeof makeRepaymentTime === 'string')) {

            // }
            var postdata = {
                aheadRepayRate: AdvanceData.aheadRepayRate * 100 / 10000,
                projectId: selectRecord.projectId,
                remark1: AdvanceData.remark1,//将Advance中的字段传给后台
                //makeRepaymentTime: DateFormat.formatTime("yyyy-MM-dd hh:mm:ss", AdvanceData.repaymentTime),
                makeRepaymentTime: makeRepaymentTime,
                realPrepaymentPenalty: AdvanceData.realPrepaymentPenalty,
            }

            Utils.ajaxSubmit({
                me: this,
                url: '/modules/LoanChange/aheadofReturnLoanSubmit.htm',
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
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading}  onClick={this.handleOk}>
                提 交
            </Button>
        ];
        if (props.title == '查看详情') {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>
            ]
        };
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1000"  footer={modalBtns} >
                <div style={{ position: "relative" }}>
                    <div className="col-22 navLine-wrap-left" >
                        <div id="s1">
                            <h2>基本信息</h2>
                            <Advance ref="Advance" canEdit={!props.canEdit} advance={this.props.advance} record={this.props.selectRecord}/>
                        </div>
                    </div>
                </div>
            </Modal>
        );
    }
});
export default AdvanceWin;