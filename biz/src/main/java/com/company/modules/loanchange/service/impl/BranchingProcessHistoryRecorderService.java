package com.company.modules.loanchange.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.service.CompleteProcessService;
import com.company.common.service.StartProcessService;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.BusinessBaseService;
import com.company.modules.common.service.HistoryRecordableService;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.utils.converter.impl.LoanProcessModelConverter;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.loanchange.util.listener.RDDelegateExecution;

public abstract class BranchingProcessHistoryRecorderService extends BusinessBaseService implements HistoryRecordableService,
    CompleteProcessService,StartProcessService{
    private static final Logger logger = LoggerFactory.getLogger(BranchingProcessHistoryRecorderService.class);

    private static final String REJECTED = "rejected";
    private static final String GAVE_UP = "gave_up";
    private static final String LOANED = "loaned";
    private static final String NEXT_STEP_REJECT_PROCESS = "rejectProcess";
    private static final String NEXT_STEP_CREDIT_CUSTOMER_GIVE_UP = "creditCustomerGiveUp";
    private static final String NEXT_STEP_LOAN_MONEY_SUCCESS = "loanMoneySuccess";
    private static final String NEXT_STEP_COMFIRM_GIVE_UP = "comfirm-give-up";
    private static final String NEXT_STEP_DEDUCT_MONEY_SUCCESS = "deductMoneySuccess";
    private static final String DEDUCTED = "deducted";

    @Autowired
	private PubLoanprocessDao pubLoanprocessDao;
	
	@Autowired
	private PlConsultDao plConsultDao;

    @Autowired
    private PlConsultService plConsultService;
    @Autowired
    protected PubRepayloaninfoDao pubRepayloaninfoDao;
    @Autowired
    protected PlBorrowDao plBorrowDao;
    @Autowired
    private PubProcessBranchingDao pubProcessBranchingDao;
    
    public void startProcessService(RDDelegateExecution execution)  throws ServiceException {
    }
    @Override
    public void completeProcess(BasicServiceDataBean bean,RDDelegateExecution execution) throws ServiceException {
    }
	/**
	 * 记录贷款处理(审批)日志
	 * @param basicServiceDataBean
	 * @throws ServiceException
	 */
	@Override
	public void recordLoanProcessHistory(BasicServiceDataBean basicServiceDataBean) throws ServiceException {
		preCheckServiceParams(basicServiceDataBean);

        preCheckWorkflowParams(basicServiceDataBean);

		doRecordLoanProcessHistory(basicServiceDataBean);

//        // 处理信贷流程结束的状态变更
//        dealWithCreditEndPoint(basicServiceDataBean);
//
//        // 处理车贷流程结束的状态变更
//        dealWithCarEndPoint(basicServiceDataBean);

        // 处理通用的几个流程结束状态
//        dealWithGenericEndPoint(basicServiceDataBean);
        
    }

    private void doRecordLoanProcessHistory(
			BasicServiceDataBean basicServiceDataBean) throws ServiceException {
		LoanProcessModelConverter<PubLoanprocess> loanProcessModelConverter = new LoanProcessModelConverter<PubLoanprocess>();
		PubLoanprocess loanprocess = loanProcessModelConverter.convert(basicServiceDataBean);

		// TODO FHJ 记录贷款处理(审批)日志
		// TODO FHJ 查询SQL
		pubLoanprocessDao.insert(loanprocess);
	}
    /**
     * @description 解锁还款记录
     * @param processInstanceId
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since  1.0.0
    */
    protected void unLockRepayLoanInfo(String processInstanceId)throws ServiceException{
        unLockRepayLoanInfo(processInstanceId, null);
    }
    /**
     * @description 解锁还款记录并更新还款状态
     * @param processInstanceId
     * @param repaymentProcess
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since  1.0.0
    */
	protected void unLockRepayLoanInfo(String processInstanceId, Integer repaymentProcess) throws ServiceException {
		if (!SystemConstant.START_NEW_REPAYMENT) {
			return;
		}
		PubRepayloaninfo pr = pubRepayloaninfoDao.getItemInfoByProcessInstanceId(processInstanceId);
		if (repaymentProcess != null) {
			pr.setRepaymentProcess((byte) (int) repaymentProcess);
		}
		pr.setIslocked((byte) 0);
		pubRepayloaninfoDao.update(pr);
	}
    /**
     * @description 更新贷后变更最终审批状态
     * @param processingOpinion
     * @param branchingProcessInstanceId
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since  1.0.0
    */
    protected void updateBranchingProcessResult(String processingOpinion,String branchingProcessInstanceId)throws ServiceException{
        Map<String,Object> paramMap=new HashMap<String,Object>();
        paramMap.put("branchingProcessId", branchingProcessInstanceId);
        PubProcessBranching ppb=pubProcessBranchingDao.getItemByMap(paramMap);
        ParamChecker.checkEmpty(ppb, "分支流程");
        paramMap.clear();
        paramMap.put("id", ppb.getId());
        paramMap.put("processStatus", "usertask-earlyRepaymentApproval");
        paramMap.put("processingOpinion", processingOpinion);
        pubProcessBranchingDao.updatePubProcessBranchingById(paramMap);
    }
}
