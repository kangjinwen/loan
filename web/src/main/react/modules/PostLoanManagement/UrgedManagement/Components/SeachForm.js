import React from 'react';
import {
  Button,
  Form,
  Input,
  Select,
  DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;

let SeachForm = React.createClass({
  getInitialState() {
    return {
      roleList: []
    }
  },
  handleQuery() {
    var params = this.props.form.getFieldsValue();
    this.props.passParams({
      search: JSON.stringify(params),
      pageSize: 10,
      pageNum: 1,
    });
  },
  handleReset() {
    this.props.form.resetFields();
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
        <FormItem label="项目名称：">
          <Input  {...getFieldProps('projectName') } />
        </FormItem>
        <FormItem label="客户姓名：">
          <Input  {...getFieldProps('customerName') } />
        </FormItem>
        <FormItem label="还款日期：">
          <DatePicker {...getFieldProps('borrowingTime') } style={{ width:180,marginRight:10 }}/>
          至
          <DatePicker {...getFieldProps('borrowingTime2') } style={{ width:180,marginLeft:10}}/>
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;