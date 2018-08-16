package com.company.modules.workflow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.parser.impl.DefaultClassTypeParser;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysRoleService;
import com.company.modules.workflow.service.RDTaskService;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

@Controller
@RequestMapping("/modules/workflow/controller/ProcessTaskController")
public class ProcessTaskController extends WorkflowBaseController {

	@Autowired
	private RDTaskService taskService;
	@Autowired
	private TaskService taskServiceReturnFee;   
	@Autowired
	private PubLoanprocessDao pubLoanprocessDao; 
	@Autowired
	 protected RepositoryService repositoryService;
	@Autowired
	private SysRoleService sysRoleService;

	private DefaultClassTypeParser defaultClassTypeParser = new DefaultClassTypeParser();

	/**
	 * 通用任务处理节点，如审核通过，审核拒绝
	 * 
	 * @param taskId
	 * @param serviceVariables
	 *            业务相关参数
	 * @param processVariables
	 *            流程相关参数
	 * @param request
	 * @param response
	 * @throws ServiceException
	 */
	@RequestMapping(value = "completeTask")
	public void completeTask(
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "serviceVariables", required = false) String serviceVariables,
			@RequestParam(value = "processVariables", required = false) String processVariables,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceException {
		TaskAssignerCenter.taskAssignee.remove();
		Map<String, Object> processVarMap = JsonUtil.parse(processVariables, Map.class);
		Map<String, Object> serviceVarMap = JsonUtil.parse(serviceVariables, Map.class);
		Map<String, Object> bpmVariables = new HashMap<String, Object>();

		SysUser loginUser = getLoginUser(request);
		SysRole role = getRoleForLoginUser(request);
		// 准备bpm变量集合
		prepareServiceParams(serviceVariables, bpmVariables);
		prepareProcessParams(processVarMap, bpmVariables);
		prepareLoginInfoParams(loginUser, bpmVariables);
		prepareRoleInfoParams(role, bpmVariables);
		
		taskService.completeTask(taskId, loginUser.getUserName(),serviceVarMap,bpmVariables);
		//taskService.completeOrgaFirstTask(taskId, loginUser.getUserName(),serviceVarMap,bpmVariables);
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		TaskAssignerCenter.isNew = false;
		TaskAssignerCenter.taskAssignee.remove();
		ServletUtils.writeToResponse(response, res);
	}


	/**
	 * 机构初审任务执行节点（因业务需求，需要把机构初审之后，下个任务节点经办人指向发起者（即 下户时候的经办人））
	 * @param taskId
	 * @param serviceVariables
	 * @param processVariables
	 * @param request
	 * @param response
	 * @throws ServiceException
	 */
	@RequestMapping(value = "completeOrgaFirstTask")
	public void completeOrgaFirstTask(
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "serviceVariables", required = false) String serviceVariables,
			@RequestParam(value = "processVariables", required = false) String processVariables,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceException {
		TaskAssignerCenter.taskAssignee.remove();
		Map<String, Object> processVarMap = JsonUtil.parse(processVariables, Map.class);
		Map<String, Object> serviceVarMap = JsonUtil.parse(serviceVariables, Map.class);
		Map<String, Object> bpmVariables = new HashMap<String, Object>();

		SysUser loginUser = getLoginUser(request);
		SysRole role = getRoleForLoginUser(request);
		// 准备bpm变量集合
		prepareServiceParams(serviceVariables, bpmVariables);
		prepareProcessParams(processVarMap, bpmVariables);
		prepareLoginInfoParams(loginUser, bpmVariables);
		prepareRoleInfoParams(role, bpmVariables);

		taskService.completeOrgaFirstTask(taskId, loginUser.getUserName(),serviceVarMap,bpmVariables);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		TaskAssignerCenter.isNew = false;
		TaskAssignerCenter.taskAssignee.remove();
		ServletUtils.writeToResponse(response, res);
	}
	/**
	 * 贷款终结退费终结主流程(正常退费主流程不影响)
	 * 
	 * @param taskId
	 * @param serviceVariables
	 *            业务相关参数
	 * @param processVariables
	 *            流程相关参数
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "completeTaskReturnFee")
	public void completeTaskReturnFee(
			@RequestParam(value = "taskId", required = false) String taskId,
			@RequestParam(value = "serviceVariables", required = false) String serviceVariables,
			@RequestParam(value = "processVariables", required = false) String processVariables,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskAssignerCenter.taskAssignee.remove();
		Map<String, Object> processVarMap = defaultClassTypeParser.parse(
				processVariables, Map.class);
		Map<String, Object> serviceVarMap = defaultClassTypeParser.parse(
				serviceVariables, Map.class);
		Map<String,Object> variables = new HashMap<>();
		Map<String,Object> mapVariables = new HashMap<>();
		String oldProcessInstanceId = (String)serviceVarMap.get("oldProcessInstanceId");
		PubLoanprocess pubLoanprocess = pubLoanprocessDao.getByTaskId((String)serviceVarMap.get("processInstanceId"));
		if (pubLoanprocess.getType().equals("endReturnFee")) {//贷款终结退费，终止主流程	
			Task task = taskServiceReturnFee.createTaskQuery().processInstanceId(oldProcessInstanceId).singleResult();				
			variables.put("remarkComment", "退费终止");
			variables.put("consultId", serviceVarMap.get("consultId"));
			variables.put("nextStep", "endReturnFee");
			variables.put("processStateCode",serviceVarMap.get("processStateCode"));
			variables.put("projectId", serviceVarMap.get("projectId"));
			variables.put("processInstanceId",oldProcessInstanceId);
			variables.put("comment", "endReturnFee");
			String strvariables = JsonUtil.toString(variables);
			mapVariables.put(SystemConstant.SERVICE_VARIABLES, strvariables);
			if (task!=null) {				
				ActivityImpl endActivity = findActivitiImpl(task.getId(), "end");  
				commitProcess(task.getId(), mapVariables, endActivity.getId());  
			}
			//taskServiceReturnFee.complete(task.getId(),mapVariables);//这个方法不知道为什么不行
	}
		Map<String, Object> bpmVariables = new HashMap<String, Object>();

		// TODO FHJ,如果什么东西都是需要前台往后台传，那就可以进行作弊，这个方法需要在后台进行调整，影响流程的参数传递需要慎重考虑！
		// 这里采用统一规范，优先去borrow表查amount,如果没有，查borrow_requrement，把这个amount做为流程变量使用，前台则不用传值，防止作弊
		SysUser loginUser = getLoginUser(request);

		SysRole role = getRoleForLoginUser(request);
		// 准备bpm变量集合
		prepareServiceParams(serviceVariables, bpmVariables);
		prepareProcessParams(processVarMap, bpmVariables);
		prepareLoginInfoParams(loginUser, bpmVariables);
		prepareRoleInfoParams(role, bpmVariables);

		taskService.completeTask(taskId, loginUser.getUserName(), bpmVariables);

		Map<String, Object> res = new HashMap<String, Object>();

		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);

		// TODO FHJ 用HashMap改造，不会静态变量，想想有没有并且问题

		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

		// if(TaskAssignerCenter.isNew&&TaskAssignerCenter.taskAssignee.get()!=null)
		// {
		// res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS +
		// ",任务分配给了：" + TaskAssignerCenter.taskAssignee.get());
		// } else {
		// res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// }

		TaskAssignerCenter.isNew = false;
		TaskAssignerCenter.taskAssignee.remove();
		ServletUtils.writeToResponse(response, res);
	}
	
	   private void commitProcess(String taskId, Map<String, Object> variables,  
	            String activityId) throws Exception {  
	        if (variables == null) {  
	            variables = new HashMap<String, Object>();  
	        }  
	        // 跳转节点为空，默认提交操作  
	        if (activityId==null) {  
	            taskServiceReturnFee.complete(taskId, variables);  
	        } else {// 流程转向操作  
	            turnTransition(taskId, activityId, variables);  
	        }  
	    }  
	
	private ActivityImpl findActivitiImpl(String taskId, String activityId)  
            throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
  
        // 获取当前活动节点ID  
        if (activityId==null) {  
            activityId = findTaskById(taskId).getTaskDefinitionKey();  
        }  
  
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals("END")) {  
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl  
                        .getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
            }  
        }  
  
        // 根据节点ID，获取对应的活动节点  
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)  
                .findActivity(activityId);  
  
        return activityImpl;  
    }  
	
	/** 
     * 流程转向操作 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            目标节点任务ID 
     * @param variables 
     *            流程变量 
     * @throws Exception 
     */  
    private void turnTransition(String taskId, String activityId,  
            Map<String, Object> variables) throws Exception {  
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);  
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity);  
  
        // 执行转向任务  
        taskServiceReturnFee.complete(taskId, variables);  
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
    }  
    
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }  
    
    private void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }  
	
	/** 
     * 根据任务ID获取流程定义 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(  
            String taskId) throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(findTaskById(taskId)  
                        .getProcessDefinitionId());  
  
        if (processDefinition == null) {  
            throw new Exception("流程定义未找到!");  
        }  
  
        return processDefinition;  
    }  
    
    /** 
     * 根据任务ID获得任务实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private TaskEntity findTaskById(String taskId) throws Exception {  
        TaskEntity task = (TaskEntity) taskServiceReturnFee.createTaskQuery().taskId(  
                taskId).singleResult();  
        if (task == null) {  
            throw new Exception("任务实例未找到!");  
        }  
        return task;  
    }  

	/**
	 * 批量执行任务
	 */
	@RequestMapping(value = "completeBatchTasks")
	public void completeBatchTasks(
			@RequestParam(value = "tasksInfo", required = false) String tasksInfo,
			HttpServletRequest request, HttpServletResponse response)
			throws ServiceException {

		List<JSONObject> taskList = defaultClassTypeParser.parse(tasksInfo,
				List.class);

		SysUser loginUser = getLoginUser(request);

		Map<String, Object> res = new HashMap<String, Object>();

		StringBuilder errorMsg = new StringBuilder();

		String taskId = null;

		// 如果单个任务失败了不会影响剩下的任务被执行，所有失败的任务会被记录下来

		for (Map taskInfo : taskList) {
			Map processVariables = (Map) taskInfo.get("processVariables");
			taskId = (String) taskInfo.get("taskId");
			String serviceVariables = taskInfo.get("serviceVariables")
					.toString();

			// Map<String, Object> processVarMap =
			// defaultClassTypeParser.parse(processVariables, Map.class);

			Map<String, Object> bpmVariables = new HashMap<String, Object>();

			// 准备bpm变量集合
			prepareServiceParams(serviceVariables, bpmVariables);
			prepareProcessParams(processVariables, bpmVariables);
			prepareLoginInfoParams(loginUser, bpmVariables);
			try {
				taskService.completeTask(taskId, loginUser.getUserName(),
						bpmVariables);
			} catch (ServiceException e) {
				errorMsg.append("task:" + taskId + "失败：" + e.getMessage());
			}
		}
		// 把所有的异常汇总后统一抛出给前台
		if (!StringUtils.isEmpty(errorMsg.toString())) {
			throw new ServiceException(errorMsg.toString());
		}

		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

		ServletUtils.writeToResponse(response, res);
	}
	
	
	/**
	 * @description
	 * 查询 复核 重新分件的处理人
	 * @param taskId
	 * @param response
	 * @param request
	 * @throws ServiceException
	 * @author wangdk
	 * @return void
	 * @since  1.0.0
	*/
	@RequestMapping("/queryUserList.htm")
	public void queryUserList(@RequestParam(value = "taskId", required = true) String taskId,
			HttpServletResponse response, HttpServletRequest request)
			throws ServiceException{
		Map<String, Object> res = new HashMap<String, Object>();
		
		List<Map<String,Object>> userList=  taskService.queryUserList(taskId);
		
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		res.put(Constant.RESPONSE_DATA, userList);
		
		ServletUtils.writeToResponse(response, res);
	}
	
	/**
	 * 将指定的任务分配给指定的人
	 * 
	 * @param taskId
	 *            任务ID
	 * @param newTaskAssignee
	 *            新的任务执行者
	 * @param response
	 * @param request
	 * @throws ServiceException
	 */
	@RequestMapping("/assignTask")
	public void assignTask(
			@RequestParam(value = "taskId", required = true) String taskId,
			@RequestParam(value = "newTaskAssignee", required = true) String newTaskAssignee,
			HttpServletResponse response, HttpServletRequest request)
			throws ServiceException {
		Map<String, Object> res = new HashMap<String, Object>();
		//拿到当前登录账号名
		String assigner = getLoginUser(request).getUserName();

		checkParams(taskId,newTaskAssignee);
		
		// 分配任务
		taskService.assignTask(taskId, newTaskAssignee, assigner);

		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

		ServletUtils.writeToResponse(response, res);
	}
	
	/**
	 * 参数非空验证
	 * @param taskId
	 * @param newTaskAssignee
	 * @throws ServiceException
	 */
	private void checkParams(String taskId, String newTaskAssignee) throws ServiceException {
		if (org.apache.commons.lang3.StringUtils.isEmpty(taskId)) {
			throw new ServiceException("任务id不能为空");
		}
		if (org.apache.commons.lang3.StringUtils.isEmpty(newTaskAssignee)) {
			throw new ServiceException("请选择任务处理人");
		}
	}

	/**
	 * @Title: queryLoanChangeApprovalTasks
	 * @Description: TODO(贷后变更审批 任务列表)
	 * @param @param isCompleted
	 * @param @param start
	 * @param @param limit
	 * @param @param searchParams
	 * @param @param request
	 * @param @param response
	 * @param @throws ServiceException 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@RequestMapping("/queryLoanChangeApprovalTasks")
	public void queryLoanChangeApprovalTasks(
			@RequestParam(required = true) boolean isCompleted,
			@RequestParam(required = true) int pageNum,
			@RequestParam(required = true) int pageSize,
			@RequestParam(required = false) String searchParams,
			HttpServletRequest request, HttpServletResponse response)throws ServiceException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		
		if (!StringUtils.isEmpty(searchParams)) {
			paramMap.putAll(JsonUtil.parse(searchParams,Map.class));
		}

		SysUser loginUser = getLoginUser(request);
		Long roleId = getRoleId(request);
		paramMap.put("userName", getLoginUserName(request));
		paramMap.put("roleId", roleId);
		List<String> coverdOffices = getCoverdOffices(loginUser);
		if (CollectionUtils.isNotEmpty(coverdOffices)) {
			paramMap.put("coverdOffices", coverdOffices);
		}
		String roleName = getRoleName(roleId);
		paramMap.put("roleName", roleName);

 		PageHelper.startPage(pageNum,pageSize);
        List<Map> result = taskService.queryLoanChangeApprovalTasks(roleId,isCompleted,paramMap);
        PageInfo<Map> page = new PageInfo<Map>(result);
        System.out.println(new ArrayList() instanceof Page);
        
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		ServletUtils.writeToResponse(response, returnMap);
	}
	
	private String getRoleName(Long roleId) throws ServiceException{
		SysRole role = null;
		role = sysRoleService.getRoleById(roleId);
		return role.getNid();
	}

}
