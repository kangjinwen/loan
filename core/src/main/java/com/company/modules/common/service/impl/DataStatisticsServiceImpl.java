package com.company.modules.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.modules.common.dao.DataStatisticsDao;
import com.company.modules.common.service.DataStatisticsService;

/**
 * @author Administrator
 *
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {
	@Autowired
	private DataStatisticsDao dataStatisticsDao;

	@Override
	public List<Map<String,Object>> queryBorrowInfo(Map<String, Object> data) throws Exception {
		return dataStatisticsDao.queryBorrowInfo(data);
	}

	@Override
	public List<Map<String,Object>> queryHouseAssessmentInfo(Map<String, Object> data) throws Exception {
		return dataStatisticsDao.queryHouseAssessmentInfo(data);
	}

}
