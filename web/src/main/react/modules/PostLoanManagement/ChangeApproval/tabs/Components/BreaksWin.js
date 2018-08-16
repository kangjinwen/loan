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
    //   disableds = props.canEdit;
    // }
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款期限：">
              <Input disabled={true}  {...getFieldProps('projectCode') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="目前期次：">
              <Input disabled={true}  {...getFieldProps('projectName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="预约还款日期：">
              <DatePicker disabled={props.canEdit}  {...getFieldProps('borrowingTime', { rules: [{ required: true, message: '必填', type: "date" }] }) } style={{ width: "274px" }}/>
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="提前还款违约金：">
              <Input disabled={true}  {...getFieldProps('penaltySum') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="实际提前还款违约金：">
              <Input disabled={props.canEdit}  {...getFieldProps('realPrepaymentPenalty', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
Advance = createForm()(Advance);
export default Advance;
