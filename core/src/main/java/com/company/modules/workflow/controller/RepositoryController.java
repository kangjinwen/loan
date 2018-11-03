package com.company.modules.workflow.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.workflow.dao.TaskDao;
import com.company.modules.workflow.service.RDRepositoryService;

@Controller
@RequestMapping("/modules/workflow/controller/RepositoryController")
public class RepositoryController extends WorkflowBaseController {

	private static final String USERTASK_REUTRNFEE ="usertask-reutrnFee";
	private static final String ENDRETURNFEE = "endReturnFee";
	private static final String USERTASK_HOUSEHOLDCONFIRM ="usertask-householdConfirm";
	private static final String BACKCHECKBANK ="backCheckBank";
	private static final String BACKTASKASSIGN ="backTaskAssign";
	@Autowired
	private RDRepositoryService repositoryService;

	@Autowired
	private TaskService taskService;


	@Autowired
	private TaskDao taskDao;

	@RequestMapping("/queryButtonNameList")
	public void queryButtonNameList(String processInstanceId, HttpServletResponse response) throws ServiceException {
		Map<String, Object> res = new HashMap<String, Object>();

		List<Map<String, String>> queryButtonNameList = (List<Map<String, String>>) repositoryService
				.queryButtonNameList(processInstanceId);

		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		res.put(Constant.RESPONSE_DATA, queryButtonNameList);

		ServletUtils.writeToResponse(response, res);
	}

	@RequestMapping("/queryButtonNameListByTaskId")
	public void queryButtonNameListByTaskId(String taskId, HttpServletResponse response,
			@RequestParam(value = "processInstanceId", required = false) String processInstanceId)
			throws ServiceException {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, String>> queryButtonNameList = (List<Map<String, String>>) repositoryService
				.queryButtonNameListByTaskId(taskId);
		Task task1 = taskService.createTaskQuery().taskId(taskId).singleResult();

		// TODO: 2018/9/29 此处函数可以新加参数，根绝产品id判断是不是渠道，如果是，移除选择节点非渠道标志 
		

		//判断是否为退费操作（此处和小贷无关）
		if(!USERTASK_REUTRNFEE.equals(task1.getTaskDefinitionKey())){
			for (int i = 0; i < queryButtonNameList.size(); i++) {
				if(ENDRETURNFEE.equals(queryButtonNameList.get(i).get("value"))){//不是退费操作，去掉退费审批意见
					queryButtonNameList.remove(i);
				}
			}
		}
		//类似判断是否有房产证明，和小贷无关
		if (USERTASK_HOUSEHOLDCONFIRM.equals(task1.getTaskDefinitionKey())) {
			List<Map<String, Object>> list = taskDao.queryHouseCheckBankList(processInstanceId);
			if (list==null || list.size()==0) {				
				for (int i = 0; i < queryButtonNameList.size(); i++) {
					if(BACKCHECKBANK.equals(queryButtonNameList.get(i).get("value"))){//面审前确认，如果没有查到核行历史记录就去掉驳回到核行审批意见
						queryButtonNameList.remove(i);
					}
				}
			}
		}
		//和小贷无关
		if (USERTASK_HOUSEHOLDCONFIRM.equals(task1.getTaskDefinitionKey())) {
			List<Map<String, Object>> list = taskDao.queryHouseCheckBankList(processInstanceId);
			if (list==null || list.size()==0) {				
				for (int i = 0; i < queryButtonNameList.size(); i++) {
					if(BACKTASKASSIGN.equals(queryButtonNameList.get(i).get("value"))){//面审前确认，如果没有查到核行历史记录就去掉驳回任务分配审批意见
						queryButtonNameList.remove(i);
					}
				}
			}
		}
		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		res.put(Constant.RESPONSE_DATA, queryButtonNameList);
		ServletUtils.writeToResponse(response, res);
	}

	@RequestMapping("/queryPossibleComments")
	public void queryPossibleComments(String processInstanceId, String taskType, HttpServletResponse response,
			HttpServletRequest request) throws ServiceException {
		Map<String, Object> res = new HashMap<String, Object>();

		Long roleId = getRoleId(request);

		Collection<Map<String, String>> possibleComments = repositoryService.queryPossibleComments(processInstanceId,
				taskType, roleId);

		res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		res.put(Constant.RESPONSE_DATA, possibleComments);

		ServletUtils.writeToResponse(response, res);
	}
}
