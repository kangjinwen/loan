package com.company.modules.loanchange.util.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;

import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.ObjectInitUtil;
import com.company.modules.loanchange.service.PostLoanChangeService;

public abstract class BranchProcessExcutionListener extends AbstractExecutionListener{
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        RDDelegateExecution exec = new RDDelegateExecution(execution);
        if(needUpdateProcessBranching()){
            updateProcessBranching(exec);
        }
        if (EVENTNAME_END.equals(eventName)) {
            notifyEndEvent(exec);
        }
        if (EVENTNAME_START.equals(eventName)) {
            notifyStartEvent(exec);
        }
    }
    protected abstract boolean needUpdateProcessBranching();
    /**
     * @description 更新未还款信息到分支流程表
     * @param execution
     * @author wtc
     * @return void
     * @since  1.0.0
    */
    protected void updateProcessBranching(RDDelegateExecution execution)throws ServiceException{
        //分支流程id
        String branchProcessInstanceId=getBranchingProcessInstanceId(execution);
        PubProcessBranchingDao pubProcessBranchingDao=ApplicationContextHelperBean.getBean(PubProcessBranchingDao.class);
        PostLoanChangeService postLoanChangeService=ApplicationContextHelperBean.getBean(PostLoanChangeService.class);
        //分支流程
        PubProcessBranching branch;
        //计算接口
        try {
        	Map params = new HashMap();
			params.put("branchingProcessId",branchProcessInstanceId);
			params.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
            branch = pubProcessBranchingDao.getItemByMap(params);
            ParamChecker.checkEmpty(branch, "分支流程");
            Map<String, Object> map = postLoanChangeService.getRepaymentAmount(branch.getBranchingProcessType(), branchProcessInstanceId,true);
            if(map!=null){
                updateProcessBranchingByMap(map, branch);
            }
        } catch (PersistentDataException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        } 
       
    }
    private void updateProcessBranchingByMap(Map<String,Object> map,PubProcessBranching branch) throws PersistentDataException{
    	PubProcessBranchingDao pubProcessBranchingDao=ApplicationContextHelperBean.getBean(PubProcessBranchingDao.class);
        branch.setCapitalSum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("capitalSum")));
        branch.setInterestSum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("interestSum")));
//        branch.setParkingfeeSum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("parkingFeeSum")));
//        branch.setGpsusingfeeSum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("gpsUsingFeeSum")));
//        branch.setPenaltySum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("realPrepaymentPenalty")));
        branch.setDefaultinterestSum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("defaultInterestSum")));
//        branch.setPlatformfeeSum(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("platformFeeSum")));
//        branch.setViolationsTotalAmount(ObjectInitUtil.initBigDecimal((BigDecimal) map.get("violationsTotalAmount")));
        branch.setPeriod((byte)map.get("period"));
        branch.setModifyTime(new Date());
        branch.setModifier("BranchProcessExcutionListener");
        pubProcessBranchingDao.update(branch);
    }
    protected  String getBranchingProcessInstanceId(RDDelegateExecution execution){
        return execution.getProcessInstanceId();
    }
    
}
