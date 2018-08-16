import React from 'react';
import {
  Button,
  Form,
  Input,
  Modal,
  Select,
  Upload,
  Tree,
  TreeSelect,
  Row,
  Col,
  message
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
import reqwest from 'reqwest'; 
var AddContractWin = React.createClass({
  getInitialState() {
    return {
      status: {},
      formData: {},
      id: ""
    };
  },
  handleCancel() {
    this.props.form.resetFields();
    this.props.hideModal();
  }, 
  handleOk(e) {
    var me = this;
    var props = me.props;
    var record = props.record;
    var title = props.title;

    var url = "/modules/common/PubContractAttachmentAction/upload.htm";
    this.props.form.validateFields((errors, values) => {
      if (!!errors) {
        console.log('Errors in form!!!');
        return;
      }
   
      const files = this.refs.file.files;   
      var formData = new FormData();
      formData.append("data", JSON.stringify(values));
      formData.append("Filedata", files[0]);

      reqwest({
        url: url,
        method: 'post',
        contentType: false,
        processData: false,
        data: formData,
        success: (result) => {
          if (result.code == 200) {
            Modal.success({
              title: "新增成功",
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

  choosFile() {
    const el = this.refs.file;
    if (!el) {
      return;
    }
    el.click();
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    var props = this.props;
    var state = this.state;
    var modalBtns = [
      <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
      <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleOk}>
        提交
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
    var canEdit = this.props.canEdit;
     
    return (
      <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="600"  footer={modalBtns} >
        <Form horizontal  form={this.props.form}>
          <Row>
            <Col span="20">
              <FormItem  {...formItemLayout} label="合同名称：">
                <Input disabled={!props.canEdit}  {...getFieldProps('name', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="20">
              <FormItem  {...formItemLayout} label="生效节点">
                <Select id="select" size="large" disabled={!props.canEdit} {...getFieldProps('effectiveNode', { rules: [{ required: true, message: '必填' }] }) } >
                  <Option value="0">合同签订</Option>
                  <Option value="1">展期合同签订</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="20">
              <FormItem  {...formItemLayout} label="是否启用">
                <Select id="select" size="large" disabled={!props.canEdit} {...getFieldProps('state', { rules: [{ required: true, message: '必填' }] }) } >
                  <Option value="1">是</Option>
                  <Option value="0">否</Option>
                </Select>
              </FormItem>
            </Col>

          </Row>
          <Row>
            <Col span="20" style={{ marginLeft: "100px" }}>
              <span style={this.props.style}>
                <span style={{ marginTop: 25, marginBottom: 15 }} >
                  <span style={{ marginRight: 25, }}>合同模板</span>
                  <a style={{ marginRight: 25, }} onClick={this.choosFile}>
                    上传附件
                    <input type="file" ref="file"  style={{ display: 'none' }}  />
                  </a>
                </span >
              </span>
            </Col>
          </Row>
        </Form>
      </Modal>
    );
  }
});
AddContractWin = createForm()(AddContractWin);
export default AddContractWin;