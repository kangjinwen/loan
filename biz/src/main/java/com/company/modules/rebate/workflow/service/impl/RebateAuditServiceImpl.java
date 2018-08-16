
package com.company.modules.rebate.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.modules.audit.dao.HousLoanfeesDao;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.rebate.dao.RebateManageDao;
import com.company.modules.rebate.databean.RebateAuditDataBean;
import com.company.modules.rebate.domain.HousRebate;
import com.company.modules.rebate.workflow.service.RebateAuditService;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

@SuppressWarnings("rawtypes")
@Service(value = "rebateAuditServiceImpl")
public class RebateAuditServiceImpl extends HistoryRecorderService implements RebateAuditService{
	
	@Autowired
	private	RebateManageDao rebateManageDao;
	
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	
	@Autowired
    private HousLoanfeesDao housLoanfeesDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RebateAuditServiceImpl.class);

	@Override
	protected void preCheckCurrentWorkflowState(
			ProjectWorkflowDataBean projectWorkflowDataBean)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void audit(RebateAuditDataBean dataBean) throws Exception {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("branchingProcessId", dataBean.getProcessInstanceId());
		map.put("isActive",WorkFlowConstant.IS_ACTIVE_RETURN_COMMISSION);
        try {
        	PubProcessBranching pb = null;
        	List<PubProcessBranching> list= pubProcessBranchingDao.getPageListByMap(map);
			if(list==null || list.size()==0){
				throw new ServiceException("查询不到指定的分支流程");
			}
			pb = list.get(0);
			pb.setProcessingOpinion(dataBean.getNextStep());
			pb.setModifier(dataBean.getUserName());
			pb.setModifyTime(new Date());
			pb.setTaskId(dataBean.getTaskId());
			
			if("pass".equalsIgnoreCase(dataBean.getNextStep())){
				pb.setRepaymentProcess((byte) 1);
			}
			
			pubProcessBranchingDao.update(pb); //更新分支流程
			HousLoanfees housLoanfees = housLoanfeesDao.getItemInfoByProjectId(dataBean.getProjectId());
			if (housLoanfees!=null) {	
				housLoanfees.setReturnCard(dataBean.getHousRebate().getRebateCard());
				housLoanfees.setReturnBankName(dataBean.getHousRebate().getReturnBankName());
				housLoanfees.setSalesman(dataBean.getHousRebate().getSalesman());
				housLoanfeesDao.update(housLoanfees);//更新返费人姓名
			}
		} catch (Exception e) {
			 logger.error(e.getMessage(),e);
	         throw new ServiceException(400, e.getMessage());
		}
        try {
        	logger.info("返佣审核...");
            if (logger.isDebugEnabled()) {
                logger.debug("参数列表：" +dataBean);
            }
            preCheckBasicParams(dataBean);
            preCheckWorkflowParams(dataBean);
            preCheckCurrentWorkflowState(dataBean);
            //登记通过
            preCheckServiceParams(dataBean);
            
            TaskAssignerCenter.isNew= true;
            //保存返佣信息
            saveHousRebate(dataBean);
            //记录审批日志
            recordLoanProcessHistory(dataBean);
            logger.info("返佣审核完成...");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ServiceException(400, e.getMessage());
		}
		
	}
	
	/**
	 * 
	* @Description: 保存返佣信息
	* @param @throws Exception    设定文件 
	* @author wangmc
	* @return void    返回类型 
	* @throws
	 */
	public void saveHousRebate(RebateAuditDataBean dataBean) throws Exception{
		try {
			HousRebate housRebate=dataBean.getHousRebate();
			housRebate.setConsultId(dataBean.getConsultId());
			housRebate.setProcessInstanceId(dataBean.getProcessInstanceId());
			housRebate.setProjectId(dataBean.getProjectId());
			housRebate.setCreator(dataBean.getLoginUserId());
			housRebate.setCreateTime(new Date());
			housRebate.setBankName(housRebate.getReturnBankName());
			
			HousRebate hRebate = rebateManageDao.getHousRebateByInstanceId(dataBean.getProcessInstanceId());
			
			if(hRebate==null){
				rebateManageDao.insert(housRebate);
			} else {
				housRebate.setId(hRebate.getId());
				rebateManageDao.update(housRebate);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throwServiceExceptionAndLog(logger, "数据库操作失败。", e, Constant.OTHER_CODE_VALUE);
		}
		
		
	}


}
