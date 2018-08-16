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
const NavLine = require("./../../../../utils/NavLine");
var roleId = window.roleId;

var MatEndowmentMage = React.createClass({
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
    componentWillMount() {
        this.queryData(this.props.record);
        Utils.ajaxData({
            url: '/modules/common/action/ComboAction/queryDic.htm',//银行字典
            method: 'get',
            data: {
                typeCode: "BANK_TYPE"
            },
            type: 'json',
            callback: (result) => {
                var BankFlag = result.data;
                this.setState({
                    BankFlag
                })
            }
        });
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
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="财务专员：">
                           <Input disabled={true}  value={this.props.record.assignee} autoComplete="off" />   
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="！申请垫资金额：">
                            <Input  disabled={true}  {...getFieldProps('advanceApplyAmount') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="开户人姓名：">
                             <Input disabled={true}  {...getFieldProps('accountHolder') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="垫资卡号：">
                             <Input disabled={true}  {...getFieldProps('bankCard') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="开户行：">
                             <Input disabled={true}  {...getFieldProps('openingBank') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="实际垫资金额：">
                             <Input disabled={!props.canEdit}  {...getFieldProps('advanceAmount') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="处理意见：">
                            <Select disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.state.nextStepList}
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row> 
                    <Col span="16">
                        <FormItem  {...formItemLayout}  labelCol={{ span: "6" }} label="备注：">
                            <Input disabled={!props.canEdit} wrapperCol={{span: "17" }} rows="2" {...getFieldProps('remarkComment') } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                
            </Form>
        );
    }
});
MatEndowmentMage = createForm()(MatEndowmentMage);
export default MatEndowmentMage;