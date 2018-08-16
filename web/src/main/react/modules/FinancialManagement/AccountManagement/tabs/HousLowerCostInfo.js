//审批信息
import React from 'react';
import {
  Button,
  Form,
  Input,
  Modal,
  Select,
  Row,
  Col,
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var PropertySituationList = [];

var ProcessInformation = React.createClass({
  getInitialState() {
    return {
    }
  },
  componentWillReceiveProps(nextProps, nextState) { 
    if (nextProps.record && nextProps.record != this.props.record) {
      this.props.form.setFieldsValue(nextProps.pubLoanProcess);
    }
  },
  componentDidMount() {
    this.props.form.setFieldsValue(this.props.pubLoanProcess);
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
      <div style={{ position: "relative" }}>
        <div className="col-22 navLine-wrap-left" >
          <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
            <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden" />
            <div id="s2">
              <h2>下户费退费</h2>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="财务专员：">
                    <Input disabled={true}  {...getFieldProps('financeSpecialist') } type="text" autoComplete="off" />
                  </FormItem>
                  <FormItem  {...formItemLayout} label="收取形式：">
                    <Select disabled={true} {...getFieldProps('state') } >
                      <Option value="0">线上</Option>
                      <Option value="1">现金</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="实收下户费(元)：">
                    <Input disabled={true}  {...getFieldProps('actualFee') } type="text" autoComplete="off" />
                  </FormItem>
                  <FormItem  {...formItemLayout} label="退费申请操作员：">
                    <Input disabled={true}  {...getFieldProps('refundOperator') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="申请退费时间：">
                    <Input disabled={true}  {...getFieldProps('applyRefundTime') } type="text" autoComplete="off" />
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem  {...formItemLayout} label="申请退费金额(元)：">
                    <Input disabled={true}  {...getFieldProps('applyRefundAmount'), {
                      rules: [
                        { required: true, message: '必填只能是数字', type: 'float' },
                        { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                      ]
                    } } type="text" autoComplete="off" />
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
ProcessInformation = createForm()(ProcessInformation);
export default ProcessInformation;
