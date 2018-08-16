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
const Option = Select.Option;
const FormItem = Form.Item;
var Region = require('../plugin/Region');
import ComboData from '../utils/ComboData';
var MarryStatuslist = ComboData.getIntCombo('MARITAL_STATUS');
var HousingInformation = React.createClass({
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
    var must, faceJust;
    var record = this.props.record
    if (record && record.processStateCode == "usertask-face-audit") {//面审不可修改贷款人姓名：贷款人身份证号：
      faceJust = true;
    } else {
      faceJust = !props.canEdit;
    }
    if (record && record.processStateCode == "usertask-businessExchange" || record && record.processStateCode == "usertask-face-audit") {
      must = [
        <Col span="7" key="1">
          <FormItem  {...formItemLayout} label="贷款人姓名：">
            <Input disabled={faceJust}  {...getFieldProps('name', { rules: [{ required: true, message: '必填' }] }) }  type="text" autoComplete="off" />
          </FormItem>
        </Col>,
        <Col span="7" key="2">
          <FormItem  {...formItemLayout} label="贷款人身份证号:" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
            <Input disabled={faceJust}  {...getFieldProps('certNumber',
              {
                rules: [
                  { required: true, message: '必填' }, { validator: validator.checkIdCode }]
              }) }  autoComplete="off" />
          </FormItem>
        </Col>
      ]
    } else {
      must = [
        <Col span="7" key="3">
          <FormItem  {...formItemLayout} label="贷款人姓名：">
            <Input disabled={faceJust}  {...getFieldProps('name') }  type="text" autoComplete="off" />
          </FormItem>
        </Col>,
        <Col span="7" key="4">
          <FormItem  {...formItemLayout} label="贷款人身份证号：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
            <Input disabled={faceJust}  {...getFieldProps('certNumber',
              {
                rules: [
                  { message: '身份证号不正确' }, { validator: validator.checkIdCode }]
              }) }  autoComplete="off" />
          </FormItem>
        </Col>
      ]
    }
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Input  {...getFieldProps('id', { initialValue: '' }) }  type="hidden" />
        <Row>
          {must}
          <Col span="7">
            <FormItem  {...formItemLayout} label="贷款人性别：">
              <Select  disabled={!props.canEdit} {...getFieldProps('sex') } >
                <Option  value={0}>男</Option>
                <Option  value={1}>女</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="7">
            <FormItem  {...formItemLayout} label="贷款人年龄：">
              <Input disabled={!props.canEdit}  {...getFieldProps('age') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="7">
            <FormItem  {...formItemLayout} label="贷款人联系电话：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
              <Input disabled={!props.canEdit}  {...getFieldProps('phone', {
                rules: [{ message: '手机号格式不正确', pattern: /^1[3|4|5|7|8]\d{9}$/, }]
              }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="7">
            <FormItem  {...formItemLayout}  label="婚姻状况：">
              <Select disabled={!props.canEdit} {...getFieldProps('marryStatus') } >
                {MarryStatuslist}
              </Select>
            </FormItem>
          </Col>
          <Col span="7">
            <FormItem  {...formItemLayout} label="配偶姓名：">
              <Input disabled={!props.canEdit}  {...getFieldProps('spouseName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="7">
            <FormItem  {...formItemLayout} label="配偶身份证号：">
              <Input disabled={!props.canEdit}  {...getFieldProps('spouseCertNumber',
                {
                  rules: [
                    { message: '身份证号不正确' }, { validator: validator.checkIdCode }]
                }
              ) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="7">
            <FormItem  {...formItemLayout}  label="配偶联系电话：">
              <Input disabled={!props.canEdit}  {...getFieldProps('spousePhone',
                {
                  rules: [{ message: '手机号格式不正确', pattern: /^1[3|4|5|7|8]\d{9}$/, }]
                }
              ) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="7">
            <FormItem  {...formItemLayout} label="共借人：">
              <Input disabled={!props.canEdit}  {...getFieldProps('totalBorrowed') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="7">
            <FormItem  {...formItemLayout} label="共借人身份证号：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
              <Input disabled={!props.canEdit}  {...getFieldProps('totalBorrowedCertNumber',
                {
                  rules: [
                    { message: '身份证号不正确' }, { validator: validator.checkIdCode }]
                }
              ) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="17">
            <FormItem  labelCol={{ span: 3 }} wrapperCol={{ span: 18 }} label="居住地(省)：">
              <Region {...getFieldProps('residentialAddressId', { initialValue: this.state.residentialAddressId, onChange: this.changeAreaId.bind(this, "residentialAddressId") }) }   disabled={!this.props.canEdit}/>
            </FormItem>
          </Col>
          <Col span="4" >
            <FormItem style ={{ marginLeft: "-84px" }}>
              <Input disabled={!props.canEdit} placeholder="详细地址"  {...getFieldProps('residentialAddress') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="19">
            <FormItem  {...formItemLayout} label="备注："  labelCol={{ span: "3" }}>
              <Input disabled={!props.canEdit}  {...getFieldProps('remark') } type="textarea" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
      </Form >
    )
  }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
