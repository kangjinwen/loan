//主表
import React from 'react'
import {
  Table,
  Modal
} from 'antd';
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
import AppActions from '../../frame/actions/AppActions';
export default React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      pagination: {
        pageSize: 5,
        currentPage: 1
      },
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.fetch(nextProps.params);
  },
  rowKey(record) {
    return record.index;
  },
  //选择
  onSelectChange(selectedRowKeys) {
    this.setState({
      selectedRowKeys
    });
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
  componentDidMount() {
    this.fetch(this.props.params);
  },
  fetch(params = { pageSize: 5, currentPage: 1 }) {
    this.setState({
      loading: true
    });
    Utils.ajaxData({
      url: '/modules/workflow/ProcessTaskController/queryMyTaskStatistic.htm',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.currentPage;
        pagination.total = result.totalCount;
        if (!pagination.current) {
          pagination.current = 1
        };
        for (var i = 0, length = result.data.length; i < length; i++) {
          result.data[i].index = i
        }
        this.setState({
          loading: false,
          data: result.data,
          pagination,
        });
        this.clearList();
      }
    });
  },
  clearList() {
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
    this.clearList();
  },
  onRowClick(record) { 
    var me = this;
    var label = record.label;
    var scriptid = record.scriptId;
    me.handleClick(label, scriptid)
    this.setState({
      record: record,
      processInstanceId: record.processInstanceId,
      selectedRowKeys: [record.index]
    });
  },
  handleClick(tabName, tabId) {//模块跳转
    AppActions.setTabActiveKey(tabName, tabId);
  },
  render() {
    var me = this;
    const {
      loading,
      record,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      //type: 'radio',
      selectedRowKeys,
      //onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '任务类型',
      dataIndex: 'myTaskType'
    }, {
        title: '任务数量',
        dataIndex: "myTaskNumber",
        render(text, record) {
          var label = record.label;
          var scriptid = record.scriptId;
          return <div style={{ textAlign: "left" }}><a href="#" onClick={me.handleClick.bind(null, label, scriptid) }>{record.myTaskNumber}</a></div>
        }
      }];
    var state = this.state;
    return (
      <div className="block-panel">
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params} rowSelection={rowSelection}
          dataSource={this.state.data} onRowClick={this.onRowClick}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
      </div>
    );
  }
})
