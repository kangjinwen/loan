import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
import SettlementInfoWin from './SettlementInfoWin';
const NavLine = require("./../../../utils/NavLine");
import SettlementSuggestWin from './SettlementSuggestWin';
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            formData: {},
            loading: false
        };
    },
    componentWillReceiveProps(nextProps) {
        this.refs.NewQueryOrg && this.refs.NewQueryOrg.setFieldsValue(nextProps.formData)
    },
    handleCancel() {
        this.props.hideModal();
    },
    //整体提交数据
    handleOk(isvalid1) {
        var me = this;
        var validation2 = this.refs.SettlementInfoWin.validateFields;
        validation2((errors, values) => {
            if (!!errors || !isvalid1) {
                return;
            } else {
                var validation3 = this.refs.SettlementSuggestWin.validateFields;
                validation3((errors, values) => {
                    if (!!errors) {
                        return
                    } else {
                        var state = this.state;
                        var props = this.props;
                        var selectRecord = props.record;
                        var postdata = {};
                        var remarkData = this.refs.SettlementSuggestWin.getFieldsValue();
                        postdata.isSettleaccounts = remarkData.isSettleaccounts;
                        postdata.remark = remarkData.remark;
                        // postdata.processStateCode = selectRecord.processStateCode;
                        // postdata.projectId = selectRecord.projectId;
                        postdata.processInstanceId = selectRecord.processInstanceId;
                        Utils.ajaxSubmit({
                            me: me,
                            url: '/modules/settleaccount/PlSettlementfeeAction/settleAccounts.htm',
                            method: 'post',
                            data: {
                                json: JSON.stringify(postdata),
                            },
                            type: 'json',
                            callback: (result) => {
                                if (result.code == 200) {
                                    Modal.success({
                                        title: result.msg,
                                        onOk: () => {
                                            props.hideModal();
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
    navLineData: {
        "基本信息": "#s1",
        "结算信息": "#s2",
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
                <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
            ];
        }
        return (
            <Modal title={props.title} visible={props.visible} destroyInactiveTabPane onCancel={this.handleCancel}   width="900"  footer={modalBtns} >
                <div style={{ position: "relative" }}>
                    <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                        <div className="col-22 navLine-wrap-left" >

                            <div id="s1">
                                <h2>基本信息</h2>
                                <SettlementInfoWin ref="SettlementInfoWin" canEdit={props.canEdit} selectedrecord={props.selectedrecord}/>
                            </div>
                            <div id="s2">
                                <h2>结算信息</h2>
                                <SettlementSuggestWin ref="SettlementSuggestWin" canEdit={props.canEdit} record={props.record}/>
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
