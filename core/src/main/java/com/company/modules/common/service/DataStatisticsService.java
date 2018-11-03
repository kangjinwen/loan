package com.company.modules.common.service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public interface DataStatisticsService {

	List<Map<String,Object>> queryBorrowInfo(Map<String, Object> data) throws Exception ;

	List<Map<String,Object>> queryHouseAssessmentInfo(Map<String, Object> data) throws Exception ;

}
