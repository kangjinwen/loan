package com.company.modules.audit.service;

import java.util.Map;
import java.util.List;

import  com.company.modules.audit.domain.HousBills;

/**
* User:    fzc
* DateTime:2016-08-17 03:46:40
* details: 放款单/打款单,Service接口层
* source:  代码生成器
*/
public interface HousBillsService {

    /**
     * 放款单/打款单表,插入数据
     * @param housBills 放款单/打款单类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousBills housBills) throws Exception;

    /**
     * 放款单/打款单表,修改数据
     * @param housBills 放款单/打款单类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousBills housBills) throws Exception;

	/**
     * 放款单/打款单表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousBills> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 放款单/打款单表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousBills getItemInfoById(long id) throws Exception;
    
	/**
     * 放款单/打款单表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousBills getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 放款单/打款单表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public Map<String, Object> getHousBillBasicInfo(String processInstanceId) throws Exception;

	public Map<String, Object> getLoanInfo(String processInstanceId) throws Exception;

	public long insertOrupdate(HousBills housBillsInfo,long userId,String type) throws Exception;
}
