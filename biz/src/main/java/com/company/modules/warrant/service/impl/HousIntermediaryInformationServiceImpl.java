package com.company.modules.warrant.service.impl;

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
import com.company.modules.warrant.dao.HousIntermediaryInformationDao;
import com.company.modules.warrant.domain.HousIntermediaryInformation;
import com.company.modules.warrant.service.HousIntermediaryInformationService;

/**
* User:    fzc
* DateTime:2016-08-10 05:04:19
* details: 房屋中介信息(权证下户),Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housIntermediaryInformationServiceImpl")
public class HousIntermediaryInformationServiceImpl extends BaseServiceImpl implements HousIntermediaryInformationService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousIntermediaryInformationServiceImpl.class);
    /**
	 * 房屋中介信息(权证下户)dao层
	 */
    @Autowired
    private HousIntermediaryInformationDao housIntermediaryInformationDao;

	/**
	 * 房屋中介信息(权证下户)表,插入数据
	 * @param collateral 房屋中介信息(权证下户)类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousIntermediaryInformation housIntermediaryInformation) throws Exception {
		try {
			return housIntermediaryInformationDao.insert(housIntermediaryInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 房屋中介信息(权证下户)表,修改数据
	* @param collateral 房屋中介信息(权证下户)类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousIntermediaryInformation housIntermediaryInformation) throws Exception {
		try {
			return housIntermediaryInformationDao.update(housIntermediaryInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 房屋中介信息(权证下户)表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousIntermediaryInformation> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housIntermediaryInformationDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 房屋中介信息(权证下户)表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousIntermediaryInformation getItemInfoById(long id) throws Exception {
		try {
			return housIntermediaryInformationDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 房屋中介信息(权证下户)表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousIntermediaryInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housIntermediaryInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 房屋中介信息(权证下户)表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housIntermediaryInformationDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housIntermediaryInformationDao;
	}
}