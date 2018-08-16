//基本信息
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
var Region = require('./../../../plugin/Region');
import ComboData from './../../../utils/ComboData';
const NavLine = require("./../../../utils/NavLine");
var MarryStatuslist = ComboData.getIntCombo('MARITAL_STATUS');
var educationList = ComboData.getIntCombo('EDUCATION');
var healthyList = ComboData.getIntCombo('HEALTHY');
var Sex = ComboData.getIntCombo('SEX');
var HousingInformation = React.createClass({
    getInitialState() {
        return {
            residentialAddressId: '110101',
        }
    },
    changeAreaId(name, areaId) {
        this.setState({
            [name]: areaId
        })
    },
    handleReset() {
        this.refs.validation.reset();
        this.setState(this.getInitialState());
    },
    navLineData: {
        "基本信息": "#s1",
        "职业信息": "#s2",
        "其他": "#s3",
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
                <div style={{ position: "relative" }}>
                    <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                        <div className="col-22 navLine-wrap-left" >
                            <div id="s1">
                                <h2>基本信息</h2>
                                <Row>{/*
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="客户编号：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('numberCust') }  type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>*/}
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="客户名称：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('name', { rules: [{ required: true, message: '必填' }] }) }  type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="性别：">
                                            <Select  disabled={!props.canEdit} {...getFieldProps('sex', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                                <Option  value={1}>男</Option>
                                                <Option  value={2}>女</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="身份证号：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('certNumber',
                                                {
                                                    rules: [
                                                        { required: true, message: '必填' }, { validator: validator.checkIdCode }]
                                                }
                                            ) } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout}  label="婚姻状况：">
                                            <Select disabled={!props.canEdit} {...getFieldProps('marryStatus', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                                <Option  value={1}>未婚</Option>
                                                <Option  value={2}>已婚</Option>
                                                <Option  value={3}>离异</Option>
                                                <Option  value={4}>丧偶</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="学历：">
                                            <Select disabled={!props.canEdit} {...getFieldProps('education') } >
                                                <Option  value={1}>高中及以下</Option>
                                                <Option  value={2}>大专</Option>
                                                <Option  value={3}>本科</Option>
                                                <Option  value={4}>硕士及以上</Option>
                                                <Option  value={5}>其他</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="有无子女：">
                                            <Select  disabled={!props.canEdit} {...getFieldProps('isHaveChildren', { rules: [{ required: true, message: '必填', type: "number" }] }) } >
                                                <Option  value={0}>有</Option>
                                                <Option  value={1}>无</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="紧急联系电话：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
                                            <Input disabled={!props.canEdit}  {...getFieldProps('emergencyContactNumber') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="健康状况：">
                                            <Select  disabled={!props.canEdit} {...getFieldProps('healthy') } >
                                                <Option  value={0}>良好</Option>
                                                <Option  value={1}>一般</Option>
                                                <Option  value={2}>较差</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout}  label="联系人号码：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('mobile',
                                                {
                                                    rules: [{ required: true, message: '手机号格式不正确', pattern: /^1[3|4|5|7|8]\d{9}$/, }]
                                                }
                                            ) } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="销售员：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('teamManager') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="业务员：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('salesman') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="17">
                                        <FormItem  labelCol={{ span: 3 }} wrapperCol={{ span: 18 }} label="现居住地(省)：">
                                            <Region {...getFieldProps('areaId', { initialValue: this.state.residentialAddressId, onChange: this.changeAreaId.bind(this, "areaId") }) }   disabled={!this.props.canEdit}/>
                                        </FormItem>
                                    </Col>
                                    <Col span="4" >
                                        <FormItem style ={{ marginLeft: "-84px" }}>
                                            <Input disabled={!props.canEdit} placeholder="详细地址"  {...getFieldProps('liveAddress') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="19">
                                        <FormItem  {...formItemLayout} label="户籍地址：" labelCol={{ span: "3" }}>
                                            <Input disabled={!props.canEdit}  {...getFieldProps('householdAddress') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                            </div>
                            <div id="s2">
                                <h2>职业信息</h2>
                                <Row>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="工作单位：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('company') }  type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="所在部门：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('department') }  type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="职位：">
                                            <Input disabled={!props.canEdit}  {...getFieldProps('position') }  type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="是否自营：">
                                            <Select  disabled={!props.canEdit} {...getFieldProps('isSelfSupport') } >
                                                <Option  value={0}>是</Option>
                                                <Option  value={1}>否</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="公司电话：" labelCol={{ span: "9" }} wrapperCol={{ span: "14" }}>
                                            <Input disabled={!props.canEdit}  {...getFieldProps('companyPhone') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                    <Col span="7">
                                        <FormItem  {...formItemLayout} label="工作状态：">
                                            <Select  disabled={!props.canEdit} {...getFieldProps('workStatus') } >
                                                <Option  value={1}>在职</Option>
                                                <Option  value={2}>离职</Option>
                                                <Option  value={3}>退休</Option>
                                                <Option  value={4}>自营</Option>
                                            </Select>
                                        </FormItem>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span="19">
                                        <FormItem  {...formItemLayout} label="公司地址：" labelCol={{ span: "3" }}>
                                            <Input disabled={!props.canEdit}  {...getFieldProps('companyAddr') } type="text" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                            </div>
                            <div id="s3">
                                <h2>其他</h2>
                                <Row>
                                    <Col span="19">
                                        <FormItem  {...formItemLayout} label="备注："  labelCol={{ span: "3" }}>
                                            <Input disabled={!props.canEdit}  {...getFieldProps('remark') } type="textarea" autoComplete="off" />
                                        </FormItem>
                                    </Col>
                                </Row>
                            </div>
                        </div>
                        <div className="navLine-wrap-right" >
                            <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                        </div>
                    </div>
                </div>
            </Form >
        )
    }
});
HousingInformation = createForm()(HousingInformation);
export default HousingInformation;
