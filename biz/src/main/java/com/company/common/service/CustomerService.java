package com.company.common.service;

import java.util.List;
import java.util.Map;

import com.company.common.domain.Customer;
import com.company.modules.common.exception.ServiceException;

/**
 * 客户基本信息Service
 * @author wgc
 * @version 1.0
 * @since 2014-12-01
 */
public interface CustomerService {

    /**
     * 添加客户基本信息
     * 
     * @param customer 客户基本信息实体
     */
    Boolean addCustomer(Customer customer) throws ServiceException;

    /**
     * 查询客户基本信息
     * 
     * @param id 主键ID
     * @return 客户基本信息
     */
    Customer getCustomerById(Long id) throws ServiceException;

    /**
     * 客户基本信息修改
     * @param customer 客户基本信息实体
     */
    int customerUpdate(Customer customer) throws ServiceException;

    /**
     * 获取客户基本信息集合
     * @param map 查询参数
     * @return 客户基本信息信息
     */
    List<Customer> getCustomerList(Map<String , Object> data) throws ServiceException;
    
    /**
     *根据ID更新  通用更新
     *@param map
     *@return Boolean
     */ 
    Boolean updateCustomerById(Map<String , Object> data) throws ServiceException;
    
    
    /**
     * 分页查询出的列表
     * @param mapdata
     * @return
     */
    List<? extends Customer> getCustomerPageList(Map<String, Object> data) throws ServiceException;
    
    /**
     * 分页查询出map列表
     * @param mapdata
     * @return
     */
    List<Map<String, Object>> getCustomerMapPageList(Map<String, Object> data) throws ServiceException;
    
    /**
     * 获取总记录数
     * @return 记录数
     */
//    int getCustomerCount(Map<String, Object> data) throws ServiceException;
    
    
//    long getCustomerNumber() throws PersistentDataException, ServiceException;
    
    /**
     * 查询不同机构今日新增客户数
     * @return
     * @throws ServiceException
     */
//    long getCustomerNumberByOfficeId(String officeId) throws ServiceException;

//	Map<String,Object> getCustomer(Map<String,Object> params)throws ServiceException;
	
    /**
     * 根据证件类型和证件号码查询有效的客户信息
     * @param data
     * @return
     * @throws ServiceException 
     * @version 1.0
     * @author 吴国成
     * @created 2015年4月3日
     */
//    Map<String, Object> getCustomerByCertTypeAndCertNumber(Map<String, Object> data) throws ServiceException;
    
    /**
     * 加入黑名单--带事务处理
     * 将客户表的状态改为黑名单，同时，如果黑名单表不存在该客户，则在黑名单表增加。
     * @param param
     * @return
     * @throws ServiceException 
     * @version 1.0
     * @author 吴国成
     * @created 2015年4月7日
     */
//    Boolean addCustomerToBlack(Map<String,Object> param) throws ServiceException;
    
    
    
    
}
