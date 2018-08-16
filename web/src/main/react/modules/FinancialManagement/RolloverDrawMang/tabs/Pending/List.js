import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import AddlWin from '../AddlWin'
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
      visible: false
    });
    this.refreshList();
  },
  //新增跟编辑弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord
    var me = this;
    var formData = {};
    var formData2 = {};
    if (title == "单笔划扣") {
      Utils.ajaxData({
        url: '/modules/extension/ExtensionAction/getHousDeduct.htm',
        data: {
          processInstanceId: selectedrecord.processInstanceId,
          newProcessInstanceId: selectedrecord.newProcessInstanceId
        },
        callback: (result) => {
          this.refs.AddlWin.refs.RenewaFeesWin.setFieldsValue(selectedrecord);
          this.refs.AddlWin.refs.RenewaFeesWin.setFieldsValue(result.data.pubProcessBranching);
          this.refs.AddlWin.refs.RenewaFeesWin.setFieldsValue(result.data.data);
          this.refs.AddlWin.refs.RenewaFeesWin.setFieldsValue({ extensionAmountChinese: result.data.extensionAmountChinese });
        }
      });
    }
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
      formData: formData2
    });
  },
  rowKey(record) {
    return record.taskId;
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
      url: '/modules/workflow/ProcessTaskController/queryDeductTasks.htm?isCompleted=false',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.currentPage;
        pagination.pageSize = params.pageSize;
        pagination.total = result.total;
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
      // type: 'checkbox',
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '项目编号',
      dataIndex: 'projectCode'
    }, {
        title: '客户名称',
        dataIndex: 'customerName'
      }, {
        title: '展期批贷金额(元)',
        dataIndex: "extensionAmount"
      }, {
        title: '展期服务费总额(元)',
        dataIndex: "actualExtensionFee"
      }, {
        title: '来源',
        dataIndex: "businessOriginText",
        render(text, record) {
            if (record.businessOriginText == "报单机构") {
              return <span style={{"color":"red"}}>报单机构</span>
            }else{
               return record.businessOriginText;
            }
          }
      }, {
        title: '机构名称',
        dataIndex: "institutionName"
      }, {
        title: '报单人',
        dataIndex: "customerManager"
      },{
          title: '报单时间',
          dataIndex: 'declarationDate',
        }, {
        title: '展期起始日',
        dataIndex: "assigwwnee"
      }, {
        title: '任务开始时间',
        dataIndex: "createTime"
      },];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.showModal.bind(this, '单笔划扣', true) }>
            单笔划扣
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <AddlWin ref="AddlWin"  visible={state.visible} formData={state.formData}   showModal={this.showModal.bind(this, '单笔划扣', true) }
          title={state.title} hideModal={me.hideModal} record={state.selectedrecord} canEdit={state.canEdit}/>
      </div>
    );
  }
})
