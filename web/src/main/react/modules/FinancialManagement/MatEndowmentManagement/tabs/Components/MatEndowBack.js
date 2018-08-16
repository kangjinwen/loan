import React from 'react';
import {
    Form,
    Input,
    Modal,
    Row,
    Col,
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
var MatEndowBack = React.createClass({
    handleCancel() { 
        this.props.hideModal();
    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        const formItemLayout = {
            labelCol: {
                span: 9
            },
            wrapperCol: {
                span: 15
            },
        };
        var button;
        var modalBtns = [
            <button key="button" className="ant-btn ant-btn-primary" disabled={!props.canEdit}  onClick={this.handleOk}>
                确 认
            </button>,
            <button key="back" className="ant-btn" onClick={this.handleCancel}>取 消</button>
        ];
        return (
            <Modal title={props.title} visible={props.visible}  onCancel={this.handleCancel}   width="400"  footer={modalBtns} >
               <div style={{textAlign:'center',fontSize:'16px'}}>是否确认垫资？</div>
            </Modal>
        );
    }
});
MatEndowBack = createForm()(MatEndowBack);
export default MatEndowBack;