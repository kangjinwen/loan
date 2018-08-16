package com.company.modules.supplymaterial.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.supplymaterial.domain.PubSupplymaterialLog;

/**
* User:    JDM
* DateTime:2016-08-18 03:51:48
* details: 补充资料,Service接口层
* source:  代码生成器
*/
public interface PubSupplymaterialLogService {

    /**
     * 补充资料表,插入数据
     * @param pubSupplymaterialLog 补充资料类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubSupplymaterialLog pubSupplymaterialLog) throws Exception;

    /**
     * 补充资料表,修改数据
     * @param pubSupplymaterialLog 补充资料类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubSupplymaterialLog pubSupplymaterialLog) throws Exception;

	/**
     * 补充资料表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PubSupplymaterialLog> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 补充资料表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubSupplymaterialLog getItemInfoById(long id) throws Exception;
    
	/**
     * 补充资料表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubSupplymaterialLog getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 补充资料表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public List<PubSupplymaterialLog> getItemList(Map<String, Object> paramMap);
}
