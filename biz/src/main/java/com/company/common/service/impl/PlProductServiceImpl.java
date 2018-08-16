package com.company.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.PlProductService;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.exception.ServiceException;

/**
* User:    wulb
* DateTime:2016-08-15 05:50:16
* details: 产品信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "plProductServiceImpl")
public class PlProductServiceImpl extends BaseServiceImpl implements PlProductService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlProductServiceImpl.class);
    /**
	 * 产品信息dao层
	 */
    @Autowired
    private PlProductDao plProductDao;

	/**
	 * 产品信息表,插入数据
	 * @param collateral 产品信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PlProduct plProduct) throws Exception {
		try {
			return plProductDao.insert(plProduct);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 产品信息表,修改数据
	* @param collateral 产品信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlProduct plProduct) throws Exception {
		try {
			return plProductDao.update(plProduct);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 产品信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PlProduct> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plProductDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 产品信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlProduct getItemInfoById(long id) throws Exception {
		try {
			return plProductDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 产品信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlProduct getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plProductDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 产品信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plProductDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return plProductDao;
	}
}