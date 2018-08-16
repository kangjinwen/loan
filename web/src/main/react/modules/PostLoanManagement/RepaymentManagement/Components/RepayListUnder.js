import React from 'react'
import {
  Table,
  Modal
} from 'antd';
import RepaymentProcessWin from './RepaymentProcessWin'
import HousingDisposalWin from './HousingDisposalWin';
var confirm = Modal.confirm;
const objectAssign = require('object-assign');
var pager = {
  pageSize: 5,
  pageNum: 1
};
export default React.createClass({
  getInitialState() {
    return {
      selectedRowKeys: [], // 这里配置默认勾选列
      loading: false,
      data: [],
      pagination: {},
      canEdit: true,
      visible2: false,
      visible: false,
    };
  },
  componentWillReceiveProps(nextProps, nextState) {
    if (this.props.parentId == nextProps.parentId) {
      return
    }
    var parentId = nextProps.parentId;
    var params;
    if (parentId) {
      params = objectAssign({},
        { processInstanceId: parentId }
        , pager)
      this.fetch(params, nextProps.record2);
    }
    else {
      params = pager;
      this.fetch(params);
    }
    // if (this.props.parentId != nextProps.parentId) {
    //   this.fetch(params, nextProps.record2);
    // }

  },
  hideModal() {
    this.setState({
      visible: false,
      visible2: false,
      // loading: true
    });
    var params = objectAssign({}, {
      processInstanceId: this.props.parentId
    },
      pager
    );
    this.fetch(params);
  },
  //新增跟编辑弹窗
  showModal(title, record, canEdit) {
    var record2 = this.props.record2;
    record = objectAssign(record, record2);
    if (record.repaymentStaus == 5) {//显示房屋处置弹框
      this.showModal2();
    } else {//显示还款记录弹框
      this.showModalloan(title, record, canEdit);
    }
  },
  showModal2(title, record, canEdit) {
    this.refs.RepaymentProcessWin.setFieldsValue(record);
    Utils.ajaxData({
      url: '/modules/repayment/RepaymentAction/queryNormalRepayment.htm',
      data: {
        processInstanceId: record.processInstanceId,
        type: 5
      },
      callback: (result) => {
        this.refs.RepaymentProcessWin.setFieldsValue(result.data);
      }
    });
    this.setState({
      canEdit: false,
      visible2: true,
      title: "房屋处置",
      record: record
    });
  },
  showModalloan(title, record, canEdit) {
    this.refs.RepaymentProcessWin.setFieldsValue(record);
    if (record.repaymentStaus == 4) {//repaymentStaus=1正常还款 2 3逾期 4提前还款 5押品处置 
      this.refs.RepaymentProcessWin.setFieldsValue({
        account: record.borrowAccount,//本期应还金额=借款金额
        totalAccount: record.borrowAccount + record.penalty//本期应还总额=借款金额+提前还款违约金
      });
    }
    this.setState({
      canEdit: !canEdit,
      visible: true,
      title: title,
      record: record
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
    this.fetch({
      pageSize: pagination.pageSize,
      pageNum: pagination.current
    }, this.props.record2);
  },
  fetch(params, record2) {
    this.setState({
      loading: true
    });
    if (params == null) {
      params = pager;
    }
    if (record2) {
      params = objectAssign({}, params, { processInstanceId: record2.processInstanceId });
    }

    if (params.processInstanceId) {
      Utils.ajaxData({
        url: '/modules/repayment/RepaymentAction/getRepaymentDetailPage.htm',
        data: params,
        callback: (result) => {
          const pagination = this.state.pagination;
          pagination.current = params.pageNum;
          pagination.pageSize = pager.pageSize;
          pagination.total = result.totalCount;
          if (!pagination.current) {
            pagination.current = 1
          };
          this.setState({
            loading: false,
            data: result.data,
            pagination,
          });
          this.clearList();
          var dragWrap = document.getElementsByClassName('ant-table-body')[1];
          dragAll.dragItem(dragWrap);
        }
      });
    } else {
      this.setState({
        loading: false,
        data: []
      });
    }
  },
  clearList() {
    this.setState({
      selectedRowKeys: [],
    });
  },
  refreshList() {
    var params = objectAssign({}, {
      processInstanceId: this.props.parentId
    },
      pager
    );
    this.fetch(params);
  },
  delete(record) {
    var me = this;
    confirm({
      title: '是否确认要删除这项内容',
      onOk: function () {
        Utils.ajaxData({
          url: "/modules/system/deleteDictDetail.htm",
          data: {
            id: record.id,
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
      selectedRowKeys: [record.id]
    });
  },
  render() {
    var me = this;
    const {
      loading,
      record,
      selectedRowKeys
    } = this.state;
    const rowSelection = {
      // type: 'radio',
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    const disabled1 = record && record.repaymentStatus != "结清";

    var columns = [{
      title: '期次',
      //width: 100,
      dataIndex: 'period',
      //fixed: 'left',
    }, {
        title: '还款日期',
        //width: 100,
        dataIndex: 'repaymentTime',
        render: function (value) {
          return value.substr(0, 10)
        }
      }, {
        title: '本期应还金额(元)',
        //width: 150,
        dataIndex: 'account',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '已还本期利息(元)',
        //width: 150,
        dataIndex: 'repaymentInterestAmount',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '已还本期本金(元)',
        //width: 150,
        dataIndex: 'repaymentCapitalAmount',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '实还金额(元)',
        //width: 150,
        dataIndex: 'realAccount',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '剩余待还本金(元)',
       // width: 150,
        dataIndex: 'needrepayCapital',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '罚息金额(元)',
       // width: 130,
        dataIndex: 'defaultInterest',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '实际罚息金额(元)',
        //width: 130,
        dataIndex: 'realInterest',
        render(value) {
          return Utils.toThousands(value)
        }
      }, {
        title: '实际还款日期',
        //width: 130,
        dataIndex: 'realpaymentTime',

      }, {
        title: '还款状态',
        //width: 100,
        dataIndex: 'repaymentStausText'
      }, {
        title: '是否还清',
        //width: 108,
        dataIndex: 'isPayoffText'
      }, {
        title: '操作',
        //width: 100,
        //fixed: 'right',
        dataIndex: "",
        render(text, record) {
          if (record.isPayoff == 1) {
            return <div style={{ textAlign: "left" }}><a href="#" onClick={me.showModal.bind(me, '还款记录', record, true) }>还款记录 </a></div>
          } else {
            return <div style={{ textAlign: "left" }}><a href="#" disabled={true}  onClick={me.showModal.bind(me, '还款记录', record, true) }>还款记录 </a></div>
          }

        }
      }];
    var state = this.state;
    return (
      <div className="block-panel">
        <div className="actionBtns" style={{ marginBottom: 16 }}>
          {/* <button className="ant-btn" disabled={!hasSelected} onClick={this.showModal.bind(this, '罚息减免', state.record, true) }>
            罚息减免
          </button>*/}
        </div>
        <Table columns={columns}  rowKey={this.rowKey}  size="middle"  params ={this.props.params}
          dataSource={this.state.data}
          rowSelection={rowSelection}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}
          onRowClick={this.onRowClick} />
        <RepaymentProcessWin ref="RepaymentProcessWin" refreshList={me.refreshList} visible={state.visible}
          recordUnder={state.record}  title={state.title} hideModal={me.hideModal} record2={this.props.record2}
          canEdit={state.canEdit}/>
        <HousingDisposalWin ref="HousingDisposalWin" refreshList={me.refreshList} visible={state.visible2}
          recordUnder={state.record}  title={state.title} hideModal={me.hideModal} record2={this.props.record2}
          canEdit={state.canEdit}/>
      </div>
    );
  }
})