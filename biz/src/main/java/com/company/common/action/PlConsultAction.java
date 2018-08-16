package com.company.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.service.ZZJFPlConsultService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.utils.ValidateUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.advance.domain.HousAdvanceApply;
import com.company.modules.common.domain.PlConsultAdvanceApply;
import com.company.modules.common.domain.PlConsultFee;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.utils.databean.BasicDataBean;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.instance.service.HousBorrowingBasicsService;
import com.company.modules.instance.service.HousEnquiryInstitutionService;
import com.company.modules.instance.service.HousPersonTypeService;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.warrant.domain.HousOritoInformation;
import com.company.modules.warrant.service.HousOritoInformationService;

/**
 * 新增咨询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/modules/common/action/plConsultAction")
public class PlConsultAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(PlConsultAction.class);
	@Autowired
	private ZZJFPlConsultService zZJFPlConsultService;
	@Autowired
	private HousAssessmentAgenciesService housAssessmentAgenciesService;
	@Autowired
	private HousBorrowingBasicsService housBorrowingBasicsService;
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;
	@Autowired
	private PlBorrowRequirementService plBorrowRequirementService;
	@Autowired
	private HousEnquiryInstitutionService housEnquiryInstitutionService;
	@Autowired
	private PlConsultService plConsultService;
	@Autowired
	private HousPersonTypeService housPersonTypeService;
	@Autowired
	private HousOritoInformationService housOritoInformationService;

	/**
	 * 新增申请
	 * 【工作台】-》【客户管理】-》【新增申请】
	    addConsult.htm接口，发起请求后，会把记录添加到下户，并且开启工作流
	 * @param request
	 * @throws ServiceException
	 * @author FHJ
	 */
	@RequestMapping("/addConsult.htm")
	public void addConsult(@RequestParam(required = true) String creditConsultFrom,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		PreliminaryEvaluationDataBean preliminaryEvaluationDataBean = null;
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			preliminaryEvaluationDataBean = JsonUtil.parseWithOnlyYMDDate(creditConsultFrom,
					PreliminaryEvaluationDataBean.class);

			fetchLoginUser(preliminaryEvaluationDataBean, request);
			preliminaryEvaluationDataBean.setLoginUserRoleId(getRoleForLoginUser(request).getId());//登录roleId

			if (preliminaryEvaluationDataBean.getCommit() != 0) {
				PlBorrowRequirement plBorrowRequirement = preliminaryEvaluationDataBean.getPlBorrowRequirement();

				if (ValidateUtils.isAmount(plBorrowRequirement.getAccount()) == false) {
					res.put(Constant.RESPONSE_CODE, Constant.OPERATION_PARAM_ERROR);
					res.put(Constant.RESPONSE_CODE_MSG, "提交申请失败！金额格式错误");
					ServletUtils.writeToResponse(response, res);
					return;
				}
			}

			Map<String, Object> result = zZJFPlConsultService.addPlConsult(getRoleForLoginUser(request),preliminaryEvaluationDataBean);
			if ((Long)result.get("consultId")>0) {
				res.put("result", result);
				res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				res.put(Constant.RESPONSE_CODE_MSG, "提交申请成功！");
			} else {
				res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
				res.put(Constant.RESPONSE_CODE_MSG, "提交申请失败！");
			}
		} catch (Exception e) {
			// TODO 建议以后修改下，抛出 ERongException的子类，然后 由@ExceptionHandler统一处理
			logger.error(e.getMessage(),e);
			String msg = "提交申请失败:"+e.getMessage();
			res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			res.put(Constant.RESPONSE_CODE_MSG, msg);
			ServletUtils.writeToResponse(response, res);
			return;
		}
		ServletUtils.writeToResponse(response, res);
	}


	/**
	 * 为BasicVO注入当前登录用户
	 * @param basicDataBean
	 * @param request
	 */
	protected void fetchLoginUser(BasicDataBean basicDataBean, HttpServletRequest request) {
		SysUser loginUser = getLoginUser(request);
		basicDataBean.setLoginUserId(loginUser.getId());
		basicDataBean.setOfficeId(loginUser.getOfficeId());
		basicDataBean.setUserName(loginUser.getUserName());
	}

	 /**
     * 根据流程id查询申请进件相关信息
     * @param request      页面的request
     * @param response      页面的response
     * @param processInstanceId   流程id
     * @throws Exception
     */
    @RequestMapping("/getConsultById.htm")
    public void getConsultById(HttpServletResponse response, HttpServletRequest request,
  		@RequestParam(value = "processInstanceId", required = true) String processInstanceId) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> plBorrowRequirement = plBorrowRequirementService.getItemInfoByProcessInstanceId(processInstanceId);
		List<HousAssessmentAgencies> housAssessmentAgencies = housAssessmentAgenciesService.getItemInfoByProcessInstanceId(processInstanceId);
		List<HousPersonType> housPersonTypes = housPersonTypeService.getItemInfoByProcessInstanceId(processInstanceId);
		HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService.getItemInfoByProcessInstanceId(processInstanceId);
		HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
		List<HousEnquiryInstitution> housEnquiryInstitution = housEnquiryInstitutionService.getItemInfoByProcessInstanceId(processInstanceId);
		result.put("plBorrowRequirement", plBorrowRequirement);
		result.put("housAssessmentAgencies", housAssessmentAgencies);
		result.put("housBorrowingBasics", housBorrowingBasics);
		result.put("housPropertyInformation", housPropertyInformation);
		result.put("housEnquiryInstitution", housEnquiryInstitution);
		result.put("housPersonTypes", housPersonTypes);
		returnMap.put(Constant.RESPONSE_DATA, result);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }


    /**
     * 垫资申请查询(查询pl_consult表,根据advance_apply=1)
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getPlConsultByadvanceApplyList.htm")
    public void getPlConsultByadvanceApplyList(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		List<PlConsultAdvanceApply> plConsult = new ArrayList<PlConsultAdvanceApply>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        PageHelper.startPage(currentPage, pageSize);
        int advanceApply = (int)paramap.get("advanceApply");
        if (advanceApply==1) {
        	result = plConsultService.getPlConsultByadvanceApplyList(paramap);
		}else{
			paramap.put("processState", HousAdvanceApply.USERTASK_ADVANCE_APPLY);
			result = plConsultService.getDoPlConsultByadvanceApplyList(paramap);
		}
		//PageInfo<PlConsultAdvanceApply> page = new PageInfo<PlConsultAdvanceApply>(plConsult);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(result);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/getPlconsultList.htm")
    public void getPlconsultList(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(value = "currentPage") Integer currentPage,
    		@RequestParam(value = "pageSize") Integer pageSize,
      		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
    	// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        PageHelper.startPage(currentPage, pageSize);
        result = plConsultService.getPlConsultList(paramap);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(result);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTAL, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
}
