
import React from 'react'
import {
  Table,
  Modal,
  Select,
} from 'antd';
import ReviewWin from '../ReviewWin'
import RevieCustumerwWin from '../RevieCustumerwWin'
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
  transformeData(data) {
    var formData = {};
    var keys = [];
    data.forEach(item => {
      let k = item.assessmentAgencies;
      keys.push(k);
      formData[k + 'assessedValue'] = item.assessedValue;
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
  },
  transFormPersonData(data) {//人员类型
    var formData = {};
    var keys = [];
    data.forEach(item => {
      let k = item.id;//id值 需要更改
      keys.push(k);
      formData[k + 'type'] = item.type;
      formData[k + 'personName'] = item.personName;
      formData[k + 'personNumber'] = item.personNumber;
      formData[k + 'personPhone'] = item.personPhone;
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
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
          var id = result.data.housPropertyInformation.id;
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
    if (title == "新增申请") {
      this.setState({
      });
    }
    this.setState({
      visible: true,
      title: title,
      canEdit: canEdit,
    });
  },
  showModalCust(title, record, canEdit) {//新增客户
	  var selectedrecord = this.state.selectedrecord
	    var me = this;
	    if (title == "查看客户信息") {//报单专员
	      Utils.ajaxData({
	    	url: '/modules/common/PubCustomerAction/getPubCustomerByProcessInstanceId.htm',
	        data: {
	        	processInstanceId: selectedrecord.processInstanceId,
	            customerId: selectedrecord.id
	        },
	        callback: (result) => {//marryStatus
	        	this.refs.RevieCustumerwWin.refs.BasiInformationMJS.setFieldsValue(result.data);
	        }
	      });
	    }
    this.setState({
      visibleCust: true,
      title: title,
      canEdit: canEdit,
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
    var url = '/modules/common/PubCustomerAction/getPubCustomerList.htm';
    Utils.ajaxData({
      url: url,
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
    this.clearSelectedList();
  },
  componentDidMount() {
    this.fetch();
  },
  onRowClick(record) {
    var id = record.id;
    this.setState({
      selectedRowKeys: [id],
      selectedrecord: record
    });
  },
  showInformationScreen(title, record) {
    var me = this;
    confirm({
      title: '是否确认要进行申请进件',
      onOk: function () {
        Utils.ajaxData({
          url: "/modules/repayment/RepaymentAction/supplyInvestigat.htm",
          data: {
            processInstanceId: record.processInstanceId,
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
      title: '客户ID',
      dataIndex: 'id'
    }, {
        title: '客户名称',
        dataIndex: 'name'
      }, {
        title: '证件号码',
        dataIndex: 'certNumber',
      }, {
        title: '联系人号码',
        dataIndex: 'mobile',
      }, {
        title: '贷款次数',
        dataIndex: 'loans',
      }, {
        title: '客户状态',
        dataIndex: 'status',
        render: function (value) {
          if (value == 0)
            return "正常用户";
          else return "黑名单用户";
        }
      }, {
        title: '业务员',
        dataIndex: 'salsman',
      },
      //  {
      //   title: '流程状态',
      //   dataIndex: "processStatusName"
      // },
    ];

    var state = this.state;
    var selectedrecord = state.selectedrecord;
    var disabled1;
    if (selectedrecord) {
      if (selectedrecord.processInstanceId) {
        disabled1 = hasSelected;
      } else {
        disabled1  = false
      }

    }
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          <button className="ant-btn" key="1" onClick={this.showModalCust.bind(this, '新增客户', true) }>
            新增客户
          </button>
          <button className="ant-btn" key="2" disabled={!hasSelected} onClick={this.showModal.bind(this, '新增申请', state.record, true) }>
            新增申请
          </button>
          <button className="ant-btn" key="4" disabled={!hasSelected} onClick={this.showModalCust.bind(this, '查看客户信息', state.record, false) }>
            查看客户信息
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
        <RevieCustumerwWin ref="RevieCustumerwWin"  visible={state.visibleCust}  title={state.title} hideModal={me.hideModal} record={state.selectedrecord} id={state.id} canEdit={state.canEdit}/>
      </div>
    );
  }
})
