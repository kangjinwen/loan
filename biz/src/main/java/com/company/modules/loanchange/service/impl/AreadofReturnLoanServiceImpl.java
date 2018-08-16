package com.company.modules.loanchange.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.loanchange.domain.LoanChangeDataBean;
import com.company.modules.loanchange.service.ApproveService;
import com.company.modules.loanchange.util.listener.RDDelegateExecution;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

/**
 * 提前还款审批服务
 * @author JDM
 * @date 2016年9月21日
 *
 */
@Service("areadofReturnLoanServiceImpl")
public class AreadofReturnLoanServiceImpl extends BranchingProcessHistoryRecorderService implements ApproveService {
	private static final Logger logger = LoggerFactory.getLogger(AreadofReturnLoanServiceImpl.class);

	@Autowired
	PlBorrowDao plBorrowDao;

	@Autowired
	PubProcessBranchingDao pubProcessBranchingDao;

//	@Autowired
//	CarDisposalLogDao carDisposalLogDao;
	
	@Autowired
	PubRepayloaninfoDao pubRepayloaninfoDao;
	
	@Autowired
	TaskService taskService; 
	@Autowired
	PlConsultService plConsultService;
	
	/**
	* @Title: Approve
	* @Description: TODO(提前还款审批)
	* @param @param approveDataBean
	* @see @see com.company.modules.carloan.service.CarApproveService#Approve(com.company.modules.carloan.utils.databean.ApproveDataBean)
	* @throws
	*/
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void Approve(LoanChangeDataBean loanChangeDataBean)throws ServiceException {
			loanChangeDataBean.setRejectionCategoryComment("不适用");
		
//			PlBorrow plBorrow = new PlBorrow();
			PubProcessBranching pubProcessBranching = new PubProcessBranching();
			pubProcessBranching.setProcessingOpinion(loanChangeDataBean.getNextStep());
			pubProcessBranching.setRemark2(loanChangeDataBean.getRemarkComment());
			Map map = new HashMap();
			map.put("branchingProcessId", loanChangeDataBean.getProcessInstanceId());
			map.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
				//这里面 有可能一个项目id返回多条数据 需重构
//				PlBorrow pb = plBorrowDao.getItemByMap(map, "PlBorrow");
				PubProcessBranching pb = null;
				List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
				if(list==null || list.size()==0){
					throw new ServiceException("查询不到指定的分支流程");
				}
				pb = list.get(0);
				pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
				pb.setRemark2(pubProcessBranching.getRemark2());
				
				pb.setModifier(loanChangeDataBean.getUserName());
				pb.setModifyTime(new Date());
				pb.setTaskId(loanChangeDataBean.getTaskId());
				
				if(WorkFlowConstant.NEXT_STEP_APPROVE.equalsIgnoreCase(loanChangeDataBean.getNextStep())){
					pb.setRepaymentProcess((byte) 1);
				}
				//如果驳回，分支流程除了结束，而且也不再显示（但通过查询历史等，是应该可以查询的）
				if(WorkFlowConstant.NEXT_STEP_REFUSE.equalsIgnoreCase(loanChangeDataBean.getNextStep())){
					pb.setIsActive((byte) 0);
				}
				
//				plBorrowDao.update(pb);
				pubProcessBranchingDao.update(pb);
				
				try {
					PlConsult consult = plConsultService.getItemInfoByProcessInstanceId(pb.getProcessInstanceId());
					if (consult != null) {
						loanChangeDataBean.setConsultId(consult.getId());
					}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			
				recordLoanProcessHistory(loanChangeDataBean);
				//无论审批通过还是驳回，都提示 下一步 还款专员
//				if(!WorkFlowConstant.NEXT_STEP_REJECT.equalsIgnoreCase(carLoanChangeDataBean.getNextStep())){
					TaskAssignerCenter.isNew= true;
//					TaskAssignerCenter.taskAssignee.set(carLoanChangeDataBean.getAssignee()); //"还款专员";
//				}
	}
	@Transactional(rollbackFor=Exception.class)
    @Override
    public void completeProcess(BasicServiceDataBean bean,RDDelegateExecution execution) throws ServiceException {
        //通过，完成提前还款流程
        Object originalProcessInstanceId=execution.getVariables().get(SystemConstant.ORIGINAL_PROCESSINSTANCEID);
        if(bean.getNextStep().equals(WorkFlowConstant.NEXT_STEP_APPROVE)){
          //解锁更新还款款信息
            unLockRepayLoanInfo(originalProcessInstanceId.toString(), 1);
        }else{
            //驳回
            unLockRepayLoanInfo(originalProcessInstanceId.toString());
        } 
        updateBranchingProcessResult(bean.getNextStep(), execution.getProcessInstanceId());
        
    }


    @Override
    protected void preCheckWorkflowParams(ProjectWorkflowDataBean projectWorkflowDataBean) throws ServiceException {
        logger.info("预先检查流程相关的参数。");
        try {
//            ParamChecker.checkEmpty(projectWorkflowDataBean.getConsultId(), "consultid");
            ParamChecker.checkEmpty(projectWorkflowDataBean.getProjectId(), "projectId");
            ParamChecker.checkEmpty(projectWorkflowDataBean.getProcessInstanceId(), "processInstanceId");
        } catch (IllegalArgumentException e) {
            throwServiceExceptionAndLog(logger, e.getMessage(), e, Constant.FAIL_CODE_PARAM_INSUFFICIENT);
        }

    }

    @Override
    protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
            throws ServiceException {

    }
	@Override
	public void startProcessService(RDDelegateExecution execution)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
}
