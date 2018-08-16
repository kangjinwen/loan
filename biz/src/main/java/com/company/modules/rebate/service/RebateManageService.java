package com.company.modules.rebate.service;

import java.util.List;
import java.util.Map;

import com.company.modules.rebate.domain.HousRebate;

public interface RebateManageService {
	
	/**
     * 根据流程id查询借、还款基本信息
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getBorrowBasicDataByInstanceId(Map<String, Object> paramMap) throws Exception;
    
    
    
    /**
     * 根据流程id查询返佣审核信息
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getRebateAuditDataByInstanceId(String processInstanceId) throws Exception;
    
    
    
    /**
     * 返佣时根据流程id查询审核信息
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getRebateHandleDataByInstanceId(String processInstanceId) throws Exception;

	public List<HousRebate> getHousRebateListByMap(Map<String, Object> paramMap);

}
