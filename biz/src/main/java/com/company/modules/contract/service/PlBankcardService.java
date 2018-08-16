package com.company.modules.contract.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.contract.domain.PlBankcard;

/**
* User:    wulb
* DateTime:2016-08-15 11:44:43
* details: 放款银行信息,Service接口层
* source:  代码生成器
*/
public interface PlBankcardService {

    /**
     * 放款银行信息表,插入数据
     * @param plBankcard 放款银行信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PlBankcard plBankcard) throws Exception;

    /**
     * 放款银行信息表,修改数据
     * @param plBankcard 放款银行信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlBankcard plBankcard) throws Exception;

	/**
     * 放款银行信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PlBankcard> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 放款银行信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlBankcard getItemInfoById(long id) throws Exception;
    
	/**
     * 放款银行信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlBankcard getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 放款银行信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public void updateByMap(Map<String, Object> bankMap) throws Exception ;
}
