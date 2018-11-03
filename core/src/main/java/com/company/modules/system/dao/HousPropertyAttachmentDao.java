package com.company.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.system.domain.HousPropertyAttachment;

@RDBatisDao
public interface HousPropertyAttachmentDao extends BaseDao<T, Long> {

	int deleteByPrimaryKey(Integer id);

	int insert(HousPropertyAttachment record);

	int insertSelective(HousPropertyAttachment record);

	HousPropertyAttachment selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(HousPropertyAttachment record);

	int updateByPrimaryKey(HousPropertyAttachment record);

	int updateHousPropertyAttachmentByPropertyAssessmentId(@Param("propertyAssessmentId") Integer propertyAssessmentId,
			@Param("ids") long[] ids);

	List<Map<String, Object>> selectPropertyAttachmentInfoBypropertyAssessmentId(Integer propertyAssessmentId);
}