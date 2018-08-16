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
    var disableds=true; 
    if(props.title=="返佣划扣"){
      disableds=props.canEdit;
    }
    return (
      <div style={{ position: "relative" }}>
            <div className="col-22 navLine-wrap-left" >
              <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                  <div id="s1">
                    <h2>基本信息</h2>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="项目编号：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('name') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="借款名称：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('idcard') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="客户名称：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('email') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="贷款金额：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('city') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="贷款期限：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('creditItgaHgstLine') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="客户还款期次：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('creditItgaHgstLine') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="客户应还款日期：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('creditItgaHgstLine') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="客户实际还款日期：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('creditItgaHgstLine') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="客户应还款金额：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('creditItgaHgstLine') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="客户实际还款金额：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('creditItgaHgstLine') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>    
                  </div>
                  <div id="s2">
                    <h2>返佣划扣</h2>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="财务专员：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="金融顾问/分销商名称：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                       <Col span="10">
                          <FormItem  {...formItemLayout} label="返费点位：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="应返佣金额：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('email') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                       <Row>
                       <Col span="10">
                          <FormItem  {...formItemLayout} label="返佣审批金额：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="返佣期限：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('city') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="返佣卡号：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="开户行：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                       <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="实际返佣金额：">
                            <Input disabled={disableds}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
                          </FormItem>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="10">
                           <FormItem  {...formItemLayout} label="处理意见：">
                            <Select  disabled={disableds} {...getFieldProps('bankPrepayment', { rules: [{ required: true, message: '必填' }] }) } >
                              <Option value="0">是</Option>
                              <Option value="1">否</Option>
                            </Select>
                    </FormItem>
                        </Col>
                      </Row>
                       <Row>
                        <Col span="10">
                          <FormItem  {...formItemLayout} label="备注：">
                            <Input disabled={disableds}  {...getFieldProps('n3mPaymentRate') } type="text" autoComplete="off" />
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
