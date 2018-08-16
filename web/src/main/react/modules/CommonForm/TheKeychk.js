import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
    Radio,
    Checkbox,
    DatePicker
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var Reflux = require('reflux');
var FormDataStore = require('./stores/FormDataStore');
var KeyDiskQueryList = [];
Utils.ajaxData({
    url: '/modules/common/action/ComboAction/queryDic.htm',
    method: 'get',
    data: {
        typeCode: "KEY_DISK_QUERY"
    },
    type: 'json',
    callback: (result) => {
        KeyDiskQueryList = result.data;
    }
});
var AddlWin = React.createClass({
    mixins: [
        Reflux.listenTo(FormDataStore, "onMatch")
    ],
    onMatch(data) {
        console.log(data)
        if (data.formData) {
            this.props.form.setFieldsValue(data.formData);
        } else this.props.form.resetFields();
    },
    getInitialState() {
        return {
            status: {
            },
            formData: {}
        };
    },
    componentDidMount() {
        var record = {};
        if (this.props.record) {
            var record = this.props.record;
        }
        this.props.form.setFieldsValue(record);
    },
    gettypeIdList() {
        return typeIdList.map((item, index) => {
            return <Option key={item.value} >{item.text}</Option>
        })
    },
  
    getKeyDiskQueryList() {
        return KeyDiskQueryList.map((item, index) => {
            return <Option key={item.value} >{item.text}</Option>
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
                <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                <Row>
                   <Col span="7">
                        <FormItem  {...formItemLayout} label="钥匙盘查询：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('keyDiskQuery') } >
                                {this.getKeyDiskQueryList() }
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        );
    }
});
AddlWin = createForm()(AddlWin);
export default AddlWin;
