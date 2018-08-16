package com.company.modules.credit.service.impl;

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
import com.company.modules.credit.dao.PubCustomerRelationDao;
import com.company.modules.credit.domain.PubCustomerRelation;
import com.company.modules.credit.service.PubCustomerRelationService;

@SuppressWarnings("rawtypes")
@Service(value = "customerRelationService")
public class PubCustomerRelationServiceImpl extends BaseServiceImpl implements PubCustomerRelationService {

	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubCustomerRelationServiceImpl.class);
    /**
	 * 联系明细管理dao层
	 */
    @Autowired
    private PubCustomerRelationDao pubCustomerRelationDao;
    

	@Override
	public List<Map<String, Object>> getCustomerRelationList(
			Map<String, Object> paramap) throws ServiceException {
		List<Map<String,Object>> list = null;
		try {
			list = pubCustomerRelationDao.getCustomerRelationList(paramap);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return list;
	}

	@Override
	public boolean addCustomerRelation(PubCustomerRelation pubCustomerRelation) throws ServiceException {
		boolean isAdd = false;
    	try {
			isAdd = pubCustomerRelationDao.insert(pubCustomerRelation) > 0 ? true : false;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
       return isAdd;
	}


	@Override
	public List<Map<String, Object>> getCustomerRelationDetail(
			Map<String, Object> paramap) throws ServiceException {
		List<Map<String,Object>> list = null;
		try {
			list = pubCustomerRelationDao.getCustomerRelationDetail(paramap);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return list;
	}

	
	@Override
	public BaseDao getMapper() {
		return pubCustomerRelationDao;
	}

}
