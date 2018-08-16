package com.company.modules.instance.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.converter.impl.LoanProcessModelConverter;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;

/**
 * 信息筛查
 */
@Service(value = "customerSurveyServiceImpl")
public class CustomerSurveyServiceImpl extends AbstractCustomerEvaluationService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerSurveyServiceImpl.class);
	
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	
	@Autowired
	private PubLoanprocessDao pubLoanprocessDao;
	@Override
	public void evaluation(PreliminaryEvaluationDataBean bean,DelegateTask delegateTask) throws Exception {
		logger.info("进入客服调查...");
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
        //面审补充资料
        if(StringUtils.isNotBlank(bean.getBranchingProcessId())){
        	PubProcessBranching pubProcessBranching = new PubProcessBranching();
			pubProcessBranching.setProcessingOpinion(bean.getNextStep());
			pubProcessBranching.setRemark1(bean.getRemarkComment());
			Map map = new HashMap();
			map.put("branchingProcessId", bean.getBranchingProcessId());
			map.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
			PubProcessBranching pb = null;
			List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
			if(list==null || list.size()==0){
				throw new ServiceException("查询不到指定的分支流程");
			}
			pb = list.get(0);
			pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
			pb.setRemark2(pubProcessBranching.getRemark2());
			
			pb.setModifier(bean.getUserName());
			pb.setModifyTime(new Date());
			pb.setTaskId(bean.getTaskId());
			pb.setProcessStatus("usertask-supplyInvestigate");
			pubProcessBranchingDao.update(pb);
			LoanProcessModelConverter<PubLoanprocess> loanProcessModelConverter = new LoanProcessModelConverter<PubLoanprocess>();
			PubLoanprocess loanprocess = loanProcessModelConverter.convert(bean);
			loanprocess.setProcessInstanceId(bean.getBranchingProcessId());
			// TODO FHJ 记录贷款处理(审批)日志
			// TODO FHJ 查询SQL
			pubLoanprocessDao.insert(loanprocess);
        }else{
        	//记录审批日志
        	recordLoanProcessHistory(bean);
        }
        logger.info("完成客服调查...");
	}
	
	/**
	 * 保存借款需求等信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	public void doEvaluation(PreliminaryEvaluationDataBean preliminaryEvaluationDataBean) throws Exception {
		try {
			createOrUpdatePlBorrowRequirement(preliminaryEvaluationDataBean);
			if(preliminaryEvaluationDataBean.getHousEnquiryInstitution() != null){
				for (HousEnquiryInstitution housEnquiryInstitution : preliminaryEvaluationDataBean.getHousEnquiryInstitution()) {
					housEnquiryInstitution.setProcessInstanceId(preliminaryEvaluationDataBean.getProcessInstanceId());
					housEnquiryInstitution.setProjectId(preliminaryEvaluationDataBean.getProjectId());
					housEnquiryInstitution.setConsultId(preliminaryEvaluationDataBean.getConsultId());
					createOrUpdateHousEnquiryInstitution(preliminaryEvaluationDataBean, housEnquiryInstitution);
				}
			}
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
	}
}