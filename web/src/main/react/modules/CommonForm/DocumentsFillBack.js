//返费签单 代收服务费
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
    InputNumber
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
import BankCard from '../utils/BankCard';//银行卡4位加空格
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var HousingInformation = React.createClass({
    getInitialState() {
        return {

        }
    },
    changeSingleRate(e) {
        //返费金额 = 批贷金额×返费点位
        var returnFee = (Number(e.target.value) * (this.props.housBillBasicInfo.account) * 10000 / 1000000).toFixed('2');
            this.props.form.setFieldsValue({ returnFee })
        this.props.changeSingleRate(e);
    },
    componentDidMount() {
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
        var returnRate = '', returnFee = '';
        var housBillBasicInfo = this.props.housBillBasicInfo;
        // if (housBillBasicInfo) {
        //     returnRate = Number((housBillBasicInfo.singleRate - housBillBasicInfo.repaymentRate).toFixed('2'));
        //     returnFee = ((returnRate * housBillBasicInfo.account) * 10000 / 1000000).toFixed('2');
        // }
        return (
            <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <div id="s4">
                    <h2>返费签单</h2>
                    <Row>
                        <Col span="10">
                            <FormItem  {...formItemLayout} label="返费人姓名：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('salesman') } type="text" autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="10">
                            <FormItem  {...formItemLayout} label="返佣点位(%)：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('returnRate', {
                                     onChange: this.changeSingleRate,
                                    rules: [{ required: true, message: '必填', type: 'float' },
                                        { message: '不能大于10位数/小数点后精确到两位/不能为负数', pattern: /^(0(\.\d{1,2})?|[1-9]\d{0,9}|[1-9]\d{0,10}\.\d{1,2})$/ }
                                    ]
                                }) } type="number" autoComplete="off" />
                            </FormItem>
                        </Col>
                        <Col span="10">
                            <FormItem  {...formItemLayout} label="月返费金额(元)：">
                                <MicroInput disabled={true}  {...getFieldProps('returnFee') } type="text" autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="10">
                            <FormItem  {...formItemLayout} label="返费期限(月)：">
                                <InputNumber disabled={true} style={{ width: "100%" }} {...getFieldProps('returnLimit') } autoComplete="off" />
                            </FormItem>
                        </Col>
                        <Col span="10">
                            <FormItem  {...formItemLayout} label="返费卡号：">
                                <BankCard disabled={!props.canEdit}  {...getFieldProps('returnCard',
                                    {
                                        rules: [
                                            { message: '只能是数字', pattern: /^(\d{0,100})$/ }
                                        ]
                                    }
                                ) } type="text" autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="10">
                            <FormItem  {...formItemLayout} label="开户银行(支行)：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('returnBankName') } type="text" autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                </div>
            </Form>
        )
    }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
