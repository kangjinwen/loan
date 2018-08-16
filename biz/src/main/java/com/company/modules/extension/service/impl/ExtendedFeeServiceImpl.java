package com.company.modules.extension.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.extension.service.ExtendedFeeService;
import com.company.modules.extension.utils.databean.ExtendedFeeDataBean;
/**
 * @author Administrator
 *
 */
@Service
public class ExtendedFeeServiceImpl extends HistoryRecorderService implements ExtendedFeeService{
	private static final Logger logger = LoggerFactory.getLogger(ExtendedFeeServiceImpl.class);
	
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fee(ExtendedFeeDataBean dataBean) throws Exception {
		PubProcessBranching pubProcessBranchingParam = dataBean.getPubProcessBranching();
		// TODO Auto-generated method stub
		if (dataBean.getNextStep().equals("pass")) {
			PubProcessBranching pubProcessBranching  = pubProcessBranchingDao.getItemInfoById(pubProcessBranchingParam.getId());
			if (pubProcessBranching!=null) {
				pubProcessBranching.setCollectionForm(pubProcessBranchingParam.getCollectionForm());
				pubProcessBranching.setActualExtensionFee(pubProcessBranchingParam.getActualExtensionFee());
				pubProcessBranching.setFinanceSpecialist(pubProcessBranchingParam.getFinanceSpecialist());
				pubProcessBranching.setModifyTime(new Date());
				pubProcessBranchingDao.update(pubProcessBranching);
			}	
		}
				
		        //记录审批日志
		        recordLoanProcessHistory(dataBean);
		       
		
	}

	
}
