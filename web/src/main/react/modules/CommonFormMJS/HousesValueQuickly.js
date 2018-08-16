//权证下户--核行
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
const Option = Select.Option;
const FormItem = Form.Item;
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var HousesValueQuickly = React.createClass({
    getInitialState() {
        return {

        }
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
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="可否代为提前还款：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('bankPrepayment') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="可否代为领取解押材料：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('bankMaterial') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="尾款(万元)：">
                            <MicroInput disabled={!props.canEdit}  {...getFieldProps('balancePayment',
                                {
                                    rules: [
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="是否为最高额抵押贷款：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('highestMortgage') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="19">
                        <FormItem  labelCol={{ span: 3 }} wrapperCol={{ span: 18 }}  label="备注：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('remark') } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="核行调查员：">
                            <Input disabled={true}  {...getFieldProps('investigator') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="核行调查日期：">
                            <DatePicker disabled={!props.canEdit}  {...getFieldProps('surveyTime') } />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
HousesValueQuickly = createForm()(HousesValueQuickly);
export default HousesValueQuickly;
