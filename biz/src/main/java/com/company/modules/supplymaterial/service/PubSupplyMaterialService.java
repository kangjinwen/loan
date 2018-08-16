package com.company.modules.supplymaterial.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.supplymaterial.domain.PubSupplymaterial;

/**
* User:    JDM
* DateTime:2016-08-17 11:31:42
* details: 补充资料,Service接口层
* source:  代码生成器
*/
public interface PubSupplyMaterialService {

    /**
     * 补充资料表,插入数据
     * @param pubAttachment 补充资料类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PubSupplymaterial pubAttachment) throws Exception;

    /**
     * 补充资料表,修改数据
     * @param pubAttachment 补充资料类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PubSupplymaterial pubAttachment) throws Exception;

	/**
     * 补充资料表,分页查询数据
     * @param data
	 * @param isCompleted 
     * @return
     * @throws Exception
     */
    public List<Map> getPageListByMap(Map<String , Object> data, Boolean isCompleted) throws Exception;
    
	/**
     * 补充资料表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PubSupplymaterial getItemInfoById(long id) throws Exception;
    
	/**
     * 补充资料表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PubSupplymaterial getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 补充资料表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public PubSupplymaterial getItemByProjectId(long id);

	public List<PubSupplymaterial> getItemList(Map<String, Object> paramMap);
}
