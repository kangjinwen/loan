package com.company.modules.instance.service.impl;

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
import com.company.modules.instance.dao.HousAssessmentAgenciesDao;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.service.HousAssessmentAgenciesService;

/**
* User:    wulb
* DateTime:2016-08-06 02:33:32
* details: 评估机构,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housAssessmentAgenciesServiceImpl")
public class HousAssessmentAgenciesServiceImpl extends BaseServiceImpl implements HousAssessmentAgenciesService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousAssessmentAgenciesServiceImpl.class);
    /**
	 * 评估机构dao层
	 */
    @Autowired
    private HousAssessmentAgenciesDao housAssessmentAgenciesDao;

	/**
	 * 评估机构表,插入数据
	 * @param collateral 评估机构类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousAssessmentAgencies housAssessmentAgencies) throws Exception {
		try {
			return housAssessmentAgenciesDao.insert(housAssessmentAgencies);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 评估机构表,修改数据
	* @param collateral 评估机构类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousAssessmentAgencies housAssessmentAgencies) throws Exception {
		try {
			return housAssessmentAgenciesDao.update(housAssessmentAgencies);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 评估机构表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousAssessmentAgencies> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housAssessmentAgenciesDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 评估机构表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousAssessmentAgencies getItemInfoById(long id) throws Exception {
		try {
			return housAssessmentAgenciesDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 评估机构表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousAssessmentAgencies> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housAssessmentAgenciesDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 评估机构表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housAssessmentAgenciesDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housAssessmentAgenciesDao;
	}
}