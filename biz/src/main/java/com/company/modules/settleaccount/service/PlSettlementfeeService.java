package com.company.modules.settleaccount.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.settleaccount.domain.PlSettlementfee;

/**
 * 结算费用表Service
 * @author wgc
 * @version 1.0
 * @since 2015-06-30
 */
public interface PlSettlementfeeService {

    

    /**
     * @description 结算信息明细
     * @param processInstanceId
     * @return
     * @throws ServiceException
     * @author wtc
     * @return Map<String,Object>
     * @since  1.0.0
    */
    Map<String,Object> settleAccountsDetail(String processInstanceId) throws ServiceException;
    
    void saveOrUpdateSettleAccountsDetail(Map<String,Object> data)throws ServiceException;
    
    
    
    /**
     * 结算管理表,插入数据
     * @param plSettlementfee 结算管理类
     * @return           返回页面map
     * @throws Exception
     */
    public long insertPlSettlementfee(PlSettlementfee plSettlementfee) throws Exception;

    /**
     * 结算管理表,修改数据
     * @param plSettlementfee 结算管理类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlSettlementfee plSettlementfee) throws Exception;

	/**
     * 结算管理表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<Map> getPageListByMap(Map<String , Object> data,Boolean isCompleted) throws Exception;
    
	/**
     * 结算管理表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlSettlementfee getItemInfoById(long id) throws Exception;
    
	/**
     * 结算管理表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlSettlementfee getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 结算管理表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;
	
}
