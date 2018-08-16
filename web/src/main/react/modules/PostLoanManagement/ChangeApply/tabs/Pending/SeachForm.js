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
      endOpen: false,
      roleList: []
    }
  },
  //期数下拉
  componentDidMount() {
    var me = this;
    Utils.ajaxData({
      url: '/modules/common/action/ComboAction/queryDic.htm?typeCode=BORROW_PEROID',
      method: 'get',
      type: 'json',
      callback: (result) => {
        var items = result.data.map((item) => {
          return (<Option key={item.value} value={String(item.value) }>{item.text}</Option>);
        });
        me.setState({ nperList: items });
      }
    });
  },

  onChange(field, value) { 
    this.setState({
      [field]: value,
    });
  },

  handleStartToggle({ open }) {
    if (!open) {
      this.setState({ endOpen: true });
    }
  },
  handleEndToggle({ open }) {
    this.setState({ endOpen: open });
  },
  handleQuery() {
    var params = this.props.form.getFieldsValue();
    this.props.passParams({
      searchParams: JSON.stringify(params),
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
        <FormItem label="借款期数：">
          <Select style={{ width: 150 }} {...getFieldProps('timeLimit', { initialValue: '' }) }>
            {this.state.nperList}
          </Select>
        </FormItem>
        <FormItem label="客户姓名：">
          <Input  {...getFieldProps('customerName') } />
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;
