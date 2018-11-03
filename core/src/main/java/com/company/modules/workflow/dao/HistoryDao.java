package com.company.modules.workflow.dao;

import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;

@RDBatisDao
public interface HistoryDao extends BaseDao<Object, Long> {

	Map<String, Object> queryTheLatestHistoricTask(Map<String, Object> map);
}
