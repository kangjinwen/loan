package com.company.modules.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.company.modules.system.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.HousPropertyAssessmentDao;
import com.company.modules.system.dao.HousPropertyAttachmentDao;
import com.company.modules.system.domain.HousPropertyAssessment;
import com.company.modules.system.domain.HousPropertyAttachment;
import com.company.modules.system.service.SysHousAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service(value = "SysHousAssessmentService")
public class SysHousAssessmentServiceImpl implements SysHousAssessmentService {
	/**
	 * 日志操作
	 */
	private static final Logger log = LoggerFactory.getLogger(ChannelPartnerServiceImpl.class);
	@Autowired
	private HousPropertyAssessmentDao housPropertyAssessmentDao;
	@Autowired
	private HousPropertyAttachmentDao housPropertyAttachmentDao;

	@Value("${logoFile.path}")
	private String logoPath;

	@Value("${uploadFile.url}")
	private String uploadFileUrl;

	@Value("${logoFile.url}")
	private String logoFileUrl;
	@Value("${uploadFile.path}")
	private String uploadPath;

	@Value("${temletaFile.path}")
	private String templateFilePath;

	@Value("${templetaFile.name}")
	private String templateFileName;
	@Autowired
	private MailService mailService;
	@Override
	public String getUploadPath() {
		StringBuilder stringBuilder = new StringBuilder().append(uploadPath).append(File.separator);
		return stringBuilder.toString();
	}

	@Override
	public String getUploadFileURL() {
		return uploadFileUrl;
	}

	@Override

	public String getLogoPath() {
		StringBuilder stringBuilder = new StringBuilder().append(logoPath).append(File.separator);
		return stringBuilder.toString();
	}

	@Override
	public String getLogoFileURL() {
		return logoFileUrl;
	}

	@Override
	public PageInfo<Map<String, Object>> queryHousPropertyAssessmentListByStatus(Integer status, Integer pageNum,
			Integer pageSize, String customerName, String houseName, String createTime, Integer creatorId)
			throws Exception {
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<Map<String, Object>> historicLoanProcess = new ArrayList<Map<String, Object>>();
			try {
				historicLoanProcess = housPropertyAssessmentDao.queryHousPropertyAssessmentListByStatus(status, pageNum,
						pageSize, customerName, houseName, createTime, creatorId);
			} catch (Exception e) {
				throw new ServiceException("数据库查询历史出错!", e);
			}
			PageInfo<Map<String, Object>> page = new PageInfo<>(historicLoanProcess);
			return page;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}

	}

	@Override
	public PageInfo<Map<String, Object>> queryHousPropertyAssessmentListByStatusOfAll(Integer status, Integer pageNum,
			Integer pageSize, String customerName, String houseName, String createTime) throws Exception {
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<Map<String, Object>> historicLoanProcess = new ArrayList<Map<String, Object>>();
			try {
				historicLoanProcess = housPropertyAssessmentDao.queryHousPropertyAssessmentListByStatusOfAll(status,
						pageNum, pageSize, customerName, houseName, createTime);
			} catch (Exception e) {
				throw new ServiceException("数据库查询历史出错!", e);
			}
			PageInfo<Map<String, Object>> page = new PageInfo<>(historicLoanProcess);
			return page;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}

	}

	@Override
	public HousPropertyAssessment getInfoByID(long id) throws Exception {
		try {
			return housPropertyAssessmentDao.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int updateHousPropertyAssessmentInfoByID(HousPropertyAssessment record) throws Exception {
		try {
			return housPropertyAssessmentDao.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int updateHousPropertyAttachmentInfoByID(HousPropertyAttachment record) throws Exception {
		try {
			return housPropertyAttachmentDao.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int insertHousPropertyAttachmentInfoSelective(HousPropertyAttachment record) throws Exception {
		try {
			return housPropertyAttachmentDao.insertSelective(record);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int insertHousPropertyAssessmentInfoSelective(HousPropertyAssessment record) throws Exception {
		try {
			mailService.SendMailForAdd(record);
			return housPropertyAssessmentDao.insertSelective(record);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int updateHousPropertyAttachmentByPropertyAssessmentId(Integer propertyAssessmentId, long[] ids)
			throws Exception {
		try {
			return housPropertyAttachmentDao.updateHousPropertyAttachmentByPropertyAssessmentId(propertyAssessmentId,
					ids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}

	}

	@Override
	public List<Map<String, Object>> selectPropertyAttachmentInfoBypropertyAssessmentId(Integer propertyAssessmentId)
			throws Exception {
		try {
			return housPropertyAttachmentDao.selectPropertyAttachmentInfoBypropertyAssessmentId(propertyAssessmentId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}

	}

	@Override
	public List<Map<String, Object>> getAssessmentList(Map<String, Object> data) throws Exception {
		try {
			return housPropertyAssessmentDao.getAssessmentList(data);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

}
