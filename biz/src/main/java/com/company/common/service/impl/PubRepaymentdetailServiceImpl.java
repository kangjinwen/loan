package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PubRepaymentdetail;
import com.company.common.service.PubRepaymentdetailService;
import com.company.modules.common.exception.ServiceException;

/**
* User:    fzc
* DateTime:2016-08-28 10:30:16
* details: 放款,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubRepaymentdetailServiceImpl")
public class PubRepaymentdetailServiceImpl extends BaseServiceImpl implements PubRepaymentdetailService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubRepaymentdetailServiceImpl.class);
    /**
	 * 放款dao层
	 */
    @Autowired
    private PubRepaymentdetailDao pubRepaymentdetailDao;

	/**
	 * 放款表,插入数据
	 * @param collateral 放款类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubRepaymentdetail pubRepaymentdetail) throws Exception {
		try {
			return pubRepaymentdetailDao.insert(pubRepaymentdetail);
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
	public long update(PubRepaymentdetail pubRepaymentdetail) throws Exception {
		try {
			return pubRepaymentdetailDao.update(pubRepaymentdetail);
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
	public List<PubRepaymentdetail> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubRepaymentdetailDao.getPageListByMap(data);
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
	public PubRepaymentdetail getItemInfoById(long id) throws Exception {
		try {
			return pubRepaymentdetailDao.getItemInfoById(id);
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
	public PubRepaymentdetail getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubRepaymentdetailDao.getItemInfoByProcessInstanceId(processInstanceId);
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
			return pubRepaymentdetailDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubRepaymentdetailDao;
	}
}