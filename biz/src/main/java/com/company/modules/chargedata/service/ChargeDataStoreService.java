package com.company.modules.chargedata.service;

import java.util.List;
import  com.company.modules.chargedata.domain.ChargeDataStore;

/**
* User:    JDM
* DateTime:2016-12-02 11:45:24
* details: 押品管理,Service接口层
* source:  代码生成器
*/
public interface ChargeDataStoreService {

    /**
     * 押品管理表,插入数据
     * @param chargeDataStore 押品管理类
     * @return           返回页面map
     * @throws Exception
     */
    public Long insert(ChargeDataStore chargeDataStore) throws Exception;

    /**
     * 押品管理表,修改数据
     * @param chargeDataStore 押品管理类
     * @return           返回页面map
     * @throws Exception
     */
    public Long update(ChargeDataStore chargeDataStore) throws Exception;

	/**
     * 押品管理表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<ChargeDataStore> getPageListByMap(ChargeDataStore chargeDataStore) throws Exception;
    
	/**
     * 押品管理表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public ChargeDataStore getItemInfoById(Long id) throws Exception;
    
	/**
     * 押品管理表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public ChargeDataStore getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 押品管理表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(Long id) throws Exception;

}
