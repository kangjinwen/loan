import React from 'react'
import {
  Table,
  Modal,
  Select
} from 'antd';
import AddlWin from '../AddlWin'
var confirm = Modal.confirm;
const Option = Select.Option;
var TaskAllocation = require("../../../../CommonForm/TaskAllocation");
const objectAssign = require('object-assign');
export default React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      pagination: {},
      canEdit: true,
      visible: false,
      visibleResult: false,
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
      visibleResult: false,
      visibleTask: false,
    });
    this.refreshList();
  },
  transformeData(data) {
    var formData = {};
    var keys = [];
    data.forEach(item => {
      let k = item.assessmentAgencies;
      if (keys.indexOf(k) < 0) {
        keys.push(k);
      }
      else return
      formData[k + 'assessedValue'] = item.assessedValue;
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
  },
  //查看时转换查询机构数据
  transformQueryData(data) {
    var ids = [];
    var formData = {};
    data.forEach(item => {
      var obj = {};
      var id = item.id;
      let k = item.institutionType;
      if (ids.indexOf(k) < 0) {
        ids.push(item.institutionType);
      }
      else return
      //是建委
      if (item.institutionType == 1) {
        formData[k + 'id'] = item.id;
        formData[k + "remark"] = item.remark;
        formData[k + "mortgage"] = item.mortgage;
        formData[k + "seizure"] = Boolean(item.seizure);
        formData[k + "businessOccupancy"] = Boolean(item.businessOccupancy);
        formData[k + "netSignedOccupancy"] = Boolean(item.netSignedOccupancy);
        formData[k + "hochstbetragshypothek"] = Boolean(item.hochstbetragshypothek);
      }
      //是安融
      else if (item.institutionType == 4) {
        formData[k + 'id'] = item.id;
        formData[k + "remark"] = item.remark;
        formData[k + "legalProcessPerformed"] = Boolean(item.legalProcessPerformed);
        formData[k + "affiliate"] = Boolean(item.affiliate);
      }
      else {
        formData[k + 'id'] = item.id;
        formData[k + "remark"] = item.remark;
        formData[k + "legalProcessPerformed"] = Boolean(item.legalProcessPerformed);
      }
    });
    formData["keys"] = ids;
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
  //新增跟编辑弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord
    var me = this;
    var formData = {};
    var formData2 = {};
    if (title == "处理") {
      Utils.ajaxData({
        url: '/modules/common/action/plConsultAction/getConsultById.htm',
        data: {
          processInstanceId: selectedrecord.processInstanceId,
        },
        callback: (result) => {
          var id = result.data.housPropertyInformation.id;
          var borrowId = result.data.plBorrowRequirement.id;
          result.data.housPropertyInformation.whetherOneContact = String(result.data.housPropertyInformation.whetherOneContact);
          result.data.housPropertyInformation.whetherTwoContact = String(result.data.housPropertyInformation.whetherTwoContact);
          result.data.housPropertyInformation.propertyProperties = result.data.housPropertyInformation.propertyProperties == undefined ? '' : result.data.housPropertyInformation.propertyProperties;
          result.data.housPropertyInformation.planningPurposes = result.data.housPropertyInformation.planningPurposes == undefined ? '' : result.data.housPropertyInformation.planningPurposes;
          result.data.housPropertyInformation.propertySituation = result.data.housPropertyInformation.propertySituation == undefined ? '' : result.data.housPropertyInformation.propertySituation;
          result.data.housPropertyInformation.keyDiskQuery = result.data.housPropertyInformation.keyDiskQuery == undefined ? '' : result.data.housPropertyInformation.keyDiskQuery;
          result.data.housPropertyInformation.keyDiskQuery = String(result.data.housPropertyInformation.keyDiskQuery);
          result.data.plBorrowRequirement.repaymentType = String(result.data.plBorrowRequirement.repaymentType);
          result.data.plBorrowRequirement.singleRate = result.data.plBorrowRequirement.singleRate * 1000000 / 10000;
          result.data.plBorrowRequirement.repaymentRate = result.data.plBorrowRequirement.repaymentRate * 1000000 / 10000;
          //result.data.plBorrowRequirement.projectBelongs = String(result.data.plBorrowRequirement.projectBelongs);
          result.data.plBorrowRequirement.productId = String(result.data.plBorrowRequirement.productId);
          this.refs.AddlWin.refs.HousingInformation.setFieldsValue(result.data.housPropertyInformation);
          this.refs.AddlWin.refs.BorrowingNeeds.setFieldsValue(result.data.plBorrowRequirement);
          this.setState({
            id: id,
            borrowId: borrowId
          });
        }
      });
    }
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
      formData: formData2
    });
  },
  showModalTask(title, canEdit) {//任务分配
    this.setState({
      visibleTask: true,
      canEdit: true,
      title: title,
    }, () => {
      var selectedrecord = this.state.selectedrecord
      if (title == "任务分配") {//查询任务分配下拉值
        Utils.ajaxData({
          url: '/modules/workflow/controller/ProcessTaskController/queryUserList.htm',
          data: {
            taskId: selectedrecord.taskId
          },
          callback: (result) => {
            var items = result.data.map((item) => {
              return (<Option key={item.userId} value={item.userName}>{item.name}</Option>);
            });
            var getUserNameList = [];
            getUserNameList = items;
            this.setState({ getUserNameList: getUserNameList });
          }
        });
      }
    });
  },
  rowKey(record) {
    return record.taskId;
  },

  downLoadProject() {//项目
    var type;
    var selectedrecord = this.state.selectedrecord;
    var processInstanceId = selectedrecord.processInstanceId;
    if (selectedrecord.businessOriginText == "报单机构") {
      type = "org";
    } else {
      type = "personal";
    }
    var exportPoint = "usertask-face-audit";
    window.open('/modules/common/action/exportAction/exportProjectForm.htm?exportPoint=' + exportPoint + '&type=' + type + '&processInstanceId=' + processInstanceId);
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
    Utils.ajaxData({
      url: '/modules/workflow/ProcessTaskController/queryOrgReviewAudit.htm?isCompleted=false',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.currentPage;
        pagination.pageSize = params.pageSize;
        pagination.total = result.total;
        if (!pagination.current) {
          pagination.current = 1
        };
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
    var id = record.taskId;
    this.setState({
      selectedRowKeys: [id],
      selectedrecord: record
    });
  },
  //选择
  onSelectAll(selected, selectedRowKeys, selectedRows, changeRows) {
    if (selected) {
      this.setState({
        selectedRowKeys
      })
    } else {
      this.setState({
        selectedRowKeys: []
      })
    }
  },
  render() {
    var me = this;
    const {
      loading,
      selectedRowKeys
    } = this.state;
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
        title: '申请额度(元)',
        dataIndex: "financialAdvisers",
        render(value) {
          return Utils.toThousands(value)
        }
      },{
        title: '报单时间',
        dataIndex: 'declarationDate',
      }, {
        title: '流程状态',
        dataIndex: "currentProcessStateCode"
      }, {
        title: '任务开始时间',
        dataIndex: "startTime"
      },];
    var state = this.state, buttons;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.showModal.bind(this, '处理', true) }>
            处理
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <AddlWin ref="AddlWin"  visible={state.visible} formData={state.formData} id={state.id}
          borrowId={state.borrowId} title={state.title} hideModal={me.hideModal} record={state.selectedrecord} canEdit={state.canEdit}/>
        <TaskAllocation ref="TaskAllocation"  visible={state.visibleTask}
          getUserNameList={state.getUserNameList}
          title={state.title} hideModal={me.hideModal}
          record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
      </div>
    );
  }
})
