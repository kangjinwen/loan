package com.company.common.utils.upload.packageUpload;

public interface AttachmenPackage {

    public String doCreateDir(String rootPath) throws Exception;

    public void doGetAttachFile(String filePath, String fileType) throws Exception;
}
