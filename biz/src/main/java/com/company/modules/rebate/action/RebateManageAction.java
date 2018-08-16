package com.company.modules.rebate.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.rebate.service.RebateManageService;
import com.company.modules.system.domain.SysUser;


/**
 * User:    mcwang
 * DateTime:2016-08-10 03:59:46
 * details: 返佣管理,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/rebate/RebateManageAction")
@Controller
public class RebateManageAction extends BaseAction{
	@Autowired
	RebateManageService rebateManageService;
	
	
	 /**
     * 查询基础信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getBorrowBasicDataByInstanceId.htm")
    public void getBorrowBasicDataByInstanceId(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId,
		@RequestParam(value = "period") String period
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data=null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processInstanceId", processInstanceId);
		params.put("period", period);
		
		data = rebateManageService.getBorrowBasicDataByInstanceId(params);
		
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 审核是查询审核信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getRebateAuditDataByInstanceId.htm")
    public void getRebateAuditDataByInstanceId(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data=null;
		
		data = rebateManageService.getRebateAuditDataByInstanceId(processInstanceId);
		
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    
    /**
     * 返佣时查询审核信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId  查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getRebateHandleDataByInstanceId.htm")
    public void getRebateHandleDataByInstanceId(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "processInstanceId") String processInstanceId
		) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> data=null;
		
		SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		loginUser.getId();
		
		data = rebateManageService.getRebateHandleDataByInstanceId(processInstanceId);
	if(!data.containsKey("financialStaff"))  //取当前登录者为财务专员
		data.put("financialStaff",loginUser.getName());
		returnMap.put(Constant.RESPONSE_DATA, data);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }

}
