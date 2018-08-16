package com.company.modules.instance.service;

import java.util.Map;
import java.util.List;

import  com.company.modules.instance.domain.HousPersonType;

/**
* User:    wulb
* DateTime:2016-11-15 13:47:18
* details: 新增人员类型,Service接口层
* source:  代码生成器
*/
public interface HousPersonTypeService {

    /**
     * 新增人员类型表,插入数据
     * @param housPersonType 新增人员类型类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousPersonType housPersonType) throws Exception;

    /**
     * 新增人员类型表,修改数据
     * @param housPersonType 新增人员类型类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousPersonType housPersonType) throws Exception;

	/**
     * 新增人员类型表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousPersonType> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 新增人员类型表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousPersonType getItemInfoById(long id) throws Exception;
    
	/**
     * 新增人员类型表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<HousPersonType> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 新增人员类型表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
    
    public void insertOrupdate(HousPersonType housPersonTypeInfo) throws Exception;
    
    public List<HousPersonType> getItemInfoByConsultId(Long consultId);
}
