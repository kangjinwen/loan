//催收记录
import React from 'react'
import {
  Table,
  Modal
} from 'antd';
var confirm = Modal.confirm;
var data=[
  {
    "operatorName":"126",
    "operateType":"126",
    "createTime":"20160918"
  }

]
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
    if (nextProps.record) {
      this.fetch(nextProps.record);
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
    this.fetch({
      pageSize: pagination.pageSize,
      currentPage: pagination.current
    });
  },

  fetch(record) {
    var params = { pageSize: 5, currentPage: 1 }
    this.setState({
      loading: true
    });
    params.projectId = record.projectId;
    Utils.ajaxData({
      url: '',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.currentPage;
        pagination.total = result.total;
        if (!pagination.current) {
          pagination.current = 1
        };
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
    this.fetch();
  },
  onRowClick(record) {
    this.setState({
      selectedRowKeys: [record.id]
    });
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
      title: '处理人',
      dataIndex: "operatorName"
    }, {
        title: '催收记录',
        dataIndex: 'operateType'
      }, {
        title: '处理时间',
        dataIndex: "createTime"
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
              dataSource={data}
              onRowClick={this.onRowClick}
              pagination={this.state.pagination}

              onChange={this.handleTableChange}  />
          </div>
        </div>
      </Modal>
    );
  }
})

