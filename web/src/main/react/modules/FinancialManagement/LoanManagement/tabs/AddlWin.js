import React from 'react';
import {
    Button,
    Modal,
    Tabs,
} from 'antd';
import HistoryApprove from '../../../CommonForm/HistoryApprove';
import ProcessInformation from './ProcessInformation';
import BorrowingNeeds from '../../../CommonForm/BorrowingNeeds';
const NavLine = require("./../../../utils/NavLine");
var NewEvaOrg = require("../../../CommonForm/NewEvaOrg");
import ResultApprove from '../../../CommonFormMJS/ResultApprove';
var NewQueryOrg = require("../../../CommonForm/NewQueryOrg");
var BasiInformation = require("../../../CommonForm/BasiInformation");
var LendingOperations = require("../../../CommonForm/LendingOperations");
var HousingInformation = require("../../../CommonForm/HousingInformation");
import LoanInfo from './LoanInfo';
import CardList from '../../../CommonForm/CardList';
import HousLoanfees from './HousLoanfees';
import LoanFormInfo from './LoanFormInfo';
import FuAccount from '../../../CommonForm/FuAccount';
var UploadALL = require("../../../CommonForm/UploadALL");
const TabPane = Tabs.TabPane;
const objectAssign = require('object-assign');
var AddlWin = React.createClass({
    getInitialState() {
        var selectRecord = this.props.record;
        return {
            status: {},
            activekey: "1",
            idData: selectRecord ? selectRecord.id : null,
            formData: {},
            loading: false,
            resultApprove:{}
        };
    },
    handleCancel() {
        if (this.refs.LoanFormInfo) {
            this.refs.LoanFormInfo.resetFields();
        }
        if (this.refs.ProcessInformation && this.props.canEdit) {
            this.refs.ProcessInformation.resetFields();
        }
        this.props.hideModal();
        this.changeTabState();
    },
    handleTabClick(key) {
        this.setState({
            activekey: key
        })
        if(key == 7){
            this.queryReslutApprove()
        }
    },
    changeTabState() {
        this.setState({
            idData: null,
            activekey: 1,
        })
    },
    queryReslutApprove(){
        let {processInstanceId} = this.props.record;
        Utils.ajaxSubmit({
            me: this,
            url: '/modules/common/PubApprovalResultsAction/queryPubApprovalResults.htm',
            method: 'post',
            data: {
                processInstanceId
            },
            type: 'json',
            callback: (response) => {
                if(response.code==200){
                    this.setState({
                        resultApprove:response
                    })
                }else{
                    this.setState({
                        resultApprove:{}
                    })
                }
            }
        });
    },
    //提交
    handleOk() {
        var selectRecord = this.props.record;
        var state = this.state;
        var props = this.props;
        var ProcessInformation = this.refs.ProcessInformation;
        var LoanFormInfo = this.refs.LoanFormInfo;//取到LoanFormInfo中的Dom
        if (!LoanFormInfo) {
            this.handleTabClick("1");
            return;
        }
        LoanFormInfo.validateFields((errors, values) => {
            if (!!errors) {
                this.handleTabClick("1");
                return;
            } else {
                if (!ProcessInformation) {
                    this.handleTabClick("2");
                    return;
                }
                ProcessInformation.validateFields((errors, values) => {
                    if (!!errors) {
                        this.handleTabClick("2");
                        return;
                    } else {
                        var remarkData = ProcessInformation.getFieldsValue();
                        var LoanData = LoanFormInfo.getFieldsValue();
                        var commentData = {};
                        commentData.comment = remarkData.nextStep;

                        var postdata = {
                            accountHolderName: LoanData.accountHolderName,
                            bankName: LoanData.bankName,
                            cardid: LoanData.cardid,
                            account: LoanData.account,


                            customerName: LoanData.customerName,
                            remark: LoanData.remark,
                            bankCardId: LoanData.bankCardId,//从LoanFormInfo中取到bankFlag字段的信息
                            thirdTransferFee: LoanData.thirdTransferFee,//三方转账手续费(
                            thirdAccount: LoanData.thirdAccount,//三方金额
                            bankAccount: LoanData.bankAccount,
                            // loanTime: LoanData.loanTime,
                            loanTime: DateFormat.formatTime("yyyy-MM-dd hh:mm:ss", LoanData.loanTime),
                            loanAmount: LoanData.loanAmount,
                            remarkComment: remarkData.remarkComment,
                            consultId: selectRecord.consultId,
                            nextStep: remarkData.nextStep,
                            processStateCode: selectRecord.processStateCode,
                            projectId: selectRecord.projectId,
                            processInstanceId: selectRecord.processInstanceId
                        }
                        Utils.ajaxSubmit({
                            me: this,
                            url: '/modules/workflow/controller/ProcessTaskController/completeTask.htm',
                            method: 'post',
                            data: {
                                taskId: selectRecord.taskId,
                                processVariables: JSON.stringify(commentData),
                                serviceVariables: JSON.stringify(postdata)
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
                });
            }
        });
    },

    navLineData: {
        "基本信息": "#s1",
        "放款信息": "#s2",
    },
    navLineData1: {
        "基本信息": "#s1",
        "放款信息": "#s2",
    },
    navLineData2: {
        "基本信息": "#s1",
        "放款信息": "#s2",
    },
    downLoadAll() {//附件全部下载
        var type = "FILE_DOWNLOAD";
        var record = this.props.record;
        var processInstanceId = record.processInstanceId;
        var projectId = record.projectId;
        window.location.href = '/modules/common/BatchFileDownloadAction/downloadZip.htm?search={"projectId" : ' + projectId + ', "type":"' + type + '", "processInstanceId":"' + processInstanceId + '"}';
    },
    render() {
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                提 交
            </Button>
        ];
        if (props.title == '查看详情') {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返回</button>
            ]
        }
        if (props.title == '查看详情' && state.activekey == 4) {
            modalBtns = [
                <button key="submit" className="ant-btn" onClick={this.downLoadAll}>附件全部下载</button>,
                <button key="back" className="ant-btn" onClick={this.handleCancel}>返回</button>
            ]
        }
        if (props.title == "放款操作" && state.activekey == 4) {
            modalBtns = [
                <button key="back" className="ant-btn" onClick={this.handleCancel}>取消</button>,
                <button key="submit" className="ant-btn" onClick={this.downLoadAll}>附件全部下载</button>,
                <Button key="button" className="ant-btn ant-btn-primary" loading={state.loading} disabled={!props.canEdit}  onClick={this.handleOk}>
                    提 交
                </Button>
            ];
        }
        var selectRecord = props.record;
        var idData
        if (selectRecord && selectRecord.processInstanceId) {
            idData = selectRecord.processInstanceId;
        }
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="1200" footer={modalBtns} >

                <Tabs defaultActiveKey="1" onTabClick={this.handleTabClick} activeKey={this.state.activekey}>
                    <TabPane tab="基本信息" key="1" >
                        <div style={{ position: "relative" }}>
                            <div className="navLine-wrap" onScroll={NavLineUtils._handleSpy.bind(this, this.navLineData) }>
                                <div className="col-22 navLine-wrap-left" >
                                    <div id="s1">
                                        <h2>基本信息</h2>
                                        <LoanInfo ref="LoanInfo" canEdit={!props.canEdit} />
                                    </div>
                                    <div id="s2">
                                        <h2>放款信息</h2>
                                        <LoanFormInfo ref="LoanFormInfo" canEdit={!props.canEdit} record={props.record}/>
                                    </div>

                                </div>
                                <div className="navLine-wrap-right" >
                                    {!props.canEdit ? <NavLine navLineData={this.navLineData2} activeItem="#s1" ref="NavLine" /> : <NavLine navLineData={this.navLineData1} activeItem="#s1" ref="NavLine" />}
                                </div>
                            </div>
                        </div>
                    </TabPane>
                    <TabPane tab="附件上传" key="4">
                        <UploadALL ref="UploadALL" idData={idData} canEdit={props.canEdit} selectRecord={props.record} title={props.title}/>
                    </TabPane>
                    <TabPane tab="审批结果" key="7">
                        <ResultApprove ref="ResultApprove" selectRecord={props.record} canEdit={false} resultApprove={state.resultApprove}/>
                    </TabPane>
                    <TabPane tab="审批历史" key="3">
                        <HistoryApprove ref="HistoryApprove" canEdit={props.canEdit} record={props.record}/>
                    </TabPane>
                    <TabPane tab="处理意见" key="2">
                        <ProcessInformation ref="ProcessInformation" canEdit={props.canEdit} record={props.record} />
                    </TabPane>
                </Tabs>
            </Modal>
        );
    }
});
export default AddlWin;
