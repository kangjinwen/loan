package com.company.modules.instance.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.instance.domain.HousPropertyInformation;

/**
* User:    wulb
* DateTime:2016-08-06 02:28:59
* details: 房产信息,Service接口层
* source:  代码生成器
*/
public interface HousPropertyInformationService {

    /**
     * 房产信息表,插入数据
     * @param housPropertyInformation 房产信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousPropertyInformation housPropertyInformation) throws Exception;

    /**
     * 房产信息表,修改数据
     * @param housPropertyInformation 房产信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousPropertyInformation housPropertyInformation) throws Exception;

	/**
     * 房产信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousPropertyInformation> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 房产信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousPropertyInformation getItemInfoById(long id) throws Exception;
    
	/**
     * 房产信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousPropertyInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 房产信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public HousPropertyInformation getItemInfoByConsultId(Long consultId);

	public String getHousAddress(Long propertyAddressId);
}
