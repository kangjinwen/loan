package com.company.modules.extension.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.extension.service.ExtensionCustomerEvaluationService;
import com.company.modules.extension.utils.databean.ExtensionPreliminaryEvaluationDataBean;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.instance.service.HousEnquiryInstitutionService;

/**
 * @author Administrator
 *
 */
@Service
public class ExtensionCustomerEvaluationServiceImpl extends HistoryRecorderService implements ExtensionCustomerEvaluationService{
	private static final Logger logger = LoggerFactory.getLogger(ExtensionCustomerEvaluationServiceImpl.class);
	@Autowired
	private HousEnquiryInstitutionService housEnquiryInstitutionService;
	@Autowired
	private HousAssessmentAgenciesService housAssessmentAgenciesService;
	
	@Override
	public void saveDraft(BasicServiceDataBean serviceDataBean) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void customerEvaluation(ExtensionPreliminaryEvaluationDataBean bean) throws Exception {
		try {
			if(bean.getHousEnquiryInstitution() != null && bean.getHousEnquiryInstitution().size()>0){
				for (HousEnquiryInstitution housEnquiryInstitution : bean.getHousEnquiryInstitution()) {
					housEnquiryInstitution.setProcessInstanceId(bean.getProcessInstanceId());
					housEnquiryInstitution.setProjectId(bean.getProjectId());
					housEnquiryInstitution.setConsultId(bean.getConsultId());
					createOrUpdateHousEnquiryInstitution(bean, housEnquiryInstitution);
				}
			}
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}		
		

		recordLoanProcessHistory(bean);//记录审批历史
		
	}
	
	/**
	 * 保存或修改查询机构信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void createOrUpdateHousEnquiryInstitution(ExtensionPreliminaryEvaluationDataBean bean, HousEnquiryInstitution housEnquiryInstitutionInfo) throws Exception {
		if(housEnquiryInstitutionInfo.getId() !=null){
			HousEnquiryInstitution housEnquiryInstitution = housEnquiryInstitutionService.getItemInfoById(housEnquiryInstitutionInfo.getId());
			housEnquiryInstitutionInfo.setId(housEnquiryInstitution.getId());
			housEnquiryInstitutionInfo.setModifier(bean.getLoginUserId());
			housEnquiryInstitutionInfo.setModifyTime(new Date());
			housEnquiryInstitutionService.update(housEnquiryInstitutionInfo);
		}else{
			housEnquiryInstitutionInfo.setCreator(bean.getLoginUserId());
			housEnquiryInstitutionInfo.setCreateTime(new Date());
			housEnquiryInstitutionService.insert(housEnquiryInstitutionInfo);
		}
	}

	@Override
	public void evaluation(ExtensionPreliminaryEvaluationDataBean bean) throws Exception {
		try {
			if(bean.getHousAssessmentAgencies() != null && bean.getHousAssessmentAgencies().size()>0){
				for (HousAssessmentAgencies housAssessmentAgencies : bean.getHousAssessmentAgencies()) {
					housAssessmentAgencies.setProcessInstanceId(bean.getProcessInstanceId());
					housAssessmentAgencies.setProjectId(bean.getProjectId());
					housAssessmentAgencies.setConsultId(bean.getConsultId());
					createOrUpdateHousAssessmentAgencies(bean, housAssessmentAgencies);
				}
			}
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		recordLoanProcessHistory(bean);//记录审批历史
		
	}
	
	public void createOrUpdateHousAssessmentAgencies(ExtensionPreliminaryEvaluationDataBean bean, HousAssessmentAgencies housAssessmentAgenciesInfo) throws Exception {
		if(housAssessmentAgenciesInfo.getId()!=null){
			HousAssessmentAgencies housAssessmentAgencies = housAssessmentAgenciesService.getItemInfoById(housAssessmentAgenciesInfo.getId());
			housAssessmentAgenciesInfo.setId(housAssessmentAgencies.getId());
			housAssessmentAgenciesInfo.setModifier(bean.getLoginUserId());
			housAssessmentAgenciesInfo.setModifyTime(new Date());
			housAssessmentAgenciesService.update(housAssessmentAgenciesInfo);
		}else{
			housAssessmentAgenciesInfo.setCreator(bean.getLoginUserId());
			housAssessmentAgenciesInfo.setCreateTime(new Date());
			housAssessmentAgenciesService.insert(housAssessmentAgenciesInfo);
		}
	}
	
}
