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
import com.company.modules.instance.dao.HousEnquiryInstitutionDao;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.service.HousEnquiryInstitutionService;

/**
* User:    wulb
* DateTime:2016-08-12 01:50:43
* details: 查询机构信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housEnquiryInstitutionServiceImpl")
public class HousEnquiryInstitutionServiceImpl extends BaseServiceImpl implements HousEnquiryInstitutionService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousEnquiryInstitutionServiceImpl.class);
    /**
	 * 查询机构信息dao层
	 */
    @Autowired
    private HousEnquiryInstitutionDao housEnquiryInstitutionDao;

	/**
	 * 查询机构信息表,插入数据
	 * @param collateral 查询机构信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousEnquiryInstitution housEnquiryInstitution) throws Exception {
		try {
			return housEnquiryInstitutionDao.insert(housEnquiryInstitution);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 查询机构信息表,修改数据
	* @param collateral 查询机构信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousEnquiryInstitution housEnquiryInstitution) throws Exception {
		try {
			return housEnquiryInstitutionDao.update(housEnquiryInstitution);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 查询机构信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousEnquiryInstitution> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housEnquiryInstitutionDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 查询机构信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousEnquiryInstitution getItemInfoById(long id) throws Exception {
		try {
			return housEnquiryInstitutionDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 查询机构信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousEnquiryInstitution> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housEnquiryInstitutionDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 查询机构信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housEnquiryInstitutionDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housEnquiryInstitutionDao;
	}

	@Override
	public List<HousEnquiryInstitution> getItemInfoByConsultId(Long consultId) {
		return housEnquiryInstitutionDao.getItemInfoByConsultId(consultId);
	}
}