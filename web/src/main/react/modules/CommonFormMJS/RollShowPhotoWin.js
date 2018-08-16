import React from 'react';
import antd from 'antd';
import {
    Button,
    Modal,
    message
} from 'antd';
var reqwest = require('reqwest');
export default React.createClass({
    getInitialState() {
        return { visible: false };
    },
    handleOk() {
        this.setState({
            visible: false,
        });
    },
    handleCancel(e) {
        this.props.hideModal();
    },
    downLoad() {
        var me = this;
        var state = this.state;
        var formData = state.formData;
        var selectRecord=this.props.selectRecord;
        var btype = this.props.btype;
        var processInstanceId = selectRecord && selectRecord.newProcessInstanceId;
        window.location.href='/modules/common/PubAttachmentAction/downloadZip.htm?search={"projectId" : '+selectRecord.projectId+', "btype":"'+btype+'", "processInstanceId":"'+processInstanceId+'"}';
    },
    downLoadOne(fileName,id) {
        var me = this;
        var state = this.state;
        var formData = state.formData;
        var selectRecord=this.props.selectRecord;
        var btype = this.props.btype;
        var processInstanceId = selectRecord && selectRecord.newProcessInstanceId;
        window.location.href='/modules/common/PubAttachmentAction/downloadZip.htm?search={"projectId" : '+selectRecord.projectId+', "btype":"'+btype+'", "processInstanceId":"'+processInstanceId+'", "fileName":"'+fileName+'","id":"'+id+'"}';
    },
    deletePic(id) {
        var selectRecord = this.props.selectRecord;
        var btype = this.props.btype;
        Utils.ajaxData({
            url: '/modules/common/PubAttachmentAction/deletes.htm',
            data: {
                ids: JSON.stringify([id])
            },
            callback: (result) => {
                message.info(result.msg);
                this.props.showModal();
            }
        });
    },
    demoImageItems() {
        var picData = this.props.picData;
        if (picData.length) {
            return picData.map((item, index) => {
                return (<div key={index} className="img">
                    <a href={item.filePath} target="_blank"><img src={item.filePath} style={{ width: 150, height: 150 }} /></a>
                    {this.props.canEdit ? <button key="back" className="ant-btn" onClick={this.deletePic.bind(null, item.id) }>删除</button> : null}
                    <button key="downLoad" type="button" className="ant-btn" onClick={this.downLoadOne.bind(null,item.fileName,item.id)} style={{ marginLeft: "25px" }} >下载</button>
                </div>
                )
            });
        }
        else return <div>暂未上传图片</div>
    },
    render() {
        var modalBtns = [
            <button key="downLoad" className="ant-btn" onClick={this.downLoad} style={{ marginLeft: "25px" }} >打包下载</button>,
            <button key="back" className="ant-btn" onClick={this.handleCancel}>关闭</button>
        ];
        return (
            <Modal title={this.props.title} visible={this.props.visible}  width="900"
                onOk={this.handleOk} onCancel={this.handleCancel}  footer={modalBtns}
                >
                <div className="uploadFile">
                    {this.demoImageItems() }
                </div>
            </Modal>
        );
    },
});