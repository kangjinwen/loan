//主表
import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import RepayListUnder from './RepayListUnder';
import RepaymentProcessWin from './RepaymentProcessWin';
import BasiInformationWin from './BasiInformationWin';
import HousingDisposalWin from './HousingDisposalWin';
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
        pageNum: 1
      },
      canEdit: true,
      visible: false,
      visible2: false,
      visibleBasic: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.fetch(nextProps.params);
  },
  hideModal() {
    this.setState({
      visible: false,
      visible2: false,
      visibleBasic: false,
    });
    this.refreshList();
    this.refs.RepayListUnder.refreshList();
  },
  //新增跟编辑弹窗
  showModal(title, record, canEdit) {
    if (title == '还款处理') {
      this.refs.RepaymentProcessWin.setFieldsValue(record);
      Utils.ajaxData({
        url: '/modules/repayment/RepaymentAction/queryNormalRepayment.htm',
        data: {
          processInstanceId: record.processInstanceId,
          type: 1
        },
        callback: (result) => {
          this.refs.RepaymentProcessWin.setFieldsValue(result.data);
          this.refs.RepaymentProcessWin.setFieldsValue({ realAccount: result.data.totalAccount });
        }
      });
    } else if (title == '提前还款') {
      this.refs.RepaymentProcessWin.setFieldsValue(record);
      Utils.ajaxData({
        url: '/modules/repayment/RepaymentAction/queryNormalRepayment.htm',
        data: {
          processInstanceId: record.processInstanceId,
          type: 2
        },
        callback: (result) => {
          this.refs.RepaymentProcessWin.setFieldsValue(result.data);
        }
      });
    } else if (title == '新增') {
      record = null
    }
    this.setState({
      canEdit: canEdit,
      visible: true,
      title: title,
      record: record
    });
  },
  showModal2(title, record, canEdit) {//房屋处置
    var record = record
    this.setState({
      canEdit: canEdit,
      visible2: true,
      title: title,
      record: record
    });
  },
  transformeData(data) {
    var formData = {};
    var keys = [];
    data.forEach(item => {
      let k = item.assessmentAgencies;
      if (keys.indexOf(k) < 0) {
        keys.push(k);
      }
      else return
      formData[k + 'assessedValue'] = item.assessedValue;
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
  },
  //查看时转换查询机构数据
  transformQueryData(data) {
    var ids = [];
    var formData = {};
    data.forEach(item => {
      var obj = {};
      var id = item.id;
      let k = item.institutionType;
      if (ids.indexOf(k) < 0) {
        ids.push(item.institutionType);
      }
      else return
      //是建委
      if (item.institutionType == 1) {
        formData[k + 'id'] = item.id;
        formData[k + "remark"] = item.remark;
        formData[k + "mortgage"] = item.mortgage;
        formData[k + "seizure"] = Boolean(item.seizure);
        formData[k + "businessOccupancy"] = Boolean(item.businessOccupancy);
        formData[k + "netSignedOccupancy"] = Boolean(item.netSignedOccupancy);
        formData[k + "hochstbetragshypothek"] = Boolean(item.hochstbetragshypothek);
      }
      //是安融
      else if (item.institutionType == 4) {
        formData[k + 'id'] = item.id;
        formData[k + "remark"] = item.remark;
        formData[k + "legalProcessPerformed"] = Boolean(item.legalProcessPerformed);
        formData[k + "affiliate"] = Boolean(item.affiliate);
      }
      else {
        formData[k + 'id'] = item.id;
        formData[k + "remark"] = item.remark;
        formData[k + "legalProcessPerformed"] = Boolean(item.legalProcessPerformed);
      }
    });
    formData["keys"] = ids;
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
  showBasiInformation(title, record, canEdit) {//查看基本信息
    var selectedrecord = this.state.selectedrecord
    var record = record;
    var me = this;
    var formData = {};
    var formData2 = {};
    if (title == "信息查看") {
      Utils.ajaxData({
        url: '/modules/common/action/plConsultAction/getConsultById.htm',
        data: {
          processInstanceId: record.processInstanceId,
        },
        callback: (result) => {
          var hous = result.data.housPropertyInformation;
          var plBorrow = result.data.plBorrowRequirement;
          var id = hous.id;
          hous.propertyProperties = hous.propertyProperties == undefined ? '' : hous.propertyProperties;
          hous.planningPurposes = hous.planningPurposes == undefined ? '' : hous.planningPurposes;
          hous.whetherOneContact = String(hous.whetherOneContact);
          hous.whetherTwoContact = String(hous.whetherTwoContact);
          hous.propertySituation = hous.propertySituation == undefined ? '' : hous.propertySituation;
          hous.keyDiskQuery = hous.keyDiskQuery == undefined ? '' : hous.keyDiskQuery;
          hous.keyDiskQuery = String(hous.keyDiskQuery);
          plBorrow.repaymentType = String(plBorrow.repaymentType);
          plBorrow.singleRate = plBorrow.singleRate * 1000000 / 10000;
          plBorrow.repaymentRate = plBorrow.repaymentRate * 1000000 / 10000;
          //plBorrow.projectBelongs = String(plBorrow.projectBelongs);
          plBorrow.productId = String(plBorrow.productId);
          this.refs.BasiInformationWin.refs.HousingInformation.setFieldsValue(hous);
          this.refs.BasiInformationWin.refs.BorrowingNeeds.setFieldsValue(plBorrow);
          this.setState({
            id: id,
          });
        }
      });
    }
    this.setState({
      visibleBasic: true,
      title: title,
      canEdit: false,
      formData: formData2
    });
  },
  rowKey(record) {
    return record.processInstanceId;
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
  componentDidMount() {
    this.fetch(this.props.params);
  },
  fetch(params = { pageSize: 5, pageNum: 1 }) {
    this.setState({
      loading: true
    });
    Utils.ajaxData({
      url: '/modules/repayment/RepaymentAction/getRepaymentPage.htm',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.pageNum;
        pagination.total = result.totalCount;
        if (!pagination.current) {
          pagination.current = 1
        };
        for (var i = 0, length = result.data.length; i < length; i++) {
          result.data[i].index = i
        }
        this.setState({
          loading: false,
          data: result.data,
          pagination,
        });
        this.clearList();
        var dragWrap = document.getElementsByClassName('ant-table-body')[0];
        dragAll.dragItem(dragWrap);
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
      pageNum: pagination.current,
      pageSize: pagination.pageSize
    });
    this.fetch(params);
    this.clearList();
  },
  showInformationScreen(title, record) {
    var me = this;
    confirm({
      title: '是否确认要进行信息筛查',
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
  onRowClick(record) {
    this.setState({
      record: record,
      processInstanceId: record.processInstanceId,
      repaymentProcess: record.repaymentProcess,
      selectedRowKeys: [record.processInstanceId]
    });
  },
  CancelOverdue(title) {
    var me = this;
    var id = me.state.selectedRowKeys[0];
    confirm({
      title: '取消逾期将不计算逾期罚息，确定取消该笔贷款逾期？',
      onOk: function () {
        Utils.ajaxData({
          url: "/modules/system/userEditPage.html",
          data: {
            id: id,
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
  dragClick(e) { 
  },
  render() {
    var roleId = window.roleId;
    var me = this;
    const {
      loading,
      record,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      //type: 'radio',
      selectedRowKeys,
      //onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    const disabled1 = selectedRowKeys.length > 0 && record && record.repaymentProcess == 0 && record.repaymentStatusText != "结清" && record.islocked == 0;//还款处理
    const disabled2 = selectedRowKeys.length > 0 && record && record.repaymentProcess == 1 && record.repaymentStatusText != "结清" && record.islocked == 0;//提前还款
    const disabled3 = selectedRowKeys.length > 0 && record && record.repaymentStatus == 2 && record.repaymentStatusText != "结清" && record.islocked == 0;//逾期 0是不锁定
    const disabledScreen = selectedRowKeys.length > 0 && record.repaymentStatusText != "结清";//信息筛查 
    var columns = [{
      title: '项目编号',
      dataIndex: 'projectCode',
      // width: 120,
      //fixed: 'left',
      render(value) {
        return Utils.longWords(120,value)
      }
    }, {
        title: '放款日期',
        // width: 100,
        dataIndex: 'loanTime'
      }, {
        title: '客户姓名',
        // width: 100,
        dataIndex: "customerName",
        render(value) {
          return Utils.longWords(100,value)
        }
      },{
        title: '借款期数(月)',
        // width: 100,
        dataIndex: "timeLimit"
      }, {
        title: '贷款金额(元)',
        // width: 130,
        dataIndex: "borrowAccount",
        render(value) {
          return Utils.toThousands(value)
        }
      },
      // {
      //   title: '首期利息(元)',
      //   dataIndex: "firstInterest"
      // },
      {
        title: '已还利息(元)',
        // width: 130,
        dataIndex: "repayInterest",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '已还本金(元)',
        // width: 130,
        dataIndex: "repayCapital",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '剩余本金(元)',
        // width: 130,
        dataIndex: "needpayCapital",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '下户费(元)',
        // width: 130,
        dataIndex: "actualFee",
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '还款状态',
        // width: 100,
        dataIndex: "repaymentStatusText"
      }, {
        title: '还款处理',
        // width: 100,
        dataIndex: "repaymentProcessText"
      }];
    var state = this.state;
    var buttons = (
      <div>
        <button className="ant-btn" disabled={!disabled1} onClick={this.showModal.bind(this, '还款处理', state.record, true) }>
          还款处理
        </button>
        <button className="ant-btn" disabled={!hasSelected} onClick={this.showBasiInformation.bind(this, '信息查看', state.record, false) }>
          信息查看
        </button>
      </div>
    );
    if (roleId == "afterLoanStaff") {//贷后专员
      buttons = (
        <button className="ant-btn" disabled={!disabled3} onClick={this.showModal2.bind(this, '房屋处置', state.record, true) }>
          房屋处置
        </button>
      );
    }
    return (
      <div className="block-panel longTableWarp">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          {buttons}
        </div>
        <Table className="dragWrap" columns={columns}  rowKey={this.rowKey}  size="middle"  params ={this.props.params} rowSelection={rowSelection}
          dataSource={this.state.data} onRowClick={this.onRowClick}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <RepayListUnder parentId={selectedRowKeys[0]} repaymentProcess={this.state.repaymentProcess} record2={state.record} ref="RepayListUnder"/>
        <RepaymentProcessWin ref="RepaymentProcessWin"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.record}
          canEdit={state.canEdit}/>
        <HousingDisposalWin ref="HousingDisposalWin"  visible={state.visible2}    title={state.title} hideModal={me.hideModal} record={state.record}
          canEdit={state.canEdit}/>
        <BasiInformationWin ref="BasiInformationWin"  visible={state.visibleBasic} formData={state.formData} id={state.id}
          title={state.title} hideModal={me.hideModal} record={state.record} canEdit={state.canEdit}/>
      </div>
    );
  }
})
