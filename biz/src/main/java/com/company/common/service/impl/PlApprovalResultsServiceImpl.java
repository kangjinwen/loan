package com.company.common.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PlApprovalResultsDao;
import com.company.common.domain.PlApprovalResults;
import com.company.common.service.PlApprovalResultsService;

@SuppressWarnings("rawtypes")
@Service(value = "plApprovalResultsServiceImpl")
public class PlApprovalResultsServiceImpl extends BaseServiceImpl implements PlApprovalResultsService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlApprovalResultsServiceImpl.class);

    @Autowired
    private PlApprovalResultsDao plApprovalResultsDao;

	public long insert(PlApprovalResults plApprovalResults) throws Exception {
		try {
			return plApprovalResultsDao.insert(plApprovalResults);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public long update(PlApprovalResults plApprovalResults) throws Exception {
		try {
			return plApprovalResultsDao.update(plApprovalResults);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}


	@Override
	public List<PlApprovalResults> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plApprovalResultsDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public PlApprovalResults getItemInfoById(long id) throws Exception {
		try {
			return plApprovalResultsDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public Map<String, Object> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plApprovalResultsDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public PlApprovalResults getInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plApprovalResultsDao.getInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plApprovalResultsDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public BaseDao getMapper() {
		return plApprovalResultsDao;
	}

	@Override
	public PlApprovalResults getItemInfoByConsultId(Long consultId) {
		return plApprovalResultsDao.getItemInfoByConsultId(consultId);
	}
}