package com.company.modules.common.dao;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@RDBatisDao
public interface DataStatisticsDao extends BaseDao{
	List<Map<String, Object>> queryBorrowInfo(Map<String, Object> data);

	List<Map<String,Object>> queryHouseAssessmentInfo(Map<String, Object> data);
}
