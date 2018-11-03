package com.company.modules.common.action;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.upload.packTemplate;
import com.company.common.utils.upload.packageUpload.AttachmenPackage;
import com.company.common.utils.upload.packageUpload.AttachmentPackageFactory;

import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.common.domain.PubBizAttachment;
import com.company.modules.common.service.PubBizAttachmentService;
import com.company.modules.common.utils.ZipUtil;
import com.company.modules.system.service.ChannelPartnerService;
import com.company.modules.common.utils.DirZipUtil;

import com.company.common.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modules/common/PubPackageAttachmentAction")
public class PubPackageAttachmentAction extends BaseAction {

    @Autowired
    private PubAttachmentService pubAttachmentService;

    @Autowired
    private ChannelPartnerService channelPartnerService;

    @Autowired
    private PubBizAttachmentService pubBizAttachmentService;

    @Autowired
    private AttachmentPackageFactory attachmentPackageFactory;

    @RequestMapping(value = "/getPackageTemplateList.htm")
    public void getPackageTemplateList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> res = new HashMap<String, Object>();
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> template = new HashMap<String, Object>();
        for (packTemplate packEnum : packTemplate.values()) {
            template.put("name" ,packEnum.getName());
            template.put("value", packEnum.toString());
            data.add(template);
        }
        if (template != null) {
            res.put(Constant.RESPONSE_DATA, data);
            res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
            ServletUtils.writeToResponse(response, res);
        } else {
            res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, "获取模板列表失败");
            ServletUtils.writeToResponse(response, res);
        }
    }

    @RequestMapping(value = "/downloadZipByTemplate.htm")
    public void downloadZipByTempLate(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "projectId") String projectId,
        @RequestParam(value = "relationId") String relationId,
        @RequestParam(value = "templateName") String templateName) throws Exception {

        try {
            Map<String, Object> res = new HashMap<String, Object>();
            Map<String, Object> param = new HashMap<String, Object>();
            if (templateName != null) {
                packTemplate template = packTemplate.valueOf(templateName);
                AttachmenPackage attachmenPackage = attachmentPackageFactory.createAttachmenPackageInf(template);
                String zipFilePath = attachmenPackage.doCreateDir(channelPartnerService.getUploadPath());
                //先处理pub_attachment表的再处理pub_biz_attachment表的
                if (projectId != null) {
                    param.put("projectId", projectId);
                    List<PubAttachment> attachmentList = pubAttachmentService.select(param);
                    for (PubAttachment attachmentRec : attachmentList) {
                        String filePath = attachmentRec.getFilePath().replace(channelPartnerService.getUploadFileURL(), channelPartnerService.getUploadPath());
                        String fileType = attachmentRec.getBtype();
                        attachmenPackage.doGetAttachFile(filePath, fileType);
                    }
                }

                if (relationId != null) {
                    param.clear();
                    param.put("relationId", relationId);
                    List<PubBizAttachment> bizAttachmentList = pubBizAttachmentService.queryAll(param);
                    for (PubBizAttachment bizAttachmentRec : bizAttachmentList) {
                        String filePath = bizAttachmentRec.getFilePath().replace(channelPartnerService.getUploadFileURL(), channelPartnerService.getUploadPath());
                        String fileType = bizAttachmentRec.getBizType();
                        attachmenPackage.doGetAttachFile(filePath, fileType);
                    }
                }
                String name = new String(("附件.zip").getBytes("gbk"), "iso8859-1");
                response.setHeader("Content-Disposition", "attachment;filename=" + name);
                DirZipUtil.createZip(zipFilePath, response.getOutputStream());
                DirZipUtil.deleteDir(new File(zipFilePath));

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
