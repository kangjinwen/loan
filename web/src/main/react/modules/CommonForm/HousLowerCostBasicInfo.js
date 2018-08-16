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
var HousLowerCostBasicInfo = React.createClass({
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
          <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
            <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
            <div id="s1">
              <h2>基本信息</h2>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="项目编号：">
                    <Input disabled={true}  {...getFieldProps('projectCode') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="借款名称：">
                    <Input disabled={true}  {...getFieldProps('projectName') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="客户名称：">
                    <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="贷款金额：">
                    <Input disabled={true}  {...getFieldProps('account') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="贷款期限：">
                    <Input disabled={true}  {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
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
HousLowerCostBasicInfo = createForm()(HousLowerCostBasicInfo);
export default HousLowerCostBasicInfo;
