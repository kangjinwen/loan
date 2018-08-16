//放款单据填写基本信息
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
import BankCard from '../utils/BankCard';//银行卡4位加空格
import MicroInput from '../utils/MicroInput';//数字金额加逗号
var FuAccount = React.createClass({
    getInitialState() {
        return {
            propertyAddressId: '110101'
        }
    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        var selectRecord = props.selectRecord;
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };
        return (
            <Form horizontal form={this.props.form} style={{ marginTop: "20px" }}>
                <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="代收服务费利率：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                            <Input disabled={!props.canEdit}  {...getFieldProps('collectionRate',
                                {
                                    rules: [
                                        { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                    ]
                                }) } autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="代收服务费金额(元)：" labelCol={{ span: "10" }} wrapperCol={{ span: "13" }}>
                            <MicroInput disabled={!props.canEdit}  {...getFieldProps('collectionServiceFee',
                                {
                                    rules: [
                                        { message: '不能为负数,保留2位小数,不能大于10位数', pattern: /^(\d{1,10})(\.\d{0,2})?$/ },
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="代收服务费开户行：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('collectionServiceBank') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="代收服务费姓名：">
                            <Input disabled={!props.canEdit}  {...getFieldProps('collectionServiceName') } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                    <Col span="10">
                        <FormItem  {...formItemLayout} label="代收服务费卡号：">
                            <BankCard disabled={!props.canEdit}  {...getFieldProps('collectionServiceCard',
                                {
                                    rules: [
                                        { message: '只能是数字', pattern: /^(\d{0,100})$/ }
                                    ]
                                }
                            ) } type="text" autoComplete="off" />
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        )
    }
});
FuAccount = createForm()(FuAccount);
export default FuAccount;
