package com.company.modules.supplymaterial.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.supplymaterial.domain.PubSupplymaterial;
import com.company.modules.supplymaterial.domain.PubSupplymaterialLog;
import com.company.modules.supplymaterial.service.PubSupplyMaterialService;
import com.company.modules.supplymaterial.service.PubSupplymaterialLogService;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.controller.WorkflowBaseController;

/**
 * User:    JDM
 * DateTime:2016-08-17 11:31:42
 * details: 补充资料,Action请求层
 * source:  代码生成器
 */
@SuppressWarnings({ "all" })
@RequestMapping("/modules/supplymaterial/SupplyMaterialAction")
@Controller
public class PubSupplyMaterialAction extends WorkflowBaseController {
	
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	static{
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
	}
	

    /**
     * 补充资料的Service
     */
	@Autowired
	private PubSupplyMaterialService pubSupplymaterialService;
	@Autowired
	private PubSupplymaterialLogService pubSupplymaterialLogService;
	@Autowired
	private PubAttachmentService attachmentService;
	
    /**
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @RequestMapping("/getSupplyMaterialPage.htm")
    public void getSupplyMaterialPage(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
		@RequestParam(value = "isCompleted") Boolean isCompleted,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        SysUser loginUser = getLoginUser(request);
//        List<String> coverdOffices = getCoverdOffices(loginUser);
//        paramap.put("coverdOffices", coverdOffices);
//        paramap.put("userName", getLoginUserName(request));
//        paramap.put("roleId", getRoleId(request));
        
        PageHelper.startPage(currentPage, pageSize);
        List<Map> supplyMaterials = pubSupplymaterialService.getPageListByMap(paramap,isCompleted);
        if (CollectionUtils.isNotEmpty(supplyMaterials)) {
			for (Map map : supplyMaterials) {
				map.put("executor", loginUser.getName());
			}
		}
		PageInfo<Map> page = new PageInfo<Map>(supplyMaterials);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 根据项目id获取补充资料
     * @param response
     * @param request
     * @param supplyMaterialId
     * @throws Exception
     */
    @RequestMapping("/getSupplyMaterial.htm")
    public void getSupplyMaterial(HttpServletResponse response, HttpServletRequest request,
  		@RequestParam(value = "projectId", required = true) Long projectId) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectId", projectId);
        
		PubSupplymaterial supplyMaterial = pubSupplymaterialService.getItemByProjectId(projectId);
		returnMap.put(Constant.RESPONSE_DATA, supplyMaterial);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 补充资料表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param PubSupplymaterial	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveSupplyMaterial.htm")
    public void saveSupplyMaterial(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "supplyMaterial", required = false) String supplyMaterial) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		PubSupplymaterial supplyMaterialInfo = new PubSupplymaterial();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(supplyMaterial)) {
			supplyMaterialInfo = JsonUtil.parse(supplyMaterial, PubSupplymaterial.class);
		}
		supplyMaterialInfo.setOperatorId(sysUser.getId());
		
		//他项材料
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("btype", "MATERIAL");
		params.put("projectId", supplyMaterialInfo.getProjectId());
		List<PubAttachment> otherfiles = attachmentService.select(params);
		if (CollectionUtils.isNotEmpty(otherfiles)) {
			supplyMaterialInfo.setOtherFileUploaded(true);
		} else {
			supplyMaterialInfo.setOtherFileUploaded(false);
		}
		// 公证材料
		params.put("btype", "JUSTICE");
		List<PubAttachment> notarizationfiles = attachmentService.select(params);
		if (CollectionUtils.isNotEmpty(notarizationfiles)) {
			supplyMaterialInfo.setNotarizationFileUploaded(true);
		} else {
			supplyMaterialInfo.setNotarizationFileUploaded(false);
		}
		
		PubSupplymaterial dbSupplymaterial = pubSupplymaterialService.getItemByProjectId(supplyMaterialInfo.getProjectId());
		if (dbSupplymaterial != null) {
			supplyMaterialInfo.setId(dbSupplymaterial.getId());
			influence = pubSupplymaterialService.update(supplyMaterialInfo);
		} else {
			supplyMaterialInfo.setCreateTime(new Date());
			influence = pubSupplymaterialService.insert(supplyMaterialInfo);
		}
		if (influence > 0) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 根据补充资料id获取补充资料日志
     * @param response
     * @param request
     * @param supplyMaterialId
     * @throws Exception
     */
    @RequestMapping("/getSupplyMaterialLogList.htm")
    public void getSupplyMaterialLogList(HttpServletResponse response, HttpServletRequest request,
  		@RequestParam(value = "projectId", required = true) Long projectId) throws Exception{
    	
    	PubSupplymaterial dbSupplymaterial = pubSupplymaterialService.getItemByProjectId(projectId);
    	
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("supplymaterialId", dbSupplymaterial.getId());
        
		List<PubSupplymaterialLog> logList = pubSupplymaterialLogService.getItemList(paramMap);
		returnMap.put(Constant.RESPONSE_DATA, logList);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
}
