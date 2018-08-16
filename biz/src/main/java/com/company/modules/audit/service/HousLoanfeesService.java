package com.company.modules.audit.service;

import java.util.Map;
import java.util.List;
import  com.company.modules.audit.domain.HousLoanfees;

/**
* User:    fzc
* DateTime:2016-08-17 03:54:15
* details: 返费签单/代收服务费,Service接口层
* source:  代码生成器
*/
public interface HousLoanfeesService {

    /**
     * 返费签单/代收服务费表,插入数据
     * @param housLoanfees 返费签单/代收服务费类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousLoanfees housLoanfees) throws Exception;

    /**
     * 返费签单/代收服务费表,修改数据
     * @param housLoanfees 返费签单/代收服务费类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousLoanfees housLoanfees) throws Exception;

	/**
     * 返费签单/代收服务费表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousLoanfees> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 返费签单/代收服务费表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousLoanfees getItemInfoById(long id) throws Exception;
    
	/**
     * 返费签单/代收服务费表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousLoanfees getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 返费签单/代收服务费表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

    /**
     * @description 查询放款表单信息
     * @param processInstanceId
     * @return
     * @throws Exception
     * @author fzc
     * @return Map<String,Object>
     * @since  1.0.0
     */
	public Map<String, Object> getLoanFormInfo(String processInstanceId) throws Exception;

	public long insertOrUpdate(HousLoanfees housLoanfees, Long loginUserId) throws Exception;

}
