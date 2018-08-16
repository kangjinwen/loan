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
      if(nextProps.visible==true){
        this.fetch(nextProps.params);
      }
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
        pageSize: 5,
        currentPage: 1,
        id:this.props.record.id
      }
    }
    Utils.ajaxData({
      url: '/modules/credit/PubCustomerRelationAction/list.htm',
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
  // downLoad(record) {
  //   var me = this;
  //   var state = this.state;
  //   var id = record.id
  //   window.location.href = '/modules/anrong/anrongInterface/downloadFile.htm?id=' + id;
  // },
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
      title: '联系方式',
      dataIndex: "type",
      render: (text, record) =>{
    		  if(record.type==1){
    			  return<div>电话</div>
    		  }else if(record.type==2){
    			  return<div>上门</div>
    		  }else if(record.type==3){
    			  return<div>邮件</div>
    		  }else if(record.type==4){
    			  return<div>短信</div>
    		  }
      }
    }, {
        title: '联系人',
        dataIndex: 'userName'
      }, {
        title: '联系内容',
        dataIndex: "remark"
      },{
        title: '联系时间',
        dataIndex: "contactTime"
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

