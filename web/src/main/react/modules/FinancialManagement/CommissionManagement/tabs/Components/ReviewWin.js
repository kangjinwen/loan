import React from 'react';
import {
    Button,
    Modal,

} from 'antd';

const objectAssign = require('object-assign');
const NavLine = require("./../../../../utils/NavLine");
import HistoryApprove from '../../../../CommonForm/HistoryApprove';
var BasiInformation = require("../../../../CustomerServiceTrial/AuditReturn/tabs/Components/BasiInformation");
var UploadPhoto = require("../../../../CommonForm/UploadPhoto");
var ProcessInformation = require("../../../../CommonForm/ProcessInformation");
import FuAccount from '../../../../CommonForm/FuAccount';
import CommissionDraw from './CommissionDraw';
import CardList from '../../../../CommonForm/CardList';
import LoanFormInfo from '../../../LoanManagement/tabs/LoanFormInfo';
var UploadPic = require("../../../../CommonForm/UploadPic");
var ReviewWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {
                idData: ""
            },
            activekey: "1",
            formData: {},
            loading: false
        };
    },
    handleCancel() {
        if (this.refs.ProcessInformation) {
            this.refs.ProcessInformation.resetFields();
        }
        this.refs.BasiInformation.resetFields();
        this.refs.CommissionDraw.resetFields();

        this.props.hideModal();
        this.changeTabState();
    },
    changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },
    handleTabClick(key) {
        this.setState({
            activekey: key
        }, () => {
        })
    },
    navLineData: {
        "基本信息": "#s1",
        "放款信息": "#s2",
        "代收服务费": "#s3",
        "返佣划扣": "#s4",
    },
    handleOk(isvalid1) {
        var me = this;
        var validation2 = this.refs.BasiInformation.validateFields;
        validation2((errors, values) => {
            if (!!errors || !isvalid1) {
                me.handleTabClick("1");
            } else {

                var validation3 = this.refs.CommissionDraw.validateFields;
                validation3((errors, values) => {
                    if (!!errors) {
                        me.handleTabClick("4");
                        return
                    } else {
                        var state = this.state;
                        var props = this.props;
                        var selectRecord = props.record;
                        var postdata = {};
                        var commentData = {};
                        var CommissionDraw = this.refs.CommissionDraw.getFieldsValue();
                        CommissionDraw.rebatePoints = CommissionDraw.rebatePoints * 10000 / 1000000
                        postdata.housRebate = CommissionDraw;
                        postdata.processInstanceId = selectRecord.processInstanceId;
                        postdata.consultId = selectRecord.consultId;
                        postdata.processStateCode = selectRecord.processStateCode;
                        postdata.projectId = selectRecord.projectId;
                        postdata.nextStep = CommissionDraw.nextStep;
                        postdata.remarkComment = CommissionDraw.remarkComment;
                        commentData.comment = CommissionDraw.nextStep;
                        Utils.ajaxSubmit({
                            me: me,
                            url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                            method: 'post',
                            data: {
                                taskId: this.props.record.taskId,
                                serviceVariables: JSON.stringify(postdata),
                                processVariables: JSON.stringify(commentData)
                            },
                            type: 'json',
                            callback: (result) => {
                                if (result.code == 200) {
                                    Modal.success({
                                        title: result.msg,
                                        onOk: () => {
                                            this.handleCancel();
                                        }
                                    });
                                } else {
                                    Modal.error({
                                        title: result.msg,
                                    });
                                }
                            }
                        });
                    }
                })
            }
        })
    },

    render() {
        var props = this.props;
        var state = this.state;
        var button;
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

        var idData = props.id;
        return (
            <Modal title={props.title} visible={props.visible}  onCancel={this.handleCancel}   width="1200"  footer={modalBtns} >

                <div style={{ position: "relative" }}>
                    <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                        <div className="col-22 navLine-wrap-left" >

                            <div id="s1">
                                <h2>基本信息</h2>
                                <BasiInformation ref="BasiInformation" canEdit={props.canEdit}/>
                            </div>
                            <div id="s2">
                                <h2>放款信息</h2>
                                <LoanFormInfo ref="LoanFormInfo" canEdit={false} record={props.record}/>
                                <CardList ref="CardList" canEdit={false} />
                            </div>
                            <div id="s3">
                                <h2>代收服务费</h2>
                                <FuAccount ref="FuAccount" canEdit={false}  />
                            </div>
                            <div id="s4">
                                <h2>返佣划扣</h2>
                                <CommissionDraw ref="CommissionDraw" canEdit={props.canEdit} record={props.record}/>
                            </div>
                        </div>
                        <div className="navLine-wrap-right" >
                            <NavLine navLineData={this.navLineData} activeItem="#s1" ref="NavLine"/>
                        </div>
                    </div>
                </div>
            </Modal>
        );
    }
});
export default ReviewWin;
