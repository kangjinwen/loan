package com.company.modules.workflow.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.company.modules.common.domain.ExportHousOritoInformation;
import com.company.modules.common.domain.HouseCheckBank;
import com.company.modules.common.exception.ServiceException;

/**
 *	@Description 查询任务
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月8日 下午2:33:37
 *  @since version 1.0.0
 */
public interface RDZZTaskService {

	/**
	 * 查询下户任务分配任务
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHouseholdTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
    
	/**
	 * 查询客服确认(展期)任务
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHousExtensionSurveyTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 查询展期房产评估
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHousExtensionCollateralAssessTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 展期公证分配查询
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHousNotarizationAssignTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 超额展期公证登记查询
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHousExcessiveNotarizationTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 展期超额抵押
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHousExcessiveMortgageTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	
	/**
	 * 展期合同
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHouSignExtendedContractTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 展期费管理
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryExtendedfeeTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 展期费划扣
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryDeductTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 查询垫资确认任务
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryHousAdvanceApply(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	/**
	 * 查询垫资公证任务
	 * @param userName
	 * @param roleId
	 * @param coverdOffices
	 * @param pageBean
	 * @param isCompleted
	 * @return
	 * @throws ServiceException
	 */
	PageInfo<Map<String, Object>> queryAdvanceNotarizeTasks(long roleId , Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException;
	
	
    /**
     * 查询客服初评分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryInstanceTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;
    
    /**
     * 报单专员查询客服初评分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryInstanceInfoTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;
    
    /**
     * 查询客服对接分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryConnectionTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;
    
    /**
     * 查询客服调查分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> querySurveyTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;
    
    /**
     * 查询客服确认分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryConfirmationTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;
    
    /**
     * 查询客服确认分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryOritoConfirmationTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;
    
    /**
     * 查询合同签订分配任务
     * @param params
     * @param isCompleted
     * @return
     * @throws Exception
     */
    PageInfo<Map<String, Object>> queryContractTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted) throws Exception;

    /**
     * @description 查询权证下户
     * @param roleId
     * @param params
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @return
     * @author fzc
     * @return PageInfo<Map<String,Object>>
     * @since  1.0.0
     */
	PageInfo<Map<String, Object>> queryHouseholdInvestigate(Long roleId,Map<String, Object> params, boolean isCompleted, int currentPage,int pageSize)throws ServiceException;

	PageInfo<Map<String, Object>> queryHouseCheckBank(Long roleId,
			Map<String, Object> params, boolean isCompleted, int currentPage,
			int pageSize)throws ServiceException;
	
	List<HouseCheckBank> queryHouseCheckBank(Long roleId,Map<String, Object> params)throws ServiceException;
			
	/**
	 * @description  查询下户费收取
	 * @param roleId
	 * @param params
	 * @param isCompleted
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @author fzc
	 * @return PageInfo<Map<String,Object>>
	 * @since  1.0.0
	 */
	PageInfo<Map<String, Object>> queryhousLowerCost(Long roleId,Map<String, Object> params, boolean isCompleted, int currentPage,int pageSize) throws ServiceException;

	PageInfo<Map<String, Object>> queryAuditTasks(Long roleId,Map<String, Object> params, boolean isCompleted, int currentPage,int pageSize, String type) throws ServiceException;

	PageInfo<Map<String, Object>> queryPreSetRepayPlanTasks(Long roleId,Map<String, Object> params, boolean isCompleted, int currentPage,int pageSize, String type) throws ServiceException;

	PageInfo<Map<String, Object>> queryMyTaskStatistic(Map<String, Object> params,int currentPage,int pageSize)throws ServiceException;

	List<ExportHousOritoInformation> queryHouseholdInvestigate(Long roleId, Map<String, Object> params) throws ServiceException;

    int modifyTaskAssigneeByAnyKey(String taskId, String userId);

	int modifyHisTaskAssigneeByAnyKey(String taskId, String userId);

    Map<String,Object> getHisTaskInfoByTaskId(String taskId);
}