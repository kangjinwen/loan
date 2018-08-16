package com.company.modules.system.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.domain.query.Pagination;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysLogDao;
import com.company.modules.system.domain.SysLog;
import com.company.modules.system.service.SysLogService;

@Service(value = "sysLogServiceImpl")
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;

//	@Override
//	public SysLog getLogById(long id) throws ServiceException {
//		try {
//			return sysLogDao.getItem(id, "SysLog");
//		} catch (PersistentDataException e) {
//			throw new ServiceException(Constant.FAIL_CODE_VALUE,
//					"get data fail");
//		}
//	}

	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addLog(SysLog log) throws ServiceException {
		log.setAddTime(new Date());
		sysLogDao.insert(log);
	}

	@Override
	public SysLog getLogById(long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination getLogPageList(Pagination page, Map<String, Object> map)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
