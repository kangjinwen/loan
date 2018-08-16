
//权证下户--房屋中介信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
    DatePicker,
    InputNumber
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var Region = require('../plugin/Region');
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var MarryStatuslist = [];
Utils.ajaxData({
    url: '/modules/common/action/ComboAction/queryDic.htm',
    method: 'get',
    data: {
        typeCode: "MARITAL_STATUS"
    },
    type: 'json',
    callback: (result) => {
        MarryStatuslist = result.data;
    }
});
var RealtorInformation = React.createClass({
    getInitialState() {
        return {
            residentialAddressId: '110101',
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
    getMarryStatuslist() {
        return MarryStatuslist.map((item, index) => {
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
        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="正常价格(净得/元)：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                            <MicroInput disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('normalPrice',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="房屋快出值(元)：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                            <MicroInput style={{ width: "100%" }} disabled={!props.canEdit}  {...getFieldProps('housingValueFaster',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="税款详情(元)：">
                            <MicroInput disabled={!props.canEdit}  {...getFieldProps('taxDetails',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: "float" },
                                        { message: '不能为负数，不能大于10位数，保留两位', pattern: /^\d{1,10}(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    {/*<Col span="7">
                        <FormItem  {...formItemLayout} label="快速成交价格(净得)：" labelCol={{span:"10"}} wrapperCol={{span:"13"}}>
                            <Input disabled={!props.canEdit}  {...getFieldProps('fastTransactionPrice') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>*/}
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="学校：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('school') } >
                                <Option value={0}>有</Option>
                                <Option value={1}>无</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="医院：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('hospital') } >
                                <Option value={0}>有</Option>
                                <Option value={1}>无</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="购物等配套情况：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
                            <Select  disabled={!props.canEdit} {...getFieldProps('shopping') } >
                                <Option value={0}>有</Option>
                                <Option value={1}>无</Option>
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
            </Form>
        )
    }
});
RealtorInformation = createForm()(RealtorInformation);
export default RealtorInformation;
