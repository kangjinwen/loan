package com.company.common.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.domain.PlProduct;

/**
* User:    wulb
* DateTime:2016-08-15 05:50:16
* details: 产品信息,Service接口层
* source:  代码生成器
*/
public interface PlProductService {

    /**
     * 产品信息表,插入数据
     * @param plProduct 产品信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PlProduct plProduct) throws Exception;

    /**
     * 产品信息表,修改数据
     * @param plProduct 产品信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlProduct plProduct) throws Exception;

	/**
     * 产品信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getPageListByMap(Map<String, Object> data) throws Exception;
    
	/**
     * 产品信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlProduct getItemInfoById(long id) throws Exception;
    
	/**
     * 产品信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlProduct getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 产品信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
