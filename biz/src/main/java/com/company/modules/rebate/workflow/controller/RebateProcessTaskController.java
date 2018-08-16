package com.company.modules.rebate.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.modules.rebate.workflow.service.RebateTaskService;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.controller.WorkflowBaseController;


@Controller
@RequestMapping("/modules/rebate/RebateProcessTaskController")
public class RebateProcessTaskController extends WorkflowBaseController{
	
	@Autowired
	
	private RebateTaskService rebateTaskService;
	 /**
     * 返佣审核任务查询
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getRebateAuditTasks.htm")
    public void getRebateAuditTasks(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(required = true) boolean isCompleted,
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
        paramap.put("isCompleted", isCompleted);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		List<String> coverdOffices = getCoverdOffices(loginUser);

		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("roleNid", getRoleForLoginUser(request).getNid());
		paramap.put("currentPage",currentPage);
		paramap.put("pageSize", pageSize);
        PageInfo<Map<String,Object>> page=rebateTaskService.getRebateAuditPageList(paramap);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 待返佣查询
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getRebateHandleTasks.htm")
    public void getRebateHandleTasks(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(required = true) boolean isCompleted,
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
        paramap.put("isCompleted", isCompleted);
        
    	SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		List<String> coverdOffices = getCoverdOffices(loginUser);

		Long roleId = getRoleId(request);
		paramap.put("coverdOffices", coverdOffices);
		paramap.put("userName", userName);
		paramap.put("roleId", roleId);
		paramap.put("roleNid", getRoleForLoginUser(request).getNid());
		paramap.put("currentPage",currentPage);
		paramap.put("pageSize", pageSize);
        PageInfo<Map<String,Object>> page=rebateTaskService.getRebateHandlePageList(paramap);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    

}
