//下一步任务分配
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
var TaskAllocation = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
        };
    },
    handleOk() {
        var record = this.props.record
        var newTaskAssignee = this.props.form.getFieldsValue();
        Utils.ajaxData({
            url: '/modules/workflow/controller/ProcessTaskController/assignTask.htm'
            , method: 'post'
            , data: {
                taskId: record.taskId,
                newTaskAssignee: newTaskAssignee.userName
            }
            , type: 'json'
            , callback: (result) => {
                if (result.code == 200) {
                    Modal.success({
                        title: result.msg,
                        onOk: () => {
                            this.handleCancel();
                        }
                    });
                }
                else if (result.code == 400) {
                    Modal.error({
                        title: result.msg
                    });
                }
            }
        });
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
    },
    render() {
        const {
            getFieldProps,
            getFieldValue
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>关 闭</button>,
            <Button  key="button" className="ant-btn ant-btn-primary" onClick={this.handleOk}>
                确 定
            </Button >
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
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="700"  footer={modalBtns} >
                <Form horizontal  form={this.props.form}>
                    <Row>
                        <Col span="15">
                            <FormItem  {...formItemLayout} label="任务分配：">
                                <Select  disabled={!props.canEdit} {...getFieldProps('userName') } >
                                    {this.props.getUserNameList}
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                </Form>
            </Modal>
        );
    }
});
TaskAllocation = createForm()(TaskAllocation);
export default TaskAllocation;