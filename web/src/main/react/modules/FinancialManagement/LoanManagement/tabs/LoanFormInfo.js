//基本信息
import React from 'react';
import {Col, DatePicker, Form, Input, Row, Select} from 'antd';
import BankCard from '../../../utils/BankCard'; //银行卡4位加空格
import MicroInput from '../../../utils/MicroInput'; //数字金额加逗号
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var UploadPhoto = require("../../../CommonForm/UploadPhoto");
var LoanFormInfo = React.createClass({
    getInitialState() {
        return {
            residentialAddressId: '110101',
            bankAccount: []
        }
    },
    onChange(bankCode) {
        Utils.ajaxData({
            url: '/modules/system/sysBankAction/getAccount.htm',
            method: 'get',
            data: {bankCode},
            type: 'json',
            callback: (result) => {

                this.setState({
                    bankAccount: result.data ? result.data : []
                })
                this.props.form.setFieldsValue({bankCardId: result.data[0].account});//account字段的值。
            }
        });
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
    chooseDate(date, dateString) {
        this.props.form.setFieldsValue({loanTime:dateString})
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
        var disableds = true;
        if (props.title == "放款操作") {
            disableds = props.canEdit;
        }
        return (
            <Form horizontal form={this.props.form} style={{marginTop: "20px"}}>


                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="开户人姓名：">
                            <Input disabled={props.canEdit}  {...getFieldProps('customerName')} type="text"
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="开户银行(支行)：">
                            <Input disabled={props.canEdit}  {...getFieldProps('bankName')} type="text"
                                   autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="放款卡号：">
                            <BankCard disabled={props.canEdit}  {...getFieldProps('bankAccount')} type="text"
                                      autoComplete="off"/>
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="放款金额(元)：">
                            <MicroInput disabled={props.canEdit} style={{width: "100%"}} {...getFieldProps('account',)}
                                        autoComplete="off"/>
                        </FormItem>
                    </Col>
                </Row>


                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="放款时间：">
                            <DatePicker format="yyyy-MM-dd"
                                        disabled={props.canEdit}  {...getFieldProps('loanTime', {
                                rules: [{
                                    required: true,
                                    message: '必填',
                                    type: "date"
                                }], onChange: this.chooseDate
                            })} style={{width: 273}}/>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
LoanFormInfo = createForm()(LoanFormInfo);
export default LoanFormInfo;
