package com.company.modules.common.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.domain.PubLoanprocess;

/**
* User:    wulb
* DateTime:2016-08-05 04:12:22
* details: 审批历史,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubLoanprocessDao extends BaseDao<PubLoanprocess,Long> {

    /**
     * 审批历史表,分页查询数据
     * @param map
     * @return List<PubLoanprocess>
     */
    public List<PubLoanprocess> getPageListByMap(Map<String, Object> map);
    /**
     * 审批历史表,查询数据
     * @param map
     * @return List<PubLoanprocess>
     */
    public Map<String, Object> getByProjectId(Long projectId);
    /**
     * 审批历史表,查询数据
     * @param map
     * @return List<PubLoanprocess>
     */
    public Map<String, Object> getByProjectIdAndProcessState(Long projectId);
    /**
     * 审批历史表,查询数据
     * @param map
     * @return List<PubLoanprocess>
     */
    public PubLoanprocess getByTaskId(String taskId);
    
    public int updateById(Map<String, Object> param);
    
    public int updateWorkflowNextAssigneeNameById(Map<String, Object> param);
    
    public int updateNewTaskId(Map<String, Object> param);


    /**
    * 审批历史表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public long getPubLoanprocessCount(Map<String, Object> data);

	public List<Map<String, Object>> queryAllHistoricLoanProcessListToCustomerByProjectId(Map<String, Object> params);

	public List<Map<String, Object>> queryAllHistoricLoanProcessListByProjectId(Map<String, Object> params);
}
