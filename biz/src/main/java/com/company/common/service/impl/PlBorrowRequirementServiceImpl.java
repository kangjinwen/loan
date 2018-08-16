package com.company.common.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PlBorrowRequirementDao;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.service.PlBorrowRequirementService;

/**
* User:    wulb
* DateTime:2016-08-08 02:01:19
* details: 借款需求信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "plBorrowRequirementServiceImpl")
public class PlBorrowRequirementServiceImpl extends BaseServiceImpl implements PlBorrowRequirementService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlBorrowRequirementServiceImpl.class);
    /**
	 * 借款需求信息dao层
	 */
    @Autowired
    private PlBorrowRequirementDao plBorrowRequirementDao;

	/**
	 * 借款需求信息表,插入数据
	 * @param collateral 借款需求信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PlBorrowRequirement plBorrowRequirement) throws Exception {
		try {
			return plBorrowRequirementDao.insert(plBorrowRequirement);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 借款需求信息表,修改数据
	* @param collateral 借款需求信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlBorrowRequirement plBorrowRequirement) throws Exception {
		try {
			return plBorrowRequirementDao.update(plBorrowRequirement);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	 * 借款需求信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PlBorrowRequirement> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plBorrowRequirementDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	 * 借款需求信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlBorrowRequirement getItemInfoById(long id) throws Exception {
		try {
			return plBorrowRequirementDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	 * 借款需求信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plBorrowRequirementDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	 * 借款需求信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           PlBorrowRequirement
	 * @throws Exception
	 */
	@Override
	public PlBorrowRequirement getInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plBorrowRequirementDao.getInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 借款需求信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plBorrowRequirementDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public BaseDao getMapper() {
		return plBorrowRequirementDao;
	}

	@Override
	public PlBorrowRequirement getItemInfoByConsultId(Long consultId) {
		return plBorrowRequirementDao.getItemInfoByConsultId(consultId);
	}
}