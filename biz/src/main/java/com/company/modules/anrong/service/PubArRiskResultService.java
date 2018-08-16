package com.company.modules.anrong.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.anrong.domain.PubArRiskResult;

/**
* User:    mcwang
* DateTime:2016-09-13 03:00:19
* details: 安融风控结果,Service接口层
* source:  代码生成器
*/
public interface PubArRiskResultService {

    /**
     * 安融风控结果表,插入数据
     * @param pubArRiskResult 安融风控结果类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubArRiskResult pubArRiskResult) throws Exception;

    /**
     * 安融风控结果表,修改数据
     * @param pubArRiskResult 安融风控结果类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubArRiskResult pubArRiskResult) throws Exception;

	/**
     * 安融风控结果表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubArRiskResult> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 安融风控结果表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubArRiskResult getItemInfoById(long id) throws Exception;
    
	/**
     * 安融风控结果表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubArRiskResult getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 安融风控结果表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
