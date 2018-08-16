package com.company.common.dao;

import com.company.common.domain.Customer;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;

/**
* User:    gaoshan
* DateTime:2016-09-14 11:54:13
* details: 垫资申请,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface CustomerDao extends BaseDao<Customer,Long> {

	
	public List<Customer> getPageListByMap(Map<String, Object> map);
	
	public Boolean updateCustomerById(Map<String , Object> map);
	
	public List<Map<String,Object>> getCustomerMapPageList(Map<String , Object> map);
}
