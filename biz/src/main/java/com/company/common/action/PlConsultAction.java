package com.company.common.action;

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
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.common.utils.databean.BasicDataBean;
import com.company.modules.instance.domain.*;
import com.company.modules.instance.service.*;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.system.domain.SysUser;
import com.company.modules.warrant.service.HousOritoInformationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 新增咨询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/modules/common/action/plConsultAction")
public class PlConsultAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(PlConsultAction.class);
	//										  2592000000
	//private static final long DATE_LIMIT = 86400000;
	@Resource
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

	@Value("#{applyLoan}")
	Properties properties;

    /**
     *根据房产证号查询是否可以贷款申请的（当前业务为一个房产证号一个月可以申请一次）
     * @param houseNum
     * @param response
     * @param request
     */
    @RequestMapping("/getWhetherLoanByHomeNum.htm")
	public void getWhetherLoanByHomeNum(@RequestParam(required = true) String houseNum,
	                                    HttpServletResponse response,
                                        HttpServletRequest request){
        Map<String, Object> res = new HashMap<String, Object>();

        Map<String,Object> map =  zZJFPlConsultService.getWhetherLoanByHomeNum(houseNum);
        if (map==null){
            res.put(Constant.RESPONSE_CODE, 200);
            res.put(Constant.RESPONSE_CODE_MSG, "true");
            ServletUtils.writeToResponse(response, res);
        }else {
            long createTime = ((Date) map.get("createTime")).getTime();
            String dateLimit = properties.getProperty("dateLimit");
            long nowTime = System.currentTimeMillis();
            if (((nowTime-createTime))<Long.valueOf(dateLimit)){
                res.put(Constant.RESPONSE_CODE, 200);
                res.put(Constant.RESPONSE_CODE_MSG, "false");
                ServletUtils.writeToResponse(response, res);
            }
        }

    }

	protected String checkBorrowRequirement(PlBorrowRequirement plBorrowRequirement) throws Exception {

		String errorMsg = "";
		if (ValidateUtils.isAmount(plBorrowRequirement.getAccount()) == false) {
			errorMsg = "提交申请失败！金额格式错误";
			return errorMsg;
		}

		if (!ValidateUtils.isInteger(plBorrowRequirement.getTimeLimit())) {
			errorMsg = "申请期限不允许为空";
			return errorMsg;
		}

		if (!ValidateUtils.isInteger(plBorrowRequirement.getProductId())) {
			errorMsg = "产品不允许为空";
			return errorMsg;
		}

		if (!ValidateUtils.isInteger(plBorrowRequirement.getProjectBelongs())) {
			errorMsg = "项目来源不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(plBorrowRequirement.getBorrowUse())) {
			errorMsg = "借款用途不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(plBorrowRequirement.getRepaymentSource())) {
			errorMsg = "还款来源不允许为";
			return errorMsg;
		}
		return errorMsg;
	}

	protected String checkHousPropertyInformation(HousPropertyInformation housPropertyInformation) throws Exception {

		String errorMsg = "";
		if (!ValidateUtils.isInteger(housPropertyInformation.getPlanningPurposes())) {
			errorMsg = "土地用途不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getPropertyOwner())) {
			errorMsg = "产权人姓名不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getDistrictAndCounty())) {
			errorMsg = "区县 不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getPropertyAddress())) {
			errorMsg = "所在地址不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getBuildingNumber())) {
			errorMsg = "楼栋号不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getTotalFloor())) {
			errorMsg = "总楼层不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getFloor())) {
			errorMsg = "楼层不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getRoomNumber())) {
			errorMsg = "室号不允许为空";
			return errorMsg;
		}

		if (housPropertyInformation.getPropertyArea() == null) {
			errorMsg = "面积不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getHouseNumber())) {
			errorMsg = "房产编号不允许为空";
			return errorMsg;
		}

		if (housPropertyInformation.getDateOfIssuing() == null) {
			errorMsg = "土地用途不允许为空";
			return errorMsg;
		}

		if (ValidateUtils.isEmpty(housPropertyInformation.getAgeOfCompletion())) {
			errorMsg = "建成年代不允许为空";
			return errorMsg;
		}
		return errorMsg;
	}

	/**
	 * 新增申请
	 * 【工作台】-》【客户管理】-》【新增申请】
	    addConsult.htm接口，发起请求后，会把记录添加到资料上传，并且开启工作流
	 * @param request
	 * @throws ServiceException
	 * @author FHJ
	 */
	@RequestMapping("/addConsult.htm")
	public void addConsult(@RequestParam(required = true) String creditConsultFrom,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception { PreliminaryEvaluationDataBean preliminaryEvaluationDataBean = null;
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			preliminaryEvaluationDataBean = JsonUtil.parseWithOnlyYMDDate(creditConsultFrom,
					PreliminaryEvaluationDataBean.class);

			fetchLoginUser(preliminaryEvaluationDataBean, request);
			preliminaryEvaluationDataBean.setLoginUserRoleId(getRoleForLoginUser(request).getId());//登录roleId

			if (preliminaryEvaluationDataBean.getCommit() != 0) {
				PlBorrowRequirement plBorrowRequirement = preliminaryEvaluationDataBean.getPlBorrowRequirement();

				if (plBorrowRequirement != null) {
					String info = checkBorrowRequirement(plBorrowRequirement);
					if (info != null && info.length() != 0) {
						res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
						res.put(Constant.RESPONSE_CODE_MSG, info);
						ServletUtils.writeToResponse(response, res);
						return;
					}
				}

				HousPropertyInformation housPropertyInformation = preliminaryEvaluationDataBean.getHousPropertyInformation();
				if (housPropertyInformation != null) {
					String info = checkHousPropertyInformation(housPropertyInformation);
					if (info != null && info.length() != 0) {
						res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
						res.put(Constant.RESPONSE_CODE_MSG, info);
						ServletUtils.writeToResponse(response, res);
						return;
					}
				}
			}
			//新增判断，一个月内一个房产证只能申请一次
			/*JSONObject json1 = JSONObject.fromObject(creditConsultFrom);
			String housPropertyInformation = json1.getString("housPropertyInformation");
			JSONObject json2 = JSONObject.fromObject(housPropertyInformation);
			if (!json2.toString().contains("houseNumber")){
				res.put(Constant.RESPONSE_CODE, Constant.OPERATION_PARAM_ERROR);
				res.put(Constant.RESPONSE_CODE_MSG, "房产证号不能为空");
				ServletUtils.writeToResponse(response, res);
				return;
			}
			String homeNum = json2.getString("houseNumber");

			Map<String,Object> map =  zZJFPlConsultService.getWhetherLoanByHomeNum(homeNum);

			long createTime = ((Date) map.get("createTime")).getTime();
			String dateLimit = properties.getProperty("dateLimit");
			long nowTime = System.currentTimeMillis();
			if (((nowTime-createTime))<Long.valueOf(dateLimit)){
				res.put(Constant.RESPONSE_CODE, Constant.OPERATION_PARAM_ERROR);
				res.put(Constant.RESPONSE_CODE_MSG, "提交申请失败！一个房产证一个月只能申请一次");
				ServletUtils.writeToResponse(response, res);
				return;
			}*/
			//执行新增申请操作
			Map<String, Object> result = zZJFPlConsultService.addPlConsult(getRoleForLoginUser(request),preliminaryEvaluationDataBean,creditConsultFrom);
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


	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(new Date()));
		System.out.println(System.currentTimeMillis());
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


		SysUser loginUser = getLoginUser(request);
		String userName = loginUser.getUserName();
		paramap.put("userName", userName);
		List<String> coveredOffices = getCoverdOffices(loginUser);
		paramap.put("coveredOffices", coveredOffices);

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
