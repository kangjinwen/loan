/* 机构初审
 */
import React from 'react';
import {Tabs,} from 'antd';
import Pending from './tabs/Pending/index';
import Processed from './tabs/Processed/index';

const TabPane = Tabs.TabPane;
const Index = React.createClass({
    getInitialState() {
        return {activekey: "1",}
    },
    handleTabClick(key) {
        this.setState({
            activekey: key,
        })
    },
    render() {
        return (
            <Tabs defaultActiveKey="1" destroyInactiveTabPane onTabClick={this.handleTabClick}
                  activeKey={this.state.activekey} animation={null}>
                <TabPane tab="待处理" key="1">
                    <Pending/>
                </TabPane>
                <TabPane tab="已处理" key="2">
                    <Processed/>
                </TabPane>

            </Tabs>
        )
    }

});

export default Index;
