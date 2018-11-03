package com.company.common.utils.upload.packageUpload;

import com.company.common.utils.upload.packageUpload.AttachmenPackage;
import com.company.common.utils.upload.packageUpload.PackTemplateCfgReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.modules.system.service.ChannelPartnerService;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneralPackage implements AttachmenPackage {

    private static final String Root_Plan = "广尊标准";

    @Autowired
    private ChannelPartnerService channelPartnerService;

    private Map<String, String> fileRelationship;

    private String getSubPath(String parentpath, String subName) {
        StringBuilder stringBuilder = new StringBuilder().append(parentpath).append(File.separator).append(subName);
        return stringBuilder.toString();
    }

    private Boolean mkDirPath(String dirPath) {

        Boolean res = false;
        File dirSave = new File(dirPath);
        if (dirSave.exists()) {
            res = dirSave.isDirectory();
        } else {
            res =dirSave.mkdir();
            dirSave.setReadable(true,false);
            dirSave.setWritable(true, false);
            dirSave.setExecutable(true, false);
        }
        return  res;
    }

    @Override
    public String doCreateDir(String rootPath) throws Exception {

        String cfgPath = channelPartnerService.getTemplateFilePath();
        String cfgName = channelPartnerService.getTemplateFileName();
        PackTemplateCfgReader.getTemplateList(cfgPath, cfgName);
        List<Map<String, Object>> packagePlan = PackTemplateCfgReader.getPackagePlan(Root_Plan);

        fileRelationship = new HashMap<String, String>();

        StringBuilder stringBuilder = new StringBuilder().append(rootPath).append("bak");
        String bakDirPath = stringBuilder.toString();
        mkDirPath(bakDirPath);

        StringBuilder rootPathBuilder = new StringBuilder().append(bakDirPath).append(File.separator).append(Root_Plan);
        String targetPath = rootPathBuilder.toString();
        mkDirPath(targetPath);

        for (Map<String, Object> group : packagePlan) {
            for (Map.Entry<String, Object> data : group.entrySet()) {

                String pathName =  data.getKey();
                String typePath = getSubPath(targetPath, pathName);
                mkDirPath(typePath);

                Map<String, String> child =(Map<String, String>) data.getValue();
                for (String filePath: child.keySet()) {
                    String bType = child.get(filePath);
                    String childPath = getSubPath(typePath, filePath);
                    mkDirPath(childPath);
                    if (bType != null)
                        fileRelationship.put(bType, childPath);
                }
            }
        }
        return targetPath;
    }

    @Override
    public void doGetAttachFile(String filePath, String fileType) throws Exception {

        File file = new File(filePath);
        if (file.exists()) {
            StringBuilder savePathBuilder = new StringBuilder().append(fileRelationship.get(fileType)).append(File.separator).append(file.getName());
            String savePath = savePathBuilder.toString();
            File saveFile = new File(savePath);
            saveFile.setReadable(true,false);
            saveFile.setWritable(true, false);
            saveFile.setExecutable(true, false);
            if (!saveFile.exists()) {
                Files.copy(file.toPath(), saveFile.toPath());}
        }
    }
}
