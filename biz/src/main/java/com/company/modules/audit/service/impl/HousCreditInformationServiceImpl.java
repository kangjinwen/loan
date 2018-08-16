package com.company.modules.audit.service.impl;

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
import com.company.modules.audit.dao.HousCreditInformationDao;
import com.company.modules.audit.domain.HousCreditInformation;
import com.company.modules.audit.service.HousCreditInformationService;

/**
* User:    fzc
* DateTime:2016-08-14 01:30:18
* details: 征信信息表(面审),Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housCreditInformationServiceImpl")
public class HousCreditInformationServiceImpl extends BaseServiceImpl implements HousCreditInformationService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousCreditInformationServiceImpl.class);
    /**
	 * 征信信息表(面审)dao层
	 */
    @Autowired
    private HousCreditInformationDao housCreditInformationDao;

	/**
	 * 征信信息表(面审)表,插入数据
	 * @param collateral 征信信息表(面审)类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousCreditInformation housCreditInformation) throws Exception {
		try {
			return housCreditInformationDao.insert(housCreditInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 征信信息表(面审)表,修改数据
	* @param collateral 征信信息表(面审)类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousCreditInformation housCreditInformation) throws Exception {
		try {
			return housCreditInformationDao.update(housCreditInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 征信信息表(面审)表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousCreditInformation> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housCreditInformationDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 征信信息表(面审)表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousCreditInformation getItemInfoById(long id) throws Exception {
		try {
			return housCreditInformationDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 征信信息表(面审)表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousCreditInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housCreditInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 征信信息表(面审)表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housCreditInformationDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housCreditInformationDao;
	}
}