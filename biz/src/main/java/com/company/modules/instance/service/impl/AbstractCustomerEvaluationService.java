package com.company.modules.instance.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.domain.PlBorrowRequirement;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.service.PlFeeinfoService;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.CustomerEvaluationService;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.instance.service.HousBorrowingBasicsService;
import com.company.modules.instance.service.HousEnquiryInstitutionService;
import com.company.modules.instance.service.HousPersonTypeService;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;

/**
 * @author Administrator
 *
 */
public abstract class AbstractCustomerEvaluationService extends HistoryRecorderService implements CustomerEvaluationService {
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;
	@Autowired
	private HousAssessmentAgenciesService housAssessmentAgenciesService;
	@Autowired
	private HousBorrowingBasicsService housBorrowingBasicsService;
	@Autowired
	private PlBorrowRequirementService plBorrowRequirementService;
	@Autowired
	private PlConsultService plConsultService;
	@Autowired
	private HousEnquiryInstitutionService housEnquiryInstitutionService;
	@Autowired
	private PlFeeinfoService plFeeinfoService;
	@Autowired
   	private HousPersonTypeService housPersonTypeService;	
	
	/**
	 * 保存或修改房产信息
	 * @param preliminaryEvaluationDataBean
	 * @return
	 * @throws Exception
	 */
	public Long createOrUpdateHousPropertyInformation(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		Long propertyId = null;
		HousPropertyInformation housPropertyInformationInfo = preliminaryEvaluationDataBean.getHousPropertyInformation();
		housPropertyInformationInfo.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		housPropertyInformationInfo.setProjectId(preliminaryEvaluationDataBean.getProjectId());
		housPropertyInformationInfo.setConsultId(preliminaryEvaluationDataBean.getConsultId());
		HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		if(housPropertyInformation != null){
			housPropertyInformationInfo.setId(housPropertyInformation.getId());
			housPropertyInformationInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
			housPropertyInformationInfo.setModifyTime(new Date());
			housPropertyInformationService.update(housPropertyInformationInfo);
			propertyId = housPropertyInformationInfo.getId();
		}else{
			housPropertyInformationInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
			housPropertyInformationInfo.setCreateTime(new Date());
			housPropertyInformationService.insert(housPropertyInformationInfo);
			propertyId = housPropertyInformationInfo.getId();
		}		
		return propertyId;
	}
	
	/**
	 * 保存或修改评估机构
	 * @param preliminaryEvaluationDataBean
	 * @param propertyId
	 * @throws Exception
	 */
	public void createOrUpdateHousAssessmentAgencies(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean, HousAssessmentAgencies housAssessmentAgenciesInfo, Long propertyId) throws Exception {
		if(housAssessmentAgenciesInfo.getId()!=null){
			HousAssessmentAgencies housAssessmentAgencies = housAssessmentAgenciesService.getItemInfoById(housAssessmentAgenciesInfo.getId());
			housAssessmentAgenciesInfo.setId(housAssessmentAgencies.getId());
			housAssessmentAgenciesInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
			housAssessmentAgenciesInfo.setModifyTime(new Date());
			housAssessmentAgenciesService.update(housAssessmentAgenciesInfo);
		}else{
			housAssessmentAgenciesInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
			housAssessmentAgenciesInfo.setCreateTime(new Date());
			housAssessmentAgenciesService.insert(housAssessmentAgenciesInfo);
		}
	}
	
	/**
	 * 保存或修改借款需求信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void createOrUpdatePlBorrowRequirement(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		PlBorrowRequirement plBorrowRequirementInfo = preliminaryEvaluationDataBean.getPlBorrowRequirement();
		if (plBorrowRequirementInfo!=null) {			
			plBorrowRequirementInfo.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
			plBorrowRequirementInfo.setProjectId(preliminaryEvaluationDataBean.getProjectId());
			plBorrowRequirementInfo.setConsultId(preliminaryEvaluationDataBean.getConsultId());
		}
		PlBorrowRequirement plBorrowRequirement = plBorrowRequirementService.getInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		if(plBorrowRequirement != null){
			PlConsult plConsult = plConsultService.getItemInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
			if (plBorrowRequirementInfo!=null) {				
				plConsult.setProductId(plBorrowRequirementInfo.getProductId());
				plConsultService.update(plConsult);
				plBorrowRequirementInfo.setId(plBorrowRequirement.getId());
				plBorrowRequirementInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
				plBorrowRequirementInfo.setModifyTime(new Date());
				plBorrowRequirementService.update(plBorrowRequirementInfo);
			}
		}else{
			if (plBorrowRequirementInfo!=null) {				
				plBorrowRequirementInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
				plBorrowRequirementInfo.setCreateTime(new Date());
				plBorrowRequirementService.insert(plBorrowRequirementInfo);
			}
		}
		createOrUpdatePlFeeinfo(preliminaryEvaluationDataBean);
	}
	
	/**
	 * 保存或修改借款基本信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void createOrUpdateHousBorrowingBasics(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		HousBorrowingBasics housBorrowingBasicsInfo = preliminaryEvaluationDataBean.getHousBorrowingBasics();
		housBorrowingBasicsInfo.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		housBorrowingBasicsInfo.setProjectId(preliminaryEvaluationDataBean.getProjectId());
		housBorrowingBasicsInfo.setConsultId(preliminaryEvaluationDataBean.getConsultId());
		HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService.getItemInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		PlConsult plConsult = plConsultService.getItemInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		plConsult.setName(housBorrowingBasicsInfo.getName());
		plConsultService.update(plConsult);
		if(housBorrowingBasics != null){
			housBorrowingBasicsInfo.setId(housBorrowingBasics.getId());
			housBorrowingBasicsInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
			housBorrowingBasicsInfo.setModifyTime(new Date());
			housBorrowingBasicsService.update(housBorrowingBasicsInfo);
		}else{
			housBorrowingBasicsInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
			housBorrowingBasicsInfo.setCreateTime(new Date());
			housBorrowingBasicsService.insert(housBorrowingBasicsInfo);
		}
	}
	
	/**
	 * 保存或修改查询机构信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void createOrUpdateHousEnquiryInstitution(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean, HousEnquiryInstitution housEnquiryInstitutionInfo) throws Exception {
		if(housEnquiryInstitutionInfo.getId() != null){
			HousEnquiryInstitution housEnquiryInstitution = housEnquiryInstitutionService.getItemInfoById(housEnquiryInstitutionInfo.getId());
			housEnquiryInstitutionInfo.setId(housEnquiryInstitution.getId());
			housEnquiryInstitutionInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
			housEnquiryInstitutionInfo.setModifyTime(new Date());
			housEnquiryInstitutionService.update(housEnquiryInstitutionInfo);
		}else{
			housEnquiryInstitutionInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
			housEnquiryInstitutionInfo.setCreateTime(new Date());
			housEnquiryInstitutionService.insert(housEnquiryInstitutionInfo);
		}
	}
	
	/**
	 * 保存或修改费用信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void createOrUpdatePlFeeinfo(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		PlFeeinfo plFeeinfoInfo = plFeeinfoService.getItemInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		PlBorrowRequirement plBorrowRequirement = preliminaryEvaluationDataBean.getPlBorrowRequirement();
		if(plFeeinfoInfo != null){
			if (plBorrowRequirement!=null) {				
				plFeeinfoInfo.setRepaymentRate(plBorrowRequirement.getRepaymentRate());
				plFeeinfoInfo.setRepaymentType(plBorrowRequirement.getRepaymentType());
				plFeeinfoInfo.setTimeLimit(plBorrowRequirement.getTimeLimit());
				plFeeinfoInfo.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
				plFeeinfoInfo.setModifyTime(new Date());
				plFeeinfoService.update(plFeeinfoInfo);
			}
		}else{
			PlFeeinfo plFeeinfo = new PlFeeinfo();
			plFeeinfo.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
			plFeeinfo.setProjectId(preliminaryEvaluationDataBean.getProjectId());
			plFeeinfo.setConsultId(preliminaryEvaluationDataBean.getConsultId());
			plFeeinfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
			plFeeinfo.setCreateTime(new Date());
			plFeeinfo.setRepaymentRate(plBorrowRequirement.getRepaymentRate());
			plFeeinfo.setRepaymentType(plBorrowRequirement.getRepaymentType());
			plFeeinfo.setTimeLimit(plBorrowRequirement.getTimeLimit());
			plFeeinfoService.insert(plFeeinfo);
		}
	}
	
    /**
	 * 保存草稿的具体实现
	 * 保存所有业务数据，对于业务表中的流程字段都不予保存
	 */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public void saveDraft(BasicServiceDataBean serviceDataBean) throws Exception {
		PreliminaryEvaluationDataBean preliminaryEvaluationDataBean = (PreliminaryEvaluationDataBean)serviceDataBean;
		preCheckBasicParams(preliminaryEvaluationDataBean);
		if(preliminaryEvaluationDataBean.getPlBorrowRequirement() != null){
			createOrUpdatePlBorrowRequirement(preliminaryEvaluationDataBean);
		}
		//新增或更新人员类型
		List<HousPersonType> housPersonTypeList = preliminaryEvaluationDataBean.getHousPersonType();
		if(CollectionUtils.isNotEmpty(housPersonTypeList)){
			for (HousPersonType housPersonTypeInfo : housPersonTypeList) {
				housPersonTypeInfo.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());	
				housPersonTypeInfo.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
				housPersonTypeInfo.setConsultId(preliminaryEvaluationDataBean.getConsultId());
				housPersonTypeService.insertOrupdate(housPersonTypeInfo);
			}
		}
		if(preliminaryEvaluationDataBean.getHousPropertyInformation() != null){
			Long propertyId = createOrUpdateHousPropertyInformation(preliminaryEvaluationDataBean);
			if(preliminaryEvaluationDataBean.getHousAssessmentAgencies() != null){
				for (HousAssessmentAgencies housAssessmentAgencies : preliminaryEvaluationDataBean.getHousAssessmentAgencies()) {
					housAssessmentAgencies.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
					housAssessmentAgencies.setProjectId(preliminaryEvaluationDataBean.getProjectId());
					housAssessmentAgencies.setConsultId(preliminaryEvaluationDataBean.getConsultId());
					housAssessmentAgencies.setPropertyId(propertyId);
					createOrUpdateHousAssessmentAgencies(preliminaryEvaluationDataBean, housAssessmentAgencies, propertyId);
				}
			}
		}
		if(preliminaryEvaluationDataBean.getHousEnquiryInstitution() != null){
			for (HousEnquiryInstitution housEnquiryInstitution : preliminaryEvaluationDataBean.getHousEnquiryInstitution()) {
				housEnquiryInstitution.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
				housEnquiryInstitution.setProjectId(preliminaryEvaluationDataBean.getProjectId());
				housEnquiryInstitution.setConsultId(preliminaryEvaluationDataBean.getConsultId());
				createOrUpdateHousEnquiryInstitution(preliminaryEvaluationDataBean, housEnquiryInstitution);
			}
		}
		if(preliminaryEvaluationDataBean.getHousBorrowingBasics() != null){
			createOrUpdateHousBorrowingBasics(preliminaryEvaluationDataBean);
		}
	}
	
	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean) throws ServiceException {
	}
}
