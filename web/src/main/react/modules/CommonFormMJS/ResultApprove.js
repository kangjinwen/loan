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
            var {approvalAccount, mortgagePrice} = this.props.form.getFieldsValue()
            // console.log(approvalaccount, mortgageprice);

            if (approvalAccount && mortgagePrice) {
                if (approvalAccount / mortgagePrice * 100 > 100) {

                    this.props.form.setFields({
                        mortgagePercent: {
                            value: '',
                            errors: [new Error('抵押率过高请重新输入审批额度!')],
                        },
                    })
                    return;
                }
                var percent = (approvalAccount / mortgagePrice * 100).toFixed(2) + '%'
                this.props.form.setFieldsValue({
                    mortgagePercent: percent
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
        var resultApprove = this.props.resultApprove ? this.props.resultApprove : {}
        let {approvalaccount, approvaltimelimit, mortgageprice,remark} = resultApprove
        if (!approvaltimelimit) {
            approvaltimelimit = 12
        }
        let mortgagepercent = approvalaccount && mortgageprice ? ((approvalaccount / mortgageprice * 100).toFixed(2) + '%') : ''
        return (
            <Form horizontal form={this.props.form} style={{marginTop: "20px"}}>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="审批额度(元)：">
                            <MicroInput disabled={disabled} style={{width: "100%"}} {...getFieldProps('approvalAccount',
                                {
                                    rules: [
                                        {required: true, message: '不能为空且只能是整数', type: 'integer'},
                                        {message: '最低额度100,000元,且不能大于10位数', pattern: /^(\d{6,10})?$/},
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
                                         style={{width: "100%"}} {...getFieldProps('approvalTimeLimit',
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
                            <MicroInput disabled={disabled} style={{width: "100%"}} {...getFieldProps('mortgagePrice',
                                {
                                    rules: [
                                        {required: true, message: '不能为空且只能是整数', type: 'integer'},
                                        {message: '最低额度100,000元,且不能大于10位数', pattern: /^(\d{6,10})?$/},
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
                            <Input readOnly disabled style={{width: "100%"}}
                                   {...getFieldProps('mortgagePercent', {
                                       rules: [
                                           {required: true, message: '抵押率过高请重新输入审批额度!'},
                                       ],
                                       initialValue: mortgagepercent
                                   })}
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="19">
                        <FormItem labelCol={{span: 3}} wrapperCol={{span: 18}} label="备注：">
                            <Input disabled={!props.canEdit}
                                   rows="2" {...getFieldProps('remark', {rules: [/*{ required: true, message: '必填' }*/],initialValue: remark})}
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
