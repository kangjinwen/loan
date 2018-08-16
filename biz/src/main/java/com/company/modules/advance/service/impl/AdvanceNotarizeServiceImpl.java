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
import com.company.modules.advance.dao.HousAdvanceNotarizeDao;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.advance.domain.HousAdvanceNotarize;
import com.company.modules.advance.service.AdvanceNotarizeService;
import com.company.modules.advance.util.databean.AdvanceNotarizeTaskDataBean;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service
public class AdvanceNotarizeServiceImpl extends HistoryRecorderService implements AdvanceNotarizeService{
	private static final Logger logger = LoggerFactory.getLogger(AdvanceNotarizeServiceImpl.class);
	@Autowired
	private HousAdvanceNotarizeDao housAdvanceNotarizeDao;

	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	
	@Autowired
	private HousAdvanceApplyDao housAdvanceApplyDao;
	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void advanceNotarizeTask(AdvanceNotarizeTaskDataBean serviceDataBean, DelegateTask delegateTask)
			throws Exception {
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
				pb.setProcessStatus(HousAdvanceApply.USERTASK_RISKCONTROLLMANAGER_ASSIGNED);
				HousAdvanceApply housAdvanceApply = housAdvanceApplyDao.getItemInfoByProcessInstanceId(serviceDataBean.getProcessInstanceId());
				housAdvanceApply.setStatus(HousAdvanceApply.USERTASK_RISKCONTROLLMANAGER_ASSIGNED);
				housAdvanceApplyDao.update(housAdvanceApply);
			}
			pubProcessBranchingDao.update(pb);
			//插入垫资公证表
			HousAdvanceNotarize housAdvanceNotarize = new HousAdvanceNotarize();
			housAdvanceNotarize.setCreateTime(new Date());
			housAdvanceNotarize.setProcessInstanceId(serviceDataBean.getProcessInstanceId());
//			housAdvanceNotarize.setProcessInstanceId(serviceDataBean.getOldProcessInstanceId());
			housAdvanceNotarize.setConsultId(serviceDataBean.getConsultId());
			housAdvanceNotarize.setLender(serviceDataBean.getHousAdvanceNotarize().getLender());
			housAdvanceNotarize.setTrusteeOfLender(serviceDataBean.getHousAdvanceNotarize().getTrusteeOfLender());
			housAdvanceNotarize.setTrustee(serviceDataBean.getHousAdvanceNotarize().getTrustee());
			housAdvanceNotarize.setRemark(serviceDataBean.getHousAdvanceNotarize().getRemark());
			housAdvanceNotarize.setProjectId(serviceDataBean.getProjectId());
			housAdvanceNotarizeDao.insert(housAdvanceNotarize);
			
		} catch (Exception e) {
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		recordLoanProcessHistory(serviceDataBean);//记录审批历史
		
	}	

}
