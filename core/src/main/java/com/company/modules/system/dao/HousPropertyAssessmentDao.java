package com.company.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.system.domain.HousPropertyAssessment;

@RDBatisDao
public interface HousPropertyAssessmentDao extends BaseDao<T, Long> {

	int deleteByPrimaryKey(Integer id);

	int insert(HousPropertyAssessment record);

	int insertSelective(HousPropertyAssessment record);

	HousPropertyAssessment selectByPrimaryKey(long id);

	int updateByPrimaryKeySelective(HousPropertyAssessment record);

	int updateByPrimaryKey(HousPropertyAssessment record);

	/**
	 * 根据状态查询房产价值评估列表
	 * 
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> queryHousPropertyAssessmentListByStatus(@Param("status") Integer status,
			@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
			@Param("customerName") String customerName, @Param("houseName") String houseName,
			@Param("createTime") String createTime, @Param("creatorId") Integer creatorId);

	List<Map<String, Object>> queryHousPropertyAssessmentListByStatusOfAll(@Param("status") Integer status,
			@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
			@Param("customerName") String customerName, @Param("houseName") String houseName,
			@Param("createTime") String createTime);

	public List<Map<String, Object>> getAssessmentList(Map<String, Object> data);
}
