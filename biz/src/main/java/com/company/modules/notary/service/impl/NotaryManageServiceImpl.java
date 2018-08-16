package com.company.modules.notary.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.modules.notary.dao.NotaryManageDao;
import com.company.modules.notary.domain.HousNotarizationRegistration;
import com.company.modules.notary.service.NotaryManageService;

/**
* User:    mcwang
* DateTime:2016-08-10 03:59:46
* details: 公证登记,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "notaryManageServiceImpl")
public class NotaryManageServiceImpl extends BaseServiceImpl implements NotaryManageService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(NotaryManageServiceImpl.class);
    /**
	 * 公证登记dao层
	 */
    @Autowired
    private NotaryManageDao notaryManageDao;

	/**
	 * 公证登记表,插入数据
	 * @param collateral 公证登记类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousNotarizationRegistration housNotarizationRegistration) throws Exception {
		try {
			return notaryManageDao.insert(housNotarizationRegistration);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	
	
	/**
	 * 公证登记表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public Map<String,Object> getNotaryRegistDataByInstanceId(String processInstanceId) throws Exception {
		try {
			return notaryManageDao.getNotaryRegistDataByInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return notaryManageDao;
	}
}