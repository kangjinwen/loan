/*我的任务*/
import React from 'react';
import MyTaskList from './MyTaskList';
export default React.createClass({

    getInitialState() {
        return {
            params: {
                pageSize: 5,
                currentPage: 1
            }
        }
    },
    passParams(params) {
        this.setState({
            params: params
        });
    },
    render() {
        return <div>
            <MyTaskList params={this.state.params}/>
        </div>
    }
});

