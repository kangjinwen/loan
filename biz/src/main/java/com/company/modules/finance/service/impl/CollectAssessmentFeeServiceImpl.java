package com.company.modules.finance.service.impl;

import java.util.Date;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.finance.dao.HousLowerCostDao;
import com.company.modules.finance.domain.HousLowerCost;
import com.company.modules.finance.service.CollectAssessmentFeeService;
import com.company.modules.finance.util.databean.HousLowerCostTaskDataBean;

@Service
public class CollectAssessmentFeeServiceImpl extends HistoryRecorderService implements CollectAssessmentFeeService {
	
	@Autowired
	private HousLowerCostDao housLowerCostDao;
	@Autowired
	private PlConsultDao plConsultDao;

	@Override
	public void collectAssessmentFee(HousLowerCostTaskDataBean serviceDataBean,DelegateTask delegateTask) throws Exception {
		HousLowerCost housLowerCost=serviceDataBean.getHousLowerCost();
		housLowerCost.setCreator(serviceDataBean.getLoginUserId());
		
		if (serviceDataBean.getNextStep().equals("pass")) {
			// 判断是存存在下户费信息
			HousLowerCost dbhousLowerCost = housLowerCostDao.getItemInfoByProcessInstanceId(serviceDataBean.getProcessInstanceId());
			if (dbhousLowerCost == null) {
				housLowerCost.setCreateTime(new Date());
				housLowerCost.setProcessInstanceId(serviceDataBean.getProcessInstanceId());
				housLowerCost.setProjectId(serviceDataBean.getProjectId());
				housLowerCostDao.insert(housLowerCost);
			} else {
				housLowerCost.setModifyTime(new Date());
				housLowerCostDao.update(housLowerCost);
			}
			
			PlConsult creditconsult = plConsultDao.getPlConsultByProjectId(serviceDataBean.getProjectId());
		    creditconsult.setFeeSuccess((byte)1);
			plConsultDao.update(creditconsult);
		}
		
		recordLoanProcessHistory(serviceDataBean);//记录审批历史
		
	}

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}	

}
