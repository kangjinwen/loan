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
    InputNumber,
    Checkbox,
    DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var Reflux = require('reflux');
var FormDataStore = require('./stores/FormDataStore');
var ThirdAnRong = require('./ThirdAnRong');
var UploadPhoto = require("../CommonForm/UploadPhoto");
import MicroInput from '../utils/MicroInput';//数字金额加逗号
const RadioGroup = Radio.Group;
const CheckboxGroup = Checkbox.Group;
var EnterprisesCategorylist = [];
Utils.ajaxData({
    url: '/modules/common/action/ComboAction/queryDic.htm',
    method: 'get',
    data: {
        typeCode: "ATTENTION_ENTERPRISE_TYPE"
    },
    type: 'json',
    callback: (result) => {
        EnterprisesCategorylist = result.data;
    }
});
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
    getEnterprisesCategorylist() {
        return EnterprisesCategorylist.map((item, index) => {
            return <Option key={item.value} >{item.text}</Option>
        })
    },
    showModalResultWin(title, canEdit) {
        var record = this.props.record
        this.setState({
            title,
            visibleResult: true,
            canEdit: canEdit,
            record: record,
        });
    },
    hideModalCheck() {
        this.setState({
            visibleResult: false
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
        var params = {};
        params.searchParams = {};
        var record = state.record;
        if (record) {
            params.searchParams.projectId = record.projectId;
            params.searchParams.consultId = record.consultId;
            params.pageSize = 5;
            params.currentPage = 1;
        }
        params.searchParams = JSON.stringify(params.searchParams);
        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="一抵性质：">
                            <Input disabled={!props.canEdit} {...getFieldProps('arrivedNature') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="一抵银行名称：">
                            <Input disabled={!props.canEdit} {...getFieldProps('againstBankName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="一抵金额(元)：">
                            <MicroInput disabled={!props.canEdit} {...getFieldProps('arrivedAmount',
                                {
                                    rules: [
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="二抵性质：">
                            <Input disabled={!props.canEdit} {...getFieldProps('twoArrivedNature') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="二抵银行名称：">
                            <Input disabled={!props.canEdit} {...getFieldProps('twoAgainstBankName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="二抵金额(元)：">
                            <MicroInput disabled={!props.canEdit} {...getFieldProps('twoArrivedAmount',
                                {
                                    rules: [
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="转单性质：">
                            <Input disabled={!props.canEdit} {...getFieldProps('threeArrivedNature') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="转单银行名称：">
                            <Input disabled={!props.canEdit} {...getFieldProps('threeAgainstBankName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="转单金额(元)：">
                            <MicroInput disabled={!props.canEdit} {...getFieldProps('threeArrivedAmount',
                                {
                                    rules: [
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="评估值(元)：">
                            <MicroInput disabled={!props.canEdit} style={{ width: "100%", }} {...getFieldProps('assessedValue') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="快出价(元)：">
                            <MicroInput disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('fastBid') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <ThirdAnRong ref="ThirdAnRong"  visible={this.state.visibleResult} formData={state.formData}
                    title={state.title} hideModal={this.hideModalCheck} params={params} canEdit={state.canEdit} />
            </Form>

        );

    }

});
AddlWin = createForm()(AddlWin);
export default AddlWin;
