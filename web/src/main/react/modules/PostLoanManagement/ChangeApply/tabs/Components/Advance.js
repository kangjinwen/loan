//放款信息
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
  changeValue(event, value) {
    var aheadRepayRate = event.target.value;
    var advance = this.props.advance;
    var penalty;
    if (advance) {
      penalty = (aheadRepayRate * 100 / 10000 * advance).toFixed('2');//提前还款违约金=提前还款利率*借款金额,
      this.props.form.setFieldsValue({ penaltySum: penalty })
    }
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
      <Form horizontal  form= { this.props.form } style= {{ marginTop: "20px" }}>
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
              <DatePicker disabled={true}  {...getFieldProps('startRepayTime') } type="text" autoComplete="off" />
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
              <DatePicker disabled={props.canEdit}  {...getFieldProps('makeRepaymentTime') } />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="提前还款违约金(元)：" labelCol={{ span: "11" }} wrapperCol={{ span: "12" }}>
              <MicroInput disabled={true}  {...getFieldProps('penaltySum') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="提前还款利率(%)：">
              <Input disabled={props.canEdit}  {...getFieldProps('aheadRepayRate',
               {
                onChange: this.changeValue,
                rules: [
                  { required: true, message: '必填只能是数字', type: 'float' },
                  { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                ]
              }) }  autoComplete="off" />
            </FormItem>
          </Col>
        </Row >
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
              <Input  disabled={props.canEdit}  {...getFieldProps('remark1') } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
            </FormItem>
          </Col>
        </Row>
      </Form >
    )
  }
});
Advance = createForm()(Advance);
export default Advance;
