package com.company.modules.supplymaterial.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.modules.supplymaterial.dao.PubSupplymaterialLogDao;
import com.company.modules.supplymaterial.domain.PubSupplymaterialLog;
import com.company.modules.supplymaterial.service.PubSupplymaterialLogService;

/**
* User:    JDM
* DateTime:2016-08-18 03:51:48
* details: 补充资料,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubSupplymaterialLogServiceImpl")
public class PubSupplymaterialLogServiceImpl extends BaseServiceImpl implements PubSupplymaterialLogService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubSupplymaterialLogServiceImpl.class);
    /**
	 * 补充资料dao层
	 */
    @Autowired
    private PubSupplymaterialLogDao pubSupplymaterialLogDao;

	/**
	 * 补充资料表,插入数据
	 * @param collateral 补充资料类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubSupplymaterialLog pubSupplymaterialLog) throws Exception {
		try {
			return pubSupplymaterialLogDao.insert(pubSupplymaterialLog);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 补充资料表,修改数据
	* @param collateral 补充资料类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PubSupplymaterialLog pubSupplymaterialLog) throws Exception {
		try {
			return pubSupplymaterialLogDao.update(pubSupplymaterialLog);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 补充资料表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubSupplymaterialLog> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubSupplymaterialLogDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 补充资料表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubSupplymaterialLog getItemInfoById(long id) throws Exception {
		try {
			return pubSupplymaterialLogDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 补充资料表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubSupplymaterialLog getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubSupplymaterialLogDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 补充资料表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return pubSupplymaterialLogDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubSupplymaterialLogDao;
	}

	@Override
	public List<PubSupplymaterialLog> getItemList(Map<String, Object> paramMap) {
		return pubSupplymaterialLogDao.getItemList(paramMap);
	}
}