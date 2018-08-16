//基本信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
    InputNumber,
    DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var Reflux = require('reflux');
var FormDataStore = require('../../../CommonForm/stores/FormDataStore');
const NavLine = require("../../../utils/NavLine");
var RenewaFeesWin = React.createClass({
    mixins: [
        Reflux.listenTo(FormDataStore, "onMatch")
    ],
    onMatch(data) { 
        if (data.formData) {
            this.props.form.setFieldsValue(data.formData);
        } else this.props.form.resetFields();
    },
    getInitialState() {
        return {
            status: {
            },
            formData: {}
        };
    },
    navLineData: {
        "划扣信息": "#s1",
        "表单信息": "#s2",
    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };
        return (
            <div style={{ position: "relative" }}>
                <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData1) }>
                    <div className="col-22 navLine-wrap-left" >
                        <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                            <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                            <div id="s1">
                                <h2>划扣信息</h2>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="项目编号：">
                                            <Input disabled={true}  {...getFieldProps('projectCode') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="借款名称：">
                                            <Input disabled={true}  {...getFieldProps('projectName') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="客户名称：">
                                            <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="批贷金额(大写)：">
                                            <Input disabled={true}  {...getFieldProps('extensionAmountChinese') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="批贷金额(/元小写)：">
                                            <Input disabled={true}  {...getFieldProps('extensionAmount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="开户银行：">
                                            <Input disabled={true}  {...getFieldProps('accountOpening') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="银行账户：">
                                            <Input disabled={true}  {...getFieldProps('creditCardNumber') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="开户网点：">
                                            <Input disabled={true}  {...getFieldProps('bankAccount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col></Row>
                            </div>
                            <div id="s2">
                                <h2>表单信息</h2>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="原贷款金额(元)：">
                                            <Input disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="展期批贷金额(元)：">
                                            <Input disabled={true}  {...getFieldProps('extensionAmount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="已收展期费金额(元)：">
                                            <Input disabled={true}  {...getFieldProps('actualExtensionFee') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="已收首期利息(元)：">
                                            <Input disabled={true}  {...getFieldProps('firstInterest') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="划扣总额(元)：">
                                            <Input disabled={true}  {...getFieldProps('extensionAmount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="划扣日期：">
                                            <DatePicker disabled={!props.canEdit}  {...getFieldProps('deductTime', { rules: [{ required: true, message: '必填', type: "date" }] }) } />
                                        </FormItem>
                                    </Col>
                                </Row>
                            </div>
                        </Form>
                    </div>
                    <div className="navLine-wrap-right" >
                        <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                    </div>
                </div>
            </div>
        );
    }
});
RenewaFeesWin = createForm()(RenewaFeesWin);
export default RenewaFeesWin;
