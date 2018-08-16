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
const NavLine = require("./../../../../utils/NavLine");
import BankCard from './../../../../utils/BankCard';//银行卡4位加空格
import MicroInput from './../../../../utils/MicroInput';//数字金额加逗号
var roleId = window.roleId;
var CommissionDraw = React.createClass({
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
    componentWillReceiveProps(nextProps) {
        if (nextProps.record && nextProps.record != this.props.record) {
            this.queryData(nextProps.record)
        }
    },
    componentDidMount() {
        this.queryData(this.props.record)
    },
    queryData(record) {
        var nextStepList = [];
        var me = this;
        var props = this.props;
        if (props.canEdit) {
            Utils.ajaxData({
                url: '/modules/workflow/controller/RepositoryController/queryButtonNameListByTaskId.htm',
                method: 'get',
                data: {
                    taskId: record.taskId
                },
                type: 'json',
                callback: (result) => {
                    var items = result.data.map((item) => {
                        return (<Option key={item.value} value={item.value}>{item.text}</Option>);
                    });
                    nextStepList = items;
                    me.setState({ nextStepList: nextStepList });
                }
            });

        } else {
            Utils.ajaxData({
                url: '/modules/common/action/ApprovalCommentsAction/getApprovalComment.htm',
                method: 'get',
                data: {
                    processInstanceId: record.processInstanceId,
                    taskId: record.taskId,
                },
                type: 'json',
                callback: (result) => {

                    this.props.form.setFieldsValue(result.data);
                }
            });
        }
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
            <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="财务专员：">
                            <Input disabled={true}  {...getFieldProps('financialStaff') } autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="返费人姓名：">
                            <Input disabled={true}  {...getFieldProps('salesman') } autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="返佣审批金额(元)：">
                            <MicroInput disabled={true}  {...getFieldProps('rebateAmount') } autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="返佣点位(%)：">
                            <Input disabled={true}  {...getFieldProps('rebatePoints') } autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="返佣期限(月)：">
                            <Input disabled={true}  {...getFieldProps('rebateDeadline') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="返佣卡号：">
                            <BankCard disabled={true}  {...getFieldProps('rebateCard') } type="number" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="开户银行(支行)："  >
                            <Input disabled={true}   {...getFieldProps('returnBankName') } autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="实际返佣金额(元)：">
                            <MicroInput disabled={!props.canEdit}  {...getFieldProps('realRebateAmount', {
                                rules: [
                                    { required: true, message: '必填只能是数字', type: 'float' },
                                    { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                                ]
                            }) } autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="审批意见：">
                            <Select disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.state.nextStepList}
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="16">
                        <FormItem  {...formItemLayout} labelCol={{ span: "6" }} label="备注：">
                            <Input disabled={!props.canEdit} wrapperCol={{ span: "17" }} rows="2" {...getFieldProps('remarkComment', { rules: [/*{ required: true, message: '必填' }*/] }) } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
CommissionDraw = createForm()(CommissionDraw);
export default CommissionDraw;
