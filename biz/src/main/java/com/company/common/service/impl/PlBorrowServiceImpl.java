package com.company.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.modules.common.exception.ServiceException;
import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PlBorrowRequirementDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubCustomer;
import com.company.common.domain.PubProject;
import com.company.common.service.PlBorrowService;
import com.company.common.service.PubCustomerService;
import com.company.common.service.PubProjectService;

/**
* User:    wulb
* DateTime:2016-08-15 05:42:32
* details: 借款信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "plBorrowServiceImpl")
public class PlBorrowServiceImpl extends BaseServiceImpl implements PlBorrowService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlBorrowServiceImpl.class);
    /**
	 * 借款信息dao层
	 */
    @Autowired
    private PlBorrowDao plBorrowDao;
    @Autowired
    private PlBorrowRequirementDao plBorrowRequirementDao;
    @Autowired
    private PubProjectService pubProjectService;
    @Autowired
    private PubCustomerService pubCustomerService;

	/**
	 * 借款信息表,插入数据
	 * @param collateral 借款信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PlBorrow plBorrow) throws Exception {
		try {
			return plBorrowDao.insert(plBorrow);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 借款信息表,修改数据
	* @param collateral 借款信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlBorrow plBorrow) throws Exception {
		try {
			return plBorrowDao.update(plBorrow);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款信息表,修改数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long updatePlBorrowById(Map<String, Object> data) throws Exception {
		try {
			return plBorrowDao.updatePlBorrowById(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PlBorrow> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plBorrowDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlBorrow getItemInfoById(long id) throws Exception {
		try {
			return plBorrowDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlBorrow getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款信息表,根据条件查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getItemInfoByMap(Map<String, Object> data) throws Exception {
		try {
			return plBorrowDao.getItemInfoByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 借款信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plBorrowDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public Map<String, Object> getborrowInfo(String processInstanceId) throws Exception {
		Map<String, Object> borrowInfo = new HashMap<String, Object>();
		PlBorrow borrow  = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
		PubCustomer cus = pubCustomerService.getCustomerByProcessInstanceId(processInstanceId);
		Long projectId = null;
		if(borrow == null){
			Map<String, Object> borrowMap  = plBorrowRequirementDao.getItemInfoByProcessInstanceId(processInstanceId);
			borrowInfo.put("account", borrowMap.get("account"));
			borrowInfo.put("timeLimit", borrowMap.get("timeLimit"));
			borrowInfo.put("singleRate", borrowMap.get("singleRate"));
			borrowInfo.put("repaymentRate", borrowMap.get("repaymentRate"));
			borrowInfo.put("id", borrowMap.get("id"));
			projectId = Long.parseLong(String.valueOf(borrowMap.get("projectId")));
		}else{
			borrowInfo.put("account", borrow.getAccount());
			borrowInfo.put("timeLimit", borrow.getTimeLimit());
			borrowInfo.put("singleRate", borrow.getSingleRate());
			borrowInfo.put("repaymentRate", borrow.getRepaymentRate());
			borrowInfo.put("id", borrow.getId());
			projectId = borrow.getProjectId();
		}
		PubProject proj = pubProjectService.getItemInfoById(projectId);
		borrowInfo.put("projectCode", proj.getCode());
		borrowInfo.put("customerName",cus.getName());
		return borrowInfo;
	}

	@Override
	public BaseDao getMapper() {
		return plBorrowDao;
	}
}