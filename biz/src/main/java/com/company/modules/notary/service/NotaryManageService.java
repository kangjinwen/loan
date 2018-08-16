package com.company.modules.notary.service;

import java.util.Map;

import  com.company.modules.notary.domain.HousNotarizationRegistration;

/**
* User:    mcwang
* DateTime:2016-08-10 03:59:46
* details: 公证登记,Service接口层
* source:  代码生成器
*/
public interface NotaryManageService {

    /**
     * 公证登记表,插入数据
     * @param housNotarizationRegistration 公证登记类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousNotarizationRegistration housNotarizationRegistration) throws Exception;

    
    
	/**
     * 公证登记表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getNotaryRegistDataByInstanceId(String processInstanceId) throws Exception;

}
