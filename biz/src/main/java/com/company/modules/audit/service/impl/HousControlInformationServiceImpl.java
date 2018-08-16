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
import com.company.modules.audit.dao.HousControlInformationDao;
import com.company.modules.audit.domain.HousControlInformation;
import com.company.modules.audit.service.HousControlInformationService;

/**
* User:    fzc
* DateTime:2016-08-14 01:29:02
* details: 风控信息表(面审),Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housControlInformationServiceImpl")
public class HousControlInformationServiceImpl extends BaseServiceImpl implements HousControlInformationService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousControlInformationServiceImpl.class);
    /**
	 * 风控信息表(面审)dao层
	 */
    @Autowired
    private HousControlInformationDao housControlInformationDao;

	/**
	 * 风控信息表(面审)表,插入数据
	 * @param collateral 风控信息表(面审)类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousControlInformation housControlInformation) throws Exception {
		try {
			return housControlInformationDao.insert(housControlInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 风控信息表(面审)表,修改数据
	* @param collateral 风控信息表(面审)类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousControlInformation housControlInformation) throws Exception {
		try {
			return housControlInformationDao.update(housControlInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 风控信息表(面审)表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousControlInformation> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housControlInformationDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 风控信息表(面审)表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousControlInformation getItemInfoById(long id) throws Exception {
		try {
			return housControlInformationDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 风控信息表(面审)表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousControlInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housControlInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 风控信息表(面审)表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housControlInformationDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housControlInformationDao;
	}
}