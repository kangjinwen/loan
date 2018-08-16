//新增打款
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Upload,
    Table,
    message,
} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
var PersonnelType = React.createClass({
    getInitialState() {
        return {
            orgData: [],
            keys: [],
            showAddOrg: false
        }
    },
    remove(k) {
        const { form } = this.props;
        var id = form.getFieldValue(`${k}id`);//取到记录的id
        if (id) {
            //删除后台数据
            Utils.ajaxData({
                url: '/modules/instance/HousPersonTypeAction/deleteById.htm',
                method: 'get',
                data: {
                    id: id//返回给后台的参数 
                },
                callback: (result) => {
                    console.log(result)
                }
            });
        }
        let keys = form.getFieldValue('keys');
        keys = keys.filter((key) => {
            return key !== k;
        });
        form.setFieldsValue({
            keys,
        });
    },
    add() {
        const { form } = this.props;
        let keys = form.getFieldValue('keys');
        keys = keys.concat(this.selectVlaue++);
        form.setFieldsValue({
            keys
        });
    },
    selectVlaue: 0,
    render() {
        const { getFieldProps, getFieldValue } = this.props.form;
        getFieldProps('keys', {
            initialValue: [],
        });

        const formItems = getFieldValue('keys').map((k) => {

            return (<tr key={k}>
                <td key={`k1`}>
                    <FormItem >
                        <Select disabled={!this.props.canEdit} {...getFieldProps(`${k}type`) } >
                            <Option  value={0}>共有人</Option>
                            <Option  value={1}>共有人配偶</Option>
                        </Select>
                    </FormItem>
                </td>
                <td key={`k2`}>
                    <FormItem >
                        <Input {...getFieldProps(`${k}personName`) } disabled={!this.props.canEdit} type="text" />
                    </FormItem>
                </td>
                <td key={`k3`}>
                    <FormItem >
                        <Input {...getFieldProps(`${k}personNumber`,
                            {
                                rules: [
                                    { validator: validator.checkIdCode }]
                            }
                        ) } disabled={!this.props.canEdit} type="text" />
                    </FormItem>
                </td>
                <td key={`k4`}>
                    <FormItem >
                        <Input {...getFieldProps(`${k}personPhone`) } disabled={!this.props.canEdit} type="text" />
                    </FormItem>
                </td>
                {this.props.canEdit ? <td key={`k6`}>
                    <Input {...getFieldProps(`${k}id`) } type="hidden" />
                    <a onClick={() => this.remove(k) }>删除</a>
                </td> : null}
            </tr >
            );
        });
        const formItemLayout = {
            labelCol: {
                span: 3
            },
            wrapperCol: {
                span: 15
            },
        };
        return <Form form={this.props.form} style={{ marginTop: 15 }}>
            <Input {...getFieldProps('id') } type="hidden" autoComplete="off" />
            {this.props.canEdit ? <Button onClick={this.add} style={{ color: '#00aaee', borderColor: '#00aaee' }}>新增人员类型</Button> : null}
            <table style={{ width: 800, marginLeft: 25, marginBottom: 20 }} className="addTable" ref="addTable" >
                <tbody>
                    <tr>
                        <td width='23%'>
                            类型
                        </td>
                        <td>
                            姓名
                        </td>
                        <td>
                            身份证号
                        </td>
                        <td>
                            联系方式
                        </td>
                        {this.props.canEdit ? <td width='9%'>
                            操作
                        </td> : null}
                    </tr>
                    {getFieldValue('keys').length ? formItems : null}
                </tbody>
            </table >
        </Form>
    }
});
PersonnelType = createForm()(PersonnelType);
export default PersonnelType;







