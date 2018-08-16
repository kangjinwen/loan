package com.company.modules.warrant.service.impl;

import java.util.Objects;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.warrant.dao.HousDataListDao;
import com.company.modules.warrant.dao.HousIntermediaryInformationDao;
import com.company.modules.warrant.dao.HousOritoInformationDao;
import com.company.modules.warrant.dao.HousQuickInformationDao;
import com.company.modules.warrant.domain.HousDataList;
import com.company.modules.warrant.domain.HousIntermediaryInformation;
import com.company.modules.warrant.domain.HousOritoInformation;
import com.company.modules.warrant.domain.HousQuickInformation;
import com.company.modules.warrant.service.HouseholdCheckBankService;
import com.company.modules.warrant.util.databean.HouseholdInvestigateDataBean;

@Service
public class HouseholdCheckBankServiceImpl extends HistoryRecorderService implements HouseholdCheckBankService {

	private static final Logger logger = LoggerFactory.getLogger(HouseholdCheckBankServiceImpl.class);
	
	@Autowired
	private HousDataListDao housDataListDao ;//资料清单表(权证下户)
	@Autowired
	private HousIntermediaryInformationDao housIntermediaryInformationDao ;//房屋中介信息(权证下户)
	@Autowired
	private HousOritoInformationDao housOritoInformationDao ;//下户信息表(权证下户)
	@Autowired
	private HousQuickInformationDao housQuickInformationDao ;//房屋快出值信息表(权证下户)
	
	@Override
	public void householdInvestigate(HouseholdInvestigateDataBean householdInvestigateDataBean, DelegateTask delegateTask) throws Exception {
		logger.info("进入权证下户...");

		if (logger.isDebugEnabled()) {
			logger.debug("参数列表：{}", householdInvestigateDataBean);
		}

        // 登记下户信息
		insertOrUpdateHousinfo(householdInvestigateDataBean);
		
		recordLoanProcessHistory(householdInvestigateDataBean);

	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Boolean insertOrUpdateHousinfo(HouseholdInvestigateDataBean householdInvestigateDataBean) throws Exception{
		String processInstanceId = householdInvestigateDataBean.getProcessInstanceId();
		long projectId = householdInvestigateDataBean.getProjectId();
		long consultId = householdInvestigateDataBean.getConsultId();
		
		HousDataList housDataList = householdInvestigateDataBean.getHousDataList();
		if (housDataList!=null) {			
			housDataList.setProcessInstanceId(processInstanceId);
			housDataList.setProjectId(projectId);
			housDataList.setConsultId(consultId);
		}
		
		HousIntermediaryInformation housIntermediaryInformation = householdInvestigateDataBean.getHousIntermediaryInformation();
		if (housIntermediaryInformation!=null) {			
			housIntermediaryInformation.setProjectId(projectId);
			housIntermediaryInformation.setProcessInstanceId(processInstanceId);
			housIntermediaryInformation.setConsultId(consultId);			
		}
		HousOritoInformation housOritoInformation = householdInvestigateDataBean.getHousOritoInformation();
		if (housOritoInformation!=null) {			
			housOritoInformation.setProjectId(projectId);
			housOritoInformation.setProcessInstanceId(processInstanceId);
			housOritoInformation.setConsultId(consultId);
			
		}
		HousQuickInformation housQuickInformation = householdInvestigateDataBean.getHousQuickInformation();
		if (housQuickInformation!=null) {			
			housQuickInformation.setProjectId(projectId);
			housQuickInformation.setProcessInstanceId(processInstanceId);
			housQuickInformation.setConsultId(consultId);
		}
		
		long housDataListNum = 0;
		long housIntermediaryInformationNum = 0;
		long housOritoInformationNum = 0;
		long housQuickInformationNum = 0;
		
		//资料清单表(权证下户)
		if(!Objects.equals(null, housDataList)){
			if(Objects.equals(null, housDataList.getId()) || Objects.equals(0L, housDataList.getId())){
				housDataListNum = housDataListDao.insert(housDataList);
			}else{
				housDataListNum = housDataListDao.update(housDataList);
			}
		}
		//房屋中介信息(权证下户)
		if(!Objects.equals(null, housIntermediaryInformation)){
			if(Objects.equals(null, housIntermediaryInformation.getId()) || Objects.equals(0L, housIntermediaryInformation.getId())){
				housIntermediaryInformationNum = housIntermediaryInformationDao.insert(housIntermediaryInformation);
			}else{
				housIntermediaryInformationNum = housIntermediaryInformationDao.update(housIntermediaryInformation);
			}
		}
		//下户信息表(权证下户)
		if(!Objects.equals(null, housOritoInformation)){
			if(Objects.equals(null, housOritoInformation.getId()) || Objects.equals(0L, housOritoInformation.getId())){
				housOritoInformationNum = housOritoInformationDao.insert(housOritoInformation);
			}else{
				housOritoInformationNum = housOritoInformationDao.update(housOritoInformation);
			}
		}
		//房屋核行信息(权证下户)
		if(!Objects.equals(null, housQuickInformation)){
			if(Objects.equals(null, housQuickInformation.getId()) || Objects.equals(0L, housQuickInformation.getId())){
				housQuickInformationNum = housQuickInformationDao.insert(housQuickInformation);
			}else{
				housQuickInformationNum = housQuickInformationDao.update(housQuickInformation);
			}
		}
		
		if(housDataListNum > 0 && housIntermediaryInformationNum > 0 && housOritoInformationNum > 0 && housQuickInformationNum > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

}
