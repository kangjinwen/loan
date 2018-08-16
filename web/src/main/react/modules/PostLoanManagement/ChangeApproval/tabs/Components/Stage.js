//提前还款审批
import React from 'react';
import {
  Button,
  Form,
  Input,
  Modal,
  Select,
  Row,
  Col,
  InputNumber,
  DatePicker
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;

import MicroInput from './../../../../utils/MicroInput';//数字金额加逗号
var Stage = React.createClass({
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
    // var disableds = true;
    // if (props.title == "放款操作") {
    //   disableds = true;
    // }
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="项目编号：">
              <Input disabled={true}  {...getFieldProps('projectCode') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="客户名称：">
              <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="借款金额(元)：">
              <MicroInput disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="剩余本金(元)：">
              <MicroInput disabled={true}  {...getFieldProps('remainderAmount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="贷款起始日期：">
              <Input disabled={true}  {...getFieldProps('startRepayTime') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款到期日期：">
              <Input disabled={true}  {...getFieldProps('endRepayTime') } type="text" autoComplete="off" />
            </FormItem>
          </Col>

        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="贷款期限(月)：">
              <Input disabled={true}  {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="申请展期金额(元)：">
              <MicroInput disabled={true} style={{ width: "100%" }} {...getFieldProps('extensionAmount', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="申请展期期限(月)：">
              <InputNumber disabled={true} style={{ width: "100%" }} {...getFieldProps('extensionCount', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="展期利率(%)：">
              <Input disabled={true} style={{ width: "100%" }} {...getFieldProps('extensionRate', { rules: [{ required: true, message: '必填', type: 'float' }] }) }  type="number" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="展期费(元)：">
              <MicroInput disabled={true} style={{ width: "100%" }} {...getFieldProps('extensionFee', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="展期返佣点位：">
              <Input disabled={true}  {...getFieldProps('extensionReturnfeeRate', { rules: [{ required: true, message: '必填', type: 'number' }] }) }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="申请类型：">
              <Select  disabled={true} {...getFieldProps('processingOpinion') } >
                <Option value={0}>超额展期</Option>
                <Option value={1}>正常展期</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
Stage = createForm()(Stage);
export default Stage;
