package com.company.modules.instance.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.instance.domain.HousEnquiryInstitution;

/**
* User:    wulb
* DateTime:2016-08-12 01:50:43
* details: 查询机构信息,Service接口层
* source:  代码生成器
*/
public interface HousEnquiryInstitutionService {

    /**
     * 查询机构信息表,插入数据
     * @param housEnquiryInstitution 查询机构信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousEnquiryInstitution housEnquiryInstitution) throws Exception;

    /**
     * 查询机构信息表,修改数据
     * @param housEnquiryInstitution 查询机构信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousEnquiryInstitution housEnquiryInstitution) throws Exception;

	/**
     * 查询机构信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousEnquiryInstitution> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 查询机构信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousEnquiryInstitution getItemInfoById(long id) throws Exception;
    
	/**
     * 查询机构信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<HousEnquiryInstitution> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 查询机构信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public List<HousEnquiryInstitution> getItemInfoByConsultId(Long consultId);
}
