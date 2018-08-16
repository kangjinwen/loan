package com.company.modules.contract.service.impl;

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
import com.company.modules.chargedata.dao.ChargeDataStoreDao;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.contract.dao.PlBankcardDao;
import com.company.modules.contract.dao.PlContractDao;
import com.company.modules.contract.domain.PlBankcard;
import com.company.modules.contract.domain.PlContract;
import com.company.modules.contract.service.ContractService;
import com.company.modules.contract.utils.databean.ContractDataBean;

/**
 * 签合同
 * @author FHJ
 *
 */
@Service("contractServiceImpl")
public class ContractServiceImpl extends HistoryRecorderService implements ContractService{
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    
    private static final String CUSTOMER_GIVE_UP = "customerGiveUp";
    private static final String CONTRACT_PIC = "Contract_Pic";

    @Autowired
    private PlBankcardDao plBankcardDao;
    @Autowired
    private PlContractDao plcontractDao;
    @Autowired
    private PubAttachmentService pubAttachmentService;
    @Autowired
    private ChargeDataStoreDao chargeDataStoreDao;

	@Override
	@Transactional
	public void signContract(ContractDataBean contractDataBean) throws Exception {
		logger.info("进入签合同...");

		if(logger.isDebugEnabled()) {
			logger.debug("参数列表：" + contractDataBean);
		}

    	preCheckBasicParams(contractDataBean);
    	preCheckServiceParams(contractDataBean);
    	preCheckWorkflowParams(contractDataBean);
    	preCheckCurrentWorkflowState(contractDataBean);
        // 更新合同的状态
    	if (!contractDataBean.getNextStep().equals("endReturnFee")) {			
    		updateContractStatus(contractDataBean);
    		createChargeDataStore(contractDataBean);
		}
    	// 记录审批日志
		recordLoanProcessHistory(contractDataBean);
		logger.info("完成签合同...");
	}

	/**
	 * 生成押品待入库信息
	 * @param contractDataBean
	 * @throws Exception
	 */
    private void createChargeDataStore(ContractDataBean contractDataBean) throws Exception {
		ChargeDataStore chargeDataStore = chargeDataStoreDao.queryBorrowInfoByProcessInstanceId(contractDataBean.getProcessInstanceId());
    	chargeDataStore.setChargeStatus(ChargeDataStore.CHARGESTATUS_WAIT_IN_STORE);
    	chargeDataStoreDao.insert(chargeDataStore);
	}

	private void updateContractStatus(ContractDataBean contractDataBean) throws Exception {
        // 在改状态之前，先检查下合同是否已经生成
//        checkIfTheContractHasBeenGenerated(contractDataBean);
        // 在改状态之前，检查下合同附件是否已经上传
        checkIfTheContractHasBeenUpLoaded(contractDataBean);
        String processInstanceId = contractDataBean.getProcessInstanceId();
        if (WorkFlowConstant.NEXT_STEP_PASS.equals(contractDataBean.getNextStep())) {
        	doUpdateContractWithStatus(processInstanceId, SystemConstant.CONTRACT_STATUS_SIGNED);
        } else if (CUSTOMER_GIVE_UP.equals(contractDataBean.getNextStep())) {
        	doUpdateContractWithStatus(processInstanceId, SystemConstant.CONTRACT_STATUS_FAILED_SIGN);
        }else {
        	throwServiceExceptionAndLog(logger, "不支持的处理意见！", Constant.FAIL_CODE_VALUE);
        }
    }

    private void doUpdateContractWithStatus(String processInstanceId, Byte contractStatus) throws Exception {
        PlContract plContract = plcontractDao.getItemInfoByProcessInstanceId(processInstanceId);
        if(plContract != null){
        	plContract.setStatus(contractStatus);
    		plcontractDao.update(plContract);
        }
    }

    @Override
	protected void preCheckServiceParams(BasicServiceDataBean basicServiceDataBean)
			throws ServiceException {
		super.preCheckServiceParams(basicServiceDataBean);
	}

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean) throws ServiceException {
	}

    private void checkIfTheContractHasBeenUpLoaded(ContractDataBean contractDataBean) throws Exception {
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

	/**
	 * 检查合同和银行卡的信息有没有录入，如果没有录入，流程不能往下走。
	 * @param projectWorkflowDataBean
	 * @throws Exception
	 */
	private void checkIfTheContractHasBeenGenerated(ProjectWorkflowDataBean projectWorkflowDataBean) throws Exception {
		String processInstanceId = projectWorkflowDataBean.getProcessInstanceId();
        PlBankcard plBankcard = plBankcardDao.getItemInfoByProcessInstanceId(processInstanceId);
        if(plBankcard == null) {
        	throwServiceExceptionAndLog(logger, "合同信息没保存，请先返回保存合同。", Constant.FAIL_CODE_PARAM_INSUFFICIENT);
		}
	}
}
