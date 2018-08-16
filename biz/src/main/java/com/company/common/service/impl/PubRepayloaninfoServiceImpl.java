package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.service.PubRepayloaninfoService;
import com.company.modules.common.exception.ServiceException;

/**
* User:    fzc
* DateTime:2016-08-28 11:07:59
* details: 放款,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubRepayloaninfoServiceImpl")
public class PubRepayloaninfoServiceImpl extends BaseServiceImpl implements PubRepayloaninfoService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubRepayloaninfoServiceImpl.class);
    /**
	 * 放款dao层
	 */
    @Autowired
    private PubRepayloaninfoDao pubRepayloaninfoDao;

	/**
	 * 放款表,插入数据
	 * @param collateral 放款类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubRepayloaninfo pubRepayloaninfo) throws Exception {
		try {
			return pubRepayloaninfoDao.insert(pubRepayloaninfo);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 放款表,修改数据
	* @param collateral 放款类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PubRepayloaninfo pubRepayloaninfo) throws Exception {
		try {
			return pubRepayloaninfoDao.update(pubRepayloaninfo);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PubRepayloaninfo> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubRepayloaninfoDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubRepayloaninfo getItemInfoById(long id) throws Exception {
		try {
			return pubRepayloaninfoDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PubRepayloaninfo getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubRepayloaninfoDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 放款表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return pubRepayloaninfoDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubRepayloaninfoDao;
	}
}