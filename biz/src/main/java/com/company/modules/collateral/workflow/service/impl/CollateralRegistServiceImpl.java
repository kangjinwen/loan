package com.company.modules.collateral.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.modules.audit.dao.HousBillsDao;
import com.company.modules.audit.domain.HousBills;
import com.company.modules.collateral.dao.CollateralManageDao;
import com.company.modules.collateral.databean.CollateralRegistDataBean;
import com.company.modules.collateral.domain.HousMortgageRegistration;
import com.company.modules.collateral.workflow.service.CollateralRegistService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;


@Service(value = "collateralRegistServiceImpl")
public class CollateralRegistServiceImpl extends HistoryRecorderService implements CollateralRegistService{
	
	private static final Logger logger = LoggerFactory.getLogger(CollateralRegistServiceImpl.class);
	
	
	@Autowired
	private CollateralManageDao collateralManageDao;
	
	@Autowired
	private HousBillsDao housBillsDao;
	
	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void regist(CollateralRegistDataBean dataBean)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("抵押登记...");
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
        createOrUpdateCollateralRegist(dataBean);
        //记录审批日志
        recordLoanProcessHistory(dataBean);
        logger.info("押品登记完成...");
		
	}
	/**
	 * 
	* @Description: 更新押品登记信息
	* @param @param dataBean
	* @param @throws Exception    设定文件 
	* @author wangmc
	* @return void    返回类型 
	* @throws
	 */
	public void createOrUpdateCollateralRegist(CollateralRegistDataBean dataBean) throws Exception{
		try {
			HousMortgageRegistration housMortgageRegistration=dataBean.getHousMortgageRegistration();
			if(housMortgageRegistration!=null){				
				housMortgageRegistration.setConsultId(dataBean.getConsultId());
				housMortgageRegistration.setProjectId(dataBean.getProjectId());
				housMortgageRegistration.setProcessInstanceId(dataBean.getProcessInstanceId());
				//如果放款单填写有值，也更新下。否则放款单填写后驳回抵押登记填写完又到放款单填写还是第一次放款单填写的值。
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "lend");
				map.put("processInstanceId", dataBean.getProcessInstanceId());
				HousBills housBills = housBillsDao.getItemInfoByProcessInstanceIdAndType(map);
				if (housBills!=null) {			
					housBills.setModifier(dataBean.getLoginUserId());
					housBills.setModifyTime(new Date());
					housBills.setThirdCardNumber(housMortgageRegistration.getThirdCardNumber());
					housBills.setThirdAccountOpening(housMortgageRegistration.getThirdAccountOpening());
					housBills.setThirdBankAccount(housMortgageRegistration.getThirdBankAccount());
					housBills.setAccountHolderName(housMortgageRegistration.getBankAccount());
					housBills.setBankName(housMortgageRegistration.getAccountOpening());
					housBills.setCardid(housMortgageRegistration.getCreditCardNumber());
					housBills.setRemark(housMortgageRegistration.getRemark());	
					housBillsDao.update(housBills);
				}
			}
			
			HousMortgageRegistration hmr=collateralManageDao.getHousMortgageRegistrationsByInstanceId(dataBean.getProcessInstanceId());
			if(hmr!=null){
				housMortgageRegistration.setModifier(dataBean.getLoginUserId());
				housMortgageRegistration.setModifyTime(new Date());
				collateralManageDao.update(housMortgageRegistration);				
			}else{
				if (housMortgageRegistration!=null) {					
					housMortgageRegistration.setCreator(dataBean.getLoginUserId());
					housMortgageRegistration.setCreateTime(new Date());
					collateralManageDao.insert(housMortgageRegistration);
				}
			}			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		
	}

	@Override
	public void registExcessiveMortgage(CollateralRegistDataBean dataBean) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				logger.info("展期抵押登记...");
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
		        //更新展期登记信息流程id存入的是展期的分支流程id
		        //createOrUpdateCollateralRegist(dataBean);
		        try {
					HousMortgageRegistration housMortgageRegistration=dataBean.getHousMortgageRegistration();
					housMortgageRegistration.setConsultId(dataBean.getConsultId());
					housMortgageRegistration.setProjectId(dataBean.getProjectId());
					housMortgageRegistration.setProcessInstanceId(dataBean.getProcessInstanceId());
					
					//HousMortgageRegistration hmr=collateralManageDao.getHousMortgageRegistrationsByInstanceId(dataBean.getProcessInstanceId());
					/*if(hmr!=null){
						housMortgageRegistration.setModifier(dataBean.getLoginUserId());
						housMortgageRegistration.setModifyTime(new Date());
						collateralManageDao.update(housMortgageRegistration);
					}else{*/
						housMortgageRegistration.setCreator(dataBean.getLoginUserId());
						housMortgageRegistration.setCreateTime(new Date());
						collateralManageDao.insert(housMortgageRegistration);
					//}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
				}
		        //记录审批日志
		        recordLoanProcessHistory(dataBean);
		        logger.info("展期押品登记完成...");
		
	}

}
