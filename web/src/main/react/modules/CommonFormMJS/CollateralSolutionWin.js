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
//var UploadPhoto = require("./UploadPhoto");
/*var typeIdList = [];
Utils.ajaxData({
  url: '/getDicts.htm?type=BANK_TYPE',
  method: 'get',
  type: 'json',
  callback: (result) => {
    typeIdList = result.data;
  }
});*/
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
        automaticWithholdingTime: ""
      },
      formData: {}
    };
  },
  handleReset() {
    this.refs.validation.reset();
    this.setState({
      status: {
        automaticWithholdingTime: ""
      }
    })
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
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
        <div id="s1">
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="押品编号：">
                <Input disabled={true}  {...getFieldProps('chargeNumber') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="他项权利证名称：">
                <Input disabled={true}  {...getFieldProps('otherMaterialName') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="抵押权人：">
                <Input disabled={true}  {...getFieldProps('mortgageRight') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="受托人：">
                <Input disabled={true}  {...getFieldProps('client') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          {/* <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="领取时间：">
                <DatePicker disabled={true}  {...getFieldProps('collectionTime') }  style={{ width: "193px" }}/>
              </FormItem>
            </Col>
             <Col span="12">
                  <FormItem  {...formItemLayout} label="登记人：">
                  <Input disabled={true}  {...getFieldProps('registeredPerson') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
          </Row>*/}
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="押品出库时间：">
                <DatePicker disabled={!props.canEdit}  {...getFieldProps('outboundCollectionTime', { rules: [{ required: true, message: '必填', type: "date" }] }) } style={{ width: "193px" }}/>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="出库人：">
                <Input disabled={true}  {...getFieldProps('outboundPerson', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
        </div>
      </Form>
    );
  }
});
AddInsuWin = createForm()(AddInsuWin);
export default AddInsuWin;
