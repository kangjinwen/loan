import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import UrgedWin from './UrgedWin';//催收弹窗
import UrgedrecordWin from './UrgedrecordWin';
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
var data = [{
  "projectCode": "123",
  "htbh": "123",
  "customerName": "123",
  "repayCapital": "123",
  "needpayCapital": "123",
  "financeSpecialist": "123",
  "returnFee": "123",
  

}]
export default React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      pagination: {
        pageSize: 5,
        pageNum: 1
      },
      canEdit: true,
      visible: false,
      visible2: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
      visible2: false,
    });
    this.refreshList();
    var pagination = this.state.pagination;

  },
  //新增跟编辑弹窗
  showModal(title, canEdit) {
    var record = record;
    if (title == '新增催收') {
      var record = record;
      this.refs.UrgedWin.setFieldsValue(record);
    }
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
      record: record
    });
  },
  showModal2(title, record, canEdit) {
    var record = record;
    this.refs.UrgedWin.setFieldsValue(record);
    this.setState({
      canEdit: canEdit,
      visible2: true,
      title: title,
      record: record
    });
  },
  rowKey(record) {
    return record.taskId;
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
  fetch(params = { pageSize: 5, pageNum: 1 }) {
    this.setState({
      loading: true
    });  
    Utils.ajaxData({
      url: '',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.pageNum;
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
      pageNum: pagination.current,
      pageSize: pagination.pageSize
    });
    this.fetch(params);
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
      type: 'radio',
      selectedRowKeys,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '项目编号',
      dataIndex: 'projectCode'
    }, {
        title: '项目名称',
        dataIndex: "htbh"
      }, {
        title: '客户姓名',
        dataIndex: "customerName"
      }, {
        title: '报单人',
        dataIndex: "officeName"
      }, {
        title: '当前期次',
        dataIndex: "timeLimit"
      }, {
        title: '还款日期',
        dataIndex: "account"
      }, {
        title: '本期应还金额',
        dataIndex: "repayInterest"
      }, {
        title: '罚息',
        dataIndex: "repayCapital"
      }, {
        title: '本期应还总额',
        dataIndex: "needpayCapital"
      }, {
        title: '催收次数',
        dataIndex: "financeSpecialist"
      }, {
        title: '上次催收日期',
        dataIndex: "returnFee"
      }];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal.bind(this, '新增催收', true) }>
            催收
          </button>
          <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal2.bind(this, '查看催收记录', true) }>
            查看催收记录
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params} rowSelection={rowSelection}
          dataSource={data}
          onRowClick={this.onRowClick}
          pagination={this.state.pagination}
          onChange={this.handleTableChange}  />
        <UrgedWin ref="UrgedWin"  visible={state.visible} title={state.title} hideModal={me.hideModal} record={state.record}
          canEdit={state.canEdit}/>
        <UrgedrecordWin ref="UrgedrecordWin"  visible={state.visible2}    title={state.title} hideModal={me.hideModal} record={state.record}
          canEdit={state.canEdit}/>
      </div>
    );
  }
})
