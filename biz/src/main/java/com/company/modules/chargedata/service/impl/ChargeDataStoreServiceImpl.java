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
import com.company.modules.chargedata.dao.ChargeDataStoreDao;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.chargedata.service.ChargeDataStoreService;

/**
* User:    JDM
* DateTime:2016-12-02 11:45:24
* details: 押品管理,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "chargeDataStoreServiceImpl")
public class ChargeDataStoreServiceImpl extends BaseServiceImpl implements ChargeDataStoreService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(ChargeDataStoreServiceImpl.class);
    /**
	 * 押品管理dao层
	 */
    @Autowired
    private ChargeDataStoreDao chargeDataStoreDao;

	/**
	 * 押品管理表,插入数据
	 * @param collateral 押品管理类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Long insert(ChargeDataStore chargeDataStore) throws Exception {
		try {
			return chargeDataStoreDao.insert(chargeDataStore);
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
	public Long update(ChargeDataStore chargeDataStore) throws Exception {
		try {
			return (long)chargeDataStoreDao.update(chargeDataStore);
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
	public List<ChargeDataStore> getPageListByMap(ChargeDataStore chargeDataStore) throws Exception {
		try {
			return chargeDataStoreDao.getPageListByMap(chargeDataStore);
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
	public ChargeDataStore getItemInfoById(Long id) throws Exception {
		try {
			return chargeDataStoreDao.getItemInfoById(id);
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
	public ChargeDataStore getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return chargeDataStoreDao.getItemInfoByProcessInstanceId(processInstanceId);
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
			return chargeDataStoreDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return chargeDataStoreDao;
	}
}