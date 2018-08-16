package com.company.modules.workflow.dao;

import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;

@RDBatisDao
public interface HistoryDao extends BaseDao<T,Long>{

	Map<String, Object> queryTheLatestHistoricTask(Map<String, Object> map);
}
