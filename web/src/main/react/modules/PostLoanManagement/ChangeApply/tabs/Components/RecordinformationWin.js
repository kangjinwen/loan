//提前还款申请
import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
const NavLine = require("./../../../../utils/NavLine");
const TabPane = Tabs.TabPane;
import Stage from './Stage';
import Disposal from './Disposal';
import Break from './Break';
import Advance from './Advance';
const objectAssign = require('object-assign');
var RecordinformationWin = React.createClass({
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
            if (!(typeof makeRepaymentTime === 'string')) {

            }
            var postdata = {
                projectId: selectRecord.projectId,
                loanChangeRemark: AdvanceData.loanChangeRemark,//将Advance中的字段传给后台
                makeRepaymentTime: DateFormat.formatTime("yyyy-MM-dd hh:mm:ss", AdvanceData.repaymentTime),
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
                { //展期申请
                    winContent = <Stage ref="Stage" canEdit={!props.canEdit} />

                    break;
                }
            case 1:
                { //提前还款申请
                    winContent = <Advance ref="Advance" canEdit={!props.canEdit} />
                    break;
                }
            case 5:
                { // 房屋处置申请
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
    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <Button key="button" className="ant-btn ant-btn-primary" onClick={this.handleOk}>
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
                            {this.renderWinContent() }
                        </div>
                    </div>
                </div>
            </Modal>
        );
    }
});
export default RecordinformationWin;