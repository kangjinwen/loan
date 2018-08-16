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
      declarationStartDate: null,
      declarationEndDate: null
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
      declarationStartDate: null,
      declarationEndDate: null
    })
  },
  disabledStartDate(declarationStartDate) {
    if (!declarationStartDate || !this.state.declarationEndDate) {
      return false;
    }
    return declarationStartDate.getTime() >= this.state.declarationEndDate.getTime();
  },
  disabledEndDate(declarationEndDate) {
    if (!declarationEndDate || !this.state.declarationStartDate) {
      return false;
    }
    return declarationEndDate.getTime() <= this.state.declarationStartDate.getTime();
  },
  onStartChange(value) {
    this.onChange('declarationStartDate', value);
  },
  onEndChange(value) {
    this.onChange('declarationEndDate', value);
  },
  onChange(field, value) {
    this.setState({
      [field]: value,
    });
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    return (
      <Form inline >
        <FormItem label="项目编号：">
          <Input  {...getFieldProps('projectCode', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="客户名称：">
          <Input  {...getFieldProps('customerName', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="来源：">
          <Select style={{ width: 150 }} {...getFieldProps('businessOriginText') }>
            <Option  value="赚赚自有">赚赚自有</Option>
            <Option  value="报单机构">报单机构</Option>
            <Option  value="报单个人">报单个人</Option>
          </Select>
        </FormItem>
        <FormItem label="机构名称：">
          <Input  {...getFieldProps('institutionName') } />
        </FormItem>
        <FormItem label="报单人：">
          <Input  {...getFieldProps('customerManager', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="报单开始时间：">
          <DatePicker disabledDate={this.disabledStartDate}  {...getFieldProps('declarationStartDate', { onChange: this.onStartChange }) } />
        </FormItem>
        <FormItem label="报单结束时间：">
          <DatePicker disabledDate={this.disabledEndDate}  {...getFieldProps('declarationEndDate', { onChange: this.onEndChange }) } />
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;
