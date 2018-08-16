package com.company.modules.collateral.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.modules.collateral.dao.CollateralManageDao;
import com.company.modules.collateral.domain.HousMortgageRegistration;
import com.company.modules.collateral.service.CollateralManageService;

/**
* User:    mcwang
* DateTime:2016-08-08 04:18:34
* details: 押品登记表,Service实现层
*/
@Service(value = "collateralManageServiceImpl")
public class CollateralManageServiceImpl extends BaseServiceImpl implements CollateralManageService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(CollateralManageServiceImpl.class);
    /**
	 * 押品登记表dao层
	 */
    @Autowired
    private CollateralManageDao collateralManageDao;
	
	/**
	*
	*继承关系
	*/
	@Override
	public BaseDao getMapper() {
		// TODO Auto-generated method stub
		return collateralManageDao;
	}
	
	/**
	 * 押品登记表表,插入数据
	 * @param collateral 押品登记表类
	 * @return           返回页面map
	 * @throws ServiceException
	 */
	public long insert(HousMortgageRegistration housMortgageRegistration) throws Exception {
		try {
			return	collateralManageDao.insert(housMortgageRegistration);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 押品登记表表,修改数据
	* @param collateral 押品登记表类
	* @return           返回页面map
	* @throws ServiceException
	*/
	public long update(HousMortgageRegistration housMortgageRegistration) throws Exception {
		try {
			return	collateralManageDao.update(housMortgageRegistration);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public Map<String, Object> getCollateralRegistData(String instanceId) throws Exception {
		// TODO Auto-generated method stub
		try {
			return  collateralManageDao.getCollateralRegistDataByInstanceId(instanceId);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 抵押登记表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousMortgageRegistration getHousMortgageRegistrationByInstanceId(String processInstanceId) throws Exception {
		try {
			return collateralManageDao.getHousMortgageRegistrationsByInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	@Override
	public Map<String, Object> returnCollateralRegistData(String instanceId) throws Exception {
		try {
			return  collateralManageDao.returnCollateralRegistData(instanceId);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
}