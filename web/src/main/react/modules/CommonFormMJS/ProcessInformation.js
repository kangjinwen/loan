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
var AssignCheckBankList = [];//核行
Utils.ajaxData({
    url: '/modules/system/getUserInfo.htm',
    method: 'get',
    data: {
        roleName: "checkBankStaff"
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
                }
            });
        }
    },
    getPropertySituationList() {
        return PropertySituationList.map((item, index) => {
            return <Option key={item.id} value={String(item.userName) }>{item.name}</Option>
        })
    },
    getAssignCheckBankList() {
        return AssignCheckBankList.map((item, index) => {
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
        var item;
        var processStateCode = this.props.record.processStateCode;
        if (processStateCode == "usertask-customerServiceStaffConfirm") {

            item = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout} label="下户费收取形式：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('receiveType', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                <Option value={0}>线上收取</Option>
                                <Option value={1}>线下收取</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
        } else {

            item = "";
        }
        var ccc, assignBank;
        var nextStep = this.props.form.getFieldsValue().nextStep;
        if (processStateCode == "usertask-collateralTaskAssign") {
            //抵押任务分配
            ccc = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout}  label="任务分配：">
                            <Select disabled={!props.canEdit} {...getFieldProps('manualAssignee', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.getPropertySituationList() }
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
        }
        else if (processStateCode == "usertask-householdTaskAssign") {//下户任务分配
            ccc = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout}  label="权证任务分配：">
                            <Select disabled={!props.canEdit} {...getFieldProps('manualAssignee', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.getPropertySituationList() }
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
            if (nextStep == "needCheckBank") {//在权证下户任务分配显示核行
                assignBank = (
                    <Row>
                        <Col span="12">
                            <FormItem  {...formItemLayout}  label="核行任务分配：">
                                <Select disabled={!props.canEdit} {...getFieldProps('assignCheckBank', { rules: [{ required: true, message: '必填' }] }) } >
                                    {this.getAssignCheckBankList() }
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                )
            }
        } else {

            ccc = "";
        }
        var roleId = window.roleId;
        var opinion;
        if (processStateCode == "usertask-recheck-refuse") {
            opinion = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout}  label="审批意见：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                <Option value={0}>拒贷</Option>
                            </Select>
                        </FormItem>

                    </Col>
                </Row>
            )
        } else if (processStateCode == "usertask-householdInvestigateTwo" || processStateCode == "usertask-houseCheckBank") {//权证下户（下户任务分配选择需要核行） 权证核行
            opinion = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout}  label="审批意见：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填' }] }) } >
                                <Option value={"pass"}>通过</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
        } else if (processStateCode == "usertask-householdInvestigate") {//权证下户（下户任务分配选择不需要核行
            opinion = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout}  label="审批意见：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填' }] }) } >
                                <Option value={"pass"}>通过</Option>
                                <Option value={"rejectProcess"}>拒贷</Option>
                                <Option value={"customerGiveUp"}>客户放弃</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
        } else {
            opinion = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout} label="审批意见：">
                            <Select disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填' }] }) } >
                                {this.state.nextStepList}
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
        }
        if (processStateCode == "usertask-relieveTaskAssign") {//解压任务分配
            opinion = (
                <div>
                    <Row>
                        <Col span="12" >
                            <FormItem  {...formItemLayout}  label="任务分配：">
                                <Select disabled={!props.canEdit} {...getFieldProps('manualAssignee', { rules: [{ required: true, message: '必填' }] }) } >
                                    {this.getPropertySituationList() }
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12" >
                            <FormItem  {...formItemLayout}  label="审批意见：">
                                <Select  disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                    <Option value={0}>通过</Option>
                                </Select>
                            </FormItem>

                        </Col>
                    </Row>
                </div>
            )
        }

        if (processStateCode == "usertask-relieve-tasks") {//押品解压
            opinion = (
                <Row>
                    <Col span="12">
                        <FormItem  {...formItemLayout}  label="审批意见：">
                            <Select  disabled={!props.canEdit} {...getFieldProps('nextStep', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                <Option value={0}>通过</Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
            )
        }
        return (
            <Form horizontal  form={this.props.form} style={{ marginTop: "20px" }}>
                {item}
                {opinion}
                {ccc}
                {assignBank}
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
