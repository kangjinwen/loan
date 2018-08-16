
import React from 'react'
import {
  Table,
  Modal,
  Select
} from 'antd';
import AddlWin from '../AddlWin'
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
const Option = Select.Option;
var TaskAllocation = require("../../../../CommonForm/TaskAllocation");
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
  //任务分配查看弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;

    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
    }, () => {
      if (title == '放款操作') {
        Utils.ajaxData({
          url: '/modules/audit/HousBillsAction/getLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
          },
          callback: (result) => {
            var loanInfo = result.data.loanInfo;//获取后台传过来的数据
            var loans = result.data.loans;
            var housLoanfees = result.data.housLoanfees;
            var loanFormInfo = result.data.loanFormInfo;
            var refs = this.refs.AddlWin.refs;
            var borrow = result.data.borrow;
            var housPropertyInformation = result.data.housPropertyInformation
            refs.LoanInfo.setFieldsValue({ singleRate: borrow.singleRate * 1000000 / 10000 });//实收利率
            refs.LoanInfo.setFieldsValue({ repaymentRate: borrow.repaymentRate * 1000000 / 10000 });//底点利率
            refs.LoanInfo.setFieldsValue({ account: borrow.account });//借款金额
            refs.LoanInfo.setFieldsValue({ timeLimit: borrow.timeLimit });//贷款期限  
            refs.LoanInfo.setFieldsValue({ mortgageSituation: String(result.data.plBorrowRequirement.mortgageSituation) });//抵押情况 
            refs.LoanInfo.setFieldsValue(loanInfo);//对应的from表单赋值
            refs.LoanFormInfo.setFieldsValue(result.data.loan);
            refs.LoanInfo.setFieldsValue({ projectCode: borrow.projectCode });//
            refs.LoanInfo.setFieldsValue({ customerName: borrow.customerName });//
          }
        });
      }
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
      formData[k + 'bankName'] = String(item.bankName);
      formData[k + 'purpose'] = item.purpose;
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
        currentPage: 1
      }
    }
    Utils.ajaxData({
      url: '/modules/workflow/ProcessTaskController/queryAuditTasks.htm?type=usertask-loan&&isCompleted=false',
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
        title: '贷款金额(元)',
        dataIndex: "account",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '报单时间',
        dataIndex: 'declarationDate',
      }, {
        title: '任务处理人',
        dataIndex: "assignee"
      }, {
        title: '流程状态',
        dataIndex: "currentProcessStateCode"
      }, {
        title: '任务开始时间',
        dataIndex: "createTime"
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
        <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal.bind(this, '放款操作', true) }>
          放款操作
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
