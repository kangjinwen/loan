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
const objectAssign = require('object-assign');
var Reflux = require('reflux');
var FormDataStore = require('./../../../CommonForm/stores/FormDataStore');
const NavLine = require("./../../../utils/NavLine");

var typeIdList = [];
Utils.ajaxData({
  url: '/getDicts.htm?type=BANK_TYPE',
  method: 'get',
  type: 'json',
  callback: (result) => {
    typeIdList = result.data;
  }
});
var SettlementInfoWin = React.createClass({
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
        automaticWithholdingTime: ""
      },
      formData: {}
    };
  },
  handleReset() {
    this.refs.validation.reset();
    this.setState(this.getInitialState());
  },
  handleCancel() {
    this.props.form.resetFields();
    this.props.hideModal();
  },
  onChange(field, value) { 
    this.setState({
      [field]: value,
    });
  },
  onStartChange(value) {
    this.onChange('automaticWithholdingTime', value);
  },
  handleStartToggle({ open }) {
    if (!open) {
      this.setState({ endOpen: true });
    }
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
        <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
        <div id="s1">
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="项目编号：">
                <Input disabled={true}  {...getFieldProps('projectCode') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="项目名称：">
                <Input disabled={true}  {...getFieldProps('projectName') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="借款人：">
                <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="借款金额(小写/元)：" >
                <Input disabled={true} {...getFieldProps('borrowAccount',) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="借款期限(月)：">
                <Input disabled={true}  {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="借款金额(大写)：">
                <Input disabled={true}  {...getFieldProps('borrowAccountChinese') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="11">
              <FormItem  {...formItemLayout} label="还款起始：">
                <Input disabled={true}  {...getFieldProps('repaymentStartTime') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="11">
              <FormItem  {...formItemLayout} label="还款结束：">
                <Input disabled={true}  {...getFieldProps('repaymentEndTime') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
        </div>
      </Form>
    );
  }
});
SettlementInfoWin = createForm()(SettlementInfoWin);
export default SettlementInfoWin;
