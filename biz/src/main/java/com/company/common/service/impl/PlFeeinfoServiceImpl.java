package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.PlFeeinfoService;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.exception.ServiceException;

/**
* User:    wulb
* DateTime:2016-08-18 17:01:44
* details: 费用信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "plFeeinfoServiceImpl")
public class PlFeeinfoServiceImpl extends BaseServiceImpl implements PlFeeinfoService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlFeeinfoServiceImpl.class);
    /**
	 * 费用信息dao层
	 */
    @Autowired
    private PlFeeinfoDao plFeeinfoDao;

	/**
	 * 费用信息表,插入数据
	 * @param collateral 费用信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PlFeeinfo plFeeinfo) throws Exception {
		try {
			return plFeeinfoDao.insert(plFeeinfo);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 费用信息表,修改数据
	* @param collateral 费用信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlFeeinfo plFeeinfo) throws Exception {
		try {
			return plFeeinfoDao.update(plFeeinfo);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 费用信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PlFeeinfo> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plFeeinfoDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 费用信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlFeeinfo getItemInfoById(long id) throws Exception {
		try {
			return plFeeinfoDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 费用信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlFeeinfo getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plFeeinfoDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 费用信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plFeeinfoDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return plFeeinfoDao;
	}

	@Override
	public PlFeeinfo getItemInfoByConsultId(Long consultId) {
		return plFeeinfoDao.getItemInfoByConsultId(consultId);
	}
}