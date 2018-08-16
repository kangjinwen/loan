package com.company.modules.workflow.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;

@RDBatisDao
@Repository("taskDao") 
public interface TaskDao extends BaseDao<Object,Long>{

	List<Map> queryAlreadyDoneAfterLoanApprovalTasks(Map<String, Object> paramMap);

	List<Map> queryUndoneAfterLoanApprovalTasks(Map<String, Object> paramMap);
	
	Map<String,Object> getProcessState(String taskId) throws PersistentDataException;
	
	/**
	 * @description
	 * 查询当前任务处理人  同一角色  同一部门的处理人
	 * @param taskId
	 * @return
	 * @throws PersistentDataException
	 * @author wangdk
	 * @return List<Map<String,Object>>
	 * @since  1.0.0
	*/
	List<Map<String,Object>> queryUserList(String taskId) throws PersistentDataException;
	
	List<Map<String,Object>> queryHouseCheckBankList(String processInstanceId);
}
