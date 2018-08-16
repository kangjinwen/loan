//放宽单据填写-放款单
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
import BankCard from '../utils/BankCard';//银行卡4位加空格
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var BankFlag = [];
Utils.ajaxData({
    url: '/modules/common/action/ComboAction/queryDic.htm',
    method: 'get',
    data: {
        typeCode: "BANK_TYPE"
    },
    type: 'json',
    callback: (result) => {
        BankFlag = result.data;
    }
});
var UploadPhoto = require("./UploadPhoto");
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var HousingInformation = React.createClass({
    getInitialState() {
        return {
            propertyAddressId: '110101',
        }
    },
    onChange(bankCode) {
        Utils.ajaxData({
            url: '/modules/system/sysBankAction/getAccount.htm',
            method: 'get',
            data: { bankCode },
            type: 'json',
            callback: (result) => {

                this.setState({
                })
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
        var account = '';
        if (this.props.housBillBasicInfo) {
            account = this.props.housBillBasicInfo.account;
        }

        return (
            <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="开户人姓名：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('accountHolderName', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="开户银行(支行)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('bankName', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="放款卡号：">
                            <BankCard disabled={!props.canEdit}  {...getFieldProps('cardid',
                                {
                                    rules: [
                                        { message: '只能是数字', pattern: /^(\d{0,100})$/ }
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="放款金额(元)：">
                            <MicroInput disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('account',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: 'float' },
                                        { message: '不能大于10位数/小数点后精确到两位/不能为负数', pattern: /^(0(\.\d{1,2})?|[1-9]\d{0,9}|[1-9]\d{0,10}\.\d{1,2})$/ }
                                    ]
                                }) } autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <UploadPhoto ref="RoomThisPhoto" title="" btype="CardNumber" canEdit={props.canEdit} selectRecord={props.record} style={{ marginLeft: "70px" }}/>
                    </Col>
                </Row>
                <Row style={{ marginTop: 25 }}>
                    <Col span="10" >
                        <FormItem  {...formItemLayout} label="开户名：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('thirdBankAccount', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="开户银行(支行)：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('thirdAccountOpening', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="三方卡号：">
                            <BankCard disabled={!props.canEdit}  {...getFieldProps('thirdCardNumber',
                                {
                                    rules: [
                                        { message: '只能是数字', pattern: /^(\d{0,100})$/ }
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="三方金额(元)：">
                            <MicroInput disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('thirdAccount',
                                {
                                    rules: [
                                        { required: true, message: '必填', type: 'float' },
                                        { message: '不能大于10位数/小数点后精确到两位/不能为负数', pattern: /^(0(\.\d{1,2})?|[1-9]\d{0,9}|[1-9]\d{0,10}\.\d{1,2})$/ }
                                    ]
                                }) } autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="12">
                        <UploadPhoto ref="RoomThisPhoto" title="" btype="ThirdCardNumber" canEdit={props.canEdit} selectRecord={props.record} style={{ marginLeft: "70px" }}/>
                    </Col>
                </Row>
                <Row>
                    <Col span="19">
                        <FormItem  labelCol={{ span: 4 }} wrapperCol={{ span: 18 }}  label="备注：">
                            <Input disabled={!props.canEdit} rows="2" {...getFieldProps('remark') } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>

                {/*<Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="备注：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('remark') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>*/}
                <Input {...getFieldProps('id') } type="hidden" />
            </Form>
        )
    }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
