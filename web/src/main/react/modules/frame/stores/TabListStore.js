var Reflux = require('reflux');
import React from 'react';

var AppActions = require('../actions/AppActions');
var Workbench = require('../../Public/Workbench/Index')
export default Reflux.createStore({
	listenables: [AppActions],
	tablist: [{
		'key': 'workbench',
		'tabName': '工作台',
		"tabId": 'workbench'
	}],
	activeId: 'workbench',
	onSetTabActiveKey(tabName, tabId) {
		var me = this;
		var tablist = window.tablist || this.tablist;
		var flag = false;
		var activeId = tabId;
		tablist.forEach((v) => {
			if (tabId === v.tabId) {
				flag = true; //当前activeKey 在tablist中已经存在
				return
			}
		});
		var tabContent;

		if (!flag) { // 点击左侧菜单时，没有相应标签页
			var Component = Workbench;

			switch (tabId) {
				case 'sysUserManage':
					{ //用户管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/UserMang/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'UserMang');
						break;
					}
				case 'sysOfficeManage':
					{ //组织管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/OrganizationMang/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'OrganizationMang');
						break;
					}
				case 'sysRoleManage':
					{ //角色管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/RoleMang/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'RoleMang');
						break;
					}
				case 'sysMenuManage':
					{ //菜单管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/MenuMang/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'MenuMang');
						break;
					}
				case 'sysFelManage':
					{ //计算公式配置

						require.ensure([], function (require) {
							Component = require('../../SystemMng/FelManag/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'FelManag');
						break;
					}
				case 'sysDicManage':
					{ //字典管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/DictionaryMang/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'DictionaryMang');
						break;
					}
				case 'sysProductParametersManage':
					{ //产品参数管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/PlProductManage/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'ProductParametersManag');
						break;
					}
				case 'sysProductTypeManage':
					{ //产品类型管理

						require.ensure([], function (require) {
							Component = require('../../SystemMng/ProductTypeManag/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'ProductTypeManag');
						break;
					}

				case 'MyTaskPending':
					{ //我的任务

						require.ensure([], function (require) {
							Component = require('../../MyTask/MyTaskPending/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'MyTaskPending');
						break;
					}
				case 'CustMang':
					{ //客户管理

						require.ensure([], function (require) {
							Component = require('../../CustomerManageMJS/CustMang/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'CustMang');
						break;
					}
                case 'InspectMJS':
                { //下户

                    require.ensure([], function (require) {
                        Component = require('../../CustomerManageMJS/InspectMJS/index');
                        tabContent = <Component />;
                        me.updataTablist(tabId, tabName, tabContent, tablist);
                    }, 'InspectMJS');
                    break;
                }
				case 'FirstTrialMJS':
					{ //风控初审

						require.ensure([], function (require) {
							Component = require('../../FirstTrialManageMJS/FirstTrialMJS/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'FirstTrialMJS');
						break;
					}
				case 'ControlReview':
					{ //风控复审

						require.ensure([], function (require) {
							Component = require('../../FirstTrialManageMJS/ControlReview/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'ControlReview');
						break;
					}
				case 'OrganizationTrial':
					{ //机构初审

						require.ensure([], function (require) {
							Component = require('../../FirstTrialManageMJS/OrganizationTrial/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'OrganizationTrial');
						break;
					}
				case 'MortgageDealt':
					{ //抵押办理

						require.ensure([], function (require) {
							Component = require('../../CollateralManageMJS/MortgageDealt/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'MortgageDealt');
						break;
					}
				case 'FirstTrialLoan':
					{ //放款初审

						require.ensure([], function (require) {
							Component = require('../../RecheckMJS/FirstTrialLoan/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'FirstTrialLoan');
						break;
					}
				case 'RiskControlReview':
					{ //放款复审

						require.ensure([], function (require) {
							Component = require('../../RecheckMJS/RiskControlReview/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'RiskControlReview');
						break;
					}
				case 'InstitutionalReview':
					{ //机构放款

						require.ensure([], function (require) {
							Component = require('../../FinalMJS/InstitutionalReview/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'InstitutionalReview');
						break;
					}
				case 'CollectionManageMJS':
					{ //催收管理

						require.ensure([], function (require) {
							Component = require('../../PostLoanManagement/CollectionManageMJS/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'CollectionManageMJS');
						break;
					}
				case 'LoanApplicationRecord':
					{ //贷款申请记录

						require.ensure([], function (require) {
							Component = require('../../LoanApplicationMJS/LoanApplicationRecord/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'LoanApplicationRecord');
						break;
					}
				case 'CustRelationship':
					{ //客户关系管理

						require.ensure([], function (require) {
							Component = require('../../CustRelationshipManageMJS/CustRelationship/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'CustRelationship');
						break;
					}
				case 'Warning':
					{ //预警与提醒

						require.ensure([], function (require) {
							Component = require('../../WarningReminderMJS/Warning/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'Warning');
						break;
					}
				case 'LoanManagement':
					{ //财务管理-放款管理

						require.ensure([], function (require) {
							Component = require('../../FinancialManagement/LoanManagement/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'LoanManagement');
						break;
					}
				case 'RepaymentManagement':
					{ //贷后管理-还款管理

						require.ensure([], function (require) {
							Component = require('../../PostLoanManagement/RepaymentManagement/Index');
							tabContent = <Component />;
							me.updataTablist(tabId, tabName, tabContent, tablist);
						}, 'RepaymentManagement');
						break;
					}
			}
		} else this.update(activeId, tablist);

	},
	updataTablist(tabId, tabName, tabContent, tablist) {
		tablist = tablist.concat({
			key: tabId,
			tabName: tabName,
			tabId: tabId,
			tabContent: tabContent
		})
		this.update(tabId, tablist);
	},
	onRemoveTab(tabId) {
		var tablist = window.tablist || this.tablist;
		var foundIndex = 0;
		tablist = tablist.filter(function (t, index) {
			if (t.tabId !== tabId) {
				return true;
			} else {
				foundIndex = index;
				return false;
			}
		});
		var activeId = window.activeId || this.activeId;
		if (activeId === tabId) {
			if (foundIndex) {
				foundIndex--;
			}
			activeId = tablist[foundIndex].tabId;
		}
		AppActions.setActiveTab(activeId);
		this.update(activeId, tablist);
	},
	update(activeId, tablist) {
		window.tablist = tablist;
		window.activeId = activeId
		this.trigger({
			activeId: activeId,
			tablist: tablist
		});
	}
});
