package com.company.modules.instance.service.impl;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProjectDao;
import com.company.common.domain.PubProject;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.service.HousPersonTypeService;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;

/**
 * 流程补全信息
 * @author JDM
 * @date 2016年9月28日
 *
 */
@Service(value = "customerConnectionServiceImpl")
public class CustomerConnectionServiceImpl extends AbstractCustomerEvaluationService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerConnectionServiceImpl.class);
	@Autowired
    protected PubProjectDao pubProjectDao;
	@Autowired
   	private HousPersonTypeService housPersonTypeService;
	
	@Override
	public void evaluation(PreliminaryEvaluationDataBean bean,DelegateTask delegateTask) throws Exception {
		logger.info("进入客服对接...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" + bean);
        }
        preCheckBasicParams(bean);
        preCheckWorkflowParams(bean);
        preCheckCurrentWorkflowState(bean);
        //初评通过
        if (WorkFlowConstant.NEXT_STEP_PASS.equals(bean.getNextStep())) {
            preCheckServiceParams(bean);
        }
        //客服对接相关实体信息保存
        doEvaluation(bean);
        //更新pub_project表project_name字段，否则后面流程取的是null
        PubProject pubProject = pubProjectDao.getItemInfoById(bean.getProjectId());
        if (pubProject!=null) {
        	pubProject.setProjectName("客户" + bean.getHousBorrowingBasics().getName() + "借款");
        	pubProjectDao.update(pubProject);
		}
        //记录审批日志
        recordLoanProcessHistory(bean);
        logger.info("完成客服对接...");
	}
	
	/**
	 * 保存借款需求等信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void doEvaluation(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		try {
			createOrUpdatePlBorrowRequirement(preliminaryEvaluationDataBean);
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
			createOrUpdateHousBorrowingBasics(preliminaryEvaluationDataBean);
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
	}
}