package com.company.modules.anrong.service;

import java.util.List;
import java.util.Map;

import com.company.modules.anrong.domain.PubAnrongAttachment;
import com.company.modules.anrong.domain.PubArRiskResult;

public interface AnRongService {
	
	/**
	 * 生成征信报告
	 * @param map
	 */
	public String createCreditReport(Map<String ,Object> map) throws Exception ;
	
	/**
     * 安融附件表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubAnrongAttachment> getPageListByMap(Map<String , Object> map) throws Exception;
    
    
    public  PubAnrongAttachment getFileById(long id) throws Exception;
    
    
    public List<PubArRiskResult> getQueryCountItems(Map<String , Object> map) throws Exception;
    
    
    
    public List<PubArRiskResult> getCheckStatus(Map<String , Object> map) throws Exception;
		

}
