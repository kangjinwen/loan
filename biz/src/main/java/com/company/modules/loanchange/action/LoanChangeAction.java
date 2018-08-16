package com.company.modules.loanchange.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PlBorrowDao;
import com.company.common.dao.PubRepayloaninfoDao;
import com.company.common.dao.PubRepaymentdetailDao;
import com.company.common.domain.PlBorrow;
import com.company.common.domain.PubProcessBranching;
import com.company.common.domain.PubRepayloaninfo;
import com.company.common.domain.PubRepaymentdetail;
import com.company.common.service.PlBorrowService;
import com.company.common.service.PubProcessBranchingService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.NumberUtil;
import com.company.common.utils.ObjectTool;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.loanchange.domain.DisposalDataBean;
import com.company.modules.loanchange.domain.LoanChangeDataBean;
import com.company.modules.loanchange.service.PostLoanChangeService;

/**
 * 贷后变更
 * @author JDM
 * @version 1.0
 * @since 2015年4月3日
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/modules/LoanChange")
public class LoanChangeAction extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(LoanChangeAction.class);

	@Autowired
	private PostLoanChangeService postLoanChangeService;
	@Autowired
	private PlBorrowService plBorrowService;
	@Autowired
	private PlBorrowDao plBorrowDao;
	@Autowired
	private PubProcessBranchingService pubProcessBranchingService;
	@Autowired
	private PubRepaymentdetailDao repaymentdetailDao;
	@Autowired
	private PubRepayloaninfoDao pubRepayloaninfoDao;
	@Autowired
	IdentityService identityService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;

    /**
     * 贷后变更待申请列表
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @param searchParams
     * @throws ServiceException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @RequestMapping("/pendingApplications")
    public void pendingApplications(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "pageNum",required=true) int pageNum,
            @RequestParam(value = "pageSize",required=true) int pageSize,
            @RequestParam(required = false) String searchParams) throws ServiceException{
    	
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		
        if (StringUtils.isNotEmpty(searchParams)) {
        	paramMap.putAll(JsonUtil.parse(searchParams, Map.class));
        }
        List<String> coverdOffices = loanChangeGetCoverdOffices(getLoginUser(request));
        if(coverdOffices.size() > 0){
        	paramMap.put("coverdOffices", coverdOffices);
        }
        
        PageHelper.startPage(pageNum,pageSize);
        List<Map> result = postLoanChangeService.pendingApplicationList(paramMap);
        PageInfo<Map> page = new PageInfo<Map>(result);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * 贷后变更申请记录列表
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @param searchParams
     * @throws IOException
     */
    @RequestMapping("/postLoanChangeList")
    public void postLoanChangeList(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value = "pageNum",required=true) int pageNum,
            @RequestParam(value = "pageSize",required=true) int pageSize,
            @RequestParam(required = false) String searchParams
            ) throws ServiceException{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<>();
		
        if (StringUtils.isNotEmpty(searchParams)) {
        	paramMap.putAll(JsonUtil.parse(searchParams, Map.class));
        }
        List<String> coverdOffices = loanChangeGetCoverdOffices(getLoginUser(request));
        if(coverdOffices.size()>0){
        	paramMap.put("coverdOffices", coverdOffices);
        }
        
        PageHelper.startPage(pageNum,pageSize);
        List<Map> result = postLoanChangeService.postLoanChangeList(paramMap);
        PageInfo<Map> page = new PageInfo<Map>(result);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * 获取提前还款信息
     * @param request
     * @param response
     * @param processInstanceId 流程id
     * @param isBranch	是否是分支流程,项目列表为false,申请记录为true.
     * @throws ServiceException
     */
    @RequestMapping("/getAheadOfReturnLoanInfo")
    public void getAheadOfReturnLoanInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(name="processInstanceId",required=true) String processInstanceId,
            @RequestParam(name="isBranch",required=true)Boolean isBranch) throws ServiceException {
        Map<String, Object> result = postLoanChangeService.getAheadOfReturnLoanInfo(processInstanceId,isBranch);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, result);
        ServletUtils.writeToResponse(response, res);
    }
    
    
    /**
     * @description 提前还款提交
     * @param request
     * @param response
     * @param processInstanceId
     * @param branchingProcessType
     * @param serviceVariables
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since 1.0.0
     */
    @RequestMapping("/aheadofReturnLoanSubmit.htm")
    public void aheadofReturnLoanSubmit(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) Long processInstanceId,
            @RequestParam(value = "serviceVariables", required = false) String serviceVariables)
            throws ServiceException {
        log.info("开始执行     LoanChangeAction-----------aheadofReturnLoanSubmit()");
        isBranchProcessRepeatSubmitNew(processInstanceId);

        LoanChangeDataBean loanChangeDataBean = JsonUtil.parse(serviceVariables,LoanChangeDataBean.class);
        String userName = getLoginUserName(request);
        // 调整 返回下一步的任务人
        Map<String, Object> res = null;
        try {
            res = postLoanChangeService.aheadofReturnLoanSubmit(processInstanceId, loanChangeDataBean, userName);
        } catch (PersistentDataException e) {
            log.info("error", e);
            throw new ServiceException(e.getMessage());
        }
        ServletUtils.writeToResponse(response, res);
    }
    
    
    
    /**
     * 贷后变更罚息减免申请：(查询逾期罚息列表)
     * @param pageNum
     * @param pageSize
     * @param processInstanceId
     * @param isBranch
     * @param request
     * @param response
     * @throws ServiceException
     */
//    @RequestMapping("/queryPenaltyReductionList.htm")
//    public void queryPenaltyReductionList(
//    		@RequestParam(value = "pageNum",required=true) int pageNum,
//            @RequestParam(value = "pageSize",required=true) int pageSize,
//            @RequestParam(value = "processInstanceId", required = false)String processInstanceId,
//            @RequestParam(value="isBranch", required = false)Boolean isBranch,
//            HttpServletRequest request,HttpServletResponse response) throws ServiceException {
//    	
//    	PageHelper.startPage(pageNum, pageSize);
//        List<Map> list = postLoanChangeService.queryPenaltyReductionList(pageNum,pageSize, processInstanceId,isBranch);
//        for(Map<String,Object> map:list){
//            NumberUtil.roundBigDecimal(map);
//        }
//        PageInfo<Map> page = new PageInfo<Map>(list);
//        Map<String, Object> res = new HashMap<String, Object>();
//        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
//        res.put(Constant.RESPONSE_DATA, page.getList());
//        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
//        ServletUtils.writeToResponse(response, res);
//    }
    
    /**
     * 贷后变更罚息减免申请
     * @param period 期次
     * @param processInstanceId 流程id
     * @param isBranch 是否是分支流程,项目列表为false,申请记录为true.
     * @param request
     * @param response
     * @throws ServiceException
     */
    @RequestMapping("/queryPenaltyReduction.htm")
    public void queryPenaltyReduction(
    		@RequestParam(value = "period",required = true) Byte period,
            @RequestParam(value = "processInstanceId", required = true)String processInstanceId,
            @RequestParam(value="isBranch", required = true)Boolean isBranch,
            HttpServletRequest request,HttpServletResponse response) throws ServiceException {
    	
        PubRepaymentdetail repaymentDetail = postLoanChangeService.queryPenaltyReduction(processInstanceId,period,isBranch);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        returnMap.put(Constant.RESPONSE_DATA, repaymentDetail);
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * @description 违约金罚息减免提交
     * @param request
     * @param response
     * @param processInstanceId
     * @param branchingProcessType
     * @param detailVariables
     * @param serviceVariables
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/penaltyReductionRequisitionSubmit.htm")
    public void penaltyReductionRequisitionSubmit(HttpServletRequest request,HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = true) Long processInstanceId,
            @RequestParam(value = "detailVariables", required = false) String detailVariables,
            @RequestParam(value = "serviceVariables", required = false) String serviceVariables)
            throws ServiceException {
        log.info("开始执行     LoanChangeAction-----------penaltyReductionRequisitionSubmit()");
        // 检查是否重复提交
        isBranchProcessRepeatSubmitNew(processInstanceId);

        LoanChangeDataBean loanChangeDataBean = JsonUtil.parse(serviceVariables,LoanChangeDataBean.class);
        String userName = getLoginUserName(request);
        PubRepaymentdetail repaymentdetail = JsonUtil.parse(detailVariables, PubRepaymentdetail.class);

        // 返回下一步的任务人
        Map<String, Object> res = null;
        try {
            res = postLoanChangeService.penaltyReductionRequisitionSubmit(processInstanceId, repaymentdetail,
                    loanChangeDataBean, userName);
        } catch (PersistentDataException e) {
            log.info("error", e);
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
		}
        ServletUtils.writeToResponse(response, res);
    }
    
    
    
    
    /**
     * 贷后审批：查询罚息减免流程审批处理意见
     * @param processInstanceId
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryPenaltyReductionProcessingOpinion.htm")
    public void queryPenaltyReductionProcessingOpinion(HttpServletRequest request,HttpServletResponse response,
    		String processInstanceId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        LoanChangeDataBean loanChangeDataBean = null;
        params.put("branchingProcessId", processInstanceId);
        List<PubProcessBranching> branchingList =  pubProcessBranchingService.getPageListByMap(params);
        if (branchingList != null && branchingList.size() > 0) {
            loanChangeDataBean = new LoanChangeDataBean();
            PubProcessBranching branching = branchingList.get(0);

            loanChangeDataBean.setNextStep(branching.getProcessingOpinion());
            loanChangeDataBean.setRemark1(branching.getRemark1());
            loanChangeDataBean.setRemarkComment(branching.getRemark2());
        }

        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, loanChangeDataBean);
        res.put(Constant.RESPONSE_DATA_TOTAL, (branchingList != null && branchingList.size() > 0) ? 1 : 0);
        ServletUtils.writeToResponse(response, res);
    }
    
    
    
    
    // ==============================================================================================
    /**
     * @description 展期信息
     * @param request
     * @param response
     * @param processInstanceId
     * @author wtc
     * @return void
     * @throws ServiceException
     * @since 1.0.0
     */
    @RequestMapping("/getExtensionInfo")
    public void getExtensionInfo(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(required=false)Boolean isBranch,
            @RequestParam(value = "processInstanceId", required = false) String processInstanceId)throws ServiceException {
        Map<String, Object> result = postLoanChangeService.getExtensionInfo(processInstanceId,isBranch);
        Map<String, Object> res = new HashMap<String, Object>();
        NumberUtil.roundBigDecimal(result);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, result);
        ServletUtils.writeToResponse(response, res);
    }

    @RequestMapping("/queryExtensionApprovalInfo.htm")
    public void queryExtensionApprovalInfo(@RequestParam(required = false) String processInstanceId,
            HttpServletRequest request, HttpServletResponse response) throws ServiceException, PersistentDataException {
        log.info("开始执行     ProcessTaskController-----------queryExtensionApprovalInfo()");
        Map params = new HashMap<String, Object>();
        params.put("start", 1);
        params.put("limit", 25);
        params.put("processInstanceId", processInstanceId);

        Map resultMap = postLoanChangeService.queryAheadOfReturnLoan(params);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, resultMap);
        res.put(Constant.RESPONSE_DATA_TOTAL, 1);
        ServletUtils.writeToResponse(response, res);
    }

    

    

    /**
     * 查询强制结清.在罚息减免审批和强制结清审批会用到该方法
     * @param request
     * @param response
     * @param processInstanceId
     * @param isBranch
     * @throws ServiceException
     */
    @RequestMapping("/queryForceSettleUp.htm")
    public void queryForceSettleUp(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value = "processInstanceId", required = true)String processInstanceId,
            @RequestParam(value = "isBranch", required = false) Boolean isBranch) throws ServiceException {
    	
        Map<String,Object> resultMap = postLoanChangeService.getForceSettlementInfo(processInstanceId,isBranch);
        Map<String, Object> res = new HashMap<String, Object>();
        NumberUtil.roundBigDecimal(resultMap);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, resultMap);
        ServletUtils.writeToResponse(response, res);
    }
    /**
     * 查询押品处置信息.在罚息减免审批和强制结清审批会用到该方法
     * @param pageNum
     * @param pageSize
     * @param taskId
     * @param isCompleted
     * @param searchParams
     * @param request
     * @param response
     * @throws ServiceException
     */
    @RequestMapping("/queryDispositionRegister.htm")
    public void queryDispositionRegister(@RequestParam(value = "pageNum",required=true) int pageNum,
            @RequestParam(value = "pageSize",required=true) int pageSize,
            @RequestParam(value = "taskId", required = false) final String taskId,
            @RequestParam(value = "isCompleted", required = false) final String isCompleted,
            @RequestParam(value = "searchParams", required = false) final String searchParams,
            final HttpServletRequest request, final HttpServletResponse response) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();

        Map searchParamsMap = JsonUtil.parse(searchParams, Map.class);
        if (searchParamsMap != null) {
            Iterator<String> iterator = searchParamsMap.keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                if (searchParamsMap.get(key) == null) {
                    continue;
                }
                params.put((String) key, searchParamsMap.get(key));
            }
        }
        Object taskId1 = params.get("taskId");
        if (taskId1 != null) {
            params.remove("taskId");
            String instanceId = null;
            try {
                instanceId = querybranchingProcessIdByTask((String) taskId1, isCompleted);
            } catch (PersistentDataException e) {
                log.info(e.getMessage());
            }
            if (StringUtils.isNotEmpty(instanceId)) {
                params.put("processInstanceId", instanceId);
            }
        }

        // 贷后变更审批时 传入taskId进行查询过滤（因为此时已经生成任务，所以用且仅用taskId)
        if (StringUtils.isNotEmpty(taskId)) {
            String branchingProcessId;
            try {
                branchingProcessId = querybranchingProcessIdByTask(taskId, isCompleted);
            } catch (PersistentDataException e) {
                log.error(e.getMessage());
                throw new ServiceException("数据库操作异常");
            }
            if (StringUtils.isNotEmpty(branchingProcessId)) {
                params.put("processInstanceId", branchingProcessId);
            }
        }

        Map<String, Object> res;
        try {
            res = postLoanChangeService.queryDispositionRegister(pageNum, pageSize, params);
        } catch (PersistentDataException e) {
            throw new ServiceException(e.getMessage());
        }
        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询流程审批意见
     * @param repaymentProcess
     * @param processInstanceId
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryProcessingOpinion.htm")
    public void queryProcessingOpinion(
            @RequestParam(value = "repaymentProcess", required = false) final Byte repaymentProcess,
            @RequestParam(value = "processInstanceId", required = false) final String processInstanceId,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        //
        Map map = new HashMap();
        map.put("processInstanceId", processInstanceId);
        map.put("repaymentProcess", repaymentProcess);
        List<PubProcessBranching> list = pubProcessBranchingService.getPageListByMap(map);

        Map<String, Object> res = new HashMap<String, Object>();

        if (list != null && list.size() > 0) {

            PubProcessBranching pb = list.get(0);
            if (pb.getBranchingProcessType() == null) {
                throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_DATA_NULL_EXCEPTION);
            } else if (repaymentProcess == null) {
                throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_PARAM_ERROR);
            } else if (pb.getBranchingProcessType().byteValue() != repaymentProcess.byteValue()) {
                throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_NO_APPLY_ERROR);
            }

            if (pb.getBranchingProcessType().byteValue() == repaymentProcess.byteValue()) {
                if (WorkFlowConstant.NEXT_STEP_PASS.equalsIgnoreCase(pb.getProcessingOpinion())) {
                    
                    res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                    res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
                } else if (WorkFlowConstant.NEXT_STEP_NOPROCESS.equalsIgnoreCase(pb.getProcessingOpinion())) {
                    res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_EXAMINE_NOPROCESS);
                } else if (WorkFlowConstant.NEXT_STEP_REJECT.equalsIgnoreCase(pb.getProcessingOpinion())) {
                    res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_EXAMINE_REJECT);
                } else if (WorkFlowConstant.NEXT_STEP_DISPOAL_REGISTER_DONE.equalsIgnoreCase(pb.getProcessingOpinion())) {
                    res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                    res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_DISPOSAL_REGISTER_DONE);
                }
            }
        }

        ServletUtils.writeToResponse(response, res);

    }

    /**
     * @description 强制结清
     * @param request
     * @param response
     * @param processInstanceId
     * @param branchingProcessType
     * @param serviceVariables
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since 1.0.0
     */
    @RequestMapping("/forceSettleUpRequisitionSubmit.htm")
    public void forceSettleUpRequisitionSubmit(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) Long processInstanceId,
            @RequestParam(value = "branchingProcessType", required = false) Byte branchingProcessType,
            @RequestParam(value = "serviceVariables", required = false) String serviceVariables)
            throws ServiceException {
        log.info("开始执行     CarLoanChangeAction-----------forceSettleUpRequisitionSubmit()");

        isBranchProcessRepeatSubmitNew(processInstanceId);
        LoanChangeDataBean carLoanChangeDataBean = JsonUtil.parse(serviceVariables,
                LoanChangeDataBean.class);
        String userName = getLoginUserName(request);

        // 返回下一步的任务人
        Map<String, Object> res = null;
        try {
            res = postLoanChangeService.forceSettleUpRequisitionSubmit(processInstanceId, carLoanChangeDataBean,
                    userName);
        } catch (PersistentDataException e) {
            log.info("error", e);
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
		}
        ServletUtils.writeToResponse(response, res);
    }

    /**
     * @description 展期提交
     * @param request
     * @param response
     * @param processInstanceId
     * @param serviceVariables
     * @author wtc
     * @return void
     * @throws Exception 
     * @since 1.0.0
     */
    @RequestMapping("/extensionApplicationSubmit.htm")
    public void extensionApplicationSubmit(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
            @RequestParam(value = "serviceVariables", required = false) String serviceVariables)
            throws Exception {
        log.info("开始执行     CarLoanChangeAction-----------extensionApplicationSubmit()");

        LoanChangeDataBean carLoanChangeDataBean = JsonUtil.parse(serviceVariables,LoanChangeDataBean.class);
        
        // 验证是否重复提交
        isExtensionRepeatSubmit(carLoanChangeDataBean.getProjectId());
        // 验证是否短期借款
        PlBorrow plBorrow = plBorrowService.getItemInfoByProcessInstanceId(processInstanceId);
        
        String userName = getLoginUserName(request);
        List<Long> roleList = getRole(request);

        // 返回下一步的任务人
        Map<String, Object> res;
        try {
            res = postLoanChangeService.extensionApplicationSubmit(carLoanChangeDataBean, plBorrow,
                    processInstanceId.toString(), carLoanChangeDataBean.getProjectId(), userName, roleList);
        } catch (PersistentDataException e) {
            throw new ServiceException(Constant.FAIL_CODE_VALUE, e.getMessage());
        }

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * @description 车辆处置提交
     * @param request
     * @param response
     * @param processInstanceId
     * @param branchingProcessType
     * @param serviceVariables
     * @throws ServiceException
     * @author wtc
     * @return void
     * @since 1.0.0
     */
    @RequestMapping("/VehicleDisposalApplicationSubmit.htm")
    public void VehicleDisposalApplicationSubmit(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) Long processInstanceId,
            @RequestParam(value = "branchingProcessType", required = false) Byte branchingProcessType,
            @RequestParam(value = "serviceVariables", required = false) String serviceVariables)
            throws ServiceException {
        log.info("开始执行     CarLoanChangeAction-----------VehicleDisposalApplicationSubmit()");

        isBranchProcessRepeatSubmitNew(processInstanceId);
        DisposalDataBean carDisposalDataBean = JsonUtil.parse(serviceVariables,
                DisposalDataBean.class);
        String userName = getLoginUserName(request);
        // 返回下一步的任务人
        Map<String, Object> res = null;
        res =  null;// TODO postLoanChangeService.vehicleDisposalStart(processInstanceId, userName, carDisposalDataBean);

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * @description 处理处置申请信息获取
     * @param processInstanceId
     * @param request
     * @param response
     * @author wtc
     * @return void
     * @throws ServiceException 
     * @since 1.0.0
     */
    @RequestMapping("/getVehicleDisposalInfo")
    public void getVehicleDisposalInfo(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(required = true) String processInstanceId,
    		@RequestParam(required = false)Boolean isBranch) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();    

        data = postLoanChangeService.getVehicleDisposalInfo(processInstanceId,isBranch);	
        NumberUtil.roundBigDecimal(data);
        returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        returnMap.put(Constant.RESPONSE_CODE_MSG, "车辆处置申请信息获取成功！");
        returnMap.put(Constant.RESPONSE_DATA, data);
        ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * @description 检查分支流程是否被重复提交，如果重复提交提示并退出
     * @param processInstanceId
     * @param branchingProcessType
     * @throws ServiceException
     * @author lukf
     * @return void
     * @since 1.0.0
     */
    public void isBranchProcessRepeatSubmitNew(Long processInstanceId)
            throws ServiceException {
        Map<String,Object> params = new HashMap<String,Object> ();
        // 几种参数
        params.put("processInstanceId", processInstanceId);
        params.put("isActive",WorkFlowConstant.IS_ACTIVE_FALSE);
        params.put("processingOpinion", WorkFlowConstant.NEXT_STEP_NOPROCESS);
        try {
        	List<PubProcessBranching> list =  pubProcessBranchingService.getPageListByMap(params);
            if (CollectionUtils.isNotEmpty(list)) {
                throw new ServiceException(Constant.FAIL_CODE_VALUE,"该记录的贷后变更尚未完成,不能发起变更");
            }
            params.put("isActive",WorkFlowConstant.IS_ACTIVE_TRUE);
            list =  pubProcessBranchingService.getPageListByMap(params);
            if (CollectionUtils.isNotEmpty(list)) {
                throw new ServiceException(Constant.FAIL_CODE_VALUE,"该记录的贷后变更尚未完成,不能发起变更");
            }
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(),e);
		}
        
    }

    public void isExtensionRepeatSubmit(Long projectId) throws ServiceException {
        Map params = new HashMap();
        // 几种参数
        params.put("projectId", projectId);
        Long lResult = null;
        try {
            lResult = postLoanChangeService.queryExtensionSubmited(params);
        } catch (PersistentDataException e) {
        	throw new ServiceException(e.getMessage(),e);
        }
        if (lResult > 0) {
            throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_REPEAT_SUBMIT);
        }
    }
    
    @RequestMapping("/queryDecisionInformation.htm")
    public void queryDecisionInformation(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) final Long processInstanceId,
            @RequestParam(value = "processState", required = false) final String processState) throws ServiceException {
        Map<String, Object> res = null;
        try {
            res = postLoanChangeService.queryDecisionInformation(processInstanceId, processState);
        } catch (PersistentDataException e) {
            log.error(e.getMessage());
            throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_DATA_NULL_EXCEPTION);
        }
        ServletUtils.writeToResponse(response, res);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/queryDecisionRecommendation.htm")
    public void queryDecisionRecommendation(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "processInstanceId", required = false) final String processInstanceId,
            @RequestParam(value = "taskId", required = false) final String taskId,
            @RequestParam(value = "isCompleted", required = false) final String isCompleted,
            @RequestParam(value = "processState", required = false) final String processState) throws ServiceException {
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            if ("false".equalsIgnoreCase(isCompleted)) {
                res.put(Constant.RESPONSE_DATA, new DisposalDataBean());
                res.put(Constant.RESPONSE_DATA_TOTAL, 0);
                res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
                ServletUtils.writeToResponse(response, res);
                return;
            }

            Map params = new HashMap();
            if (StringUtils.isNotEmpty(processInstanceId)) {
                params.put("processInstanceId", processInstanceId);
            }

            if (StringUtils.isNotEmpty("processState")) {
                params.put("processState", processState);
            }
            // 贷后变更审批时 传入taskId进行查询过滤（因为此时已经生成任务，所以用且仅用taskId)
            if (StringUtils.isNotEmpty(taskId)) {
                String instanceId;
                try {
                    instanceId = queryProcessInstanceIdByTask(taskId, isCompleted);
                } catch (PersistentDataException e) {
                    log.error(e.getMessage());
                    throw new ServiceException("数据库操作异常");
                }
                if (StringUtils.isNotEmpty(instanceId)) {
                    params.put("processInstanceId", instanceId);
                }
            }
            res = postLoanChangeService.queryDecisionRecommendation(params);
        } catch (PersistentDataException e) {
            log.error(e.getMessage());
            throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_DATA_NULL_EXCEPTION);
        }
        ServletUtils.writeToResponse(response, res);

    }

    /**
     * 查询附件信息
     * @param request
     * @param response
     * @param taskId
     * @param isCompleted
     * @param rdBtype
     * @throws ServiceException
     */
    @RequestMapping("/queryAttachmentInfo.htm")
    public void queryAttachmentInfo(HttpServletRequest request,HttpServletResponse response,
            @RequestParam(value = "taskId", required = false) final String taskId,
            @RequestParam(value = "isCompleted", required = false) final String isCompleted,
            @RequestParam(value = "rdBtype", required = false) final String rdBtype) throws ServiceException {
        Map<String, Object> res = null;
        // 贷后变更审批时 传入taskId进行查询过滤（因为此时已经生成任务，所以用且仅用taskId)
        String processInstanceId = null;
        if (StringUtils.isNotEmpty(taskId)) {
            try {
                processInstanceId = queryProcessInstanceIdByTask(taskId, isCompleted);
            } catch (PersistentDataException e) {
                log.error(e.getMessage());
                throw new ServiceException("数据库操作异常");
            }
        }
        try {
            res = postLoanChangeService.queryAttachmentInfo(processInstanceId, rdBtype);
        } catch (PersistentDataException e) {
            log.error(e.getMessage());
            throw new ServiceException(Constant.FAIL_CODE_VALUE, Constant.OPERATION_DATA_NULL_EXCEPTION);
        }
        ServletUtils.writeToResponse(response, res);

    }

    // 获取branching_process_id
    public String queryProcessInstanceIdByTask(String taskId, String isCompleted) throws PersistentDataException {
        Map map = postLoanChangeService.queryProcessInstanceByTask(taskId, isCompleted);
        String processInstanceId = (String) map.get("processInstanceId");
        return processInstanceId;
    }

    // 获取branching_process_id
    public String querybranchingProcessIdByTask(String taskId, String isCompleted) throws PersistentDataException {
        Map map = postLoanChangeService.querybranchingProcessIdByTask(taskId, isCompleted);
        String branchingProcessId = (String) map.get("branchingProcessId");
        return branchingProcessId;
    }
    /**
     * 抵押品追回、处置、拍卖
     * @description
     * @param params
     * @param request
     * @param response
     * @throws ServiceException
     * @throws PersistentDataException
     * @author wulb
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/updateCollateralStatus.htm")
    public void updateCollateralStatus(@RequestParam(value = "params", required = false) String params,
            HttpServletRequest request, HttpServletResponse response) throws PersistentDataException {
    	Map<String, Object> res = new HashMap<String, Object>();
    	res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
    	JSONObject tempJson = new JSONObject();
		tempJson.putAll(JsonUtil.parse(params, Map.class));
		Map<String, Object> map = JsonUtil.parse(tempJson.toJSONString(), Map.class);
		if(map.get("projectId") != null && !ObjectTool.String(map.get("projectId")).equals("")){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("isLocked", 0);
			param.put("projectId", map.get("projectId"));
			List<? extends PubRepayloaninfo> pubRepayloaninfos = pubRepayloaninfoDao.getPageListByMap(param);
			for (PubRepayloaninfo pubRepayloaninfo : pubRepayloaninfos) {
				if(ObjectTool.String(map.get("collateralStatus")).equals("1")){
					//抵押品追回
					pubRepayloaninfo.setRepaymentProcess((byte)6);
				}else if(ObjectTool.String(map.get("collateralStatus")).equals("2")){
					//抵押品处置
					pubRepayloaninfo.setRepaymentProcess((byte)5);
				}else if(ObjectTool.String(map.get("collateralStatus")).equals("3")){
					//抵押品拍卖
					pubRepayloaninfo.setRepaymentProcess((byte)7);
					PlBorrow plBorrow = plBorrowDao.getItemInfoById(pubRepayloaninfo.getBorrowid());
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("processInstanceId", plBorrow.getProcessInstanceId());
					data.put("isPayoff", 0);
					List<? extends PubRepaymentdetail> repaymentdetails =  null;// TODO repaymentdetailDao.getRepaymentdetailList(data);
					for (PubRepaymentdetail repaymentdetail : repaymentdetails) {
						repaymentdetail.setIsPayoff((byte)1);
						repaymentdetail.setRepaymentStaus((byte)5);
						repaymentdetailDao.update(repaymentdetail);
					}
				}
				pubRepayloaninfoDao.update(pubRepayloaninfo);
			}
			Boolean result =  null;// TODO 更新押品信息 carMessageDao.updateCarMessageObjectByProjectId(map);
			if(result){
				res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			}
		}else{
			res.put(Constant.RESPONSE_CODE_MSG, "projectId不存在或者为空！");
		}
        ServletUtils.writeToResponse(response, res);
    }

}
