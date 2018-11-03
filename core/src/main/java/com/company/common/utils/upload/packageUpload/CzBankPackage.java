package com.company.common.utils.upload.packageUpload;

import com.company.common.utils.upload.packageUpload.AttachmenPackage;

public class CzBankPackage implements AttachmenPackage {

    @Override
    public String doCreateDir(String rootPath) throws Exception {
        return "嘻嘻";
    }

    @Override
    public void doGetAttachFile(String filePath, String fileType) throws Exception {

    }
}
