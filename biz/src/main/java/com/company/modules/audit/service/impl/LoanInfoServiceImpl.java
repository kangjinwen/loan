package com.company.modules.audit.service.impl;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.dao.PlBorrowRequirementDao;
import com.company.common.domain.PlBorrowRequirement;
import com.company.modules.audit.dao.HousBillsDao;
import com.company.modules.audit.dao.HousLoanfeesDao;
import com.company.modules.audit.domain.HousBills;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.audit.service.HousBillsService;
import com.company.modules.audit.service.HousLoanfeesService;
import com.company.modules.audit.service.LoanInfoService;
import com.company.modules.audit.util.databean.LoanInfoDataBean;
import com.company.modules.collateral.dao.CollateralManageDao;
import com.company.modules.collateral.domain.HousMortgageRegistration;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@SuppressWarnings("all")
@Service(value="loanInfoServiceImpl")
public class LoanInfoServiceImpl extends HistoryRecorderService implements LoanInfoService {

	@Autowired
	private HousBillsDao housBillsDao;
	@Autowired
	private HousLoanfeesDao housLoanfeesDao;
	@Autowired
	private HousBillsService housBillsService;
	@Autowired
	private HousLoanfeesService housLoanfeesServiceImpl;
	@Autowired
	protected PlBorrowRequirementDao plBorrowRequirementDao;
	@Autowired
	private CollateralManageDao collateralManageDao;
	
	@Override
	@Transactional
	public void writeLoanInfo(LoanInfoDataBean serviceDataBean, DelegateTask delegateTask) throws Exception {
		//插入或者更新放款单据信息
		//if (serviceDataBean.getNextStep().equals(WorkFlowConstant.NEXT_STEP_PASS)) {
			boolean flag = insertOrUpdatehousLoanfees(serviceDataBean);
		//}
		
		//记录审批日志
        recordLoanProcessHistory(serviceDataBean);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean insertOrUpdatehousLoanfees(LoanInfoDataBean serviceDataBean) throws Exception {
		
		String processInstanceId = serviceDataBean.getProcessInstanceId();
		long projectId = serviceDataBean.getProjectId();
		
		HousLoanfees housLoanfees = serviceDataBean.getHousLoanfees();
		HousBills lend = serviceDataBean.getLend();
		List<HousBills> loans = serviceDataBean.getLoans();
		
		Long loginUserId = serviceDataBean.getLoginUserId();
		long housLoanfeesNum = 0;
		long lendNum = 0;
		PlBorrowRequirement plBorrowRequirement = plBorrowRequirementDao.getInfoByProcessInstanceId(serviceDataBean.getProcessInstanceId());
		if(plBorrowRequirement != null){	
			plBorrowRequirement.setCollectionRate(serviceDataBean.getCollectionRate());
			plBorrowRequirement.setCollectionServiceFee(serviceDataBean.getCollectionServiceFee());
			plBorrowRequirement.setCollectionServiceBank(serviceDataBean.getCollectionServiceBank());
			plBorrowRequirement.setCollectionServiceCard(serviceDataBean.getCollectionServiceCard());
			plBorrowRequirement.setCollectionServiceName(serviceDataBean.getCollectionServiceName());
			plBorrowRequirement.setModifier(serviceDataBean.getLoginUserId());
			plBorrowRequirement.setModifyTime(new Date());
			plBorrowRequirementDao.update(plBorrowRequirement);
		}
		if(housLoanfees != null ){
			housLoanfees.setProcessinstanceid(processInstanceId);
			housLoanfees.setProjectId(projectId);
			housLoanfees.setCollectionServiceBank(serviceDataBean.getCollectionServiceBank());
			housLoanfees.setCollectionServiceCard(serviceDataBean.getCollectionServiceCard());
			housLoanfees.setCollectionServiceFee(serviceDataBean.getCollectionServiceFee());
			housLoanfees.setCollectionServiceName(serviceDataBean.getCollectionServiceName());
			housLoanfees.setReturnServiceFee(serviceDataBean.getReturnServiceFee());
			housLoanfeesNum = housLoanfeesServiceImpl.insertOrUpdate(housLoanfees,loginUserId);
		}
		if(lend != null ){
			lend.setProcessInstanceId(processInstanceId);
			lendNum = housBillsService.insertOrupdate(lend, loginUserId, "lend");//放款
			//如果放款单修改了信息把抵押登记也更新下(抵押登记肯定是有条数据的，只用update就可以了)
			HousMortgageRegistration hmr=collateralManageDao.getHousMortgageRegistrationsByInstanceId(processInstanceId);
			if(hmr!=null){
				hmr.setModifier(serviceDataBean.getLoginUserId());
				hmr.setModifyTime(new Date());
				hmr.setThirdCardNumber(lend.getThirdCardNumber());
				hmr.setThirdBankAccount(lend.getThirdBankAccount());
				hmr.setThirdAccountOpening(lend.getThirdAccountOpening());
				hmr.setCreditCardNumber(lend.getCardid());
				hmr.setBankAccount(lend.getAccountHolderName());
				hmr.setAccountOpening(lend.getBankName());
				hmr.setRemark(lend.getRemark());
				collateralManageDao.update(hmr);
			}
		}
		
		if(CollectionUtils.isNotEmpty(loans)){
			for (HousBills housBills : loans) {
				housBills.setProcessInstanceId(processInstanceId);
				long loanNum = housBillsService.insertOrupdate(housBills, loginUserId, "loan");//打款
				if(loanNum <= 0){
					throw new ServiceException("插入或者更新打款失败");
				}
			}
		}
		if(housLoanfeesNum > 0L && lendNum > 0L){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

}
