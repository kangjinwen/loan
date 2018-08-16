import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import AddlWin from '../../AddlWin'
import Actions from '../Actions'
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
  toThousands(num) {
    var num = (num || 0).toString(), result = '';
    while (num.length > 3) {
      result = ',' + num.slice(-3) + result;
      num = num.slice(0, num.length - 3);
    }
    if (num) { result = num + result; }
    return result;
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
    if (title == "结算处置") { 
      Utils.ajaxData({
        url: '/modules/settleaccount/PlSettlementfeeAction//settleAccountsDetail.htm',
        data: {
          processInstanceId: selectedrecord.processInstanceId,
          projectId: selectedrecord.projectId
        },
        callback: (result) => {
          this.refs.AddlWin.refs.SettlementSuggestWin.setFieldsValue(result.data);
          this.refs.AddlWin.refs.SettlementInfoWin.setFieldsValue(selectedrecord);
          var borrowAccount = this.toThousands(selectedrecord.borrowAccount);
          this.refs.AddlWin.refs.SettlementInfoWin.setFieldsValue({ borrowAccount: borrowAccount });
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
    return record.index;
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
        pageNum: 1
      }
    }
    Utils.ajaxData({
      url: '/modules/settleaccount/PlSettlementfeeAction/getPlSettlementfeeList.htm?isCompleted=false',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.pageNum;
        pagination.pageSize = params.pageSize;
        pagination.total = result.total;
        if (!pagination.current) {
          pagination.current = 1
        };
        for (var i = 0, length = result.data.length; i < length; i++) {
          result.data[i].index = i
        }
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
      pageNum: pagination.current,
      pageSize: pagination.pageSize
    });
    this.fetch(params);
    this.clearSelectedList();
  },
  componentDidMount() {
    this.fetch();
  },
  onRowClick(record) {
    var id = record.index;
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
        title: '合同编号',
        dataIndex: 'contractNo'
      }, {
        title: '客户名称',
        dataIndex: 'customerName'
      }, {
        title: '借款金额(元)',
        dataIndex: "borrowAccount",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '来源',
        dataIndex: 'businessOriginText',
        render(text, record) {
          if (record.businessOriginText == "报单机构") {
            return <span style={{ "color": "red" }}>报单机构</span>
          } else {
            return record.businessOriginText;
          }
        }
      }, {
        title: '机构名称',
        dataIndex: 'institutionName',
      }, {
        title: '报单人',
        dataIndex: 'customerManager',
      }, {
        title: '报单时间',
        dataIndex: 'declarationDate',
      },];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn"  disabled={!hasSelected} onClick={this.showModal.bind(this, '结算处置', true) }>
            结算处置
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <AddlWin ref="AddlWin"  visible={state.visible} formData={state.formData}   title={state.title} hideModal={me.hideModal} record={state.selectedrecord} canEdit={state.canEdit}/>
      </div>
    );
  }
})
