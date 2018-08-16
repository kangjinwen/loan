package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.service.PubCustomerService;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PubCustomerDao;
import com.company.common.domain.PubCustomer;

/**
* User:    huy
* DateTime:2016-12-08 17:53:10
* details: 客户管理,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubCustomerServiceImpl")
public class PubCustomerServiceImpl extends BaseServiceImpl implements PubCustomerService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubCustomerServiceImpl.class);
    /**
	 * 客户管理dao层
	 */
    @Autowired
    private PubCustomerDao pubCustomerDao;

	/**
	 * 客户管理表,插入数据
	 * @param collateral 客户管理类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubCustomer pubCustomer) throws Exception {
		try {
			return pubCustomerDao.insert(pubCustomer);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 客户管理表,修改数据
	* @param collateral 客户管理类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PubCustomer pubCustomer) throws Exception {
		try {
			return pubCustomerDao.update(pubCustomer);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 客户管理表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubCustomer> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubCustomerDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 客户管理表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubCustomer getItemInfoById(long id) throws Exception {
		try {
			return pubCustomerDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 客户管理表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubCustomer getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubCustomerDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 客户管理表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return pubCustomerDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubCustomerDao;
	}

	@Override
	public PubCustomer getCustomerByProcessInstanceId(String processInstanceId) {
		// TODO Auto-generated method stub
		return pubCustomerDao.getCustomerByProcessInstanceId(processInstanceId);
	}

	@Override
	public List<Map<String, Object>> getCustomerListByMap(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pubCustomerDao.getCustomerListByMap(map);
	}

	@Override
	public List<Map<String, Object>> getCustomerListByCreator(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return pubCustomerDao.getCustomerListByCreator(map);
	}
}