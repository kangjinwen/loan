import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import AddContractWin from './AddContractWin'
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
  showModal(title, record, canEdit) {
    var record = record;
    if (title == '新增') {
      record = null
    }
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
      record: record
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
    Utils.ajaxData({
      url: '/modules/common/PubContractAttachmentAction/query.htm',
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
  changeStatus(title) {
    var me = this;
    var ids = me.state.selectedRowKeys;
    var status;
    var msg = "";
    var tips = "";
    var trueurl = "";
    if (title == "禁用") {
      msg = '禁用成功';
      status = 'lock';
      tips = '您是否确定禁用';
      trueurl = "/modules/common/PubContractAttachmentAction/updateState.htm"
    } else if (title == "启用") {
      msg = '启用成功';
      status = 'unlock';
      tips = '您是否确定启用';
      trueurl = "/modules/common/PubContractAttachmentAction/updateState.htm"
    }
    confirm({
      title: tips,
      onOk: function () {
        Utils.ajaxData({
          url: trueurl,
          data: {
            ids: ids,
            status: status
          },
          method: 'post',
          callback: (result) => {
            Modal.success({
              title: result.msg,
            });
            me.refreshList();
          }
        });
      },
      onCancel: function () { }
    });
  },
  delete(record) {
    var me = this;
    confirm({
      title: '是否确认要删除这项内容',
      onOk: function () {
        Utils.ajaxData({
          url: "/modules/common/PubContractAttachmentAction/delete.htm",
          data: {
            id: record.id,
          },
          method: 'post',
          callback: (result) => {
            if (result.code == 200) {
              Modal.success({
                title: result.msg,
              });
            } else {
              Modal.error({
                title: result.msg,
              });
            }
            me.refreshList();
          }
        });
      },
      onCancel: function () { }
    });
  },
  componentDidMount() {
    this.fetch();
  },
  onRowClick(record) {
    var id = record.id;
    var selectedRowKeys = this.state.selectedRowKeys;
    if (selectedRowKeys.indexOf(id) < 0) {
      selectedRowKeys.push(id);
    } else {
      selectedRowKeys.remove(id)
    }
    this.setState({
      selectedRowKeys: selectedRowKeys
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
  downLoad() {
    var ids = this.state.selectedRowKeys;
    window.location.href = '/modules/common/PubContractAttachmentAction/downloadZip.htm?search={"ids" : ' + JSON.stringify(ids) + '}';
  },
  render() {
    var me = this;
    const {
      loading,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      type: 'checkbox',
      selectedRowKeys,
      onSelectAll: this.onSelectAll,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '合同名称',
      dataIndex: 'name'
    }, {
        title: '生效节点',
        dataIndex: "effectiveNode",
        render: (text, record) => {
          if (text == 0) {
            return <span>合同签订</span>
          } else if (text == 1) {
            return <span>展期合同签订</span>
          } else {
            return <span></span>
          }
        }
      }, {
        title: '添加时间',
        dataIndex: "createTime"
      }, {
        title: '状态',
        dataIndex: 'state',
        render: (text, record) => {
          if (text == 1) {
            return <span>启用</span>
          } else if (text == 0) {
            return <span>禁用</span>
          } else {
            return <span></span>
          }
        }
      }, {
        title: '操作',
        dataIndex: "",
        render(text, record) {

          return <div style={{ textAlign: "left" }}>
            <a href="#" onClick={me.delete.bind(null, record) }>删除</a>
          </div>
        }
      }];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" onClick={this.showModal.bind(this, '新增', null, true) }>
            新增
          </button>
          <button className="ant-btn" disabled={!hasSelected} onClick={this.changeStatus.bind(this, '禁用') }>
            禁用
          </button>
          <button className="ant-btn" disabled={!hasSelected} onClick={this.changeStatus.bind(this, '启用') }>
            启用
          </button>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.downLoad}>
            下载模板
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <AddContractWin ref="AddContractWin"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.record}
          canEdit={state.canEdit}/>
      </div>
    );
  }
})