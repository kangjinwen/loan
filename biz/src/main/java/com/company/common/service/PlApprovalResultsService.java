package com.company.common.service;

import java.util.Map;
import java.util.List;
import  com.company.common.domain.PlApprovalResults;

public interface PlApprovalResultsService {

    public long insert(PlApprovalResults plApprovalResults) throws Exception;

    public long update(PlApprovalResults plApprovalResults) throws Exception;

    public List<PlApprovalResults> getPageListByMap(Map<String , Object> data) throws Exception;

    public PlApprovalResults getItemInfoById(long id) throws Exception;

    public PlApprovalResults getInfoByProcessInstanceId(String processInstanceId) throws Exception;

    public Map<String, Object> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    public int deleteById(long id) throws Exception;

	public PlApprovalResults getItemInfoByConsultId(Long consultId);
}
