package com.company.modules.instance.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.warrant.domain.HousQuickInformation;
import com.company.modules.warrant.service.HousQuickInformationService;

/**
 * @author Administrator
 *
 */
@Service(value = "oritoCustomerConfirmationServiceImpl")
public class OritoCustomerConfirmationServiceImpl extends AbstractCustomerEvaluationService {
	private static final Logger logger = LoggerFactory.getLogger(OritoCustomerConfirmationServiceImpl.class);
	
	@Autowired
	private HousQuickInformationService housQuickInformationService;
	
	@Override
	public void evaluation(PreliminaryEvaluationDataBean bean,DelegateTask delegateTask) throws Exception {
		logger.info("进入下户确认...");
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
        //客服调查相关实体信息保存
        doEvaluation(bean);
        //记录审批日志
        recordLoanProcessHistory(bean);
        logger.info("完成下户确认...");
	}
	
	/**
	 * 保存借款需求等信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void doEvaluation(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		try {
			createOrUpdatePlBorrowRequirement(preliminaryEvaluationDataBean);
			createOrUpdatehousQuickInformation(preliminaryEvaluationDataBean);
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
	}
	
	/**
	 * 修改下户信息房屋快出值
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	private void createOrUpdatehousQuickInformation(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		HousQuickInformation housQuickInformation = housQuickInformationService.getItemInfoByProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
		if(housQuickInformation != null){
			housQuickInformation.setHousingValueFaster(preliminaryEvaluationDataBean.getAmountComment());
			housQuickInformationService.update(housQuickInformation);
		}
	}
}