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
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PubProcessBranching;
import com.company.common.service.PubProcessBranchingService;

/**
* User:    wulb
* DateTime:2016-08-10 05:46:59
* details: 贷后信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubProcessBranchingServiceImpl")
public class PubProcessBranchingServiceImpl extends BaseServiceImpl implements PubProcessBranchingService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubProcessBranchingServiceImpl.class);
    /**
	 * 贷后信息dao层
	 */
    @Autowired
    private PubProcessBranchingDao pubProcessBranchingDao;

	/**
	 * 贷后信息表,插入数据
	 * @param collateral 贷后信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubProcessBranching pubProcessBranching) throws Exception {
		try {
			return pubProcessBranchingDao.insert(pubProcessBranching);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 贷后信息表,修改数据
	* @param collateral 贷后信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PubProcessBranching pubProcessBranching) throws Exception {
		try {
			return pubProcessBranchingDao.update(pubProcessBranching);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 贷后信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubProcessBranching> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubProcessBranchingDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 贷后信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubProcessBranching getItemInfoById(long id) throws Exception {
		try {
			return pubProcessBranchingDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 贷后信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubProcessBranching getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubProcessBranchingDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 贷后信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return pubProcessBranchingDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubProcessBranchingDao;
	}
}