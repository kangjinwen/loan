//基本信息
import React from 'react';
import {
  Button,
  Form,
  Input,
  Modal,
  Select,
  Row,
  Col,
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var Region = require('../plugin/Region');
var UploadPhoto = require("./UploadPhoto");
var MarryStatuslist = [];
var HousingInformation = React.createClass({
  getInitialState() {

    return {
      residentialAddressId: '110101',
      visibleUp: false,
    }
  },
  getMarryStatuslist() {
    return MarryStatuslist.map((item, index) => {
      return <Option key={item.value} >{item.text}</Option>
    })
  },
  disabledStartDate2(custSignedTimeBegintime) {
    if (!custSignedTimeBegintime || !this.state.custSignedTimeEndtime) {
      return false;
    }
    return custSignedTimeBegintime.getTime() >= this.state.custSignedTimeEndtime.getTime();
  },
  disabledEndDate2(custSignedTimeEndtime) {
    if (!custSignedTimeEndtime || !this.state.custSignedTimeBegintime) {
      return false;
    }
    return custSignedTimeEndtime.getTime() <= this.state.custSignedTimeBegintime.getTime();
  },
  onChange(field, value) {
    console.log(field, 'change', value);
    this.setState({
      [field]: value,
    });
  },

  onStartChange2(value) {
    this.onChange('custSignedTimeBegintime', value);
  },
  onEndChange2(value) {
    this.onChange('custSignedTimeEndtime', value);
  },
  //生成合同编号
  handleOk() {
    var selectedrecord = this.props.record
    Utils.ajaxData({
      url: '/modules/contract/action/PlContractAction/createContractNo.htm',
      method: 'post',
      data: {
        processInstanceId: selectedrecord.processInstanceId,
        consultId: selectedrecord.consultId,
        projectId: selectedrecord.projectId
      },
      type: 'json',
      callback: (result) => {
        if (result.code == 200) {
          this.props.form.setFieldsValue(result.data)
        } else {
          Modal.error({
            title: result.msg,
          });
        }
      }
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
  hideModal() {
    this.setState({
      visibleUp: false
    });
  },
  upload() {

  },
  showupload(title, canEdit) {
    this.setState({
      visibleUp: true,
      title: title,
      canEdit: canEdit,
    })
  },
  render() {
    const {
      getFieldProps
    } = this.props.form;
    var props = this.props;
    var state = this.state;
    const formItemLayout = {
      labelCol: {
        span: 8
      },
      wrapperCol: {
        span: 15
      },
    };
    return (
      <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
        <Row>
          <Col span="6">
            <FormItem  {...formItemLayout}  label="合同编号：">
              <Input disabled={true}   {...getFieldProps('contractNo', { rules: [{ required: true, message: '必填' }] }) } autoComplete="off" labelCol={{ span: 4 }} />
            </FormItem>
          </Col>
          <Col span="6">
            {this.props.canEdit ? <button key="submit" className="ant-btn" onClick={this.handleOk}>生成合同编号</button> : null}
          </Col>
        </Row>
        <Row>
          <UploadPhoto style={{ marginLeft: 25 }} title="上传合同与签合同照片" btype="Contract_Pic" canEdit={props.canEdit}  selectRecord={props.record}/>
        </Row>
      </Form>
    )

  }

});

HousingInformation = createForm()(HousingInformation);
export default HousingInformation;