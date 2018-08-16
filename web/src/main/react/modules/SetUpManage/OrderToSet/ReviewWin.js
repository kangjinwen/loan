//是否接单
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
var ReviewWin = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
            visible: false
        };
    },
    handleOk() {
        var isReceiveOrder = this.props.form.getFieldsValue();
        Utils.ajaxData({
            url: '/modules/system/ZzjfSysUserAction/orderReceiveSet.htm'
            , method: 'post'
            , data:isReceiveOrder
            , type: 'json'
            , callback: (result) => {
                if (result.code == 200) {
                    Modal.success({
                        title: result.msg

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
    render() {
        const {
            getFieldProps,
            getFieldValue
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.props.hideModal}>关 闭</button>,
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
            <Modal title={props.title} visible={props.visible} onCancel={this.props.hideModal} width="700"  footer={modalBtns} >
                <Form horizontal  form={this.props.form}>
                    <Row>
                        <Col span="15">
                            <FormItem  {...formItemLayout} label="是否接单：">
                                <Select  disabled={!props.canEdit} {...getFieldProps('isReceiveOrder',{ initialValue: 1,}) } >
                                    <Option value={1}>是</Option>
                                    <Option value={2}>否</Option>
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                </Form>
            </Modal>
        );
    }
});
ReviewWin = createForm()(ReviewWin);
export default ReviewWin;