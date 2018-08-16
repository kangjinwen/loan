package com.company.common.service;

import java.util.Map;
import java.util.List;

import com.company.common.domain.PubCustomer;

/**
* User:    huy
* DateTime:2016-12-08 17:53:10
* details: 客户管理,Service接口层
* source:  代码生成器
*/
public interface PubCustomerService {

    /**
     * 客户管理表,插入数据
     * @param pubCustomer 客户管理类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubCustomer pubCustomer) throws Exception;

    /**
     * 客户管理表,修改数据
     * @param pubCustomer 客户管理类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubCustomer pubCustomer) throws Exception;

	/**
     * 客户管理表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubCustomer> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 客户管理表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubCustomer getItemInfoById(long id) throws Exception;
    
	/**
     * 客户管理表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubCustomer getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 客户管理表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
    
    public PubCustomer getCustomerByProcessInstanceId(String processInstanceId);
    
    public List<Map<String,Object>> getCustomerListByMap(Map<String, Object> map);

    public List<Map<String,Object>> getCustomerListByCreator(Map<String, Object> map);
}
