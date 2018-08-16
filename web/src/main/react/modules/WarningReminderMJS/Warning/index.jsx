/* 预警与提醒*/
import React from 'react';
import {
  Tabs,
} from 'antd';
const TabPane = Tabs.TabPane;
import Pending from './tabs/Pending/index';
import List from './tabs/Pending/List';
import SeachForm from './tabs/Pending/SeachForm';
const Index = React.createClass({
  getInitialState() {
    return {
      activekey: "1",
      params: {}
    }
  },
  handleTabClick(key) {
    this.setState({
      activekey: key,
    })
  },
  passParams(params) {
    this.setState({
      params: params
    });
  },
  render() {
    var roleId = window.roleId;
    return (
      <div>
        <div className="block-panel">
          <SeachForm passParams={this.passParams}/>
        </div>
        <List params={this.state.params}/>
      </div>
    )
  }

});

export default Index;
