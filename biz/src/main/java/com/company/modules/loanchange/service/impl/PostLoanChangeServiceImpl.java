package com.company.modules.loanchange.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.common.context.Constant;
import com.company.common.context.SysDictConstant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.dao.PubProjectDao;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubProject;
import com.company.common.domain.PubProjectProcess;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.domain.PubRepaymentdetail;
import com.company.common.domain.query.Pagination;
import com.company.common.service.PlBorrowService;
import com.company.common.service.PubProcessBranchingService;
import com.company.common.service.PubRepaymentdetailService;
import com.company.common.utils.ParamChecker;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.dao.PlFeeinfoDao;
import com.company.modules.common.dao.PlProductDao;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlFeeinfo;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.domain.PubLoanprocess;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.service.impl.HistoryRecorderService;
import com.company.modules.common.util.calculate.CalculateService;
import com.company.modules.common.utils.ObjectInitUtil;
import com.company.modules.common.utils.databean.ProjectWorkflowDataBean;
import com.company.modules.fel.context.FelConstant;
import com.company.modules.fel.context.FelTypeEnum;
import com.company.modules.fel.service.FelService;
import com.company.modules.loanchange.dao.PostLoanChangeDao;
import com.company.modules.loanchange.domain.DisposalDataBean;
import com.company.modules.loanchange.domain.DisposalLog;
import com.company.modules.loanchange.domain.LoanChangeDataBean;
import com.company.modules.loanchange.service.PostLoanChangeService;
import com.company.modules.repayment.dao.PubRepaymentDao;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.domain.SysRole;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysRoleService;
import com.company.modules.workflow.utils.observation.TaskAssignerCenter;

//@SuppressWarnings("all")
@Service(value = "postLoanChangeService")
public class PostLoanChangeServiceImpl extends HistoryRecorderService implements PostLoanChangeService {
    private static Logger log = LoggerFactory.getLogger(PostLoanChangeServiceImpl.class);
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private PlBorrowService plBorrowService;
    @Autowired
    private PlConsultService plConsultService;
    @Autowired
    TaskService taskService;
    @Autowired
    private PubRepaymentdetailService repaymentdetailService;
    @Autowired
    private PubProcessBranchingService pubProcessBranchingService;
    @Autowired
    private FelService felService;
    @Autowired
    private PostLoanChangeDao postLoanChangeDao;
    @Autowired
    private PlBorrowDao plBorrowDao;
    @Autowired
    private PubProcessBranchingDao pubProcessBranchingDao;
    @Autowired
    private PubLoanprocessDao pubLoanprocessDao;
    @Autowired
    private PubProjectProcessDao pubProjectProcessDao;
    @Autowired
    private PubProjectDao pubProjectDao;
    @Autowired
    private PlProductDao plProductDao;
    @Autowired
	private SysUserDao sysUserDao;
//    @Autowired
//    private HouseDisposalLogDao houseDisposalLogDao;
    @Autowired
    private PubRepaymentDao pubRepaymentDao;
    @Autowired
    private PubRepayloaninfoDao pubRepayloaninfoDao;
    @Autowired
    private PlFeeinfoDao plFeeinfoDao;
    @Autowired
    private PubRepaymentdetailDao repaymentdetailDao;
    @Autowired
	private PlBorrowDao borrowDao;
    

    private final Double SECONDARY_AUDIT_AMOUNT_THRESHOLD = 200000D;
    private final Double SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD = 200000D;
    private final Double MANAGER_CONFIRMATION_AMOUNT_THRESHOLD = 500000D;
    private static final String USERTASK_CUSTOMER_SURVEY = "usertask-customer-survey";
    private static final String USERTASK_EXTENSION_CUSTOMER_SURVEY = "usertask-extension-customer-survey";
    private static final String USERTASK_EXTENSION_COLLATERALASSESS = "usertask-extension-collateralAssess";
    
    @Override
    public List<Map> pendingApplicationList(Map<String, Object> params) throws ServiceException {
//        params.put("isLocked", 0);
        params.put("repaymentTypeDictId", 50);//还款
        params.put("nqRepaymentStatus", 1);
        return pubRepaymentDao.pendingApplicationList(params);
    }

    @Override
    public List<Map> postLoanChangeList(Map<String, Object> params) throws ServiceException {
        params.put("repaymentTypeDictId", SysDictConstant.REPAYMENT_TYPE);
        params.put("extensionTypeDictId", SysDictConstant.EXTENSION_Type);
        params.put("processStatusDictId", SysDictConstant.WORKFLOW_STATE);
        List<Map> postLoanChangeList = pubRepaymentDao.postLoanChangeList(params);
        return postLoanChangeList;
    }
    
    @Override
    public Map<String, Object> getAheadOfReturnLoanInfo(String processInstanceId,Boolean isBranch) throws ServiceException {
		Map<String, Object> aheadInfo = new HashMap<String, Object>();
		Map<String, Object> processInstanceMap = getProcessOrBranchProcessMap(processInstanceId, isBranch);
		// 获取主流程实例id
		String mainProcessInstanceId = processInstanceMap.get("processInstanceId").toString();
		// 客户贷款基本信息
		aheadInfo.putAll(getCustomerBaseInfo(mainProcessInstanceId));
		// 本金
		BigDecimal capitalSum;
		// 违约金
		BigDecimal penaltySum;
		byte period;
		
		PlFeeinfo feeinfo = plFeeinfoDao.getItemInfoByProcessInstanceId(mainProcessInstanceId);
		aheadInfo.put("aheadRepayRate", feeinfo.getAheadRepayRate().setScale(4,BigDecimal.ROUND_CEILING));
		
		if (processInstanceMap.get("branchingProcessId") == null) {
			// 主流程
			Map<String, Object> result = getPrepayment(mainProcessInstanceId);
			// 本金
			capitalSum = (BigDecimal) result.get("capital");
			// 违约金
			penaltySum = (BigDecimal) result.get("penalty");
			period = (byte) result.get("currentPeriod");
			
		} else {
			// String branchid = processInstanceMap.get("branchingProcessId").toString();
			
			PubProcessBranching ppb = pubProcessBranchingDao.getItemByMap(processInstanceMap);
			aheadInfo.put("remark1", ppb.getRemark1());
			aheadInfo.put("makeRepaymentTime", ppb.getMakeRepaymentTime());
//			aheadInfo.put("realPrepaymentPenalty", ppb.getPenaltySum());
			if (isCompleteProcess(ppb.getBranchingProcessId())) {
				// 分支流程结束
				capitalSum = ppb.getCapitalSum();
				penaltySum = ppb.getPenaltySum();
				period = ppb.getPeriod();
			} else {
				Map<String, Object> result = getPrepayment(mainProcessInstanceId);
				// 本金
				capitalSum = (BigDecimal) result.get("capital");
				// 违约金
				penaltySum = (BigDecimal) result.get("penalty");
				period = (byte) (result.get("currentPeriod"));
			}
        }
        //划扣总额
        BigDecimal needReturnTotalSum = capitalSum.add(penaltySum);
        aheadInfo.put("processInstanceId", processInstanceId);
        aheadInfo.put("capitalSum", capitalSum.setScale(2,BigDecimal.ROUND_CEILING));
        aheadInfo.put("penaltySum", penaltySum.setScale(2,BigDecimal.ROUND_CEILING));
        aheadInfo.put("needReturnTotalSum", needReturnTotalSum.setScale(2,BigDecimal.ROUND_CEILING));
        aheadInfo.put("period", period);
        return aheadInfo;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("all")
    @Override
    public Map aheadofReturnLoanSubmit(Long processInstanceId, LoanChangeDataBean loanChangeDataBean,
            String userName) throws ServiceException{
    	Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_SUCCESS);
    	
    	try {
    		PlBorrow plBorrow = plBorrowService.getItemInfoByProcessInstanceId(Long.toString(processInstanceId));
    		Map paramMap = new HashMap();
            paramMap.put("processInstanceId", processInstanceId);
            List<PlBorrow> borrowList = plBorrowService.getPageListByMap(paramMap);
        	if (borrowList == null) {
                throw new RuntimeException("missing project information");
            }
            
            // 更新费用表提前还款利率
            PlFeeinfo feeinfo = plFeeinfoDao.getItemInfoByProcessInstanceId(String.valueOf(processInstanceId));
            feeinfo.setAheadRepayRate(loanChangeDataBean.getAheadRepayRate());
            plFeeinfoDao.update(feeinfo);
            // 提前还款违约金
            BigDecimal penalty = felService.calculateUseFeeInfo(Long.toString(processInstanceId),plBorrow.getAccount(),FelTypeEnum.PREPAYMENT_PENALTY.value());
            
            paramMap.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
            List<PubProcessBranching> list = pubProcessBranchingService.getPageListByMap(paramMap);
            // 将前一个分支流程设置成非活动状态
            if (list != null && list.size() > 0) {
                for (PubProcessBranching branching : list) {
                    branching.setIsActive((byte) 0);
                    branching.setModifier(userName);
                    branching.setModifyTime(new Date());
                    pubProcessBranchingDao.update(branching);
                }
            }
            
         // 新生成一个分支流程 并插入分支流程表
            PubProcessBranching pb = new PubProcessBranching();
            pb.setProcessInstanceId(Long.toString(processInstanceId));
            pb.setMakeRepaymentTime(loanChangeDataBean.getMakeRepaymentTime());
//            pb.setPenaltySum(loanChangeDataBean.getRealPrepaymentPenalty());
            pb.setPenaltySum(penalty);
            pb.setRemark1(loanChangeDataBean.getRemark1());
            pb.setRemark2(loanChangeDataBean.getRemark2());

            List<Task> task = startProcess(processInstanceId.toString(), 
            		WorkFlowConstant.PROCESS_NAME_EARLY_REPAYMENT_APPROVAL, 
            		userName,loanChangeDataBean.getProjectId(), null);

            // 几个小流程，都设置为 贷后分支流程类别 需要在任务中进行统一处理。
            pb.setTaskId(task.get(0).getId());
            pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
            pb.setBranchingProcessType((byte) 1);
            pb.setRepaymentProcess((byte) 0);
            pb.setProcessingOpinion("noprocess");
            pb.setProjectId(loanChangeDataBean.getProjectId());
            pb.setCreateTime(new Date());
            pb.setCreator(userName);
            pb.setIsDelete((byte) 0);
            pb.setIsActive((byte) 1);
            pb.setProcessStatus(WorkFlowConstant.PROCESS_STATUS_EARLY_REPAYMENT_APPROVAL);
            pubProcessBranchingDao.insert(pb);
            
            //插入下一步审批人
            PubLoanprocess pubLoanprocess = new PubLoanprocess();
            pubLoanprocess.setCreateTime(new Date());
           	pubLoanprocess.setIsDelete(SystemConstant.IS_DELETE_NORMAL);
           	pubLoanprocess.setProcessInstanceId(Long.toString(processInstanceId));
           	pubLoanprocess.setProjectId(loanChangeDataBean.getProjectId());
           	pubLoanprocess.setProcessState(WorkFlowConstant.PROCESS_STATUS_EARLY_REPAYMENT_APPROVAL);
           	pubLoanprocess.setTaskId(task.get(0).getProcessInstanceId());
           	pubLoanprocess.setNextAssignee(task.get(0).getAssignee());
           	pubLoanprocessDao.insert(pubLoanprocess);
		} catch (Exception e) {
			log.error("【贷后变更申请失败】"+e.getMessage(),e);
			throw new ServiceException("分配任务失败，请检查人员配置",e);
		}
        return res;
    }
    

    @Override
    public List<Map> getPostLoanChangeList(Map params) throws PersistentDataException {
        return postLoanChangeDao.getPostLoanChangeList(params);
    }

    @Override
    public Long getPostLoanChangeCount(Map params) throws PersistentDataException {
        Pagination pagination = new Pagination();
        return postLoanChangeDao.getPostLoanChangeCount(params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveAheadofReturnLoanApply(LoanChangeDataBean carLoanChangeDataBean) throws Exception {
        recordLoanProcessHistory(carLoanChangeDataBean);
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object insertProjectProcess(PubProjectProcess pubProjectProcess) {
        return pubProjectProcessDao.insert(pubProjectProcess);
    }

    public Map queryAheadOfReturnLoan(Map map) throws ServiceException, PersistentDataException {
        return postLoanChangeDao.queryAheadOfReturnLoan(map);
    }

    public Map<String, Object> queryAheadOfReturnLoan1(Map map) throws ServiceException, PersistentDataException {
        Date now = new Date();
        map.put("repaymentTypeDictId", 45);
        List<Map> result = pubRepaymentDao.pendingApplicationList(map);
        if (CollectionUtils.isNotEmpty(result)) {
            Map<String, Object> detail = (Map) result.get(0);
            ParamChecker.checkEmpty(detail, "还款明细信息");
            String processInstanceId = detail.get("processInstanceId").toString();
            PlFeeinfo feeinfo = getFeeinfo(processInstanceId);
            ParamChecker.checkEmpty(feeinfo, "费用信息");
            Map<String, Object> m = repaymentInfo(processInstanceId, now);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("branchingProcessId", processInstanceId);
            PubProcessBranching ppb = pubProcessBranchingDao.getItemByMap(paramMap);
            if (ppb != null) {
                detail.put("remark1", ppb.getRemark1());
                detail.put("remark2", ppb.getRemark2());
                detail.put("makeRepaymentTime", ppb.getMakeRepaymentTime());
                detail.put("otherAmount", ppb.getOtherAmount());
            }
            detail.putAll(m);
            return detail;
        }
        return null;
        // return postLoanChangeDao.queryAheadOfReturnLoan(map);
    }

    private Map<String, Object> repaymentInfo(String processInstanceId, Date now) throws ServiceException,
            PersistentDataException {
        Map<String, Object> m = new HashMap<String, Object>();
        PlFeeinfo feeinfo = getFeeinfo(processInstanceId);
        ParamChecker.checkEmpty(feeinfo, "费用信息");
        CalculateService calculateService = null; // TODO getCalculateService(processInstanceId);
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("processInstanceId", processInstanceId);
        // 未还款信息 TODO
        List<Map<String, Object>> details = null;//repaymentdetailDao.getUnPayedRepaymentdetails(searchParam);
        BigDecimal defaultInterestSum = calculateService.calculateOverdueByRepaymentdetails(details, now);
        BigDecimal penaltySum = new BigDecimal(0);
        BigDecimal capitalSum = new BigDecimal(0);
        BigDecimal interestSum = new BigDecimal(0);
        BigDecimal needReturnTotalSum = new BigDecimal(0);
        for (Map<String, Object> d : details) {
            Map<String, Object> calculateDetail = calculateRepaymentDetail(d, processInstanceId, now);
            capitalSum = capitalSum.add((BigDecimal) calculateDetail.get("capital"));
            interestSum = interestSum.add((BigDecimal) calculateDetail.get("interest"));
            penaltySum = penaltySum.add((BigDecimal) calculateDetail.get("penalty"));
            needReturnTotalSum = needReturnTotalSum.add((BigDecimal) calculateDetail.get("realAccount"));
        }
        m.put("defaultInterestSum", defaultInterestSum);
        m.put("penaltySum", penaltySum);
        m.put("interestSum", interestSum);
        m.put("capitalSum", capitalSum);
        m.put("needReturnTotalSum", needReturnTotalSum);
        return m;
    }

    private Map<String, Object> calculateRepaymentDetail(Map<String, Object> detail, String processInstanceId,
            Date realPaymentDate) throws ServiceException {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("processInstanceId", processInstanceId);
        // Map<String, Object> detail;
        // 服务费
        // BigDecimal serviceFee;
        // 本期应还总额
        BigDecimal totalAccount = new BigDecimal("0");
        // 本期实际应还总额（计算得出的）
        BigDecimal realAccount = new BigDecimal("0");
        // List<Map<String, Object>> details;
        BigDecimal overdue = new BigDecimal("0");
        BigDecimal penalty = new BigDecimal("0");
        // 本期违规违约金
        BigDecimal violationsPenalty;
        // 本期违规拖车费
        BigDecimal violationsTruckFee;
        // 本期违规差旅费
        BigDecimal violationsTravelFee;
        // 本期违规其它费用
        BigDecimal violationsOtherFee;
        // 罚息减免金额
        BigDecimal reductionPenalty;
        // 违约金减免
        BigDecimal breachContract;
        // 违约金减免、罚息减免总和
        BigDecimal allBreaks;
        // 停车费GPS费
        BigDecimal gpsUsingFee = (BigDecimal) detail.get("gpsUsingFee");
        BigDecimal parkingFee = (BigDecimal) detail.get("parkingFee");
        // 其他费用
        BigDecimal otherFee = (BigDecimal) detail.get("otherFee");
        // 期数
        Integer period = Integer.parseInt(detail.get("period").toString());
        Integer maxPerod = pubRepaymentDao.queryMaxPeriod(processInstanceId);
        boolean isOverdue;
        CalculateService calculateService;

        try {
            calculateService = null;// TODO getCalculateService(processInstanceId);
            ParamChecker.checkEmpty(detail, "未还款明细");
            ParamChecker.checkEmpty(detail.get("repaymentTime"), "还款时间");
            if (detail.get("violationsPenalty") == null) {
                violationsPenalty = new BigDecimal("0");
            } else {
                violationsPenalty = (BigDecimal) detail.get("violationsPenalty");
            }
            if (detail.get("violationsTruckFee") == null) {
                violationsTruckFee = new BigDecimal("0");
            } else {
                violationsTruckFee = (BigDecimal) detail.get("violationsTruckFee");
            }
            if (detail.get("violationsTravelFee") == null) {
                violationsTravelFee = new BigDecimal("0");
            } else {
                violationsTravelFee = (BigDecimal) detail.get("violationsTravelFee");
            }
            if (detail.get("violationsOtherFee") == null) {
                violationsOtherFee = new BigDecimal("0");
            } else {
                violationsOtherFee = (BigDecimal) detail.get("violationsOtherFee");
            }
            if (detail.get("allBreaks") == null) {
                allBreaks = new BigDecimal("0");
            } else {
                allBreaks = (BigDecimal) detail.get("allBreaks");
            }
            if (detail.get("reductionPenalty") == null) {
                reductionPenalty = new BigDecimal("0");
            } else {
                reductionPenalty = (BigDecimal) detail.get("reductionPenalty");
            }
            if (detail.get("breachContract") == null) {
                breachContract = new BigDecimal("0");
            } else {
                breachContract = (BigDecimal) detail.get("breachContract");
            }
            result.putAll(detail);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        // 判断是否逾期
        isOverdue = calculateService.isOverdue((Date) detail.get("repaymentTime"), realPaymentDate);
        if (isOverdue) {
            List<Map<String, Object>> params = new ArrayList<Map<String, Object>>(1);
            params.add(detail);
            // 计算罚息
            overdue = calculateService.calculateOverdueByRepaymentdetails(params, realPaymentDate);
            // 计算违约金
            penalty = calculateService.calculateOverduePenalty(detail, realPaymentDate);
        }
        totalAccount = ((BigDecimal) detail.get("account")).add(overdue).add(penalty).add(otherFee).add(parkingFee)
                .add(gpsUsingFee);
        // 实际应还总额
        realAccount = totalAccount.add(violationsPenalty).add(violationsTruckFee).add(violationsTravelFee)
                .add(violationsOtherFee)
                // 违约金、罚息减免
                .add(allBreaks.negate());
        // 服务费
        // .add(serviceFee);
        result.put("defaultInterest", overdue);
        result.put("penalty", penalty);
        result.put("totalAccount", totalAccount);
        result.put("realAccount", realAccount);

        result.put("violationsPenalty", violationsPenalty);
        result.put("violationsTruckFee", violationsTruckFee);
        result.put("violationsTravelFee", violationsTravelFee);
        result.put("violationsOtherFee", violationsOtherFee);

        result.put("allBreaks", allBreaks);
        result.put("breachContract", breachContract);
        result.put("reductionPenalty", reductionPenalty);

        result.put("overdue", overdue);
        result.put("penalty", penalty);
        result.put("account", (BigDecimal) detail.get("account"));
        return result;
    }

    @Override
    protected void preCheckWorkflowParams(ProjectWorkflowDataBean projectWorkflowDataBean) throws ServiceException {

    }

    @Override
    protected void preCheckCurrentWorkflowState(ProjectWorkflowDataBean projectWorkflowDataBean)
            throws ServiceException {

    }

    

    /**
     * @Title: startProcess
     * @Description: (启动新的审批流程（包括：提前还款审批、违约罚金减免、强制结清、车辆处置审批等）)
     * @param processDefinitionName
     * @param userName
     * @throws ServiceException
     *             设定文件
     * @return void 返回类型
     * @throws
     */
    private Task startProcessNoInsertProjectProcess(String processDefinitionName, String userName, Long projectId)
            throws ServiceException {

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionName(processDefinitionName).orderByProcessDefinitionVersion().desc().listPage(0, 1);

        ProcessDefinition latestProcessDefinition = processDefinitions.get(0);
        identityService.setAuthenticatedUserId(userName); // creditConsultDataBean.getUserName());
        try {
            Map<String, Object> processGlobalMap = new HashMap<String, Object>();
            processGlobalMap.put(SystemConstant.PROCESS_LAUNCHER, userName); // creditConsultDataBean.getUserName());
            // TODO FHJ 把这些值做成系统配置参数
            processGlobalMap.put(SystemConstant.SECONDARY_AUDIT_AMOUNT_THRESHOLD, SECONDARY_AUDIT_AMOUNT_THRESHOLD);
            processGlobalMap.put(SystemConstant.SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD,
                    SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD);
            processGlobalMap.put(SystemConstant.MANAGER_CONFIRMATION_AMOUNT_THRESHOLD,
                    MANAGER_CONFIRMATION_AMOUNT_THRESHOLD);

            // TODO FHJ just for testing
            TaskAssignerCenter.processDefinitionId = latestProcessDefinition.getId();

            ProcessInstance processInstance = runtimeService.startProcessInstanceById(latestProcessDefinition.getId(),
                    projectId + latestProcessDefinition.getId(), processGlobalMap); // creditConsultDataBean.getProjectId()
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

            return task;
        } catch (RDRuntimeException e) {
            // 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
            throw new ServiceException(e.getMessage(), e);
        }

    }

    /**
     * @description (启动新的审批流程（包括：展期和车辆处置审批等）)
     * @param originalProcessInstanceId  前一流程id
     * @param processDefinitionName 流程名称
     * @param userName
     * @param projectId
     * @param variablesMap 流程启动参数
     * @throws ServiceException
     * @author wtc
     */
    private List<Task> startProcess(String originalProcessInstanceId, String processDefinitionName, String userName,
            Long projectId, Map<String, Object> variablesMap) throws ServiceException {
        try {
        	// 锁定还款记录
            lockRepayLoanInfo(originalProcessInstanceId, projectId);
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionName(processDefinitionName).orderByProcessDefinitionVersion().desc().listPage(0, 1);

            ProcessDefinition latestProcessDefinition = processDefinitions.get(0);
            identityService.setAuthenticatedUserId(userName);
            if (variablesMap == null) {
                variablesMap = new HashMap<String, Object>();
            }
        	
            variablesMap.put(SystemConstant.PROCESS_LAUNCHER, userName);
            // TODO FHJ 把这些值做成系统配置参数
            variablesMap.put(SystemConstant.SECONDARY_AUDIT_AMOUNT_THRESHOLD, SECONDARY_AUDIT_AMOUNT_THRESHOLD);
            variablesMap.put(SystemConstant.SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD,
                    SECONDARY_FINANCIAL_CONFIRMATION_AMOUNT_THRESHOLD);
            variablesMap.put(SystemConstant.MANAGER_CONFIRMATION_AMOUNT_THRESHOLD,
                    MANAGER_CONFIRMATION_AMOUNT_THRESHOLD);
            // 分之流程的根流程id
            variablesMap.put(SystemConstant.ORIGINAL_PROCESSINSTANCEID, originalProcessInstanceId);
            
            // TODO FHJ just for testing
            TaskAssignerCenter.processDefinitionId = latestProcessDefinition.getId();

            ProcessInstance processInstance = runtimeService.startProcessInstanceById(latestProcessDefinition.getId(),
                    projectId + latestProcessDefinition.getId(), variablesMap);
            List<Task> task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();

            // 维护 项目流程关系表 向里面插入数据
            PubProjectProcess ppp = pubProjectProcessDao.getProjectProcessByProjectId(projectId);
            PubProjectProcess pubProjectProcess = new PubProjectProcess();
            
            //插入下一步审批人
	        PubLoanprocess pubLoanprocess = new PubLoanprocess();
	        pubLoanprocess.setCreateTime(new Date());
           	pubLoanprocess.setIsDelete(SystemConstant.IS_DELETE_NORMAL);
           	pubLoanprocess.setProcessInstanceId(task.get(0).getProcessInstanceId());
           	pubLoanprocess.setProjectId(projectId);
           	
           	if (variablesMap.get("extendedFlag") != null) {
           		if (((String)variablesMap.get("extendedFlag")).equals("0")) {				
               		pubLoanprocess.setProcessState("usertask-extension-customer-survey");//展期重新客户调查
    			}else{
    				pubLoanprocess.setProcessState("usertask-extension-collateralAssess");//展期重新评估房产信息
    			}
			}
           	
           	pubLoanprocess.setTaskId(task.get(0).getProcessInstanceId());
    		String nextAssignee = "";
			String nextAssigneeName = "";
			if(CollectionUtils.isNotEmpty(task)){
				for(int i=0;i<task.size();i++){
					Task task1 = task.get(i);
					if (task1!=null) {			
						try {
							SysUser user = sysUserDao.getUserByUserName(task1.getAssignee());
							//得到下一个流程的任务处理人
							nextAssignee += task1.getAssignee();
							nextAssigneeName += user.getName();
						} catch (PersistentDataException e) {
							throw new ServiceException(e);
						}
						if(i < task.size()-1){
							nextAssignee += ",";
							nextAssigneeName += ",";
						}
					}
				}	
			}
			pubLoanprocess.setNextAssignee(nextAssignee);	
			pubLoanprocess.setNextAssigneeName(nextAssigneeName);
           	pubLoanprocessDao.insert(pubLoanprocess);  

            if (processDefinitionName.equals("car_loan_extension")) {
                // 展期 最大展期基础上 +1
                Byte extensionNumber = 1;
                if (ppp != null && ppp.getExtensionNumber() != null) {
                    extensionNumber = (byte) (ppp.getExtensionNumber() + 1);
                }
                pubProjectProcess.setExtensionNumber(extensionNumber);
                pubProjectProcess.setProcessType((byte) 0); // 展期和非展期 都属正常流程类型
                                                            // (代码值为0)
                pubProjectProcess.setProjectId(projectId);
                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());

            } else if (processDefinitionName.equals("vehicle_disposal")) {
                // 车辆处置 展期数为现最大展期数
                Byte extensionNumber = 0;
                if (ppp != null && ppp.getExtensionNumber() != null) {
                    extensionNumber = ppp.getExtensionNumber();
                }
                pubProjectProcess.setExtensionNumber(extensionNumber);
                pubProjectProcess.setProcessType((byte) 5);
                pubProjectProcess.setProjectId(projectId);
                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());

            }

            else if (processDefinitionName.equals(WorkFlowConstant.PROCESS_NAME_EARLY_REPAYMENT_APPROVAL)) {
                // 提前还款 展期数为现最大展期数
                Byte extensionNumber = 0;
                if (ppp != null && ppp.getExtensionNumber() != null) {
                    extensionNumber = ppp.getExtensionNumber();
                }
                pubProjectProcess.setExtensionNumber(extensionNumber);
                pubProjectProcess.setProcessType((byte) 1);
                pubProjectProcess.setProjectId(projectId);
                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());

            } else if (processDefinitionName.equals(WorkFlowConstant.PROCESS_NAME_PENALTY_RELIEF_APPROVAL)) {
                // 违约罚息减免 展期数为现最大展期数
                Byte extensionNumber = 0;
                if (ppp != null && ppp.getExtensionNumber() != null) {
                    extensionNumber = ppp.getExtensionNumber();
                }
                pubProjectProcess.setExtensionNumber(extensionNumber);
                pubProjectProcess.setProcessType((byte) 2);
                pubProjectProcess.setProjectId(projectId);
                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());

            }

            else if (processDefinitionName.equals("compulsory_settlement")) {
                // 强制结清 展期数为现最大展期数
                Byte extensionNumber = 0;
                if (ppp != null && ppp.getExtensionNumber() != null) {
                    extensionNumber = ppp.getExtensionNumber();
                }
                pubProjectProcess.setExtensionNumber(extensionNumber);
                pubProjectProcess.setProcessType((byte) 3);
                pubProjectProcess.setProjectId(projectId);
                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());

            }

            else {
                Byte extensionNumber = 0;
                if (ppp != null && ppp.getExtensionNumber() != null) {
                    extensionNumber = ppp.getExtensionNumber();
                }
                pubProjectProcess.setExtensionNumber(extensionNumber);
                pubProjectProcess.setProcessType((byte) 0);
                pubProjectProcess.setProjectId(projectId);
                pubProjectProcess.setProcessInstanceId(task.get(0).getProcessInstanceId());// processInstance.getId());

            }

            insertProjectProcess(pubProjectProcess);
            return task;
        } catch (RDRuntimeException e) {
            // 在这里捕获所有在监听器中throw的runtime异常，并且统一把它们包装成ServiceException从Service层抛出去
        	log.error("【贷后变更申请失败】" + e.getMessage(),e);
            throw new ServiceException("分配任务失败，请检查人员配置", e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map penaltyReductionRequisitionSubmit(Long processInstanceId, PubRepaymentdetail repaymentdetail,
            LoanChangeDataBean loanChangeDataBean, String userName) throws Exception {
        Map map = new HashMap();
        map.put("processInstanceId", processInstanceId);

        List<PlBorrow> borrowList = plBorrowService.getPageListByMap(map);
        if (borrowList == null) {
            throw new RuntimeException("missing project information");
        }

        Map param = new HashMap();
        param.put("processInstanceId", processInstanceId);
        param.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);

        List<PubProcessBranching> list = pubProcessBranchingService.getPageListByMap(param);

        // 将前一个分支流程设置成非活动状态
        if (list != null && list.size() > 0) {
            for (PubProcessBranching branching : list) {
                branching.setIsActive((byte) 0);
                branching.setModifier(userName);
                branching.setModifyTime(new Date());
                pubProcessBranchingDao.update(branching);
            }
        }

        // 新生成一个分支流程 并插入分支流程表
        PubProcessBranching pb = new PubProcessBranching();
        pb.setProcessInstanceId(Long.toString(processInstanceId));

//        pb.setMakeRepaymentTime(loanChangeDataBean.getMakeRepaymentTime());
        pb.setRemark2(loanChangeDataBean.getRemark2());
        //记录之前的罚息减免金额到流程参数中，如果审批失败，将恢复
        Map<String,Object> preParams = new HashMap<String,Object>();
        PubRepaymentdetail detail = repaymentdetailService.getItemInfoById(repaymentdetail.getId());
        preParams.put("repaymentdetail_id", detail.getId());
        preParams.put("pre_breachContract", detail.getBreachContract());
        preParams.put("pre_reductionPenalty", detail.getReductionPenalty());
        preParams.put("pre_allBreaks", detail.getAllBreaks());
        
        detail.setBreachContract(repaymentdetail.getBreachContract());
        detail.setReductionPenalty(repaymentdetail.getReductionPenalty());
        detail.setAllBreaks(repaymentdetail.getAllBreaks());
        repaymentdetailService.update(detail);
       
        List<Task> task = startProcess(processInstanceId.toString(), WorkFlowConstant.PROCESS_NAME_PENALTY_RELIEF_APPROVAL, userName,
                loanChangeDataBean.getProjectId(), preParams);
        // 几个小流程，都设置为 贷后分支流程类别 需要在任务中进行统一处理。
        pb.setTaskId(task.get(0).getId());
        pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
        pb.setBranchingProcessType(WorkFlowConstant.BRANCHING_PROCESS_TYPE_PENALTY_RELIEF);
        pb.setRepaymentProcess((byte) 0);
        pb.setProcessingOpinion("noprocess");
        pb.setProjectId(loanChangeDataBean.getProjectId());

        pb.setCreateTime(new Date());
        pb.setCreator(userName);

        pb.setIsDelete((byte) 0);
        pb.setIsActive((byte) 1);
        pb.setProcessStatus(WorkFlowConstant.PROCESS_STATUS_PENALTY_RELIEF_APPROVAL);
        pubProcessBranchingDao.insert(pb);
       
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG,Constant.OPERATION_SUCCESS);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map forceSettleUpRequisitionSubmit(Long processInstanceId, LoanChangeDataBean carLoanChangeDataBean,
            String userName) throws Exception {
        // 检查processInstanceId借款表中是否存在
        Map map = new HashMap();
        map.put("processInstanceId", processInstanceId);
        List<PlBorrow> borrowList =  plBorrowService.getPageListByMap(map);
        if (borrowList == null) {
            throw new RuntimeException("missing project information");
        }

        PubProcessBranching pb = null;
        Map param = new HashMap();
        param.put("processInstanceId", processInstanceId);
        param.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);

        List<PubProcessBranching> list =  pubProcessBranchingService.getPageListByMap(param);

        // 将前一个分支流程设置成非活动状态
        if (list != null && list.size() > 0) {
            for (PubProcessBranching branching : list) {
                branching.setIsActive((byte) 0);
                branching.setModifier(userName);
                branching.setModifyTime(new Date());
                pubProcessBranchingDao.update(branching);
            }
        }

        // 新生成一个分支流程 并插入分支流程表
        pb = new PubProcessBranching();
        pb.setProcessInstanceId(Long.toString(processInstanceId));

        pb.setMakeRepaymentTime(carLoanChangeDataBean.getMakeRepaymentTime());
        pb.setRemark1(carLoanChangeDataBean.getRemark1());

        List<Task> task = startProcess(processInstanceId.toString(), "compulsory_settlement", userName,
                carLoanChangeDataBean.getProjectId(), null);
        // 几个小流程，都设置为 贷后分支流程类别 需要在任务中进行统一处理。
        pb.setTaskId(task.get(0).getId());
        pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
        pb.setBranchingProcessType((byte) 3);
        pb.setRepaymentProcess((byte) 0);
        pb.setProcessingOpinion("noprocess");
        pb.setProjectId(carLoanChangeDataBean.getProjectId());

        pb.setCreateTime(new Date());
        pb.setCreator(userName);

        pb.setIsDelete((byte) 0);
        pb.setIsActive((byte) 1);

        pb.setDefaultSituation(new BigDecimal(carLoanChangeDataBean.getDefaultSituation()) );
        pb.setProcessPeriod(carLoanChangeDataBean.getPeriod());
        pb.setProcessStatus("taskuser-compulsory_settlement-approval");
        pubProcessBranchingDao.insert(pb);

        Map params = new HashMap();
        params.put("processInstanceId", processInstanceId);
        // params.put("taskId", taskId);
        if (carLoanChangeDataBean.getPeriod() != null) {
            params.put("period", carLoanChangeDataBean.getPeriod());
        } else {
            log.info("程序错误，强制结清提交 period为空");
        }

        List<PubRepaymentdetail> repaymentdetailList = repaymentdetailService.getPageListByMap(params);// getRepaymentdetailById(carLoanChangeDataBean.getRepaymentDetailId());
        PubRepaymentdetail repaymentdetail = null;
        if (repaymentdetailList != null && repaymentdetailList.size() > 0) {
            repaymentdetail = repaymentdetailList.get(0);
        } else {
            throw new RuntimeException("missing repaymentdetail information");
        }

        repaymentdetail.setViolationsOtherFee(carLoanChangeDataBean.getViolationsOtherFee());
        repaymentdetail.setViolationsPenalty(carLoanChangeDataBean.getViolationsPenalty());
        repaymentdetail.setViolationsTotalAmount(carLoanChangeDataBean.getViolationsTotalAmount());
        repaymentdetail.setViolationsTravelFee(carLoanChangeDataBean.getViolationsTravelFee());
        repaymentdetail.setViolationsTruckFee(carLoanChangeDataBean.getViolationsTruckFee());

        repaymentdetailService.update(repaymentdetail);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG,
                Constant.OPERATION_SUCCESS);
        return res;
    }

    /**
     * @description 提前归还本金金额
     * @param processInstanceId
     * @return
     * @author wtc
     * @return BigDecimal
     * @since 1.0.0
     */
    private BigDecimal getCapital(String processInstanceId) throws ServiceException {
        PlFeeinfo feeinfo = getFeeinfo(processInstanceId);
        // TODO
//        Byte productType = Byte.parseByte(feeinfo.getProductType());
//        CalculateLoanMoneyService service = HenhaodaiCalculateLoanMoneyFactory
//                .getCalculateLoanMoneyService(productType);
//        return service.calculateCapitalMoney(processInstanceId);
        return null;
    }

    /**
     * @description 获取尚未还款的本金和
     * @param processInstanceId
     * @return
     * @throws ServiceException
     * @author wtc
     * @return BigDecimal
     * @since 1.0.0
     */
    private BigDecimal calculateLoanAmount(String processInstanceId) throws ServiceException {
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("processInstanceId", processInstanceId);
        List<Map<String, Object>> details = null; // TODO repaymentdetailDao.getUnPayedRepaymentdetails(searchParam);
        BigDecimal capital = new BigDecimal(0);
        for (Map<String, Object> detail : details) {
            BigDecimal c = new BigDecimal(detail.get("capital").toString());
            capital = capital.add(c);
        }
        return capital;
    }

    private PlFeeinfo getFeeinfo(String processInstanceId) throws ServiceException {
        return plFeeinfoDao.getItemInfoByProcessInstanceId(processInstanceId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map extensionApplicationSubmit(LoanChangeDataBean carLoanChangeDataBean, PlBorrow plBorrow, String processInstanceId, Long projectId,
            String userName, List<Long> roleList) throws Exception {
    	Map<String,Object> searchMap = new HashMap<String,Object>();
    	searchMap.put("processInstanceId", processInstanceId);
    	PubRepaymentdetail minUnPayedRepaymentdetai = repaymentdetailDao.getMinUnPayedRepaymentdetail(searchMap);
    	int minPeriod = minUnPayedRepaymentdetai.getPeriod();
   /* 	if((minPeriod-1) != plBorrow.getTimeLimit()){
    		throw new ServiceException("最后一期才能展期！");
    	}*/
        Map map = new HashMap();
        map.put("projectId", projectId);
        List<PlBorrow> list = plBorrowService.getPageListByMap(map);
        if (list == null) {
            throw new ServiceException("missing project information");
        }
        
        Map<String, Object> variablesMap = new HashMap<String, Object>();
        variablesMap.put("extendedFlag", carLoanChangeDataBean.getExtendedFlag());//判断展期是否走并行节点
        // 说明新的展期申请，只在pub_project_process中进行标识 type '类型：0否，1展期'
        List<Task> task = startProcess(processInstanceId, "extension", userName, projectId, variablesMap); // NoInsertProjectProcess
        // 新增贷后变更分支流程信息
        PubProcessBranching ppb = new PubProcessBranching();
        ppb.setBranchingProcessId(task.get(0).getProcessInstanceId());
        // 展期
        ppb.setRepayCapitalAmount(plBorrow.getAccount());
        ppb.setBranchingProcessType((byte) 6);
        ppb.setIsActive((byte) 1);
        ppb.setCreateTime(new Date());
        ppb.setCreator(userName);
        ppb.setRemark1(carLoanChangeDataBean.getRemark1());
        ppb.setRemark2("展期");
        ppb.setExtensionCount(carLoanChangeDataBean.getExtensionCount());
        ppb.setProcessingOpinion(carLoanChangeDataBean.getExtendedFlag());
        ppb.setProcessInstanceId(processInstanceId);
        if (carLoanChangeDataBean.getExtendedFlag().equals("0")) {			
        	ppb.setProcessStatus("usertask-extension-customer-survey");//展期重新客户调查
		}else{
			ppb.setProcessStatus("usertask-extension-collateralAssess");//展期重新评估房产信息
		}
        ppb.setProjectId(projectId);
        ppb.setExtensionAmount(carLoanChangeDataBean.getExtensionAmount());
        ppb.setExtensionRate(carLoanChangeDataBean.getExtensionRate());
        ppb.setExtensionFee(carLoanChangeDataBean.getExtensionFee());
        ppb.setExtensionReturnfeeRate(carLoanChangeDataBean.getExtensionReturnfeeRate());
        pubProcessBranchingDao.insert(ppb);
        // 修改咨询表的状态
        PlConsult consult = plConsultService.getPlConsultByProjectId(projectId);
        if (carLoanChangeDataBean.getExtendedFlag().equals("0")) {			
        	consult.setStatus(USERTASK_EXTENSION_CUSTOMER_SURVEY);
		}else{
			consult.setStatus(USERTASK_EXTENSION_COLLATERALASSESS);
		}
        consult.setModifyTime(new Date());
        plConsultService.update(consult);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG,
                Constant.OPERATION_SUCCESS);
        return res;
    }

    /**
     * @description 用户角色验证 当前用户id列表(roleList)中是否有我们需要的角色
     * @param needSysRoleNid
     * @param roleList
     * @return
     * @throws ServiceException
     * @author lukf
     * @return boolean
     * @since 1.0.0
     */
    @Override
    public boolean isNeedUserRole(String needSysRoleNid, List<Long> roleList) throws ServiceException {
        Map roleParams = new HashMap();
        roleParams.put("nid", needSysRoleNid); // "customerServiceStaff");
        List<SysRole> roleL = sysRoleService.getListByMap(roleParams);// getRgetRolePageList(roleParams);
        if (roleL == null || roleL.size() == 0) {
            throw new NullPointerException();
        }
        SysRole customerServiceRole = (SysRole) roleL.get(0);

        boolean isNeedUserRole = false;
        for (Long roleId : roleList) {
            if (roleId.longValue() == customerServiceRole.getId().longValue()) {
                isNeedUserRole = true;
            }
        }
        return isNeedUserRole;
    }

    @Override
    public Long queryExtensionSubmited(Map params) throws PersistentDataException {
        // TODO 展期判断
        List<Map> list =  null;// TODO postLoanChangeDao.simpleQuery("RepaymentApprove.queryExtensionSubmited", params);
        Map map = new HashMap();
        if (list != null && list.size() > 0) {
            map = list.get(0);
            if (map.get("count") != null) {
                Long existedRecordCount = (Long) map.get("count");
                return existedRecordCount;
            }
            ;
        }
        // runtimeService.createProcessInstanceQuery().processDefinitionKey();
        return 0L;
    }

    @Override
    public List<Map> queryForceSettleUp(Integer pageNum, Integer pageSize, Map<String, Object> params)
            throws ServiceException {
        return postLoanChangeDao.queryForceSettleUp(pageNum, pageSize, params);
    }

    @Override
    public List<Map> queryPenaltyReductionList(Integer pageNum, Integer pageSize,String processInstanceId,Boolean isBranch) throws ServiceException {
        Map<String,Object> params=new HashMap<String,Object>();
        Map<String, Object> processMap=getProcessOrBranchProcessMap(processInstanceId,isBranch);
        String mainProcessInstanceId=processMap.get("processInstanceId").toString();
        params.put("processInstanceId", mainProcessInstanceId);
        List<Map> pager = postLoanChangeDao.queryPenaltyReductionList(pageNum, pageSize, params);
        CalculateService calculateService = null;// TODO getCalculateService(mainProcessInstanceId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        for (Map<String, Object> map : pager) {
            try {
                String repaymentTimeStr = map.get("repaymentTime").toString();
                Date repaymentTime = sdf.parse(repaymentTimeStr);
                map.put("repaymentTime", repaymentTime);
                if (calculateService.isOverdue(repaymentTime, now)) {
                    // 如果逾期，则计算违约金罚息等
                    Integer overdueDays = calculateService.calculateOverdueDays(repaymentTime, now);
                    BigDecimal penalty = calculateService.calculateOverduePenalty(map, now);
                    List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
                    l.add(map);
                    BigDecimal defaultInterest = calculateService.calculateOverdueByRepaymentdetails(l, now);
                    map.put("overdueDays", overdueDays);
                    map.put("penalty", penalty.setScale(2, BigDecimal.ROUND_HALF_UP));
                    map.put("defaultInterest", defaultInterest.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        return pager;

    }

//    private CalculateService getCalculateService(String processInstanceId) throws ServiceException {
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("processInstanceId", processInstanceId);
//        CalculateService calculateService;
//        
//        PlFeeinfo feeinfo = plFeeinfoDao.getItemInfoByProcessInstanceId(processInstanceId);
//		PlBorrow borrow = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
//		calculateService =  null;// TODO chargingStrategy.getChargingStrategy(borrow, Byte.parseByte(feeinfo.getProductType()));
//		
//        return calculateService;
//    }

    @Transactional(rollbackFor = Exception.class)
//    @Override
    public Map<String, Object> vehicleDisposalStart(Long processInstanceId, String userName,
    		DisposalDataBean databean) throws Exception{
        ParamChecker.checkEmpty(processInstanceId, "processInstanceId");
        ParamChecker.checkEmpty(userName, "用户名");

        Map map = new HashMap();
        map.put("processInstanceId", processInstanceId);
        List<PlBorrow> borrowList =  plBorrowService.getPageListByMap(map);
        if (borrowList == null) {
            throw new RuntimeException("missing project information");
        }

        PubProcessBranching pb = null;
        Map param = new HashMap();
        param.put("processInstanceId", processInstanceId);
        param.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);

        List<PubProcessBranching> list =  pubProcessBranchingService.getPageListByMap(param);

        // 将前一个分支流程设置成非活动状态
        if (list != null && list.size() > 0) {
            for (PubProcessBranching branching : list) {
                branching.setIsActive((byte) 0);
                branching.setModifier(userName);
                branching.setModifyTime(new Date());

                pubProcessBranchingDao.update(branching);
            }
        }

        // 新生成一个分支流程 并插入分支流程表
        Long projectId = pubProjectProcessDao.getItemByProcessInstanceId(processInstanceId).getProjectId();
        List<Task> task = startProcess(processInstanceId.toString(), "vehicle_disposal", userName, projectId, null);

        pb = new PubProcessBranching();
        pb.setProcessInstanceId(processInstanceId.toString());
        pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
        pb.setBranchingProcessType((byte) 5);
        pb.setProcessingOpinion("noprocess");
        pb.setCreateTime(new Date());
        pb.setCreator(userName);
        //车辆处置原因
        pb.setRemark1(databean.getRemark());
        // 其他金额
        pb.setOtherAmount(databean.getOtherAmount());
        pb.setProcessInstanceId(Long.toString(processInstanceId));

        // task.setCategory(SystemConstant.PROCESS_CATEGORY_AFTER_LOAN_BRANCHING);
        // 几个小流程，都设置为 贷后分支流程类别 需要在任务中进行统一处理。
        pb.setTaskId(task.get(0).getId());
        pb.setBranchingProcessId(task.get(0).getProcessInstanceId());
        pb.setBranchingProcessType((byte) 5);
        // pb.setRepaymentProcess((byte) 5);
        pb.setProcessingOpinion("noprocess");
        pb.setProjectId(projectId);

        pb.setCreateTime(new Date());
        pb.setCreator(userName);

        pb.setIsDelete((byte) 0);
        pb.setIsActive((byte) 1);
        pb.setProcessStatus("taskuser-pledge-estimation");
        pubProcessBranchingDao.insert(pb);

        // 修改咨询表的状态
        PlConsult consult =  plConsultService.getPlConsultByProjectId(projectId);
        consult.setStatus("taskuser-pledge-estimation");
        consult.setModifyTime(new Date());
        plConsultService.update(consult);

        // 新增车辆处置记录  TODO 
//        CarDisposalLog item = new CarDisposalLog();
//        item.setProjectId(projectId);
//        item.setProcessInstanceId(task.get(0).getProcessInstanceId());
//        // 车辆处置申请原因
//        item.setReason(databean.getReason());
//        item.setCreateTime(new Date());
//        item.setCreator(userName);
//        item.setIsDelete((byte) 0);
//
//        houseDisposalLogDao.insert(item);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG,
                Constant.OPERATION_SUCCESS + "!");
        return res;
    }

    @Override
    public Map<String, Object> queryDecisionInformation(Long processInstanceId, String processState)
            throws ServiceException, PersistentDataException {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("processInstanceId", processInstanceId);
        map.put("processState", processState);
        List<PubLoanprocess> loanlist =  pubLoanprocessDao.getPageListByMap(map);
        PubLoanprocess Loanprocess = (loanlist == null) ? new PubLoanprocess() : loanlist.get(0);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("processInstanceId", processInstanceId);

        List<DisposalLog> list = null; // TODO disposalLogDao.getPageListByMap(paramMap);
        DisposalLog carDisposalLog = list.get(0);
        DisposalDataBean carDisposalDataBean = new DisposalDataBean();
        carDisposalDataBean.setProcessStateCode(Loanprocess.getProcessState());
        carDisposalDataBean.setRemarkComment(Loanprocess.getRemark());
        carDisposalDataBean.setNextStep(Loanprocess.getType());

        carDisposalDataBean.setPricingAmount(carDisposalLog.getPricingAmount());

        Map<String, Object> res = new HashMap<String, Object>();
        res = new HashMap<String, Object>();

        res.put(Constant.RESPONSE_DATA, carDisposalDataBean);
        res.put(Constant.RESPONSE_DATA_TOTAL, 1);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        return res;
    }

    @Override
    public Map<String, Object> queryDecisionRecommendation(Map map) // Long
                                                                    // processInstanceId,
                                                                    // String
                                                                    // processState)
            throws ServiceException, PersistentDataException {

        List<PubLoanprocess> loanlist = pubLoanprocessDao.getPageListByMap(map);
        DisposalDataBean carDisposalDataBean = new DisposalDataBean();
        if (loanlist != null && loanlist.size() > 0) {
        	PubLoanprocess Loanprocess = loanlist.get(0);
            carDisposalDataBean.setProcessStateCode(Loanprocess.getProcessState());
            carDisposalDataBean.setRemarkComment(Loanprocess.getRemark());
            carDisposalDataBean.setNextStep(Loanprocess.getType());
        }

        Map<String, Object> res = new HashMap<String, Object>();
        res = new HashMap<String, Object>();

        res.put(Constant.RESPONSE_DATA, carDisposalDataBean);
        res.put(Constant.RESPONSE_DATA_TOTAL, 1);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        return res;
    }

    @Override
    public Map<String, Object> queryDispositionRegister(Integer pageNum, Integer pageSize, Map<String, Object> params)
            throws ServiceException, PersistentDataException {
        List<DisposalLog> disposalList =  null;// TODO houseDisposalLogDao.getItemListByMap(params, "CarDisposalLog");

        DisposalDataBean carDisposalDataBean = new DisposalDataBean();
        if (disposalList != null && disposalList.size() > 0) {
            DisposalLog carDisposalLog = disposalList.get(0);
            carDisposalDataBean.setCompanyName(carDisposalLog.getCompanyName());
            carDisposalDataBean.setMobile(carDisposalLog.getMobile());
            carDisposalDataBean.setBuyAmount(carDisposalLog.getBuyAmount());
            carDisposalDataBean.setTransactionDate(carDisposalLog.getTransactionDate());
            carDisposalDataBean.setRemark(carDisposalLog.getRemark());
            carDisposalDataBean.setBranchingProcessId(carDisposalLog.getProcessInstanceId());
        }

        Map<String, Object> res = new HashMap<String, Object>();
        res = new HashMap<String, Object>();

        res.put(Constant.RESPONSE_DATA, carDisposalDataBean);
        res.put(Constant.RESPONSE_DATA_TOTAL, 1);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        return res;
    }

    @Override
    public Map<String, Object> queryAttachmentInfo(String processInstanceId, String rdBtype)
            throws PersistentDataException {
        Map params = new HashMap();
        params.put("processInstanceId", processInstanceId);
        List<DisposalLog> disposalList =  null;// TODO houseDisposalLogDao.getItemListByMap(params, "CarDisposalLog");
        List<PubAttachment> plAttachmentList = null;
        if (disposalList != null && disposalList.size() > 0) {
            DisposalLog carDisposalLog = disposalList.get(0);
            String attachIds = carDisposalLog.getAttachIds() + ",";
            String[] ids = attachIds.split(",");

            plAttachmentList =  null;// TODO plAttachmentDao.getItemListByIds(ids, "PlAttachment");
        }

        Map res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_DATA, plAttachmentList);
        res.put(Constant.RESPONSE_DATA_TOTAL,
                (plAttachmentList != null && plAttachmentList.size() > 1) ? plAttachmentList.size() : 0);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

        return res;
    }

    @Override
    public Map<String, Object> queryProcessInstanceByTask(String taskId, String isCompleted)
            throws PersistentDataException {
        return postLoanChangeDao.queryProcessInstanceByTask(taskId, isCompleted);
    }

    @Override
    public Integer queryRepaymentDetailCurrentPeriod(String processInstanceId) throws PersistentDataException {
        return postLoanChangeDao.queryRepaymentDetailCurrentPeriod(processInstanceId);
    }

    @Override
    public Map<String, Object> querybranchingProcessIdByTask(String taskId, String isCompleted)
            throws PersistentDataException {
        return postLoanChangeDao.querybranchingProcessIdByTask(taskId, isCompleted);
    }

    /**
     * @description 锁定活动的还款记录
     * @param projectId
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since 1.0.0
     */
    private void lockRepayLoanInfo(String processInstanceId, Long projectId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("processInstanceId", processInstanceId);
        PlBorrow borrow = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
		params.clear();
		params.put("borrowId", borrow.getId());
		Map<String, Object> pr = pubRepaymentDao.getItemByMap(params);
		params.clear();
		params.put("id", pr.get("id"));
		params.put("isLocked", 1);
		pubRepaymentDao.updatePubRepayloaninfoById(params);
    }

    /**
     * @description 贷后变更重复校验
     * @param processInstanceId
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since 1.0.0
     */
    private void repeatValidate(PubRepayloaninfo pr) throws ServiceException {
        if (pr.getIslocked() == 1) {
            throw new ServiceException("该记录处于贷后变更中或者已失效");
        }
    }

    

    /**
     * @description 根据传入的实例ID来返回分支流程ID和主流程ID（如果传入的是展期流程ID，那么当且仅当该展期流程成功结束后才判定为主流程，否则判定为分支流程。针对展期的查看详情有问题）
     * @param processInstanceId
     * @return
     * @throws ServiceException
     * @author wtc
     * @return Map<String,Object>
     * @since  1.0.0
    */
    @Override
    public Map<String, Object> getProcessOrBranchProcessMap(String processInstanceId,Boolean isBranch) throws ServiceException {
        Map<String,Object> result=new HashMap<String,Object>();
        if(isBranch!=null && isBranch == false){
            result.put("processInstanceId", processInstanceId);
        }else{
            PubProcessBranching ppb;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("branchingProcessId", processInstanceId);
            
            ppb = pubProcessBranchingDao.getItemByMap(paramMap);
            
            result.put("processInstanceId", ppb.getProcessInstanceId());
            result.put("branchingProcessId", ppb.getBranchingProcessId());
        }
        return result;
    }


    
    
    
    public Map<String, Object> getPrepayment(String processInstanceId) throws ServiceException {
    	byte currentTimeLimit = 0;
        Map<String, Object> searchParam = new HashMap<String, Object>();
        
        //查询是否有展期还款，如果有则进行展期流程提前还款
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("processInstanceId", processInstanceId);
        paramMap.put("branchingProcessType", "6");//展期
        List<PubProcessBranching> ppbList = pubProcessBranchingDao.getPageListByMap(paramMap);
        PubProcessBranching ppb = CollectionUtils.isNotEmpty(ppbList)?ppbList.get(0):null;
        if(ppb == null){
        	searchParam.put("processInstanceId", processInstanceId);
        }else{
        	searchParam.put("processInstanceId", ppb.getBranchingProcessId());
        }
        processInstanceId = (String)searchParam.get("processInstanceId");
        
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String, Object> unPayRepaymentDetail = pubRepaymentDao.getMinUnPayedRepaymentdetail(searchParam);
        if(unPayRepaymentDetail != null){
            currentTimeLimit=Byte.valueOf(unPayRepaymentDetail.get("period").toString());
        }
        // 未还款明细统计
        List<Map<String, Object>> details = pubRepaymentDao.getUnPayedRepaymentdetails(searchParam);
       
        BigDecimal capital = new BigDecimal(0);
        BigDecimal interest = new BigDecimal(0);
        //实际罚息
        BigDecimal overdue=new BigDecimal(0);
        
        for (int i = 0; i < details.size(); i++) {
            Map<String, Object> detail = details.get(i);
            // 本金
            BigDecimal detailCapital = (BigDecimal) detail.get("capital");
            // 利息
            BigDecimal detailInterest = (BigDecimal) detail.get("interest");
            //归还但改期未结清的已还金额
            if(i==0){
                BigDecimal realAccount=ObjectInitUtil.initBigDecimal((BigDecimal) detail.get("realAccount"));
                //判断本金
                if(realAccount.subtract(detailCapital).setScale(0,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0))>=0){
                    realAccount=realAccount.subtract(detailCapital);
                    detailCapital=new BigDecimal(0);
                }else{
                    detailCapital=detailCapital.subtract(realAccount);
                    realAccount=new BigDecimal(0);
                }
            }
            capital = capital.add(detailCapital);
        }
        capital = capital.setScale(0, BigDecimal.ROUND_HALF_UP);
        // 提前还款违约金
        BigDecimal AHEAD_MONEY = felService.calculateUseFeeInfo(processInstanceId,capital,FelTypeEnum.PREPAYMENT_PENALTY.value());
        
        result.put("capital",capital);
        result.put("penalty",AHEAD_MONEY);//提前还款违约金
        result.put("currentPeriod", currentTimeLimit);
        return result;
	}
    
    @Override
    public Map<String, Object> getExtensionInfo(String processInstanceId,Boolean isBranch) throws ServiceException {
        Map<String,Object> aheadInfo=new HashMap<String,Object>();
        Map<String, Object> processInstanceMap=getProcessOrBranchProcessMap(processInstanceId,isBranch);
        //获取主流程实例id
        String mainProcessInstanceId=processInstanceMap.get("processInstanceId").toString();
        //客户贷款基本信息
        aheadInfo.putAll(getCustomerBaseInfo(mainProcessInstanceId));
        //本金
        BigDecimal capitalSum;
        Long period;
        if(processInstanceMap.get("branchingProcessId")==null){
            //主流程
            Map<String, Object> result =  null;// TODO repaymentService.getExtensionPrepayment(new Date(), mainProcessInstanceId);
            //本金
            capitalSum=(BigDecimal) result.get("capital");
            period = (Long)(result.get("currentPeriod"));
        }else{
        	
        }
        //划扣总额
        aheadInfo.put("processInstanceId", processInstanceId);
        return aheadInfo;
    }

    /**
     * @description 客户基本贷款信息
     * @param processInstanceId 主流程id
     * @return
     * @throws ServiceException
     * @author wtc
     * @return Map<String,Object>
     * @since  1.0.0
    */
    private Map<String, Object> getCustomerBaseInfo(String processInstanceId) throws ServiceException {
        Map<String, Object> info = new HashMap<String, Object>();
        Long projectId;
        PubRepayloaninfo repayinfo;
        String projectCode;
        PlBorrow borrow;
        Long extensionNumber;
        PlProduct plProduct;
        try {
            PubProjectProcess ppp = pubProjectProcessDao.getItemByProcessInstanceId(Long.parseLong(processInstanceId));
            borrow = plBorrowDao.getItemInfoByProcessInstanceId(processInstanceId);
            PubProject project = pubProjectDao.getItemInfoById(ppp.getProjectId());
            projectCode = project.getCode();
            repayinfo = pubRepayloaninfoDao.getItemInfoByProcessInstanceId(processInstanceId);
            projectId = ppp.getProjectId();
            plProduct = plProductDao.getItemInfoById(borrow.getProductId());
            PlConsult cc = plConsultService.getPlConsultByProjectId(ppp.getProjectId());
            extensionNumber = cc.getExtensionTime();
            if (extensionNumber == null) {
                extensionNumber = 0L;
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e);
        } catch (PersistentDataException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("查询失败请稍后重试");
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        	throw new ServiceException(e);
		}
        info.put("projectId", projectId);
        info.put("productName", plProduct.getName());
        info.put("projectCode", projectCode);
        // 贷款账户
        info.put("bankAccount", repayinfo.getCustomerAccount());
        info.put("timeLimit", repayinfo.getTimeLimit());
        // 借款结束时间
    	info.put("lastRepaymentDate", repayinfo.getEndRepayTime());
    	
        info.put("repaymentType", borrow.getRepaymentType());
        info.put("extensionNumber", extensionNumber);
        info.put("customerName", repayinfo.getCustomerName());
        info.put("contractNo", repayinfo.getContractNo());
        return info;
    }
    /**
     * @description 获取借款结束时间
     * @param processInstanceId
     * @return
     * @throws ServiceException
     * @author wtc
     * @return Date
     * @since 1.0.0
     */
    private Date getLastRepaymentDate(String processInstanceId) throws ServiceException {
        try {
            PubRepaymentdetail rep = null;// TODO repaymentdetailDao.queryLastRepaymentdetail(processInstanceId);
            ParamChecker.checkEmpty(rep, "最后一期还款明细");
            return rep.getRepaymentTime();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> getRepaymentAmount(Byte type, String processInstanceId,Boolean isBranch) throws ServiceException {
        if (type == 1) {
            // 提前还款
            return getAheadOfReturnLoanInfo(processInstanceId,isBranch);
        
        } else if (type == 3) {
            // 强制
            return getAheadOfReturnLoanInfo(processInstanceId,isBranch);
        } else if (type == 5) {
            // 处置
            return getVehicleDisposalInfo(processInstanceId,isBranch);
        } else if(type==6){
            return getExtensionInfo(processInstanceId,isBranch);
        }
        return null;
    }

	@Override
	public Map<String, Object> getForceSettlementInfo(String processInstanceId,Boolean isBranch)
			throws ServiceException {
		
		Map<String,Object> params = getProcessOrBranchProcessMap(processInstanceId,isBranch);
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> dSMap = null;
		Integer currentTimeLimit  = null;
		try {
			
			//如果是分支流程,查询该分支流程的违约情况
			if(params.get("branchingProcessId")!=null){
				dSMap = postLoanChangeDao.getDefaultSituation(params.get("branchingProcessId").toString());
			}
			
			//完善resultMap
			boolean flag = false;
			
			if(params.get("branchingProcessId")!=null){
				flag = isCompleteProcess(params.get("branchingProcessId").toString());//判断分支流程是否结束
			}
			
			if(!flag){
				Map<String, Object> detail =  null;// TODO repaymentdetailDao.getMinUnPayedRepaymentdetail(params);
				
				if (detail == null) {
		            throw new ServiceException("款项已结清");
		        }
		        // 最早一期未还款期数
				currentTimeLimit = (Integer) detail.get("period");
			}
			
		} catch (PersistentDataException e) {
			e.printStackTrace();
            log.error(e.getMessage(),e);
            throw new ServiceException("查询当前期数失败请稍后重试");
		} 
		
		Map<String,Object> baseMap = getCustomerBaseInfo(params.get("processInstanceId").toString());
		resultMap =  null;// TODO henhaodaiPrepaymentService.getForceSettleRepaymentAccount(new Date(),params.get("processInstanceId").toString(), carloanCalculateService);
		resultMap.putAll(baseMap);
		if(dSMap!=null){
			resultMap.putAll(dSMap);
		}
		if(currentTimeLimit!=null){
			resultMap.put("period", currentTimeLimit);
		}
		return resultMap;
	}

    @Override
    public Map<String, Object> getVehicleDisposalInfo(String processInstanceId,Boolean isBranch) throws ServiceException {
        Map<String,Object> aheadInfo=new HashMap<String,Object>();
        Map<String, Object> processInstanceMap=getProcessOrBranchProcessMap(processInstanceId,isBranch);
        //获取主流程实例id
        String mainProcessInstanceId=processInstanceMap.get("processInstanceId").toString();
        //客户贷款基本信息
        aheadInfo.putAll(getCustomerBaseInfo(mainProcessInstanceId));
        //本金
        BigDecimal capitalSum;
        //利息
        BigDecimal interestSum;
        //违约金
        BigDecimal penaltySum;
        //罚息
        BigDecimal defaultInterestSum;
        BigDecimal otherAmount=new BigDecimal(0);
        byte period;
        String remark="";
        if(processInstanceMap.get("branchingProcessId")==null){
            //主流程
            Map<String, Object> result=  null;// TODO repaymentService.getDisposalRepaymentAccount(new Date(), mainProcessInstanceId, calculateService);
            //本金
            capitalSum=(BigDecimal) result.get("capital");
            //利息
            interestSum=(BigDecimal) result.get("interest");
            //违约金
            penaltySum=(BigDecimal) result.get("penalty");
            //罚息
            defaultInterestSum=(BigDecimal) result.get("overdue");
            period=(byte)(result.get("currentPeriod"));
        }else{
            Long branchid = (Long)processInstanceMap.get("branchingProcessId");
            PubProcessBranching ppb = null;// TODO pubProcessBranchingDao.getPubProcessBranchingByBranchingProcessId(branchid);
			otherAmount=ppb.getOtherAmount();
			remark=ppb.getRemark1();
			if(isCompleteProcess(ppb.getBranchingProcessId())){
			    capitalSum=ppb.getCapitalSum();
			    interestSum=ppb.getInterestSum();
			    penaltySum=ppb.getPenaltySum();
			    defaultInterestSum=ppb.getDefaultinterestSum();
			    period=ppb.getPeriod();
			}else{
			  //主流程
			    Map<String, Object> result =  null;// TODO repaymentService.getDisposalRepaymentAccount(new Date(), mainProcessInstanceId, calculateService);
			    //本金
			    capitalSum=(BigDecimal) result.get("capital");
			    //利息
			    interestSum=(BigDecimal) result.get("interest");
			    //违约金
			    penaltySum=(BigDecimal) result.get("penalty");
			    //罚息
			    defaultInterestSum=(BigDecimal) result.get("overdue");
			    period = (byte)(result.get("currentPeriod"));
			}
        
        }
        //划扣总额
        BigDecimal needReturnTotalSum=capitalSum.add(interestSum).add(penaltySum).add(defaultInterestSum);
        aheadInfo.put("processInstanceId", processInstanceId);
        aheadInfo.put("capitalSum", capitalSum);
        aheadInfo.put("interestSum", interestSum);
        aheadInfo.put("penaltySum", penaltySum);
        aheadInfo.put("defaultInterestSum",defaultInterestSum );
        aheadInfo.put("needReturnTotalSum", needReturnTotalSum);
        aheadInfo.put("period", period);
        aheadInfo.put("otherAmount", otherAmount);
        //车辆处置原因
        aheadInfo.put("remark", remark);
        return aheadInfo;
    }
    /**
     * @description 判断流程是否结束
     * @param processInstanceId 
     * @return
     * @throws ServiceException
     * @author wtc
     * @return boolean
     * @since  1.0.0
    */
    public boolean isCompleteProcess(String processInstanceId)throws ServiceException{
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().size()<=0;
    }

    
	@Override
	public PubRepaymentdetail queryPenaltyReduction(String processInstanceId, Byte period, Boolean isBranch) throws ServiceException {
		Map<String,Object> params=new HashMap<String,Object>();
        Map<String, Object> processMap = getProcessOrBranchProcessMap(processInstanceId,isBranch);
        String mainProcessInstanceId = processMap.get("processInstanceId").toString();
        params.put("processinstanceid", mainProcessInstanceId);
        params.put("period", period);
        // 根据期次获取还款计划详情
        PubRepaymentdetail repaymentDetail = repaymentdetailDao.getItemInfoByParams(params);
        PlBorrow borrow = borrowDao.getItemInfoByProcessInstanceId(mainProcessInstanceId);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        // 计算逾期天数
        int overdueDay = calculateOverdueDays(repaymentDetail.getRepaymentTime(), now);
        
        if (overdueDay > 0) {
        	// 逾期费减免
            BigDecimal reductionPenalty;
            if (repaymentDetail.getReductionPenalty() == null) {
            	reductionPenalty = new BigDecimal("0");
            } else {
            	reductionPenalty = repaymentDetail.getReductionPenalty();
            }
            // 如果逾期，则计算违约金罚息等
        	Map<String, Object> extraParam = new HashMap<String, Object>();
        	extraParam.put(FelConstant.OVERDUE_DATE, overdueDay);
        	BigDecimal defaultInterest = felService.calculateUseFeeInfo(mainProcessInstanceId, borrow.getAccount(), FelConstant.PENALTY, extraParam);
        	repaymentDetail.setRealInterest(defaultInterest.add(reductionPenalty.negate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
		return repaymentDetail;
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
