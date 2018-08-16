package com.company.common.service;

import java.util.Map;
import java.util.List;
import  com.company.common.domain.PlBorrowRequirement;

/**
* User:    wulb
* DateTime:2016-08-08 02:01:19
* details: 借款需求信息,Service接口层
* source:  代码生成器
*/
public interface PlBorrowRequirementService {

    /**
     * 借款需求信息表,插入数据
     * @param plBorrowRequirement 借款需求信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PlBorrowRequirement plBorrowRequirement) throws Exception;

    /**
     * 借款需求信息表,修改数据
     * @param plBorrowRequirement 借款需求信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlBorrowRequirement plBorrowRequirement) throws Exception;

	/**
     * 借款需求信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PlBorrowRequirement> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 借款需求信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlBorrowRequirement getItemInfoById(long id) throws Exception;
    
	/**
     * 借款需求信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlBorrowRequirement getInfoByProcessInstanceId(String processInstanceId) throws Exception;
    
    /**
     * 借款需求信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public Map<String, Object> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 借款需求信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public PlBorrowRequirement getItemInfoByConsultId(Long consultId);
}
