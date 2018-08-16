import React from 'react';
import {
  Button,
  Form,
  Input,
  Select,
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
      user: JSON.stringify(params),
      pageSize: 10,
      currentPage: 1,
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
        <FormItem label="合同名称：">
          <Input  {...getFieldProps('name', { initialValue: '' }) } />
        </FormItem>
        <FormItem label="生效节点：">
          <Select style={{ width: 150 }} {...getFieldProps('effectiveNode', { initialValue: '' }) }>
            <Option value="0">合同签订</Option>
            <Option value="1">展期合同签订</Option>
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