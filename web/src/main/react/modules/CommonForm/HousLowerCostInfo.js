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
var FormDataStore = require('./stores/FormDataStore');
const NavLine = require("../utils/NavLine");
var typeIdList = [];
Utils.ajaxData({
  url: '/getDicts.htm?type=BANK_TYPE',
  method: 'get',
  type: 'json',
  callback: (result) => {
    typeIdList = result.data;
  }
});
var AddInsuWin = React.createClass({
  mixins: [
    Reflux.listenTo(FormDataStore, "onMatch")
  ],
  onMatch(data) {
    console.log(data)
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
    console.log(field, 'change', value);
    this.setState({
      [field]: value,
    });
  },
  componentDidMount() {
    var record = {};
    if (this.props.record) {
      var record = this.props.record;
    }
    this.props.form.setFieldsValue(record);
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
      <div style={{ position: "relative" }}>
        <div className="col-22 navLine-wrap-left" >
          <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
            <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden" />
            <div id="s2">
              <h2>退费申请</h2>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="退费申请操作员：">
                    <Input disabled={true}  {...getFieldProps('financeSpecialist') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="实收下户费：">
                    <Input disabled={true}  {...getFieldProps('actualFee', ) } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="下户费收取形式:">
                    <Select disabled={true} {...getFieldProps('receiveType') } >
                      <Option value={0}>线上收取</Option>
                      <Option value={1}>线下收取</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="申请退费金额(元)：">
                    <Input disabled={!props.canEdit}  {...getFieldProps('applyRefundAmount',
                      {
                        rules: [
                          { required: true, message: '必填只能是数字', type: 'float' },
                          { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                        ]
                      }) } type="number" autoComplete="off" />
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="缴纳人：">
                    <Input disabled={true}  {...getFieldProps('payPerson') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
              </Row>
            </div>
          </Form>
        </div>
      </div>
    );
  }
});
AddInsuWin = createForm()(AddInsuWin);
export default AddInsuWin;
