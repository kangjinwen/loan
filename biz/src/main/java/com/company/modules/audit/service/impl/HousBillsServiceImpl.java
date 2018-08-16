package com.company.modules.audit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.audit.dao.HousBillsDao;
import com.company.modules.audit.domain.HousBills;
import com.company.modules.audit.service.HousBillsService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.RMB;

/**
* User:    fzc
* DateTime:2016-08-17 03:46:40
* details: 放款单/打款单,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "housBillsServiceImpl")
public class HousBillsServiceImpl extends BaseServiceImpl implements HousBillsService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(HousBillsServiceImpl.class);
    /**
	 * 放款单/打款单dao层
	 */
    @Autowired
    private HousBillsDao housBillsDao;

	/**
	 * 放款单/打款单表,插入数据
	 * @param collateral 放款单/打款单类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(HousBills housBills) throws Exception {
		try {
			return housBillsDao.insert(housBills);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 放款单/打款单表,修改数据
	* @param collateral 放款单/打款单类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(HousBills housBills) throws Exception {
		try {
			return housBillsDao.update(housBills);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款单/打款单表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<HousBills> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return housBillsDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款单/打款单表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousBills getItemInfoById(long id) throws Exception {
		try {
			return housBillsDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 放款单/打款单表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public HousBills getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return housBillsDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 放款单/打款单表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return housBillsDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public Map<String, Object> getHousBillBasicInfo(String processInstanceId)
			throws Exception {
		Map<String, Object> data;
		try {
			data = housBillsDao.getHousBillBasicInfo(processInstanceId);
			//data.put("firstInterest", ((BigDecimal)data.get("account")).multiply((BigDecimal)data.get("repaymentRate")));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return data;
	}
	
	@Override
	public Map<String, Object> getLoanInfo(String processInstanceId) throws Exception {
		Map<String, Object> loanInfo;
		try {
			loanInfo = housBillsDao.getLoanInfo(processInstanceId);
			loanInfo.put("BigLendAccount", RMB.toBigAmt(Double.parseDouble(((BigDecimal)loanInfo.get("lendAccount")).toString())));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return loanInfo;
	}
	
	@Override
	public long insertOrupdate(HousBills housBillsInfo,long userId,String type) throws Exception {
		long num;
		try {
			if(housBillsInfo.getId() == null){
				log.info("【放款单填写111】");
				housBillsInfo.setCreateTime(new Date());
				housBillsInfo.setCreator(userId);
				housBillsInfo.setType(type);//类型为打款或者放款
				num = housBillsDao.insert(housBillsInfo);
			}else{
				HousBills dbHousBillsInfo = housBillsDao.getItemInfoById(housBillsInfo.getId());
				if (dbHousBillsInfo == null) {
					log.info("【放款单填写222】");
					housBillsInfo.setCreateTime(new Date());
					housBillsInfo.setCreator(userId);
					housBillsInfo.setType(type);//类型为打款或者放款
					num = housBillsDao.insert(housBillsInfo);
				} else {
					if (dbHousBillsInfo.getProcessInstanceId().equals(housBillsInfo.getProcessInstanceId())) {	//（特殊处理）加一个判断，否则可能会跟hous_bills表id重复，导致直接走update方法，而没有保存到数据库					
						log.info("【放款单填写333】");
						housBillsInfo.setModifier(userId);
						housBillsInfo.setModifyTime(new Date());
						num = housBillsDao.update(housBillsInfo);
					}else{
						log.info("【放款单填写444】");
						housBillsInfo.setCreateTime(new Date());
						housBillsInfo.setCreator(userId);
						housBillsInfo.setType(type);//类型为打款或者放款
						num = housBillsDao.insert(housBillsInfo);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return num;
	}

	@Override
	public BaseDao getMapper() {
		return housBillsDao;
	}
}