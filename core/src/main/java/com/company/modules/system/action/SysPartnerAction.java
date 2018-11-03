package com.company.modules.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.upload.CustomFileUpload;
import com.company.common.web.action.BaseAction;
import com.company.modules.system.domain.ChannelPartner;
import com.company.modules.system.domain.SysUploadInfo;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.ChannelPartnerService;
import com.company.modules.system.service.SysRoleService;
import com.company.modules.system.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class SysPartnerAction extends BaseAction {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ChannelPartnerService channelPartnerService;
	@Autowired
	private CustomFileUpload customFileUpload;

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/getPubPartnerInfo.htm")
	public void getPubPartnerInfo(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "partnerName") String partnerName) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> PartnerInfo = channelPartnerService.getLogoInfoByName(partnerName);
		if (!PartnerInfo.isEmpty()) {
			returnMap.put("logoUrl", PartnerInfo.get("logo"));
			returnMap.put("titleName", PartnerInfo.get("title"));
			returnMap.put("salesman", PartnerInfo.get("salesman"));
			returnMap.put("locality", PartnerInfo.get("locality"));
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
			@RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "partnerName") String partnerName,
			@RequestParam(value = "title") String title) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		if (!partnerName.isEmpty()) {
			paramap.put("partnerName", partnerName);
		}
		if (!title.isEmpty()) {
			paramap.put("title", title);
		}
		PageHelper.startPage(currentPage, pageSize);
		List<ChannelPartner> channelPartners = channelPartnerService.getPartnerList(paramap);
		PageInfo<ChannelPartner> page = new PageInfo<>(channelPartners);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	@RequestMapping("/modules/system/getPubPartnerlistForUser.htm")
	public void getPubPartnerlistForUser(HttpServletResponse response, HttpServletRequest request) throws Exception {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		List<Map<String, Object>> list = null;
		// 查询未被删除的渠道
		paramap.put("isDelete", 1);
		SysUser loginUser = getLoginUser(request);
		// 如果是渠道用戶，返回该渠道；如果是冠润，则返回全部渠道列表。
		paramap.put("channelPartnerId", loginUser.getChannelPartnerId());
		list = channelPartnerService.getPubPartnerlistForUser(paramap);
		returnMap.put(Constant.RESPONSE_DATA, list);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/getPubPartnerbyid.htm")
	public void getPubPartnerbyid(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "id") Integer id) throws Exception {

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
			@RequestParam(value = "partnerName", required = false) String partnerName,
			@RequestParam(value = "salesman", required = false) String salesman, @RequestParam(value = "locality", required = false) String locality,
			@RequestParam(value = "logo", required = false) String logo, @RequestParam(value = "title") String title)
			throws Exception {

		long influence = 0;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner = new ChannelPartner();
		channelPartner.setPartnerName(partnerName);
		channelPartner.setLogo(logo);
		channelPartner.setTitle(title);
		channelPartner.setSalesman(salesman);
		channelPartner.setLocality(locality);
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
			@RequestParam(value = "id") Integer id) throws Exception {

		boolean flag = false;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner = channelPartnerService.getInfoByID(id);
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
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "partnerName", required = false) String partnername,
			@RequestParam(value = "salesman", required = false) String salesman,
			@RequestParam(value = "locality", required = false) String locality,
			@RequestParam(value = "logo", required = false) String logo, @RequestParam(value = "title") String title)
			throws Exception {

		boolean flag = false;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ChannelPartner channelPartner = channelPartnerService.getInfoByID(id);
		if (channelPartner != null) {
			channelPartner.setPartnerName(partnername);
			channelPartner.setLogo(logo);
			channelPartner.setTitle(title);
			channelPartner.setSalesman(salesman);
			channelPartner.setLocality(locality);
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

	@SuppressWarnings("unchecked")
	@RequestMapping("/modules/system/uploadPubPartner.htm")
	public void uploadPubPartner(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		Map<String, Object> res = new HashMap<String, Object>();
		List<SysUploadInfo> sysUploadInfos = customFileUpload.doFileUpload(request, channelPartnerService.getLogoPath(),
				channelPartnerService.getLogoFileURL(), false);
		List<String> urls = new ArrayList<String>();
		if (sysUploadInfos != null && sysUploadInfos.size() > 0) {
			for (int i = 0; i < sysUploadInfos.size(); i++) {
				urls.add(sysUploadInfos.get(i).getUrl());
			}
			res.put("url", urls);
		} else {
			throw new RuntimeException("没有文件可上传");
		}
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put("success", "true");
		ServletUtils.writeToResponse(response, res);
	}

}
