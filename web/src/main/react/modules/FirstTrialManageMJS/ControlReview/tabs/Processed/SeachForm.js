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
      payChannelList: [],
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
  componentDidMount() {
    var me = this;
    Utils.ajaxData({
      url: '/getDicts.htm?type=WORKFLOW_STATE',
      method: 'get',
      type: 'json',
      callback: (result) => {
        var items = result.data.map((item) => {
          return (<Option key={item.value} value={String(item.value) }>{item.text}</Option>);
        });
        me.setState({ payChannelList: items });
      }
    });
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    return (
      <Form inline >
        <FormItem label="项目编号：">
          <Input  {...getFieldProps('projectCode') } />
        </FormItem>
        <FormItem label="客户名称：">
          <Input  {...getFieldProps('customerName') } />
        </FormItem>
       
        <FormItem label="报单开始时间：">
          <DatePicker disabledDate={this.disabledStartDate}  {...getFieldProps('declarationStartDate', { onChange: this.onStartChange }) } />
        </FormItem>
        <FormItem label="报单结束时间：">
          <DatePicker disabledDate={this.disabledEndDate}  {...getFieldProps('declarationEndDate', { onChange: this.onEndChange }) } />
        </FormItem>
        <FormItem label="流程状态：">
          <Select style={{ width: "160px" }} {...getFieldProps('flowStatus') }>
            {this.state.payChannelList}
          </Select>
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;
