package com.company.modules.notary.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PlBorrowRequirementDao;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.utils.DateUtil;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.instance.dao.HousBorrowingBasicsDao;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.notary.dao.NotaryManageDao;
import com.company.modules.notary.databean.NotaryRegistDataBean;
import com.company.modules.notary.domain.HousNotarizationRegistration;
import com.company.modules.notary.workflow.service.NotaryRegistService;

@SuppressWarnings("rawtypes")
@Service(value = "notaryRegistServiceImpl")
public class NotaryRegistServiceImpl extends HistoryRecorderService implements NotaryRegistService{

	private static final Logger logger = LoggerFactory.getLogger(NotaryRegistServiceImpl.class);
	
	@Autowired
	private NotaryManageDao notaryManageDao;
	
	@Autowired
   	private PlBorrowRequirementDao plBorrowRequirementDao;
   	@Autowired
   	private PlFeeinfoDao plFeeinfoDao;
   	@Autowired
   	private HousBorrowingBasicsDao housBorrowingBasicsDao;
   	
	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void regist(NotaryRegistDataBean dataBean) throws Exception {
		// TODO Auto-generated method stub
				logger.info("公证登记...");
		        if (logger.isDebugEnabled()) {
		            logger.debug("参数列表：" +dataBean);
		        }
		        preCheckBasicParams(dataBean);
		        preCheckWorkflowParams(dataBean);
		        preCheckCurrentWorkflowState(dataBean);
		        //登记通过
		        if (WorkFlowConstant.NEXT_STEP_PASS.equals(dataBean.getNextStep())) {
		            preCheckServiceParams(dataBean);
		        }
		        //更新登记信息
		        createOrUpdateNotaryRegist(dataBean);
		        //更新基本信息、借款需求信息
		        updateHousBorrowingBasics(dataBean);
		        updatePlBorrowRequirement(dataBean);
		        //记录审批日志
		        recordLoanProcessHistory(dataBean);
		        logger.info("公证登记完成...");
	}
	
	public void createOrUpdateNotaryRegist(NotaryRegistDataBean dataBean) throws ServiceException{
				try {
					HousNotarizationRegistration housNotarizationRegistration=dataBean.getHousNotarizationRegistration();
					if (housNotarizationRegistration!=null) {						
						housNotarizationRegistration.setConsultId(dataBean.getConsultId());
						housNotarizationRegistration.setProjectId(dataBean.getProjectId());
						housNotarizationRegistration.setProcessInstanceId(dataBean.getProcessInstanceId());
					}
					
					HousNotarizationRegistration hnrn=notaryManageDao.getHousNotarizationRegistrationByInstanceId(dataBean.getProcessInstanceId());
					if(hnrn!=null){
						housNotarizationRegistration.setModifier(dataBean.getLoginUserId());
						housNotarizationRegistration.setModifyTime(new Date());
						notaryManageDao.update(housNotarizationRegistration);
					} else {
						if (housNotarizationRegistration!=null) {							
							housNotarizationRegistration.setCreator(dataBean.getLoginUserId());
							housNotarizationRegistration.setCreateTime(new Date());
							notaryManageDao.insert(housNotarizationRegistration);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
				}
		
		
	}

	@Override
	public void registExtensionExcessiveNotarization(NotaryRegistDataBean dataBean) throws Exception {
		// TODO Auto-generated method stub
		logger.info("展期超额公证登记...");
        if (logger.isDebugEnabled()) {
            logger.debug("参数列表：" +dataBean);
        }
        preCheckBasicParams(dataBean);
        preCheckWorkflowParams(dataBean);
        preCheckCurrentWorkflowState(dataBean);
        //登记通过
        if (WorkFlowConstant.NEXT_STEP_PASS.equals(dataBean.getNextStep())) {
            preCheckServiceParams(dataBean);
        }
        //插入展期登记信息       
        try {
			HousNotarizationRegistration housNotarizationRegistration=dataBean.getHousNotarizationRegistration();
			housNotarizationRegistration.setConsultId(dataBean.getConsultId());
			housNotarizationRegistration.setProjectId(dataBean.getProjectId());
			housNotarizationRegistration.setProcessInstanceId(dataBean.getProcessInstanceId());
			
			//HousNotarizationRegistration hnrn=notaryManageDao.getHousNotarizationRegistrationByInstanceId(dataBean.getProcessInstanceId());
			//if(hnrn!=null){
				//housNotarizationRegistration.setModifier(dataBean.getLoginUserId());
				//housNotarizationRegistration.setModifyTime(new Date());
				//notaryManageDao.update(housNotarizationRegistration);
			//} else {
				housNotarizationRegistration.setCreator(dataBean.getLoginUserId());
				housNotarizationRegistration.setCreateTime(new Date());
				notaryManageDao.insert(housNotarizationRegistration);
			//}
		} catch (Exception e) {
			// TODO: handle exception
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
        //记录审批日志
        recordLoanProcessHistory(dataBean);
        logger.info("展期超额公证登记...");
	}

	public void updateHousBorrowingBasics(NotaryRegistDataBean dataBean) throws ServiceException {
		HousBorrowingBasics housBorrowingBasics = dataBean.getHousBorrowingBasics();
		if(housBorrowingBasics != null){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", housBorrowingBasics.getId());
			paramMap.put("modifier", dataBean.getLoginUserId());
			paramMap.put("modifyTime", DateUtil.now());
			paramMap.put("name", housBorrowingBasics.getName());
			paramMap.put("certNumber", housBorrowingBasics.getCertNumber());
			paramMap.put("phone", housBorrowingBasics.getPhone());
			paramMap.put("marryStatus", housBorrowingBasics.getMarryStatus());
			paramMap.put("spouseName", housBorrowingBasics.getSpouseName());
			paramMap.put("spouseCertNumber", housBorrowingBasics.getSpouseCertNumber());
			paramMap.put("spousePhone", housBorrowingBasics.getSpousePhone());
			paramMap.put("fatherName", housBorrowingBasics.getFatherName());
			paramMap.put("motherName", housBorrowingBasics.getMotherName());
			paramMap.put("fatherNumber", housBorrowingBasics.getFatherNumber());
			paramMap.put("motherNumber", housBorrowingBasics.getMotherNumber());
			paramMap.put("totalBorrowed", housBorrowingBasics.getTotalBorrowed());
			paramMap.put("totalBorrowedCertNumber", housBorrowingBasics.getTotalBorrowedCertNumber());
			paramMap.put("residentialAddress", housBorrowingBasics.getResidentialAddress());
			paramMap.put("residentialAddressId", housBorrowingBasics.getResidentialAddressId());
			paramMap.put("remark", housBorrowingBasics.getRemark());
			housBorrowingBasicsDao.updateByMap(paramMap);
		}
	}
	
	public void updatePlBorrowRequirement(NotaryRegistDataBean dataBean) throws ServiceException {
		PlBorrowRequirement plBorrowRequirement = dataBean.getPlBorrowRequirement();
		if(plBorrowRequirement != null){
			PlFeeinfo plFeeinfo = plFeeinfoDao.getItemInfoByProcessInstanceId(dataBean.getProcessInstanceId());
			plFeeinfo.setConsultId(dataBean.getConsultId());
			plFeeinfo.setProjectId(dataBean.getProjectId());
			plFeeinfo.setProcessInstanceId(dataBean.getProcessInstanceId());
			plFeeinfo.setRepaymentRate(plBorrowRequirement.getRepaymentRate());
			plFeeinfo.setTimeLimit(plBorrowRequirement.getTimeLimit());
			plFeeinfo.setSingleRate(plBorrowRequirement.getSingleRate());
			plFeeinfoDao.update(plFeeinfo);
		}
    }
}
