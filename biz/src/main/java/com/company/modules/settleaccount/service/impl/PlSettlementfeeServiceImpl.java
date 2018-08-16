package com.company.modules.settleaccount.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PageBean;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProject;
import com.company.common.domain.PubProjectProcess;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.common.utils.ParamChecker;
import com.company.modules.chargedata.dao.ChargeDataStoreDao;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.collateral.workflow.service.CollateralTaskService;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.RMB;
import com.company.modules.contract.dao.PlBankcardDao;
import com.company.modules.repayment.dao.PubRepaymentDao;
import com.company.modules.settleaccount.dao.PlSettlementfeeDao;
import com.company.modules.settleaccount.domain.PlSettlementfee;
import com.company.modules.settleaccount.service.PlSettlementfeeService;

/**
 * 结算费用表Service
 * 
 * @author wgc
 * @version 1.0
 * @since 2015-06-30
 */
@SuppressWarnings("all")
@Service(value = "plSettlementfeeServiceImpl")
public class PlSettlementfeeServiceImpl extends BaseServiceImpl<PlSettlementfee,Long> implements PlSettlementfeeService {
    private static final Logger log = LoggerFactory.getLogger(PlSettlementfeeServiceImpl.class);
    @Autowired
    private PlBorrowDao plBorrowDao;
    @Autowired
    private PubProjectDao pubProjectDao;
    @Autowired
    private PlSettlementfeeDao plSettlementfeeDao;
    @Autowired
    private PubRepayloaninfoDao pubRepayloaninfoDao;
    @Autowired
    private PlBankcardDao plBankcardDao;
    @Autowired
    private PubRepaymentdetailDao repaymentdetailDao;
    @Autowired
    private PlProductDao plProductDao;
    @Autowired
    private PubProjectProcessDao pubProjectProcessDao;
    @Autowired
    private PlFeeinfoDao plFeeinfoDao;
    @Autowired
    private PubProcessBranchingDao pubProcessBranchingDao;
    @Autowired
    private PubRepaymentDao pubRepaymentDao;
    @Autowired
    private ChargeDataStoreDao chargeDataStoreDao;
    
    private static final String ELOAN_CAR_LOANSETTLEMENT = "eloan_car_loanSettlement";
    @Autowired
	private RuntimeService runtimeService;

    @Autowired
	private CollateralTaskService collateralTaskService;
    
    public PlSettlementfee getPlSettlementfeeById(Long id) throws ServiceException {
        return getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean plSettlementfeeUpdate(PlSettlementfee plSettlementfee) throws ServiceException {
        return updateById(plSettlementfee) > 0 ? true:false;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePlSettlementfee(Long id) throws ServiceException {
        return deleteByID(id) > 0 ? true:false;
    }

    
	private void setFilterParams(PageBean pageBean, Map<String, Object> params) {
		// 额外的查询参数
		if(pageBean.getArg() != null) {
			params.putAll(pageBean.getArg());
		}
	}

    @Override
    public Map<String, Object> settleAccountsDetail(String processInstanceId) throws ServiceException {
        try {
            ParamChecker.checkEmpty(processInstanceId, "processInstanceId");
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        Map<String, Object> result = null;
        boolean exist = false;
        try {
            result = plSettlementfeeDao.findSettileAccountMapByProcessInstanceId(processInstanceId);
            if (result != null) {
                exist = true;
            }else {
            	result = new HashMap<String, Object>();
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("processInstanceId", processInstanceId);
            PlBorrow borrow = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
    		ParamChecker.checkEmpty(borrow, "借款信息");
    		
    		params.clear();
            params.put("borrowId", borrow.getId());
            PubRepayloaninfo repayinfo = pubRepayloaninfoDao.getItemInfoByBorrowId(borrow.getId());
           
            ParamChecker.checkEmpty(repayinfo, "还款信息");
            PubProject project = pubProjectDao.getItemInfoById(repayinfo.getProjectid());
            
            params.clear();
            params.put("processInstanceId",processInstanceId);
            if (exist) {
                BigDecimal capital = (BigDecimal) result.get("capital");
                capital = capital.setScale(2, BigDecimal.ROUND_CEILING);
                result.put("capitalBigAmt", RMB.toBigAmt(capital.doubleValue()));
            }
            result.put("processInstanceId", processInstanceId);
            // 项目id
            result.put("projectId", project.getId());
            // 项目编码
            result.put("projectCode", project.getCode());
            
            ParamChecker.checkEmpty(repayinfo.getCustomerName(), "客户名称");
            // 客户名称
            result.put("customerName", repayinfo.getCustomerName());
            // 合同编号
            result.put("contractNo", repayinfo.getContractNo());
            // 借款本金
            result.put("capital", repayinfo.getCapitalAmount());            
            // 本金大写
            BigDecimal capital = repayinfo.getCapitalAmount().setScale(2, BigDecimal.ROUND_CEILING);
            result.put("capitalChinese", RMB.toBigAmt(capital.doubleValue()));
            
//            // 数据库中还款状态 '还款中0，结清1，逾期2' 
//            byte repaymentStatus = (byte) (repayinfo.getRepaymentStatus()==new Byte("2")?1:0);//逾期返回1，否则0
//            result.put("repaymentStatus", repaymentStatus);
//            //数据库中'还款处理（0正常 1提前 2减免 3强制 5处置）'
//            byte aheadRepaymentProcess = (byte) (repayinfo.getRepaymentStatus()==new Byte("1")?1:0);//提前还款返回1，否则0
//            result.put("repaymentProcess", aheadRepaymentProcess);

            // 是否还清
            result.put("isPayoff", repayinfo.getRepaymentStatus());
            result.put("isPayoffText", repayinfo.getRepaymentStatus() == 1 ? "是" : "否");
            result.put("isOverdueText", repayinfo.getRepaymentStatus() == 2 ? "是" : "否");
        } catch (PersistentDataException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("数据库操作失败");
        }
        return result;
    }
	
	
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateSettleAccountsDetail(Map<String, Object> data) throws ServiceException {
        
		String processInstanceId = data.get("processInstanceId").toString();
		Map<String, Object> baseInfo = settleAccountsDetail(processInstanceId);
		// 客户名称
		String customerName = baseInfo.get("customerName").toString();
		// 合同编号
		String contractNo = baseInfo.get("contractNo").toString();
		// 本金
		BigDecimal capital = new BigDecimal(baseInfo.get("capital").toString());
		data.put("customerName", customerName);
        data.put("contractNo", contractNo);
        data.put("capital", capital);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("processInstanceId", processInstanceId);
        Map<String, Object> originalSettleAccount;
        PlBorrow borrow = null;
        String assignee=null;
        try {
            PubProjectProcess ppp = pubProjectProcessDao.getItemByProcessInstanceId(Long.parseLong(processInstanceId));
            borrow = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
            originalSettleAccount = plSettlementfeeDao.findSettileAccountMapByProcessInstanceId(processInstanceId);
            data.put("projectId", ppp.getProjectId());
            if (originalSettleAccount == null) {
                plSettlementfeeDao.savePlSettlementfee(data);
            } else {
                originalSettleAccount.putAll(data);
                plSettlementfeeDao.updatePlSettlementfeeById(originalSettleAccount);
            }
            assignee= plSettlementfeeDao.getDocumentApplicationAssignee(((Long)data.get("projectId")).intValue());//找到启动申请进件的用户
            
            ChargeDataStore chargeDataStore = new ChargeDataStore();
            chargeDataStore.setProcessInstanceId(processInstanceId);
            chargeDataStore.setChargeStatus(ChargeDataStore.CHARGESTATUS_WAIT_OUT_STORE);
            chargeDataStoreDao.update(chargeDataStore);
        } catch (PersistentDataException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("数据库操作失败");
        }
        
       
        // TODO 启动解压任务分配流程
        /*Map<String, Object> variablesMap = new HashMap<String, Object>();
        variablesMap.put(SystemConstant.PROCESS_LAUNCHER, assignee);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("", variablesMap);//启动结算流程
        
        PubProjectProcess pubProjectProcess = new PubProjectProcess();
        pubProjectProcess.setExtensionNumber((byte) 0);
        pubProjectProcess.setProcessType((byte) 5); //结算流程
        pubProjectProcess.setProjectId((Long) data.get("projectId"));
        pubProjectProcess.setProcessInstanceId(processInstance.getProcessInstanceId());
        pubProjectProcessDao.insert(pubProjectProcess);*/

        try {
        	long projectId=(Long)data.get("projectId");
        	long userid=(Long)data.get("userid");
			collateralTaskService.startRelieveHouse(processInstanceId,projectId,userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new ServiceException("启动解押流程失败");
		}
    }
    
    
    

    
    


	/**
	 * 结算管理表,插入数据
	 * @param collateral 结算管理类
	 * @return           返回页面map
	 * @throws ServiceException 
	 */
	@Override
	public long insertPlSettlementfee(PlSettlementfee plSettlementfee) throws ServiceException {
		try {
			return plSettlementfeeDao.insert(plSettlementfee);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}

	/**
	* 结算管理表,修改数据
	* @param collateral 结算管理类
	* @return 返回页面map
	* @throws Exception
	*/
	@Override
	public long update(PlSettlementfee plSettlementfee) throws Exception {
		try {
			return plSettlementfeeDao.update(plSettlementfee);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 结算管理表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public List<Map> getPageListByMap(Map<String , Object> data,Boolean isCompleted) throws Exception {
		try {
			if (isCompleted) {
				return plSettlementfeeDao.getCompletedPageListByMap(data);
			}else {
				return plSettlementfeeDao.getUnCompletePageListByMap(data);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 结算管理表,根据id查询数据
	 * @param id
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlSettlementfee getItemInfoById(long id) throws Exception {
		try {
			return plSettlementfeeDao.getItemInfoById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	 * 结算管理表,根据流程id查询数据
	 * @param processInstanceId
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PlSettlementfee getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		try {
			return plSettlementfeeDao.getItemInfoByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	
	/**
	* 结算管理表,删除数据
	* @param id 主键
	* @return   返回页面map
	* @throws Exception
	*/
	@Override
	public int deleteById(long id) throws Exception {
		try {
			return plSettlementfeeDao.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
	}
	

	@Override
	public BaseDao<PlSettlementfee, Long> getMapper() {
		return plSettlementfeeDao;
	}
}