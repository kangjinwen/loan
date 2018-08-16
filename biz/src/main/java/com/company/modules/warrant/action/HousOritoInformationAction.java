package com.company.modules.warrant.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
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
import com.company.modules.common.exception.ServiceException;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.dao.SysUserRoleDao;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysRoleService;
import com.company.modules.warrant.domain.HousDataList;
import com.company.modules.warrant.domain.HousIntermediaryInformation;
import com.company.modules.warrant.domain.HousOritoInformation;
import com.company.modules.warrant.domain.HousQuickInformation;
import com.company.modules.warrant.service.HousDataListService;
import com.company.modules.warrant.service.HousIntermediaryInformationService;
import com.company.modules.warrant.service.HousOritoInformationService;
import com.company.modules.warrant.service.HousQuickInformationService;
import com.company.modules.warrant.service.HouseholdInvestigateService;
import com.company.modules.warrant.util.databean.HouseholdInvestigateDataBean;

/**
 * User:    fzc
 * DateTime:2016-08-10 03:35:36
 * details: 下户信息表(权证下户),Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/warrant/HousOritoInformationAction")
@Controller
public class HousOritoInformationAction extends BaseAction {

    /**
     * 下户信息表(权证下户)的Service
     */
	@Autowired
	private HousOritoInformationService housOritoInformationService;
	/**
     * 房屋快出值信息表(权证下户)的Service
     */
	@Autowired
	private HousQuickInformationService housQuickInformationService;
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;
	/**
     * 房屋中介信息(权证下户)的Service
     */
	@Autowired
	private HousIntermediaryInformationService housIntermediaryInformationService;
	/**
     * 资料清单表(权证下户)的Service
     */
	@Autowired
	private HousDataListService housDataListService;
	
	@Autowired
	private HouseholdInvestigateService householdInvestigateService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;   
	
	@Autowired
	private SysUserDao sysUserDao; 
	
	@Autowired
	private TaskService taskServiceReturnFee; 
	
	

    /**
     * 下户信息表(权证下户)表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housOritoInformation	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousOritoInformation.htm")
    public void saveOrUpdateHousOritoInformation(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housOritoInformation", required = false) String housOritoInformation, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousOritoInformation housOritoInformationInfo = new HousOritoInformation();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housOritoInformation)) {
			housOritoInformationInfo = JsonUtil.parse(housOritoInformation, HousOritoInformation.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housOritoInformationInfo.setCreator(sysUser.getId());
			housOritoInformationInfo.setCreateTime(new Date());
			influence = housOritoInformationService.insert(housOritoInformationInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housOritoInformationInfo.setModifier(sysUser.getId());
			housOritoInformationInfo.setModifyTime(new Date());
			influence = housOritoInformationService.update(housOritoInformationInfo);
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
    @RequestMapping("/getHousOritoInformationList.htm")
    public void getHousOritoInformationList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousOritoInformation> housOritoInformations = housOritoInformationService.getPageListByMap(paramap);
		PageInfo<HousOritoInformation> page = new PageInfo<HousOritoInformation>(housOritoInformations);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
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
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id") long id) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = housOritoInformationService.deleteById(id);
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
    
    /**
     * 根据实例id获取下户数据
     * @param response    页面的response
     * @param id          主键
     * @throws ServiceException
     */
     @RequestMapping("/getHousInfo.htm")
     public void getHousInfo(
    		 HttpServletRequest request,
    		 HttpServletResponse response, 
    		 @RequestParam(value = "processInstanceId") String processInstanceId,
    		 @RequestParam(value = "taskId") String taskId) throws Exception{
    	 Map<String, Object> returnMap = new HashMap<String, Object>();
    	 Map<String, Object> result = new HashMap<String, Object>();
    	 HousOritoInformation housOritoInformation = housOritoInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	 HousQuickInformation housQuickInformation = housQuickInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	 HousIntermediaryInformation housIntermediaryInformation = housIntermediaryInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	 HousDataList housDataList = housDataListService.getItemInfoByProcessInstanceId(processInstanceId);
    	 HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	 
    	 if(!Objects.equals(null, housOritoInformation)){
	    	 if(Objects.equals(null, housOritoInformation.getInvestigator())){	 
	    		
	    	 }
	    	 result.put("housOritoInformation", housOritoInformation);
    	 }else{
    		/* housOritoInformation =new HousOritoInformation();
    		 housOritoInformation.setInvestigator(getSysUser().getName());*/
    		 Map<String,Object> housOritoInformationMap = new HashMap<String,Object>();    		
    		 //Task task = taskServiceReturnFee.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    		 Task task = taskServiceReturnFee.createTaskQuery().taskId(taskId).singleResult();
    		//得到当前流程的任务处理人
 			 String nextAssignee = task.getAssignee();
 			 SysUser user = sysUserDao.getUserByUserName(nextAssignee);
    		 housOritoInformationMap.put("investigator", user.getName());
    		 housOritoInformationMap.put("propertyAddressId", housPropertyInformation.getPropertyAddressId());
    		 housOritoInformationMap.put("propertyAddress", housPropertyInformation.getPropertyAddress());
    		 result.put("housOritoInformation", housOritoInformationMap);
    	 }
    	 
    	 result.put("housQuickInformation", housQuickInformation);
    	 result.put("housIntermediaryInformation", housIntermediaryInformation);
    	 result.put("housDataList", housDataList);
    	 result.put("housPropertyInformation", housPropertyInformation);
    	 
    	//返回给页面
    	 returnMap.put(Constant.RESPONSE_DATA, result);
    	 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
 		 ServletUtils.writeToResponse(response, returnMap);
    	 
     }
     
     /**
      * 核行弹窗调用这个（分开来，逻辑清楚些,只用处理核行）
      * @param response    页面的response
      * @param id          主键
      * @throws ServiceException
      */
      @RequestMapping("/getHousInfoQuickInformation.htm")
      public void getHousInfoQuickInformation(
     		 HttpServletRequest request,
     		 HttpServletResponse response, 
     		 @RequestParam(value = "processInstanceId") String processInstanceId,
     		 @RequestParam(value = "taskId") String taskId) throws Exception{
     	 Map<String, Object> returnMap = new HashMap<String, Object>();
     	 Map<String, Object> result = new HashMap<String, Object>();
     	 HousOritoInformation housOritoInformation = housOritoInformationService.getItemInfoByProcessInstanceId(processInstanceId);
     	 HousQuickInformation housQuickInformation = housQuickInformationService.getItemInfoByProcessInstanceId(processInstanceId);
     	 HousIntermediaryInformation housIntermediaryInformation = housIntermediaryInformationService.getItemInfoByProcessInstanceId(processInstanceId);
     	 HousDataList housDataList = housDataListService.getItemInfoByProcessInstanceId(processInstanceId);
     	 HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
     	 
     	 if(!Objects.equals(null, housQuickInformation)){
 	    	 if(Objects.equals(null, housQuickInformation.getInvestigator())){	 
 	    		
 	    	 }
 	    	 result.put("housQuickInformation", housQuickInformation);
     	 }else{
     		/* housOritoInformation =new HousOritoInformation();
     		 housOritoInformation.setInvestigator(getSysUser().getName());*/
     		 Map<String,Object> housQuickInformationMap = new HashMap<String,Object>();    		
     		 //Task task = taskServiceReturnFee.createTaskQuery().processInstanceId(processInstanceId).singleResult();
     		 Task task = taskServiceReturnFee.createTaskQuery().taskId(taskId).singleResult();
     		//得到当前流程的任务处理人
  			 String nextAssignee = task.getAssignee();
  			 SysUser user = sysUserDao.getUserByUserName(nextAssignee);
  			housQuickInformationMap.put("investigator", user.getName());
  			housQuickInformationMap.put("propertyAddressId", housPropertyInformation.getPropertyAddressId());
  			housQuickInformationMap.put("propertyAddress", housPropertyInformation.getPropertyAddress());
     		 result.put("housQuickInformation", housQuickInformationMap);
     	 }
     	 
     	 result.put("housIntermediaryInformation", housIntermediaryInformation);
     	 result.put("housDataList", housDataList);
     	 result.put("housPropertyInformation", housPropertyInformation);
     	 
     	//返回给页面
     	 returnMap.put(Constant.RESPONSE_DATA, result);
     	 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
 		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
  		 ServletUtils.writeToResponse(response, returnMap);
     	 
      }
     
     
     /**
      *保存下户信息草稿
      * @param response    页面的response
      * @param id          主键
      * @throws ServiceException
      */
      @RequestMapping("/saveDraft.htm")
      public void saveDraft(
     		 HttpServletRequest request,
     		 HttpServletResponse response, 
     		 @RequestParam(value = "searchParams") String searchParams) throws Exception{
    	  HouseholdInvestigateDataBean householdInvestigateDataBean = new HouseholdInvestigateDataBean();
    	  //对json对象进行转换
          if (!StringUtils.isEmpty(searchParams)){
        	  householdInvestigateDataBean = JsonUtil.parseWithOnlyYMDDate(searchParams, HouseholdInvestigateDataBean.class);
          }
    	  
    	 boolean flag = householdInvestigateService.insertOrUpdateHousinfo(householdInvestigateDataBean);
    	 Map<String, Object> returnMap = new HashMap<String, Object>();
    	 if(flag){
    		 returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
     		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
    	 }else{
    		 returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
     		 returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
    	 }
  		 ServletUtils.writeToResponse(response, returnMap);
      }
}
