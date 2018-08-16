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
var MatEndowInformation = React.createClass({
    getInitialState() {
        return {
            formData: {}
        };
    }, 
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        const formItemLayout = {
            labelCol: {
                span: 9
            },
            wrapperCol: {
                span: 15
            },
        };
        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资金额(元)：">
                            <Input  disabled={true}  {...getFieldProps('advanceApplyAmount', { rules: [{ required: true, message: '请正确输入垫资金额',type:'number' }] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资费率(%)：">
                             <Input disabled={true}  {...getFieldProps('advanceRateAmount', { rules: [{ required: true, message: '请正确输入垫资费率' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>   
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资费用(元)：">
                             <Input disabled={true}  {...getFieldProps('advanceAmount', { rules: [{ required: true, message: '请正确输入垫资费' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资期限(天)：">
                             <Input disabled={true}  {...getFieldProps('advanceLimited', { rules: [{ required: true, message: '请正确输入垫资期限' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资卡开户名：">
                            <Input  disabled={true}  {...getFieldProps('advanceApplyAmount', { rules: [{ required: true, message: '请正确输入垫资金额',type:'number' }] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                     <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资卡开户行(支行)：">
                            <Input disabled={true}  {...getFieldProps('advanceR6ateAmount', { rules: [{ required: true, message: '请正确输入垫资费率' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资卡卡号：">
                             <Input disabled={true}  {...getFieldProps('advanceRateAmount', { rules: [{ required: true, message: '请正确输入垫资费率' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="已收取垫资费用：">
                             <Input disabled={true}  {...getFieldProps('advanceAmount', { rules: [{ required: true, message: '请正确输入垫资费' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                 <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="实际收取垫资费用：">
                             <Input disabled={!props.canEdit}  {...getFieldProps('advanceAmo3unt', { rules: [{ required: true, message: '请正确输入垫资费' ,type:'number'}] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
MatEndowInformation = createForm()(MatEndowInformation);
export default MatEndowInformation;