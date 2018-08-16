
import React from 'react'
import {
  Table,
  Modal,
  Select,
} from 'antd';
import ReviewWin from '../ReviewWin'
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
  //新增客户跟编辑查看弹窗
  showModal(title, record, canEdit) {
    var selectedrecord = this.state.selectedrecord
    var me = this;
    if (title == "查看贷款信息") {//报单专员
      Utils.ajaxData({
        url: '/modules/common/action/plConsultAction/getConsultById.htm',
        data: {
          processInstanceId: selectedrecord.processInstanceId,
          customerId: selectedrecord.id
        },
        callback: (result) => {//marryStatus
          var housProperty = result.data.housPropertyInformation;
          var plBorrow = result.data.plBorrowRequirement;
          plBorrow.singleRate = plBorrow.singleRate * 1000000 / 10000;
          plBorrow.repaymentRate = plBorrow.repaymentRate * 1000000 / 10000;
          //housProperty.whetherTwoContact = housProperty.whetherTwoContact == undefined ? '' : housProperty.whetherTwoContact;
          //housProperty.propertySituation = housProperty.propertySituation == undefined ? '' : housProperty.propertySituation;
          //housProperty.propertyProperties = housProperty.propertyProperties == undefined ? '' : housProperty.propertyProperties;
          //housProperty.planningPurposes = housProperty.planningPurposes == undefined ? '' : housProperty.planningPurposes;
          housProperty.whetherOneContact = String(housProperty.whetherOneContact);
          //housProperty.keyDiskQuery = housProperty.keyDiskQuery == undefined ? '' : housProperty.keyDiskQuery;
          housProperty.keyDiskQuery = String(housProperty.keyDiskQuery);
          //marryStatus = marryStatus == undefined ? "" : marryStatus;
          plBorrow.repaymentType = String(plBorrow.repaymentType);
          //plBorrow.projectBelongs = plBorrow.projectBelongs == undefined ? '' : plBorrow.projectBelongs;
          //plBorrow.projectBelongs = String(plBorrow.projectBelongs);
          plBorrow.productId = String(plBorrow.productId);
          this.refs.ReviewWin.refs.HousingInformation.setFieldsValue(housProperty);
          this.refs.ReviewWin.refs.BorrowingNeeds.setFieldsValue(plBorrow);
          this.setState({
            id: id,
            canEdit: false
          });
        }
      });
    }
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
    });
  },
  rowKey(record) {
    return record.code;
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
    var url = '/modules/common/action/plConsultAction/getPlconsultList.htm';
    Utils.ajaxData({
      url: url,
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
    var id = record.code;
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
      title: '项目编号',
      dataIndex: 'code'
    }, {
        title: '项目名称',
        dataIndex: 'projectName'
      }, {
        title: '客户姓名',
        dataIndex: 'name',
      }, {
        title: '业务员',
        dataIndex: 'salesman',
      }, {
        title: '申请时间',
        dataIndex: 'createTime',
      }, {
        title: '流程状态',
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
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" key="1" disabled={!hasSelected} onClick={this.showModal.bind(this, '查看贷款信息', state.record, false) }>
            查看贷款信息
          </button>
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <ReviewWin ref="ReviewWin"  visible={state.visible}  title={state.title} hideModal={me.hideModal} record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
      </div>
    );
  }
})
