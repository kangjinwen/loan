package com.company.modules.instance.service.impl;

import java.util.Date;
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
import com.company.modules.instance.dao.HousPersonTypeDao;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.service.HousPersonTypeService;

/**
* User:    wulb
* DateTime:2016-11-15 13:47:18
* details: 新增人员类型,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housPersonTypeServiceImpl")
public class HousPersonTypeServiceImpl extends BaseServiceImpl implements HousPersonTypeService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousPersonTypeServiceImpl.class);
    /**
	 * 新增人员类型dao层
	 */
    @Autowired
    private HousPersonTypeDao housPersonTypeDao;

	/**
	 * 新增人员类型表,插入数据
	 * @param collateral 新增人员类型类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousPersonType housPersonType) throws Exception {
		try {
			return housPersonTypeDao.insert(housPersonType);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 新增人员类型表,修改数据
	* @param collateral 新增人员类型类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousPersonType housPersonType) throws Exception {
		try {
			return housPersonTypeDao.update(housPersonType);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 新增人员类型表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousPersonType> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housPersonTypeDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 新增人员类型表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousPersonType getItemInfoById(long id) throws Exception {
		try {
			return housPersonTypeDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 新增人员类型表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousPersonType> getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housPersonTypeDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 新增人员类型表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housPersonTypeDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housPersonTypeDao;
	}

	@Override
	public void insertOrupdate(HousPersonType housPersonTypeInfo) throws Exception {
		if(housPersonTypeInfo.getId()!=null){
			HousPersonType housPersonType = housPersonTypeDao.getItemInfoById(housPersonTypeInfo.getId());
			housPersonTypeInfo.setId(housPersonType.getId());
			housPersonTypeInfo.setModifyTime(new Date());
			housPersonTypeDao.update(housPersonTypeInfo);
		}else{			
			housPersonTypeInfo.setCreateTime(new Date());
			housPersonTypeDao.insert(housPersonTypeInfo);
		}
	}

	@Override
	public List<HousPersonType> getItemInfoByConsultId(Long consultId) {
		return housPersonTypeDao.getItemInfoByConsultId(consultId);
	}
}