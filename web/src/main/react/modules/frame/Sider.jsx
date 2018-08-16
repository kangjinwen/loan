import React from 'react';
import { Menu,Modal,Tooltip2 } from 'antd';
var SubMenu = Menu.SubMenu;
var Reflux = require('reflux');
var AppActions = require('./actions/AppActions');
var MenuDataStore = require('./stores/MenuDataStore');
var ActiveTabStore = require('./stores/ActiveTabStore');
const Sider = React.createClass({
  mixins: [
    Reflux.listenTo(ActiveTabStore, 'onMatch'),
    Reflux.connect(MenuDataStore, "menuData")
  ],
  onMatch(activeId) {
        this.setState({
            current: activeId,
        });
  },
  getInitialState() {
    return {
      current: "workbench",
      menuData: {},
      // openKeys:["WarningReminderMJS","MyTask","CustomerManageMJS", "FirstTrialManageMJS", "CollateralManageMJS", "RecheckMJS", "FinalMJS", "FinancialManagement", "PostLoanManagement", "LoanApplicationMJS", "CustRelationshipManageMJS", "PerformanceManageMJS", "sysManage"],
      openKeys:['0'],
    }
  },
  componentDidMount() {
    AppActions.initStore();
    //AppActions.setTabActiveKey("放款单据填写","DocumentsFill");
  },
  getMenuByData(){
    var me = this;
    var SubMenuNodes = [];
    if (this.state.menuData.length) {

      SubMenuNodes = this.state.menuData.map((item, key)=> {
        if(item.children){
           let itemNoades = item.children&&item.children.map((child, i)=> {
            if (!child.children) {
              return ( <Menu.Item key={child.scriptid} tabName={child.label} tabId={child.scriptid}>{child.label}</Menu.Item>)
            }
            else {
              var childChild = child.children;
              let childrenNodes = childChild.map((son, j)=> {
                return ( <Menu.Item key={son.scriptid} tabName={son.label} tabId={son.scriptid}>{son.label}</Menu.Item>)
              });
              return (<SubMenu key={child.scriptid}
                              title={<span><i className={`iconfont ${child.iconCls}`}></i><span className="menu-text" >{child.label}</span></span>}>
                { childrenNodes }
              </SubMenu> );
            }
          });
          return (
            <SubMenu key={item.scriptid}
                    title={<span><i className={`iconfont ${item.iconCls}`}></i><span className="menu-text">{item.label}</span></span>}>
              { itemNoades }
            </SubMenu> );
        }
       else {
         return (
            <Menu.Item key={item.scriptid} tabName={item.label} tabId={item.scriptid}>
                   <i className={`iconfont ${item.iconCls}`}></i><span className="menu-text">{item.label}</span>
            </Menu.Item> );
       }
      });

    }
    return SubMenuNodes ;
  },
  handleClick(e){
    this.setState({
      current: e.key
    });
    AppActions.setTabActiveKey(e.item.props.tabName,e.item.props.tabId);
    // //返回页面顶部
    window.scrollTo(0, 0);
  },
  handleClickWhenFold(){
    if (this.props.fold) {
      this.toggleMenu();
    }
  },
  toggleMenu(){
    var me = this;
    this.setState({
      openKeys: null
    });
    this.props.toggleMenu();
  },
  render() {
    var SubMenuNodes= this.getMenuByData();

    var fold = this.props.fold;
    return (
      <div>
        <div onClick={this.handleClickWhenFold}>
          <Menu onClick={this.handleClick}
                style={{width:'100%'}}
                defaultOpenKeys={this.state.openKeys}
                selectedKeys={[this.state.current]}
                mode="inline">
            {SubMenuNodes}
          </Menu>
        </div>
      </div>
    )
  }
});

export default Sider;
