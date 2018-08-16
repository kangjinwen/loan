package com.company.modules.chargedata.service;

import java.util.List;
import  com.company.modules.chargedata.domain.ChargeDataLog;

/**
* User:    JDM
* DateTime:2016-11-30 02:08:46
* details: 押品管理,Service接口层
* source:  代码生成器
*/
public interface ChargeDataLogService {

    /**
     * 押品管理表,插入数据
     * @param chargeDataLog 押品管理类
     * @return           返回页面map
     * @throws Exception
     */
    public Long insert(ChargeDataLog chargeDataLog) throws Exception;

    /**
     * 押品管理表,修改数据
     * @param chargeDataLog 押品管理类
     * @return           返回页面map
     * @throws Exception
     */
    public Long update(ChargeDataLog chargeDataLog) throws Exception;

	/**
     * 押品管理表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<ChargeDataLog> getPageListByMap(ChargeDataLog chargeDataLog) throws Exception;
    
	/**
     * 押品管理表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public ChargeDataLog getItemInfoById(Long id) throws Exception;
    
	/**
     * 押品管理表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public ChargeDataLog getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 押品管理表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(Long id) throws Exception;
}
