package com.company.modules.advance.service;

import java.util.Map;

import com.company.modules.advance.domain.HousAdvanceNotarize;

import java.util.List;

/**
* User:    wulb
* DateTime:2016-09-21 09:24:32
* details: 借款基本信息,Service接口层
* source:  代码生成器
*/
public interface HousAdvanceNotarizeService {

    /**
     * 借款基本信息表,插入数据
     * @param housAdvanceNotarize 借款基本信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousAdvanceNotarize housAdvanceNotarize) throws Exception;
    
    public Map<String, Object> getHousAdvanceNotarizeInfo(long projectId) throws Exception;
    
    public Map<String, Object> getHousAdvanceNotarize(long processInstanceId) throws Exception;

    /**
     * 借款基本信息表,修改数据
     * @param housAdvanceNotarize 借款基本信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousAdvanceNotarize housAdvanceNotarize) throws Exception;

	/**
     * 借款基本信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousAdvanceNotarize> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 借款基本信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousAdvanceNotarize getItemInfoById(long id) throws Exception;
    
	/**
     * 借款基本信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousAdvanceNotarize getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 借款基本信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
