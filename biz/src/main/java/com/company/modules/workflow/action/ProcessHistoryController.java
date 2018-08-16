package com.company.modules.workflow.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.UserRoleMapDataBean;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.controller.WorkflowBaseController;
import com.company.modules.workflow.service.RDZZHistoryService;

@Controller
@RequestMapping("/modules/workflow/controller/ProcessHistoryController")
public class ProcessHistoryController extends WorkflowBaseController {

	@Autowired
	private RDZZHistoryService historyService;
	
	/**
	 * @description  根据项目id查看指定流程实例的历史
	 * @param projectId
	 * @param vouchType
	 * @param start
	 * @param limit
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @author fzc
	 * @return void
	 * @since  1.0.0
	 */
	@RequestMapping("getHistory")
	public void getHistoryByProjectId(
			@RequestParam(required = true) String projectId,
			@RequestParam(required = true) int currentPage,
			@RequestParam(required = true) int pageSize,
			HttpServletRequest request,
			HttpServletResponse response) throws ServiceException {
		
		SysUser user = getLoginUser(request);
		
		SysRole role = getRoleForLoginUser(request);
		
		//默认车贷
		PageInfo<Map<String, Object>> pagedInfo = getPagedResultByProjectId(projectId,currentPage,pageSize,user,role);
	
		Map<String, Object> res = new HashMap<String, Object>();
		
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		res.put(Constant.RESPONSE_DATA, pagedInfo.getList());
		res.put(Constant.RESPONSE_DATA_TOTAL, pagedInfo.getTotal());
		
		ServletUtils.writeToResponse(response, res);
	}
	
	public PageInfo<Map<String, Object>> getPagedResultByProjectId(String projectId,int currentPage,int pageSize,SysUser user,SysRole role) throws ServiceException{
		PageInfo<Map<String, Object>> pageInfo = null;
		
		UserRoleMapDataBean urmDb = new UserRoleMapDataBean();
		
		if(urmDb.getCustomerMap().containsKey(role.getNid())){ //当前登录人为客服岗
			pageInfo = historyService.queryAllHistoricLoanProcessListToCustomerByProjectId(
					projectId,currentPage,pageSize);
		}else{
			pageInfo = historyService.queryAllHistoricLoanProcessListByProjectId(
					projectId,currentPage,pageSize);
		}
		for (Map<String, Object> map : pageInfo.getList()) {
			if(map.get("type")==null) {
				map.put("type","无审批意见");
			}else if(map.get("type").equals("pass")) {
				map.put("type","通过");
			}else if(map.get("type").equals("rejectProcess")){
				map.put("type","驳回");
			}
			else if(map.get("type").equals("refuse")){
				map.put("type","拒绝");
			}
		}
		return pageInfo;
	}
}
