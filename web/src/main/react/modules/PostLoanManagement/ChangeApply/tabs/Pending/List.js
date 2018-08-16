
import React from 'react'
import {
  Table,
  Modal,
} from 'antd';
import StageWin from '../Components/StageWin'
import AdvanceWin from '../Components/AdvanceWin'
import DisposalWin from '../Components/DisposalWin'
import BreaksWin from '../Components/BreaksWin'
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
      visible1: false,
      visible2: false,
      visible3: false,
      visible4: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    this.clearSelectedList();
    this.fetch(nextProps.params);
  },
  //隐藏弹窗
  hideModal() {
    this.setState({
      visible1: false,
      visible2: false,
      visible3: false,
      visible4: false,
    });
    this.refreshList();
  },
  //展期申请查看弹窗
  showModal1(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;
    this.setState({
      canEdit: canEdit,
      visible1: true,
      title: title,
    }, () => {
      if (title == '展期申请') {
        //获取列中中的信息
        var refs = this.refs.StageWin.refs;
        refs.Stage.setFieldsValue(selectedrecord);
        Utils.ajaxData({
          url: '/modules/audit/HousBillsAction/getLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
            isBranch: false,
          },
          callback: (result) => {
            var stage = result.data;
            var refs = this.refs.StageWin.refs;
            refs.Stage.setFieldsValue(stage);

          }
        });
      }
    });

  },
  //提前还款查看弹窗
  showModal2(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;
    this.setState({
      canEdit: canEdit,
      visible2: true,
      title: title,
    }, () => {
      if (title == '提前还款申请') {
        var refs = this.refs.AdvanceWin.refs;
        refs.Advance.setFieldsValue(selectedrecord);
        var borrowAccount = selectedrecord.borrowAccount
        Utils.ajaxData({
          url: '/modules/LoanChange/getAheadOfReturnLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
            isBranch: false,
          },
          callback: (result) => {
            var advance = result.data;
            var refs = this.refs.AdvanceWin.refs;
            advance.aheadRepayRate = advance.aheadRepayRate * 1000000 / 10000;
            refs.Advance.setFieldsValue(advance);
            refs.Advance.setFieldsValue({ realPrepaymentPenalty: advance.penaltySum });
            this.setState({
              advance: borrowAccount
            });
          }
        });
      }
    });

  },
  //任务分配查看弹窗
  showModal3(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;

    this.setState({
      canEdit: canEdit,
      visible3: true,
      title: title,
    }, () => {
      if (title == '房屋处置申请') {
        Utils.ajaxData({
          url: '/modules/audit/HousBillsAction/getLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
          },
          callback: (result) => {

          }
        });
      }
    });

  },
  //任务分配查看弹窗
  showModal4(title, canEdit) {
    var selectedrecord = this.state.selectedrecord;
    var me = this;

    this.setState({
      canEdit: canEdit,
      visible4: true,
      title: title,
    }, () => {
      if (title == '罚息减免申请') {
        Utils.ajaxData({
          url: '/modules/audit/HousBillsAction/getLoanInfo.htm',
          data: {
            processInstanceId: selectedrecord.processInstanceId,
          },
          callback: (result) => {
            // var loanInfo = result.data.loanInfo;//获取后台传过来的数据
            // var loans = result.data.loans;
            // var housLoanfees = result.data.housLoanfees;
            // var loanFormInfo = result.data.loanFormInfo;

            // var refs = this.refs.AddlWin.refs;
            // //loanInfo.bankFlag = String(loanInfo.bankFlag);// bankFlag 下拉数值转化        
            // refs.LoanInfo.setFieldsValue(loanInfo);//对应的from表单赋值
            // refs.HousLoanfees.setFieldsValue(housLoanfees);
            // refs.LoanFormInfo.setFieldsValue(loanFormInfo);
            // loans && refs.CardList.setFieldsValue(this.transFormCardData(loans));//数组对应赋值

          }
        });
      }
    });

  },
  transFormCardData(data) {
    var formData = {};
    var keys = [];
    data.forEach(item => {
      let k = item.id;//id值 需要更改
      keys.push(k);
      formData[k + 'accountHolderName'] = item.accountHolderName;
      formData[k + 'cardid'] = item.cardid;
      formData[k + 'account'] = item.account;
      formData[k + 'bankFlag'] = String(item.bankFlag);
      formData[k + 'id'] = item.id;
    })
    formData["keys"] = keys;
    return formData;
  },
  rowKey(record) {
    return record.processInstanceId;

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
      url: '/modules/LoanChange/pendingApplications.htm',
      data: params,
      callback: (result) => {
        const pagination = this.state.pagination;
        pagination.current = params.pageNum;
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
    var id = record.processInstanceId;
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
    var roleId = window.roleId;
    const rowSelection = {
      // type: 'checkbox',
      selectedRowKeys,
      onSelectAll: this.onSelectAll,
    };
    //根据还款类型的不同显示不同的数据
    var disabled1 = true, disabled2 = true;
    if (this.state.selectedrecord && this.state.selectedrecord.repaymentStatusText == "还款中") {
      disabled1 = false;
    }

    if (this.state.selectedrecord && this.state.selectedrecord.repaymentStatusText == "逾期") {
      disabled2 = false;
    }

    //结清 不显示数据
    if (this.state.selectedrecord && this.state.selectedrecord.repaymentStatusText == "结清") {
      disabled2 = false;
      disabled1 = false;
    }

    const hasSelected = selectedRowKeys.length > 0;
    var columns = [{
      title: '项目编号',
      dataIndex: 'projectCode'
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
        title: '借款期限(月)',
        dataIndex: "timeLimit"
      }, {
        title: '到期日期',
        dataIndex: "endRepayTime"
      }, {
        title: '目前期数',
        dataIndex: "period"
      }, {
        title: '还款状态',
        dataIndex: "repaymentStatusText"
      }, {
        title: '展期次数',
        dataIndex: "extensionTime"
      },
    ];


    var stageWin = (
      <button className="ant-btn" disabled={disabled1} onClick={this.showModal1.bind(this, '展期申请', true) }>
        展期申请
      </button>
    );
    var advanceWin = (
      <button className="ant-btn"  key="1" disabled={disabled1}  onClick={this.showModal2.bind(this, '提前还款申请', true) }>
        提前还款申请
      </button>
    );
    // var disposalWin = (
    //   <button className="ant-btn"  key="2" disabled={disabled2} onClick={this.showModal3.bind(this, '房屋处置申请', true) }>
    //     房屋处置申请
    //   </button>
    // );
    // var breaksWin = (
    //   <button className="ant-btn" disabled={disabled2} onClick={this.showModal4.bind(this, '罚息减免申请', true) }>
    //     罚息减免申请
    //   </button>
    // );
    //催收专员
    // if (roleId == "moneyUrgeStaff") {
    //   breaksWin = "";
    // }
    //贷后专员
    // if (roleId == "afterLoanStaff") {
    //   stageWin = "";
    //   advanceWin = "";
    //   // disposalWin = "";
    // }
    //商务专员
    if (roleId == "customerServiceStaffB") {
      advanceWin = "";
      //disposalWin = "";
      // breaksWin = "";
    }
    var state = this.state, buttons;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          {stageWin}
          { advanceWin }
          {/* { disposalWin }*/}
          {/*{breaksWin}*/ }
        </div>
        <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          rowSelection={rowSelection}
          onRowClick={this.onRowClick}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}  />
        <StageWin ref="StageWin"  visible={state.visible1}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
        <AdvanceWin ref="AdvanceWin"  visible={state.visible2} advance={this.state.advance}   title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
        <DisposalWin ref="DisposalWin"  visible={state.visible3}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
        <BreaksWin ref="BreaksWin"  visible={state.visible4}    title={state.title} hideModal={me.hideModal} record={state.selectedrecord}
          canEdit={state.canEdit}/>
      </div>
    );
  }
})
