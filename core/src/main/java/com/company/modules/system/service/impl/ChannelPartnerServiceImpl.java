package com.company.modules.system.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.ChannelPartnerDao;
import com.company.modules.system.domain.ChannelPartner;
import com.company.modules.system.service.ChannelPartnerService;

@SuppressWarnings("rawtypes")
@Service(value = "channelpartnerImpl")
public class ChannelPartnerServiceImpl extends BaseServiceImpl implements ChannelPartnerService {
	/**
	 * 日志操作
	 */
	private static final Logger log = LoggerFactory.getLogger(ChannelPartnerServiceImpl.class);

	@Value("${uploadFile.path}")
	private String uploadPath;

	@Value("${uploadFile.url}")
	private String uploadFileUrl;

	@Value("${logoFile.path}")
	private String logoPath;

	@Value("${logoFile.url}")
	private String logoFileUrl;

	@Value("${docFile.path}")
	private String docPath;

	@Value("${docFile.url}")
	private String docFileUrl;

	@Value("${temletaFile.path}")
	private String templateFilePath;

	@Value("${templetaFile.name}")
	private String templateFileName;

	@Autowired
	private ChannelPartnerDao channelPartnerDao;

	@Override
	public BaseDao getMapper() {
		return channelPartnerDao;
	}

	@Override
	public String getUploadPath() {
		StringBuilder stringBuilder = new StringBuilder().append(uploadPath).append(File.separator);
		return stringBuilder.toString();
	}

	@Override
	public String getUploadFileURL() {
		return uploadFileUrl;
	}

	public String getLogoPath() {
		StringBuilder stringBuilder = new StringBuilder().append(logoPath).append(File.separator);
		return stringBuilder.toString();
	}

	public String getLogoFileURL() {
		return logoFileUrl;
	}

	public String getDocPath() {
		StringBuilder stringBuilder = new StringBuilder().append(docPath).append(File.separator);
		return stringBuilder.toString();
	}

	public String getDocFileURL() {
		return docFileUrl;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	@Override
	public long insert(ChannelPartner channelPartner) throws Exception {
		try {
			return channelPartnerDao.insert(channelPartner);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public boolean update(ChannelPartner channelPartner) throws Exception {
		try {
			return channelPartnerDao.update(channelPartner);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		try {
			return channelPartnerDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public ChannelPartner queryInfoByPartnerName(String partnerName) throws Exception {
		try {
			return channelPartnerDao.queryInfoByPartnerName(partnerName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public ChannelPartner getInfoByID(long id) throws Exception {
		try {
			return channelPartnerDao.getInfoByID(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public List<ChannelPartner> getPartnerList(Map<String, Object> map) throws Exception {
		try {
			return channelPartnerDao.getPartnerList(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public List<Map<String, Object>> getPubPartnerlistForUser(Map<String, Object> map) throws Exception {
		try {
			return channelPartnerDao.getPubPartnerlistForUser(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public Map<String, Object> getLogoInfoByName(String partnerName) throws Exception {
		try {
			return channelPartnerDao.getLogoInfoByName(partnerName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
		}
	}

}