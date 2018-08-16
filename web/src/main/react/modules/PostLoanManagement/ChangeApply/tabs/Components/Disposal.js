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

var Disposal = React.createClass({
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
            <FormItem  {...formItemLayout}  label="借款金额：">
              <Input disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="剩余本金：">
              <Input disabled={true}  {...getFieldProps('remainderAmount') } type="text" autoComplete="off" />
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
              <Input disabled={true}  {...getFieldProps('lastRepaymentDate') } type="text" autoComplete="off" />
            </FormItem>
          </Col>

        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款期限：">
              <Input disabled={true}  {...getFieldProps('ddd') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="逾期罚息：">
              <Input disabled={true}  {...getFieldProps('dd') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="罚息利率：">
              <Input disabled={true}  {...getFieldProps('fff') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="房屋预处置金额：">
              <Input disabled={props.canEdit}  {...getFieldProps('fffe', {
                rules: [{ required: true, message: '必填', type: "date" },
                  { message: '不能为负数', pattern: /^\d+(\.\d+)?$/ },
                  { message: '不能大于10位数', pattern: /^\d{1,10}$/ }]
              }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
              <Input  disabled={props.canEdit}  {...getFieldProps('rrr', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
Disposal = createForm()(Disposal);
export default Disposal;
