package com.company.modules.system.action;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.company.common.utils.upload.HttpServletRequestProxy;
import com.company.common.utils.upload.RenamePolicyCos;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.domain.PubContractAttachment;
import com.github.pagehelper.PageInfo;
import com.oreilly.servlet.MultipartRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysRoleService;
import com.company.modules.system.service.SysUserService;
import com.company.modules.system.service.ChannelPartnerService;
import com.company.modules.system.domain.ChannelPartner;

/**
 * 角色Action
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月23日 下午1:45:50
 */
@Controller
public class SysPartnerAction extends BaseAction {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ChannelPartnerService channelPartnerService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/getPubPartnerInfo.htm")
	public void getPubPartnerInfo(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "partnerName") String partnerName) throws Exception{

		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> PartnerInfo = channelPartnerService.getLogoInfoByName(partnerName);
		if (!PartnerInfo.isEmpty()) {
			returnMap.put("logoUrl", PartnerInfo.get("logo"));
			returnMap.put("titleName", PartnerInfo.get("title"));
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/getPubPartnerlist.htm")
	public void getPubPartnerlist(HttpServletResponse response, HttpServletRequest request,
								  @RequestParam(value = "currentPage") Integer currentPage,
								  @RequestParam(value = "pageSize") Integer pageSize) throws Exception{

		Map<String, Object> returnMap = new HashMap<String, Object>();
		PageHelper.startPage(currentPage, pageSize);
		List<ChannelPartner> channelPartners = channelPartnerService.getPartnerList();
		PageInfo<ChannelPartner> page = new PageInfo<>(channelPartners);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/getPubPartnerbyid.htm")
	public void getPubPartnerbyid(HttpServletResponse response, HttpServletRequest request,
								  @RequestParam(value = "id") Integer id) throws Exception{

		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner = channelPartnerService.getInfoByID(id);
		returnMap.put(Constant.RESPONSE_DATA, channelPartner);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/insertPubPartnerInfo.htm")
	public void insertPubPartnerInfo(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="partnerName",required=false) String partnerName,
		@RequestParam(value="logo",required=false) String logo,
		@RequestParam(value = "title") String title) throws Exception{

		long influence = 0;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner = new ChannelPartner();
		channelPartner.setPartnerName(partnerName);
		channelPartner.setLogo(logo);
		channelPartner.setTitle(title);
		channelPartner.setCreateTime(new Date());
		channelPartner.setIsDelete((byte) 0);
		influence = channelPartnerService.insert(channelPartner);
		if (influence > 0) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/deletePubPartnerInfo.htm")
	public void deletePubPartnerInfo(HttpServletResponse response, HttpServletRequest request,
								  @RequestParam(value = "id") Integer id) throws Exception{

		boolean flag = false;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner= channelPartnerService.getInfoByID(id);
		if (channelPartner != null) {
			flag = channelPartnerService.deleteById(channelPartner.getId());
			if (flag) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/updatePubPartnerInfo.htm")
	public void updatePubPartnerInfo(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="id",required=false) Integer id,
		@RequestParam(value="partnerName",required=false) String partnername,
		@RequestParam(value="logo",required=false) String logo,
		@RequestParam(value = "title") String title) throws Exception{

		boolean flag = false;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner= channelPartnerService.getInfoByID(id);
		if (channelPartner != null) {
			channelPartner.setPartnerName(partnername);
			channelPartner.setLogo(logo);
			channelPartner.setTitle(title);
			flag = channelPartnerService.update(channelPartner);
			if (flag) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
	}
/**
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/modules/system//uploadpartner.htm")
	public void upload3(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		// 设置上传文件的大小，超过这个大小 将抛出IOException异常，默认大小是1M。
		int inmaxPostSize = 10 * 1024 * 1024;
		MultipartRequest multirequest = null;
		RenamePolicyCos myRenamePolicyCos = new RenamePolicyCos();
		HttpServletRequestProxy srp = new HttpServletRequestProxy(request);
		multirequest = new MultipartRequest(srp, getSaveDir(request).getAbsolutePath(), inmaxPostSize, "utf8",
				myRenamePolicyCos);

		Enumeration<String> filedFileNames = multirequest.getFileNames();
		logger.info("        upload:");
		logger.info(JsonUtil.toString(multirequest));

		// 返回数据
		Map<String, Object> result = new HashMap<String, Object>();

		if (null != filedFileNames && filedFileNames.hasMoreElements()) {
			String fieldName = filedFileNames.nextElement();

			File uploadFile = multirequest.getFile(fieldName);
			String uploadFilePath = uploadFile.getPath();
			File compressFile = new File(uploadFile.getParent() + "/thum_" + uploadFile.getName());
			compressFile.createNewFile();

			//图片压缩保存
			Thumbnails.of(uploadFile.getPath()).scale(0.25f).outputQuality(0.25f) .toFile(compressFile.getPath());
			String uri = com.company.common.utils.StringUtil.getRelativePath(compressFile, new File(request.	getRealPath("/")));
			//String uri = com.company.common.utils.StringUtil.getRelativePath(uploadFile,
			//		new File(request.getRealPath("/")));
			uploadFile.delete();
			compressFile.renameTo(new File(uploadFilePath));

			Map<String, Object> data = JSONObject.parseObject(multirequest.getParameter("data"), Map.class);

			PubAttachment attachment = new PubAttachment();
			attachment.setFilePath(uri);
			attachment.setFileName(multirequest.getOriginalFileName(fieldName));
			attachment.setNewfileName(compressFile.getName());
			attachment.setFileSize(new BigDecimal(compressFile.length()).divide(new BigDecimal(1024))
					.setScale(0, RoundingMode.CEILING).longValue());
			attachment.setOperatorId(getLoginUser(request).getId());
			attachment.setProcessInstanceId(Long.valueOf(data.get("processInstanceId").toString()));
			attachment.setProjectId(Long.valueOf(data.get("projectId").toString()));
			attachment.setBtype(data.get("btype").toString());
			Long id = pubAttachmentService.insert(attachment);

			result.put("id", attachment.getId());
			result.put("createtime", new Date());
			result.put("uri", uri);
		} else {
			throw new RuntimeException("没有文件可上传");
		}

		Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_DATA, result);
		res.put("success", "true");
		ServletUtils.writeToResponse(response, res);
	}
**/
}
