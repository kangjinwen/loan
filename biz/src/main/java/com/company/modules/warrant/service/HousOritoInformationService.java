package com.company.modules.warrant.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.warrant.domain.HousOritoInformation;

/**
* User:    fzc
* DateTime:2016-08-10 03:35:36
* details: 下户信息表(权证下户),Service接口层
* source:  代码生成器
*/
public interface HousOritoInformationService {

    /**
     * 借款基本信息表,插入数据
     * @param housOritoInformation 借款基本信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousOritoInformation housOritoInformation) throws Exception;

    /**
     * 借款基本信息表,修改数据
     * @param housOritoInformation 借款基本信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousOritoInformation housOritoInformation) throws Exception;

	/**
     * 借款基本信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousOritoInformation> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 借款基本信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousOritoInformation getItemInfoById(long id) throws Exception;
    
	/**
     * 借款基本信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousOritoInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 借款基本信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
