//借款需求
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
const NavLine = require("../utils/NavLine");
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var roleId = window.roleId;
var BorrowFace = React.createClass({
    getInitialState() {
        return {
            status: {
                automaticWithholdingTime: ""
            },
            formData: {}
        };
    },
    handleReset() {
        this.refs.validation.reset();
        this.setState(this.getInitialState());
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
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="贷款额(元)：">
                            <MicroInput disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('account',
                                {
                                    rules: [
                                        { required: true, message: '必填只能是数字', type: 'float' },
                                        { message: '不能为负数,不能大于10位数,保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="借款期限(月)：">
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('timeLimit',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: 'number' },
                                        { message: '不能为负数', pattern: /^\d+(\.\d+)?$/ },
                                        { message: '不能大于4位数', pattern: /^\d{1,4}$/ }
                                    ]
                                }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="底点利率(%)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('repaymentRate',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                    ]
                                }) } type="text"  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="成单利率(%)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('singleRate',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                    ]
                                }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
BorrowFace = createForm()(BorrowFace);
export default BorrowFace;