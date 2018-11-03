package com.company.common.utils.upload.packageUpload;

import com.company.common.utils.upload.packageUpload.AttachmenPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class GzDefaultPackage implements AttachmenPackage {

    private static final String Root_Dir = "广尊房贷申请资料";

    private static final String Basic_Dir = "基本信息";

    private static final String House_Dir = "房产信息";

    private static final String FaceTrial_Dir = "面审信息";

    private static final String Contract_Dir = "合同信息";

    private static final String Fair_Dir = "公正登记";

    private static final String Mortgage_Dir = "抵押登记";

    private static final String Other_Dir = "其他";

    private static final String ID_Dir = "身份证";

    private static final String HukouBook_Dir = "户口本";

    private static final String Marriage_Dir = "婚姻证明";

    private static final String Credit_Dir = "征信资料";

    private static final String HouseProperty_Dir = "房产资料";

    private static final String RISK_Dir = "风控单";

    private static final String ContractPic_Dir = "合同与签合同照片";

    private static final String FairReceipt_Dir = "公证回执单";

    private static final String ImpartialMaterial_Dir = "公证材料";

    private static final String MortgageReceipt_Dir = "抵押回执单";

    private static final String LoanCard_Dir = "放款卡";

    private static final String ThreeSquareCard_Dir = "三方卡";

    private static final String OtherRights_Dir = "他项权利证材料";

    private static final String Seq_Dir = "流水";

    private static final String Production_Dir = "产调";

    private static final String HouseBak_Dir = "备用房产证";

    private Map<String, String> fileRelationship;

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

    private String getSubPath(String parentpath, String subName) {
        StringBuilder stringBuilder = new StringBuilder().append(parentpath).append(File.separator).append(subName);
        return stringBuilder.toString();
    }

    @Override
    public String doCreateDir(String rootPath) throws Exception {

        fileRelationship = new HashMap<String, String>();

        StringBuilder stringBuilder = new StringBuilder().append(rootPath).append("bak");
        String bakDirPath = stringBuilder.toString();
        mkDirPath(bakDirPath);

        StringBuilder rootPathBuilder = new StringBuilder().append(bakDirPath).append(File.separator).append(Root_Dir);
        String targetPath = rootPathBuilder.toString();
        mkDirPath(targetPath);

        //一级目录
        String basicPath = getSubPath(targetPath, Basic_Dir);
        mkDirPath(basicPath);

        String housePath = getSubPath(targetPath, House_Dir);
        mkDirPath(housePath);

        String faceTrialPath = getSubPath(targetPath, FaceTrial_Dir);
        mkDirPath(faceTrialPath);

        String contractPath = getSubPath(targetPath, Contract_Dir);
        mkDirPath(contractPath);

        String fairPath = getSubPath(targetPath, Fair_Dir);
        mkDirPath(fairPath);

        String mortgagePath = getSubPath(targetPath, Mortgage_Dir);
        mkDirPath(mortgagePath);

        String otherPath = getSubPath(targetPath, Other_Dir);
        mkDirPath(otherPath);

        //次级目录

        //基本信息
        String idPath = getSubPath(basicPath, ID_Dir);
        mkDirPath(idPath);
        fileRelationship.put("IDCARD", idPath);

        String hukouBookPath = getSubPath(basicPath, HukouBook_Dir);
        mkDirPath(hukouBookPath);
        fileRelationship.put("HOUSEHOLD", hukouBookPath);

        String marriagePath = getSubPath(basicPath, Marriage_Dir);
        mkDirPath(marriagePath);
        fileRelationship.put("MARRIAGE", marriagePath);

        String creditPath = getSubPath(basicPath, Credit_Dir);
        mkDirPath(creditPath);
        fileRelationship.put("CREDIT", creditPath);

        //房产资料
        String housePropertyPath = getSubPath(housePath, HouseProperty_Dir);
        mkDirPath(housePropertyPath);
        fileRelationship.put("HOUSE", housePropertyPath);

        //面审信息
        String riskPath = getSubPath(faceTrialPath, RISK_Dir);
        mkDirPath(riskPath);
        fileRelationship.put("RISK", riskPath);

        //合同信息
        String ContractPicPath = getSubPath(contractPath, ContractPic_Dir);
        mkDirPath(ContractPicPath);
        fileRelationship.put("Contract_Pic", ContractPicPath);

        //公正登记
        String fairReceiptPath = getSubPath(fairPath, FairReceipt_Dir);
        mkDirPath(fairReceiptPath);
        fileRelationship.put("HUIZHIDAN", fairReceiptPath);

        String impartialMaterialPath = getSubPath(fairPath, ImpartialMaterial_Dir);
        mkDirPath(impartialMaterialPath);
        fileRelationship.put("JUSTICE", impartialMaterialPath);

        //抵押登记
        String mortgageReceiptPath = getSubPath(mortgagePath, MortgageReceipt_Dir);
        mkDirPath(mortgageReceiptPath);
        fileRelationship.put("MortgageReceipt", mortgageReceiptPath);

        String loanCardPath = getSubPath(mortgagePath, LoanCard_Dir);
        mkDirPath(loanCardPath);
        fileRelationship.put("CardNumber", loanCardPath);

        String threeSquareCardPath = getSubPath(mortgagePath, ThreeSquareCard_Dir);
        mkDirPath(threeSquareCardPath);
        fileRelationship.put("ThirdCardNumber", threeSquareCardPath);

        String otherRightsPath = getSubPath(mortgagePath, OtherRights_Dir);
        mkDirPath(otherRightsPath);
        fileRelationship.put("MATERIAL", otherRightsPath);

        //其他
        String seqPath = getSubPath(otherPath, Seq_Dir);
        mkDirPath(seqPath);
        fileRelationship.put("Water", seqPath);

        String productionPath = getSubPath(otherPath, Production_Dir);
        mkDirPath(productionPath);
        fileRelationship.put("Production", productionPath);

        String houseBakPath = getSubPath(otherPath, HouseBak_Dir);
        mkDirPath(houseBakPath);
        fileRelationship.put("HouseBak", houseBakPath);


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
