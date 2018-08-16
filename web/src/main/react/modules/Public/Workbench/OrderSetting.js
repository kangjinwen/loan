import React from 'react';
import { Radio, Modal} from 'antd';
const RadioGroup = Radio.Group;

export default React.createClass({
    getInitialState() { 
        var isReceiveOrder = localStorage.isReceiveOrder ? localStorage.isReceiveOrder : 1;
        return {
            value: isReceiveOrder,
        };
    },
    onChange(e) {
        console.log('radio checked', e.target.value);
        Utils.ajaxData({
            url: '/modules/system/ZzjfSysUserAction/orderReceiveSet.htm'
            , method: 'post'
            , data: { isReceiveOrder: e.target.value }
            , type: 'json'
            , callback: (result) => {
                this.setState({
                    value: e.target.value,
                });
                localStorage.isReceiveOrder = e.target.value;
                if (result.code == 200) {
                    Modal.success({
                        title: result.msg

                    });
                }
                else if (result.code == 400) {
                    Modal.error({
                        title: result.msg
                    });
                }
            }
        });

    }, 
    render() {
        return (
            <div className="topnav order-setting">
                <span style={{ color: '#fc8675' }}>接单设置</span>
                <RadioGroup onChange={this.onChange} value={this.state.value}>
                    <Radio key="a" value="1">是</Radio>
                    <Radio key="b" value="2">否</Radio>
                </RadioGroup>
            </div>
        );
    },
});