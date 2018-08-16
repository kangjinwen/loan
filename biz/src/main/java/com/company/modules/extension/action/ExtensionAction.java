package com.company.modules.extension.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.collateral.service.CollateralManageService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.RMB;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.domain.SysUser;

/**
 * User:    gaoshan
 * DateTime:2016-09-14 11:54:13
 * details: 展期,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/extension/ExtensionAction")
@Controller
public class ExtensionAction extends BaseAction {
   
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	@Autowired
	private TaskService taskServiceReturnFee; 
	@Autowired
	private SysUserDao sysUserDao; 
	/**
     * 押品登记表的Service
     */
	@Autowired
	private CollateralManageService collateralManageService;
	/**
     * 根据实例id获取展期费数据
     * @param response    页面的response
     * @param id          主键
     * @throws ServiceException
     */
     @RequestMapping("/getHousExtendedFee.htm")
     public void getHousExtendedFee(
    		 HttpServletRequest request,
    		 HttpServletResponse response, 
    		 @RequestParam(value = "newProcessInstanceId") String newProcessInstanceId,
    		 @RequestParam(value = "processInstanceId") String processInstanceId) throws Exception{    		
    	 Map<String, Object> returnMap = new HashMap<String, Object>();
    	 Map<String, Object> result = new HashMap<String, Object>();
    	 Map<String, Object> data = new HashMap<String, Object>();
    	 data.put("branchingProcessId", newProcessInstanceId);
    	 data.put("processInstanceId", processInstanceId);
    	 PubProcessBranching pubProcessBranching = pubProcessBranchingDao.getItemByMap(data);    	
    	 if(!Objects.equals(null, pubProcessBranching.getFinanceSpecialist())){	    	
	    	 result.put("pubProcessBranching", pubProcessBranching);
    	 }else{    		
    		 Task task = taskServiceReturnFee.createTaskQuery().processInstanceId(newProcessInstanceId).singleResult();
    		//得到当前流程的任务处理人
 			 String nextAssignee = task.getAssignee();
 			 SysUser user = sysUserDao.getUserByUserName(nextAssignee);
 			 pubProcessBranching.setFinanceSpecialist(user.getName());
    		 result.put("pubProcessBranching", pubProcessBranching);
    	 }    	 
    	//返回给页面
    	 returnMap.put(Constant.RESPONSE_DATA, result);
    	 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
 		 ServletUtils.writeToResponse(response, returnMap);
    	 
     }
     
     /**
      * 根据实例id获取展期划扣数据
      * @param response    页面的response
      * @param id          主键
      * @throws ServiceException
      */
      @RequestMapping("/getHousDeduct.htm")
      public void getHousDeduct(
     		 HttpServletRequest request,
     		 HttpServletResponse response, 
     		 @RequestParam(value = "newProcessInstanceId") String newProcessInstanceId,
     		 @RequestParam(value = "processInstanceId") String processInstanceId) throws Exception{    		
     	 Map<String, Object> returnMap = new HashMap<String, Object>();
     	 Map<String, Object> result = new HashMap<String, Object>();
     	 Map<String, Object> param = new HashMap<String, Object>();
     	 Map<String, Object> data = new HashMap<String, Object>();
     	 param.put("branchingProcessId", newProcessInstanceId);
     	 param.put("processInstanceId", processInstanceId);
     	 PubProcessBranching pubProcessBranching = pubProcessBranchingDao.getItemByMap(param);    	
 	     result.put("pubProcessBranching", pubProcessBranching);
 	     if (pubProcessBranching.getExtensionAmount()!=null) {
 	    	 BigDecimal account=pubProcessBranching.getExtensionAmount();
             String big = RMB.toBigAmt(account.doubleValue());
             result.put("extensionAmountChinese", big);
		}
 	    data = collateralManageService.getCollateralRegistData(newProcessInstanceId); 
 	   result.put("data", data);
 	     
 	     
 	     
     	//返回给页面
     	 returnMap.put(Constant.RESPONSE_DATA, result);
     	 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
 		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
  		 ServletUtils.writeToResponse(response, returnMap);
     	 
      }

}
