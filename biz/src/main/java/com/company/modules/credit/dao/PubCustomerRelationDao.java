package com.company.modules.credit.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.credit.domain.PubCustomerRelation;


/**
 * 联系明细表DAO接口
 * @version 1.0
 * @since 2016-12-12
 */
@RDBatisDao
public interface PubCustomerRelationDao extends BaseDao<PubCustomerRelation, Long> {

	/**
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getCustomerRelationList(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getCustomerRelationDetail(Map<String, Object> map);
	
}
