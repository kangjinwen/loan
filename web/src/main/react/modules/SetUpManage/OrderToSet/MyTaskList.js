import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import ReviewWin from './ReviewWin';
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
    //this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false
    });
    this.refreshList();
  },

  //新增跟编辑弹窗
  showModal(title, canEdit) {
    // var selectedrecord = this.state.selectedrecord
    // var me = this;
    // var formData = {};
    // var formData2 = {};
    // var ProjectBelongslist = [];
    // Utils.ajaxData({
    //   url: '/modules/system/getProjectBelongInfo.htm',
    //   method: 'get',
    //   type: 'json',
    //   callback: (result) => {
    //   }
    // });
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
     // formData: formData2
    });
  },
  rowKey(record) {
    return record.taskId;
  },
  //分页
  // handleTableChange(pagination, filters, sorter) {
  //   const pager = this.state.pagination;
  //   pager.current = pagination.current;
  //   this.setState({
  //     pagination: pager,
  //   });
  //   this.refreshList();
  // },
  // fetch(params = {}) {
  //   this.setState({
  //     loading: true
  //   });
  //   if (!params.pageSize) {
  //     var params = {};
  //     params = {
  //       pageSize: 10,
  //       currentPage: 1
  //     }
  //   }
  //   Utils.ajaxData({
  //     url: '/modules/workflow/ProcessTaskController/querySurveyTasks.htm?isCompleted=false',
  //     data: params,
  //     callback: (result) => {
  //       const pagination = this.state.pagination;
  //       pagination.current = params.currentPage;
  //       pagination.pageSize = params.pageSize;
  //       pagination.total = result.total;
  //       if (!pagination.current) {
  //         pagination.current = 1
  //       };
  //       this.setState({
  //         loading: false,
  //         data: result.data,
  //         pagination
  //       });
  //     }
  //   });
  // },
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
    //this.fetch(params);
    this.clearSelectedList();
  },
  // componentDidMount() {
  //   this.fetch();
  // },
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
    var columns = [];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn"  disabled={hasSelected} onClick={this.showModal.bind(this, '接单设置', true) }>
            接单设置
          </button>
        </div>
       {/* <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />*/}
        <ReviewWin ref="ReviewWin"  visible={state.visible} formData={state.formData}  getCredit={this.state.getCredit} showModal={this.showModal.bind(this, '接单设置', true) }
          title={state.title} hideModal={me.hideModal} record={state.selectedrecord} canEdit={state.canEdit} />
      </div>
    );
  }
})
