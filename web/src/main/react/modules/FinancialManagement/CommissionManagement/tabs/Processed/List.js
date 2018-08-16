import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import ReviewWin from '../Components/ReviewWin';
var confirm = Modal.confirm;
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
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
    });
    this.refreshList();
  },
  //新增跟编辑弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord
    var me = this;
    if (title == "查看详情") {
      Utils.ajaxData({
        url: '/modules/rebate/RebateManageAction/getBorrowBasicDataByInstanceId.htm',
        data: {
          processInstanceId: selectedrecord.oldProcessInstanceId,
          period: selectedrecord.period,
        },
        callback: (result) => {
          result.data.singleRate = result.data.singleRate * 1000000 / 10000;
          result.data.repaymentRate = result.data.repaymentRate * 1000000 / 10000;
          this.refs.ReviewWin.refs.BasiInformation.setFieldsValue(result.data);
          this.refs.ReviewWin.refs.FuAccount.setFieldsValue(result.data);
          this.refs.ReviewWin.refs.BasiInformation.setFieldsValue({ period: selectedrecord.period });
        }
      });
      Utils.ajaxData({
        url: '/modules/rebate/RebateManageAction/getRebateHandleDataByInstanceId.htm',

        data: {
          processInstanceId: selectedrecord.processInstanceId
        },
        callback: (result) => {
          result.data.rebatePoints = result.data.rebatePoints * 1000000 / 10000;
          this.refs.ReviewWin.refs.CommissionDraw.setFieldsValue(result.data);
        }
      });
      Utils.ajaxData({//读取放款管理放款信息
        url: '/modules/audit/HousBillsAction/getLoanInfo.htm',
        data: {
          processInstanceId: selectedrecord.oldProcessInstanceId
        },
        callback: (result) => {
          var refs = this.refs.ReviewWin.refs;
          var loanFormInfo = result.data.loanFormInfo;//表单信息
          var loans = result.data.loans;//放款卡
          refs.LoanFormInfo.setFieldsValue(loanFormInfo);
          loans && refs.CardList.setFieldsValue(this.transFormCardData(loans));
        }
      });
    }
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
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
      formData[k + 'bankName'] = item.bankName;
      formData[k + 'purpose'] = item.purpose;
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
  },
  rowKey(record) {
    return record.taskId;
  },
  downLoad() {//财务
    var type;
    var selectedrecord = this.state.selectedrecord;
    var processInstanceId = selectedrecord.processInstanceId;
    var exportPoint = "usertask-returnCommission";
    var type = "normal";
    window.open('/modules/common/action/exportAction/exportFinancialForm.htm?exportPoint=' + exportPoint + '&type=' + type + '&processInstanceId=' + processInstanceId);
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
      url: '/modules/rebate/RebateProcessTaskController/getRebateHandleTasks.htm?isCompleted=true',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.currentPage;
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
  /*onSelectAll(selected, selectedRowKeys, selectedRows, changeRows) {
    if (selected) {
      this.setState({
        selectedRowKeys
      })
    } else {
      this.setState({
        selectedRowKeys: []
      })
    }
  },*/
  render() {
    var me = this;
    const {
      loading,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      selectedRowKeys
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
        dataIndex: 'account',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '贷款期限(月)',
        dataIndex: 'timeLimit'
      }, {
        title: '当前返佣期数',
        dataIndex: 'period',
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
        title: '任务处理人',
        dataIndex: "assignee"
      }, {
        title: '流程状态',
        dataIndex: "currentProcessState"
      }, {
        title: '任务开始时间',
        dataIndex: "startTime"
      }, {
        title: '任务结束时间',
        dataIndex: "endTime"
      }
    ];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal.bind(this, '查看详情', false) }>
            查看详情
          </button>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.downLoad}>
            导出财务流转单
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <ReviewWin ref="ReviewWin"  visible={state.visible}  title={state.title} hideModal={me.hideModal} id={state.id} record={state.selectedrecord} canEdit={state.canEdit}/>
      </div>
    );
  }
})
