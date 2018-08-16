package com.company.modules.audit.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PlBorrowRequirementDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.utils.DateUtil;
import com.company.common.utils.ParamChecker;
import com.company.common.utils.converter.CarBorrowModelConverter;
import com.company.modules.audit.dao.HousControlInformationDao;
import com.company.modules.audit.dao.HousCreditInformationDao;
import com.company.modules.audit.dao.HousFaceTrialDao;
import com.company.modules.audit.dao.HousMarriageInformationDao;
import com.company.modules.audit.domain.HousControlInformation;
import com.company.modules.audit.domain.HousCreditInformation;
import com.company.modules.audit.domain.HousFaceTrial;
import com.company.modules.audit.domain.HousMarriageInformation;
import com.company.modules.audit.util.databean.AuditDataBean;
import com.company.modules.audit.util.databean.FaceAuditDataBean;
import com.company.modules.common.dao.PlConsultDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;

public abstract class AbstractAuditService extends HistoryRecorderService implements AuditService{

	private static final Logger logger = LoggerFactory.getLogger(AbstractAuditService.class);
	
	@Autowired
	protected PlBorrowDao plBorrowDao ;
	@Autowired
	protected PlBorrowRequirementDao plBorrowRequirementDao ;
	@Autowired
	protected PlConsultDao plConsultDao ;
	@Autowired
	private HousControlInformationDao housControlInformationDao;
	@Autowired
	private HousCreditInformationDao HousCreditInformationDao;
	@Autowired
	private HousFaceTrialDao HousFaceTrialDao;
	@Autowired
	private HousMarriageInformationDao HousMarriageInformationDao;
	
	protected void updateBorrowTable(PlBorrow borrowToBeUpdated)
			throws ServiceException {
		try {
			plBorrowDao.update(borrowToBeUpdated);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	protected void insertBorrowTable(PlBorrow borrowToBeUpdated)
			throws ServiceException {
		try {
			plBorrowDao.insert(borrowToBeUpdated);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	/**
	 * 保存或修改借款需求信息
	 * @param preliminaryEvaluationDataBean
	 * @throws Exception
	 */
	@Transactional
	public void createOrUpdatePlBorrowRequirement(AuditDataBean faceAuditDataBean) throws Exception {
		PlBorrowRequirement plBorrowRequirementInfo = faceAuditDataBean.getPlBorrowRequirement();
		if (plBorrowRequirementInfo!=null) {			
			plBorrowRequirementInfo.setProcessInstanceId(faceAuditDataBean.getProcessInstanceId());
			plBorrowRequirementInfo.setProjectId(faceAuditDataBean.getProjectId());
			plBorrowRequirementInfo.setConsultId(faceAuditDataBean.getConsultId());
		}
		PlBorrowRequirement plBorrowRequirement = plBorrowRequirementDao.getInfoByProcessInstanceId(faceAuditDataBean.getProcessInstanceId());
		if(plBorrowRequirement != null){
			PlConsult plConsult = plConsultDao.getItemInfoByProcessInstanceId(faceAuditDataBean.getProcessInstanceId());
			plConsult.setProductId(plBorrowRequirementInfo.getProductId());
			plConsultDao.update(plConsult);
			plBorrowRequirementInfo.setId(plBorrowRequirement.getId());
			plBorrowRequirementInfo.setModifier(faceAuditDataBean.getLoginUserId());
			plBorrowRequirementInfo.setModifyTime(new Date());
			plBorrowRequirementDao.update(plBorrowRequirementInfo);
		}else{
			plBorrowRequirementInfo.setCreator(faceAuditDataBean.getLoginUserId());
			plBorrowRequirementInfo.setCreateTime(new Date());
			plBorrowRequirementDao.insert(plBorrowRequirementInfo);
		}
	}
	
	@Transactional
    @Override
	public boolean insertOrUpdateAuditInfo(FaceAuditDataBean faceAuditDataBean)
			throws Exception {
		
		String processInstanceId = faceAuditDataBean.getProcessInstanceId();
		long projectId = faceAuditDataBean.getProjectId();
		long consultId = faceAuditDataBean.getConsultId();
		
		HousControlInformation housControlInformation = faceAuditDataBean.getHousControlInformation();
		HousCreditInformation housCreditInformation = faceAuditDataBean.getHousCreditInformation();
		HousFaceTrial housFaceTrial = faceAuditDataBean.getHousFaceTrial();
		HousMarriageInformation housMarriageInformation = faceAuditDataBean.getHousMarriageInformation();
		PlBorrow borrow = faceAuditDataBean.getBorrow();
		
		long housControlInformationNum=0;
		long housCreditInformationNum=0;
		long housFaceTrialNum=0;
		long housMarriageInformationNum=0;
		long borrowNum=0;
		
//		HousControlInformation existHousControlInformation = housControlInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
//		//风控信息
//		if(null == existHousControlInformation){
//			if (housControlInformation!=null) {				
//				housControlInformation.setProcessInstanceId(processInstanceId);
//				housControlInformation.setProjectId(projectId);
//				housControlInformation.setConsultId(consultId);
//				housControlInformationNum = housControlInformationDao.insert(housControlInformation);
//			}
//		}else{
//			housControlInformation.setId(existHousControlInformation.getId());
//			housControlInformationNum = housControlInformationDao.update(housControlInformation);
//		}
		
		if (housControlInformation!=null) {				
			housControlInformation.setProcessInstanceId(processInstanceId);
			housControlInformation.setProjectId(projectId);
			housControlInformation.setConsultId(consultId);
			HousControlInformation existingBorrowItem = null;
	        existingBorrowItem = housControlInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
	        if(existingBorrowItem == null){
	        	housControlInformationNum = housControlInformationDao.insert(housControlInformation);
	        }else{
	        	housControlInformation.setId(existingBorrowItem.getId());
	        	housControlInformationDao.update(housControlInformation);
	        }
		
		}
			
		
	//	HousCreditInformation existHousCreditInformation = HousCreditInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		//征信信息
//		if(null == existHousCreditInformation){
//			if (housCreditInformation!=null) {				
//				housCreditInformation.setProcessInstanceId(processInstanceId);
//				housCreditInformation.setProjectId(projectId);
//				housCreditInformationNum = HousCreditInformationDao.insert(housCreditInformation);
//			}
//		}else{
//			housCreditInformation.setId(existHousCreditInformation.getId());
//			housCreditInformation.setOverdueAmountsNullUpdate(true);
//			housCreditInformation.setSecuredLoanAmountsNullUpdate(true);
//			housCreditInformation.setBadDebtItemsNullUpdate(true);
//			housCreditInformation.setBadDebtAmountsNullUpdate(true);
//			
//			housCreditInformationNum = HousCreditInformationDao.update(housCreditInformation);
//		}
		
		if (housCreditInformation!=null) {				
			housCreditInformation.setProcessInstanceId(processInstanceId);
			housCreditInformation.setProjectId(projectId);
			HousCreditInformation existingBorrowItem = null;
	        existingBorrowItem = HousCreditInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
	        if(existingBorrowItem == null){
	        	housCreditInformationNum = HousCreditInformationDao.insert(housCreditInformation);
	        }else{
	        	housCreditInformation.setId(existingBorrowItem.getId());
	        	housCreditInformationNum =	HousCreditInformationDao.update(housCreditInformation);
	        }
		
		}
		
		//HousFaceTrial existHousFaceTrial = HousFaceTrialDao.getItemInfoByProcessInstanceId(processInstanceId);
		//面审信息
//		if(null == existHousFaceTrial){
//			if (housFaceTrial!=null) {				
//				housFaceTrial.setProcessInstanceId(processInstanceId);
//				housFaceTrial.setProjectId(projectId);
//				housFaceTrial.setConsultId(consultId);
//				housFaceTrialNum = HousFaceTrialDao.insert(housFaceTrial);
//			}
//		}else{
//			housFaceTrial.setId(existHousFaceTrial.getId());
//			housFaceTrialNum = HousFaceTrialDao.update(housFaceTrial);
//		}
		
		if (housFaceTrial!=null) {				
			housFaceTrial.setProcessInstanceId(processInstanceId);
			housFaceTrial.setProjectId(projectId);
			housFaceTrial.setConsultId(consultId);
			HousFaceTrial existingBorrowItem = null;
	        existingBorrowItem = HousFaceTrialDao.getItemInfoByProcessInstanceId(processInstanceId);
	        if(existingBorrowItem == null){
	        	housFaceTrialNum = HousFaceTrialDao.insert(housFaceTrial);
	        }else{
	        	housFaceTrial.setId(existingBorrowItem.getId());
	        	housFaceTrialNum =HousFaceTrialDao.update(housFaceTrial);
	        }
		
		}
		
		if (housMarriageInformation!=null) {				
			housMarriageInformation.setProcessInstanceId(processInstanceId);
			housMarriageInformation.setProjectId(projectId);
			housMarriageInformation.setConsultId(consultId);
			HousMarriageInformation existingBorrowItem = null;
		        existingBorrowItem = HousMarriageInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		        if(existingBorrowItem == null){
		        	HousMarriageInformationDao.insert(housMarriageInformation);
		        }else{
		        	housMarriageInformation.setId(existingBorrowItem.getId());
		        	HousMarriageInformationDao.update(housMarriageInformation);
		        }
			
		}
//		HousMarriageInformation existHousMarriageInformation = HousMarriageInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		//婚姻信息
//		if(null == existHousMarriageInformation){
//			housMarriageInformationNum = HousMarriageInformationDao.insert(housMarriageInformation);
//		}else{
//			housMarriageInformation.setId(existHousMarriageInformation.getId());
//			housMarriageInformationNum = HousMarriageInformationDao.update(housMarriageInformation);
//		}
		
//		PlBorrow existborrow = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
		//借款需求
//		if(existborrow == null){
//			if (borrow!=null) {				
//				borrow.setProcessInstanceId(processInstanceId);
//				borrow.setProjectId(projectId);
//				borrow.setConsultId(consultId);
//				borrowNum = plBorrowDao.insert(borrow);
//			}
//		}else{
//			borrow.setId(existborrow.getId());
//			borrowNum = plBorrowDao.update(borrow);
//		}

		if (borrow!=null) {		
			PlBorrowRequirement plBorrowRequirement = plBorrowRequirementDao.getInfoByProcessInstanceId(faceAuditDataBean.getProcessInstanceId());
			if(plBorrowRequirement != null){
				borrow.setRepaymentType(plBorrowRequirement.getRepaymentType());
			}
			borrow.setProcessInstanceId(processInstanceId);
			borrow.setProjectId(projectId);
			borrow.setConsultId(consultId);
			 PlBorrow existingBorrowItem = null;
		        existingBorrowItem = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
		        if(existingBorrowItem == null){
		        	borrowNum = plBorrowDao.insert(borrow);
		        }else{
		        	borrow.setId(existingBorrowItem.getId());
		        	borrowNum=plBorrowDao.update(borrow);
		        }
		}
//		if(housControlInformationNum > 0 && housCreditInformationNum > 0
//				&& housFaceTrialNum > 0 && housMarriageInformationNum> 0 && borrowNum > 0){
//			return true;
//		}else{
//			return false;
//		}
		return true;
	}
	
	 protected void createOrUpdateBorrowInfo(AuditDataBean auditDataBean) throws ServiceException {
	        logger.info("处理借款信息中...");

	        PlBorrow existingBorrowItem = null;
	        existingBorrowItem = plBorrowDao.getItemInfoByProcessInstanceId(auditDataBean.getProcessInstanceId());

	        if (existingBorrowItem != null) {
	            updateBorrowInfo(auditDataBean, existingBorrowItem);
	        } else {
	            createBorrowInfo(auditDataBean);
	        }
	    }
	    
	 protected void updateBorrowInfo(AuditDataBean auditDataBean, PlBorrow existingBorrowItem) throws ServiceException {
	        logger.info("正在更新借款信息。");
	        ParamChecker.checkEmpty(existingBorrowItem, "existingBorrowItem");

	        CarBorrowModelConverter<PlBorrow> carBorrowModelConverter = new CarBorrowModelConverter<PlBorrow>();

	        PlBorrow borrowToBeUpdated = carBorrowModelConverter.convert(auditDataBean);

	        borrowToBeUpdated.setId(existingBorrowItem.getId());
	        updateBorrowTable(borrowToBeUpdated);
	    }
	    
	 protected void createBorrowInfo(AuditDataBean auditDataBean) throws ServiceException {
	        logger.info("正在创建借款信息。");

	        CarBorrowModelConverter<PlBorrow> carBorrowModelConverter = new CarBorrowModelConverter<PlBorrow>();
	        PlBorrow borrowToBeUpdated = carBorrowModelConverter.convert(auditDataBean);

	        borrowToBeUpdated.setCreateTime(DateUtil.now());
	        borrowToBeUpdated.setCreator(auditDataBean.getLoginUserId());
	        
	        insertBorrowTable(borrowToBeUpdated);
	    }
}
