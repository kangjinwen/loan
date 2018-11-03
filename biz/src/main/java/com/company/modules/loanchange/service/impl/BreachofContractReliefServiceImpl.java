package com.company.modules.loanchange.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubRepaymentdetail;
import com.company.common.service.CompleteProcessService;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.loanchange.dao.PostLoanChangeDao;
import com.company.modules.loanchange.domain.LoanChangeDataBean;
import com.company.modules.loanchange.service.ApproveService;
import com.company.modules.loanchange.util.listener.RDDelegateExecution;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

@Service("breachofContractReliefService")
public class BreachofContractReliefServiceImpl extends BranchingProcessHistoryRecorderService
		implements ApproveService, CompleteProcessService {
	private static final Logger logger = LoggerFactory.getLogger(BreachofContractReliefServiceImpl.class);

	@Autowired
	PlBorrowDao plBorrowDao;

	@Autowired
	PostLoanChangeDao postLoanChangeDao;

	@Autowired
	PubProcessBranchingDao pubProcessBranchingDao;
	@Autowired
	private PubRepaymentdetailDao repaymentdetailDao;

	/**
	 * @throws ServiceException @Title: Approve @Description:
	 *                          (违约金减免审批) @param @param approveDataBean @see @see
	 *                          com.company.modules.carloan.service.impl.CarApproveServiceImpl#Approve(com.company.modules.carloan.utils.databean.ApproveDataBean) @throws
	 */
	@Override
	public void Approve(LoanChangeDataBean loanChangeDataBean) throws ServiceException {
		loanChangeDataBean.setRejectionCategoryComment("不适用");

//		PlBorrow plBorrow = new PlBorrow();
		PubProcessBranching pubProcessBranching = new PubProcessBranching();
		pubProcessBranching.setProcessingOpinion(loanChangeDataBean.getNextStep());
		pubProcessBranching.setRemark2(loanChangeDataBean.getRemarkComment());

		String isCompleted = "false";
		String processInstanceId = null;
		try {
			processInstanceId = queryProcessInstanceIdByTask(loanChangeDataBean.getTaskId(), isCompleted);
		} catch (PersistentDataException e1) {
			logger.info("错误", e1.getMessage());
			throw new RDRuntimeException("流程实例不存在");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("processInstanceId", processInstanceId);
		map.put("isActive", WorkFlowConstant.IS_ACTIVE_TRUE);

		// 这里面 有可能一个项目id返回多条数据 需重构
//			PlBorrow pb = plBorrowDao.getItemByMap(map, "PlBorrow");
		List<PubProcessBranching> list = pubProcessBranchingDao.getPageListByMap(map);
		PubProcessBranching pb = (list != null && list.size() > 0) ? list.get(0) : new PubProcessBranching();
		pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
		pb.setRemark2(pubProcessBranching.getRemark2());
		if ("pass".equalsIgnoreCase(loanChangeDataBean.getNextStep())) {
			pb.setRepaymentProcess((byte) 2);
		}
		// 如果驳回，分支流程除了结束，而且也不再显示（但通过查询历史等，是应该可以查询的）
		if (WorkFlowConstant.NEXT_STEP_REJECT.equalsIgnoreCase(loanChangeDataBean.getNextStep())) {
			pb.setIsActive((byte) 0);
		}
		pubProcessBranchingDao.update(pb);

		try {
			recordLoanProcessHistory(loanChangeDataBean);
			if (!WorkFlowConstant.NEXT_STEP_REJECT.equalsIgnoreCase(loanChangeDataBean.getNextStep())) {
				TaskAssignerCenter.isNew = true;
//				TaskAssignerCenter.taskAssignee.set(carLoanChangeDataBean.getAssignee()); //"还款专员";
			} else {
				TaskAssignerCenter.isNew = false;
//				TaskAssignerCenter.taskAssignee.set(""); //"还款专员";
			}
//			TaskAssignerCenter.isNew= true;
//			TaskAssignerCenter.taskAssignee=carLoanChangeDataBean.getAssignee(); //"还款专员";
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		logger.info("预先检查流程相关的参数。");
		try {
//	            ParamChecker.checkEmpty(projectWorkflowDataBean.getConsultid(), "consultid");
			ParamChecker.checkEmpty(projectWorkflowDataBean.getProjectId(), "projectId");
			ParamChecker.checkEmpty(projectWorkflowDataBean.getProcessInstanceId(), "processInstanceId");
		} catch (IllegalArgumentException e) {
			throwServiceExceptionAndLog(logger, e.getMessage(), e, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
		}

	}

	// 获取branching_process_id
	public String queryProcessInstanceIdByTask(String taskId, String isCompleted) throws PersistentDataException {
		Map<?, ?> map = postLoanChangeDao.queryProcessInstanceByTask(taskId, isCompleted);
		return (String) map.get("processInstanceId");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void completeProcess(BasicServiceDataBean bean, RDDelegateExecution execution) throws ServiceException {
		String originalProcessInstanceId = execution.getVariable(SystemConstant.ORIGINAL_PROCESSINSTANCEID,
				String.class);
		if (bean.getNextStep().equals("pass")) {
			// 解锁更新还款款信息
			unLockRepayLoanInfo(originalProcessInstanceId.toString(), 2);
		} else {
			// 驳回
			unLockRepayLoanInfo(originalProcessInstanceId.toString());
			recoverRepaymentDetail(execution);
		}
		updateBranchingProcessResult(bean.getNextStep(), execution.getProcessInstanceId());
	}

	private void recoverRepaymentDetail(RDDelegateExecution execution) throws ServiceException {
		Long id = execution.getVariable("repaymentdetail_id", Long.class);
		BigDecimal pre_breachContract = execution.getVariable("pre_breachContract", BigDecimal.class);
		BigDecimal pre_reductionPenalty = execution.getVariable("pre_reductionPenalty", BigDecimal.class);
		BigDecimal pre_allBreaks = execution.getVariable("pre_allBreaks", BigDecimal.class);
//        Map<String,Object> data=new HashMap<String,Object>();
//        data.put("id", id);
//        data.put("breachContract",pre_breachContract);
//        data.put("reductionPenalty",pre_reductionPenalty);
//        data.put("allBreaks",pre_allBreaks);
		PubRepaymentdetail repaymentDetail = new PubRepaymentdetail();
		repaymentDetail.setId(id);
		repaymentDetail.setBreachContract(pre_breachContract);
		repaymentDetail.setReductionPenalty(pre_reductionPenalty);
		repaymentDetail.setAllBreaks(pre_allBreaks);
		repaymentdetailDao.update(repaymentDetail);
	}

}
