//借款需求
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Row,
    Col,
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
var EditPassword = React.createClass({
    getInitialState() {
        return {
        };
    }, 

    checkPass2(rule, value, callback) {
        const { getFieldValue } = this.props.form;
        if (value && value !== getFieldValue('password1')) {
            callback('两次输入密码不一致！');
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
        const formItemLayout = {
            labelCol: {
                span: 6
            },
            wrapperCol: {
                span: 13
            },
        };
        return (
            <Form  horizontal   form={this.props.form}>
                <FormItem  {...formItemLayout} label="原密码：">
                    <Input  style={{ width: "100%" }} {...getFieldProps('password',
                        {
                            rules: [
                                { required: true, message: '必填' }
                            ]
                        }) }  autoComplete="off" type="password"/>
                </FormItem>

                <FormItem  {...formItemLayout} label="新密码：" help="6-16位，区分大小写，只能使用字母、数字、下划线">
                    <Input  style={{ width: "100%" }} {...getFieldProps('password1',
                        {
                            rules: [
                                { required: true,min: 6,  message: '必填'   },
                                { message: '只能使用字母、数字、下划线', pattern: /^[a-zA-Z]\w{5,15}$/ },
                            ]
                        }) }  autoComplete="off" type="password"/>
                </FormItem>

                <FormItem  {...formItemLayout} label="确认新密码：">
                    <Input   {...getFieldProps('password2',
                        {
                            rules: [
                                { required: true,min: 6, message: '必填' }, { validator: this.checkPass2 },{ message: '只能使用字母、数字、下划线', pattern: /^[a-zA-Z]\w{5,15}$/ },
                            ]
                        }) }  autoComplete="off" type="password" />
                </FormItem>
            </Form>
        );
    }
});
EditPassword = createForm()(EditPassword);
export default EditPassword;