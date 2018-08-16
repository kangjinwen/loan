package com.company.modules.loanchange.service;

import java.util.List;
import java.util.Map;

import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProjectProcess;
import com.company.common.domain.PubRepaymentdetail;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.loanchange.domain.LoanChangeDataBean;

public interface PostLoanChangeService {

	/**
	 * @throws PersistentDataException @Title:
	 * getPostLoanModificationList @Description: TODO(获取贷后变更列表) @param @param
	 * params @param @return 设定文件 @return List 返回类型 @throws
	 */
	List<Map> getPostLoanChangeList(Map params) throws PersistentDataException;

	Long getPostLoanChangeCount(Map params) throws PersistentDataException;

	int saveAheadofReturnLoanApply(LoanChangeDataBean carLoanChangeDataBean) throws ServiceException, Exception;

	Object insertProjectProcess(PubProjectProcess pubProjectProcess);

	Map<String, Object> queryAheadOfReturnLoan(Map map) throws ServiceException, PersistentDataException;

	public Map aheadofReturnLoanSubmit(Long processInstanceId, LoanChangeDataBean carLoanChangeDataBean,
			String userName) throws ServiceException, PersistentDataException;

	Map penaltyReductionRequisitionSubmit(Long processInstanceId, PubRepaymentdetail repaymentdetail,
			LoanChangeDataBean carLoanChangeDataBean, String userName)
			throws ServiceException, PersistentDataException, Exception;

	Map forceSettleUpRequisitionSubmit(Long processInstanceId, LoanChangeDataBean carLoanChangeDataBean,
			String userName) throws ServiceException, PersistentDataException, Exception;

	Map<String, Object> extensionApplicationSubmit(LoanChangeDataBean carLoanChangeDataBean, PlBorrow plBorrow,
			String processInstanceId, Long projectId, String userName, List<Long> roleList)
			throws ServiceException, PersistentDataException, Exception;

	boolean isNeedUserRole(String needSysRoleNid, List<Long> roleList) throws ServiceException;

	Long queryExtensionSubmited(Map params) throws PersistentDataException;

	List<Map> queryForceSettleUp(Integer pageNum, Integer pageSize, Map<String, Object> params) throws ServiceException;

	List<Map> queryPenaltyReductionList(Integer pageNum, Integer pageSize, String processInstanceId, Boolean isBranch)
			throws ServiceException;

	/**
	 * @description 押品处置流程启动
	 * @param processInstanceId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @author wtc
	 * @return Map<String,Object>
	 * @throws PersistentDataException
	 * @since 1.0.0
	 */
	// Map<String, Object> vehicleDisposalStart(Long processInstanceId,String
	// userName,DisposalDataBean databean) throws ServiceException,
	// PersistentDataException;

	Map<String, Object> queryDecisionInformation(Long processInstanceId, String processState)
			throws ServiceException, PersistentDataException;

	Map<String, Object> queryDecisionRecommendation(Map map) throws ServiceException, PersistentDataException;

	// Long processInstanceId, String processState
	Map<String, Object> queryDispositionRegister(Integer pageNum, Integer pageSize, Map<String, Object> params)
			throws ServiceException, PersistentDataException;

	Map<String, Object> queryAttachmentInfo(String processInstanceId, String rdBtype) throws PersistentDataException;

	Map<String, Object> queryProcessInstanceByTask(String taskId, String isCompleted) throws PersistentDataException;

	Integer queryRepaymentDetailCurrentPeriod(String processInstanceId) throws PersistentDataException;

	Map<String, Object> querybranchingProcessIdByTask(String taskId, String isCompleted) throws PersistentDataException;

	List<Map> pendingApplicationList(Map<String, Object> params) throws ServiceException;

	List<Map> postLoanChangeList(Map<String, Object> params) throws ServiceException;

	/**
	 * @description 根据传入的实例ID（也可能是分支流程）返回流程ID或分支流程ID
	 * @param processInstanceId
	 * @param isBranch
	 * @return
	 * @throws ServiceException
	 * @author wtc
	 * @return Map<String,Object>
	 * @since 1.0.0
	 */
	Map<String, Object> getProcessOrBranchProcessMap(String processInstanceId, Boolean isBranch)
			throws ServiceException;

	Map<String, Object> getAheadOfReturnLoanInfo(String processInstanceId, Boolean isBranch) throws ServiceException;

	/**
	 * @description 展期信息
	 * @param processInstanceId
	 * @param isBranch
	 * @return
	 * @throws ServiceException
	 * @author wtc
	 * @return Map<String,Object>
	 * @since 1.0.0
	 */
	Map<String, Object> getExtensionInfo(String processInstanceId, Boolean isBranch) throws ServiceException;

	/**
	 * @description
	 * @param type 提前1 减免2 强制3 处置5 展期6
	 * @param processInstanceId
	 * @param isBranch
	 * @return
	 * @throws ServiceException
	 * @author wtc
	 * @return Map<String,Object>
	 * @since 1.0.0
	 */
	Map<String, Object> getRepaymentAmount(Byte type, String processInstanceId, Boolean isBranch)
			throws ServiceException;

	/**
	 * 返回项目的借款信息.
	 *
	 * @param params
	 *            the params
	 * @param isBranch
	 * @return the project message
	 * @throws ServiceException
	 *             the service exception
	 */
	Map<String, Object> getForceSettlementInfo(String processInstanceId, Boolean isBranch) throws ServiceException;

	/**
	 * 获取车辆处置信息
	 * 
	 * @description
	 * @param processInstanceId
	 * @param isBranch
	 * @return
	 * @throws ServiceException
	 * @author wgc
	 * @return Map<String,Object>
	 * @since 1.0.0
	 */
	public Map<String, Object> getVehicleDisposalInfo(String processInstanceId, Boolean isBranch)
			throws ServiceException;

	/**
	 * 查询逾期罚息减免
	 * @param processInstanceId 流程id  
	 * @param period 期次
	 * @param isBranch 是否是分支流程
	 * @return
	 * @throws ServiceException
	 */
	PubRepaymentdetail queryPenaltyReduction(String processInstanceId, Byte period, Boolean isBranch) throws ServiceException;
}
