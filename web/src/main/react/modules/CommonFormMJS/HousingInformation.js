//房产信息
import React from 'react';
import {Col, Form, Input, Row, Select} from 'antd';
import ComboData from '../utils/ComboData'; //获取字典
import MicroInput from '../utils/MicroInput'; //数字金额加逗号
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var Region = require('../plugin/Region');
var PurposesList = ComboData.getIntCombo('PLAN_USE');//规划用途
var PropertyPropertiesList = ComboData.getIntCombo('PROPERTY_TYPE');//房产性质：
var PropertySituationList = ComboData.getIntCombo('HOUSING_STATUS');//房屋现状：
var HousingInformation = React.createClass({
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
    componentDidMount() {
        this.props.record && this.props.form.setFieldsValue(this.props.record);
    },
    remarkValidator(rule, value, callback) {
        if (value) {
            if (value.length > 60) {
                callback([new Error('备注过长！')]);
            } else {
                callback();
            }
        } else {
            callback([]);
        }
    },
    sameToLocal() {
        var selectedrecord = this.props.record;
        Utils.ajaxData({
            url: '/modules/common/PubCustomerAction/getPubCustomerByProcessInstanceId.htm',
            data: {
                processInstanceId: selectedrecord.processInstanceId,
                customerId: selectedrecord.id
            },
            callback: (result) => {
                this.setState({
                    propertyAddressId: result.data.areaId
                });
                this.props.form.setFieldsValue({propertyAddress: result.data.liveAddress})
            }
        });
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

        var mortgageSituation = this.props.form.getFieldValue('mortgageSituation');
        var whetherTwoContacta = this.props.form.getFieldValue('whetherTwoContact');
        var aWorth, whetherOne, whetherTwo;
        whetherOne = (
            <div>
                <Row>
                    <Col span="7" key="2">
                        <FormItem  {...formItemLayout} label="是否一抵 ：">
                            <Select disabled={!props.canEdit} {...getFieldProps('whetherOneContact', {
                                initialValue: 0,
                                rules: [{required: true, type: "number", message: '必填'}]
                            })} >
                                <Option key="0" value={0}>是</Option>
                                <Option key="1" value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7" key="3">
                        <FormItem  {...formItemLayout} label="抵押权人：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('againstOneMortgagee')} type="text"
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="7" key="4">
                        <FormItem  {...formItemLayout} label="金额(元)：">
                            <MicroInput disabled={!props.canEdit}
                                        style={{width: "100%"}} {...getFieldProps('againstOneAmount',
                                {
                                    rules: [
                                        {message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/},
                                    ]
                                })} autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="19" key="7">
                        <FormItem  {...formItemLayout} label="一抵备注：" labelCol={{span: "3"}}>
                            <Input
                                disabled={!props.canEdit}  {...getFieldProps('remarkOne', {rules: [{validator: this.remarkValidator}]})}
                                type="textarea" autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
            </div>
        );
        whetherTwo = (
            <div>
                <Row>
                    <Col span="7" key="1">
                        <FormItem  {...formItemLayout} label="是否二抵 ：">
                            <Select disabled={!props.canEdit} {...getFieldProps('whetherTwoContact', {
                                initialValue: 0,
                                rules: [{required: true, message: '必填', type: "number",}]
                            })} >
                                <Option value={0}>是</Option>
                                <Option value={1}>否</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7" key="5">
                        <FormItem  {...formItemLayout} label="抵押权人：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('againstTwoMortgagee')} type="text"
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="7" key="6">
                        <FormItem  {...formItemLayout} label="金额(元)：">
                            <MicroInput disabled={!props.canEdit}
                                        style={{width: "100%"}} {...getFieldProps('againstTwoAmount', {
                                rules: [
                                    {message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/},
                                ]
                            })} type="text" autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="19" key="8">
                        <FormItem  {...formItemLayout} label="二抵备注：" labelCol={{span: "3"}}>
                            <Input
                                disabled={!props.canEdit}  {...getFieldProps('remarkTwo', {rules: [{validator: this.remarkValidator}]})}
                                type="textarea" autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
            </div>
        );
        if (mortgageSituation == 0) {
            aWorth = (
                <div>
                    {whetherOne}
                </div>
            )
        } else if (mortgageSituation == 1) {
            aWorth = (
                <div>
                    {whetherOne}
                    {whetherTwo}
                </div>
            )
        } else if (mortgageSituation == 2) {
            aWorth = ""
        }

        return (
            <Form horizontal form={this.props.form} style={{marginTop: "20px"}}>
                <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>
                <Input  {...getFieldProps('consultId', {initialValue: ''})} type="hidden"/>
                <Row>
                    <Col span="18">
                        <FormItem labelCol={{span: 3}} wrapperCol={{span: 17}}
                                  label={<span className='ant-form-item-required'>房产地址：</span>}>
                            <Region {...getFieldProps('propertyAddressId', {
                                initialValue: this.state.propertyAddressId,
                                onChange: this.changeAreaId.bind(this, "propertyAddressId")
                            })} disabled={!this.props.canEdit}/>
                        </FormItem>
                    </Col>
                    <Col span="3">
                        <FormItem style={{marginLeft: "-84px"}}>
                            <Input disabled={!props.canEdit} placeholder="详细地址"  {...getFieldProps('propertyAddress', {
                                rules: [{
                                    required: true,
                                    message: '必填'
                                }]
                            })} type="text" autoComplete="off"/>
                        </FormItem>
                    </Col>
                    {props.canEdit && (<Col span="2">
                        <FormItem>
                            <div style={{
                                paddingLeft: "20px",
                                cursor: "pointer",
                                color: "#00aaee",
                                fontSize: "13px"
                            }} onClick={this.sameToLocal}>同现居住地
                            </div>
                        </FormItem>
                    </Col>)}

                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="面积(平方)：">
                            <Input disabled={!props.canEdit} style={{width: "100%"}} {...getFieldProps('propertyArea',
                                {
                                    rules: [
                                        {required: true, message: '必填只能是数字', type: "float"},
                                        {message: '不能为负数,保留2位小数', pattern: /^\d+(\.\d{0,2})?$/}
                                    ]
                                })} autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="房产性质：">
                            <Select disabled={!props.canEdit} {...getFieldProps('propertyProperties')} >
                                {PropertyPropertiesList}
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="规划用途：">
                            <Select disabled={!props.canEdit} {...getFieldProps('planningPurposes')} >
                                {PurposesList}
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="房屋现状：">
                            <Select disabled={!props.canEdit} {...getFieldProps('propertySituation')} >
                                {PropertySituationList}
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="房产证号：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('houseNumber')} type="text"
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="房屋抵押情况：">
                            <Select disabled={!props.canEdit} {...getFieldProps('mortgageSituation', {
                                initialValue: 2,
                                rules: [{required: true, message: '必填', type: "number"}]
                            })} >
                                <Option value={0}>一抵</Option>
                                <Option value={1}>二抵</Option>
                                <Option value={2}>无抵押</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                {aWorth}
            </Form>
        )
    }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
