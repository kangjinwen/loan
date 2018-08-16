//基本信息
import React from 'react';
import {
  Button,
  Form,
  Input,
  Modal,
  Select,
  Row,
  Col,
  DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
import BankCard from '../utils/BankCard';//银行卡4位加空格
var Reflux = require('reflux');
var UploadPhoto = require("./UploadPhoto");
import MicroInput from '../utils/MicroInput';//数字金额加逗号

var AddInsuWin = React.createClass({

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
      <Form horizontal  form={this.props.form} >
        <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
        <div id="s1">
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="客户姓名：">
                <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="借款金额：">
                <MicroInput disabled={true}  {...getFieldProps('account') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="借款期限：">
                <Input disabled={true}  {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="放款卡号:">
                <BankCard disabled={!props.canEdit}  {...getFieldProps('creditCardNumber',
                  {
                    rules: [
                      { message: '只能是数字', pattern: /^(\d{0,130})$/ }
                    ]
                  }
                ) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="三方收款卡号:">
                <BankCard disabled={!props.canEdit}  {...getFieldProps('thirdCardNumber',
                  {
                    rules: [
                      { message: '只能是数字', pattern: /^(\d{0,130})$/ }
                    ]
                  }
                ) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>

          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="开户名:">
                <Input disabled={!props.canEdit}  {...getFieldProps('bankAccount') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="开户名:">
                <Input disabled={!props.canEdit}  {...getFieldProps('thirdBankAccount') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>

          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="开户银行(支行):">
                <Input disabled={!props.canEdit}  {...getFieldProps('accountOpening') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="开户银行(支行):">
                <Input disabled={!props.canEdit}  {...getFieldProps('thirdAccountOpening') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <UploadPhoto ref="RoomThisPhoto" title="" btype="CardNumber" canEdit={props.canEdit} selectRecord={props.record} style={{ marginLeft: "70px" }}/>
            </Col>
            <Col span="12">
              <UploadPhoto ref="RoomThisPhoto" title="" btype="ThirdCardNumber" canEdit={props.canEdit} selectRecord={props.record} style={{ marginLeft: "70px" }}/>
            </Col>
          </Row>
          <Row>
            <Col span="17">
              <FormItem  labelCol={{ span: 5 }} wrapperCol={{ span: 18 }}  label="备注：">
                <Input disabled={!props.canEdit} rows="2" {...getFieldProps('remark') } type="textarea" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="合同编号：">
                <Input disabled={true}  {...getFieldProps('chargeNumber') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="出借人：">
                <Input disabled={true}  {...getFieldProps('lender') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="出借人的受托人：">
                <Input disabled={true}  {...getFieldProps('trusteeOfLender') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="他项权利证可领取时间：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
                <DatePicker disabled={!props.canEdit} {...getFieldProps('collectionTime') } />
              </FormItem>
            </Col>
          </Row>
          <Row>
          </Row>
          <Row>
            <Col span="12">
              <UploadPhoto ref="RoomThisPhoto" style={{ marginLeft: 80 }} title="抵押回执单" btype="MortgageReceipt" canEdit={props.canEdit} selectRecord={props.record}/>
            </Col>
          </Row>
        </div>
      </Form>
    );
  }
});
AddInsuWin = createForm()(AddInsuWin);
export default AddInsuWin;
