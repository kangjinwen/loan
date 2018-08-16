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
    if (data.formData) {
      this.props.form.setFieldsValue(data.formData);
    } else this.props.form.resetFields();
  },
  getInitialState() {
    return {
      status: {
        automaticWithholdingTime:""
      },
      formData: {}
    };
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
                  <div id="s2">
                    <h2>下户费退费</h2>
                      <Row>
                        <Col span="12">
                          <FormItem  {...formItemLayout} label="财务专员：">
                            <Input disabled={true}  {...getFieldProps('financeSpecialist') } type="text" autoComplete="off" />
                          </FormItem>
                            <FormItem  {...formItemLayout} label="收取形式：">
                                <Select  disabled={true} {...getFieldProps('state') } >
                                  <Option value="0">线上</Option>
                                  <Option value="1">现金</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                          <FormItem  {...formItemLayout} label="实收下户费：">
                            <Input disabled={true}  {...getFieldProps('actualFee') } type="text" autoComplete="off" />
                          </FormItem>
                           <FormItem  {...formItemLayout} label="退费申请操作员：">
                            <Input disabled={true}  {...getFieldProps('refundOperator') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="12">
                          <FormItem  {...formItemLayout} label="申请退费时间：">
                            <Input disabled={true}  {...getFieldProps('applyRefundTime') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="12">
                          <FormItem  {...formItemLayout} label="申请退费金额：">
                            <Input disabled={true}  {...getFieldProps('applyRefundAmount') } type="text" autoComplete="off" />
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
