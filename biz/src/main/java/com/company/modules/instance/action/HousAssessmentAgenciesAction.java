package com.company.modules.instance.action;

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
import com.company.common.web.action.BaseAction;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.system.domain.SysUser;

/**
 * User:    wulb
 * DateTime:2016-08-06 02:33:32
 * details: 评估机构,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/instance/HousAssessmentAgenciesAction")
@Controller
public class HousAssessmentAgenciesAction extends BaseAction {

    /**
     * 评估机构的Service
     */
	@Autowired
	private HousAssessmentAgenciesService housAssessmentAgenciesService;
	@Autowired
	private PubAttachmentService attachmentService;

    /**
     * 评估机构表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housAssessmentAgencies	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousAssessmentAgencies.htm")
    public void saveOrUpdateHousAssessmentAgencies(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housAssessmentAgencies", required = false) String housAssessmentAgencies, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousAssessmentAgencies housAssessmentAgenciesInfo = new HousAssessmentAgencies();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housAssessmentAgencies)) {
			housAssessmentAgenciesInfo = JsonUtil.parse(housAssessmentAgencies, HousAssessmentAgencies.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housAssessmentAgenciesInfo.setCreator(sysUser.getId());
			housAssessmentAgenciesInfo.setCreateTime(new Date());
			influence = housAssessmentAgenciesService.insert(housAssessmentAgenciesInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housAssessmentAgenciesInfo.setModifier(sysUser.getId());
			housAssessmentAgenciesInfo.setModifyTime(new Date());
			influence = housAssessmentAgenciesService.update(housAssessmentAgenciesInfo);
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
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getHousAssessmentAgenciesList.htm")
    public void getHousAssessmentAgenciesList(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        PageHelper.startPage(currentPage, pageSize);
		List<HousAssessmentAgencies> housAssessmentAgenciess = housAssessmentAgenciesService.getPageListByMap(paramap);
		PageInfo<HousAssessmentAgencies> page = new PageInfo<HousAssessmentAgencies>(housAssessmentAgenciess);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 根据流程id查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId   流程id
     * @throws Exception
     */
    @RequestMapping("/getHousAssessmentAgenciesByProcessInstanceId.htm")
    public void getHousAssessmentAgenciesByProcessInstanceId(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(value = "processInstanceId", required = true) String processInstanceId) throws Exception{
    	// 返回给页面的Map
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	List<HousAssessmentAgencies> housAssessmentAgenciess = housAssessmentAgenciesService.getItemInfoByProcessInstanceId(processInstanceId);
    	returnMap.put(Constant.RESPONSE_DATA, housAssessmentAgenciess);
    	returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
    	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
    	// 返回给页面
    	ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
    * 根据id删除数据
    * @param response    页面的response
    * @param id          主键
    * @throws ServiceException
    */
    @RequestMapping("/deleteById.htm")
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id",required=true) Long id,
    		@RequestParam(name = "btype") String btype,
    		@RequestParam(name = "projectId") Long projectId,
    		@RequestParam(name = "processInstanceId") Integer processInstanceId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = housAssessmentAgenciesService.deleteById(id);
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("btype", btype);
		params.put("projectId", projectId);
		params.put("processInstanceId", processInstanceId);
		
		List<PubAttachment> files = attachmentService.select(params);
		if (CollectionUtils.isNotEmpty(files)) {
			for (PubAttachment pubAttachment : files) {
				attachmentService.delete(pubAttachment.getId().intValue());
			}
		}
		
		if(influence > 0){
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
}
