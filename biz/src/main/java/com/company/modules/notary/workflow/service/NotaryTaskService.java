package com.company.modules.notary.workflow.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

public interface NotaryTaskService {
	
	/**
     * 公证登记表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public PageInfo<Map<String, Object>> getNotaryRegistPageList(Map<String , Object> data) throws Exception;

}
