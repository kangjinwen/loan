// 审批结果
import React from 'react';
import {Col, Form, Input, InputNumber, Row} from 'antd';
import MicroInput from '../utils/MicroInput'; //数字金额加逗号
const createForm = Form.create;
const FormItem = Form.Item;
var ResultApprove = React.createClass({
    getInitialState() {
        console.log(this.props.resultApprove);
        return {
            formData: {},
            mortgagepercent: '0%'
        };
    },
    componentWillReceiveProps(next) {
        if (next.resultApprove) {

        }
    },
    computeMortgagePercent() {
        setTimeout(() => {
            var {approvalaccount, mortgageprice} = this.props.form.getFieldsValue()
            console.log(approvalaccount, mortgageprice);
            if (approvalaccount && mortgageprice) {
                var percent = (approvalaccount / mortgageprice * 100).toFixed(2) + '%'
                this.props.form.setFieldsValue({
                    mortgagepercent: percent
                })
            }
        })
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
        var disabled = !props.canEdit;
        var resultApprove = this.props.resultApprove?this.props.resultApprove:{}
        let {approvalaccount, approvaltimelimit, mortgageprice} = resultApprove
        let mortgagepercent = approvalaccount&&mortgageprice?((approvalaccount / mortgageprice * 100).toFixed(2) + '%'):''
        return (
            <Form horizontal form={this.props.form} style={{marginTop: "20px"}}>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="审批额度(元)：">
                            <MicroInput disabled={disabled} style={{width: "100%"}} {...getFieldProps('approvalaccount',
                                {
                                    rules: [
                                        {required: true, message: '必填只能是数字', type: 'float'},
                                        {message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/},
                                    ],
                                    onChange: () => {
                                        this.computeMortgagePercent()
                                    },
                                    initialValue: approvalaccount
                                })} autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="审批期限(月)：">
                            <InputNumber disabled={disabled} max="250"
                                         style={{width: "100%"}} {...getFieldProps('approvaltimelimit',
                                {

                                    rules: [
                                        {required: true, message: '必填只能是数字', type: 'number'},
                                        {message: '不能为负数', pattern: /^\d+(\.\d+)?$/},
                                    ],
                                    initialValue: approvaltimelimit
                                })} autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="抵押物价格：">
                            <InputNumber  disabled={disabled} style={{width: "100%"}} {...getFieldProps('mortgageprice',
                                {

                                    rules: [
                                        {required: true, message: '必填只能是数字', type: 'number'},
                                        {message: '不能为负数', pattern: /^\d+(\.\d+)?$/},
                                    ],
                                    onChange: () => {
                                        this.computeMortgagePercent()
                                    },
                                    initialValue: mortgageprice
                                })} autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="抵押率：">
                            <Input readOnly style={{width: "100%"}}
                                   {...getFieldProps('mortgagepercent', {
                                       initialValue:mortgagepercent
                                   })}
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="19">
                        <FormItem labelCol={{span: 3}} wrapperCol={{span: 18}} label="备注：">
                            <Input disabled={!props.canEdit}
                                   rows="2" {...getFieldProps('remark', {rules: [/*{ required: true, message: '必填' }*/]})}
                                   type="textarea" autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
ResultApprove = createForm()(ResultApprove);
export default ResultApprove;
