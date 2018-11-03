package com.company.modules.system.domain;

import java.io.Serializable;

public class SysUploadInfo implements Serializable {

    //文件名称
    private String name;
    //文件访问url地址
    private String url;
    //文件访问实际地址
    private String saveFilePath;
    //文件原始名称
    private String originaName;
    //文件大小
    private Long fileSize;
    //二次压缩文件字节数组
    private  byte[] zipFileBytes;

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getSaveFilePath()  {
        return saveFilePath;
    }

    public String getOriginaName(){
        return originaName;
    }

    public Long getFileSize(){
        return fileSize;
    }

    public byte[] getZipFileBytes() {
        return zipFileBytes;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setSaveFilePath(String saveFilePath){
        this.saveFilePath = saveFilePath;
    }

    public void setOriginaName(String originaName){
        this.originaName = originaName;
    }

    public void setFileSize(Long fileSize){
        this.fileSize = fileSize;
    }

    public void setZipFileBytes(byte[] zipFileBytes) {
        this.zipFileBytes = zipFileBytes;
    }
}
