//数字千分位加, 
import React from 'react';
import antd from 'antd';
import {
    Input,
} from 'antd';
var MicroInput = React.createClass({

    getInitialState() {
        return {
            microValue: '',
        }
    },
    componentWillReceiveProps(nextProps) {
        if (typeof nextProps.value == 'undefined' || nextProps.value == 'NaN') {
            this.setState({
                microValue: ''
            })
            return
        }
        var value = String(nextProps.value);
        var arr = value.split('.');
        var isFloat = arr.length == 2;
        if (isFloat) {
            var floatSection = arr[1];
            value = arr[0];//整数部分
        }


        var microValue = value.replace(/,/g, '').replace(/\d+?(?=(?:\d{3})+$)/g, function (s) {
            return s + ',';
        });
        if (isFloat) {
            microValue = microValue + '.' + floatSection
        }
        this.setState({
            microValue
        })

    },
    componentDidMount() {
        var props = this.props;
        if (typeof props.value == 'undefined' || props.value == 'NaN') {
            this.setState({
                microValue: ''
            })
            return
        }
        var value = String(props.value);
        var arr = value.split('.');
        var isFloat = arr.length == 2;
        if (isFloat) {
            var floatSection = arr[1];
            value = arr[0];//整数部分
        }


        var microValue = value.replace(/,/g, '').replace(/\d+?(?=(?:\d{3})+$)/g, function (s) {
            return s + ',';
        });
        if (isFloat) {
            microValue = microValue + '.' + floatSection
        }
        this.setState({
            microValue
        })
    },
    changeValue(e) {
        var microValue = e.target.value;
        if (microValue == "") {
            this.props.onChange("");
            return
        }
        var arr = microValue.split('.');
        var value = parseInt(arr[0].split(',').join(''));
        if (arr.length == 2) {
            if (arr[1] == '') {
                value = String(value) + '.';
            }
            else value = parseFloat(value + '.' + arr[1])
        }
        if (String(value) == 'NaN') {
            value = '';
        }
        this.props.onChange(value);
    },
    render() {
        var props = this.props;
        return <Input {...props} value={this.state.microValue} onChange={this.changeValue} type="text" autoComplete="off"  />
    },
});
module.exports = MicroInput;