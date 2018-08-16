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
var typeIdList = [];
Utils.ajaxData({
    url: '/getDicts.htm?type=BANK_TYPE',
    method: 'get',
    type: 'json',
    callback: (result) => {
        typeIdList = result.data;
    }
});
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
                automaticWithholdingTime: ""
            },
            formData: {}
        };
    },
    handleReset() {
        this.refs.validation.reset();
        this.setState(this.getInitialState());
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
    },
    onChange(field, value) { 
        this.setState({
            [field]: value,
        });
    },
    onStartChange(value) {
        this.onChange('automaticWithholdingTime', value);
    },
    handleStartToggle({ open }) {
        if (!open) {
            this.setState({ endOpen: true });
        }
    },
    componentDidMount() {
        var record = {};
        if (this.props.record) {
            var record = this.props.record;
        }
        this.props.form.setFieldsValue(record);
    },
    gettypeIdList() {
        return typeIdList.map((item, index) => {
            return <Option key={item.value} >{item.text}</Option>
        })
    },
    navLineData: {
        "基本信息": "#s1",
        "下户费收取": "#s2",
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
                                <h2>基本信息</h2>
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
                                        <FormItem  {...formItemLayout} label="贷款金额(元)：">
                                            <Input disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="超额金额(元)：">
                                            <Input disabled={true}  {...getFieldProps('excessAccount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="展期期限(月)：">
                                            <Input disabled={true}  {...getFieldProps('extensionCount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>

                            </div>
                            <div id="s2">
                                <h2>下户费收取</h2>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="财务专员：">
                                            <Input disabled={true}  {...getFieldProps('financeSpecialist') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="应收展期费(元)：">
                                            <Input disabled={true}  {...getFieldProps('extensionFee') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="首期利息：">
                                            <Input disabled={true}  {...getFieldProps('firstInterest') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="应收取金额(元)：">
                                            <Input disabled={true}  {...getFieldProps('receivableAmount') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="收取形式:">
                                            <Select  disabled={!props.canEdit} {...getFieldProps('collectionForm', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                                <Option value={0}>现金</Option>
                                                <Option value={1}>转账</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                    <Col span="11">
                                        <FormItem  {...formItemLayout} label="实收展期费(元)：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('actualExtensionFee',
                                                {
                                                    rules: [
                                                        { required: true, message: '必填只能是数字', type: 'float' },
                                                        { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                                                    ]
                                                }) } type="text" autoComplete="off" />
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
