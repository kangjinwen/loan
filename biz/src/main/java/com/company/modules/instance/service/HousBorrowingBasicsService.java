package com.company.modules.instance.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.instance.domain.HousBorrowingBasics;

/**
* User:    wulb
* DateTime:2016-08-06 02:36:08
* details: 借款基本信息,Service接口层
* source:  代码生成器
*/
public interface HousBorrowingBasicsService {

    /**
     * 借款基本信息表,插入数据
     * @param housBorrowingBasics 借款基本信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousBorrowingBasics housBorrowingBasics) throws Exception;

    /**
     * 借款基本信息表,修改数据
     * @param housBorrowingBasics 借款基本信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousBorrowingBasics housBorrowingBasics) throws Exception;

	/**
     * 借款基本信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousBorrowingBasics> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 借款基本信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousBorrowingBasics getItemInfoById(long id) throws Exception;
    
	/**
     * 借款基本信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousBorrowingBasics getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 借款基本信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public HousBorrowingBasics getItemInfoByConsultId(Long consultId);
}
