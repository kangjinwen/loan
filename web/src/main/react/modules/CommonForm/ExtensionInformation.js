//基本信息
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    InputNumber,
    Row,
    Col,
    DatePicker
} from 'antd';
const createForm = Form.create;
const Option = Select.Option;
const FormItem = Form.Item;
var ExtensionInformation = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            formData: {}
        };
    },
    handleReset() {
        this.refs.validation.reset();
        this.setState(this.getInitialState());
    },
    // componentWillReceiveProps(nextProps) {
    //     if (nextProps.record && this.props.record != nextProps.record) {
    //         this.queryFormData(nextProps.record);
    //     }
    // },
    // componentDidMount() {
    //     if (this.props.record) {
    //         this.queryFormData(this.props.record);
    //     }
    // },
    // queryFormData(record) {
    //     this.props.form.setFieldsValue(record);
    // },
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
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="项目编号：">
                            <Input disabled={true}  {...getFieldProps('projectCode') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="客户姓名：">
                            <Input disabled={true}  {...getFieldProps('customerName') }  type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="借款金额(元)：">
                            <InputNumber disabled={true} style={{ width: "100%" }} {...getFieldProps('borrowAccount') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="剩余本金(元)：">
                            <InputNumber disabled={true} style={{ width: "100%" }} {...getFieldProps('remainderAmount') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="贷款起始日期：">
                            <DatePicker disabled={true}  {...getFieldProps('startRepayTime') } style={{ width: "100%" }}/>
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="贷款到期日期：">
                            <DatePicker disabled={true}  {...getFieldProps('endRepayTime') } style={{ width: "100%" }}/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="借款期限(月)：" >
                            <InputNumber disabled={true} style={{ width: "100%" }} {...getFieldProps('timeLimit') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="申请展期金额(元)：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('extensionAmount') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="申请展期期限(月)：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('extensionCount') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="展期费(元)：">
                            <InputNumber disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('extensionFee') }  autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="展期利率(%)：">
                            <Input disabled={!props.canEdit} style={{ width: "100%" }} {...getFieldProps('extensionRate') }  type="number" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="7">
                        <FormItem  {...formItemLayout} label="展期返佣点位：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('extensionReturnfeeRate') }  type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form >
        )
    }
});
ExtensionInformation = createForm()(ExtensionInformation);
export default ExtensionInformation;
