package com.company.modules.advance.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.modules.advance.dao.HousAdvanceApplyDao;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.advance.service.AdvanceConfirmService;
import com.company.modules.advance.util.databean.AdvanceConfirmTaskDataBean;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service
public class AdvanceConfirmServiceImpl extends HistoryRecorderService implements AdvanceConfirmService {
	private static final Logger logger = LoggerFactory.getLogger(AdvanceConfirmServiceImpl.class);
	@Autowired
	private HousAdvanceApplyDao housAdvanceApplyDao;
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 垫资权证总监确认
	 */
	@Transactional
	@Override
	public void advanceConfirmTask(AdvanceConfirmTaskDataBean serviceDataBean, DelegateTask delegateTask)
			throws Exception {
		HousAdvanceApply housAdvanceApply=serviceDataBean.getHousAdvanceApply();
		try {
			PubProcessBranching pubProcessBranching = new PubProcessBranching();
			pubProcessBranching.setProcessingOpinion(serviceDataBean.getNextStep());
			pubProcessBranching.setRemark2(serviceDataBean.getRemarkComment());
			Map map = new HashMap();
			map.put("branchingProcessId", serviceDataBean.getProcessInstanceId());
			map.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
			PubProcessBranching pb = null;
			List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
			if(list==null || list.size()==0){
				throw new ServiceException("查询不到指定的分支流程");
			}
			pb = list.get(0);
			pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
			pb.setRemark2(pubProcessBranching.getRemark2());
			
			pb.setModifier(serviceDataBean.getUserName());
			pb.setModifyTime(new Date());
			pb.setTaskId(serviceDataBean.getTaskId());
			
			//如果驳回，分支流程除了结束，而且也不再显示（但通过查询历史等，是应该可以查询的）
			if(WorkFlowConstant.NEXT_STEP_REFUSE.equalsIgnoreCase(serviceDataBean.getNextStep())){
				pb.setIsActive((byte) 0);
			}else if(WorkFlowConstant.NEXT_STEP_PASS.equalsIgnoreCase(serviceDataBean.getNextStep())){
				pb.setProcessStatus(HousAdvanceApply.USERTASK_LOANING_NOTARIZE);
				housAdvanceApply.setStatus(HousAdvanceApply.USERTASK_LOANING_NOTARIZE);
			}
			pubProcessBranchingDao.update(pb);
			housAdvanceApplyDao.update(housAdvanceApply);
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		recordLoanProcessHistory(serviceDataBean);//记录审批历史
		
	}

	/**
	 * 垫资商务专员确认
	 */
	@Transactional
	@Override
	public void advanceCustomerServiceConfirmTask(
			AdvanceConfirmTaskDataBean serviceDataBean,
			DelegateTask delegateTask) throws Exception {
		HousAdvanceApply housAdvanceApply=serviceDataBean.getHousAdvanceApply();
		try {
			PubProcessBranching pubProcessBranching = new PubProcessBranching();
			pubProcessBranching.setProcessingOpinion(serviceDataBean.getNextStep());
			pubProcessBranching.setRemark2(serviceDataBean.getRemarkComment());
			Map map = new HashMap();
			map.put("branchingProcessId", serviceDataBean.getProcessInstanceId());
			map.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
			PubProcessBranching pb = null;
			List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
			if(list==null || list.size()==0){
				throw new ServiceException("查询不到指定的分支流程");
			}
			pb = list.get(0);
			pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
			pb.setRemark2(pubProcessBranching.getRemark2());
			
			pb.setModifier(serviceDataBean.getUserName());
			pb.setModifyTime(new Date());
			pb.setTaskId(serviceDataBean.getTaskId());
			
			//如果驳回，分支流程除了结束，而且也不再显示（但通过查询历史等，是应该可以查询的）
			if(WorkFlowConstant.NEXT_STEP_CUSTOMERGIVEUP.equalsIgnoreCase(serviceDataBean.getNextStep())){
				pb.setIsActive((byte) 0);
			}else if(WorkFlowConstant.NEXT_STEP_PASS.equalsIgnoreCase(serviceDataBean.getNextStep())){
				pb.setProcessStatus(HousAdvanceApply.USERTASK_LOANING);
				housAdvanceApply.setStatus(HousAdvanceApply.USERTASK_LOANING);
			}
			pubProcessBranchingDao.update(pb);
			housAdvanceApplyDao.update(housAdvanceApply);
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}		
		recordLoanProcessHistory(serviceDataBean);//记录审批历史
		
	}	

	/**
	 * 垫资操作
	 * 
	 */
	@Transactional
	@Override
	public void advanceDisposeServiceConfirmTask(
			AdvanceConfirmTaskDataBean serviceDataBean,
			DelegateTask delegateTask) throws Exception {
		HousAdvanceApply housAdvanceApply=serviceDataBean.getHousAdvanceApply();
		try {
			PubProcessBranching pubProcessBranching = new PubProcessBranching();
			pubProcessBranching.setProcessingOpinion(serviceDataBean.getNextStep());
			pubProcessBranching.setRemark2(serviceDataBean.getRemarkComment());
			Map map = new HashMap();
			map.put("branchingProcessId", serviceDataBean.getProcessInstanceId());
			map.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
			PubProcessBranching pb = null;
			List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
			if(list==null || list.size()==0){
				throw new ServiceException("查询不到指定的分支流程");
			}
			pb = list.get(0);
			pb.setProcessingOpinion(pubProcessBranching.getProcessingOpinion());
			pb.setRemark2(pubProcessBranching.getRemark2());
			pb.setModifier(serviceDataBean.getUserName());
			pb.setModifyTime(new Date());
			pb.setTaskId(serviceDataBean.getTaskId());
			pubProcessBranchingDao.update(pb);
			housAdvanceApplyDao.update(housAdvanceApply);
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}		
		recordLoanProcessHistory(serviceDataBean);//记录审批历史
		
	}	
	
}
