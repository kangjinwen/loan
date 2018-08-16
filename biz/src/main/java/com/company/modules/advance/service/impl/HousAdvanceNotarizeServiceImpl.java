package com.company.modules.advance.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.advance.dao.HousAdvanceNotarizeDao;
import com.company.modules.advance.domain.HousAdvanceNotarize;
import com.company.modules.advance.service.HousAdvanceNotarizeService;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;

/**
* User:    wulb
* DateTime:2016-09-21 09:24:32
* details: 借款基本信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housAdvanceNotarizeServiceImpl")
public class HousAdvanceNotarizeServiceImpl extends BaseServiceImpl implements HousAdvanceNotarizeService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousAdvanceNotarizeServiceImpl.class);
    /**
	 * 借款基本信息dao层
	 */
    @Autowired
    private HousAdvanceNotarizeDao housAdvanceNotarizeDao;

	/**
	 * 借款基本信息表,插入数据
	 * @param collateral 借款基本信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousAdvanceNotarize housAdvanceNotarize) throws Exception {
		try {
			return housAdvanceNotarizeDao.insert(housAdvanceNotarize);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 借款基本信息表,修改数据
	* @param collateral 借款基本信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousAdvanceNotarize housAdvanceNotarize) throws Exception {
		try {
			return housAdvanceNotarizeDao.update(housAdvanceNotarize);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款基本信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousAdvanceNotarize> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housAdvanceNotarizeDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款基本信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousAdvanceNotarize getItemInfoById(long id) throws Exception {
		try {
			return housAdvanceNotarizeDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款基本信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousAdvanceNotarize getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housAdvanceNotarizeDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 借款基本信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housAdvanceNotarizeDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housAdvanceNotarizeDao;
	}

	@Override
	public Map<String, Object> getHousAdvanceNotarizeInfo(long projectId) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = housAdvanceNotarizeDao.getHousAdvanceNotarizeInfo(projectId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}

	@Override
	public Map<String, Object> getHousAdvanceNotarize(long processInstanceId) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = housAdvanceNotarizeDao.getHousAdvanceNotarize(processInstanceId);		
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}
}