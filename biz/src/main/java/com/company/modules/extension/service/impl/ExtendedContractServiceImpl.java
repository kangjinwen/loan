package com.company.modules.extension.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.extension.service.ExtendedContractService;
import com.company.modules.extension.utils.databean.ExtendedContractDataBean;
/**
 * @author Administrator
 *
 */
@Service
public class ExtendedContractServiceImpl extends HistoryRecorderService implements ExtendedContractService{
	private static final Logger logger = LoggerFactory.getLogger(ExtendedContractServiceImpl.class);
	private static final String CONTRACT_PIC = "ROLLCONTACT";
	 
	 
	 @Autowired
	 private PubAttachmentService pubAttachmentService;
	


	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void contract(ExtendedContractDataBean dataBean) throws Exception {
		// TODO Auto-generated method stub
				logger.info("展期合同开始...");
		        if (logger.isDebugEnabled()) {
		            logger.debug("参数列表：" +dataBean);
		        }
		        preCheckBasicParams(dataBean);
		    	preCheckServiceParams(dataBean);
		    	preCheckWorkflowParams(dataBean);
		    	preCheckCurrentWorkflowState(dataBean);
		    	//检查下合同附件是否已经上传
		        checkIfTheContractHasBeenUpLoaded(dataBean);
		        //记录审批日志
		        recordLoanProcessHistory(dataBean);
		        logger.info("展期合同结束...");
		
	}
	
	  private void checkIfTheContractHasBeenUpLoaded(ExtendedContractDataBean contractDataBean) throws Exception {
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("btype", CONTRACT_PIC);
	        paramMap.put("processInstanceId", contractDataBean.getProcessInstanceId());
	        long count = 0;
	    	List<PubAttachment> plAttachment = pubAttachmentService.select(paramMap);
	        count = plAttachment.size();
	        if (count <= 0) {
	            throwServiceExceptionAndLog(logger, "合同附件没有上传，请上传后再提交。", Constant.FAIL_CODE_VALUE);
	        }
	    }
	
}
