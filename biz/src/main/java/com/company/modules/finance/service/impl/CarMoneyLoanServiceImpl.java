package com.company.modules.finance.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProjectProcess;
import com.company.common.domain.PubRepaymentdetail;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.EachPlan;
import com.company.modules.common.utils.databean.LoanDataBean;
import com.company.modules.contract.dao.PlContractDao;
import com.company.modules.contract.domain.PlContract;

public abstract class CarMoneyLoanServiceImpl extends AbstractMoneyLoanService {
	private static final Logger logger = LoggerFactory.getLogger(CarMoneyLoanServiceImpl.class);
	@Autowired
	protected PlProductDao plProductDao;
	@Autowired
	protected PubProjectProcessDao pubProjectProcessDao;
	@Autowired
	private PlContractDao plContractDao;
	@Autowired
	private PlFeeinfoDao plFeeinfoDao;
	@Autowired
	private PubRepaymentdetailDao pubRepaymentdetailDao;

	protected PlFeeinfo getFeeinfo(String processInstanceId) throws ServiceException {
		try {
			return plFeeinfoDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ServiceException("数据库操作失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void loan(LoanDataBean loanDataBean) throws Exception {
		logger.info("进入放款...");
		if (logger.isDebugEnabled()) {
			logger.debug("参数列表：" + loanDataBean);
		}
		if (WorkFlowConstant.agreeStep(loanDataBean.getNextStep())) {
			preCheckBasicParams(loanDataBean);
			preCheckServiceParams(loanDataBean);
			preCheckWorkflowParams(loanDataBean);
			preCheckCurrentWorkflowState(loanDataBean);
			// 获取借款信息
			PlBorrow borrowInfo = updateBorrowInfoAndRetrieveIt(loanDataBean);

//            Map<String, Object> params = new HashMap<>();
//            params.put("processInstanceId", loanDataBean.getProcessInstanceId());
//            params.put("type","lend");
//            try {
//            	HousBills lend = housBillsService.getPageListByMap(params).get(0);
//            	if (lend != null) {				
//            		lend.setRemark(loanDataBean.getRemark());
//            		lend.setThirdAccount(loanDataBean.getThirdAccount());
//            		lend.setThirdTransferFee(loanDataBean.getThirdTransferFee());
//            		housBillsService.update(lend);
//    			}
//    		} catch (Exception e) {
//    			throw new ServiceException(e.getMessage(),e);
//    		}
			// 记录放款信息
			recordLoanInfo(loanDataBean);
			// 根据借款信息，制定回访计划,巡检计划和还款计划
			//TO DO 还款计划
			//makeVisitPlanAndInspectionPlanAndRepaymentDetailPlanByBorrowInfo(loanDataBean, borrowInfo);

//        	ChargeDataStore chargeDataStore = new ChargeDataStore();
//            chargeDataStore.setProcessInstanceId(loanDataBean.getProcessInstanceId());
//            chargeDataStore.setLoanDate(loanDataBean.getLoanTime());
//            chargeDataStoreDao.update(chargeDataStore);
		}
		// 记录审批日志
		recordLoanProcessHistory(loanDataBean);
	}

	protected void updateLoanTime(LoanDataBean loanDataBean) throws ServiceException {
		PlContract contract = plContractDao.getItemInfoByProcessInstanceId(loanDataBean.getProcessInstanceId());
		// 放款时间是合同签署日期
		loanDataBean.setLoanTime(contract.getSignatureTime());
	}

	protected void makeVisitPlanAndInspectionPlanAndRepaymentDetailPlanByBorrowInfo(LoanDataBean loanDataBean,
			PlBorrow borrowInfo) throws ServiceException {
		borrowInfo.setLoanTime(loanDataBean.getLoanTime());// 放款日为当前操作时间
		List<EachPlan> allPlans = createEachPlans(borrowInfo);
		for (int period = 0; period < allPlans.size(); period++) {
			// 制定每期还款计划
			makeEachRepaymentPlan(allPlans.get(period), loanDataBean, borrowInfo);
		}
		// 生成还款信息
		createRepayLoanInfo(borrowInfo, allPlans);
	}

	/**
	 * 制定每期还款计划
	 * 
	 * @param eachPlanData
	 * @param loanDataBean
	 * @param borrowInfo
	 * @throws ServiceException
	 */
	protected void makeEachRepaymentPlan(EachPlan eachPlanData, LoanDataBean loanDataBean, PlBorrow borrowInfo)
			throws ServiceException {
		PubRepaymentdetail item = populateRepaymentDetail(eachPlanData, loanDataBean, borrowInfo);
		try {
			if (item.getRepaymentTime() != null) {
				item.setCustomerId(0L);
				pubRepaymentdetailDao.insert(item);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public abstract List<EachPlan> createEachPlans(PlBorrow borrowInfo) throws ServiceException;

	protected PlProduct getProduct(Long productId) throws ServiceException {
		try {
			return plProductDao.getItemInfoById(productId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 是否是展期
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws ServiceException
	 */
	protected boolean isExtension(String processInstanceId) throws ServiceException {
		try {
			PubProjectProcess ppp = pubProjectProcessDao.getItemByProcessInstanceId(Long.parseLong(processInstanceId));
			return ppp.getExtensionNumber() > 0;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ServiceException("解析流程实例id错误");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ServiceException("数据库操作失败");
		}

	}

}