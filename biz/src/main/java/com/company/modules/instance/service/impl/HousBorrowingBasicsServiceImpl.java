package com.company.modules.instance.service.impl;

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
import com.company.modules.instance.dao.HousBorrowingBasicsDao;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.service.HousBorrowingBasicsService;

/**
* User:    wulb
* DateTime:2016-08-06 02:36:08
* details: 借款基本信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housBorrowingBasicsServiceImpl")
public class HousBorrowingBasicsServiceImpl extends BaseServiceImpl implements HousBorrowingBasicsService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousBorrowingBasicsServiceImpl.class);
    /**
	 * 借款基本信息dao层
	 */
    @Autowired
    private HousBorrowingBasicsDao housBorrowingBasicsDao;

	/**
	 * 借款基本信息表,插入数据
	 * @param collateral 借款基本信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousBorrowingBasics housBorrowingBasics) throws Exception {
		try {
			return housBorrowingBasicsDao.insert(housBorrowingBasics);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 借款基本信息表,修改数据
	* @param collateral 借款基本信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousBorrowingBasics housBorrowingBasics) throws Exception {
		try {
			return housBorrowingBasicsDao.update(housBorrowingBasics);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款基本信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousBorrowingBasics> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housBorrowingBasicsDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款基本信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousBorrowingBasics getItemInfoById(long id) throws Exception {
		try {
			return housBorrowingBasicsDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 借款基本信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousBorrowingBasics getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housBorrowingBasicsDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 借款基本信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housBorrowingBasicsDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housBorrowingBasicsDao;
	}

	@Override
	public HousBorrowingBasics getItemInfoByConsultId(Long consultId) {
		return housBorrowingBasicsDao.getItemInfoByConsultId(consultId);
	}
}