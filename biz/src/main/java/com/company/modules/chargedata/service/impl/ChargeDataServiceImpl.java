package com.company.modules.chargedata.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.modules.chargedata.dao.ChargeDataDao;
import com.company.modules.chargedata.dao.ChargeDataLogDao;
import com.company.modules.chargedata.dao.ChargeDataStoreDao;
import com.company.modules.chargedata.domain.ChargeData;
import com.company.modules.chargedata.domain.ChargeDataLog;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.chargedata.service.ChargeDataService;

/**
* User:    JDM
* DateTime:2016-11-30 02:08:46
* details: 押品管理,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "chargeDataServiceImpl")
public class ChargeDataServiceImpl extends BaseServiceImpl implements ChargeDataService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(ChargeDataServiceImpl.class);
    /**
	 * 押品管理dao层
	 */
    @Autowired
    private ChargeDataDao chargeDataDao;
    @Autowired
    private ChargeDataLogDao chargeDataLogDao;
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
	public Long insert(ChargeData chargeData) throws Exception {
		try {
			return chargeDataDao.insert(chargeData);
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
	public Long update(ChargeData chargeData) throws Exception {
		try {
			return (long)chargeDataDao.update(chargeData);
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
	public List<ChargeData> getPageListByMap(ChargeData chargeData) throws Exception {
		try {
			return chargeDataDao.getPageListByMap(chargeData);
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
	public ChargeData getItemInfoById(Long id) throws Exception {
		try {
			return chargeDataDao.getItemInfoById(id);
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
	public ChargeData getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return chargeDataDao.getItemInfoByProcessInstanceId(processInstanceId);
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
			return chargeDataDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return chargeDataDao;
	}

	@Override
	@Transactional
	public Long lendOut(ChargeDataLog chargeDataLog,SysUser sysUser) {
		ChargeData chargeData = new ChargeData();
		chargeData.setId(chargeDataLog.getChargeDataId());
		chargeData.setStatus(ChargeData.STATUS_LEND_OUT);
		long influence = chargeDataDao.update(chargeData);
		if (influence > 0) {
			chargeDataLog.setCheckInTime(new Date());
			chargeDataLog.setCreator(sysUser.getName());
			chargeDataLog.setType(ChargeDataLog.TYPE_LEND_OUT);
			influence = chargeDataLogDao.insert(chargeDataLog);
		}
		return influence;
	}
	
	@Override
	@Transactional
	public Long giveBack(ChargeDataLog chargeDataLog,SysUser sysUser) {
		ChargeData chargeData = new ChargeData();
		chargeData.setId(chargeDataLog.getChargeDataId());
		chargeData.setStatus(ChargeData.STATUS_IN_STORE);
		long influence = chargeDataDao.update(chargeData);
		if (influence > 0) {
			chargeDataLog.setCheckInTime(new Date());
			chargeDataLog.setCreator(sysUser.getName());
			chargeDataLog.setType(ChargeDataLog.TYPE_RETURN);
			influence = chargeDataLogDao.insert(chargeDataLog);
		}
		return influence;
	}

	@Override
	@Transactional
	public Long inStore(List<ChargeData> chargeDatas) {
		String processInstanceId = "";
		if (CollectionUtils.isNotEmpty(chargeDatas)) {
			processInstanceId = chargeDatas.get(0).getProcessInstanceId();
			for (ChargeData obj : chargeDatas) {
				obj.setCreateTime(new Date());
				obj.setStatus(ChargeData.STATUS_IN_STORE);
				chargeDataDao.insert(obj);
			}
		}
		ChargeDataStore chargeDataStore = chargeDataStoreDao.getItemInfoByProcessInstanceId(processInstanceId);
		chargeDataStore.setChargeStatus(ChargeDataStore.CHARGESTATUS_IN_STORE);
		long  influence = chargeDataStoreDao.update(chargeDataStore);
		return influence;
	}

	@Override
	@Transactional
	public Long outStore(ChargeDataStore chargeDataStore) {
		ChargeDataStore dbchargeDataStore = chargeDataStoreDao.getItemInfoByProcessInstanceId(chargeDataStore.getProcessInstanceId());
		dbchargeDataStore.setOutStoreTime(chargeDataStore.getOutStoreTime());
		dbchargeDataStore.setChargeStatus(ChargeDataStore.CHARGESTATUS_OUT_STORE);
		long  influence = chargeDataStoreDao.update(dbchargeDataStore);
		
		ChargeData chargeData = new ChargeData();
		chargeData.setProcessInstanceId(chargeDataStore.getProcessInstanceId());
		List<ChargeData> chargeDatas = chargeDataDao.getPageListByMap(chargeData);
		if (CollectionUtils.isNotEmpty(chargeDatas)) {
			for (ChargeData obj : chargeDatas) {
				obj.setStatus(ChargeData.STATUS_OUT_STORE);
				influence += chargeDataDao.update(obj);
			}
		}
//		if (CollectionUtils.isNotEmpty(chargeDataStore.getChargeDataIds())) {
//			for (Long id : chargeDataStore.getChargeDataIds()) {
//				ChargeData chargeData = new ChargeData();
//				chargeData.setId(id);
//				chargeData.setStatus(ChargeData.STATUS_OUT_STORE);
//				influence += chargeDataDao.update(chargeData);
//			}
//		}
		return influence;
	}
}