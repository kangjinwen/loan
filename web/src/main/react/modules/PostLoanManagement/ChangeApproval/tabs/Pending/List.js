
import React from 'react'
import {
  Table,
  Modal,
  Select
} from 'antd';
import AddlWin from '../Components/AddlWin'
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
  //任务分配查看弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;
    var formData2 = {};
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
    }, () => {
      if (selectedrecord.branchingProcessTypeText == '展期') {
        var refs = this.refs.AddlWin.refs;
        refs.Stage.setFieldsValue(selectedrecord);
        Utils.ajaxData({
          url: '/modules/common/action/plConsultAction/getConsultById.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
            isBranch: false
          },
          callback: (result) => {
            var Stage = result.data;
            var refs = this.refs.AddlWin.refs;
            refs.Stage.setFieldsValue(Stage);
            //获取查询机构数据
            formData2 = this.transformQueryData(result.data.housEnquiryInstitution);
            this.refs.AddlWin.refs.NewQueryOrg.setFieldsValue(formData2);

          }
        });
      };
      if (selectedrecord.branchingProcessTypeText == '提前还款') {
        var refs = this.refs.AddlWin.refs;
        refs.Advance.setFieldsValue(selectedrecord);
        Utils.ajaxData({
          url: '/modules/LoanChange/getAheadOfReturnLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
            isBranch: true
          },
          callback: (result) => {
            var Advance = result.data;
            var refs = this.refs.AddlWin.refs;
            refs.Advance.setFieldsValue(Advance);
          }
        });
      };
    });

  },
  transFormCardData(data) {
    var formData = {};
    var keys = [];
    data.forEach(item => {
      let k = item.id;//id值 需要更改
      keys.push(k);
      formData[k + 'accountHolderName'] = item.accountHolderName;
      formData[k + 'cardid'] = item.cardid;
      formData[k + 'account'] = item.account;
      formData[k + 'bankFlag'] = String(item.bankFlag);
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
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
        pageNum: 1
      }
    }
    Utils.ajaxData({
      url: '/modules/workflow/controller/ProcessTaskController/queryLoanChangeApprovalTasks.htm?isCompleted=false',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.pageNum;
        pagination.pageSize = params.pageSize;
        pagination.total = result.totalCount;
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
      pageNum: pagination.current,
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
      onSelectAll: this.onSelectAll,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '项目编号',
      dataIndex: 'projectCode'
    }, {
        title: '客户名称',
        dataIndex: 'customerName'
      }, {
        title: '变更类型',
        dataIndex: "branchingProcessTypeText"
      }, {
        title: '处理人',
        dataIndex: "executor"
      }, {
        title: '来源',
        dataIndex: "businessOriginText",
        render(text, record) {
          if (record.businessOriginText == "报单机构") {
            return <span style={{ "color": "red" }}>报单机构</span>
          } else {
            return record.businessOriginText;
          }
        }
      }, {
        title: '机构名称',
        dataIndex: "institutionName"
      }, {
        title: '报单人',
        dataIndex: "customerManager"
      }, {
        title: '报单时间',
        dataIndex: 'declarationDate',
      }, {
        title: '任务开始时间',
        dataIndex: "startTime"
      },
    ];
    var state = this.state, buttons;
    if (roleId == "financialManager" || roleId == "customerServiceManager" || roleId == "warrantDirector" || roleId == "riskControllDirector" || roleId == "taskAssign") {//任务分配财务主管。客服主管.权证主管. 风控主管：
      buttons = (
        <button className="ant-btn" key="2" disabled={!hasSelected} onClick={this.showModalTask.bind(this, '任务分配', true) }>
          任务分配
        </button>
      );
    } else {
      buttons = (
        <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal.bind(this, '处理', true) }>
          处理
        </button>
      );
    }
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          {buttons}
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <AddlWin ref="AddlWin"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
        <TaskAllocation ref="TaskAllocation"  visible={state.visibleTask}
          getUserNameList={state.getUserNameList}
          title={state.title} hideModal={me.hideModal}
          record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
      </div>
    );
  }
})
