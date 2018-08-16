package com.company.modules.finance.service;

import java.util.Map;
import java.math.BigDecimal;
import java.util.List;

import  com.company.modules.finance.domain.HousLowerCost;
import com.company.modules.system.domain.SysUser;

/**
* User:    fzc
* DateTime:2016-08-12 10:23:50
* details: 下户费表,Service接口层
* source:  代码生成器
*/
public interface HousLowerCostService {
	
	public void createReturnFeeTasks(Long processInstanceId, Long projectId, SysUser sysUser,BigDecimal applyRefundAmount,String type,String remark) throws Exception;

    /**
     * 下户费表表,插入数据
     * @param housLowerCost 下户费表类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousLowerCost housLowerCost) throws Exception;

    /**
     * 下户费表表,修改数据
     * @param housLowerCost 下户费表类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousLowerCost housLowerCost) throws Exception;

	/**
     * 下户费表表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousLowerCost> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 下户费表表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousLowerCost getItemInfoById(long id) throws Exception;
    
	/**
     * 下户费表表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousLowerCost getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 下户费表表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public Map<String, Object> getHousLowerCostBasicInfo(long projectId) throws Exception;

	public Map<String, Object> getHousLowerCostInfo(long processInstanceId) throws Exception;
}
