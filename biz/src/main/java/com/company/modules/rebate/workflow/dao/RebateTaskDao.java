package com.company.modules.rebate.workflow.dao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;

@RDBatisDao
public interface RebateTaskDao {
	
	 /**
     * 
    * @Description: 查询返佣审核任务表
    * @param @param map
    * @param @return    设定文件 
    * @author wangmc
    * @return List<Map<String,Object>>    返回类型 
    * @throws
     */
    public List<Map<String, Object>> getUnDoneRebateAuditList(Map<String, Object> map);
    
    public List<Map<String, Object>> getDoneRebateAuditList(Map<String, Object> map);
    
    
    /**
     * 
    * @Description: 待返佣的任务
    * @param @param map
    * @param @return    设定文件 
    * @author wangmc
    * @return List<Map<String,Object>>    返回类型 
    * @throws
     */
	 public List<Map<String, Object>> getUnDoneRebateHandleList(Map<String, Object> map);
	    
	 public List<Map<String, Object>> getDoneRebateHandleList(Map<String, Object> map);


}
