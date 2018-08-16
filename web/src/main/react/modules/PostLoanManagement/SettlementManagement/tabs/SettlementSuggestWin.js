//审批信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    Col,
    DatePicker
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var SettlementSuggestWin = React.createClass({
    getInitialState() {
        return {
        }
    },

    // componentDidMount() {

    //     var nextStepList = [];
    //     var me = this;
    //     var props = this.props;
    //     if (props.canEdit) {
    //         Utils.ajaxData({
    //             url: '',
    //             method: 'get',
    //             data: {
    //                 taskId: this.props.record.taskId
    //             },
    //             type: 'json',
    //             callback: (result) => {
    //                 var items = result.data.map((item) => {
    //                     return (<Option key={item.value} value={item.value}>{item.text}</Option>);
    //                 });
    //                 nextStepList = items;
    //                 me.setState({ nextStepList: nextStepList });
    //             }
    //         });

    //     } else {
    //         Utils.ajaxData({
    //             url: '/modules/common/action/ApprovalCommentsAction/getApprovalComment.htm',
    //             method: 'get',
    //             data: {
    //                 processInstanceId: this.props.record.processInstanceId,
    //                 taskId: this.props.record.taskId,
    //             },
    //             type: 'json',
    //             callback: (result) => {

    //                 this.props.form.setFieldsValue(result.data);
    //             }
    //         });
    //     }

    // },
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
                span: 15
            },
        };
        const formItemLayout2 = {
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
                    <Col span="11">
                        <FormItem  {...formItemLayout2} label="处理人员：">
                             <Input disabled={true}  {...getFieldProps('userName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout2} label="是否还清：">
                             <Input disabled={true}  {...getFieldProps('isPayoffText') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="11">
                        <FormItem  {...formItemLayout2} label="是否逾期：">
                             <Input disabled={true}  {...getFieldProps('isOverdueText') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="审批意见：" labelCol={{ span: "8" }}>
                            <Select disabled={!props.canEdit} {...getFieldProps('isSettleaccounts', { rules: [{ required: true, message: '必填',type:'number' }] }) } >        
                                <Option value="1">已结算</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem  {...formItemLayout} label="备注：" labelCol={{ span: "8" }}>
                            <textarea disabled={!props.canEdit}  {...getFieldProps('remark') }  style={{ width: "550px", border: "1px solid #d9d9d9", borderRadius: "3px", paddingLeft: "5px" }} type="text" autoComplete="off" >
                            </textarea>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
SettlementSuggestWin = createForm()(SettlementSuggestWin);
export default SettlementSuggestWin;
