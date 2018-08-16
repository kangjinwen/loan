import React from 'react'
import {
  Table,
  Modal,
  Select
} from 'antd';
import AddlWin from '../AddlWin'
import RefundlWin from '../RefundlWin'
var confirm = Modal.confirm;
var TaskAllocation = require("../../../../CommonForm/TaskAllocation");
const Option = Select.Option;
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
      visible1: false,
      visible2: false,
      visibleTask: false,
    });
    this.refreshList();
  },
  //收费编辑弹窗
  showModal1(title, canEdit) {
    var selectedrecord = this.state.selectedrecord
    var me = this;
    var formData = {};
    var formData2 = {};
    var processStateCode = selectedrecord.processStateCode
    if (title == "收费") {
      Utils.ajaxData({
        url: '/modules/finance/HousLowerCostAction/getHousLowerCostInfo.htm',
        data: {
          processInstanceId: selectedrecord.processInstanceId,
          projectId: selectedrecord.projectId
        },
        callback: (result) => {
          this.refs.AddlWin.refs.Charge_xh.setFieldsValue(result.data.housLowerCostBasicInfo);
          this.refs.AddlWin.refs.Charge_xh.setFieldsValue(result.data.housLowerCostInfo);
        }
      });
    }
    this.setState({
      visible1: true,
      title: title,
      canEdit: canEdit,
      formData: formData2
    });
  },
  //退费模块弹窗
  showModal2(title, canEdit) {
    var selectedrecord = this.state.selectedrecord
    var processStateCode = selectedrecord.processStateCode
    var me = this;
    var formData = {};
    var formData2 = {};
    if (title == "退费" && processStateCode == "usertask-reutrnFee") {
      Utils.ajaxData({
        url: '/modules/finance/HousLowerCostAction/getHousLowerCostInfo.htm',
        data: {
          processInstanceId: selectedrecord.oldProcessInstanceId,
          projectId: selectedrecord.projectId
        },
        callback: (result) => {
          var housLowerCostBasicInfo = result.data.housLowerCostBasicInfo;//获取后台传过来的数据
          var housLowerCostInfo = result.data.housLowerCostInfo;
          var refs = this.refs.RefundlWin.refs;
          refs.Charge_xh.setFieldsValue(housLowerCostBasicInfo);//对应的from表单赋值
          refs.Charge_xh.setFieldsValue(housLowerCostInfo);
        }
      });
    }
    this.setState({
      visible2: true,
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
      url: '/modules/workflow/ProcessTaskController/queryhousLowerCost.htm?isCompleted=false',
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
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    var disabled = true;
    if (this.state.selectedrecord && this.state.selectedrecord.processStateCode == "usertask-collectAssessmentFee") {
      disabled = false;
    }
    var disabled2 = true;
    if (this.state.selectedrecord && this.state.selectedrecord.processStateCode == "usertask-reutrnFee") {
      disabled2 = false;
    }
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
        title: '贷款期限(月)',
        dataIndex: "timeLimit"
      }, {
        title: '来源',
        dataIndex: 'businessOriginText',
        render(text, record) {
          if (record.businessOriginText == "报单机构") {
            return <span style={{ "color": "red" }}>报单机构</span>
          } else {
            return record.businessOriginText;
          }
        }
      }, {
        title: '机构名称',
        dataIndex: 'institutionName',
      }, {
        title: '报单人',
        dataIndex: 'customerManager',
      }, {
        title: '报单时间',
        dataIndex: 'declarationDate',
      }, {
        title: '数据类型',
        dataIndex: "dataType"
      },{
        title: '任务类型',
        dataIndex: 'processStateCode',
        render: function (value) {
          if (value == 'usertask-collectAssessmentFee')
          { return "收费"; }
          else if (value == 'usertask-reutrnFee') {
            return "退费";
          }

        }
      }];
    var state = this.state, buttons;
    if (roleId == "financialManager" || roleId == "customerServiceManager" || roleId == "warrantDirector" || roleId == "riskControllDirector" || roleId == "taskAssign") {//任务分配财务主管。客服主管.权证主管. 风控主管：
      buttons = (
        <button className="ant-btn" key="2" disabled={!hasSelected} onClick={this.showModalTask.bind(this, '任务分配', true) }>
          任务分配
        </button>
      );
    } else {
      buttons = [
        <button className="ant-btn" key="1" disabled={disabled} onClick={this.showModal1.bind(this, '收费', true) }>
          收费
        </button>,
        <button className="ant-btn"  key="2" disabled={disabled2} onClick={this.showModal2.bind(this, '退费', true) }>
          退费
        </button>
      ];
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
        <AddlWin ref="AddlWin"  visible={state.visible1} formData={state.formData}   showModal={this.showModal1.bind(this, '收费', true) }
          title={state.title} hideModal={me.hideModal} record={state.selectedrecord} canEdit={state.canEdit}/>
        <RefundlWin ref="RefundlWin"  visible={state.visible2} formData={state.formData}   showModal={this.showModal2.bind(this, '退费', true) }
          title={state.title} hideModal={me.hideModal} record={state.selectedrecord} canEdit={state.canEdit}/>
        <TaskAllocation ref="TaskAllocation"  visible={state.visibleTask}
          getUserNameList={state.getUserNameList}
          title={state.title} hideModal={me.hideModal}
          record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
      </div>
    );
  }
})
