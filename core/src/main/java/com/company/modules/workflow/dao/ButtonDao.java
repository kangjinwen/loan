package com.company.modules.workflow.dao;

import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;

@RDBatisDao
public interface ButtonDao extends BaseDao<T,Long>{
	/**
	 * 根据任务ID 查询当前活动的activityID
	 * @description
	 * @param taskId
	 * @return
	 * @author wangdk
	 * @return Map<String,Object>
	 * @since  1.0.0
	*/
	Map<String,Object> queryActivityId(String taskId) ;
}
