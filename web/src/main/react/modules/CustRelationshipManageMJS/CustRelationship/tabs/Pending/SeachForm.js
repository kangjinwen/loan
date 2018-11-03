import React from 'react';
import {
  Button,
  Form,
  Input,
  Select,
  DatePicker,
  Row
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;

let SeachForm = React.createClass({
  getInitialState() {
    return {
      repaymentTimeStart: null,
      repaymentTimeEnd: null
    }
  },
  handleQuery() {
    var params = this.props.form.getFieldsValue();
    this.props.passParams({
      searchParams: JSON.stringify(params),
      pageSize: 10,
      currentPage: 1,
    });
  },
  handleReset() {
    this.props.form.resetFields();
    this.setState({
      repaymentTimeStart: null,
      repaymentTimeEnd: null
    })
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    return (
      <Form inline >
          <FormItem label="客户名称：">
              <Input  {...getFieldProps('customerName')} />
          </FormItem>
          <FormItem label="联系电话：">
              <Input  {...getFieldProps('mobile')} />
          </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;
