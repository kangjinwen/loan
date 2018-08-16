import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import ReviewWin from '../Components/ReviewWin';
import MatEndowBack from '../Components/MatEndowBack';
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
      visibleBack: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
      visibleBack: false,
    });
    this.refreshList();
  },
  //新增跟编辑弹窗
  showModal(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;
    var formData = {};

    if (title == "处理") {
      //获取基本信息
      Utils.ajaxData({
        url: '/modules/advance/HousAdvanceApplyAction/getHousAdvanceApplyInfo.htm',
        data: {
          projectId: selectedrecord.projectId,
          processInstanceId: selectedrecord.newProcessInstanceId
        },
        callback: (result) => {
          this.refs.ReviewWin.refs.BasiInformation.setFieldsValue(result.data.housAdvanceApplyInfo);
          this.refs.ReviewWin.refs.ProcessInformation.setFieldsValue(result.data.housAdvanceApply);
        }
      });

    }
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
    });
  },
  showModalBack(title, canEdit){
    this.setState({
      visibleBack: true,
      title: title,
      canEdit: canEdit,
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
      url: '/modules/workflow/ProcessTaskController/queryHousAdvanceLoaning.htm?isCompleted=false',
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
    var columns = [{
      title: '房屋地址',
      dataIndex: 'projectCode'
      }, {
        title: '客户名称',
        dataIndex: 'customerName'
      }, {
        title: '来源',
        dataIndex: 'businessOriginText ',
        render(text, record) {
            if (record.businessOriginText == "报单机构") {
              return <span style={{"color":"red"}}>报单机构</span>
            }else{
               return record.businessOriginText;
            }
          }
      }, {
        title: '报单机构',
        dataIndex: 'institutionName'
      }, {
        title: '报单人',
        dataIndex: "customerManager"
      }, {
        title: '状态',
        dataIndex: "status"
      }];
    var state = this.state; 
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.showModal.bind(this, '垫资放款', true) }>
            垫资放款
          </button>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.showModalBack.bind(this, '垫资收回', true) }>
            垫资收回
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <ReviewWin ref="ReviewWin"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
        <MatEndowBack ref="MatEndowBack"  visible={state.visibleBack}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
      </div>
    );
  }
})
