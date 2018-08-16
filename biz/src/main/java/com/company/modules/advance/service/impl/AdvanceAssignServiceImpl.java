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

import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.modules.advance.dao.HousAdvanceApplyDao;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.advance.service.AdvanceAssignService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service
public class AdvanceAssignServiceImpl extends HistoryRecorderService implements AdvanceAssignService {
	private static final Logger logger = LoggerFactory.getLogger(AdvanceAssignServiceImpl.class);
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

	@Transactional
	@Override
	public void advanceAssignTask(BasicServiceDataBean serviceDataBean,
		DelegateTask delegateTask) throws Exception {
		logger.info("垫资任务分配...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" +serviceDataBean);
        }
        preCheckBasicParams(serviceDataBean);
        preCheckWorkflowParams(serviceDataBean);
        preCheckCurrentWorkflowState(serviceDataBean);
        
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
		
		if(WorkFlowConstant.NEXT_STEP_CUSTOMERGIVEUP.equalsIgnoreCase(serviceDataBean.getNextStep())){
			pb.setIsActive((byte) 0);
		}else if(WorkFlowConstant.NEXT_STEP_PASS.equalsIgnoreCase(serviceDataBean.getNextStep())){
			pb.setProcessStatus(HousAdvanceApply.USERTASK_TRANSFER_FILES);
			HousAdvanceApply housAdvanceApply = housAdvanceApplyDao.getItemInfoByProcessInstanceId(serviceDataBean.getProcessInstanceId());
			housAdvanceApply.setStatus(HousAdvanceApply.USERTASK_TRANSFER_FILES);
			housAdvanceApplyDao.update(housAdvanceApply);
		}
		pubProcessBranchingDao.update(pb);
        String manualAssignee = serviceDataBean.getManualAssignee();
		delegateTask.setVariable("ManualAssignee", manualAssignee);//设置任务分配人
		
		//记录审批日志
	    recordLoanProcessHistory(serviceDataBean);
	    logger.info("垫资任务分配完成...");
	}
	
	
}
