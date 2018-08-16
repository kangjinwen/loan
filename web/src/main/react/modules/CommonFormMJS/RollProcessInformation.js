//审批信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Row,
    InputNumber,
    Col,
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var PropertySituationList = [];
Utils.ajaxData({
    url: '/modules/system/getUserInfo.htm',
    method: 'get',
    data: {
        roleName: "warrantStaff"
    },
    type: 'json',
    callback: (result) => {
        PropertySituationList = result.data;
    }
});
var RollProcessInformation = React.createClass({
    getInitialState() {
        return {
        }
    },
    componentWillReceiveProps(nextProps) {
        if (nextProps.record && nextProps.record != this.props.record) {
            this.queryData(nextProps.record);
        }
    },
    componentDidMount() {
        this.queryData(this.props.record);
    },
    queryData(record) {
        var nextStepList = [];
        var me = this;
        var props = this.props;
        if (props.canEdit) {
            Utils.ajaxData({
                url: '/modules/workflow/controller/RepositoryController/queryButtonNameListByTaskId.htm',
                method: 'get',
                data: {
                    taskId: record.taskId
                },
                type: 'json',
                callback: (result) => {
                    var items = result.data.map((item) => {
                        return (<Option key={item.value} value={item.value}>{item.text}</Option>);
                    });
                    nextStepList = items;
                    me.setState({ nextStepList: nextStepList });
                }
            });

        } else {
            var processInstanceId = record.newProcessInstanceId
            Utils.ajaxData({
                url: '/modules/common/action/ApprovalCommentsAction/getApprovalComment.htm',
                method: 'get',
                data: {
                    processInstanceId: processInstanceId,
                    taskId: record.taskId,
                },
                type: 'json',
                callback: (result) => {

                    this.props.form.setFieldsValue(result.data);
                }
            });
        }

    },
    getPropertySituationList() {
        return PropertySituationList.map((item, index) => {
            return <Option key={item.id} value={String(item.userName) }>{item.name}</Option>
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
                    <Col span="12">
                        <FormItem  {...formItemLayout} label="审批意见：">
                            <Select disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.state.nextStepList}
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="19">
                        <FormItem  labelCol={{ span: 5 }} wrapperCol={{ span: 18 }}  label="备注：">
                            <Input disabled={!props.canEdit} rows="2" {...getFieldProps('remarkComment', { rules: [/*{ required: true, message: '必填' }*/] }) } type="textarea" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
RollProcessInformation = createForm()(RollProcessInformation);
export default RollProcessInformation;
