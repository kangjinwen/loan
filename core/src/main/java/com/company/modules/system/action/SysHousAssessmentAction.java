package com.company.modules.system.action;

import java.text.SimpleDateFormat;
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
import com.company.common.utils.StringUtil;
import com.company.common.utils.upload.CustomFileUpload;
import com.company.common.web.action.BaseAction;
import com.company.modules.system.domain.HousPropertyAssessment;
import com.company.modules.system.domain.HousPropertyAttachment;
import com.company.modules.system.domain.SysUploadInfo;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysHousAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class SysHousAssessmentAction extends BaseAction {
	@Autowired
	private CustomFileUpload customFileUpload;

	@Autowired
	private SysHousAssessmentService sysHousAssessmentService;

	/**
	 * 获取房产评估列表
	 * 
	 * @param response
	 * @param request
	 * @param currentPage 分页参数，起始页 【Integer】 必传
	 * @param pageSize    分页参数，每页显示条数 【Integer】 必传
	 * @param             status：0代表待评估；1代表已评估；
	 * @response {"msg":"操作成功","code":200,"data":[{"id":9,"customerName":"test1","houseName":"house1","status":"0","createTime":"2018-09-17
	 *           09:46:00","isDelete":0},{"id":8,"customerName":"test2","houseName":"house2","status":"0","createTime":"2018-09-17
	 *           09:44:01","isDelete":0},{"id":3,"customerName":"test3","houseName":"house3","status":"0","createTime":"2018-09-11
	 *           10:07:14","isDelete":0}],"totalCount":3}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/getHousAssessmentList.htm")
	public void getHousAssessmentList(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "status") Integer status, @RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "houseName", required = false) String houseName,
			@RequestParam(value = "createTime", required = false) String createTime,
			@RequestParam(value = "isAssessment", required = false, defaultValue = "false") boolean isAssessment)
			throws Exception {
		try {
			Map<String, Object> res = new HashMap<String, Object>();
			PageInfo<Map<String, Object>> pagedInfo = null;
			Map<String, Object> paramap = new HashMap<>();
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			// False就按照当前用户来过滤 True就能看到全量的
			if (isAssessment) {
				pagedInfo = sysHousAssessmentService.queryHousPropertyAssessmentListByStatusOfAll(status, currentPage,
						pageSize, customerName, houseName, createTime);
				res.put(Constant.RESPONSE_DATA, pagedInfo.getList());
				res.put(Constant.RESPONSE_DATA_TOTAL, pagedInfo.getTotal());

			} else {
				PageHelper.startPage(currentPage, pageSize);
				SysUser loginUser = getLoginUser(request);
				String userName = loginUser.getUserName();
				paramap.put("userName", userName);
				List<String> coveredOffices = getCoverdOffices(loginUser);
				paramap.put("coveredOffices", coveredOffices);
				paramap.put("status", status);
				result = sysHousAssessmentService.getAssessmentList(paramap);
				PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(result);
				res.put(Constant.RESPONSE_DATA, page.getList());
				res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
			}
			res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id获取该条房产评估信息记录
	 * 
	 * @param response
	 * @param request
	 * @param 主键       id 主键id 【Integer】 必传
	 * @response {"msg":"操作成功","code":200,"data":[{"id":9,"customerName":"test1","houseName":"house1","status":"0","createTime":"2018-09-17
	 *           09:46:00","isDelete":0}]}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/getHousAssessmentInfoById.htm")
	public void getHousAssessmentInfoById(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "id") Integer id) throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if (id != null) {
				HousPropertyAssessment housPropertyAssessment = sysHousAssessmentService.getInfoByID(id);
				returnMap.put(Constant.RESPONSE_DATA, housPropertyAssessment);
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
				ServletUtils.writeToResponse(response, returnMap);
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 客户经理录入需要评估的房产基本信息
	 * 
	 * @param response
	 * @param request
	 * @param customerName 客户名称 【String】 必传
	 * @param houseName    房产名称 【String】 必传
	 * @param remark       备注 【String】
	 * @param ids          附件返回的id数组 必传
	 * @response {"msg":"操作成功","code":200,"id":1}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/insertHousAssessmentInfo.htm")
	public void insertHousAssessmentInfo(HttpServletResponse response, HttpServletRequest request,

			@RequestParam(value = "customerName", required = true) String customerName,

			@RequestParam(value = "houseName", required = true) String houseName,

			@RequestParam(value = "remark", required = false) String remark,

			@RequestParam(value = "ids[]", required = false) long[] ids) throws Exception {

		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			HousPropertyAssessment record = new HousPropertyAssessment();
			if (StringUtil.isEmpty(customerName) & StringUtil.isEmpty(houseName)) {
			} else {
				record.setCustomerName(customerName);
				record.setHouseName(houseName);
				record.setRemark(remark);
				record.setCreatorId(Integer.parseInt(String.valueOf(getLoginUser(request).getId())));
				record.setCreateTime(getNow());
			}
			int res = sysHousAssessmentService.insertHousPropertyAssessmentInfoSelective(record);
			if (res < 1) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			} else {
				int rst = sysHousAssessmentService.updateHousPropertyAttachmentByPropertyAssessmentId(record.getId(),
						ids);
				if (rst > 0) {
					returnMap.put("id", record.getId());
					returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
					returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
				} else {
					returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
					returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
				}
			}
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id删除该条房产价值评估记录。
	 * 
	 * @param response
	 * @param request
	 * @param id       主键 id 【Integer】必传
	 * @response {"msg":"操作成功","code":200,"id":1}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/deleteHousAssessmentInfo.htm")
	public void deleteHousAssessmentInfo(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "id") Integer id) throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			HousPropertyAssessment record = new HousPropertyAssessment();
			if (id != null) {
				record.setId(id);
				record.setIsDelete(1);
			}
			int res = sysHousAssessmentService.updateHousPropertyAssessmentInfoByID(record);
			if (res > 0) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修改房产评估记录
	 * 
	 * @param response
	 * @param request
	 * @param id           主键id 【Integer】
	 * @param customerName 客户名称 【String】
	 * @param houseName    房产名称 【String】
	 * @param unitPrice    单价 【Integer】
	 * @param totalPrice   总价 【Integer】
	 * @param status       状态 【Integer】
	 * @param assesserId   评估人员id 【Integer】
	 * @param remark       备注 【String】
	 * @response {"msg":"操作成功","code":200,"id":1}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/updateHousAssessmentInfo.htm")
	public void updateHousAssessmentInfo(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "houseName", required = false) String houseName,
			@RequestParam(value = "unitPrice", required = false) Integer unitPrice,
			@RequestParam(value = "totalPrice", required = false) Integer totalPrice,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "assesserId", required = false) Integer assesserId,
			@RequestParam(value = "remark", required = false) String remark) throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			HousPropertyAssessment record = new HousPropertyAssessment();
			record.setId(id);
			record.setCustomerName(customerName);
			record.setHouseName(houseName);
			record.setUnitPrice(unitPrice);
			record.setTotalPrice(totalPrice);
			record.setStatus(status);
			record.setAssesserId(Integer.parseInt(String.valueOf(getLoginUser(request).getId())));
			record.setRemark(remark);
			int res = sysHousAssessmentService.updateHousPropertyAssessmentInfoByID(record);
			if (res > 0) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 给出房产价值评估与备注
	 * 
	 * @param response
	 * @param request
	 * @param id         主键id 【Integer】 必传
	 * @param unitPrice  单价 【Integer】
	 * @param totalPrice 总价 【Integer】
	 * @param remark     备注 【String】
	 * @response {"msg":"操作成功","code":200,"id":1}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/assessHousProperty.htm")
	public void assessHousProperty(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "unitPrice", required = false) Integer unitPrice,
			@RequestParam(value = "totalPrice", required = false) Integer totalPrice,
			@RequestParam(value = "remark", required = false) String remark) throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			HousPropertyAssessment record = new HousPropertyAssessment();
			if ((unitPrice == null) & (totalPrice == null)) {
			} else {
				record.setStatus(1);
			}
			record.setId(id);
			record.setUnitPrice(unitPrice);
			record.setTotalPrice(totalPrice);
			record.setRemark(remark);
			int res = sysHousAssessmentService.updateHousPropertyAssessmentInfoByID(record);
			if (res > 0) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传房产附件信息
	 * 
	 * @param request
	 * @param response
	 * @response {"msg":"操作成功","code":200,"id":1,"fileName":"附件名","filePath":"http://192.168.1.136:8081/fileLogo/2018-09/WechatIMG786_2018_09_11_10_07_01_895.png"}
	 * @throws Exception
	 */
	@RequestMapping("/modules/system/uploadHousAttachment.htm")
	public void uploadHousAttachment(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			List<SysUploadInfo> sysUploadInfos = customFileUpload.doFileUpload(request,
					sysHousAssessmentService.getUploadPath(), sysHousAssessmentService.getUploadFileURL(), true);
			if (sysUploadInfos != null && sysUploadInfos.size() > 0) {
				for (int i = 0; i < sysUploadInfos.size(); i++) {
					HousPropertyAttachment housPropertyAttachment = new HousPropertyAttachment();
					housPropertyAttachment.setFileName(sysUploadInfos.get(i).getOriginaName());
					housPropertyAttachment.setFilePath(sysUploadInfos.get(i).getUrl());
					housPropertyAttachment.setCreateTime(getNow());
					sysHousAssessmentService.insertHousPropertyAttachmentInfoSelective(housPropertyAttachment);
					result.put("id", housPropertyAttachment.getId());
					result.put("fileName", sysUploadInfos.get(i).getOriginaName());
					result.put("createtime", new Date());
					result.put("url", sysUploadInfos.get(i).getUrl());
				}
			} else {
				throw new RuntimeException("没有文件可上传");
			}
			Map<String, Object> res = new HashMap<String, Object>();
			res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			res.put(Constant.RESPONSE_DATA, result);
			res.put("success", "true");
			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据主键id删除房产附件信息
	 * 
	 * @param request
	 * @param response
	 * @param id       主键id 【Integer】 必传
	 * @response {"msg":"操作成功","code":200,"id":1}
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/system/deleteAttachment.htm")
	public void deleteAttachment(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			HousPropertyAttachment record = new HousPropertyAttachment();
			if (id != null) {
				record.setId(id);
				record.setIsDelete(1);
			}
			int res = sysHousAssessmentService.updateHousPropertyAttachmentInfoByID(record);
			if (res > 0) {
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据propertyAssessmentId查询全部房产附件信息
	 * 
	 * @param request
	 * @param response
	 * @param propertyAssessmentId 房产信息id 【Integer】 必传
	 * @response {"msg":"操作成功","code":200,"data":[{"id":1,"propertyAssessmentId":1,"fileName":"附件名","filePath":"http://192.168.1.136:8081/fileLogo/2018-09/WechatIMG786_2018_09_11_10_07_01_895.png"}],"totalCount":3}
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/system/quaryAllAttachment.htm")
	public void queryAllAttachment(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "propertyAssessmentId", required = true) Integer propertyAssessmentId)
			throws Exception {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			List<Map<String, Object>> files = sysHousAssessmentService
					.selectPropertyAttachmentInfoBypropertyAssessmentId(propertyAssessmentId);
			if (files != null && !files.isEmpty()) {
				returnMap.put(Constant.RESPONSE_DATA, files);
				returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			} else {
				returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
			}
			ServletUtils.writeToResponse(response, returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获得当前日期格式
	 * 
	 * @return 2018-09-19 14:42:2
	 */
	public static String getNow() {
		SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = format0.format(date.getTime());// 这个就是把时间戳经过处理得到期望格式的时间
		return time;

	}
}
