package com.company.modules.audit.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.audit.domain.HousMarriageInformation;

/**
* User:    fzc
* DateTime:2016-08-14 01:28:36
* details: 婚姻信息表(面审),Service接口层
* source:  代码生成器
*/
public interface HousMarriageInformationService {

    /**
     * 婚姻信息表(面审)表,插入数据
     * @param housMarriageInformation 婚姻信息表(面审)类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousMarriageInformation housMarriageInformation) throws Exception;

    /**
     * 婚姻信息表(面审)表,修改数据
     * @param housMarriageInformation 婚姻信息表(面审)类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousMarriageInformation housMarriageInformation) throws Exception;

	/**
     * 婚姻信息表(面审)表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousMarriageInformation> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 婚姻信息表(面审)表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousMarriageInformation getItemInfoById(long id) throws Exception;
    
	/**
     * 婚姻信息表(面审)表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousMarriageInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 婚姻信息表(面审)表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
