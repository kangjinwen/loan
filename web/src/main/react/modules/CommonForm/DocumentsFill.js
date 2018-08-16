//放款单据填写基本信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
    DatePicker
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;

import MicroInput from '../utils/MicroInput';//数字金额加逗号
var HousingInformation = React.createClass({
    getInitialState() {
        return {
            propertyAddressId: '110101'
        }
    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        var selectRecord = props.selectRecord;
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };
        return (
            <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="项目编号：">
                            <Input disabled={true}  {...getFieldProps('projectCode') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="客户姓名：">
                            <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="批贷金额(元)：">
                            <MicroInput disabled={true} {...getFieldProps('account') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="借款期限：">
                            <Input disabled={true} {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="底点利率(%)：">
                            <Input disabled={true}  {...getFieldProps('repaymentRate') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="成单利率(%)：">
                            <Input disabled={true}  {...getFieldProps('singleRate') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="首期利息：">
                            <MicroInput disabled={true} {...getFieldProps('firstInterest') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="下户费收取形式：">
                            <Select disabled={true} {...getFieldProps('collectionForm') } >
                                <Option value={0}>线上收取</Option>
                                <Option value={1}>线下收取</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="下户费金额(元)：">
                            <MicroInput disabled={true}  {...getFieldProps('actualFee') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
