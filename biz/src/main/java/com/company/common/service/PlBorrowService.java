package com.company.common.service;

import java.util.Map;
import java.util.List;
import  com.company.common.domain.PlBorrow;

/**
* User:    wulb
* DateTime:2016-08-15 05:42:32
* details: 借款信息,Service接口层
* source:  代码生成器
*/
public interface PlBorrowService {

    /**
     * 借款信息表,插入数据
     * @param plBorrow 借款信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PlBorrow plBorrow) throws Exception;

    /**
     * 借款信息表,修改数据
     * @param plBorrow 借款信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlBorrow plBorrow) throws Exception;
    
    /**
     * 借款信息表,修改数据
     * @param data 
     * @return           返回页面map
     * @throws Exception
     */
    public long updatePlBorrowById(Map<String , Object> data) throws Exception;

	/**
     * 借款信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PlBorrow> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 借款信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlBorrow getItemInfoById(long id) throws Exception;
    
	/**
     * 借款信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlBorrow getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;
    
    /**
     * 借款信息表,根据条件查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public Map<String, Object> getItemInfoByMap(Map<String, Object> data) throws Exception;

    /**
    * 借款信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

    /**
     * @description 面审获取借款信息
     * @param processInstanceId
     * @return
     * @throws Exception
     * @author fzc
     * @return Map<String,Object>
     * @since  1.0.0
     */
	public Map<String, Object> getborrowInfo(String processInstanceId) throws Exception;
}
