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
import com.company.modules.instance.dao.HousPropertyInformationDao;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousPropertyInformationService;

/**
* User:    wulb
* DateTime:2016-08-06 02:28:59
* details: 房产信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housPropertyInformationServiceImpl")
public class HousPropertyInformationServiceImpl extends BaseServiceImpl implements HousPropertyInformationService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousPropertyInformationServiceImpl.class);
    /**
	 * 房产信息dao层
	 */
    @Autowired
    private HousPropertyInformationDao housPropertyInformationDao;

	/**
	 * 房产信息表,插入数据
	 * @param collateral 房产信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousPropertyInformation housPropertyInformation) throws Exception {
		try {
			return housPropertyInformationDao.insert(housPropertyInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 房产信息表,修改数据
	* @param collateral 房产信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousPropertyInformation housPropertyInformation) throws Exception {
		try {
			return housPropertyInformationDao.update(housPropertyInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 房产信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousPropertyInformation> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housPropertyInformationDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 房产信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousPropertyInformation getItemInfoById(long id) throws Exception {
		try {
			return housPropertyInformationDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 房产信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousPropertyInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housPropertyInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 房产信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housPropertyInformationDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housPropertyInformationDao;
	}

	@Override
	public HousPropertyInformation getItemInfoByConsultId(Long consultId) {
		return housPropertyInformationDao.getItemInfoByConsultId(consultId);
	}

	@Override
	public String getHousAddress(Long propertyAddressId) {
		return housPropertyInformationDao.getHousAddress(propertyAddressId);
	}
}