package com.company.modules.contract.service.impl;

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
import com.company.modules.contract.dao.PlBankcardDao;
import com.company.modules.contract.domain.PlBankcard;
import com.company.modules.contract.service.PlBankcardService;

/**
* User:    wulb
* DateTime:2016-08-15 11:44:43
* details: 放款银行信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "plBankcardServiceImpl")
public class PlBankcardServiceImpl extends BaseServiceImpl implements PlBankcardService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlBankcardServiceImpl.class);
    /**
	 * 放款银行信息dao层
	 */
    @Autowired
    private PlBankcardDao plBankcardDao;

	/**
	 * 放款银行信息表,插入数据
	 * @param collateral 放款银行信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PlBankcard plBankcard) throws Exception {
		try {
			return plBankcardDao.insert(plBankcard);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 放款银行信息表,修改数据
	* @param collateral 放款银行信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlBankcard plBankcard) throws Exception {
		try {
			return plBankcardDao.update(plBankcard);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款银行信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PlBankcard> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plBankcardDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款银行信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlBankcard getItemInfoById(long id) throws Exception {
		try {
			return plBankcardDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款银行信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlBankcard getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plBankcardDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 放款银行信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plBankcardDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return plBankcardDao;
	}

	@Override
	public void updateByMap(Map<String, Object> bankMap) throws Exception {
		try {
			plBankcardDao.updateByMap(bankMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
}