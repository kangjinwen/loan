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
let treeData = {};
var HousingDisposalWin = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
            loading: false
        };
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
    },
    handleOk(e) {
        e.preventDefault();
        var me = this;
        var props = me.props;
        var record = props.record;
        var title = props.title;
        var remark = this.props.form.getFieldsValue().remark;
        var url = "/modules/repayment/RepaymentAction/pay.htm";
        Utils.ajaxSubmit({
            me: me,
            url: url,
            data: {
                remark,
                processInstanceId: record.processInstanceId,
            },
            callback: (result) => {
                if (result.code == 200) {
                    Modal.success({
                        title: result.msg,
                        onOk: () => {
                            props.hideModal();
                        }
                    });
                } else {
                    Modal.error({
                        title: result.msg,
                    });
                }
            }
        });
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
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="900"  footer={modalBtns} >
                <div style={{ position: "relative" }}>
                    <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                        <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                        <Row>
                            <Col span="24">
                                <FormItem  labelCol={{ span: 4 }} wrapperCol={{ span: 19 }} label="备注：">
                                    <Input disabled={!props.canEdit} rows="3" type="textarea"  {...getFieldProps('remark') } autoComplete="off"/>
                                </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </div>
            </Modal>
        );
    }
});
HousingDisposalWin = createForm()(HousingDisposalWin);
export default HousingDisposalWin;
