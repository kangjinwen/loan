package com.company.modules.rebate.workflow.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

public interface RebateTaskService {
	
	/**
     * 返佣表,分页查询待审核任务数据
     * @param data
     * @return
     * @throws Exception
     */
    public PageInfo<Map<String, Object>> getRebateAuditPageList(Map<String , Object> data) throws Exception;
    
    
    
    /**
     * 查询待操作的返佣任务
     * @param data
     * @return
     * @throws Exception
     */
    public PageInfo<Map<String, Object>> getRebateHandlePageList(Map<String , Object> data) throws Exception;
    
    
    
    public void createRebateTasks(Map<String, Object> map) throws Exception;

}
