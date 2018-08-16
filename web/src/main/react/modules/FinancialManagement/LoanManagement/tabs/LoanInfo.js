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

import MicroInput from '../../../utils/MicroInput';//数字金额加逗号
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
    var disableds = true;
    if (props.title == "放款操作") {
      disableds = props.canEdit;
    }
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="项目编号：">
              <Input disabled={props.canEdit}  {...getFieldProps('projectCode') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="借款人：">
              <Input disabled={props.canEdit}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>

        <Row>

          <Col span="10">
            <FormItem  {...formItemLayout} label="抵押情况：">
              <Select  disabled={props.canEdit} {...getFieldProps('mortgageSituation') } >
                <Option value={0}>一抵</Option>
                <Option value={1}>二抵</Option>
                <Option value={2}>无抵押</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="贷款期限：">
              <Input disabled={props.canEdit}  {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>

          <Col span="10">
            <FormItem  {...formItemLayout}  label="底点利率：">
              <Input disabled={props.canEdit}  {...getFieldProps('repaymentRate') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款金额：">
              <MicroInput disabled={props.canEdit}  {...getFieldProps('account') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>

          <Col span="10">
            <FormItem  {...formItemLayout} label="成单利率：">
              <Input disabled={props.canEdit}  {...getFieldProps('singleRate') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
