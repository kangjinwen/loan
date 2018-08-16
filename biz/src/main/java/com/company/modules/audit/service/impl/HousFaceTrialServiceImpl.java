package com.company.modules.audit.service.impl;

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
import com.company.modules.audit.dao.HousFaceTrialDao;
import com.company.modules.audit.domain.HousFaceTrial;
import com.company.modules.audit.service.HousFaceTrialService;

/**
* User:    fzc
* DateTime:2016-08-14 01:28:09
* details: 面审信息表,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housFaceTrialServiceImpl")
public class HousFaceTrialServiceImpl extends BaseServiceImpl implements HousFaceTrialService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousFaceTrialServiceImpl.class);
    /**
	 * 面审信息表dao层
	 */
    @Autowired
    private HousFaceTrialDao housFaceTrialDao;

	/**
	 * 面审信息表表,插入数据
	 * @param collateral 面审信息表类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousFaceTrial housFaceTrial) throws Exception {
		try {
			return housFaceTrialDao.insert(housFaceTrial);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 面审信息表表,修改数据
	* @param collateral 面审信息表类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousFaceTrial housFaceTrial) throws Exception {
		try {
			return housFaceTrialDao.update(housFaceTrial);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 面审信息表表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousFaceTrial> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housFaceTrialDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 面审信息表表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousFaceTrial getItemInfoById(long id) throws Exception {
		try {
			return housFaceTrialDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 面审信息表表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousFaceTrial getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housFaceTrialDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 面审信息表表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housFaceTrialDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return housFaceTrialDao;
	}
}