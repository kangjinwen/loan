import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
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
const RadioGroup = Radio.Group;
const CheckboxGroup = Checkbox.Group;
var Region = require('../plugin/Region');

import ComboData from '../utils/ComboData';//获取字典
var PurposesList = ComboData.getIntCombo('PLAN_USE');//规划用途：
var PropertyListedProofList = ComboData.getIntCombo('HOUSE_MARKET_CERTIFY');//房改房上市证明：
var PropertyPropertiesList = ComboData.getIntCombo('PROPERTY_TYPE');//房产性质：

// var PurposesList = [];
// Utils.ajaxData({
//     url: '/modules/common/action/ComboAction/queryDic.htm',
//     method: 'get',
//     data: {
//         typeCode: "PLAN_USE"
//     },
//     type: 'json',
//     callback: (result) => {
//         PurposesList = result.data;
//     }
// });
// var PropertyListedProofList = [];
// Utils.ajaxData({
//     url: '/modules/common/action/ComboAction/queryDic.htm',
//     method: 'get',
//     data: {
//         typeCode: "HOUSE_MARKET_CERTIFY"
//     },
//     type: 'json',
//     callback: (result) => {
//         PropertyListedProofList = result.data;
//     }
// });
// var PropertyPropertiesList = [];
// Utils.ajaxData({
//     url: '/modules/common/action/ComboAction/queryDic.htm',
//     method: 'get',
//     data: {
//         typeCode: "PROPERTY_TYPE"
//     },
//     type: 'json',
//     callback: (result) => {
//         PropertyPropertiesList = result.data;
//     }
// });
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
            propertyAddressId: '110101',
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
    onChangea(checkedValues) {

    },
    // getPropertyPropertiesList() {
    //     return PropertyPropertiesList.map((item, index) => {
    //         return <Option key={item.value} >{item.text}</Option>
    //     })
    // },
    // getPropertyListedProofList() {
    //     return PropertyListedProofList.map((item, index) => {
    //         return <Option key={item.value} >{item.text}</Option>
    //     })
    // },
    // getPurposesList() {
    //     return PurposesList.map((item, index) => {
    //         return <Option key={item.value} >{item.text}</Option>
    //     })
    // },
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
        var affor, purchase, property;
        var propertyProperties = this.props.form.getFieldsValue().propertyProperties;
        if (propertyProperties == 2) {//经济适用房
            affor = (
                <Col span="7">
                    <FormItem  {...formItemLayout} label="经适房房本时间：" >
                        <DatePicker  wrapperCol={{ span: "7" }} {...getFieldProps('affordableRoomTime', { rules: [{ required: true, message: '必填', type: "date" }] }) }  disabled={!props.canEdit}/>
                    </FormItem>
                </Col>
            )
            purchase = (
                <Col span="7">
                    <FormItem  {...formItemLayout} label="经适房购房发票时间：">
                        <DatePicker  wrapperCol={{ span: "7" }} {...getFieldProps('purchaseInvoicesTime', { rules: [{ required: true, message: '必填', type: "date" }] }) }  disabled={!props.canEdit}/>
                    </FormItem>
                </Col>
            )
        } else if (propertyProperties == 7) {//房改房
            property = (
                <Col span="7">
                    <FormItem  {...formItemLayout} label="房改房上市证明：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                        <Select   {...getFieldProps('propertyListedProof', { rules: [{ required: true, message: '必填', type: "number" }] }) }  disabled={!props.canEdit}>
                            {PropertyListedProofList}
                        </Select>
                    </FormItem>
                </Col>
            )
        }

        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />

                <Row>
                    <Col span="17">
                        <FormItem  labelCol={{ span: 3 }} wrapperCol={{ span: 18 }} label={<span className='ant-form-item-required'>房产地址：</span>}>
                            <Region {...getFieldProps('propertyAddressId', { initialValue: this.state.propertyAddressId, onChange: this.changeAreaId.bind(this, "propertyAddressId") }) }   disabled={!this.props.canEdit}/>
                        </FormItem>
                    </Col>
                    <Col span="4" >
                        <FormItem style={{ marginLeft: "-84px" }}>
                            <Input disabled={!props.canEdit} placeholder=""  {...getFieldProps('propertyAddress', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  label="房产性质：">
                            <Select disabled={!props.canEdit} {...getFieldProps('propertyProperties', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                {PropertyPropertiesList}
                            </Select>
                        </FormItem>
                    </Col>
                    {property}
                </Row>
                <Row>
                    {affor}
                    {purchase}
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  label="规划用途：">
                            <Select disabled={!props.canEdit} {...getFieldProps('planningPurposes', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                {PurposesList}
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
AddlWin = createForm()(AddlWin);
export default AddlWin;
