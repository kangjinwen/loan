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
import com.company.modules.audit.dao.HousMarriageInformationDao;
import com.company.modules.audit.domain.HousMarriageInformation;
import com.company.modules.audit.service.HousMarriageInformationService;

/**
* User:    fzc
* DateTime:2016-08-14 01:28:36
* details: 婚姻信息表(面审),Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housMarriageInformationServiceImpl")
public class HousMarriageInformationServiceImpl extends BaseServiceImpl implements HousMarriageInformationService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousMarriageInformationServiceImpl.class);
    /**
	 * 婚姻信息表(面审)dao层
	 */
    @Autowired
    private HousMarriageInformationDao housMarriageInformationDao;

	/**
	 * 婚姻信息表(面审)表,插入数据
	 * @param collateral 婚姻信息表(面审)类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousMarriageInformation housMarriageInformation) throws Exception {
		try {
			return housMarriageInformationDao.insert(housMarriageInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 婚姻信息表(面审)表,修改数据
	* @param collateral 婚姻信息表(面审)类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousMarriageInformation housMarriageInformation) throws Exception {
		try {
			return housMarriageInformationDao.update(housMarriageInformation);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 婚姻信息表(面审)表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousMarriageInformation> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housMarriageInformationDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 婚姻信息表(面审)表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousMarriageInformation getItemInfoById(long id) throws Exception {
		try {
			return housMarriageInformationDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 婚姻信息表(面审)表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousMarriageInformation getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housMarriageInformationDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 婚姻信息表(面审)表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housMarriageInformationDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housMarriageInformationDao;
	}
}