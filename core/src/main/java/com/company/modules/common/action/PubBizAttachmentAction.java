package com.company.modules.common.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.upload.CustomFileUpload;
import com.company.common.utils.upload.ProgressUtil;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.domain.PubBizAttachment;
import com.company.modules.common.service.PubBizAttachmentService;
import com.company.modules.common.utils.ZipUtil;
import com.company.modules.system.domain.SysUploadInfo;
import com.company.modules.system.service.ChannelPartnerService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * 
 *	@Description 附件
 *  @author liyc,lyc1@erongdu.com
 *  @CreatTime 2016年7月12日 下午3:05:51
 *  @since version 1.0.0
 */
@Controller
@RequestMapping("/modules/common/PubBizAttachmentAction")
public class PubBizAttachmentAction extends BaseAction {

    private Map<String, Object> map = new HashMap<String, Object>();
    private Logger logger = LoggerFactory.getLogger(PubBizAttachmentAction.class);

    @Autowired
    private PubBizAttachmentService pubBizAttachmentService;

    @Autowired
    private ChannelPartnerService channelPartnerService;

    @Autowired
    private CustomFileUpload customFileUpload;

    List<PubBizAttachment> lp = null;

    /**
     * 
     * @description 附件上传带进度
     * @param request
     * @param response
     * @throws IOException
     * @author liyc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping(value = "/progress.htm")
    public void getProgress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String progressId = request.getParameter("progressId");
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (progressId == null || "".equals(progressId)) {
            responseMap.put("success", "false");
            responseMap.put("progress", "0");
            return;
        }
        String percent = "0";
        if (ProgressUtil.progressMap.get(progressId) == null) {
        	logger.info("progressMethod 未查找到 progressId=" + progressId);
            ProgressUtil.progressMap.put(progressId, 0);
        } else {
            percent = String.valueOf(ProgressUtil.progressMap.get(progressId));
        }

        responseMap.put("success", "true");
        responseMap.put("progress", percent);
        com.company.common.utils.ServletUtils.writeToResponse(response, responseMap);
    }

    @RequestMapping(value = "/getfilesize.htm")
    public void getFilesize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer filesize = request.getContentLength();

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("success", "true");
        responseMap.put("filesize", String.valueOf(filesize));

        com.company.common.utils.ServletUtils.writeToResponse(response, responseMap);
    }

    @SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@RequestMapping(value = "/downloadZip.htm")
    public void downloadZip(
    		HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam(value="search") String search,
			@RequestParam(value="fileExistCheck", defaultValue="false") boolean fileExistCheck
			) throws Exception {
    			Map<String, Object> param = JsonUtil.parse(search, Map.class);
    			param.put("fields", "file_name");
                List<PubBizAttachment> list = pubBizAttachmentService.queryAll(param);
                String appDir = channelPartnerService.getUploadPath();
                if(fileExistCheck){
                    List<File> notExists=new ArrayList<File>();
                    for (PubBizAttachment rec: list) {
                        String filePath = rec.getFilePath().replace(channelPartnerService.getUploadFileURL(), channelPartnerService.getUploadPath());
                        File file=new File(filePath);
                        if(!file.exists())notExists.add(file);
                    }

                    Map result=new HashMap();
                    result.put("success",list.size()>0 && notExists.size()==0);
                    result.put("fileSurvivalCnt",list.size()-notExists.size());
                    if(list.size()==0 || notExists.size()>0){
                        String info="共<span style='color:red'>"+list.size()+"</span>个文件，有<span style='color:red'>"+notExists.size()+"</span>个文件已被删除,";
                        info+=notExists.size()==list.size()?"没有文件可下载!":"是否确认下载?";

                        result.put(Constant.RESPONSE_CODE_MSG,info);
                    }else{
                        result.put(Constant.RESPONSE_CODE_MSG,"文件正常");
                    }
                    ServletUtils.writeToResponse(response, result);
                }else{
                    String name=new String(("附件.zip").getBytes("gbk"),"iso8859-1");
                    response.setHeader("Content-Disposition","attachment;filename="+name);

                    List<File> files=new ArrayList<File>();
                    for (PubBizAttachment rec : list) {
                        String filePath = rec.getFilePath().replace(channelPartnerService.getUploadFileURL(), channelPartnerService.getUploadPath());
                        File file=new File(filePath);
                        if(file.exists()){
                            files.add(file);
                        }
                    }
                    ZipUtil.zipFiles4j(files,response.getOutputStream());
                }
    }

    /**
     * 
     * @description 查询所有，不包含分页
     * @param search
     * @param request
     * @param response
     * @throws Exception
     * @author liyc
     * @return void
     * @since  1.0.0
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryAll.htm")
    public void query3(
    		@RequestParam(value="search") String search,
    		HttpServletRequest request, HttpServletResponse response
    		) throws Exception {
    	Map<String, Object> params = JsonUtil.parse(search, Map.class);
        List<PubBizAttachment> files = pubBizAttachmentService.queryAll(params);
//        params.put("fields","file_name");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_DATA, files);
        ServletUtils.writeToResponse(response, res);
    }


    
    /**
     * 
     * @description 
     * @param currentPage
     * @param pageSize
     * @param response
     * @param request
     * @author liyc
     * @return void
     * @since  1.0.0
     */
    @SuppressWarnings({ "unchecked" })
	@RequestMapping("/query.htm")
    public void query(
    		@RequestParam(value="search") String search,
    		@RequestParam(value="currentPage", required = false) Integer currentPage, 
    		@RequestParam(value="pageSize", required = false) Integer pageSize, 
    		HttpServletResponse response, HttpServletRequest request) 
    throws Exception {
        Map<String, Object> params = JsonUtil.parse(search, Map.class);
        PageBounds bounds = new PageBounds();
        bounds.setPage(currentPage);
        bounds.setLimit(pageSize);
        List<PubBizAttachment> pager = pubBizAttachmentService.getPageListByMap(params, bounds);
        Integer total = pubBizAttachmentService.getPageCountByMap(params);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_DATA, pager);
        res.put(Constant.RESPONSE_DATA_TOTALCOUNT, total);
        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 
     * @description 附件批量删除
     * @param request
     * @param response
     * @param ids
     * @throws Exception
     * @author liyc
     * @return void
     * @since  1.0.0
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/deletes.htm")
    public void deletes(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="ids", required=true)String ids
    		) throws Exception{
    	Map<String, Object> res = new HashMap<String, Object>();
    	List<Integer> idList = JsonUtil.parse(ids, List.class);
    	List<Long> idL = new ArrayList<Long>();
    	for (Integer id : idList) {
    		idL.add(id.longValue());
		}
        pubBizAttachmentService.deletes(idL);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, res);
    }

    @RequestMapping(value = "/translateAttachment.htm")
    public void translateAttachment(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        List<PubBizAttachment>list = pubBizAttachmentService.queryAll(params);
        StringBuilder stringBuilder = new StringBuilder().append(channelPartnerService.getUploadPath()).append("bak");
        String bakDirPath = stringBuilder.toString();
        customFileUpload.mkDirPath(bakDirPath);
        for (PubBizAttachment rec : list) {
            String filePath = rec.getFilePath().replace(channelPartnerService.getUploadFileURL(), channelPartnerService.getUploadPath());
            File file=new File(filePath);
            if((rec.getThumbnailBlob() == null) && file.exists() && (customFileUpload.isPicture(file))) {
                StringBuilder bakFilePathBuilder = new StringBuilder().append(bakDirPath).append(File.separator).append(rec.getNewfileName());
                String bakFilePath = bakFilePathBuilder.toString();
                File newFile = new File(bakFilePath);
                byte[] fileByteArray = new byte[1024];
                fileByteArray = customFileUpload.getFileByteArray(file, newFile);
                newFile.delete();
                if (fileByteArray.length != 0) {
                    rec.setThumbnailBlob(fileByteArray);
                }
                pubBizAttachmentService.update(rec);
            }
        }
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 
     * @description 附件上传
     * @param request
     * @param response
     * @author liyc
     * @return void
     * @since  1.0.0
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/upload.htm")
    public void upload3(final HttpServletRequest request, final HttpServletResponse response) throws Exception{
        // 设置上传文件的大小，超过这个大小 将抛出IOException异常，默认大小是1M。

        // 返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        List<SysUploadInfo> sysUploadInfos = customFileUpload.doFileUpload(request, channelPartnerService.getUploadPath(), channelPartnerService.getUploadFileURL(), true);
        if (sysUploadInfos!=null && sysUploadInfos.size()>0) {
            for (int i = 0; i < sysUploadInfos.size(); i++) {
                PubBizAttachment attachment = new PubBizAttachment();
                attachment.setState(1);
                attachment.setFilePath(sysUploadInfos.get(i).getUrl());
                attachment.setFileName(sysUploadInfos.get(i).getOriginaName());
                attachment.setNewfileName(sysUploadInfos.get(i).getName());
                attachment.setFileSize(sysUploadInfos.get(i).getFileSize());
                attachment.setOperatorId(getLoginUser(request).getId());
                attachment.setBizType(customFileUpload.getUploadReqData().get("bizType").toString());
                attachment.setRelationId(Long.valueOf(customFileUpload.getUploadReqData().get("relationId").toString()));
                if ((sysUploadInfos.get(i).getZipFileBytes() != null) && (sysUploadInfos.get(i).getZipFileBytes().length != 0)) {
                    attachment.setThumbnailBlob(sysUploadInfos.get(i).getZipFileBytes());//(thumbnailBlob);
                }
                Long id = pubBizAttachmentService.insert(attachment);

                result.put("id", attachment.getId());
                result.put("createtime", new Date());
                result.put("uri", sysUploadInfos.get(i).getUrl());
            }
        } else {
            throw new RuntimeException("没有文件可上传");
        }
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_DATA, result);
        res.put("success", "true");
        ServletUtils.writeToResponse(response, res);
    }

    @SuppressWarnings("deprecation")
	private File getSaveDir(String dirName,HttpServletRequest request){
        if(dirName==null)dirName=new SimpleDateFormat("yyyy-MM").format(new Date());
		final File fileDir = new File(
				request.getSession().getServletContext().getRealPath("/FileUploadDir/" + dirName));
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    private File getSaveDir(HttpServletRequest request){
        return getSaveDir(null,request);
    }

    @SuppressWarnings("deprecation")
	@RequestMapping("/download/isFileExists")
    public void isFileExists(
    		String filename, 
    		HttpServletRequest request, HttpServletResponse response
    		) throws Exception{
		logger.info("        " + request.getSession().getServletContext().getRealPath("/"));
        Map<String, Object> res = new HashMap<String, Object>();
        String name=filename.replaceFirst("^/FileUploadDir","");
        name="/FileUploadDir/"+name;

		File file = new File(request.getSession().getServletContext().getRealPath("/"), name);
        boolean exists = file.exists();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put("exists", exists);
        if (!exists) {
        	res.put("filename",file.getName());
        	res.put(Constant.RESPONSE_CODE_MSG, "文件不存在:" + file.getName());
        }
        ServletUtils.writeToResponse(response, res);
    }

    @SuppressWarnings("deprecation")
	@RequestMapping("/download/{dir}/{fileName}")
    public void download(@PathVariable("dir") String dir, @PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        String separator = File.separator;

		String webRoot = request.getSession().getServletContext().getRealPath("/FileUploadDir")
				.replaceAll(separator + "$", "");

        File downLoadFile = new File(webRoot + separator + dir + separator + fileName);
        if (!downLoadFile.exists()) {
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("msg", "文件不存在:" + fileName);
            com.company.common.utils.ServletUtils.writeToResponse(response, json);
        }
        try {
            long fileLength = downLoadFile.length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(downLoadFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[4096];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }

    /**
     * 根据流程ID获取某合同的答订成功上传的图片
     *
     * @param request
     * @param response
     * @version 1.0
     * @author 吴国成
     * @created 2015年3月3日
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/getContractPic.htm")
    public void queryContractPicByProcessInstanceId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> res = new HashMap<String, Object>();
        long id = this.paramLong(request, "id");
        String flag = this.paramString(request, "flag");

        try {
            param.put("processInstanceId", id);
            param.put("btype", "Contract_Pic");
            if (null == lp || lp.size() == 0 || "initdata".equals(flag)) {
                lp = pubBizAttachmentService.queryAll(param);

                for (int i = 0; i < lp.size(); i++) {
                    PubBizAttachment pubBizAttachment = lp.get(i);
                    String path = pubBizAttachment.getFilePath();
					File file = new File(request.getSession().getServletContext().getRealPath("/") + "/" + path);
                    if (!file.exists()) {
                        //pubAttachment.setPath("/resources/img/s.gif");
                        lp.clear();
                        break;
                    }
                }
            }

            res.put("datas", lp);
            res.put("dataCount", lp.size());
            ServletUtils.writeToResponse(response, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

