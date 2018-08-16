package com.company.modules.warrant.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.warrant.domain.HousDataList;

/**
* User:    fzc
* DateTime:2016-08-10 05:14:20
* details: 资料清单表(权证下户),Service接口层
* source:  代码生成器
*/
public interface HousDataListService {

    /**
     * 资料清单表(权证下户)表,插入数据
     * @param housDataList 资料清单表(权证下户)类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousDataList housDataList) throws Exception;

    /**
     * 资料清单表(权证下户)表,修改数据
     * @param housDataList 资料清单表(权证下户)类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousDataList housDataList) throws Exception;

	/**
     * 资料清单表(权证下户)表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousDataList> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 资料清单表(权证下户)表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousDataList getItemInfoById(long id) throws Exception;
    
	/**
     * 资料清单表(权证下户)表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousDataList getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 资料清单表(权证下户)表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
