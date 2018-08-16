
import React from 'react'
import {
  Table,
  Modal,
  Select,
} from 'antd';
import RevieCustumerwWin from '../RevieCustumerwWin'
var confirm = Modal.confirm;
import ChackLog from './ChackLog';
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
      visibleCust: false,
      visibleChack: false,
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
      visibleCust: false,
      visibleChack: false,
    });
    this.refreshList();
  },
  showModal2(title, canEdit) {
    var record = this.state.selectedrecord
    var me = this;
    this.setState({
      visibleChack: true,
      title: title,
      canEdit: canEdit,
      record: record,
    });
  },
  showModalCust(title, record, canEdit) {//新增客户
    this.setState({
      visibleCust: true,
      title: title,
      canEdit: canEdit,
    });
  },
  rowKey(record) {
    return record.id;
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
    var url = '/modules/credit/RepaymentCollectionAction/getRepaymentList.htm';
    Utils.ajaxData({
      url: url,
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
    this.clearSelectedList();
  },
  componentDidMount() {
    this.fetch();
  },
  onRowClick(record) {
    var id = record.id;
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
        title: '项目名称',
        dataIndex: 'projectName'
      },
      {
        title: '客户姓名',
        dataIndex: 'customerName'
      }, {
        title: '期次',
        dataIndex: "period"
      }, {
        title: '还款日期',
        dataIndex: "repaymentTime"
      }, {
        title: '应还金额',
        dataIndex: 'account'
      }, {
        title: '罚息',
        dataIndex: 'defaultInterest'
      }, {
        title: '违约金',
        dataIndex: 'penalty'
      }, {
        title: '还款状态',
        dataIndex: 'repaymentStatusText',
      }, {
        title: '催收次数',
        dataIndex: 'dunCount'
      }, {
        title: '上次催收日期',
        dataIndex: 'lastDuntime'
      }
    ];

    var state = this.state;
    var selectedrecord = state.selectedrecord;
    var recordLog = state.selectedrecord
    var paramsLog = {};
    if (recordLog) {
      paramsLog.id = recordLog.id;
    }
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" key="2" disabled={!hasSelected} onClick={this.showModalCust.bind(this, '新增催收', state.record, true) }>
            新增催收
          </button>
          <button className="ant-btn" key="3" disabled={!hasSelected} onClick={this.showModal2.bind(this, '催收明细', state.record, true) }>
            催收明细
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <RevieCustumerwWin ref="RevieCustumerwWin"  visible={state.visibleCust}  title={state.title} hideModal={me.hideModal} record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
        <ChackLog ref="ChackLog"  visible={state.visibleChack}  params={paramsLog}  title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
      </div>
    );
  }
})
