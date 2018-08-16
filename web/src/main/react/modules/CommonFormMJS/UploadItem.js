import React from 'react';
import {message, Upload} from 'antd';
import 'viewerjs/dist/viewer.min.css';
import Viewer from "viewerjs";

var reqwest = require('reqwest');
export default React.createClass({

    getInitialState() {
        return {
            fileList: [],
            formData: {}
        };
    },
    queryFormData(selectRecord, idData) {
        var me = this;
        me.setState({
            fileList: []
        });
        var btype = this.props.btype;
        var url = '/modules/common/PubAttachmentAction/queryAll.htm';
        var data = {
            btype: btype,
            projectId: selectRecord && selectRecord.projectId,
            processInstanceId: selectRecord && selectRecord.processInstanceId,
        };
        if (btype && btype == 'HOUSE') {
            url = '/modules/common/PubBizAttachmentAction/queryAll.htm';
            data = {
                bizType: btype,
                relationId: idData && idData,
            }
        }
        if ((selectRecord && selectRecord.processInstanceId && btype != 'HOUSE') || (idData && btype == 'HOUSE')) {
            reqwest({
                url: url,
                method: 'get',
                data: {
                    search: JSON.stringify(data)
                },
                type: 'json',
                success: (result) => {
                    var data = result.data;
                    var length = data.length;
                    var fileList = [];
                    for (let i = 0; i < length; i++) {
                        let row = data[i];
                        let file = {};
                        file.url = row.filePath;
                        file.id = row.id;
                        fileList.push(file);
                    }
                    me.setState({
                        fileList: fileList
                    });
                }
            });
        }
    },
    componentWillReceiveProps(nextProps) {
        if (this.props.selectRecord && this.props.idData && (this.props.idData != nextProps.idData)) {
            //if(this.props.selectRecord){
            this.queryFormData(nextProps.selectRecord, nextProps.idData);
        }
    },
    componentDidMount() {
        console.log(this.props.canEdit)

        if (this.props.selectRecord || this.props.idData) {
            this.queryFormData(this.props.selectRecord, this.props.idData);
        }
    },
    handleChange(info) {
        console.log(info)
        if (info.file.status === 'done') {
            console.log("handleChange", info);
            this.queryFormData(this.props.selectRecord);
        }
    },
    downLoad() {
        var props = this.props;
        var selectRecord = props.selectRecord;
        var btype = props.btype;
        var idData = props.idData;

        if (btype == 'HOUSE') {
            var bizType = btype;
            window.location.href = '/modules/common/PubBizAttachmentAction/downloadZip.htm?search={"relationId" : ' + idData + ', "bizType":"' + bizType + '"}';
        } else {
            var processInstanceId = selectRecord && selectRecord.processInstanceId;
            var projectId = selectRecord && selectRecord.projectId;
            if (props.title == "他项权利证材料" || props.title == "公证材料") {
                processInstanceId = selectRecord && selectRecord.projectId;
            }
            window.location.href = '/modules/common/PubAttachmentAction/downloadZip.htm?search={"projectId" : ' + projectId + ', "btype":"' + btype + '", "processInstanceId":"' + processInstanceId + '"}';
        }

    },
    /*  downLoad() {
          var me = this;
          var state = this.state;
          var formData = state.formData;
          var selectRecord = this.props.selectRecord;
          var btype = this.props.btype;
          var processInstanceId = selectRecord && selectRecord.processInstanceId;
          if (this.props.title == "他项权利证材料" || this.props.title == "公证材料") {
              processInstanceId = selectRecord && selectRecord.projectId;
          }
          window.location.href = '/modules/common/PubAttachmentAction/downloadZip.htm?search={"projectId" : ' + selectRecord.projectId + ', "btype":"' + btype + '", "processInstanceId":"' + processInstanceId + '"}';
      },*/
    downLoadOne(fileName, id) {
        var me = this;
        var state = this.state;
        var formData = state.formData;
        var selectRecord = this.props.selectRecord;
        var btype = this.props.btype;
        var processInstanceId = selectRecord && selectRecord.processInstanceId;
        if (this.props.title == "他项权利证材料" || this.props.title == "公证材料") {
            processInstanceId = selectRecord && selectRecord.projectId;
        }
        window.location.href = '/modules/common/PubAttachmentAction/downloadZip.htm?search={"projectId" : ' + selectRecord.projectId + ', "btype":"' + btype + '", "processInstanceId":"' + processInstanceId + '", "fileName":"' + fileName + '","id":"' + id + '"}';
    },
    deletePic(id) {
        var props = this.props;
        var selectRecord = props.selectRecord;
        var btype = props.btype;
        var idData = props.idData;
        var url = '/modules/common/PubAttachmentAction/deletes.htm';
        if (btype && btype == 'HOUSE') {
            url = '/modules/common/PubBizAttachmentAction/deletes.htm';
        }
        Utils.ajaxData({
            url: url,
            data: {
                ids: JSON.stringify([id])
            },
            callback: (result) => {
                message.info(result.msg);
                this.queryFormData(selectRecord, idData);
            }
        });
    },
    lookPdf(url) {
        window.open(url);
    },
    previewImage(e) {
        let viewer = new Viewer(e.target);
        viewer.show()
    },
    render() {
        var me = this;
        var formData = this.state.formData;
        var props = this.props;
        /*if(!props.selectRecord)
          return null*/
        var selectRecord = props.selectRecord;
        var idData = props.idData;
        var btype = props.btype;
        var images = "";
        var canEdit = props.canEdit;
        var canUpload = props.canUpload;
        if (props.otherType && btype != 'HOUSE') {
            canEdit = false
        }
        images = this.state.fileList.map((file, index) => {
            var url = file.url;
            var fileExtension = url.substring(url.lastIndexOf('.') + 1);
            return <div className="img" key={index}>
                <a href="javascrript:;">{fileExtension == 'jpg' || fileExtension == 'JPG' || fileExtension == 'JPEG' || fileExtension == 'jpeg' || fileExtension == 'png' || fileExtension == 'PNG' ?
                    <img src={url} onClick={this.previewImage}/> :
                    <iframe width="100%" height="230px" src={url}></iframe>}</a>
                {fileExtension == 'pdf' ? <button key="lookPdf" type="button" className="ant-btn lookPdf"
                                                  onClick={this.lookPdf.bind(this, url)}>预览</button> : null}
                {canUpload ?
                    <button key="delete" type="button" className="ant-btn" onClick={this.deletePic.bind(this, file.id)}>
                        <i className="anticon anticon-delete"></i>删除</button> : null}
            </div>
        });
        /*var postDate={
                        rd_operatorid :1,
                        relationId:selectRecord.relationId,
                        processInstanceId:selectRecord.processInstanceId,
                        project_id:selectRecord.projectId,
                        rd_btype:btype
                      };*/
        var isFirst = true;
        var data = {
            btype: btype,
            projectId: selectRecord && selectRecord.projectId,
            processInstanceId: selectRecord && selectRecord.processInstanceId,
        };
        var url = '/modules/common/PubAttachmentAction/upload.htm';
        if (btype && btype == 'HOUSE') {
            url = '/modules/common/PubBizAttachmentAction/upload.htm';
            data = {
                bizType: btype,
                relationId: idData && idData,
            }
        }
        const uploadProps = {
            name: 'Filedata',
            action: url,
            multiple: true,
            data: {
                data: JSON.stringify(data)
            },
            onChange(info) {
                if (info.file.status !== 'uploading') {
                    // console.log(info.file, info.fileList);
                }
                if (info.file.status === 'done' && info.file.response && info.file.response.code == 200) {
                    isFirst = true;
                    message.destroy();
                    message.success(`${info.file.name} 上传成功。`);
                    me.queryFormData(selectRecord, idData);
                } else if (info.file.status === 'error') {
                    isFirst = true;
                    message.destroy();
                    message.error(`${info.file.name} 上传失败。`);
                } else if (info.file.response && info.file.response.code != 200) {
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
                // const isJPG = (file.type === 'image/jpeg' || file.type === 'application/pdf');
                var isJPG = file.type === 'image/png' || file.type === 'image/jpeg';
                if (btype == "Contract_Pic") {
                    isJPG = (file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'application/pdf');
                }
                if (!isJPG) {
                    if (btype == "Contract_Pic") {
                        message.error('只能上传 PNG、JPG 文件或者pdf文件哦！');
                    } else {
                        message.error('只能上传  PNG、JPG 文件哦！');
                    }

                }
                return isJPG;
            },
        };
        return (<div className="uploadFile">
                {images}
                <div className="img">
                    <div className="uploadBg uploadPlus">
                        {canEdit&&canUpload ? <Upload {...uploadProps}> <a style={{marginRight: 25,}}></a> </Upload> : null}
                    </div>
                    <button key="downLoad" type="button" className="ant-btn" onClick={this.downLoad}
                            style={{marginTop: '5px'}}><i className="anticon anticon-download-line"></i>打包下载
                    </button>
                </div>
            </div>

        )
    }
});
