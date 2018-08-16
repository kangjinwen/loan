package com.company.modules.rebate.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.rebate.dao.RebateManageDao;
import com.company.modules.rebate.domain.HousRebate;
import com.company.modules.rebate.service.RebateManageService;

@SuppressWarnings("rawtypes")
@Service(value = "RebateManageServiceImpl")
public class RebateManageServiceImpl extends BaseServiceImpl implements RebateManageService{
	
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(RebateManageServiceImpl.class);
    /**
	 * 公证登记dao层
	 */
    @Autowired
    private RebateManageDao rebateManageDao;

    @Override
	public BaseDao getMapper() {
		// TODO Auto-generated method stub
		return rebateManageDao;
	}
    
    
	@Override
	public Map<String, Object> getBorrowBasicDataByInstanceId(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		try {
			return rebateManageDao.getAuditBasicDataByInstanceId(paramMap);
		} catch (Exception e) {
			
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}


	@Override
	public Map<String, Object> getRebateAuditDataByInstanceId(
			String processInstanceId) throws Exception {
		// TODO Auto-generated method stub
		try {
			return rebateManageDao.getRebateAuditDataByInstanceId(processInstanceId);
		} catch (Exception e) {
			
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public Map<String, Object> getRebateHandleDataByInstanceId(
			String processInstanceId) throws Exception {
		// TODO Auto-generated method stub
		try {
			return rebateManageDao.getRebateHandleDataByInstanceId(processInstanceId);
		} catch (Exception e) {
			
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}


	@Override
	public List<HousRebate> getHousRebateListByMap(Map<String, Object> paramMap) {
		return rebateManageDao.getHousRebateListByMap(paramMap);
	}



}
