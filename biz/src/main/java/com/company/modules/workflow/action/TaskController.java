package com.company.modules.workflow.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.common.domain.ExportHousOritoInformation;
import com.company.modules.common.domain.HouseCheckBank;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.ExportExcel;
import com.company.modules.common.utils.parser.impl.DefaultClassTypeParser;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.system.domain.SysUser;
import com.company.modules.workflow.controller.WorkflowBaseController;
import com.company.modules.workflow.service.RDZZTaskService;
import com.github.pagehelper.PageInfo;

//import com.company.contract.util.ContractUtil;


/**
 * @author fzc, fzc@erongdu.com
 * @Description 任务controller
 * @CreatTime 2016年8月8日 下午4:45:24
 * @since version 1.0.0
 */
@Controller
@RequestMapping("/modules/workflow/ProcessTaskController")
public class TaskController extends WorkflowBaseController {
    private static final String USERTASK_REPAY_PLAN = "usertask-repay-plan";
    private String USERTASK_COLLATERALASSESS = "usertask-collateralAssess";
    private String USERTASK_CONTROL_REVIEW = "usertask-control-review";
    private String USERTASK_XIAHU = "usertask-xiahu";//下户
    private String USERTASK_UPLOADING = "usertask-uploading";//上传资料（等同老版本下户，只是更名，同样作为流程起点）

    private String USERTASK_BUSINESSEXCHANGE = "usertask-businessExchange";
    private String USERTASK_CUSTOMERINVESTIGATE = "usertask-customerInvestigate";
    private String USERTASK_CUSTOMERSERVICESTAFFCONFIRM = "usertask-customerServiceStaffConfirm";
    private String USERTASK_HOUSEHOLDCONFIRM = "usertask-householdConfirm";
    private String USERTASK_SIGN_CONTRACT = "usertask-sign-contract";
    private String USERTASK_FACEAUDIT_SUPPLYMATERIAL = "usertask-faceaudit-investigate";
    private String USERTASK_SUPPLYINVESTIGATE = "usertask-supplyInvestigate";
    private String USERTASK_FACECONFIRM_INVESTIGATE = "usertask-faceConfirm-investigate";
    private String USERTASK_ORGANIZATION_INITIALAUDIT = "usertask-organization-initialAudit";
    private DefaultClassTypeParser defaultClassTypeParser = new DefaultClassTypeParser();


    @Autowired
    private RDZZTaskService taskService;
    @Autowired
    private HousPropertyInformationService housPropertyInformationService;
    @Autowired
    TaskService myTaskService;
    @Autowired
    HistoryService historyService;

    /**
     * 通用流程任务查询接口
     * @param usertask
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/queryCommonTasks.htm")
    public void queryCommonTasks(@RequestParam(required = true) String usertask,
                                      @RequestParam(required = true) boolean isCompleted,
                                      @RequestParam(required = true) int currentPage,
                                      @RequestParam(required = true) int pageSize,
                                      @RequestParam(required = false) String searchParams,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", usertask);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }
    /**
     * 手动更改执行人
     * @param taskId
     * @param processInstanceId
     * @param userId
     * @param projectId
     * @param request
     * @param response
     * @throws ServiceException
     */
    @RequestMapping("/modifyTaskAssigneeByAnyKey.htm")
    public void modifyTaskAssigneeByAnyKey(@RequestParam(required = true) String taskId,
                                           @RequestParam(required = true) String processInstanceId,
                                           @RequestParam(required = true) String userId,
                                           @RequestParam(required = false) int projectId,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws ServiceException{
        //查询当前任务是否存在，并且是否处于待处理的状态。如若任务已经完成，再去更改执行人显然没意义
        //去历史任务列表查询，当前任务是否已经完成
        Map<String,Object> res = new HashMap<>();
         Map<String,Object> hisTask = taskService.getHisTaskInfoByTaskId(taskId);
        if (hisTask.get("END_TIME_")!=null){
            res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, "该任务已经完成");

            ServletUtils.writeToResponse(response, res);
        }

        //任务待处理，修改任务执行人（当前任务和历史任务）
        int result = taskService.modifyTaskAssigneeByAnyKey(taskId,userId);
        int result2 = taskService.modifyHisTaskAssigneeByAnyKey(taskId,userId);
        if (result>0&&result2>0){
            res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);

            ServletUtils.writeToResponse(response, res);
        }
    }

    /**
     * 查询下户任务分配任务
     */
    @RequestMapping("/queryHouseholdTasks")
    public void queryHouseholdTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHouseholdTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询权证下户任务
     */
    @RequestMapping("/queryHouseholdInvestigate")
    public void queryHouseholdInvestigate(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        PageInfo<Map<String, Object>> pageInfo = taskService.queryHouseholdInvestigate(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }


    /**
     * 导出权证下户任务
     */
    @RequestMapping("/exportHouseholdInvestigate")
    public void exportHouseholdInvestigate(@RequestParam(required = false) String searchParams,
                                           HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        List<ExportHousOritoInformation> houseHoldInvestigateList = taskService.queryHouseholdInvestigate(roleId, params);
        if (houseHoldInvestigateList != null && houseHoldInvestigateList.size() > 0) {
            for (ExportHousOritoInformation exportHousOritoInformation : houseHoldInvestigateList) {
                String houseAddress = housPropertyInformationService.getHousAddress(exportHousOritoInformation.getResidentialAddressId());
                if (exportHousOritoInformation.getResidentialAddress() != null) {
                    String address = houseAddress += exportHousOritoInformation.getResidentialAddress();
                    exportHousOritoInformation.setAddress(address);
                } else {
                    exportHousOritoInformation.setAddress(houseAddress);
                }
            }
        }


        response.setContentType("octets/stream");
        Map<String, Object> result = new HashMap<>();
        String sep = File.separator;
		String rootDir = request.getSession().getServletContext().getRealPath("/");// 文件根目录
        String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
        String baseDestDir = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(dirName).toString();
        File baseDestDirFile = new File(baseDestDir);
        if (!baseDestDirFile.exists()) {
            baseDestDirFile.mkdirs();
        }
        String templeteFile = null;
        String destFile = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            String filename = "权证下户.xls";
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + new String(filename.replace(" ", "").getBytes("UTF-8"), "iso8859-1"));
            result.put(Constant.RESPONSE_DATA, houseHoldInvestigateList);
            String[] headers = {"项目编号", "客户名称", "房产面积（平方）", "贷款金额(元)", "贷款期限(月)", "贷款人姓名", "贷款人身份证号", "贷款人性别", "婚姻状况", "房产地址", "正常价格(净得/元)", "房屋快出值(元)", "税款详情(元)", "来源", "机构名称", "报单人", "报单时间", "流程状态", "任务处理人", "任务开始时间", "任务结束时间"};
            ExportExcel<ExportHousOritoInformation> ex = new ExportExcel<ExportHousOritoInformation>();
            ex.exportExcel("权证下户", headers, houseHoldInvestigateList, response.getOutputStream(), "yyy-MM-dd HH:mm:ss");
			/*result.put(Constant.RESPONSE_DATA, houseHoldInvestigateList);
			templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("HouseholdInvestigate.docx").toString();
			destFile = new StringBuilder(baseDestDir).append(sep).append("HouseholdInvestigate").append(System.currentTimeMillis()).append(".docx").toString();
			ContractUtil.generateWord(templeteFile, destFile, result);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("权证下户.docx".getBytes("UTF-8"),"ISO-8859-1"));
			FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(destFile)),response.getOutputStream());*/
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Map<String, Object> res = new HashMap<String, Object>();
            res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
            res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            ServletUtils.writeToResponse(response, res);
        }
    }


    /**
     * 查询权证核行任务
     */
    @RequestMapping("/queryHouseCheckBank")
    public void queryHouseCheckBank(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHouseCheckBank(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }


    /**
     * 导出权证核行任务
     */
    @RequestMapping("/exportHouseCheckBank")
    public void exportHouseCheckBank(@RequestParam(required = false) String searchParams,
                                     HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        try {
            List<HouseCheckBank> housCheckBankList = taskService.queryHouseCheckBank(roleId, params);
            if (housCheckBankList != null && housCheckBankList.size() > 0) {
                for (HouseCheckBank houseCheckBank : housCheckBankList) {
                    String houseAddress = housPropertyInformationService.getHousAddress(houseCheckBank.getResidentialAddressId());
                    if (houseCheckBank.getResidentialAddress() != null) {
                        String address = houseAddress += houseCheckBank.getResidentialAddress();
                        houseCheckBank.setAddress(address);
                    } else {
                        houseCheckBank.setAddress(houseAddress);
                    }
                }
            }


            response.setContentType("octets/stream");
            Map<String, Object> result = new HashMap<>();
            String sep = File.separator;
			String rootDir = request.getSession().getServletContext().getRealPath("/");// 文件根目录
            String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
            String baseDestDir = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(dirName).toString();
            File baseDestDirFile = new File(baseDestDir);
            if (!baseDestDirFile.exists()) {
                baseDestDirFile.mkdirs();
            }
            String templeteFile = null;
            String destFile = null;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            String filename = "权证核行.xls";
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + new String(filename.replace(" ", "").getBytes("UTF-8"), "iso8859-1"));
            result.put(Constant.RESPONSE_DATA, housCheckBankList);
            String[] headers = {"项目编号", "客户名称", "贷款金额(元)", "贷款期限(月)", "贷款人姓名", "贷款人身份证号", "贷款人性别", "婚姻状况", "房产地址", "来源", "机构名称", "报单人", "报单时间", "流程状态", "任务处理人", "任务开始时间", "任务结束时间", "核行备注"};
            ExportExcel<HouseCheckBank> ex = new ExportExcel<HouseCheckBank>();
            ex.exportExcel("权证核行", headers, housCheckBankList, response.getOutputStream(), "yyy-MM-dd HH:mm:ss");
			/*templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("HousCheckBank.docx").toString();
			destFile = new StringBuilder(baseDestDir).append(sep).append("HousCheckBank").append(System.currentTimeMillis()).append(".docx").toString();
			ContractUtil.generateWord(templeteFile, destFile, result);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("权证核行.docx".getBytes("UTF-8"),"ISO-8859-1"));
			FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(destFile)),response.getOutputStream());*/
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Map<String, Object> res = new HashMap<String, Object>();
            res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
            res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            ServletUtils.writeToResponse(response, res);
        }
    }

    /**
     * 查询下户费管理任务
     */
    @RequestMapping("/queryhousLowerCost")
    public void queryhousLowerCost(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        PageInfo<Map<String, Object>> pageInfo = taskService.queryhousLowerCost(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询垫资风控总监确认任务
     */
    @RequestMapping("/queryHousAdvanceApply")
    public void queryHousAdvanceApply(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        params.put("taskDefinition", HousAdvanceApply.USERTASK_LOANING_CONFIRM_WARRANTMANAGER);

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousAdvanceApply(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询垫资客服确认任务
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @return void
     * @throws ServiceException
     * @description
     * @author huy
     * @since 1.0.0
     */
    @RequestMapping("/queryCustomerServiceHousAdvanceApply")
    public void queryCustomerServiceHousAdvanceApply(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        params.put("taskDefinition", HousAdvanceApply.USERTASK_LOANING_CONFIRM_BUSINESSSTAFF);

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousAdvanceApply(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询垫资建委调档任务
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @return void
     * @throws ServiceException
     * @description
     * @author huy
     * @since 1.0.0
     */
    @RequestMapping("/queryHousAdvanceTransferFile")
    public void queryHousAdvanceTransferFile(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        params.put("taskDefinition", HousAdvanceApply.USERTASK_TRANSFER_FILES);

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousAdvanceApply(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询垫资操作任务
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @return void
     * @throws ServiceException
     * @description
     * @author huy
     * @since 1.0.0
     */
    @RequestMapping("/queryHousAdvanceLoaning")
    public void queryHousAdvanceLoaning(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        params.put("taskDefinition", HousAdvanceApply.USERTASK_LOANING);

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousAdvanceApply(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 查询垫资操作任务
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @return void
     * @throws ServiceException
     * @description
     * @author huy
     * @since 1.0.0
     */
    @RequestMapping("/queryHousAdvanceAssign")
    public void queryHousAdvanceAssign(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        params.put("taskDefinition", HousAdvanceApply.USERTASK_WARRANTMANAGER_ASSIGNED);

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousAdvanceApply(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());
        ServletUtils.writeToResponse(response, res);
    }


    /**
     * 查询垫资公证任务
     */
    @RequestMapping("/queryAdvanceNotarizeTasks")
    public void queryAdvanceNotarizeTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryAdvanceNotarizeTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期客户调查
     */
    @RequestMapping("/queryHousExtensionSurveyTasks")
    public void queryHousExtensionSurveyTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);


        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousExtensionSurveyTasks(roleId, params, isCompleted, currentPage, pageSize);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期房产评估
     */
    @RequestMapping("/queryHousExtensionCollateralAssessTasks")
    public void queryHousExtensionCollateralAssessTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousExtensionCollateralAssessTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期公证分配查询
     */
    @RequestMapping("/queryHousNotarizationAssignTasks")
    public void queryHousNotarizationAssignTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousNotarizationAssignTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 超额展期公证登记查询
     */
    @RequestMapping("/queryHousExcessiveNotarizationTasks")
    public void queryHousExcessiveNotarizationTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousExcessiveNotarizationTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期超额抵押
     */
    @RequestMapping("/queryHousExcessiveMortgageTasks")
    public void queryHousExcessiveMortgageTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHousExcessiveMortgageTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期合同
     */
    @RequestMapping("/queryHouSignExtendedContractTasks")
    public void queryHouSignExtendedContractTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryHouSignExtendedContractTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期费管理
     */
    @RequestMapping("/queryExtendedfeeTasks")
    public void queryExtendedfeeTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryExtendedfeeTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 展期费划扣
     */
    @RequestMapping("/queryDeductTasks")
    public void queryDeductTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);

        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);

        PageInfo<Map<String, Object>> pageInfo = taskService.queryDeductTasks(roleId, params, isCompleted, currentPage, pageSize);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());

        ServletUtils.writeToResponse(response, res);
    }


    /**
     * 小贷流程放款初审
     * 【工作台】-》【放款审核】-》【放款初审】
     *
     */
    @RequestMapping("/queryAuditTasks")
    public void queryAuditTasks(
            @RequestParam(required = true) boolean isCompleted,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            @RequestParam(required = true) String type,
            @RequestParam(required = false) String searchParams,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();

        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        if (coverdOffices.size() > 0) {
            params.put("coverdOffices", coverdOffices);
        }

        Long roleId = getRoleId(request);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        PageInfo<Map<String, Object>> pageInfo = taskService.queryAuditTasks(roleId, params, isCompleted, currentPage, pageSize, type);

        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, pageInfo.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, pageInfo.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }


    /**
     * 保报单专员客服初评任务查询
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryInstanceInfoTasks")
    public void queryInstanceInfoTasks(@RequestParam(required = true) boolean isCompleted,
                                       @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                       @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        params.put("userId", getLoginUser(request).getId());
        params.put("roleNid", getRoleForLoginUser(request).getNid());

        Map<String, Object> res = new HashMap<String, Object>();
        params.put("taskDefinition", USERTASK_COLLATERALASSESS);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceInfoTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    @RequestMapping("/queryInstanceRepayPlanTasks")
    public void queryInstanceRepayPlanTasks(@RequestParam(required = true) boolean isCompleted,
                                        @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                        @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", USERTASK_REPAY_PLAN);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }
    /**
     * 【资料上传】任务查询
     * 【工作台】-》【客户管理】-》【下户】，在此位置，点击下户请求，实际是执行
     queryInstanceUploadingTasks下面的方法，拉取  需要上传资料的  任务列表
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryInstanceUploadingTasks")
    public void queryInstanceUploadingTasks(@RequestParam(required = true) boolean isCompleted,
                                   @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                   @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", USERTASK_UPLOADING);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 风控初审任务查询
      【风控审核】-》【风控初审】，在此位置，点击风控初审请求，实际是执行
       queryInstanceTasks 方法，拉取  风控初审  task列表
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryInstanceTasks")
    public void queryInstanceTasks(@RequestParam(required = true) boolean isCompleted,
                                   @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                   @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", USERTASK_COLLATERALASSESS);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 【资料上传】任务查询
     * 【工作台】-》【客户管理】-》【下户】，在此位置，点击下户请求，实际是执行
     queryInstanceXiaHuTasks，拉取  下户  任务列表
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryInstanceXiaHuTasks")
    public void queryInstanceXiaHuTasks(@RequestParam(required = true) boolean isCompleted,
                                            @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                            @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", USERTASK_XIAHU);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }
    /**
     * 风控复审任务查询
      【风控审核】-》【风控复审】，在此位置，点击风控初审请求，实际是执行
       queryInstanceReviewTasks 方法，拉取  风控复审  task列表
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryInstanceReviewTasks")
    public void queryInstanceReviewTasks(@RequestParam(required = true) boolean isCompleted,
                                         @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                         @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", USERTASK_CONTROL_REVIEW);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 机构初审任务查询
       【风控审核】-》【机构初审】，在此位置，点击机构初审请求，实际是执行
        queryOrgInitialAuditTasks 方法，拉取  机构初审  task列表
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryOrgInitialAuditTasks")
    public void queryOrgInitialAuditTasks(@RequestParam(required = true) boolean isCompleted,
                                          @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                          @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", USERTASK_ORGANIZATION_INITIALAUDIT);
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 抵押办理（拉取 抵押办理 列表）
     * 【工作台】-》【押品管理】-》【抵押办理】
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryPledgeRegiste")
    public void queryPledgeRegiste(@RequestParam(required = true) boolean isCompleted,
                                   @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                   @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", "usertask-offline-tasks");
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/queryOrgReviewAudit")
    public void queryOrgReviewAudit(@RequestParam(required = true) boolean isCompleted,
                                    @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                    @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("taskDefinition", "usertask-organization-reviewAudit");
        PageInfo<Map<String, Object>> page = taskService.queryInstanceTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        res.put(Constant.ALLOW_REASSIGN, getRoleForLoginUser(request).isAdmin());

        ServletUtils.writeToResponse(response, res);
    }


    /**
     * 客服对接任务查询-补全信息
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryConnectionTasks")
    public void queryConnectionTasks(@RequestParam(required = true) boolean isCompleted,
                                     @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                     @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        params.put("taskDefinition", USERTASK_BUSINESSEXCHANGE);
        PageInfo<Map<String, Object>> page = taskService.queryConnectionTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 客服调查任务查询--信息筛查
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/querySurveyTasks")
    public void querySurveyTasks(@RequestParam(required = true) boolean isCompleted,
                                 @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                 @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        String[] taskDefinitionArray = new String[]{USERTASK_CUSTOMERINVESTIGATE, USERTASK_FACEAUDIT_SUPPLYMATERIAL, USERTASK_SUPPLYINVESTIGATE, USERTASK_FACECONFIRM_INVESTIGATE};
        params.put("taskDefinitionArray", taskDefinitionArray);
        PageInfo<Map<String, Object>> page = taskService.querySurveyTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 客服确认任务查询--下户确认
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryConfirmationTasks")
    public void queryConfirmationTasks(@RequestParam(required = true) boolean isCompleted,
                                       @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                       @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        params.put("taskDefinition", USERTASK_CUSTOMERSERVICESTAFFCONFIRM);
        PageInfo<Map<String, Object>> page = taskService.querySurveyTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 客服下户确认任务查询
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryOritoConfirmationTasks")
    public void queryOritoConfirmationTasks(@RequestParam(required = true) boolean isCompleted,
                                            @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                            @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        params.put("taskDefinition", USERTASK_HOUSEHOLDCONFIRM);
        PageInfo<Map<String, Object>> page = taskService.queryOritoConfirmationTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    /**
     * 合同签订任务查询
     *
     * @param isCompleted
     * @param currentPage
     * @param pageSize
     * @param searchParams
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/queryContractTasks")
    public void queryContractTasks(@RequestParam(required = true) boolean isCompleted,
                                   @RequestParam(required = true) int currentPage, @RequestParam(required = true) int pageSize,
                                   @RequestParam(required = false) String searchParams, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // 对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)) {
            params = JsonUtil.parse(searchParams, Map.class);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        List<String> coverdOffices = getCoverdOffices(loginUser);
        Long roleId = getRoleId(request);
        params.put("coverdOffices", coverdOffices);
        params.put("userName", userName);
        params.put("roleId", roleId);
        params.put("roleNid", getRoleForLoginUser(request).getNid());
        params.put("taskDefinition", USERTASK_SIGN_CONTRACT);
        PageInfo<Map<String, Object>> page = taskService.queryContractTasks(params, currentPage, pageSize, isCompleted);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());

        ServletUtils.writeToResponse(response, res);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/queryMyTaskStatistic")
    public void queryMyTaskStatistic(
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<>();
        SysUser loginUser = getLoginUser(request);
        String userName = loginUser.getUserName();
        params.put("userName", userName);
        Map<String, Object> res = new HashMap<String, Object>();
        PageInfo<Map<String, Object>> page = taskService.queryMyTaskStatistic(params, currentPage, pageSize);
        res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        res.put(Constant.RESPONSE_DATA, page.getList());
        res.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
        ServletUtils.writeToResponse(response, res);
    }


}