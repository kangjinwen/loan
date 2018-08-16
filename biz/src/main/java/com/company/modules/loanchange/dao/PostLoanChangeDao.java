package com.company.modules.loanchange.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.company.common.dao.BaseDao;
import com.company.common.domain.PubProjectProcess;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.loanchange.domain.LoanChangeDataBean;

@RDBatisDao
@Repository("postLoanChangeDao") 
public interface PostLoanChangeDao extends BaseDao<LoanChangeDataBean,Long>{

	public List<Map> getPostLoanChangeList(Map params) throws PersistentDataException;

	long getPostLoanChangeCount(Map paramMap) throws PersistentDataException;

	public int saveAheadofReturnLoanApply(LoanChangeDataBean loanChangeDataBean);

	public Object insertProjectProcess(PubProjectProcess pubProjectProcess);

	public Map queryAheadOfReturnLoan(Map map) throws PersistentDataException;

	List<Map> queryForceSettleUp(Integer pageNum, Integer pageSize, Map<String, Object> params);

	public List<Map> queryPenaltyReductionList(Integer pageNum,Integer pageSize, Map<String, Object> params);

	public Map<String, Object> queryProcessInstanceByTask(String taskId,String isCompleted) throws PersistentDataException;

	public Integer queryRepaymentDetailCurrentPeriod(String processInstanceId) throws PersistentDataException;

	public Map<String, Object> querybranchingProcessIdByTask(String taskId,String isCompleted) throws PersistentDataException;

	/**
     * 返回项目的借款信息.
     *
     * @param params the params
     * @return the project message
     * @throws ServiceException the service exception
     */
    Map<String,Object> getProjectMessage(Map<String, Object> params) throws PersistentDataException;
    
    /**
	 * 查询违约情况.
	 *
	 * @param processInstanceId the process instance id
	 * @return the default situation
	 * @throws ServiceException the service exception
	 */
	Map<String,Object> getDefaultSituation(String processInstanceId) throws PersistentDataException;
}
