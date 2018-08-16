//放款信息
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

var Recordiformation = React.createClass({
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
    var selectRecord = props.selectRecord; 
    var state = this.state;
    const formItemLayout = {
      labelCol: {
        span: 8
      },
      wrapperCol: {
        span: 15
      },
    };
    // var disableds = true;
    // if (props.title == "放款操作") {
    //   disableds = true;
    // }
    var branchingProcessType = this.props.record.branchingProcessType;
    var Stage, Advance, Disposal, Breaks;
    if (branchingProcessType == 1) {//提前还款
      Advance = (<div id="s1">
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款期限：">
              <Input disabled={true}  {...getFieldProps('projectCode') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="目前期次：">
              <Input disabled={true}  {...getFieldProps('period') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="预约还款日期：">
              <DatePicker   {...getFieldProps('repaymentTime') } showTime  format="yyyy-MM-dd hh:mm:ss" disabled={true} style={{ width: 230 }}/>
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="提前还款违约金：">
              <Input disabled={true}  {...getFieldProps('penaltySum') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="实际提前还款违约金：">
              <Input disabled={true}  {...getFieldProps('realPrepaymentPenalty', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
              <Input  disabled={true}  {...getFieldProps('loanChangeRemark', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
            </FormItem>
          </Col>
        </Row>
      </div>
      )
    }
    if (branchingProcessType == 2) {//发息减免
      Breaks = (<div id="s1">
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款期限：">
              <Input disabled={true}  {...getFieldProps('ddd') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款利率：">
              <Input disabled={true}  {...getFieldProps('ddde') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="逾期罚息：">
              <Input disabled={true}  {...getFieldProps('dd') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="罚息利率：">
              <Input disabled={true}  {...getFieldProps('fff') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="实际罚息金额：">
              <Input disabled={true}  {...getFieldProps('ffft', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
              <Input  disabled={true}  {...getFieldProps('rrr', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
            </FormItem>
          </Col>
        </Row>
      </div>
      )
    }
    if (branchingProcessType == 6) {//展期
      Stage = (
        <div id="s1">
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout} label="贷款期限：">
                <Input disabled={true}  {...getFieldProps('timeLimit') }  type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout}  label="申请展期金额：">
                <Input disabled={true}  {...getFieldProps('extensionAmount', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="10">
              <FormItem  {...formItemLayout} label="展期费：">
                <Input disabled={true}  {...getFieldProps('extensionFee', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout}  label="展期利率%：">
                <Input disabled={true}  {...getFieldProps('extensionRate', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="10">
              <FormItem  {...formItemLayout}  label="展期返佣点位：">
                <Input disabled={true}  {...getFieldProps('extensionReturnfeeRate', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout}  label="申请类型：">
                <Select  disabled={true} {...getFieldProps('extendedFlag', { initialValue: 0 }) }>
                <Option value="0">展期申请</Option>
                <Option value="1">发起评估</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="8">
              <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
                <Input  disabled={true}  {...getFieldProps('remark1', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
              </FormItem>
            </Col>
          </Row>
        </div>
      )
    }
    if (branchingProcessType == 5) {//房屋处置
      Disposal = (
        <div id="s1">
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout} label="贷款期限：">
                <Input disabled={true}  {...getFieldProps('ddd') }  type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout}  label="逾期罚息：">
                <Input disabled={true}  {...getFieldProps('dd') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="10">
              <FormItem  {...formItemLayout} label="罚息利率：">
                <Input disabled={true}  {...getFieldProps('fff') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="10">
              <FormItem  {...formItemLayout}  label="房屋预处置金额：">
                <Input disabled={true}  {...getFieldProps('fffe', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="8">
              <FormItem  {...formItemLayout} label="备 注：" style={{ width: "950px" }} labelCol={{ span: "3" }}>
                <Input  disabled={true}  {...getFieldProps('rrr', { rules: [{ required: true, message: '必填', type: "date" }] }) } type="textarea" autoComplete="off" style={{ height: "80px" }}  />
              </FormItem>
            </Col>
          </Row>
        </div>
      )
    }
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout} label="项目编号：">
              <Input disabled={true}  {...getFieldProps('projectCode') }  type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="客户名称：">
              <Input disabled={true}  {...getFieldProps('customerName') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="借款金额：">
              <Input disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="剩余本金：">
              <Input disabled={true}  {...getFieldProps('remainderAmount') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="10">
            <FormItem  {...formItemLayout}  label="贷款起始日期：">
              <Input disabled={true}  {...getFieldProps('startRepayTime') } type="text" autoComplete="off" />
            </FormItem>
          </Col>
          <Col span="10">
            <FormItem  {...formItemLayout} label="贷款到期日期：">
              <Input disabled={true}  {...getFieldProps('endRepayTime') } type="text" autoComplete="off" />
            </FormItem>
          </Col>

        </Row>
        {Stage}
        {Advance}
        {Disposal}
        {Breaks}
      </Form>
    )
  }
});
Recordiformation = createForm()(Recordiformation);
export default Recordiformation;
