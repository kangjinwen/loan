package com.company.modules.instance.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.instance.domain.HousAssessmentAgencies;

/**
* User:    wulb
* DateTime:2016-08-06 02:33:32
* details: 评估机构,Service接口层
* source:  代码生成器
*/
public interface HousAssessmentAgenciesService {

    /**
     * 评估机构表,插入数据
     * @param housAssessmentAgencies 评估机构类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousAssessmentAgencies housAssessmentAgencies) throws Exception;

    /**
     * 评估机构表,修改数据
     * @param housAssessmentAgencies 评估机构类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousAssessmentAgencies housAssessmentAgencies) throws Exception;

	/**
     * 评估机构表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousAssessmentAgencies> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 评估机构表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousAssessmentAgencies getItemInfoById(long id) throws Exception;
    
	/**
     * 评估机构表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<HousAssessmentAgencies> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 评估机构表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
