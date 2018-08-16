package com.company.modules.credit.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.credit.domain.PubRepaymentdetail;

/**
 * 还款明细表Service
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
public interface RepaymentdetailService {

    /**
     * 添加还款明细表
     * 
     * @param repaymentdetail 还款明细表实体
     */
    Boolean addRepaymentdetail(PubRepaymentdetail repaymentdetail) throws ServiceException;

    /**
     * 查询还款明细表
     * 
     * @param id 主键ID
     * @return 还款明细表
     */
    PubRepaymentdetail getRepaymentdetailById(Long id) throws ServiceException;

    /**
     * 还款明细表修改
     * @param repaymentdetail 还款明细表实体
     */
    int repaymentdetailUpdate(PubRepaymentdetail repaymentdetail) throws ServiceException;

    /**
     * 还款明细表删除
     * 
     * @param id 主键ID
     */
    int deleteRepaymentdetail(Long id) throws ServiceException;
    
    /**
     *根据ID更新  通用更新
     *@param map
     *@return Boolean
     */ 
    Boolean updateRepaymentdetailById(Map<String , Object> data) throws ServiceException;
    
    /**
     * 获取总记录数
     * @return 记录数
     */
    int getRepaymentdetailCount(Map<String, Object> data) throws ServiceException;
    
    int getRepaymentdetailCountForDun(Map<String, Object> data) throws ServiceException;
    
    
    /**
     * 催收统计分页列表
     * @param data
     * @return
     * @throws ServiceException
     * @author htz
     */
    List<? extends PubRepaymentdetail> getCollectionList(Map<String, Object> data) throws ServiceException;
    int getCollectionCountList(Map<String, Object> data) throws ServiceException;


	int updateRepaymentdetail(PubRepaymentdetail pd) throws  ServiceException;
	
	/**
	 * @description 查询某日（天）的未还款明细
	 * @param day 日期
	 * @return
	 * @throws ServiceException
	 * @author wtc
	 * @return List<Map<String,Object>>
	 * @since  1.0.0
	*/
	List<Map<String,Object>> queryRepaymentdetailByRepaymentDay(Date day)throws ServiceException ;
	
}
