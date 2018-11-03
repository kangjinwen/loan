package com.company.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.modules.instance.dao.HousPropertyInformationDao;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.CommonConstant;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.domain.PubCustomer;
import com.company.common.domain.PubProject;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.service.PlFeeinfoService;
import com.company.common.service.PlProductService;
import com.company.common.service.PubCustomerService;
import com.company.common.service.PubProjectProcessService;
import com.company.common.service.PubProjectService;
import com.company.common.service.ZZJFPlConsultService;
import com.company.common.utils.DateUtil;
import com.company.common.utils.converter.CreditConsultModelConverter;
import com.company.common.utils.generator.NOGenerator;
import com.company.common.utils.generator.impl.ProjectNOGenerator;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PubBizAttachmentDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.domain.PubBizAttachment;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.BusinessBaseService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.instance.service.HousBorrowingBasicsService;
import com.company.modules.instance.service.HousPersonTypeService;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.service.SysOfficeService;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

/**
 * User: wulb DateTime:2016-08-08 01:01:45 details: 咨询信息,Service实现层 source:
 * 代码生成器
 */
@Service(value = "zZJFPlCreditconsultServiceImpl")
public class ZZJFPlConsultServiceImpl extends BusinessBaseService implements ZZJFPlConsultService {
	private static final Logger logger = LoggerFactory.getLogger(ZZJFPlConsultServiceImpl.class);
	private String USERTASK_COLLATERALASSESS = "usertask-collateralAssess";
	private String USERTASK_XIAHU = "usertask-xiahu";
	private String USERTASK_UPLOADING = "usertask-uploading";
	private Double SECONDARY_AUDIT_AMOUNT_THRESHOLD = null;
	private Double SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD = null;
	private Double MANAGER_CONFIRMATION_AMOUNT_THRESHOLD = null;
	private static final long IS_DELETE_NO = 0;
	@Autowired
	private PlConsultDao plCreditconsultDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private SysOfficeService sysOfficeService;
	@Autowired
	private PubProjectService pubProjectService;
	@Autowired
	private PubProjectProcessService pubProjectProcessService;
	@Autowired
	private HousAssessmentAgenciesService housAssessmentAgenciesService;
	@Autowired
	private HousBorrowingBasicsService housBorrowingBasicsService;
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;
	@Autowired
	private PlBorrowRequirementService plBorrowRequirementService;
	@Autowired
	private PlFeeinfoService plFeeinfoService;
	@Autowired
	private PlProductService plProductService;
	@Autowired
	private HousPersonTypeService housPersonTypeService;
	@Autowired
	private PubBizAttachmentDao pubBizAttachmentDao;
	@Autowired
	private PubCustomerService pubCustomerService;

	@Autowired
	HousPropertyInformationDao housPropertyInformationDao;

	@Override
	public Map<String, Object> getWhetherLoanByHomeNum(String homeNum) {
		Map<String,Object> houseInfo = housPropertyInformationDao.getWhetherLoanByHomeNum(homeNum);
		if (houseInfo!=null){
			return houseInfo;
		}
		return null;
	}

	/**
	 * 新增咨询
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addPlConsult(SysRole sysRole, PreliminaryEvaluationDataBean newConsult, String creditConsultFrom)
			throws Exception {
		logger.info("新增咨询...");
		if (logger.isDebugEnabled()) {
			logger.debug("传入参数：" + newConsult);
		}
		// 点击返回只保存数据不启动流程，点击提交启动流程
		if (newConsult.getCommit() != 0) {
			preCheckBasicParams(newConsult);
			preCheckServiceParams(newConsult);
			preCheckWorkflowParams(newConsult);

			if (newConsult.getConsultId() == null) {
				Long insertCreditConsultId = createCreditConsult(sysRole, newConsult);
				// 把刚刚新插入的id放入VO供后续的数据库表作逻辑“外键”使用
				newConsult.setConsultId(insertCreditConsultId);
			} else {
				updateCreditConsult(sysRole, newConsult);
			}
			PubCustomer cus = pubCustomerService.getItemInfoById(Long.parseLong(newConsult.getCustomerId()));
			cus.setLoans((cus.getLoans() + 1));
			pubCustomerService.update(cus);
			// 当nextStep=1，启动流程
			if (SystemConstant.FLOW_STATE_APPLY_DOCUMENTING.equals(newConsult.getNextStep())) {
				startProject(newConsult,creditConsultFrom);

				HousPropertyInformation housPropertyInformation = newConsult.getHousPropertyInformation();

				housPropertyInformation.setProcessInstanceId(newConsult.getProcessInstanceId());
				housPropertyInformation.setProjectId(newConsult.getProjectId());
				housPropertyInformation.setConsultId(newConsult.getConsultId());
				housPropertyInformation.setCreateTime(new Date());
				housPropertyInformation.setCreator(newConsult.getLoginUserId());
				HousPropertyInformation dbhousPropertyInformation = housPropertyInformationService
						.getItemInfoByConsultId(newConsult.getConsultId());
				if (dbhousPropertyInformation != null) {
					housPropertyInformation.setId(dbhousPropertyInformation.getId());
					housPropertyInformationService.update(housPropertyInformation);
				} else {
					housPropertyInformationService.insert(housPropertyInformation);
				}
				// 把新增申请的房屋附件pub_biz_attachment-relation_id字段更新成ProcessInstanceId
				Map<String, Object> param = new HashMap<String, Object>();
				if (dbhousPropertyInformation != null) {
					param.put("relationId", dbhousPropertyInformation.getId());
					List<PubBizAttachment> pubBizAttachmentList = pubBizAttachmentDao.queryAll(param);
					if (pubBizAttachmentList != null && pubBizAttachmentList.size() > 0) {
						for (PubBizAttachment pubBizAttachment : pubBizAttachmentList) {
							pubBizAttachment.setRelationId(Long.parseLong(newConsult.getProcessInstanceId()));
							pubBizAttachmentDao.update(pubBizAttachment);
						}
					}
				}

				PlBorrowRequirement plBorrowRequirement = newConsult.getPlBorrowRequirement();
				plBorrowRequirement.setProcessInstanceId(newConsult.getProcessInstanceId());
				plBorrowRequirement.setProjectId(newConsult.getProjectId());
				plBorrowRequirement.setConsultId(newConsult.getConsultId());
				plBorrowRequirement.setCreateTime(new Date());
				plBorrowRequirement.setCreator(newConsult.getLoginUserId());
				PlBorrowRequirement dbplBorrowRequirement = plBorrowRequirementService
						.getItemInfoByConsultId(newConsult.getConsultId());
				if (dbplBorrowRequirement != null) {
					plBorrowRequirement.setId(dbplBorrowRequirement.getId());
					plBorrowRequirementService.update(plBorrowRequirement);
				} else {
					plBorrowRequirementService.insert(plBorrowRequirement);
				}

				PlFeeinfo plFeeinfo = new PlFeeinfo();
				plFeeinfo.setProcessInstanceId(newConsult.getProcessInstanceId());
				plFeeinfo.setProjectId(newConsult.getProjectId());
				plFeeinfo.setConsultId(newConsult.getConsultId());
				plFeeinfo.setCreator(newConsult.getLoginUserId());
				plFeeinfo.setCreateTime(new Date());
				plFeeinfo.setRepaymentRate(plBorrowRequirement.getRepaymentRate());
				plFeeinfo.setRepaymentType(plBorrowRequirement.getRepaymentType());
				plFeeinfo.setTimeLimit(plBorrowRequirement.getTimeLimit());
				plFeeinfo.setSingleRate(plBorrowRequirement.getSingleRate());
				logger.info("【productId】" + plBorrowRequirement.getProductId());
				PlProduct product = plProductService.getItemInfoById(plBorrowRequirement.getProductId());
				if (product != null) {
					plFeeinfo.setOverduePenaltyRate(product.getOverduePenaltyRate());
					plFeeinfo.setAheadRepayRate(product.getAheadRepayRate());
					plFeeinfo.setProductType(product.getProductType());
					plFeeinfo.setPtype(product.getPtype());
				}
				PlFeeinfo dbPlFeeinfo = plFeeinfoService.getItemInfoByConsultId(newConsult.getConsultId());
				if (dbPlFeeinfo == null) {
					plFeeinfoService.insert(plFeeinfo);
				} else {
					plFeeinfo.setId(dbPlFeeinfo.getId());
					plFeeinfoService.update(plFeeinfo);
				}

//				HousBorrowingBasics housBorrowingBasics = newConsult.getHousBorrowingBasics();
//				housBorrowingBasics.setProcessInstanceId(newConsult.getProcessInstanceId());
//				housBorrowingBasics.setProjectId(newConsult.getProjectId());
//				housBorrowingBasics.setConsultId(newConsult.getConsultId());
//				housBorrowingBasics.setCreateTime(new Date());
//				housBorrowingBasics.setCreator(newConsult.getLoginUserId());
//				HousBorrowingBasics dbhousBorrowingBasics = housBorrowingBasicsService.getItemInfoByConsultId(newConsult.getConsultId());
//				if (dbhousBorrowingBasics!= null) {
//					housBorrowingBasics.setId(dbhousBorrowingBasics.getId());
//					housBorrowingBasicsService.update(housBorrowingBasics);
//				}else {
//					housBorrowingBasicsService.insert(housBorrowingBasics);
//				}
				// 将新产生的流程实例ID设置到CreditConsult中
				updateConsultWithProcessAndProjectId(newConsult);

				// 新增或更新人员类型
				List<HousPersonType> housPersonTypeList = newConsult.getHousPersonType();
				if (CollectionUtils.isNotEmpty(housPersonTypeList)) {
					for (HousPersonType housPersonTypeInfo : housPersonTypeList) {
						housPersonTypeInfo.setProcessInstanceId(newConsult.getProcessInstanceId());
						housPersonTypeInfo.setConsultId(newConsult.getConsultId());
						housPersonTypeInfo.setCreator(newConsult.getLoginUserId());
						housPersonTypeService.insertOrupdate(housPersonTypeInfo);
					}
				}
			}
			// 保存草稿把id返回去
			Map<String, Object> result = new HashMap<String, Object>();
			PlBorrowRequirement plBorrowRequirement = plBorrowRequirementService
					.getItemInfoByConsultId(newConsult.getConsultId());
			HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService
					.getItemInfoByConsultId(newConsult.getConsultId());
			HousPropertyInformation housPropertyInformation = housPropertyInformationService
					.getItemInfoByConsultId(newConsult.getConsultId());
			List<HousPersonType> housPersonTypes = housPersonTypeService
					.getItemInfoByConsultId(newConsult.getConsultId());
			PlFeeinfo plFeeinfo = plFeeinfoService.getItemInfoByConsultId(newConsult.getConsultId());
			result.put("plBorrowRequirement", plBorrowRequirement);
			result.put("housBorrowingBasics", housBorrowingBasics);
			result.put("housPropertyInformation", housPropertyInformation);
			result.put("plFeeinfo", plFeeinfo);
			result.put("housPersonTypes", housPersonTypes);
			result.put("consultId", newConsult.getConsultId());
			return result;

		} else { // 点击返回，保存临时数据
			preCheckBasicParams(newConsult);
			if (newConsult.getConsultId() == null) {
				Long insertCreditConsultId = createCreditConsult(sysRole, newConsult);
				// 把刚刚新插入的id放入VO供后续的数据库表作逻辑“外键”使用
				newConsult.setConsultId(insertCreditConsultId);
			} else {
				updateCreditConsult(sysRole, newConsult);
			}
			if (SystemConstant.FLOW_STATE_APPLY_DOCUMENTING.equals(newConsult.getNextStep())) {

				PlBorrowRequirement plBorrowRequirement = newConsult.getPlBorrowRequirement();
				plBorrowRequirement.setConsultId(newConsult.getConsultId());
				plBorrowRequirement.setCreateTime(new Date());
				plBorrowRequirement.setCreator(newConsult.getLoginUserId());
				PlBorrowRequirement dbplBorrowRequirement = plBorrowRequirementService
						.getItemInfoByConsultId(newConsult.getConsultId());
				if (dbplBorrowRequirement != null) {
					plBorrowRequirement.setCollectionRateUpdateNull(true);
					plBorrowRequirement.setCollectionServiceFeeUpdateNull(true);
					plBorrowRequirement.setId(dbplBorrowRequirement.getId());
					plBorrowRequirementService.update(plBorrowRequirement);
				} else {
					plBorrowRequirement.setCustomerId(Long.parseLong(newConsult.getCustomerId()));
					plBorrowRequirementService.insert(plBorrowRequirement);
				}

				PlFeeinfo plFeeinfo = new PlFeeinfo();
				plFeeinfo.setConsultId(newConsult.getConsultId());
				plFeeinfo.setCreator(newConsult.getLoginUserId());
				plFeeinfo.setCreateTime(new Date());
				plFeeinfo.setRepaymentRate(plBorrowRequirement.getRepaymentRate());
				plFeeinfo.setRepaymentType(plBorrowRequirement.getRepaymentType());
				plFeeinfo.setTimeLimit(plBorrowRequirement.getTimeLimit());
				plFeeinfo.setSingleRate(plBorrowRequirement.getSingleRate());

				PlProduct product = null;
				if (plBorrowRequirement.getProductId() != null) {
					product = plProductService.getItemInfoById(plBorrowRequirement.getProductId());
				}
				if (product != null) {
					plFeeinfo.setOverduePenaltyRate(product.getOverduePenaltyRate());
					plFeeinfo.setAheadRepayRate(product.getAheadRepayRate());
					plFeeinfo.setProductType(product.getProductType());
					plFeeinfo.setPtype(product.getPtype());
				}
				PlFeeinfo dbPlFeeinfo = plFeeinfoService.getItemInfoByConsultId(newConsult.getConsultId());
				if (dbPlFeeinfo == null) {
					plFeeinfoService.insert(plFeeinfo);
				} else {
					plFeeinfo.setId(dbPlFeeinfo.getId());
					plFeeinfoService.update(plFeeinfo);
				}

//				HousBorrowingBasics housBorrowingBasics = newConsult.getHousBorrowingBasics();
//				housBorrowingBasics.setConsultId(newConsult.getConsultId());
//				housBorrowingBasics.setCreateTime(new Date());
//				housBorrowingBasics.setCreator(newConsult.getLoginUserId());
//				HousBorrowingBasics dbhousBorrowingBasics = housBorrowingBasicsService.getItemInfoByConsultId(newConsult.getConsultId());
//				if (dbhousBorrowingBasics!= null) {
//					housBorrowingBasics.setId(dbhousBorrowingBasics.getId());
//					housBorrowingBasicsService.update(housBorrowingBasics);
//				}else {
//					housBorrowingBasicsService.insert(housBorrowingBasics);
//				}

				// 新增或更新人员类型
				List<HousPersonType> housPersonTypeList = newConsult.getHousPersonType();
				if (CollectionUtils.isNotEmpty(housPersonTypeList)) {
					for (HousPersonType housPersonTypeInfo : housPersonTypeList) {
						housPersonTypeInfo.setCreator(newConsult.getLoginUserId());
						housPersonTypeInfo.setConsultId(newConsult.getConsultId());
						housPersonTypeService.insertOrupdate(housPersonTypeInfo);
					}
				}

				HousPropertyInformation housPropertyInformation = newConsult.getHousPropertyInformation();
				housPropertyInformation.setConsultId(newConsult.getConsultId());
				housPropertyInformation.setCreateTime(new Date());
				housPropertyInformation.setCreator(newConsult.getLoginUserId());
				HousPropertyInformation dbhousPropertyInformation = housPropertyInformationService
						.getItemInfoByConsultId(newConsult.getConsultId());
				if (dbhousPropertyInformation != null) {
					housPropertyInformation.setId(dbhousPropertyInformation.getId());
					housPropertyInformationService.update(housPropertyInformation);
				} else {
					housPropertyInformationService.insert(housPropertyInformation);
				}
			}
			// 保存草稿把id返回去
			Map<String, Object> result = new HashMap<String, Object>();
			PlBorrowRequirement plBorrowRequirement = plBorrowRequirementService
					.getItemInfoByConsultId(newConsult.getConsultId());
			HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService
					.getItemInfoByConsultId(newConsult.getConsultId());
			HousPropertyInformation housPropertyInformation = housPropertyInformationService
					.getItemInfoByConsultId(newConsult.getConsultId());
			List<HousPersonType> housPersonTypes = housPersonTypeService
					.getItemInfoByConsultId(newConsult.getConsultId());
			PlFeeinfo plFeeinfo = plFeeinfoService.getItemInfoByConsultId(newConsult.getConsultId());
			result.put("plBorrowRequirement", plBorrowRequirement);
			result.put("housBorrowingBasics", housBorrowingBasics);
			result.put("housPropertyInformation", housPropertyInformation);
			result.put("plFeeinfo", plFeeinfo);
			result.put("housPersonTypes", housPersonTypes);
			result.put("consultId", newConsult.getConsultId());
			return result;
		}

	}

	private void startProject(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean,String creditConsultFrom) throws Exception {
		String processDefinitionName = "mjsProcess";
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				.processDefinitionName(processDefinitionName).orderByProcessDefinitionVersion().desc().listPage(0, 10);
		if (logger.isDebugEnabled()) {
			logger.debug("流程图版本排列顺序： {}", processDefinitions.toString());
		}
		ProcessDefinition latestProcessDefinition = processDefinitions.get(0);
		logger.info("咨询使用流程定义ID：{}", latestProcessDefinition.getId());
		identityService.setAuthenticatedUserId(preliminaryEvaluationDataBean.getUserName());
		Long projectId;
		ProcessInstance processInstance;
		try {
			Map<String, Object> processGlobalMap = new HashMap<String, Object>();
			processGlobalMap.put(SystemConstant.PROCESS_LAUNCHER, preliminaryEvaluationDataBean.getUserName());
			processGlobalMap.put(SystemConstant.PROCESS_LAUNCHER_ROLEID,
					preliminaryEvaluationDataBean.getLoginUserRoleId());
			processGlobalMap.put(SystemConstant.SECONDARY_AUDIT_AMOUNT_THRESHOLD, SECONDARY_AUDIT_AMOUNT_THRESHOLD);
			processGlobalMap.put(SystemConstant.SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD,
					SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD);
			processGlobalMap.put(SystemConstant.MANAGER_CONFIRMATION_AMOUNT_THRESHOLD,
					MANAGER_CONFIRMATION_AMOUNT_THRESHOLD);
			processGlobalMap.put("creditConsultFrom",creditConsultFrom);


			// TODO FHJ just for testing
			TaskAssignerCenter.processDefinitionId = latestProcessDefinition.getId();
			processInstance = runtimeService.startProcessInstanceById(latestProcessDefinition.getId(),
					latestProcessDefinition.getId() + ":" + preliminaryEvaluationDataBean.getCustomerId(),
					processGlobalMap);
			projectId = createProject(preliminaryEvaluationDataBean, processInstance);
		} catch (RDRuntimeException e) {
			// 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
			throw new ServiceException(e.getMessage(), e);
		}
		preliminaryEvaluationDataBean.setProjectId(projectId);
		preliminaryEvaluationDataBean.setProcessInstanceId(processInstance.getProcessInstanceId());
	}

	private Long createProject(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean,
			ProcessInstance processInstance) throws Exception {
		Long projectId = null;
		PubProject project = new PubProject();
		project.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
		project.setModifyTime(DateUtil.now());
		project.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
		project.setCreateTime(new Date());
		project.setIsDelete(IS_DELETE_NO);
		NOGenerator noGenerator = new ProjectNOGenerator();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sysOfficeService", sysOfficeService);
		params.put("pubProjectService", pubProjectService);
		params.put("preliminaryEvaluationDataBean", preliminaryEvaluationDataBean);
		project.setCode(noGenerator.generateName(params));
		PubCustomer cus = pubCustomerService
				.getItemInfoById(Long.parseLong(preliminaryEvaluationDataBean.getCustomerId()));

		project.setProjectName("客户" + cus.getName() + "借款");
		try {
			projectId = pubProjectService.insert(project);
			projectId = project.getId();
			PubProjectProcess projectProcessRelation = new PubProjectProcess();
			projectProcessRelation.setProcessInstanceId(processInstance.getProcessInstanceId());
			projectProcessRelation.setProjectId(projectId);
			projectProcessRelation.setExtensionNumber(SystemConstant.PUB_PROCESS_TYPE_NOT_EXTENSION);
			projectProcessRelation.setProcessType(SystemConstant.NORMAL_PROCESS_TYPE);
			pubProjectProcessService.insert(projectProcessRelation);
		} catch (PersistentDataException e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败", e, e.getCode());
		}
		return projectId;
	}

	/**
	 * 如果流程开始的地方变了，那么此处的 常量USERTASK_UPLOADING需要修改
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	private void updateConsultWithProcessAndProjectId(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean)
			throws Exception {
		Long consultId = preliminaryEvaluationDataBean.getConsultId();
		String processInstanceId = preliminaryEvaluationDataBean.getProcessInstanceId();
		Long projectId = preliminaryEvaluationDataBean.getProjectId();
		PlConsult plCreditconsult = plCreditconsultDao.getItemInfoById(consultId);
		plCreditconsult.setProcessInstanceId(processInstanceId);
		plCreditconsult.setProjectId(projectId);
		plCreditconsult.setStatus(USERTASK_UPLOADING);
		plCreditconsult.setCustomerId(preliminaryEvaluationDataBean.getCustomerId());
		plCreditconsultDao.update(plCreditconsult);
	}

	public void updatePlCreditconsultById(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean)
			throws Exception {
		CreditConsultModelConverter<PlConsult> creditConsultModelConverter = new CreditConsultModelConverter<PlConsult>();
		PlConsult plCreditconsult = creditConsultModelConverter.convert(preliminaryEvaluationDataBean);
		updateConsult(preliminaryEvaluationDataBean, plCreditconsult);
		plCreditconsultDao.update(plCreditconsult);
	}

	private void updateConsult(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean, PlConsult plCreditconsult) {
		plCreditconsult.setStatus(preliminaryEvaluationDataBean.getNextStep());
		plCreditconsult.setModifyTime(DateUtil.now());
		plCreditconsult.setModifier(preliminaryEvaluationDataBean.getLoginUserId());
	}
	private Long createCreditConsult(SysRole sysRole, PreliminaryEvaluationDataBean preliminaryEvaluationDataBean)
			throws Exception {
		CreditConsultModelConverter<PlConsult> creditConsultModelConverter = new CreditConsultModelConverter<PlConsult>();
		PlConsult plCreditconsult = creditConsultModelConverter.convert(preliminaryEvaluationDataBean);
		// 初始化咨询的状态
		initConsult(preliminaryEvaluationDataBean, plCreditconsult);
		// 根据角色设置
		switch (sysRole.getNid()) {
		case CommonConstant.BUSINESS_ORIGIN_CUSTOMERSERVICESTAFFB_KEY:
			plCreditconsult.setBusinessOrigin(CommonConstant.BUSINESS_ORIGIN_CUSTOMERSERVICESTAFFB_VALUE);
			break;
		case CommonConstant.BUSINESS_ORIGIN_DECLARATIONSTAFF_KEY:
			plCreditconsult.setBusinessOrigin(CommonConstant.BUSINESS_ORIGIN_DECLARATIONSTAFF_VALUE);
			break;
		default:
			plCreditconsult.setBusinessOrigin(CommonConstant.BUSINESS_ORIGIN_APP_VALUE);
			break;
		}
		plCreditconsult.setAdvanceApply((byte) 1);
		plCreditconsult.setCustomerId(preliminaryEvaluationDataBean.getCustomerId());
		plCreditconsultDao.insert(plCreditconsult);
		return plCreditconsult.getId();
	}

	private Long updateCreditConsult(SysRole sysRole, PreliminaryEvaluationDataBean preliminaryEvaluationDataBean)
			throws Exception {
		CreditConsultModelConverter<PlConsult> creditConsultModelConverter = new CreditConsultModelConverter<PlConsult>();
		PlConsult plCreditconsult = creditConsultModelConverter.convert(preliminaryEvaluationDataBean);
		// 初始化咨询的状态
		initConsult(preliminaryEvaluationDataBean, plCreditconsult);
		// 根据角色设置
		switch (sysRole.getNid()) {
		case CommonConstant.BUSINESS_ORIGIN_CUSTOMERSERVICESTAFFB_KEY:
			plCreditconsult.setBusinessOrigin(CommonConstant.BUSINESS_ORIGIN_CUSTOMERSERVICESTAFFB_VALUE);
			break;
		case CommonConstant.BUSINESS_ORIGIN_DECLARATIONSTAFF_KEY:
			plCreditconsult.setBusinessOrigin(CommonConstant.BUSINESS_ORIGIN_DECLARATIONSTAFF_VALUE);
			break;
		default:
			plCreditconsult.setBusinessOrigin(CommonConstant.BUSINESS_ORIGIN_APP_VALUE);
			break;
		}
		plCreditconsult.setId(preliminaryEvaluationDataBean.getConsultId());
		plCreditconsult.setAdvanceApply((byte) 1);
		plCreditconsultDao.update(plCreditconsult);
		return plCreditconsult.getId();
	}

	private void initConsult(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean, PlConsult plCreditconsult) {
		plCreditconsult.setStatus(preliminaryEvaluationDataBean.getNextStep());
		plCreditconsult.setCreator(preliminaryEvaluationDataBean.getLoginUserId());
	}

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void preCheckWorkflowParams(ProjectWorkflowDataBean projectWorkflowDataBean) throws ServiceException {
	}

}