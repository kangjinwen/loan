package com.company.modules.rebate.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.rebate.domain.HousRebate;

@RDBatisDao
public interface RebateManageDao extends BaseDao<HousRebate, Long>{
	
	/**
     * 查询基本信息
     * @param processInstanceId
     * @return HousNotarizationRegistration
     */
    public Map<String, Object> getAuditBasicDataByInstanceId(Map<String, Object> paramMap);
    
    
    /**
     * 查询返佣信息
     * @param processInstanceId
     * @return HousNotarizationRegistration
     */
    public Map<String, Object> getRebateAuditDataByInstanceId(String processInstanceId);
    
    
    /**
     * 返佣时查询的审核信息
     * @param processInstanceId
     * @return HousNotarizationRegistration
     */
    public Map<String, Object> getRebateHandleDataByInstanceId(String processInstanceId);
    
    
    
    public HousRebate getHousRebateByInstanceId(String processInstanceId);


	public List<HousRebate> getHousRebateListByMap(Map<String, Object> paramMap);
    

}
