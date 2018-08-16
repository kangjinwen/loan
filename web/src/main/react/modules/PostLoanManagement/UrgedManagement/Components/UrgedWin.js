//催收
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
let treeData = {};
var AddDic = React.createClass({
  getInitialState() {
    return {
      status: {},
      formData: {}
    };
  },
  handleCancel() {
    this.props.form.resetFields();
    this.props.hideModal();
  },
  //提交
  handleOk() {
    var me = this;
    this.props.form.validateFields((errors, values) => {
      if (!!errors) {
        return;
      } 
    });
  },

  getRoleList() {
    return roleList.map((item, index) => {
      return <Option key={item.id} >{item.name}</Option>
    })
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    var props = this.props;
    var state = this.state;
    var modalBtns = [
      <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleOk}>
        确 定
      </button>
    ];
    const formItemLayout = {
      labelCol: {
        span: 8
      },
      wrapperCol: {
        span: 15
      },
    };
    return (
      <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="500" footer={modalBtns} >
        <div style={{ position: "relative" }}>
          <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
            <div className="col-22 navLine-wrap-left" >
              <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                <Row>
                  <Col span="6">
                    <FormItem  {...formItemLayout} label="催收类型：" style={{ width: 400 }}>
                      <Select  disabled={!props.canEdit} {...getFieldProps('whetherOneContact', { rules: [{ required: true, message: '必填' }] }) } >
                        <Option value="0">短信催收</Option>
                        <Option value="1">上门催收</Option>
                        <Option value="2">邮件催收</Option>
                        <Option value="3">电话催收</Option>
                      </Select>
                    </FormItem>
                  </Col>
                </Row>
                <Row>
                  <Col span="6">
                    <FormItem  {...formItemLayout} label="催收内容：" style={{ width: 400 }}>
                      <Input disabled={!props.canEdit}  {...getFieldProps('whetherOneContact2', { rules: [{ required: true, message: '必填' }] }) } type="textarea" autoComplete="off" />
                    </FormItem>
                  </Col>
                </Row>
              </Form>
            </div>
          </div>
        </div>
      </Modal>
    );
  }
});
AddDic = createForm()(AddDic);
export default AddDic;
