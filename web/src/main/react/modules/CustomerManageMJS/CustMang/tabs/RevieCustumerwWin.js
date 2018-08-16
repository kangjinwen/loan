import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
//新增客户
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
import BasiInformationMJS from './BasiInformationMJS';
var roleId = window.roleId;
var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            activekey: "1",
            formData: {},
            idData: "",
            loading: false
        };
    },
    handleCancel() {
        if (this.refs.BasiInformationMJS) {
            this.refs.BasiInformationMJS.resetFields();
        }
        this.props.hideModal();
        this.setState(this.getInitialState());
    },
    handleTabClick(key) {
        this.setState({
            activekey: key
        })
    },
    navLineData: {
        "基本信息": "#s1",
        "职业信息": "#s2",
        "其他": "#s3",
    },
    validateTab1() {//验证第一个tab页 
        var me = this;
        var B = false;
        this.refs.BasiInformationMJS.validateFields((errors, values) => {
            if (!!errors) {
                return;
            } else {
                B = true;
            }
        })
        return B 
    },
    handleOk() {//新增客户提交
        if (this.validateTab1()) {
            var state = this.state;
            var props = this.props;
            var creditConsultFrom = {};
            var housBorrowingBasics = this.refs.BasiInformationMJS.getFieldsValue();
            Utils.ajaxSubmit({
                me: this,
                url: '/modules/common/PubCustomerAction/saveOrUpdatePubCustomer.htm',
                method: 'post',
                data: {
                    pubCustomer: JSON.stringify(housBorrowingBasics)
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
        var selectRecord = props.record;
        var modalBtns = [
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                提 交
            </Button>
        ];
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >
                 <div>
                    <BasiInformationMJS ref="BasiInformationMJS" canEdit={props.canEdit} record={props.record} title={props.title}/>
                </div>
            </Modal>
        );
    }
});
export default ReviewWin;
