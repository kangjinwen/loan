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
import com.company.modules.warrant.dao.HousDataListDao;
import com.company.modules.warrant.domain.HousDataList;
import com.company.modules.warrant.service.HousDataListService;

/**
* User:    fzc
* DateTime:2016-08-10 05:14:20
* details: 资料清单表(权证下户),Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housDataListServiceImpl")
public class HousDataListServiceImpl extends BaseServiceImpl implements HousDataListService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousDataListServiceImpl.class);
    /**
	 * 资料清单表(权证下户)dao层
	 */
    @Autowired
    private HousDataListDao housDataListDao;

	/**
	 * 资料清单表(权证下户)表,插入数据
	 * @param collateral 资料清单表(权证下户)类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousDataList housDataList) throws Exception {
		try {
			return housDataListDao.insert(housDataList);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 资料清单表(权证下户)表,修改数据
	* @param collateral 资料清单表(权证下户)类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousDataList housDataList) throws Exception {
		try {
			return housDataListDao.update(housDataList);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 资料清单表(权证下户)表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousDataList> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housDataListDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 资料清单表(权证下户)表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousDataList getItemInfoById(long id) throws Exception {
		try {
			return housDataListDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 资料清单表(权证下户)表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousDataList getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housDataListDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 资料清单表(权证下户)表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housDataListDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housDataListDao;
	}
}