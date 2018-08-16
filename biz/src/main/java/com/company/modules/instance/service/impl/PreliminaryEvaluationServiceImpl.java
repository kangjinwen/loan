package com.company.modules.instance.service.impl;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.system.domain.SysOffice;
import com.company.modules.system.service.SysOfficeService;

/**
 * @author Administrator
 *
 */
@Service(value = "preliminaryEvaluationServiceImpl")
public class PreliminaryEvaluationServiceImpl extends AbstractCustomerEvaluationService {
	private static final Logger logger = LoggerFactory.getLogger(PreliminaryEvaluationServiceImpl.class);
	
	@Autowired
    private PlConsultService plConsultService;
	@Autowired
    private SysOfficeService sysOfficeService;
	
	@Override
	public void evaluation(PreliminaryEvaluationDataBean bean,DelegateTask delegateTask) throws Exception {
		logger.info("进入客服初评...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" + bean);
        }
        preCheckBasicParams(bean);
        preCheckWorkflowParams(bean);
        preCheckCurrentWorkflowState(bean);
        //初评通过
        if (WorkFlowConstant.NEXT_STEP_PASS.equals(bean.getNextStep())) {
            preCheckServiceParams(bean);
            if(StringUtils.isNotBlank(bean.getAssigneeOrg())){
            	delegateTask.setVariable("assigneeOrg", bean.getAssigneeOrg());
            	if(StringUtils.isNotBlank(bean.getAssigneeOrg())){
            		SysOffice so = sysOfficeService.getOfficeById(Long.parseLong(bean.getAssigneeOrg()));
            		bean.setManualAssignee(so.getName());
            	}
            	
            }
        }
        //客服初评相关实体信息保存
        doEvaluation(bean);
        
        PlConsult creditconsult = plConsultService.getItemInfoById(bean.getConsultId());
        if(creditconsult !=null){
        	creditconsult.setOrgId(bean.getAssigneeOrg());
        }
        plConsultService.update(creditconsult);
        //记录审批日志
        recordLoanProcessHistory(bean);
        logger.info("完成客服初评...");
	}
	
	/**
	 * 保存借款需求等信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	protected void doEvaluation(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		try {
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
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
	}
}