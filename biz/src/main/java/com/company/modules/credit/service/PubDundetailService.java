package com.company.modules.credit.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.credit.domain.PubDundetail;

/**
 * 催收记录表Service
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
public interface PubDundetailService {

    /**
     * 添加催收记录表
     * 
     * @param dundetail 催收记录表实体
     */
    Boolean addDundetail(PubDundetail dundetail) throws ServiceException;

    /**
     * 查询催收记录表
     * 
     * @param id 主键ID
     * @return 催收记录表
     */
    PubDundetail getDundetailById(Long id) throws ServiceException;

    /**
     * 催收记录表修改
     * @param dundetail 催收记录表实体
     */
    int dundetailUpdate(PubDundetail dundetail) throws ServiceException;

    /**
     * 催收记录表删除
     * 
     * @param id 主键ID
     */
    int deleteDundetail(Long id) throws ServiceException;
    
    /**
     *根据ID更新  通用更新
     *@param map
     *@return Boolean
     */ 
    Boolean updateDundetailById(Map<String , Object> data) throws ServiceException;
    
    /**
     * 获取总记录数
     * @return 记录数
     */
    int getDundetailCount(Map<String, Object> data) throws ServiceException;
    
    /**
     * 获取催收记录表集合
     * @param map 查询参数
     * @return 催收记录表信息
     */
    List<PubDundetail> getDundetailList(Map<String , Object> data) throws ServiceException;
    
    /**
     * 
     * @param data
     * @return
     */
    List<Map<String, Object>> getDundetailAndRepaymentList(Map<String , Object> data) throws Exception;
    
}
