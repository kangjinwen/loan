package com.company.modules.warrant.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.warrant.domain.HousIntermediaryInformation;

/**
* User:    fzc
* DateTime:2016-08-10 05:04:19
* details: 房屋中介信息(权证下户),Service接口层
* source:  代码生成器
*/
public interface HousIntermediaryInformationService {

    /**
     * 房屋中介信息(权证下户)表,插入数据
     * @param housIntermediaryInformation 房屋中介信息(权证下户)类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousIntermediaryInformation housIntermediaryInformation) throws Exception;

    /**
     * 房屋中介信息(权证下户)表,修改数据
     * @param housIntermediaryInformation 房屋中介信息(权证下户)类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousIntermediaryInformation housIntermediaryInformation) throws Exception;

	/**
     * 房屋中介信息(权证下户)表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousIntermediaryInformation> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 房屋中介信息(权证下户)表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousIntermediaryInformation getItemInfoById(long id) throws Exception;
    
	/**
     * 房屋中介信息(权证下户)表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousIntermediaryInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 房屋中介信息(权证下户)表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
