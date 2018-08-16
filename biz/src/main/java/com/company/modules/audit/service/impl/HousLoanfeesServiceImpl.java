package com.company.modules.audit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.audit.dao.HousLoanfeesDao;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.audit.service.HousLoanfeesService;
import com.company.modules.common.exception.ServiceException;

/**
* User:    fzc
* DateTime:2016-08-17 03:54:15
* details: 返费签单/代收服务费,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housLoanfeesServiceImpl")
public class HousLoanfeesServiceImpl extends BaseServiceImpl implements HousLoanfeesService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousLoanfeesServiceImpl.class);
    /**
	 * 返费签单/代收服务费dao层
	 */
    @Autowired
    private HousLoanfeesDao housLoanfeesDao;

	/**
	 * 返费签单/代收服务费表,插入数据
	 * @param collateral 返费签单/代收服务费类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousLoanfees housLoanfees) throws Exception {
		try {
			return housLoanfeesDao.insert(housLoanfees);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 返费签单/代收服务费表,修改数据
	* @param collateral 返费签单/代收服务费类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousLoanfees housLoanfees) throws Exception {
		try {
			return housLoanfeesDao.update(housLoanfees);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 返费签单/代收服务费表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousLoanfees> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housLoanfeesDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 返费签单/代收服务费表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousLoanfees getItemInfoById(long id) throws Exception {
		try {
			return housLoanfeesDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 返费签单/代收服务费表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousLoanfees getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housLoanfeesDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 返费签单/代收服务费表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housLoanfeesDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public Map<String, Object> getLoanFormInfo(String processInstanceId) throws Exception {
		try {
			return housLoanfeesDao.getLoanFormInfo(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public long insertOrUpdate(HousLoanfees housLoanfees, Long loginUserId) throws Exception {
		long housLoanfeesNum = 0;
		try {
			if(Objects.equals(null, housLoanfees.getId()) || Objects.equals(0L, housLoanfees.getId())){
				housLoanfees.setCreateTime(new Date());
				housLoanfees.setCreator(loginUserId);
				housLoanfeesNum = housLoanfeesDao.insert(housLoanfees);
			}else{
				housLoanfees.setModifier(loginUserId);
				housLoanfees.setModifyTime(new Date());
				housLoanfeesNum = housLoanfeesDao.update(housLoanfees);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return housLoanfeesNum;
	}

	@Override
	public BaseDao getMapper() {
		return housLoanfeesDao;
	}
}