
import React from 'react'
import {
  Table,
  Modal,
  Select,
} from 'antd';
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
const Option = Select.Option;
export default React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      pagination: {},
      visible: false,
      visibleTask: false,
      visibleCust: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
      visibleTask: false,
      visibleCust: false,
    });
    this.refreshList();
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
  //   var url = '/modules/common/PubCustomerAction/getPubCustomerList.htm';
  //   Utils.ajaxData({
  //     url: url,
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
    this.fetch(params);
    this.clearSelectedList();
  },
  // componentDidMount() {
  //   this.fetch();
  // },
  onRowClick(record) {
    var id = record.id;
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
    var roleId = window.roleId;
    const rowSelection = {
      // type: 'checkbox',
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '客户编号',
      dataIndex: 'name'
    }, {
        title: '项目名称',
        dataIndex: 'certNumber',
      }, {
        title: '客户姓名',
        dataIndex: 'mobile',
      }, {
        title: '还款期次',
        dataIndex: 'loans',
      }, {
        title: '预警类型',
        dataIndex: 'salsman',
      }, {
        title: '预警内容',
        dataIndex: "processStatusName"
      },
    ];

    var state = this.state;
    var selectedrecord = state.selectedrecord;
    var disabled1;
    if (selectedrecord) {
      if (selectedrecord.processInstanceId) {
        disabled1 = hasSelected;
      } else {
        disabled1 = false
      }

    }
    var dataSourceData = [{
      key: "1",
      id: "1",
      name: "12",
      certNumber: "客户曹德充借款",
      mobile: "曹德充",
      loans: "2",
      salsman: "即将到期",
      processStatusName: "剩余1天",
    },{
      key: "2",
      id: "2",
      name: "13",
      certNumber: "客户邓堃登借款",
      mobile: "邓堃登",
      loans: "2",
      salsman: "逾期催收",
      processStatusName: "逾期13天",
    },{
      key: "3",
      id: "3",
      name: "33",
      certNumber: "客户王添福借款",
      mobile: "王添福",
      loans: "1",
      salsman: "即将到期",
      processStatusName: "剩余13天",
    },]
    return (
      <div className="block-panel">
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={dataSourceData}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
      </div>
    );
  }
})
