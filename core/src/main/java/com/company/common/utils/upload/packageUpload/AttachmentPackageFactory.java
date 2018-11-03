package com.company.common.utils.upload.packageUpload;

import com.company.common.utils.upload.packTemplate;
import com.company.common.utils.upload.packageUpload.AttachmenPackage;
import com.company.common.utils.upload.packageUpload.GzDefaultPackage;
import com.company.common.utils.upload.packageUpload.CzBankPackage;
import com.company.common.utils.upload.packageUpload.GeneralPackage;
import org.springframework.stereotype.Service;

@Service
public class AttachmentPackageFactory {

    public static AttachmenPackage createAttachmenPackageInf(packTemplate template) {
        switch (template) {
            case gzDefault:
                return new GzDefaultPackage();
            //case czBank:
            //    return new CzBankPackage();
            default:
                return new GeneralPackage();
        }
    }
}
