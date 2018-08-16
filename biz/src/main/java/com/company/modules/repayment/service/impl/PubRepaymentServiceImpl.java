package com.company.modules.repayment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.CommonConstant;
import com.company.common.context.SysDictConstant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.BaseDao;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProjectProcess;
import com.company.common.service.PubProjectProcessService;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.common.utils.DateUtil;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ObjectInitUtil;
import com.company.modules.contract.dao.PlContractDao;
import com.company.modules.contract.domain.PlContract;
import com.company.modules.fel.context.FelConstant;
import com.company.modules.fel.service.FelService;
import com.company.modules.instance.dao.HousBorrowingBasicsDao;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.rebate.workflow.service.RebateTaskService;
import com.company.modules.repayment.RepaymentVO;
import com.company.modules.repayment.dao.PubRepaymentDao;
import com.company.modules.repayment.domain.ChargeDisposalLog;
import com.company.modules.repayment.service.PubRepaymentService;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.domain.SysUser;

@Service(value="repaymentService")
@SuppressWarnings("all")
public class PubRepaymentServiceImpl extends BaseServiceImpl implements PubRepaymentService {
	
	private static Logger logger = LoggerFactory.getLogger(PubRepaymentServiceImpl.class);
	@Autowired
    private FelService felServiceImpl;
	@Autowired
	private PubRepaymentDao repaymentDao;
	@Autowired
	private PlBorrowDao borrowDao;
	@Autowired
	private HousBorrowingBasicsDao borrowingBasicsDao;
	@Autowired
	private PlContractDao contractDao;
	@Autowired
    private PubProcessBranchingDao pubProcessBranchingDao;
	@Autowired
    private PubProjectProcessService pubProjectProcessService;
	@Autowired
    private PlFeeinfoDao plFeeinfoDao;
	@Autowired
	private RebateTaskService rebateTaskService;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
    private TaskService taskService;
    @Autowired
  	private RuntimeService runtimeService;
    @Autowired
    private PubLoanprocessDao pubLoanprocessDao;
    @Autowired
	private PubProjectProcessDao pubProjectProcessDao;
	
	/** 流程id */
	protected static final String PROCESS_INSTANCE_ID = "processInstanceId";
	/** 实际还款时间 */
	protected static final String REAL_PAYMENT_TIME = "realpaymentTime";
	/** 应还款时间 */
	protected static final String REPAYMENT_TIME = "repaymentTime";
	
	/** 逾期费用 */
	protected static final String DEFAULT_INTEREST = "defaultInterest";
	/** 实际逾期费用 */
	protected static final String REAL_INTEREST = "realInterest";
	/** 逾期费用减免 */
	protected static final String BREACH_CONTRACT = "breachContract";
	
	/** 违约金 */
	protected static final String PENALTY = "penalty";
	/** 违约金减免 */
	protected static final String REDUCTION_PENALTY = "reductionPenalty";
	
	/** 本期应还金额 */
	protected static final String ACCOUNT = "account";
	/** 总金额 */
	protected static final String TOTAL_ACCOUNT = "totalAccount";
	/** 实际还款总额 */
	protected static final String REAL_ACCOUNT = "realAccount";
	
	/** 还款期次 */
	protected static final String PERIOD = "period";
	
	
	public BigDecimal getZeroBigDecimal(){
		BigDecimal temp = new BigDecimal(0);
		return temp;
	}
	
	@Override
	public BaseDao getMapper() {
		return repaymentDao;
	}
	
	@Override
	public List<Map> getPageListByMap(Map<String, Object> paramMap) throws Exception {
		paramMap.put("dictEloanType", SysDictConstant.REPAYMENT_TYPE);
		paramMap.put("dictBorrowPeroid", SysDictConstant.BORROW_PEROID);
		return repaymentDao.getPageListByMap(paramMap);
	}

	/**
	 * 还款详情分页列表
	 */
	@Override
	@SuppressWarnings("all")
	public List<Map> getRepaymentDetailPageByMap(Map<String, Object> paramMap) throws ServiceException {
		List<Map> repaymentDetailList = repaymentDao.getRepaymentDetailPageByMap(paramMap);
		RepaymentVO repayment = (RepaymentVO) paramMap.get("repayment");
		PlBorrow borrow = borrowDao.getItemInfoByProcessInstanceId(repayment.getProcessInstanceId());
		HousBorrowingBasics borrowBasic = borrowingBasicsDao
				.getItemInfoByProcessInstanceId(repayment.getProcessInstanceId());
		PlContract contract = contractDao.getItemInfoByProcessInstanceId(repayment.getProcessInstanceId());
		for (Map detail : repaymentDetailList) {
//			detail.put("contractNo", contract.getContractNo());
//			detail.put("customerName", borrowBasic.getName());
			if (((Integer) detail.get("isPayoff")) == CommonConstant.PAY_OFF_FALSE) {
				Date calculateTime = new Date();
				if (detail.get(REAL_PAYMENT_TIME) != null) {
					calculateTime = (Date) detail.get(REAL_PAYMENT_TIME);
					detail.put(REAL_PAYMENT_TIME, DateUtil.getFormatDate(calculateTime));
				} else {
					detail.put(REAL_PAYMENT_TIME, "--");
				}
				Map<String, Object> d = this.calculateRepaymentDetail(repayment.getProcessInstanceId(),borrow, detail,
						calculateTime);
				detail.put(REPAYMENT_TIME, DateUtil.getFormatDate((Date) detail.get(REPAYMENT_TIME)));
				detail.putAll(d);
			} else {
				BigDecimal totalAccount = ((BigDecimal) detail.get(ACCOUNT))
						.add((BigDecimal) detail.get(DEFAULT_INTEREST)).add((BigDecimal) detail.get(PENALTY));
				if (detail.get(REAL_PAYMENT_TIME) != null) {
					Date calculateTime = (Date) detail.get(REAL_PAYMENT_TIME);
					detail.put(REAL_PAYMENT_TIME, DateUtil.getFormatDate(calculateTime));
				}
				detail.put(REPAYMENT_TIME, DateUtil.getFormatDate((Date) detail.get(REPAYMENT_TIME)));
				detail.put(TOTAL_ACCOUNT, totalAccount);
			}
		}
		return repaymentDetailList;
	}
	
	/**
	 * 正常还款、提前还款等界面信息获取
	 */
	@Override
    public Map<String, Object> queryRepaymentDetail(Integer type, String processInstanceId, Date realPaymentDate)
            throws ServiceException {
        Map<String, Object> result = new HashMap<String, Object>();
        PlBorrow borrow = borrowDao.getItemInfoByProcessInstanceId(processInstanceId);
        // borrow验证
        ParamChecker.checkEmpty(borrow, "借款记录");
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put(PROCESS_INSTANCE_ID, processInstanceId);
        Map<String, Object> detail = repaymentDao.getMinUnPayedRepaymentdetail(searchParam);
        if (detail == null) {
            throw new ServiceException("款项已结清");
        }
        // 最早一期未还款期数
        Integer currentTimeLimit = (Integer) detail.get(PERIOD);
        searchParam.put(PROCESS_INSTANCE_ID, processInstanceId);
        if (type == CommonConstant.REPAYMENT_TYPE_NORMAL) { // 正常还款
            result = calculateRepaymentDetail(processInstanceId, borrow,null,realPaymentDate);
            result.remove(REAL_PAYMENT_TIME);
            result.put("realPaymentDate", realPaymentDate);
        } else if (type == CommonConstant.REPAYMENT_TYPE_PREPAY) { // 提前还款
            result = getPrepaymentAccount(realPaymentDate, currentTimeLimit,borrow, processInstanceId);
            // 本期应还款时间
            result.put(REPAYMENT_TIME, detail.get(REPAYMENT_TIME));
            result.put("needRepay", result.get(REAL_ACCOUNT));
        } else if (type == CommonConstant.REPAYMENT_TYPE_FORCE_SETTLE_REPAY) { // 强制结清
//            result = getForceSettlerepaymentAccount(realPaymentDate, currentTimeLimit, processInstanceId,
//            		CKRCarloanCalculateService);
//            // 本期应还款时间
//            result.put(REPAYMENT_TIME, detail.get(REPAYMENT_TIME));
//            result.put("needRepay", result.get(REAL_ACCOUNT));
        } else if (type == CommonConstant.REPAYMENT_TYPE_CHARGE_DISPOSAL) { // 押品处置
//            result = 
        }
        return result;
    }
	
	
	/**
	 * 计算还款详情
	 * @param borrow
	 * @param detail
	 * @param processInstanceId
	 * @param realPaymentDate
	 * @return
	 * @throws ServiceException
	 */
	private Map<String, Object> calculateRepaymentDetail(String processInstanceId,PlBorrow borrow, 
			Map<String, Object> detail,Date realPaymentDate) throws ServiceException {
        Map<String, Object> result = new HashMap<String, Object>();
        
        if (detail == null) {
        	Map<String, Object> searchParam = new HashMap<String, Object>();
    		searchParam.put(PROCESS_INSTANCE_ID, processInstanceId);
    		detail = repaymentDao.getMinUnPayedRepaymentdetail(searchParam);
    		result.putAll(detail);
		}
        
        // 本期应还总额
        BigDecimal totalAccount = getZeroBigDecimal();
        // 逾期费
        BigDecimal defaultInterest = getZeroBigDecimal();
        // 违约金减免
        BigDecimal reductionPenalty;
        // 逾期费减免
     	BigDecimal breachContract;
        try {
            ParamChecker.checkEmpty(detail, "未还款明细");
            ParamChecker.checkEmpty(detail.get(REPAYMENT_TIME), "还款时间");
            if (detail.get(REDUCTION_PENALTY) == null) {
            	reductionPenalty = getZeroBigDecimal();
            } else {
            	reductionPenalty = (BigDecimal) detail.get(REDUCTION_PENALTY);
            }
            if (detail.get(BREACH_CONTRACT) == null) {
				breachContract = getZeroBigDecimal();
			} else {
				breachContract = (BigDecimal) detail.get(BREACH_CONTRACT);
			}
            result.putAll(detail);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
        // 判断是否逾期
        int overdueDay = calculateOverdueDays((Date) detail.get(REPAYMENT_TIME), realPaymentDate);
        if(overdueDay > 0 ){
        	Map<String, Object> extraParam = new HashMap<String, Object>();
        	extraParam.put(FelConstant.OVERDUE_DATE, overdueDay);
        	defaultInterest = felServiceImpl.calculateUseFeeInfo(borrow.getProcessInstanceId(), borrow.getAccount(), FelConstant.PENALTY, extraParam);
        }
        totalAccount = ((BigDecimal) detail.get(ACCOUNT)).add(defaultInterest);
        result.put(DEFAULT_INTEREST, defaultInterest);
        result.put(TOTAL_ACCOUNT, totalAccount);
        result.put(REDUCTION_PENALTY, reductionPenalty);
        result.put(BREACH_CONTRACT, breachContract);
        // 本期应还总额
        result.put(ACCOUNT, (BigDecimal) detail.get(ACCOUNT));
        // 实际逾期费用
        result.put(REAL_INTEREST, defaultInterest.add(reductionPenalty.negate()));
        return result;
    }

	/**
	 * 还款提交
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void pay(Integer type, Map<String, Object> repaymentDetailVO, Long userId) throws ServiceException {
        try {
            String processInstanceId = repaymentDetailVO.get(PROCESS_INSTANCE_ID).toString();
            repaymentDetailVO.put("modifier", userId);
            repaymentDetailVO.put("modifyTime", new Date());
            PlBorrow borrow = borrowDao.getItemInfoByProcessInstanceId(processInstanceId);
            String projectId = String.valueOf(pubProjectProcessService.getItemInfoByProcessInstanceId(processInstanceId).getProjectId());
            
            Map map = new HashMap();
            map.put(PROCESS_INSTANCE_ID, processInstanceId);
            map.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
//            boolean isOverdue = false; // 是否逾期
            int overdueDay = 0;//逾期天数
            
            // 分支流程
            PubProcessBranching pubProcessBranching = pubProcessBranchingDao.getItemByMap(map);
            byte repaymentStatus = borrow.getRepaymentStatus();
            if (repaymentStatus == 1 || repaymentStatus == 3) {
                throw new ServiceException("已结清或者已展期的贷款记录不能进行还款!");
            }
            if (type == CommonConstant.REPAYMENT_TYPE_NORMAL) { // 正常还款
                // 提前申请的处置状态时，做正常还款，则改变borrow表的处置状态为正常
                if (pubProcessBranching != null) {
                    pubProcessBranching.setRepaymentProcess((byte) 0);
                    pubProcessBranchingDao.update(pubProcessBranching);
                }
                overdueDay = normalRepay(borrow, repaymentDetailVO);
            } else if (type == CommonConstant.REPAYMENT_TYPE_PREPAY) { // 提前还款
                prepayment(repaymentDetailVO, processInstanceId, borrow);
            } else if (type == 4) {
//                forceSettleRepay(type, rec, processInstanceId, borrow, calculateService);
            }  else if (type == CommonConstant.REPAYMENT_TYPE_CHARGE_DISPOSAL) {
            	String remark = (String)repaymentDetailVO.get("remark");
            	//  房屋处置
                disposalRepayment(processInstanceId,borrow,userId,remark);
            }
            //通过processInstanceId获取项目流程表实体类并获取projectId
			 // 更新borrow表
			repaymentDao.updateBorrowCnt(processInstanceId);
            // 更新repayInfo
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(PROCESS_INSTANCE_ID, borrow.getProcessInstanceId());
            params.put("borrowId", borrow.getId());
            repaymentDao.updateRepayCnt(params);
            // 逾期天数4天内产生返佣
            if (overdueDay <= CommonConstant.OVER_DUE_DAY && type == CommonConstant.REPAYMENT_TYPE_NORMAL) { 
            	startRebateProcess(type, repaymentDetailVO, userId, processInstanceId, borrow, projectId);
			}
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        	throw new ServiceException(e.getMessage(),e);
		}
	}

	/**
	 * 启动返佣流程
	 * @param type
	 * @param rec
	 * @param userId
	 * @param processInstanceId
	 * @param borrow
	 * @param projectId
	 * @throws Exception
	 */
	private void startRebateProcess(Integer type, Map<String, Object> rec, Long userId, String processInstanceId,
			PlBorrow borrow, String projectId) throws Exception {
        //创建参数
        Map<String, Object> rebateMap=new HashMap<String,Object>();
        rebateMap.put(PROCESS_INSTANCE_ID, processInstanceId);
        rebateMap.put("projectId", projectId);
        rebateMap.put("userId", userId);
        rebateMap.put("totalPeriod", borrow.getTimeLimit());
        rebateMap.put(PERIOD, (Integer)rec.get(PERIOD));
        rebateMap.put("repaymentType", type);
        rebateTaskService.createRebateTasks(rebateMap);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
    public void startSupplyInvestigateProcess(String processInstanceId,Long userId) throws ServiceException {
        try {
        	PlBorrow borrow = borrowDao.getItemInfoByProcessInstanceId(processInstanceId);
			Long projectId = pubProjectProcessService.getItemInfoByProcessInstanceId(processInstanceId).getProjectId();
			SysUser user=sysUserDao.getByPrimary(userId);
			String userName=user.getUserName();
			PubProcessBranching pb = new PubProcessBranching();
			pb.setProcessInstanceId(processInstanceId);
			pb.setRemark2("还款补充信息筛查");
			Map<String,Object> variablesMap = new HashMap<String, Object>();
			variablesMap.put(SystemConstant.PROCESS_LAUNCHER, user.getUserName());
			variablesMap.put(SystemConstant.ORIGINAL_PROCESSINSTANCEID, processInstanceId);
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("supplyInvestigateProcess", variablesMap);
			String procInstId = processInstance.getProcessInstanceId();//得到分支流程的流程id
			String taskId = processInstance.getId();//得到分支流程的主键id
			List<Task> task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();//.singleResult();
			pb.setTaskId(task.get(0).getId());
			pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
			pb.setBranchingProcessType((byte) 9);
			pb.setProcessingOpinion("noprocess");
			pb.setProjectId(projectId);
			pb.setCreateTime(new Date());
			pb.setCreator(userName);
			pb.setIsDelete((byte) 0);
			pb.setPeriod((byte) 0);
			pb.setIsActive((byte) 1); //状态
			pb.setProcessStatus("usertask-supplyInvestigate");
			pb.setRemark2("还款补充信息筛查");
			pubProcessBranchingDao.insert(pb);
			
	    	PubProjectProcess pubProjectProcess = new PubProjectProcess();
	    	pubProjectProcess.setProjectId(projectId);
	    	pubProjectProcess.setProcessInstanceId(procInstId);
	    	pubProjectProcess.setExtensionNumber(SystemConstant.IS_DELETE_NORMAL);
	    	pubProjectProcess.setProcessType(SystemConstant.SUPPLY_MATERIAL);
	    	pubProjectProcessDao.insert(pubProjectProcess);
	    	
	    	PubLoanprocess pubLoanprocess = new PubLoanprocess();
	    	pubLoanprocess.setCreator(userId);
	    	pubLoanprocess.setCreateTime(new Date());
	    	pubLoanprocess.setIsDelete(SystemConstant.IS_DELETE_NORMAL);
	    	pubLoanprocess.setOfficeId(user.getOfficeId());
	    	pubLoanprocess.setProcessInstanceId(procInstId);
	    	pubLoanprocess.setProjectId(projectId);
	    	pubLoanprocess.setConsultId(borrow.getConsultId());
	    	pubLoanprocess.setType("supply");
	    	pubLoanprocess.setRemark("");
	    	pubLoanprocess.setProcessState("usertask-supplyInvestigate");
	    	pubLoanprocess.setTaskId(taskId);
	    	pubLoanprocess.setNextAssignee(task.get(0).getAssignee());
	    	pubLoanprocessDao.insert(pubLoanprocess);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
        	throw new ServiceException(e.getMessage(),e);
		}
    }
    /**
     * 正常还款提交
     * @param borrow
     * @param repaymentDetailVO
     * @throws ServiceException
     */
	private int normalRepay(PlBorrow borrow, Map<String, Object> repaymentDetailVO) throws ServiceException {
		// 实际还款时间
		Date realpaymentTime = DateUtil.getFormatDateTime(repaymentDetailVO.get(REAL_PAYMENT_TIME).toString());
		BigDecimal realAccount = new BigDecimal(repaymentDetailVO.get(REAL_ACCOUNT).toString());
		BigDecimal realInterest = new BigDecimal(repaymentDetailVO.get(REAL_INTEREST).toString());
		
		BigDecimal backTotalAmount = getZeroBigDecimal();// 退还金额
		Map<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put(PROCESS_INSTANCE_ID, borrow.getProcessInstanceId());
		PlFeeinfo feeinfo;
		Integer currentTimeLimit;
		int timeLimit = borrow.getTimeLimit();
		try {
			Map<String, Object> unPayRepaymentDetail = repaymentDao.getMinUnPayedRepaymentdetail(searchParam);
			feeinfo = getFeeinfo(borrow.getProcessInstanceId());
			currentTimeLimit = Integer.parseInt(unPayRepaymentDetail.get(PERIOD).toString());
			
			int overdueDay = calculateOverdueDays((Date) unPayRepaymentDetail.get(REPAYMENT_TIME),realpaymentTime);
			boolean isOverdue = overdueDay > 0 ? true : false;
			if (timeLimit == currentTimeLimit && hasOverdue(borrow.getProcessInstanceId()) && isOverdue) {
				plFeeinfoDao.update(feeinfo);
			}
			if (isOverdue) {// 逾期计算逾期费
				String processInstanceId = (String) unPayRepaymentDetail.get(PROCESS_INSTANCE_ID);
				Date calculateTime = new Date();
				if (unPayRepaymentDetail.get(REAL_PAYMENT_TIME) != null) {
					calculateTime = (Date) unPayRepaymentDetail.get(REAL_PAYMENT_TIME);
					unPayRepaymentDetail.put(REAL_PAYMENT_TIME, DateUtil.getFormatDate(calculateTime));
				}
				Map<String, Object> d = this.calculateRepaymentDetail(processInstanceId,borrow, unPayRepaymentDetail,calculateTime);
				unPayRepaymentDetail.putAll(d);
			}
			normalRepay(isOverdue, unPayRepaymentDetail, realAccount, realpaymentTime, backTotalAmount);
			
			// 实际还款大于应还总额
			BigDecimal temp = realAccount.subtract(new BigDecimal(unPayRepaymentDetail.get(ACCOUNT).toString())).setScale(0, BigDecimal.ROUND_CEILING);
			logger.info("【实际还款】" + temp);
			if (temp.compareTo(getZeroBigDecimal()) >= 0) {
				//实还金额大于本期应还则结清
				unPayRepaymentDetail.put("isPayoff", CommonConstant.PAY_OFF_TRUE);
			} else {
				throw new ServiceException("本期实际还款金额不能小于本期应还金额");
			}
			unPayRepaymentDetail.put(REAL_INTEREST, realInterest);
			repaymentDao.updateRepaymentdetailById(unPayRepaymentDetail);
			return overdueDay;
		} catch (PersistentDataException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException("数据库操作失败,请稍后重试");
		}
	}
    
    /**
     * @description 正常还款金额处理  本金>利息>违约金>罚息 按整数处理(四舍五入)
     * @param detail
     * @param realAmount  本次还款金额
     * @throws ServiceException 
    */
    private BigDecimal normalRepay( boolean isOverdue,Map<String,Object> detail,BigDecimal realAmount, Date realpaymentTime,BigDecimal  backPlatformFee) throws ServiceException{
		// 已还金额处理
		BigDecimal realAccount = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(REAL_ACCOUNT));
		realAmount = realAmount.add(realAccount);
		BigDecimal totalAmount = realAmount;
		if (backPlatformFee != null) {
			realAmount = realAmount.add(backPlatformFee);
		}
		// 减免费用
		// 罚息减免金额
		BigDecimal detailReductionPenalty = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(REDUCTION_PENALTY));
		// 违约金减免金额
		BigDecimal detailBreachContract = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(BREACH_CONTRACT));
		BigDecimal penalty = getZeroBigDecimal();
		BigDecimal overdue = getZeroBigDecimal();
		// 罚息
		BigDecimal detailOverdue = new BigDecimal(detail.get(DEFAULT_INTEREST).toString());
		// 违约金
		BigDecimal detailPenalty = new BigDecimal(detail.get(PENALTY).toString());
		if (isOverdue) {
			// 逾期情况计算利息
			List<Map<String, Object>> d = new ArrayList<Map<String, Object>>();
			d.add(detail);

			// 更新罚息和违约金
			penalty = detailPenalty.subtract(detailBreachContract);
			overdue = detailOverdue.subtract(detailReductionPenalty);
			if (penalty.compareTo(getZeroBigDecimal()) < 0) {
				penalty = getZeroBigDecimal();
			}
			if (overdue.compareTo(getZeroBigDecimal()) < 0) {
				overdue = getZeroBigDecimal();
			}
			detail.put(DEFAULT_INTEREST, overdue);
			detail.put(PENALTY, penalty);
			detail.put("repaymentStaus", 3);
		} else {
			detail.put("repaymentStaus", 1);
		}
		// 本金
		BigDecimal detailCapital = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get("capital"));
		if (realAmount.subtract(detailCapital).setScale(2, BigDecimal.ROUND_CEILING).compareTo(getZeroBigDecimal()) >= 0) {
			detail.put("repaymentCapitalAmount", detailCapital);
			detail.put("needrepayCapital", 0);
			realAmount = realAmount.subtract(detailCapital);
		} else {
			detail.put("repaymentCapitalAmount", realAmount);
			detail.put("needrepayCapital", detailCapital.subtract(realAmount));
			realAmount = getZeroBigDecimal();
		}
		// 利息
		BigDecimal detailInterest = ObjectInitUtil.initBigDecimal(
				new BigDecimal((detail.get("interest") == null ? 0 : detail.get("interest")).toString()));
		if (realAmount.subtract(detailInterest).setScale(2, BigDecimal.ROUND_CEILING).compareTo(getZeroBigDecimal()) >= 0) {
			detail.put("repaymentInterestAmount", detailInterest);
			realAmount = realAmount.subtract(detailInterest);
		} else {
			detail.put("repaymentInterestAmount", realAmount);
			realAmount = getZeroBigDecimal();
		}
		if (isOverdue) {
			// 违约金罚息，如果剩余金额不足以归还，则把剩余金额记0
			if (realAmount.subtract(penalty).setScale(2, BigDecimal.ROUND_CEILING).compareTo(getZeroBigDecimal()) >= 0) {
				realAmount = realAmount.subtract(penalty);
			} else {
				realAmount = getZeroBigDecimal();
			}
			if (realAmount.subtract(overdue).setScale(2, BigDecimal.ROUND_CEILING).compareTo(getZeroBigDecimal()) >= 0) {
				realAmount = realAmount.subtract(overdue);
			} else {
				realAmount = getZeroBigDecimal();
			}
		}
		detail.put(REAL_ACCOUNT, totalAmount);
		detail.put(REAL_PAYMENT_TIME, realpaymentTime);
		return realAmount;
    }
    
    /**
     * 获取提前还款总额
     * @param realPaymentDate
     * @param currentTimeLimit
     * @param processInstanceId
     * @param calculateService
     * @return
     * @throws ServiceException
     */
    private Map<String, Object> getPrepaymentAccount(Date realPaymentDate, Integer currentTimeLimit,PlBorrow borrow,
            String processInstanceId) throws ServiceException {
        Map<String, Object> map = getPrepayment(realPaymentDate, borrow,processInstanceId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.putAll(map);
        BigDecimal realAccount=getZeroBigDecimal();
        BigDecimal capital = ObjectInitUtil.initBigDecimal((BigDecimal) map.get("capital"));
        BigDecimal interest = ObjectInitUtil.initBigDecimal((BigDecimal) map.get("interest"));
        BigDecimal overdue = ObjectInitUtil.initBigDecimal((BigDecimal) map.get("overdue"));
        BigDecimal penalty = ObjectInitUtil.initBigDecimal((BigDecimal) map.get(PENALTY));
        BigDecimal alreadyRepayAmount=ObjectInitUtil.initBigDecimal((BigDecimal) map.get("alreadyRepayAmount"));
        BigDecimal totalAccount = getZeroBigDecimal();
        totalAccount = capital.add(interest).add(overdue).add(penalty);
        realAccount=totalAccount.subtract(alreadyRepayAmount);
        if(realAccount.compareTo(getZeroBigDecimal())<0){
            realAccount=getZeroBigDecimal();
        }
        result.put(REAL_ACCOUNT, realAccount);
        result.put("needRepay", realAccount);
        result.put(TOTAL_ACCOUNT, totalAccount);
        result.put(ACCOUNT, capital.add(interest));
        if (currentTimeLimit != null) {
            result.put(PERIOD, currentTimeLimit);
        }
        result.put("realPaymentDate", realPaymentDate);
        result.put(PROCESS_INSTANCE_ID, processInstanceId);
        return result;
    }
    
    public Map<String, Object> getPrepayment(Date realPaymentDate,PlBorrow borrow,
			String processInstanceId) throws ServiceException {
    	Integer currentTimeLimit=null;
        Map<String, Object> searchParam = new HashMap<String, Object>();
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(PROCESS_INSTANCE_ID, processInstanceId);
        paramMap.put("branchingProcessType", "6");//展期
        paramMap.put("limit", 1);
        PubProcessBranching ppb = pubProcessBranchingDao.getItemByMap(paramMap);
        if(ppb == null){
        	searchParam.put(PROCESS_INSTANCE_ID, processInstanceId);
        }else{
        	searchParam.put(PROCESS_INSTANCE_ID, ppb.getBranchingProcessId());
        }
        Map<String,Object> result=new HashMap<String,Object>();
        BigDecimal backTotalAmount = getZeroBigDecimal();
        //未还款明细
        List<Map<String, Object>> details;
        // 提前还款违约金
        BigDecimal aheadMoney = getZeroBigDecimal();
        Map<String, Object> d= repaymentDao.getMinUnPayedRepaymentdetail(searchParam);
		if(d!=null){
		    currentTimeLimit=Integer.parseInt(d.get(PERIOD).toString());
		}
		
		// 未还款明细统计
		details = repaymentDao.getUnPayedRepaymentdetails(searchParam);
		aheadMoney = felServiceImpl.calculateUseFeeInfo(processInstanceId,borrow.getAccount(), FelConstant.PREPAYMENT_PENALTY);
       
        BigDecimal capital=getZeroBigDecimal();
        //未减免违约金
        BigDecimal totalPenalty=getZeroBigDecimal();
        //未减免罚息
        BigDecimal totalOverdue=getZeroBigDecimal();
        //平台服务费
        BigDecimal otherFee=getZeroBigDecimal();
        
        for (int i = 0; i < details.size(); i++) {
            Map<String, Object> detail = details.get(i);
            // 本金
            BigDecimal detailCapital = (BigDecimal) detail.get("capital");
            // 利息
            BigDecimal detailInterest = (BigDecimal) detail.get("interest");
            //归还但改期未结清的已还金额
            if(i==0){
                BigDecimal realAccount=ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(REAL_ACCOUNT));
                //判断本金
                if(realAccount.subtract(detailCapital).setScale(0,BigDecimal.ROUND_HALF_UP).compareTo(getZeroBigDecimal())>=0){
                    realAccount=realAccount.subtract(detailCapital);
                    detailCapital=getZeroBigDecimal();
                }else{
                    detailCapital=detailCapital.subtract(realAccount);
                    realAccount=getZeroBigDecimal();
                }
                //判断利息
                if(realAccount.subtract(detailInterest).setScale(0,BigDecimal.ROUND_HALF_UP).compareTo(getZeroBigDecimal())>=0){
                    realAccount=realAccount.subtract(detailInterest);
                    detailInterest=getZeroBigDecimal();
                }else{
                    detailInterest=detailInterest.subtract(realAccount);
                    realAccount=getZeroBigDecimal();
                }
            }
            capital=capital.add(detailCapital);
        }
        result.put("backTotalAmount", backTotalAmount);
        capital = capital.setScale(0, BigDecimal.ROUND_HALF_UP);
        result.put("capital",capital);
        result.put(PENALTY,aheadMoney);//提前还款违约金
        
        result.put("totalPenalty",totalPenalty);
        result.put("totalOverdue",totalOverdue);
        result.put("totalDefaultInterest", totalOverdue);
        result.put("otherFee",otherFee);
        result.put("currentPeriod", currentTimeLimit);
        return result;
	}
	
    
    
    /**
     * 提前还款提交
     * @param rec
     * @param processInstanceId
     * @param borrow
     * @param calculateService
     * @throws ServiceException
     */
    public void prepayment(Map<String, Object> rec, String processInstanceId, PlBorrow borrow) throws ServiceException {
        // 实际还款时间
        Date realpaymentTime = DateUtil.getFormatDateTime(rec.get(REAL_PAYMENT_TIME).toString());
        
        // 实际还款
        BigDecimal realAccount=getZeroBigDecimal();
        if(rec.get(REAL_ACCOUNT)!=null){
            realAccount=new BigDecimal(rec.get(REAL_ACCOUNT).toString());
        }
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put(PROCESS_INSTANCE_ID, processInstanceId);
        
        // 提前还款违约金
        BigDecimal totalPenalty = felServiceImpl.calculateUseFeeInfo(processInstanceId,borrow.getAccount(), FelConstant.PREPAYMENT_PENALTY);
        
        //未还款明细
        List<Map<String, Object>> details = repaymentDao.getUnPayedRepaymentdetails(searchParam);
        if (CollectionUtils.isNotEmpty(details)) {
        	for (int i = 0; i < details.size(); i++) {
            	if (i == 0) {// 提前还款处理最近一期
					Map<String, Object> detail = details.get(i);
					boolean isOverdue = calculateOverdueDays((Date) detail.get(REPAYMENT_TIME), realpaymentTime) > 0 ? true : false;
					// 本金
					BigDecimal detailCapital = (BigDecimal) detail.get("capital");
					// 利息
					BigDecimal detailInterest = (BigDecimal) detail.get("interest");
					// 罚息
					BigDecimal detailOverdue = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(DEFAULT_INTEREST));
					// 违约金
					BigDecimal detailPenalty = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(PENALTY));
					// 罚息减免金额
					BigDecimal detailReductionPenalty = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(REDUCTION_PENALTY));
					// 违约金减免金额
					BigDecimal detailBreachContract = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(BREACH_CONTRACT));
					BigDecimal penalty = getZeroBigDecimal();
					BigDecimal overdue = getZeroBigDecimal();
					if (isOverdue) {
						detailPenalty = felServiceImpl.calculateUseFeeInfo(processInstanceId, borrow.getAccount(),
								FelConstant.PREPAYMENT_PENALTY);
						// 更新罚息和违约金
						detail.put(DEFAULT_INTEREST, detailOverdue);
						detail.put(PENALTY, detailPenalty);
						penalty = detailPenalty.subtract(detailBreachContract);
						overdue = detailOverdue.subtract(detailReductionPenalty);
						if (penalty.compareTo(getZeroBigDecimal()) < 0) {
							penalty = getZeroBigDecimal();
						}
						if (overdue.compareTo(getZeroBigDecimal()) < 0) {
							overdue = getZeroBigDecimal();
						}
					}
                    //还款时间
                    detail.put(REAL_PAYMENT_TIME, realpaymentTime);
                    //已还本期本金
                    detail.put("repaymentCapitalAmount", detailCapital);
                    //已还本期利息
                    detail.put("repaymentInterestAmount", getZeroBigDecimal());
                    // 剩余待还本金
                    detail.put("needrepayCapital", getZeroBigDecimal());
                    detail.put("isPayoff", CommonConstant.PAY_OFF_TRUE);
                    detail.put("repaymentStaus", 4);
                    // 违约金
                    detail.put(PENALTY, totalPenalty.add(penalty));
                    // 实际还款
                    detail.put(REAL_ACCOUNT, realAccount);
                    repaymentDao.updateRepaymentdetailById(detail);
				} else{
					Map<String, Object> detail = details.get(i);
					BigDecimal zero = getZeroBigDecimal();
	                //还款时间
	                detail.put(REAL_PAYMENT_TIME, realpaymentTime);
	                //已还本期本金
	                detail.put("repaymentCapitalAmount",zero);
	                //已还本期利息
	                detail.put("repaymentInterestAmount",zero);
                    // 剩余待还本金
	                detail.put("needrepayCapital",zero);
	                detail.put("isPayoff", CommonConstant.PAY_OFF_TRUE);
	                detail.put("repaymentStaus", 4);
	                // 违约金
                    detail.put(PENALTY, zero);
	                // 实际还款
	                detail.put(REAL_ACCOUNT,zero);
	                repaymentDao.updateRepaymentdetailById(detail);
				}
            }
		}
    }
    
    
    /**
     * 押品处置
     * @param processInstanceId
     * @param borrow
     * @throws ServiceException
     */
    public void disposalRepayment(String processInstanceId, PlBorrow borrow,Long userId,String remark) throws ServiceException {
        // 实际还款时间
        Date realpaymentTime = new Date();
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("processInstanceId", processInstanceId);
        // 押品处置
        BigDecimal totalPenalty = felServiceImpl.calculateUseFeeInfo(processInstanceId,borrow.getAccount(), FelConstant.PREPAYMENT_PENALTY);
        
        //未还款明细
        List<Map<String, Object>> details = repaymentDao.getUnPayedRepaymentdetails(searchParam);
        if (CollectionUtils.isNotEmpty(details)) {
        	for (int i = 0; i < details.size(); i++) {
            	if (i == 0) {// 提前还款处理最近一期
					Map<String, Object> detail = details.get(i);
					boolean isOverdue = calculateOverdueDays((Date) detail.get(REPAYMENT_TIME), realpaymentTime) > 0 ? true : false;
					// 本金
					BigDecimal detailCapital = (BigDecimal) detail.get("capital");
					// 利息
					BigDecimal detailInterest = (BigDecimal) detail.get("interest");
					// 罚息
					BigDecimal detailOverdue = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(DEFAULT_INTEREST));
					// 违约金
					BigDecimal detailPenalty = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(PENALTY));
					// 罚息减免金额
					BigDecimal detailReductionPenalty = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(REDUCTION_PENALTY));
					// 违约金减免金额
					BigDecimal detailBreachContract = ObjectInitUtil.initBigDecimal((BigDecimal) detail.get(BREACH_CONTRACT));
					BigDecimal penalty = getZeroBigDecimal();
					BigDecimal overdue = getZeroBigDecimal();
					if (isOverdue) {
						detailPenalty = felServiceImpl.calculateUseFeeInfo(processInstanceId, borrow.getAccount(),
								FelConstant.PREPAYMENT_PENALTY);
						// 更新罚息和违约金
						detail.put(DEFAULT_INTEREST, detailOverdue);
						detail.put(PENALTY, detailPenalty);
						penalty = detailPenalty.subtract(detailBreachContract);
						overdue = detailOverdue.subtract(detailReductionPenalty);
						if (penalty.compareTo(getZeroBigDecimal()) < 0) {
							penalty = getZeroBigDecimal();
						}
						if (overdue.compareTo(getZeroBigDecimal()) < 0) {
							overdue = getZeroBigDecimal();
						}
					}
                    //还款时间
                    detail.put(REAL_PAYMENT_TIME, realpaymentTime);
                    BigDecimal zero = getZeroBigDecimal();
                    //已还本期本金
                    detail.put("repaymentCapitalAmount",zero);
                    //已还本期利息
                    detail.put("repaymentInterestAmount",zero);
                    // 剩余待还本金
                    detail.put("needrepayCapital",zero);
                    detail.put("isPayoff", CommonConstant.PAY_OFF_TRUE);
                    detail.put("repaymentStaus", CommonConstant.REPAYMENT_TYPE_CHARGE_DISPOSAL);
                    // 违约金
                    detail.put(PENALTY, totalPenalty.add(penalty));
                    // 实际还款
                    detail.put(REAL_ACCOUNT,zero);
                    repaymentDao.updateRepaymentdetailById(detail);
				} else{
					Map<String, Object> detail = details.get(i);
					BigDecimal zero = getZeroBigDecimal();
	                //还款时间
	                detail.put(REAL_PAYMENT_TIME, realpaymentTime);
	                //已还本期本金
	                detail.put("repaymentCapitalAmount",zero);
	                //已还本期利息
	                detail.put("repaymentInterestAmount",zero);
                    // 剩余待还本金
	                detail.put("needrepayCapital",zero);
	                detail.put("isPayoff", CommonConstant.PAY_OFF_TRUE);
	                detail.put("repaymentStaus", CommonConstant.REPAYMENT_TYPE_CHARGE_DISPOSAL);
	                // 违约金
                    detail.put(PENALTY, zero);
	                // 实际还款
	                detail.put(REAL_ACCOUNT,zero);
	                repaymentDao.updateRepaymentdetailById(detail);
				}
            }
        ChargeDisposalLog chargeDisposalLog = new ChargeDisposalLog();
        chargeDisposalLog.setCreateTime(new Date());
        chargeDisposalLog.setCreator(userId);
        chargeDisposalLog.setProcessInstanceId(processInstanceId);
        chargeDisposalLog.setRemark(remark);
        repaymentDao.addChargeDisposal(chargeDisposalLog);
		}
        
    }
    
    
    /**
     * 导出表格查询还款详情
     */
    @Override
	public List<Map<String, Object>> queryRepaymentDetailList(Map<String, Object> paramMap) throws ServiceException {
		RepaymentVO repayment = (RepaymentVO)paramMap.get("repayment");
		List<Map<String, Object>> repaymentDetailList = (List<Map<String, Object>>) (Object)repaymentDao.getRepaymentDetailPageByMap(paramMap);
		PlBorrow borrow = borrowDao.getItemInfoByProcessInstanceId(repayment.getProcessInstanceId());
        PlContract contract = contractDao.getItemInfoByProcessInstanceId(repayment.getProcessInstanceId());
        HousBorrowingBasics borrowBasic = borrowingBasicsDao.getItemInfoByProcessInstanceId(repayment.getProcessInstanceId());
        // 对未还款明细进行罚息、违约金计算,已还款的不计算
        for (Map<String, Object> detail : repaymentDetailList) {
            detail.put("contractNo", contract.getContractNo());
            detail.put("customerName", borrowBasic.getName());
            Object realAccount=detail.get(REAL_ACCOUNT);
            if (((Integer) detail.get("isPayoff")) == 0) {
                Date calculateTime=new Date();
                if(detail.get(REAL_PAYMENT_TIME)!=null){
                    calculateTime=(Date)detail.get(REAL_PAYMENT_TIME);
                }
                // TODO 还款计算
                Map<String, Object> d = this.calculateRepaymentDetail(borrow.getProcessInstanceId(),borrow,detail,calculateTime );
                detail.putAll(d);
            } else {
            	BigDecimal totalAccount = ((BigDecimal) detail.get(ACCOUNT)).add(
                      (BigDecimal) detail.get(DEFAULT_INTEREST)).add((BigDecimal) detail.get(PENALTY)
                              );
                detail.put(TOTAL_ACCOUNT, totalAccount);
            }
            detail.put(REAL_ACCOUNT, realAccount);
        }
		return repaymentDetailList;
	}
    
    /**
     * 导出表格查询
     */
    @Override
	public List<Map<String, Object>> queryFactBorrowList(Map<String, Object> params) {
		params.put("dictParentId2", 50);
        params.put("dictParentId1", 50);//还款方式parientId=50
        params.put("dict1parentId", 53);
        params.put("isLocked", 0);
        return repaymentDao.queryRepayLoanList(params);
	}
    
    
    @Override
	public Boolean validateWhetherCanPay(Map<String, Object> params) {
		if (repaymentDao.validateWhetherCanPay(params) == null) {
			return false;
		}
		return true;
	}
    
    private PlFeeinfo getFeeinfo(String processInstanceId) throws PersistentDataException {
        return plFeeinfoDao.getItemInfoByProcessInstanceId(processInstanceId);
    }
    
    /**
     * 计算是否逾期
     * @param processInstanceId
     * @return
     * @throws ServiceException
     */
    private boolean hasOverdue(String processInstanceId)throws ServiceException{
        Map<String,Object> params=new HashMap<String,Object>();
        params.put(PROCESS_INSTANCE_ID, processInstanceId);
        List<Map<String, Object>> list;
        list = repaymentDao.queryAllOverdue(params);
        if (list != null) {
        	return list.size() > 0;
		} else {
			return false;
		}
    }
    
    /**
     * @description 计算逾期天数。
     * 款人未能在约定还款日24:00应还款金额存入指定还款账户的，视为还款逾期，出借人有权自还款日24：00以后开始计收逾期违约金及罚息
     * @param repaymentTime 约定还款时间
     * @param current 当前时间
     * @return
     * @author wtc
     * @return int
     * @since  1.0.0
    */
    public  int calculateOverdueDays(Date repaymentTime, Date current){
        int count=0;
        int repayHour=23;
        Calendar c = Calendar.getInstance();
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        c.setTime(repaymentTime);
        //24点开始计算
        startDay.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        startDay.set(Calendar.HOUR_OF_DAY, repayHour);
        startDay.set(Calendar.MINUTE,59);
        startDay.set(Calendar.SECOND,59);
        startDay.set(Calendar.MILLISECOND,999);
        c.clear();
        endDay.setTime(current);
        count=(int) ((endDay.getTimeInMillis()-startDay.getTimeInMillis())/(1000*24*60*60));
        if(count<0){
            count=0;
        }
        startDay.add(Calendar.DAY_OF_YEAR, count);
        if(endDay.compareTo(startDay)>0){
            count++;
        }
        return count;
    }
}
