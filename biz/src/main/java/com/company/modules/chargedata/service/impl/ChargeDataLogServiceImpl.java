package com.company.modules.chargedata.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.modules.chargedata.dao.ChargeDataLogDao;
import com.company.modules.chargedata.domain.ChargeDataLog;
import com.company.modules.chargedata.service.ChargeDataLogService;

/**
* User:    JDM
* DateTime:2016-11-30 02:08:46
* details: 押品管理,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "chargeDataLogServiceImpl")
public class ChargeDataLogServiceImpl extends BaseServiceImpl implements ChargeDataLogService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(ChargeDataLogServiceImpl.class);
    /**
	 * 押品管理dao层
	 */
    @Autowired
    private ChargeDataLogDao chargeDataLogDao;

	/**
	 * 押品管理表,插入数据
	 * @param collateral 押品管理类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Long insert(ChargeDataLog chargeDataLog) throws Exception {
		try {
			return chargeDataLogDao.insert(chargeDataLog);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 押品管理表,修改数据
	* @param collateral 押品管理类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	@Transactional
	public Long update(ChargeDataLog chargeDataLog) throws Exception {
		try {
			return (long)chargeDataLogDao.update(chargeDataLog);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 押品管理表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<ChargeDataLog> getPageListByMap(ChargeDataLog chargeDataLog) throws Exception {
		try {
			return chargeDataLogDao.getPageListByMap(chargeDataLog);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 押品管理表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public ChargeDataLog getItemInfoById(Long id) throws Exception {
		try {
			return chargeDataLogDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 押品管理表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public ChargeDataLog getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return chargeDataLogDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 押品管理表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	@Transactional
	public int deleteById(Long id) throws Exception {
		try {
			return chargeDataLogDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return chargeDataLogDao;
	}
}