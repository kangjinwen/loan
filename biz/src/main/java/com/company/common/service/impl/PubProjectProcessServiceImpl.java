package com.company.common.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.PubProjectProcessService;

/**
* User:    wulb
* DateTime:2016-08-06 12:02:17
* details: 项目流程关联,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubProjectProcessServiceImpl")
public class PubProjectProcessServiceImpl extends BaseServiceImpl implements PubProjectProcessService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubProjectProcessServiceImpl.class);
    /**
	 * 项目流程关联dao层
	 */
    @Autowired
    private PubProjectProcessDao pubProjectProcessDao;

	/**
	 * 项目流程关联表,插入数据
	 * @param collateral 项目流程关联类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubProjectProcess pubProjectProcess) throws Exception {
		try {
			return pubProjectProcessDao.insert(pubProjectProcess);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 项目流程关联表,修改数据
	* @param collateral 项目流程关联类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PubProjectProcess pubProjectProcess) throws Exception {
		try {
			return pubProjectProcessDao.update(pubProjectProcess);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 项目流程关联表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubProjectProcess> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubProjectProcessDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 项目流程关联表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubProjectProcess getItemInfoById(long id) throws Exception {
		try {
			return pubProjectProcessDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 项目流程关联表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubProjectProcess getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubProjectProcessDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 项目流程关联表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return pubProjectProcessDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubProjectProcessDao;
	}
}