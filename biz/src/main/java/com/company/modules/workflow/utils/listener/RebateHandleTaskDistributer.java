package com.company.modules.workflow.utils.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.company.common.domain.PubProcessBranching;
import com.company.common.service.PubProcessBranchingService;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysUserService;
import com.company.modules.workflow.service.RDHistoryService;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 * 返佣管理任务分配器
 * 初次返佣随机受到任务，之后的返佣取上一次财务返佣专员
 * @See 
 * @author huy
 *
 */
public class RebateHandleTaskDistributer implements TaskDistributer{
	@Override
	public void assignTask(TaskWrapper taskWrapper) { 
		ParamChecker.checkEmpty(taskWrapper, "taskWrapper");
		DelegateTask delegateTask = taskWrapper.getDelegateTask();
		String branchingProcessInstanceId = delegateTask.getProcessInstanceId();
		String assignee = delegateTask.getAssignee();
		String roleName = assignee.substring(assignee.indexOf(":") + 1);
		SysUserService sysUserService = ApplicationContextHelperBean.getBean(SysUserService.class);
		PubProcessBranchingService pubProcessBranchingService = ApplicationContextHelperBean.getBean(PubProcessBranchingService.class);
		RDHistoryService historyService = ApplicationContextHelperBean.getBean(RDHistoryService.class);
		SysUser sysUser;
		String assigneeName = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("branchingProcessId", branchingProcessInstanceId);
			//map.put("isActive",WorkFlowConstant.IS_ACTIVE_RETURN_COMMISSION);
			// 1.根据分支流程id查询主流程id对应的之前分支流程 2.根据分支流程id查询历史流程信息
			List<PubProcessBranching> processBraninglist = pubProcessBranchingService.getPageListByMap(map);
			if(CollectionUtils.isNotEmpty(processBraninglist)){
				PubProcessBranching pb = processBraninglist.get(0);
				map.clear();
		    	map.put("processInstanceId", pb.getProcessInstanceId());
				map.put("processStatus", "usertask-returnCommission");
				processBraninglist= pubProcessBranchingService.getPageListByMap(map);
				if(CollectionUtils.isNotEmpty(processBraninglist)){
					pb = processBraninglist.get(processBraninglist.size()-1);
					Map<String,Object> thelatestHistoricTaskInstance = 
							historyService.queryTheLatestHistoricTask(pb.getBranchingProcessId(), "usertask-returnCommission");
					if(thelatestHistoricTaskInstance!= null){
						assigneeName = thelatestHistoricTaskInstance.get("ASSIGNEE_").toString();
					}
				}
			}
			if(StringUtils.isBlank(assigneeName)){
				String taskDefinition = null;
				// TODO FHJ查询出来的人必须和启动流程的用户是同一个机构
				// 首先查询出启动流程的用户
				String processLauncher = (String) delegateTask.getVariable(SystemConstant.PROCESS_LAUNCHER);
				SysUser launcher = sysUserService.getUserByUserName(processLauncher);
				sysUser = sysUserService.queryTheLeastBusyUserByRoleName(roleName, launcher.getOfficeId(),branchingProcessInstanceId, taskDefinition);
				if (sysUser == null) {
			        throw new RDRuntimeException("后续任务分配失败。");
			    }
				assigneeName = sysUser.getUserName();
			}
		} catch (ServiceException e) {
			throw new RDRuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RDRuntimeException(e.getMessage(), e);
		}
		delegateTask.setAssignee(assigneeName);
	}

}
