package com.company.common.dao;

import com.company.common.domain.PubCustomer;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;

/**
* User:    huy
* DateTime:2016-12-08 17:53:10
* details: 客户管理,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubCustomerDao extends BaseDao<PubCustomer,Long> {

    /**
     * 客户管理表,分页查询数据
     * @param map
     * @return List<PubCustomer>
     */
    public List<PubCustomer> getPageListByMap(Map<String, Object> map);
    
    /**
     * 客户管理表,根据id查询数据
     * @param id
     * @return PubCustomer
     */
    public PubCustomer getItemInfoById(long id);
    
    /**
     * 客户管理表,根据流程id查询数据
     * @param processInstanceId
     * @return PubCustomer
     */
    public PubCustomer getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 客户管理表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
    
    public PubCustomer getCustomerByProcessInstanceId(String processInstanceId);
    
    
    public List<Map<String,Object>> getCustomerListByMap(Map<String, Object> map);
    /*  根据创建人查找客户记录 */
    public List<Map<String,Object>> getCustomerListByCreator(Map<String, Object> map);
}
