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

var Stage = React.createClass({
  getInitialState() {
    return {
      residentialAddressId: '110101',
    }
  },
  //展期费=展期金额*展期利率
  changeextensionAmount(e) {
    var extensionFee = (0.015 * Number(e.target.value)).toFixed(3);//this.props.form.getFieldsValue()获取输入的值
    var extensionAmount = Number(e.target.value)
    this.props.form.setFieldsValue({ extensionFee })
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
          {/*<Col span="10">
            <FormItem  {...formItemLayout} label="项目编号：">
              <Input disabled={true}  {...getFieldProps('projectCode') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>*/}
          <Col span="10">
            <FormItem  {...formItemLayout} label="客户姓名：">
              <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="借款金额(元)：">
              <Input disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="剩余本金(元)：">
              <Input disabled={true}  {...getFieldProps('remainderAmount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="贷款起始日期：">
              <DatePicker disabled={true}  {...getFieldProps('startRepayTime') } type="date" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款到期日期：">
              <DatePicker disabled={true}  {...getFieldProps('endRepayTime') } type="date" autoComplete="off" />
            </FormItem>
          </Col>

        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款期限(月)：">
              <Input disabled={true}  {...getFieldProps('timeLimit') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
       {/* <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="申请展期金额(元)：">
              <Input disabled={props.canEdit}  {...getFieldProps('extensionAmount', {
                onChange: this.changeextensionAmount,
                rules: [
                  { required: true, message: '必填只能是数字', type: 'float' },
                  { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                ]
              }) } type="number" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="申请展期期限(月)：">
              <Input disabled={props.canEdit}  {...getFieldProps('extensionCount', {
                rules: [{ required: true, message: '必填', type: "number" },
                  { message: '不能为负数', pattern: /^\d+(\.\d+)?$/ },
                  { message: '不能大于4位数', pattern: /^\d{1,4}$/ }
                ]
              }) } type="number" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="展期费(元)：">
              <Input disabled={true}  {...getFieldProps('extensionFee', { initialValue: "" }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="展期利率%：">
              <Input disabled={props.canEdit}  {...getFieldProps('extensionRate',
                {
                  rules: [
                    { required: true, message: '必填', type: "float" },
                    { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                  ]
                }
              ) } type="number" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="展期返佣点位：">
              <Input disabled={props.canEdit}  {...getFieldProps('extensionReturnfeeRate', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>*/}
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="申请类型：">
              <Select  disabled={props.canEdit} {...getFieldProps('extendedFlag', { initialValue: 0 }) }>
                <Option value="0">展期申请</Option>
                <Option value="1">发起评估</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
              <Input  disabled={props.canEdit}  {...getFieldProps('remark1') } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
});
Stage = createForm()(Stage);
export default Stage;
