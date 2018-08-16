package com.company.modules.advance.service;

import java.util.Map;

import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.system.domain.SysUser;

import java.math.BigDecimal;
import java.util.List;

/**
* User:    gaoshan
* DateTime:2016-09-14 11:54:13
* details: 垫资申请,Service接口层
* source:  代码生成器
*/
public interface HousAdvanceApplyService {
	
	public void createAdvanceTasks(Long processInstanceId, Long projectId, Long consultId, SysUser sysUser,BigDecimal advanceApplyAmount,BigDecimal advanceRateAmount,BigDecimal advanceAmount,String type,String remark) throws Exception;

    /**
     * 垫资申请表,插入数据
     * @param housAdvanceApply 垫资申请类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(HousAdvanceApply housAdvanceApply) throws Exception;
    
    public Map<String, Object> getHousAdvanceApplyInfo(long projectId) throws Exception;
    
    public Map<String, Object> getHousAdvanceApply(long processInstanceId) throws Exception;

    /**
     * 垫资申请表,修改数据
     * @param housAdvanceApply 垫资申请类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(HousAdvanceApply housAdvanceApply) throws Exception;

	/**
     * 垫资申请表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<HousAdvanceApply> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 垫资申请表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public HousAdvanceApply getItemInfoById(long id) throws Exception;
    
	/**
     * 垫资申请表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public HousAdvanceApply getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 垫资申请表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
}
