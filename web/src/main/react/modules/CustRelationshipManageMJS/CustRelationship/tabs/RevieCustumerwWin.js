import React from 'react';
import {
    Button,
    Modal,
    Tabs,
    Form,
    Row,
    Col,
    Select,
    Input,
    DatePicker
} from 'antd';
//新增联系
const TabPane = Tabs.TabPane;
const Option = Select.Option;
const createForm = Form.create;
const FormItem = Form.Item;
const objectAssign = require('object-assign');
var roleId = window.roleId;
var RevieCustumerwWin = React.createClass({
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
        this.props.form.resetFields();
        this.props.hideModal();
        this.setState(this.getInitialState());
    },
    handleTabClick(key) {
        this.setState({
            activekey: key
        })
    },
    handleOk() {//新增联系提交
        var state = this.state;
        var props = this.props;
        var formeData=this.props.form.getFieldsValue();
        var contactTime;
        if (formeData.contactTime) {
            contactTime=DateFormat.formatTime("yyyy-MM-dd hh:mm:ss", formeData.contactTime)
        }
        console.log(contactTime)
        var creditConsultFrom={
            customerId:props.record.id,
            contactTime:contactTime ,
            type:formeData.type,
            remark:formeData.remark,
        }
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                console.log('Errors in form!!!');
                return;
            } else {
                Utils.ajaxSubmit({
                    me: this,
                    url: '/modules/credit/PubCustomerRelationAction/save.htm',
                    method: 'post',
                    data: {
                    	customerRelation: JSON.stringify(creditConsultFrom),
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
    },
    render() {
        var props = this.props;
        var state = this.state;
        const {
            getFieldProps
        } = this.props.form;
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                提 交
            </Button>
        ];
        return (
            <Modal title= { props.title } visible= { props.visible } onCancel= { this.handleCancel }   width= "1200"  footer= { modalBtns } >
                <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                    <div style={{ position: "relative" }}>
                        <Row>
                            <Col span="7">
                                <FormItem  {...formItemLayout} label="联系时间：">
                                    <DatePicker disabled={!props.canEdit} wrapperCol={{ span: "7" }}  {...getFieldProps('contactTime', { rules: [{ required: true, message: '必填', type: "date" }] }) } />
                                </FormItem>
                            </Col>
                            <Col span="7">
                                <FormItem  {...formItemLayout} label="联系方式：">
                                    <Select  disabled={!props.canEdit} {...getFieldProps('type', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                        <Option  value={1}>电话</Option>
                                        <Option  value={2}>上门</Option>
                                        <Option  value={3}>邮件</Option>
                                        <Option  value={4}>短信</Option>
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="19">
                                <FormItem  {...formItemLayout} label="联系内容："  labelCol={{ span: "3" }}>
                                    <Input disabled={!props.canEdit}  {...getFieldProps('remark') } type="textarea" autoComplete="off" />
                                </FormItem>
                            </Col>
                        </Row>
                    </div>

                </Form >
            </Modal >
        );
    }
});
RevieCustumerwWin = createForm()(RevieCustumerwWin);
export default RevieCustumerwWin;
