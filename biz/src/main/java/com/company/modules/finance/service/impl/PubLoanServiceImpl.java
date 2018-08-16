package com.company.modules.finance.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.domain.PubLoan;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.finance.dao.PubLoanDao;
import com.company.modules.finance.service.PubLoanService;

/**
* User:    fzc
* DateTime:2016-08-26 05:51:23
* details: 放款,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "pubLoanServiceImpl")
public class PubLoanServiceImpl extends BaseServiceImpl implements PubLoanService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PubLoanServiceImpl.class);
    /**
	 * 放款dao层
	 */
    @Autowired
    private PubLoanDao pubLoanDao;

	/**
	 * 放款表,插入数据
	 * @param collateral 放款类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PubLoan pubLoan) throws Exception {
		try {
			return pubLoanDao.insert(pubLoan);
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
	public long update(PubLoan pubLoan) throws Exception {
		try {
			return pubLoanDao.update(pubLoan);
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
	public List<PubLoan> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return pubLoanDao.getPageListByMap(data);
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
	public PubLoan getItemInfoById(long id) throws Exception {
		try {
			return pubLoanDao.getItemInfoById(id);
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
	public PubLoan getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return pubLoanDao.getItemInfoByProcessInstanceId(processInstanceId);
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
			return pubLoanDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return pubLoanDao;
	}
}