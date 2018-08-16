package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PubProjectDao;
import com.company.common.domain.PubProject;
import com.company.common.service.PubProjectService;
import com.company.modules.common.exception.ServiceException;

/**
* User:    wulb
* DateTime:2016-08-06 11:39:36
* details: 项目,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubProjectServiceImpl")
public class PubProjectServiceImpl extends BaseServiceImpl implements PubProjectService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubProjectServiceImpl.class);
    /**
	 * 项目dao层
	 */
    @Autowired
    private PubProjectDao pubProjectDao;

	/**
	 * 项目表,插入数据
	 * @param collateral 项目类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubProject pubProject) throws Exception {
		try {
			return pubProjectDao.insert(pubProject);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 项目表,修改数据
	* @param collateral 项目类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PubProject pubProject) throws Exception {
		try {
			return pubProjectDao.update(pubProject);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 项目表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubProject getItemInfoById(long id) throws Exception {
		try {
			return pubProjectDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 项目表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubProject getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubProjectDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	/**
	 * 项目表,分页查询数据
	 * @param pubProject 项目类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubProject> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubProjectDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 获取项目代码最大值
	 */
	@Override
	public Map<String, Object> getMaxProjectCode(String projectCode) throws Exception {
		try {
			return pubProjectDao.getMaxProjectCode(projectCode);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 项目表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return pubProjectDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubProjectDao;
	}
}