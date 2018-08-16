package com.company.modules.contract.service;

import java.util.Map;
import java.util.List;

import  com.company.modules.contract.domain.PlContract;

/**
* User:    wulb
* DateTime:2016-08-15 11:21:07
* details: 合同信息,Service接口层
* source:  代码生成器
*/
public interface PlContractService {

    /**
     * 合同信息表,插入数据
     * @param plContract 合同信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PlContract plContract) throws Exception;

    /**
     * 合同信息表,修改数据
     * @param plContract 合同信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlContract plContract) throws Exception;

	/**
     * 合同信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PlContract> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 合同信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlContract getItemInfoById(long id) throws Exception;
    
	/**
     * 合同信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlContract getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 合同信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
    
    /**
     * 合同生成
     * @param plContract
     * @param param
     * @return
     * @throws Exception 
     * @version 1.0
     * @author wulb
     */
    Boolean addPlContract(PlContract plContract, Map<String, Object> param) throws Exception;
    
    /**
     * 合同生成更新
     * @param map
     * @return
     * @throws Exception 
     * @version 1.0
     * @author wulb
     */
    Boolean updatePlContractById(Map<String , Object> map) throws Exception;
}
