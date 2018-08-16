import React from 'react';
import {
  Button,
  Form,
  Input,
  InputNumber,
  Modal,
  Select,
  Tree,
  TreeSelect,
  Row,
  Col,
  DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
const NavLine = require("./../../../utils/NavLine");
import MicroInput from './../../../utils/MicroInput';//数字金额加逗号
let treeData = {};
var AddDic = React.createClass({
  getInitialState() {
    return {
      status: {
        idData: ""
      },
      activekey: "1",
      formData: {},
      loading: false
    };
  },
  handleCancel() {
    this.props.form.resetFields();
    this.props.hideModal();

  },
  navLineData: {
    "基本信息": "#s1",
    "还款信息": "#s2",
  },
  handleOk() {
    var props = this.props;
    if (props.title == "还款处理" || props.title == "提前还款") {
      this.handleOk1();
    } else if (props.title == "编辑") {
      this.AddhandleOk();
    }
  },
  handleOk1() {//还款处理
    var me = this;
    var props = me.props;
    var record = props.record;
    var title = props.title;
    var url = "/modules/repayment/RepaymentAction/pay.htm";
    this.props.form.validateFields((errors, values) => {
      if (!!errors) {
        console.log('Errors in form!!!');
        return;
      }
      if (props.title == "还款处理") {//正常还款
        if (values.realAccount < values.account) {
          Modal.error({
            title: '本期实际还款金额不能小于本期应还金额',
            onOk: () => {
            }
          });
          return;
        }
        var dataParams = {
          processInstanceId: record.processInstanceId,
          realInterest: values.realInterest,
          period: values.period,
          realAccount: values.realAccount,
          totalAccount: values.totalAccount,
        };
        var getTime = (values.realpaymentTime).toLocaleDateString();
        getTime = getTime.replace(/\//g, '-');
        dataParams.realpaymentTime = DateFormat.formatTime("yyyy-MM-dd 00:00:00", values.realpaymentTime);//getTime;
        dataParams.type = 1;
      } else if (props.title == "提前还款") {
        var dataParams = {
          processInstanceId: record.processInstanceId,
          realAccount: values.realAccount,
          period: values.period,
        };
        var getTime = (values.realpaymentTime).toLocaleDateString();
        getTime = getTime.replace(/\//g, '-');
        dataParams.realpaymentTime = getTime;
        dataParams.type = 2;
      }
      var data = objectAssign({}, {
        json: JSON.stringify(dataParams)
      });
      if (title == "修改") {
        var data = objectAssign({}, {
          json: JSON.stringify(values)
        }, {
            status: 'update'
          });
      }
      Utils.ajaxSubmit({
        me: this,
        url: url,
        data: data,
        callback: (result) => {
          if (result.code == 200) {
            Modal.success({
              title: result.msg,
              onOk: () => {
                this.handleCancel();
              }
            });
          } else {
            Modal.error({
              title: result.msg,
            });
          }
        }
      });
    });
  },
  getRoleList() {
    return roleList.map((item, index) => {
      return <Option key={item.id} >{item.name}</Option>
    })
  },
  checkRealAccount(rule, value, callback) {
    if (value == '') {
      callback(new Error('本期实际还款不能为空'));
    } else {
      callback();
    }
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    var props = this.props;
    var state = this.state;
    var modalBtns = [
      <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
      <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
        提 交
      </Button>
    ];
    if (!props.canEdit) {
      modalBtns = [
        <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
      ];
    }
    const formItemLayout = {
      labelCol: {
        span: 8
      },
      wrapperCol: {
        span: 15
      },
    };
    var reimbursementProcess;
    var record2 = this.props.record2;
    var recordUnder = this.props.recordUnder;
    if (props.title == "还款处理" || (props.title == "还款记录" && recordUnder && recordUnder.repaymentStaus == 1) || (props.title == "还款记录" && recordUnder && recordUnder.repaymentStaus == 3)) {//正常还款 逾期
      reimbursementProcess = (
        <div>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还款日期：">
                <Input disabled={true}  {...getFieldProps('repaymentTime') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="还款期次：">
                <Input disabled={true}  {...getFieldProps('period') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还金额(元)：">
                <MicroInput disabled={true}  {...getFieldProps('account') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还逾期罚息(元)：">
                <MicroInput disabled={true}  {...getFieldProps('defaultInterest') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还总额(元)：">
                <MicroInput disabled={true}  {...getFieldProps('totalAccount') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期实际逾期罚息(元)：">
                <MicroInput disabled={!props.canEdit}  {...getFieldProps('realInterest',
                  {
                    rules: [
                      { required: true, message: '必填只能是数字', type: 'float' },
                      { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                    ]
                  }
                ) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期实际还款(元)：">
                <MicroInput disabled={!props.canEdit} {...getFieldProps('realAccount',
                  {
                    rules: [
                      { required: true, message: '必填只能是数字', type: 'float' },
                      { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                    ]
                  }
                ) } min={0} step={0.01} autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="实际还款日期：">
                <DatePicker disabled={!props.canEdit}  {...getFieldProps('realpaymentTime', { rules: [{ required: true, message: '实际还款日期不能为空', type: "date" }] }) } />
              </FormItem>
            </Col>
          </Row>
        </div>
      )
    } else if (props.title == "提前还款" || (props.title == "还款记录" && recordUnder && recordUnder.repaymentStaus == 4)) {
      reimbursementProcess = (
        <div>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还款日期：">
                <Input disabled={true}  {...getFieldProps('repaymentTime') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="还款期次：">
                <Input disabled={true}  {...getFieldProps('period') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还金额(元)：">
                <MicroInput disabled={true}  {...getFieldProps('account') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="提前还款违约金(元)：">
                <MicroInput disabled={true}  {...getFieldProps('penalty') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期应还总额(元)：">
                <MicroInput disabled={true}  {...getFieldProps('totalAccount') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="剩余本金(元)：">
                <MicroInput disabled={true}  {...getFieldProps('capital') } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem  {...formItemLayout} label="本期实际还款(元):">
                <MicroInput disabled={!props.canEdit} {...getFieldProps('realAccount',
                  {
                    rules: [
                      { required: true, message: '必填只能是数字', type: 'float' },
                      { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                    ]
                  }
                ) } min={0} step={0.01} autoComplete="off" />
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem  {...formItemLayout} label="实际还款日期：">
                <DatePicker disabled={!props.canEdit}  {...getFieldProps('realpaymentTime', { rules: [{ required: true, message: '实际还款日期不能为空', type: "date" }] }) } />
              </FormItem>
            </Col>
          </Row>
        </div>
      )
    }
    return (
      <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="1200" footer={modalBtns} >
        <div style={{ position: "relative" }}>
          <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
            <div className="col-22 navLine-wrap-left" >
              <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden" />
                <div id="s1">
                  <h2>基本信息</h2>
                  <Row>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="项目编号：">
                        <Input disabled={true}  {...getFieldProps('projectCode') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="借款名称：">
                        <Input disabled={true}  {...getFieldProps('projectName') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                  </Row>
                  <Row>
                   
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="借款期限(月)：">
                        <Input disabled={true}  {...getFieldProps('timeLimit') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="借款金额(小写/元)：">
                        <MicroInput disabled={true}  {...getFieldProps('borrowAccount') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="借款金额(大写):">
                        <Input disabled={true}  {...getFieldProps('borrowAccountChinese') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="还款起始（日期）：">
                        <Input disabled={true}  {...getFieldProps('repaymentStartTime') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="还款结束(日期)：">
                        <Input disabled={true}  {...getFieldProps('repaymentEndTime') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                  </Row>
                  <Row>
                    <Col span="12">
                      <FormItem  {...formItemLayout} label="还款方式：">
                        <Input disabled={true}  {...getFieldProps('repaymentTypeText') } type="text" autoComplete="off" />
                      </FormItem>
                    </Col>
                  </Row>
                </div>
                <div id="s2">
                  <h2>还款信息</h2>
                  {reimbursementProcess}
                </div>
              </Form>
            </div>
            <div className="navLine-wrap-right" >
              <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine" />
            </div>
          </div>
        </div>
      </Modal>
    );
  }
});
AddDic = createForm()(AddDic);
export default AddDic;
