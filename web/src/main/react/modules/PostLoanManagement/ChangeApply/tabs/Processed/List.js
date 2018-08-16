
import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import RecordinformationWin from '../Components/RecordinformationWin'
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

  //任务分配查看弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
    }, () => {
      if (selectedrecord.branchingProcessTypeText == '展期') {
        var refs = this.refs.RecordinformationWin.refs;
        refs.Stage.setFieldsValue(selectedrecord);
        // Utils.ajaxData({
        //   url: '/modules/common/action/plConsultAction/getConsultById.htm',
        //   data: {
        //     processInstanceId: selectedrecord.processInstanceId,
        //     isBranch: false
        //   },
        //   callback: (result) => {
        //     var Stage = result.data;
        //     var refs = this.refs.RecordinformationWin.refs;
        //     refs.Stage.setFieldsValue(Stage);
        //   }
        // });
      };
      if (selectedrecord.branchingProcessTypeText == '提前还款') {
        var refs = this.refs.RecordinformationWin.refs;
        refs.Advance.setFieldsValue(selectedrecord);
        Utils.ajaxData({
          url: '/modules/LoanChange/getAheadOfReturnLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
            isBranch: true
          },
          callback: (result) => {
            var Advance = result.data;
            var refs = this.refs.RecordinformationWin.refs;
            Advance.aheadRepayRate = Advance.aheadRepayRate * 1000000 / 10000;
            refs.Advance.setFieldsValue(Advance);


          }
        });
      };
    });
  },
  rowKey(record) {
    return record.processInstanceId;
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
      url: '/modules/LoanChange/postLoanChangeList.htm',
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
  },
  componentDidMount() {
    this.fetch();
  },
  onRowClick(record) {
    var id = record.processInstanceId;
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
        title: '合同编号',
        dataIndex: 'contractNo'
      }, {
        title: '客户名称',
        dataIndex: 'customerName'
      }, {
        title: '贷款金额(元)',
        dataIndex: "borrowAccount",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '贷款期限(月)',
        dataIndex: "timeLimit"
      }, {
        title: '还款方式',
        dataIndex: "repaymentTypeText"
      }, {
        title: '到期日期',
        dataIndex: "endRepayTime"
      }, {
        title: '目前期数',
        dataIndex: "period"
      }, {
        title: '还款状态',
        dataIndex: "repaymentStatusText"
      }, {
        title: '变更类型',
        dataIndex: 'branchingProcessTypeText',
      }, {
        title: '下一步审批人',
        dataIndex: "nextAssignee"
      }, {
        title: '流程状态',
        dataIndex: "processStateText"
      }, {
        title: '申请时间',
        dataIndex: "createTime"
      },
    ];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal.bind(this, '查看详情', false) }>
            查看详情
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <RecordinformationWin ref="RecordinformationWin"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
      </div>
    );
  }
})
