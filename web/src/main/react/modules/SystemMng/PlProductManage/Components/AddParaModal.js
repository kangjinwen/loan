import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Tree,
    TreeSelect,
    Row,
    Col
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
let treeData = [];
Utils.ajaxData({
    url: '/modules/system/checkboxoffices.htm',
    method: 'get',
    type: 'json',
    callback: (result) => {
        let data = result.data;
        treeData = data;
    }
});
// var ProductTypeList = [];
// Utils.ajaxData({
//     url: '/modules/paramconfig/ProductTypeAction/getProductTypeCombo.htm',
//     method: 'get',
//     type: 'json',
//     callback: (result) => {
//         ProductTypeList = result.data;
//     }
// });
var RepaymentTypeList = [];
Utils.ajaxData({
    url: '/getDicts.htm?type=REPAYMENT_TYPE',
    method: 'get',
    type: 'json',
    callback: (result) => {
        RepaymentTypeList = result.data;
    }
});
var AddParaModal = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
            needParam: []
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

        var url = "/modules/common/PlProductAction/saveOrUpdatePlProduct.htm";
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }
            values.repaymentRate = this.props.form.getFieldsValue().repaymentRate * 100 / 10000
            values.overduePenaltyRate = this.props.form.getFieldsValue().overduePenaltyRate * 100 / 10000
            values.aheadRepayRate = this.props.form.getFieldsValue().aheadRepayRate * 100 / 10000
            if (values.officeIds) {
                // var officeIds = values.officeIds.join(',')
                var officeIds = values.officeIds
            } else {
                var officeIds = ""
            }
            var data = objectAssign({}, {
                json: JSON.stringify(objectAssign({}, values, {
                    officeIds: officeIds
                }))
            }, {
                    status: 'create'
                });
            if (title == "编辑") {
                var data = objectAssign({}, {
                    json: JSON.stringify(objectAssign({}, values, {
                        officeIds: officeIds
                    }))
                }, {
                        status: 'update'
                    });
            }
            Utils.ajaxData({
                url: url,
                data: data,
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
        });
    },
    getRepaymentTypeList() {
        return RepaymentTypeList.map((item, index) => {
            return <Option key={item.value} >{item.text}</Option>
        })
    },
    render() {
        const {
            getFieldProps
        } = this.props.form;
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary"  onClick={this.handleOk}>
                提 交
            </button>
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
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="1000"  footer={modalBtns} >
                <Form horizontal  form={this.props.form}>
                    <Input  {...getFieldProps('id', { initialValue: '' }) } type="hidden"   />
                    <Row>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="产品类型：">
                                <Select id="select" disabled={!props.canEdit} {...getFieldProps('productType', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                    {this.props.ProductTypeList}
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="产品名称：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('name', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="底点利率(%)：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('repaymentRate',
                                    {
                                        rules: [
                                            { required: true, message: '必填', type: 'float' },
                                            { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                        ]
                                    }) } type="number" autoComplete="off" />
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="还款方式：">
                                <Select id="select" disabled={!props.canEdit} {...getFieldProps('repaymentType', { rules: [{ required: true, message: '必填' }] }) } >
                                    {this.getRepaymentTypeList() }
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="逾期罚息费率(%)：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('overduePenaltyRate',
                                    {
                                        rules: [
                                            { required: true, message: '必填', type: 'float' },
                                            { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                        ]
                                    }
                                ) }  autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="适用机构：">
                                <TreeSelect disabled={!props.canEdit} dropdownStyle={{ maxHeight: 400, overflow: 'auto' }} treeData={treeData}
                                    {...getFieldProps('officeIds') }
                                    placeholder="请选择" treeDefaultExpandAll />
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="提前还款罚息费率(%)：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('aheadRepayRate',
                                    {
                                        rules: [
                                            { required: true, message: '必填', type: 'float' },
                                            { message: '不能为负数，不能大于100,保留两位小数', pattern: /^(\d{1,2}(\.\d{0,2})?|100)$/ },
                                        ]
                                    }
                                ) } autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem  {...formItemLayout} label="备注：">
                                <Input disabled={!props.canEdit}  {...getFieldProps('remark', { rules: [{ required: true, message: '必填' }] }) } type="text" autoComplete="off" />
                            </FormItem>
                        </Col>
                    </Row>
                </Form>
            </Modal>
        );
    }
});
AddParaModal = createForm()(AddParaModal);
export default AddParaModal;
