package com.company.modules.finance.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.dao.PubProjectProcessDao;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.finance.dao.HousLowerCostDao;
import com.company.modules.finance.domain.HousLowerCost;
import com.company.modules.finance.service.ReturnFeeService;
import com.company.modules.finance.util.databean.ReturnFeeTaskDataBean;

@Service
public class ReturnFeeServiceImpl extends HistoryRecorderService implements ReturnFeeService {
	
	@Autowired
	private HousLowerCostDao housLowerCostDao;
	@Autowired
	private PlConsultDao plConsultDao;   
	@Autowired
	private PubLoanprocessDao pubLoanprocessDao; 
	@Autowired
	private TaskService taskService;   
	@Autowired
	private PubProjectProcessDao pubProjectProcessDao; 
	

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void returnFee(ReturnFeeTaskDataBean serviceDataBean, DelegateTask delegateTask) throws Exception {
		if (serviceDataBean.getNextStep().equals("returnFeeSuccess")) {
			//退费成功
			Map<String,Object> variables = new HashMap<>();
			Map<String,Object> mapVariables = new HashMap<>();
			long projectId = serviceDataBean.getProjectId();
			String taskId = serviceDataBean.getProcessInstanceId();
			HousLowerCost housLowerCost =  housLowerCostDao.getHousLowerCostByProjectId(projectId);
			PubLoanprocess pubLoanprocess = pubLoanprocessDao.getByTaskId(taskId);
		    housLowerCost.setApplyRefundIsSuccess((byte)1);		   
			housLowerCostDao.update(housLowerCost);	
			/*if (pubLoanprocess.getType().equals("endReturnFee")) {//贷款终结退费，终止主流程	
				PubProjectProcess pubProjectProcess = pubProjectProcessDao.getProjectProcessByProjectIdAndType(projectId);
				Task task = taskService.createTaskQuery().processInstanceId(pubProjectProcess.getProcessInstanceId()).singleResult();				
				variables.put("remarkComment", "退费终止");
				variables.put("consultId", serviceDataBean.getConsultId());
				variables.put("nextStep", "endReturnFee");
				variables.put("processStateCode",serviceDataBean.getProcessStateCode() );
				variables.put("projectId", serviceDataBean.getProjectId());
				variables.put("processInstanceId",pubProjectProcess.getProcessInstanceId());
				variables.put("comment", "endReturnFee");
				String strvariables = JsonUtil.toString(variables);
				mapVariables.put(SystemConstant.SERVICE_VARIABLES, strvariables);
				taskService.complete(task.getId(),mapVariables);
			}*/
		}
		recordLoanProcessHistory(serviceDataBean);//记录审批历史
			
		
	}	

}
