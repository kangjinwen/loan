package com.company.modules.settleaccount.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.settleaccount.domain.PlSettlementfee;

/**
 * 结算费用表DAO接口
 * @author wgc
 * @version 1.0
 * @since 2015-06-30
 */
@RDBatisDao
@SuppressWarnings("all")
public interface PlSettlementfeeDao extends BaseDao<PlSettlementfee,Long> {
	
	/**
     * 结算管理表,分页查询数据
     * @param map
     * @return List<PlSettlementfee>
     */
    public List<Map> getCompletedPageListByMap(Map<String, Object> map);
    
    /**
     * 结算管理表,分页查询数据
     * @param map
     * @return List<PlSettlementfee>
     */
    public List<Map> getUnCompletePageListByMap(Map<String, Object> map);
    
    /**
     * 结算管理表,根据id查询数据
     * @param id
     * @return PlSettlementfee
     */
    public PlSettlementfee getItemInfoById(long id);
    
    /**
     * 结算管理表,根据流程id查询数据
     * @param processInstanceId
     * @return PlSettlementfee
     */
    public PlSettlementfee getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 结算管理表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
	

    /**
     * 根据ID更新  通用更新
     * @param map
     * @return Boolean
     * @throws PersistentDataException
     */ 
    Boolean updatePlSettlementfeeById(Map<String , Object> data) throws PersistentDataException;
    
    Boolean savePlSettlementfee(Map<String,Object> data)throws PersistentDataException;
    
    /**
     * 分页查询出的列表
     * @param mapdata
     * @return 分页列表
     * @throws PersistentDataException
     */
    List<? extends PlSettlementfee> getPlSettlementfeePageList(Map<String, Object> data) throws PersistentDataException;
        
    /**
     * 获取总记录数
     * @param param
     * @return 记录数
     * @throws PersistentDataException
     */
    int getPlSettlementfeeCount(Map<String, Object> data) throws PersistentDataException;


    /**
     * 获取结算信息
     * @param processInstanceId
     * @return
     * @throws PersistentDataException
     */
    Map<String,Object> findSettileAccountMapByProcessInstanceId(String processInstanceId)throws PersistentDataException;

	String getDocumentApplicationAssignee(Integer integer) throws PersistentDataException;
}
