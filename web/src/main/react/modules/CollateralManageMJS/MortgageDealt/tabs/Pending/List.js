import React from 'react'
import {Modal, Select, Table,} from 'antd';
import ReviewWin from '../ReviewWin'

var TaskAllocation = require("../../../../CommonForm/TaskAllocation");
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
const Option = Select.Option;
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {},
            visible: false,
            visibleTask: false,
        };
    },
    componentWillReceiveProps(nextProps, nextState) {
        this.clearSelectedList();
        this.fetch(nextProps.params);
    },
    hideModal() {
        this.setState({
            visible: false,
            visibleTask: false,
        });
        this.refreshList();
    },
    transformeData(data) {
        var formData = {};
        var keys = [];
        data.forEach(item => {
            let k = item.assessmentAgencies;
            keys.push(k);
            formData[k + 'assessedValue'] = item.assessedValue;
            formData[k + 'id'] = item.id;
        })
        formData["keys"] = keys;
        return formData;
    },
    transFormPersonData(data) {//人员类型
        var formData = {};
        var keys = [];
        data.forEach(item => {
            let k = item.id;//id值 需要更改
            keys.push(k);
            formData[k + 'type'] = item.type;
            formData[k + 'personName'] = item.personName;
            formData[k + 'personNumber'] = item.personNumber;
            formData[k + 'personPhone'] = item.personPhone;
            formData[k + 'id'] = item.id;
        })
        formData["keys"] = keys;
        return formData;
    },
    //新增跟编辑查看弹窗
    showModal(title, record, canEdit, canUpload) {
        var selectedrecord = this.state.selectedrecord
        var me = this;
        if (roleId == "declarationStaff" || roleId == "customerServiceStaffB" || roleId == "system") {
            var ProjectBelongslist = [];
            Utils.ajaxData({
                url: '/modules/system/getProjectBelongInfo.htm',
                method: 'get',
                type: 'json',
                callback: (result) => {
                    var data = result.data;
                    if (data.projectBelongs == 1) {
                        this.refs.ReviewWin.refs.BorrowingNeeds.setFieldsValue({projectBelongs: data.projectBelongs});
                    }
                    if (data.projectBelongs == 2) {
                        this.refs.ReviewWin.refs.BorrowingNeeds.setFieldsValue({projectBelongs: data.projectBelongs});
                        this.refs.ReviewWin.refs.BorrowingNeeds.setFieldsValue({institutionName: data.institutionName});
                    }
                    if (data.projectBelongs == 3) {
                        this.refs.ReviewWin.refs.BorrowingNeeds.setFieldsValue({projectBelongs: data.projectBelongs});
                    }
                }
            });
        }
        var formData = {};
        if (title == "处理") {
            Utils.ajaxData({
                url: '/modules/common/action/plConsultAction/getConsultById.htm',
                data: {
                    processInstanceId: selectedrecord.processInstanceId,
                    projectId: selectedrecord.projectId
                },
                callback: (result) => {
                    var id = result.data.housPropertyInformation.id;
                    var housProperty = result.data.housPropertyInformation;
                    var plBorrow = result.data.plBorrowRequirement;
                    result.data.plBorrowRequirement.singleRate = result.data.plBorrowRequirement.singleRate * 1000000 / 10000;
                    result.data.plBorrowRequirement.repaymentRate = result.data.plBorrowRequirement.repaymentRate * 1000000 / 10000;
                    //housProperty.propertySituation = housProperty.propertySituation == undefined ? '' : housProperty.propertySituation;
                    //housProperty.propertyProperties = housProperty.propertyProperties == undefined ? '' : housProperty.propertyProperties;
                    // housProperty.planningPurposes = housProperty.planningPurposes == undefined ? '' : housProperty.planningPurposes;
                    //housProperty.whetherOneContact = String(housProperty.whetherOneContact);
                    // housProperty.whetherTwoContact = String(housProperty.whetherTwoContact);
                    //housProperty.keyDiskQuery = housProperty.keyDiskQuery == undefined ? '' : housProperty.keyDiskQuery;
                    housProperty.keyDiskQuery = String(housProperty.keyDiskQuery);
                    plBorrow.repaymentType = String(plBorrow.repaymentType);
                    // plBorrow.projectBelongs = plBorrow.projectBelongs == undefined ? '' : plBorrow.projectBelongs;
                    //plBorrow.projectBelongs = String(plBorrow.projectBelongs);
                    plBorrow.productId = String(plBorrow.productId);
                    formData = this.transformeData(result.data.housAssessmentAgencies);
                    this.refs.ReviewWin.refs.HousingInformation.setFieldsValue(housProperty);
                    this.refs.ReviewWin.refs.BorrowingNeeds.setFieldsValue(result.data.plBorrowRequirement);
                    this.setState({
                        id: id,
                    });
                }
            });
        }
        this.setState({
            visible: true,
            title: title,
            canEdit: canEdit,
            canUpload: canUpload
        });
    },
    rowKey(record) {
        return record.consultId;
    },
    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        this.refreshList();
    },
    fetch(params = {}) {
        this.setState({
            loading: true
        });
        if (!params.pageSize) {
            var params = {};
            params = {
                pageSize: 10,
                currentPage: 1
            }
        }
        var url = '/modules/workflow/ProcessTaskController/queryPledgeRegiste.htm?isCompleted=false';

        Utils.ajaxData({
            url: url,
            data: params,
            callback: (result) => {
                const pagination = this.state.pagination;
                pagination.current = params.currentPage;
                pagination.pageSize = params.pageSize;
                pagination.total = result.total;
                if (!pagination.current) {
                    pagination.current = 1
                }
                ;
                this.setState({
                    loading: false,
                    data: result.data,
                    pagination
                });
            }
        });
    },
    clearSelectedList() {
        this.setState({
            selectedRowKeys: [],
        });
    },
    refreshList() {
        var pagination = this.state.pagination;
        var params = objectAssign({}, this.props.params, {
            currentPage: pagination.current,
            pageSize: pagination.pageSize
        });
        this.fetch(params);
        this.clearSelectedList();
    },
    componentDidMount() {
        this.fetch();
    },
    onRowClick(record) {
        var id = record.consultId;
        this.setState({
            selectedRowKeys: [id],
            selectedrecord: record
        });
    },
    render() {
        var me = this;
        const {
            loading,
            selectedRowKeys
        } = this.state;
        var roleId = window.roleId;
        const rowSelection = {
            // type: 'checkbox',
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;
        var columns = [{
            title: '项目编号',
            dataIndex: 'projectCode'
        }, {
            title: '客户名称',
            dataIndex: 'customerName'
        }, {
            title: '报单时间',
            dataIndex: 'declarationDate',
        }, {
            title: '流程状态',
            dataIndex: "currentProcessStateCode"
        }, {
            title: '任务处理人',
            dataIndex: "executor"
        }, {
            title: '任务开始时间',
            dataIndex: "startTime"
        }
        ];
        var state = this.state;
        return (
            <div className="block-panel">
                <div className="actionBtns" style={{marginBottom: 16}}>
                    <button className="ant-btn" key="1" disabled={!hasSelected}
                            onClick={this.showModal.bind(this, '处理', "", true, true)}>
                        处理
                    </button>
                </div>
                <Table columns={columns} rowKey={this.rowKey} size="middle" params={this.props.params}
                       rowSelection={rowSelection}
                       onRowClick={this.onRowClick}
                       dataSource={this.state.data}
                       pagination={this.state.pagination}
                       loading={this.state.loading}
                       onChange={this.handleTableChange}/>
                <ReviewWin ref="ReviewWin" visible={state.visible} title={state.title} hideModal={me.hideModal}
                           record={state.selectedrecord} id={state.id} canEdit={state.canEdit}
                           canUpload={state.canUpload}/>
                <TaskAllocation ref="TaskAllocation" visible={state.visibleTask} getUserNameList={state.getUserNameList}
                                title={state.title} hideModal={me.hideModal} record={state.selectedrecord} id={state.id}
                                canEdit={state.canEdit}/>
            </div>
        );
    }
})
