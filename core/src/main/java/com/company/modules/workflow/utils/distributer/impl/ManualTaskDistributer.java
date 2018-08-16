package com.company.modules.workflow.utils.distributer.impl;

import org.activiti.engine.delegate.DelegateTask;

import com.company.common.utils.ParamChecker;
import com.company.modules.workflow.utils.distributer.TaskDistributer;
import com.company.modules.workflow.utils.distributer.TaskWrapper;

/**
 *	@Description 手工分配任务
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月8日 下午5:49:33
 *  @since version 1.0.0
 */
public class ManualTaskDistributer implements TaskDistributer{

	@Override
	public void assignTask(TaskWrapper task) {
		 ParamChecker.checkEmpty(task, "taskWrapper");
	        
			DelegateTask delegateTask = task.getDelegateTask();		
			
			String userName = (String) delegateTask.getVariable("ManualAssignee");
			
			String useCheckBankrName = (String) delegateTask.getVariable("AssignCheckBank");
			
			if (delegateTask.getTaskDefinitionKey().equals("usertask-householdInvestigateTwo")) {
				delegateTask.setAssignee(userName);
			}else if(delegateTask.getTaskDefinitionKey().equals("usertask-householdInvestigate")){
				delegateTask.setAssignee(userName);
			}else if(delegateTask.getTaskDefinitionKey().equals("usertask-houseCheckBank")){
				delegateTask.setAssignee(useCheckBankrName);
			}else{
				delegateTask.setAssignee(userName);
			}
			
			
	
		
	}

}
