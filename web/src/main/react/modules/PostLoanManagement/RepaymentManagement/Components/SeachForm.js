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
      timeLimitList: [],
      officeList: [],
      startValue: null,
      endValue: null,
      endOpen: false,
    }
  },
  handleQuery() {
    var params = this.props.form.getFieldsValue();
    params.loanStartDate == null ? params.loanStartDate = "" : params.loanStartDate = DateFormat.formatTime("yyyy-MM-dd 00:00:00", params.loanStartDate);
    params.loanEndDate == null ? params.loanEndDate = "" : params.loanEndDate = DateFormat.formatTime("yyyy-MM-dd 00:00:00", params.loanEndDate);
    this.props.passParams({
      businessOriginText: params.businessOriginText,
      institutionName: params.institutionName,
      customerManager: params.customerManager,
      projectCode: params.projectCode,
      contractNo: params.contractNo,
      repaymentStatus: params.repaymentStatus,
      customerName: params.customerName,
      officeId: params.officeId,
      timeLimit: params.timeLimit,
      loanStartDate: params.loanStartDate,
      loanEndDate: params.loanEndDate,
      pageSize: 5,
      pageNum: 1,
    });
  },
  handleReset() {
    this.props.form.resetFields();
    this.setState({
      loanStartDate: null,
      loanEndDate: null
    })
  },
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
        me.setState({ timeLimitList: items });
      }
    });
    Utils.ajaxData({
      url: '/modules/system/getBusinessDepartment.htm?type=3',
      method: 'get',
      type: 'json',
      callback: (result) => {
        var items = result.data.map((item) => {
          return (<Option key={item.id} value={String(item.id) }>{item.name}</Option>);
        });
        me.setState({ officeList: items });
      }
    });
  },
  disabledStartDate(loanStartDate) {
    if (!loanStartDate || !this.state.loanEndDate) {
      return false;
    }
    return loanStartDate.getTime() >= this.state.loanEndDate.getTime();
  },
  disabledEndDate(loanEndDate) {
    if (!loanEndDate || !this.state.loanStartDate) {
      return false;
    }
    return loanEndDate.getTime() <= this.state.loanStartDate.getTime();
  },
  onStartChange(value) {
    this.onChange('loanStartDate', value);
  },
  onEndChange(value) {
    this.onChange('loanEndDate', value);
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
          <Input  {...getFieldProps('projectCode') } />
        </FormItem>
        <FormItem label="合同编号：">
          <Input  {...getFieldProps('contractNo') } />
        </FormItem>
        <FormItem label="还款状态：">
          <Select style={{ width: 150 }} {...getFieldProps('repaymentStatus', { initialValue: '' }) }>
            <Option value="0">还款中</Option>
            <Option value="1">结清</Option>
            <Option value="2">逾期</Option>
          </Select>
        </FormItem>
        <FormItem label="客户姓名：">
          <Input  {...getFieldProps('customerName') } />
        </FormItem>
        <FormItem label="来源：">
          <Select style={{ width: 150 }} {...getFieldProps('businessOriginText', { initialValue: '' }) }>
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
        <FormItem label="借款期数：">
          <Input  {...getFieldProps('timeLimit') } type="number"/>
        </FormItem>
        <FormItem label="放款开始时间：">
          <DatePicker disabledDate={this.disabledStartDate}  {...getFieldProps('loanStartDate', { onChange: this.onStartChange }) } />
        </FormItem>
        <FormItem label="放款结束时间：">
          <DatePicker disabledDate={this.disabledEndDate}  {...getFieldProps('loanEndDate', { onChange: this.onEndChange }) } />
        </FormItem>
        <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
        <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
      </Form>
    );
  }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;