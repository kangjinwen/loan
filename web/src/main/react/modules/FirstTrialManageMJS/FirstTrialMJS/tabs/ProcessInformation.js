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
var AssignCheckBankList = [];//核行
Utils.ajaxData({
    url: '/modules/system/getOrgListByParentId.htm',
    method: 'get',
    data: {

    },
    type: 'json',
    callback: (result) => {
        AssignCheckBankList = result.data;
    }
});
var ProcessInformation = React.createClass({
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
                    taskId: record.taskId,
                    processInstanceId: record.processInstanceId
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
            var processStateCode = this.props.record.processStateCode;
            var processInstanceId = record.processInstanceId;
            if (processStateCode == "usertask-supplyInvestigate") {
                processInstanceId = record.branchingProcessId;
            }
            var proces = record.processStateCode
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
                    this.props.form.setFieldsValue({assigneeOrg:result.data.manualAssignee});
                }
            });
        }
    },
    getAssignCheckBankList() {
        return AssignCheckBankList.map((item, index) => {
            return <Option key={item.id} value={String(item.id) }>{item.name}</Option>
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
        var processStateCode = this.props.record.processStateCode;
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
                {/* <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout} label="机构选择：">
                            <Select disabled={!props.canEdit} {...getFieldProps('assigneeOrg', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.getAssignCheckBankList()}
                            </Select>
                        </FormItem>
                    </Col>
                </Row> */}
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
ProcessInformation = createForm()(ProcessInformation);
export default ProcessInformation;
