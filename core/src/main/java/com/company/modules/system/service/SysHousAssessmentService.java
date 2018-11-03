package com.company.modules.system.service;

import java.util.List;
import java.util.Map;

import com.company.modules.system.domain.HousPropertyAssessment;
import com.company.modules.system.domain.HousPropertyAttachment;
import com.github.pagehelper.PageInfo;

public interface SysHousAssessmentService {
	public String getUploadPath();

	public String getUploadFileURL();

	public String getLogoPath();

	public String getLogoFileURL();

	public PageInfo<Map<String, Object>> queryHousPropertyAssessmentListByStatus(Integer status, Integer pageNum,
			Integer pageSize, String customerName, String houseName, String createTime, Integer creatorId)
			throws Exception;

	public PageInfo<Map<String, Object>> queryHousPropertyAssessmentListByStatusOfAll(Integer status, Integer pageNum,
			Integer pageSize, String customerName, String houseName, String createTime) throws Exception;

	public HousPropertyAssessment getInfoByID(long id) throws Exception;

	public int updateHousPropertyAssessmentInfoByID(HousPropertyAssessment record) throws Exception;

	public int updateHousPropertyAttachmentInfoByID(HousPropertyAttachment record) throws Exception;

	public int insertHousPropertyAttachmentInfoSelective(HousPropertyAttachment record) throws Exception;

	public int insertHousPropertyAssessmentInfoSelective(HousPropertyAssessment record) throws Exception;

	public int updateHousPropertyAttachmentByPropertyAssessmentId(Integer propertyAssessmentId, long[] ids)
			throws Exception;

	public List<Map<String, Object>> selectPropertyAttachmentInfoBypropertyAssessmentId(Integer propertyAssessmentId)
			throws Exception;

	public List<Map<String, Object>> getAssessmentList(Map<String, Object> data) throws Exception;
}
