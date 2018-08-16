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
  DatePicker
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;

import MicroInput from './../../../../utils/MicroInput';//数字金额加逗号
var Advance = React.createClass({
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
              <MicroInput disabled={true}  {...getFieldProps('capitalSum') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="贷款起始日期：">
              <DatePicker disabled={true}  {...getFieldProps('startTime') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款到期日期：">
              <DatePicker disabled={true}  {...getFieldProps('endRepayTime') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款期限(月)：">
              <Input disabled={true}  {...getFieldProps('timeLimit') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="目前期次：">
              <Input disabled={true}  {...getFieldProps('period') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="预约还款日期：">
              <DatePicker disabled={true}  {...getFieldProps('makeRepaymentTime') } style={{ width: "274px" }}/>
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="提前还款违约金(元)：">
              <MicroInput disabled={true}  {...getFieldProps('penaltySum') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
Advance = createForm()(Advance);
export default Advance;
