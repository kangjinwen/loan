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
const NavLine = require("../utils/NavLine");
import ComboData from '../utils/ComboData';//获取字典
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var repaymentTypelist = ComboData.getIntCombo('REPAYMENT_TYPE');//还款方式：
var roleId = window.roleId;

var InstitutionNamelist = [];
Utils.ajaxData({
    url: '/modules/system/getOfficeListByTypeAndParentId.htm',
    method: 'get',
    data: {
        type: "5"
    },
    type: 'json',
    callback: (result) => {
        InstitutionNamelist = result.data;
    }
});
var BorrowingNeeds = React.createClass({
    getInitialState() {
        return {
            status: {
                automaticWithholdingTime: ""
            },
            formData: {}
        };
    },
    componentDidMount() {
        var me = this;
        var ProductIdList = [];
        Utils.ajaxData({
            url: '/modules/common/action/ComboAction/getAllProductSimpleInfos.htm',
            method: 'get',
            type: 'json',
            callback: (result) => {
                ProductIdList = result.data;
                var items = result.data.map((item) => {
                    return (<Option key={item.value} value={item.value}>{item.text}</Option>);
                });
                ProductIdList = items;
                me.setState({ ProductIdList: ProductIdList });
            }
        });
    },

    handleReset() {
        this.refs.validation.reset();
        this.setState(this.getInitialState());
    },
    onChange(name, value) {
        if (name == "productId") {
            Utils.ajaxData({
                url: '/modules/common/action/ComboAction/queryProductAbout.htm',
                method: 'post',
                data: {
                    productId: value
                },
                type: 'json',
                callback: (result) => {

                    result.data.repaymentRate = result.data.repaymentRate * 100,
                        this.props.form.setFieldsValue(result.data)
                }
            });
        }
    },
    geInstitutionNamelist() {
        return InstitutionNamelist.map((item, index) => {
            return <Option key={item.name} >{item.name}</Option>
        })
    },
    getProjectBelongslist() {
        return ProjectBelongslist.map((item, index) => {
            return <Option key={item.value} >{item.text}</Option>
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
        var disabled = !props.canEdit, disabledaAccount = !props.canEdit, disabledCollectionRate = !props.canEdit;
        if (this.props.record) {
            var processStateCode = this.props.record.processStateCode;
            if (processStateCode == "usertask-face-audit" || processStateCode == "usertask-final-audit" || processStateCode == "usertask-recheck") {//面审前确认面审终审复审
                disabled = false
            }
            if (processStateCode == "usertask-face-audit") {//面审
                disabledCollectionRate = false
            }
            if (processStateCode == "usertask-householdConfirm") {
                disabledaAccount = false;
            }
            if (this.props.title == '查看') {
                disabled = true;
                disabledaAccount = true;
                disabledCollectionRate = true;
            }
        }
        var disabledBelongs, disabledInstitutionName, organ;
        var roleId = window.roleId;//declarationStaff  报单专员//customerServiceStaffB  商务专员
        if (roleId == "customerServiceStaffB") {
            if (this.props.title == "新增申请" || this.props.title == "编辑") {
                disabledBelongs = !props.canEdit;
                disabledInstitutionName = !props.canEdit;
                organ = (
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="机构名称：">
                            <Select  disabled={disabledInstitutionName} showSearch optionFilterProp="children" notFoundContent="无法找到" {...getFieldProps('institutionName', ) } >
                                {this.geInstitutionNamelist() }
                            </Select>
                        </FormItem>
                    </Col>
                );
            } else if (this.props.record.roleName == "商务专员") {
                disabledBelongs = !props.canEdit;
                disabledInstitutionName = !props.canEdit;
                organ = (
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="机构名称：">
                            <Select  disabled={disabledInstitutionName} {...getFieldProps('institutionName', ) } >
                                {this.geInstitutionNamelist() }
                            </Select>
                        </FormItem>
                    </Col>
                );
            } else {
                disabledBelongs = true;
                disabledInstitutionName = true;
                organ = (
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="机构名称：">
                            <Input disabled={disabledInstitutionName}  {...getFieldProps('institutionName') } type="text" autoComplete="off"  />
                        </FormItem>
                    </Col>
                );
            }
        } else {
            disabledBelongs = true;
            disabledInstitutionName = true;
            organ = (
                <Col span="7">
                    <FormItem  {...formItemLayout} label="机构名称：">
                        <Input disabled={disabledInstitutionName}  {...getFieldProps('institutionName') } type="text" autoComplete="off"  />
                    </FormItem>
                </Col>
            );
        }
        var finan;
        var instit;
        var projectBelongs = this.props.form.getFieldsValue().projectBelongs;
        if (projectBelongs == 2) {
            instit = (
                <Row>
                    {organ}
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="报单人：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('salesman') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="联系电话(报单机构)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('mobile') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            )
        } else if (projectBelongs == 3) {
            finan = (
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="报单人：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('salesman') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="联系电话(报单人)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('mobile') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            )
        }
        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="申请额度(元)：">
                            <MicroInput disabled={disabledaAccount} style={{ width: "100%" }} {...getFieldProps('account',
                                {
                                    rules: [
                                        { required: true, message: '必填只能是数字', type: 'float' },
                                        { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                                    ]
                                }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="订单类型：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('productId', { rules: [{ required: true, message: '必填', type: "number" }], onChange: this.onChange.bind(this, "productId") }) } >
                                {this.state.ProductIdList }
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="还款方式：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('repaymentType', ) } >
                                {repaymentTypelist }
                            </Select>
                        </FormItem>
                    </Col>

                </Row>
                <Row>

                    <Col span="7">
                        <FormItem  {...formItemLayout} label="借款期限(月)：">
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('timeLimit',
                                {
                                    rules: [
                                        { required: true, message: '必填只能是数字', type: 'number' },
                                        { message: '不能为负数', pattern: /^\d+(\.\d+)?$/ },
                                        { message: '不能大于4位数', pattern: /^\d{1,4}$/ }
                                    ]
                                }) }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="底点利率(%)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('repaymentRate',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                    ]
                                }) }  type="number" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="成单利率(%)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('singleRate',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                    ]
                                }) } type="number" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>

                    <Col span="7">
                        <FormItem  {...formItemLayout} label="项目来源：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('projectBelongs') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                {finan}
                {instit}
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout}  label="借款用途：" style={{ width: "800px" }} labelCol={{ span: "3" }}>
                            <Input disabled={disabled}   {...getFieldProps('borrowUse') } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="还款来源：" style={{ width: "800px" }} labelCol={{ span: "3" }}>
                            <Input disabled={disabled}  {...getFieldProps('repaymentSource') } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="代收服务费利率(%)：" labelCol={{ span: "12" }} wrapperCol={{ span: "11" }}>
                            <Input disabled={disabledCollectionRate}  {...getFieldProps('collectionRate',
                                {
                                    rules: [
                                        { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                    ]
                                }) } autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="代收服务费金额(元)：" labelCol={{ span: "12" }} wrapperCol={{ span: "11" }}>
                            <MicroInput disabled={disabledCollectionRate}  {...getFieldProps('collectionServiceFee',
                                {
                                    rules: [
                                        { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
BorrowingNeeds = createForm()(BorrowingNeeds);
export default BorrowingNeeds;