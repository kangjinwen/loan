//权证下户--下户信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    InputNumber,
    Modal,
    Select,
    Row,
    Col,
    DatePicker
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var Region = require('../plugin/Region');

import ComboData from '../utils/ComboData';//获取字典
var LivingConditionsList = ComboData.getIntCombo('LIVE_STATE');//居住情况：
var FurnishingStatusList = ComboData.getIntCombo('FURNISHING_STATUS');//装修状况：
var NextDoorInformation = React.createClass({
    getInitialState() {
        return {
            propertyAddressId: '110101'
        }
    },
    changeAreaId(name, areaId) {
        this.setState({
            [name]: areaId
        })
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
                    <Col span="17">
                        <FormItem  labelCol={{ span: 3 }} wrapperCol={{ span: 18 }} label={<span className='ant-form-item-required'>房产地址：</span>}>
                            <Region {...getFieldProps('propertyAddressId', { initialValue: this.state.propertyAddressId, onChange: this.changeAreaId.bind(this, "propertyAddressId") }) }   disabled={!this.props.canEdit}/>
                        </FormItem>
                    </Col>
                    <Col span="4" >
                        <FormItem style ={{ marginLeft: "-84px" }}>
                            <Input disabled={!props.canEdit} placeholder=""  {...getFieldProps('propertyAddress', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="小区名称：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('communityName', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="楼牌号是否与房本一致 ：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('buildingBrands', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  label="居住人含：">
                            <Input disabled={!props.canEdit} placeholder=""  {...getFieldProps('livingPeople') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="装修状况：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('furnishingStatus', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                {FurnishingStatusList}
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="6">
                        <FormItem  {...formItemLayout} label="房室户型 (室)：" labelCol={{ span: "10" }} wrapperCol={{ span: "11" }}>
                            <InputNumber disabled={!props.canEdit}  {...getFieldProps('room', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="5" style={{ marginLeft: "-20px" }}>
                        <FormItem  {...formItemLayout} label="(厅)" labelCol={{ span: "5" }} wrapperCol={{ span: "11" }}>
                            <InputNumber disabled={!props.canEdit}  {...getFieldProps('hall', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="5" style={{ marginLeft: "-30px" }}>
                        <FormItem  {...formItemLayout} label="(卫)" labelCol={{ span: "5" }} wrapperCol={{ span: "11" }}>
                            <InputNumber disabled={!props.canEdit}  {...getFieldProps('guard', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="所在楼层：">
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('floor', { rules: [{ required: true, message: '必填', type: 'number' }] }) } autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="总共层数：">
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('totalFloors', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  label="房屋朝向：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('housingOrientation', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  label="居住情况：">
                            <Select disabled={!props.canEdit} {...getFieldProps('livingConditions', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                {LivingConditionsList}
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="租户签署放弃优先购买权：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('purchasingPower', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
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
                        <FormItem  {...formItemLayout} label="权证调查员：">
                            <Input disabled={true}  {...getFieldProps('investigator') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="权证调查日期：">
                            <DatePicker disabled={!props.canEdit}  {...getFieldProps('surveyTime', { rules: [{ required: true, message: '必填', type: "date" }] }) } />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
NextDoorInformation = createForm()(NextDoorInformation);
export default NextDoorInformation;
