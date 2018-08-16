package com.company.modules.collateral.workflow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.modules.collateral.dao.CollateralManageDao;
import com.company.modules.collateral.databean.CollateralRegistDataBean;
import com.company.modules.collateral.domain.HousMortgageRegistration;
import com.company.modules.collateral.workflow.service.CollateralRelieveService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;

@Service(value = "collateralRelieveServiceImpl")
public class CollateralRelieveServiceImpl extends HistoryRecorderService implements CollateralRelieveService{

	private static final Logger logger = LoggerFactory.getLogger(CollateralRegistServiceImpl.class);
	
	
	@Autowired
	private CollateralManageDao collateralManageDao;
	
	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void relieve(CollateralRegistDataBean dataBean)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("解押押登记...");
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
        createOrUpdateCollateralRelieve(dataBean);
        //记录审批日志
        recordLoanProcessHistory(dataBean);
        logger.info("押品解押完成...");
		
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
	public void createOrUpdateCollateralRelieve(CollateralRegistDataBean dataBean) throws Exception{
		try {
			HousMortgageRegistration housMortgageRegistration=dataBean.getHousMortgageRegistration();
			
			HousMortgageRegistration hmr=(HousMortgageRegistration) collateralManageDao.getHousMortgageRegistrationsByProjectId(dataBean.getProjectId().toString());
			if(hmr!=null){
				collateralManageDao.update(housMortgageRegistration);
			}else{
				collateralManageDao.insert(housMortgageRegistration);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		
	}

}
