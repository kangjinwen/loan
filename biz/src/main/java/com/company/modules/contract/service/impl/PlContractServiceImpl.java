package com.company.modules.contract.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.PlBorrowService;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.common.utils.DateUtil;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.contract.dao.PlContractDao;
import com.company.modules.contract.domain.PlBankcard;
import com.company.modules.contract.domain.PlContract;
import com.company.modules.contract.service.PlBankcardService;
import com.company.modules.contract.service.PlContractService;

/**
* User:    wulb
* DateTime:2016-08-15 11:21:07
* details: 合同信息,Service实现层
* source:  代码生成器
*/
@SuppressWarnings("rawtypes")
@Service(value = "plContractServiceImpl")
public class PlContractServiceImpl extends BaseServiceImpl implements PlContractService {
	/**
	 * 日志操作
	 */
    private static final Logger log = LoggerFactory.getLogger(PlContractServiceImpl.class);
    /**
	 * 合同信息dao层
	 */
    @Autowired
    private PlContractDao plContractDao;
    @Autowired
    private PlBankcardService plBankcardService;
    @Autowired
    private PlBorrowService plBorrowService;

	/**
	 * 合同信息表,插入数据
	 * @param collateral 合同信息类
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public long insert(PlContract plContract) throws Exception {
		try {
			return plContractDao.insert(plContract);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 合同信息表,修改数据
	* @param collateral 合同信息类
	* @return           返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlContract plContract) throws Exception {
		try {
			return plContractDao.update(plContract);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 合同信息表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<PlContract> getPageListByMap(Map<String , Object> data) throws Exception {
		try {
			return plContractDao.getPageListByMap(data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 合同信息表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlContract getItemInfoById(long id) throws Exception {
		try {
			return plContractDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 合同信息表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlContract getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plContractDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 合同信息表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plContractDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	@Override
	public BaseDao getMapper() {
		return plContractDao;
	}
	
	@Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean addPlContract(PlContract plContract, Map<String, Object> param) throws Exception {
    	log.info("####################合同数据处理开始#######################");
        boolean isret=false;      
        try {
        	//更新每月还款日        	
        	updateRepaymentDay(param);    	
            String processInstanceId = String.valueOf(param.get("processInstanceId"));
            PlContract plContractInfo = plContractDao.getItemInfoByProcessInstanceId(processInstanceId);
            if(plContractInfo == null){
            	plContract.setStatus(Byte.valueOf("0"));
            }
            //插入合同信息
            long contractid = plContractDao.insert(plContract);
            //客户银行卡信息
            PlBankcard bankcard = new PlBankcard();
            bankcard.setBankCardId(String.valueOf(param.get("bankCardId")));
            bankcard.setBankArea(String.valueOf(param.get("bankArea")));
            bankcard.setBankFlag(Byte.valueOf(String.valueOf(param.get("bankFlag"))));//银行唯一标识
            bankcard.setProcessInstanceId(processInstanceId);
            bankcard.setStatus((byte)0);
            bankcard.setAccountName(String.valueOf(param.get("accountName")));
            long bankid = plBankcardService.insert(bankcard);
        	if(bankid > 0 && contractid > 0){
        		isret = true;
        	}
        	log.info("####################合同数据处理完成#######################");
        } catch (Exception e) {
        	log.error("#############合同数据异常############", e);
            throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
        }
        return isret;
    }
    
    /**
     * 更新每月还款日
     * @param param 
     * @version 1.0
     * @author 吴国成
     * @throws Exception 
     * @created 2015年3月20日
     */
	@SuppressWarnings("deprecation")
	private void updateRepaymentDay(Map<String, Object> param) throws Exception {
		String signaturedateStr = (String) param.get("signatureTime");
		Date signaturedateDate = DateUtil.getDate(signaturedateStr,DateUtil.DATE_PATTERN);
		Date afterMonth = DateUtil.rollMon(signaturedateDate,Integer.parseInt(param.get("timeLimit").toString()));
		String yesterDay = DateUtil.yesteday(afterMonth, 1);
		Date yesterDate = DateUtil.getDate(yesterDay, DateUtil.DATE_PATTERN);
		Map<String,Object> bm = new HashMap<String, Object>();
		bm.put("id", param.get("id"));
		bm.put("repaymentDay", yesterDate.getDate());
		plBorrowService.updatePlBorrowById(bm);
	}
	
	@Override
	@SuppressWarnings("unchecked")
    @Transactional(rollbackFor=Exception.class)
    public Boolean updatePlContractById(Map<String , Object> map) throws Exception{
        log.info("####################合同数据更新处理开始#######################");
        try {
            Map<String, Object> m = (Map<String, Object>) map.get("paramap");
            updateRepaymentDay(m);//更新每月还款日
            //合同表更新
            Map<String, Object> contractMap=new HashMap<String, Object>();
            contractMap.put("qdhtdata", map.get("qdhtdata"));
            contractMap.put("modifyTime", new Date());
            contractMap.put("signatureTime", m.get("signatureTime"));
            contractMap.put("borrowStart", m.get("borrowStart"));
            contractMap.put("borrowEnd", m.get("borrowEnd"));
            contractMap.put("modifier", m.get("modifier"));
            contractMap.put("signatureAddress", m.get("signatureAddress"));
            contractMap.put("consultId", m.get("consultId"));
            contractMap.put("processInstanceId", m.get("processInstanceId"));
            contractMap.put("contractAccount", m.get("contractAccount"));
            plContractDao.updateByMap(contractMap);  //更新合同表合同相关信息
        	//银行表更新
            Map<String, Object> bankMap=new HashMap<String, Object>();
            bankMap.put("bankCardId", m.get("bankCardId"));
            bankMap.put("bankArea", m.get("bankArea"));
            bankMap.put("bankFlag", m.get("bankFlag"));
            bankMap.put("accountName", m.get("accountName"));
            bankMap.put("processInstanceId", m.get("processInstanceId"));
            plBankcardService.updateByMap(bankMap); //更新客户银行卡信息
            log.info("####################合同数据更新处理完成#######################");
        } catch (Exception e) {
        	log.info("####################合同数据处理异常#######################");
            e.printStackTrace();
            throw new ServiceException(Constant.OTHER_CODE_VALUE, e.getMessage());
        }
        return true;
    }
}