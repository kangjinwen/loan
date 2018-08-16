//房本照片
import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Upload,
    message
} from 'antd';

import ShowPhotoWin from './ShowPicWin'
const createForm = Form.create;
const Option = Select.Option;
var LenderId = React.createClass({
    getInitialState() {
        return {
            picData: [],
            visible: false,
        }
    },
    showModal(title, canEdit) {
        var id = this.props.id;
        var selectRecord = this.props.selectRecord;
        var btype = this.props.btype;
        Utils.ajaxData({
            url: '/modules/common/PubBizAttachmentAction/queryAll.htm',
            data: {
                search: JSON.stringify({
                    bizType: btype,
                    relationId: this.props.idData
                })
            },
            callback: (result) => {
                this.setState({
                    canEdit: canEdit,
                    visible: true,
                    title: title,
                    picData: result.data,
                });
            }
        });
    },
    hideModal() {
        this.setState({
            visible: false
        });
    },
    render() {
        var canEdit = this.props.canEdit;
        var selectRecord = this.props.selectRecord;
        var btype = this.props.btype;
        var isFirst = true;
        const uploadProps = {
            name: 'Filedata',
            action: '/modules/common/PubBizAttachmentAction/upload.htm',
            multiple: true,
            data: {
                data: JSON.stringify({
                    bizType: btype,
                    relationId: this.props.idData
                })
            },
            onChange(info) {
                if (info.file.status !== 'uploading') {
                }
                if (info.file.status === 'done' && info.file.response && info.file.response.code == 200) {
                    isFirst = true;
                    message.destroy();
                    message.success(`${info.file.name} 上传成功。`);
                } else if (info.file.status === 'error') {
                    isFirst = true;
                    message.destroy();
                    message.error(`${info.file.name} 上传失败。`);
                } else if (info.file.response && info.file.response.code == 400) {
                    isFirst = true;
                    message.destroy();
                    message.error(`${info.file.name} ${info.file.response.msg}。`);
                }
                else {
                    if (info.file.status == "uploading" && isFirst) {
                        message.loading(`${info.file.name} 上传中.......`, 0);
                        isFirst = false
                    }

                }
            },
            beforeUpload(file) {
                message.config({
                    duration: 5,
                });
                if (!/^[a-zA-Z0-9_\-\u4e00-\u9fa5.\s*|\[|\]|\(|\)|\{|\}|\（|\）|\【|\】]*$/.test(file.name)) {
                    message.error('上传文件名称只能是数字、字母、汉字、.、—、空格、{}、[]、()、_！');
                    return false;
                }
                var size = file.size;
                if (size > 5242880) {
                    message.error('上传文件不能大于5M！');
                    return false;
                }
                const isJPG = file.type === 'image/jpeg';
                if (!isJPG) {
                    message.error('只能上传 JPG 文件哦！');
                }
                return isJPG;
            },
        };
        var state = this.state;
        var idData = this.props.idData
        return (<span style={this.props.style}>
            <span style={{ marginTop: 25, marginBottom: 15 }} >
                <span style={{ marginRight: 25, }}>{this.props.title}</span>
                {canEdit ? <Upload {...uploadProps}> <a style={{ marginRight: 25, }}>上传附件</a> </Upload> : null}
                <a style={{ marginRight: 25, }} onClick={this.showModal.bind(this, '查看附件', canEdit) }>查看附件</a>
            </span >

            <ShowPhotoWin ref="ShowPhotoWin" showModal={this.showModal.bind(this, '查看附件', canEdit) }
                visible={state.visible} canEdit={this.props.canEdit}
                title={state.title} hideModal={this.hideModal}
                record={state.selectedrecord}
                canEdit={state.canEdit}
                selectRecord={selectRecord}
                picData={state.picData}
                idData={idData}
                btype={btype}
                />
        </span>
        )
    }
});
export default LenderId;