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
const Option = Select.Option;
const FormItem = Form.Item;
var MarryStatuslist = [];
Utils.ajaxData({
  url: '/modules/common/action/ComboAction/queryDic.htm',
  method: 'get',
  data: {
    typeCode: "MARITAL_STATUS"
  },
  type: 'json',
  callback: (result) => {
    MarryStatuslist = result.data;
  }
});
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
  getMarryStatuslist() {
    return MarryStatuslist.map((item, index) => {
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
    var disableds = true;
    if (props.title == "放款操作") {
      disableds = props.canEdit;
    }
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="开户人姓名：">
              <Input disabled={true}  {...getFieldProps('serviceName') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="开户银行 ：">
              <Input disabled={true}  {...getFieldProps('serviceBankName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="银行账户 ：">
              <Select disabled={true}  {...getFieldProps('serviceCard') } >
                {this.getMarryStatuslist() }
              </Select>
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="打款金额(元)：">
              <Input disabled={true}  {...getFieldProps('serviceFee') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
