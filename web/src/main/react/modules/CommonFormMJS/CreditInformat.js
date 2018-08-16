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
    Radio,
    Checkbox,
    DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var Reflux = require('reflux');
var FormDataStore = require('./stores/FormDataStore');
const NavLine = require("../utils/NavLine");
import MicroInput from '../utils/MicroInput';//数字金额加逗号
const RadioGroup = Radio.Group;
const CheckboxGroup = Checkbox.Group;
var Region = require('../plugin/Region');
var typeIdList = [];
Utils.ajaxData({
    url: '/getDicts.htm?type=BANK_TYPE',
    method: 'get',
    type: 'json',
    callback: (result) => {
        typeIdList = result.data;
    }
});
var AddlWin = React.createClass({
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
                automaticWithholdingTime: "",
            },
            alternatePropertyAddressId: '110101',
            formData: {}
        };
    },
    changeAreaId(name, areaId) {
        this.setState({
            [name]: areaId
        })
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
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
    onChangea(checkedValues) {

    },
    navLineData: {
        "房产信息": "#s1",
        "婚姻信息": "#s2",
        "征信信息": "#s3",
        "风控信息": "#s4",
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
        var current, secured, bad;
        var currentOverdue = this.props.form.getFieldsValue().currentOverdue;
        var securedLoan = this.props.form.getFieldsValue().securedLoan;
        var badDebt = this.props.form.getFieldsValue().badDebt;
        if (currentOverdue == true) {
            current = (
                <Col span="5" >
                    <FormItem  {...formItemLayout} label="逾期金额(元)："  style={{ marginLeft: "-100px", marginTop: "15px" }}>
                        <MicroInput disabled={!props.canEdit} style={{ width: "80%" }} {...getFieldProps('overdueAmounts') }  autoComplete="off" />
                    </FormItem>
                </Col>
            )
        }
        if (securedLoan == true) {
            secured = (
                <Col span="8">
                    <FormItem  {...formItemLayout} label="担保性贷款金额(元)：" style={{ marginLeft: "-20px", marginTop: "15px" }}>
                        <MicroInput disabled={!props.canEdit} style={{ width: "82%" }} {...getFieldProps('securedLoanAmounts') }  autoComplete="off" />
                    </FormItem>
                </Col>
            )
        }
        if (badDebt == true) {
            bad = [
                <Col span="7" key='1'>
                    <FormItem  {...formItemLayout} label="呆账笔数：" >
                        <Input disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('badDebtItems') }  autoComplete="off" />
                    </FormItem>
                </Col>,
                <Col span="7" key='2'>
                    <FormItem  {...formItemLayout} label="呆账金额(元)：">
                        <MicroInput disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('badDebtAmounts') }  autoComplete="off" />
                    </FormItem>
                </Col>
            ]
        }
        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  style={{ marginLeft: "30px", marginTop: "15px" }}>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('goodCredit', { valuePropName: 'checked' }) }>征信良好</Checkbox>
                            <Checkbox className="ant-checkbox-inline"  disabled={!props.canEdit} {...getFieldProps('currentOverdue', { valuePropName: 'checked' }) }>当前逾期</Checkbox>
                        </FormItem>
                    </Col>
                    {current}

                </Row>
                <Row>
                    <Col span="4">
                        <FormItem  {...formItemLayout}  style={{ marginLeft: "30px", marginTop: "15px" }}>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit}  {...getFieldProps('securedLoan', { valuePropName: 'checked' }) }>担保性贷款</Checkbox>
                        </FormItem>
                    </Col>
                    {secured}
                </Row>
                <Row>
                    <Col span="4">
                        <FormItem  {...formItemLayout}  style={{ marginLeft: "30px" }}>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('badDebt', { valuePropName: 'checked' }) }>呆账</Checkbox>
                        </FormItem>
                    </Col>
                    {bad}
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="备注：" style={{ width: "800px" }} labelCol={{ span: "3" }}>
                            <Input disabled={!props.canEdit}  {...getFieldProps('remark') } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="证件照片相符：" {...formItemLayout} labelCol={{ span: 6 }} wrapperCol={{ span: 18 }} >
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('photoMatchesIdCard', { valuePropName: 'checked' }) }>身份证</Checkbox>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('photoMatchesMarriageCertificate', { valuePropName: 'checked' }) }>结婚证</Checkbox>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('photoMatchesDivorceCertificate', { valuePropName: 'checked' }) }>离婚证</Checkbox>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="证件信息一致：" {...formItemLayout} labelCol={{ span: 6 }} wrapperCol={{ span: 18 }}  >
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('identityInformaitonIdCard', { valuePropName: 'checked' }) }>身份证</Checkbox>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('identityInformaitonMarriageCertificate', { valuePropName: 'checked' }) }>结婚证</Checkbox>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('identityInformaitonDivorceCertificate', { valuePropName: 'checked' }) }>离婚证</Checkbox>
                            <Checkbox className="ant-checkbox-inline" disabled={!props.canEdit} {...getFieldProps('identityInformaitonAccountBook', { valuePropName: 'checked' }) }>户口本</Checkbox>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="户口与房屋所在地是否一致：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('locationPropertyConsistent') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="抵贷是否一致：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('foreclosedConsistency') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="抵贷实际用款人：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
                            <Input disabled={!props.canEdit} {...getFieldProps('realLoanName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="是否共借：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('whetherTotal') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="是否家庭名下唯一房产：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('onlyHousing') } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="17">
                        <FormItem  labelCol={{ span: 3 }} wrapperCol={{ span: 19 }} label="备用房屋地址：">
                            <Region {...getFieldProps('alternatePropertyAddressId', { initialValue: this.state.alternatePropertyAddressId, onChange: this.changeAreaId.bind(this, "alternatePropertyAddressId") }) }   disabled={!this.props.canEdit}/>
                        </FormItem>
                    </Col>
                    <Col span="4" >
                        <FormItem style={{ marginLeft: "-83px" }}>
                            <Input disabled={!props.canEdit} placeholder="详细地址"  {...getFieldProps('alternatePropertyAddress') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
AddlWin = createForm()(AddlWin);
export default AddlWin;
