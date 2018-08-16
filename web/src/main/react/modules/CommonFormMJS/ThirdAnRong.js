//查询结果
import React from 'react'
import {
  Table,
  Modal
} from 'antd';
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
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
      canEdit: true,
      visible: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    if (nextProps.params) {
      this.fetch(nextProps.params);
    }
  },
  handleCancel() {
    this.props.hideModal();
  },
  rowKey(record) {
    return record.id;
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
      url: '/modules/anrong/anrongInterface/getPubAnrongAttachmentList.htm',
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
  },
  onRowClick(record) {
    this.setState({
      selectedRowKeys: [record.id]
    });
  },
  downLoad(record) {
    var me = this;
    var state = this.state;
    var id = record.id
    window.location.href = '/modules/anrong/anrongInterface/downloadFile.htm?id=' + id;
  },
  render() {
    var me = this;
    const {
      loading,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      //type: 'radio',
      selectedRowKeys,
      //onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '征信名称',
      dataIndex: "fileName"
    }, {
        title: '附件类型',
        dataIndex: 'fileType'
      }, {
        title: '创建时间',
        dataIndex: 'createTime'
      }, {
        title: '操作',
        dataIndex: "",
        render(text, record) {
          return <div style={{ textAlign: "left" }}><a href="#" onClick={me.downLoad.bind(me, record) }>下载 </a></div>
        }
      },];
    var modalBtns = [
      <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
    ];
    var state = this.state;
    var props = this.props;
    return (
      <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="900"  footer={modalBtns} >
        <div style={{ height: '350px', overflowY: "scroll" }}>
          <div className="block-panel">
            <Table columns={columns} rowKey={this.rowKey}  size="middle"
              rowSelection={rowSelection}
              dataSource={this.state.data}
              onRowClick={this.onRowClick}
              pagination={this.state.pagination}
              loading={this.state.loading}
              onChange={this.handleTableChange}  />
          </div>
        </div>
      </Modal>
    );
  }
})

