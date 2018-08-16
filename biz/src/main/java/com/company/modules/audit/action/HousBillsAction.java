package com.company.modules.audit.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.company.common.service.PlBorrowService;
import com.company.common.service.PlProductService;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.audit.domain.HousBills;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.audit.service.HousBillsService;
import com.company.modules.audit.service.HousLoanfeesService;
import com.company.modules.audit.service.LoanInfoService;
import com.company.modules.audit.util.databean.LoanInfoDataBean;
import com.company.modules.collateral.service.CollateralManageService;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.domain.PubLoan;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.finance.service.PubLoanService;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.notary.service.NotaryManageService;
import com.company.modules.system.domain.SysUser;

/**
 * User:    fzc
 * DateTime:2016-08-17 03:46:40
 * details: 放款单/打款单,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/audit/HousBillsAction")
@Controller
public class HousBillsAction extends BaseAction {

    /**
     * 放款单/打款单的Service
     */
	@Autowired
	private HousBillsService housBillsService;
	
	@Autowired
	private HousLoanfeesService housLoanfeesService;
	
	@Autowired
	private PlBorrowRequirementService plBorrowRequirementServiceImpl;

	@Autowired
	private LoanInfoService loanInfoServiceImpl ;
	@Autowired
	private HousPropertyInformationService housPropertyInformationService;
	@Autowired
	private PlBorrowService plBorrowServiceImpl;
	@Autowired
	private HousLoanfeesService housLoanfeesServiceImpl;
	 /**
     * 公证登记的Service
     */
	@Autowired
	private NotaryManageService notaryManageService;
	@Autowired
	private PlProductService plProductService;
	@Autowired
	private PubLoanService pubLoanService;
	
	
	 /**
     * 押品登记表的Service
     */
	@Autowired
	private CollateralManageService collateralManageService;
    /**
     * 打款单表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housBills	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousBills.htm")
    public void saveOrUpdateHousBills(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    	@RequestParam(value = "housBills", required = false) String housBills) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		SysUser sysUser = this.getLoginUser(request);
		HousBills housBillsInfo = new HousBills();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housBills)) {
			housBillsInfo = JsonUtil.parse(housBills, HousBills.class);
		}
		long influence = housBillsService.insertOrupdate(housBillsInfo,sysUser.getId(),"loan");
		if (influence > 0) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		ServletUtils.writeToResponse(response, returnMap);
    }

    /**
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getHousBillsList.htm")
    public void getHousBillsList(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        PageHelper.startPage(currentPage, pageSize);
		List<HousBills> housBillss = housBillsService.getPageListByMap(paramap);
		PageInfo<HousBills> page = new PageInfo<HousBills>(housBillss);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
    * 根据id删除数据
    * @param response    页面的response
    * @param id          主键
    * @throws ServiceException
    */
    @RequestMapping("/deleteById.htm")
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id") long id) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = housBillsService.deleteById(id);
		if(influence > 0){
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * @description 获取放款单信息
     * @param response
     * @param processInstanceId
     * @throws Exception
     * @author fzc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/getHousBillBasicInfo.htm")
    public void getHousBillBasicInfo(
    		HttpServletResponse response, 
    		@RequestParam(value = "processInstanceId") String processInstanceId) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	Map<String, Object> housBillBasicInfo = housBillsService.getHousBillBasicInfo(processInstanceId);//放款单基本信息
    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("processInstanceId", processInstanceId);
    	params.put("type", "loan");
    	List<HousBills> loans = housBillsService.getPageListByMap(params);//打款
    	params.put("type", "lend");
    	List<HousBills> lend = housBillsService.getPageListByMap(params);//放款
    	
    	HousLoanfees housLoanfees = housLoanfeesService.getItemInfoByProcessInstanceId(processInstanceId);//返费签单和代收服务费
    	if (lend.size()==0) {	
    		List<Map<String,Object>> collateralList = new ArrayList<>();
    		collateralList.add(collateralManageService.returnCollateralRegistData(processInstanceId));
    		result.put("lend",collateralList);
		}else{
			result.put("lend", lend);//放款单
		}
    	PlBorrowRequirement borrowRequirement =null;
    	if(housLoanfees== null){
    		borrowRequirement = plBorrowRequirementServiceImpl.getInfoByProcessInstanceId(processInstanceId);//返费签单
    		if(borrowRequirement != null ){
    			borrowRequirement.setId(0L);//便于前台判断
    			result.put("housLoanfees", borrowRequirement);
    		}
    	}else{
    		result.put("housLoanfees", housLoanfees);
    	}
    	Map<String, Object> plBorrowRequirement = plBorrowRequirementServiceImpl.getItemInfoByProcessInstanceId(processInstanceId);//查询代收服务费和代收服务费金额
    	result.put("plBorrowRequirement", plBorrowRequirement);
    	result.put("loans", loans);//打款单    	
    	result.put("housBillBasicInfo", housBillBasicInfo);//放款单基本信息
    	
    	if(housBillBasicInfo != null){
    		returnMap.put(Constant.RESPONSE_DATA,result);
			returnMap.put(Constant.RESPONSE_CODE,Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /** 
     * @description 查询贷款信息
     * @param response
     * @param processInstanceId
     * @throws Exception
     * @author fzc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/getLoanInfo.htm")
    public void getLoanInfo(
    		HttpServletResponse response, 
    		@RequestParam(value = "processInstanceId") String processInstanceId) throws Exception{
    	Map<String, Object> result = new HashMap<String, Object>();
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	
//    	Map<String, Object> loanInfo  = housBillsService.getLoanInfo(processInstanceId);
    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("processInstanceId", processInstanceId);
    	
    	params.put("type", "lend");
//    	List<HousBills> loans = housBillsService.getPageListByMap(params);//打款
    	
    	HousLoanfees housLoanfees = housLoanfeesService.getItemInfoByProcessInstanceId(processInstanceId);//返费签单和代收服务费
    	HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);//获取房屋信息
    	Map<String,Object> borrowInfo = plBorrowServiceImpl.getborrowInfo(processInstanceId);
    	result.put("borrow", borrowInfo);
    	Map<String, Object> loanFormInfo  = housLoanfeesService.getLoanFormInfo(processInstanceId);//获取放款表单信息
    	Map<String, Object> plBorrowRequirement = plBorrowRequirementServiceImpl.getItemInfoByProcessInstanceId(processInstanceId);//查询代收服务费和代收服务费金额
    	
    	PlProduct plProduct = plProductService.getItemInfoById(Long.parseLong(plBorrowRequirement.get("productId").toString()));
    	plBorrowRequirement.put("mortgageSituation", plProduct.getName());
    	PubLoan loan = pubLoanService.getItemInfoByProcessInstanceId(processInstanceId);
    	result.put("loan", loan);
    	result.put("plBorrowRequirement", plBorrowRequirement);
    	Map<String, Object> housNotarizationRegistration = notaryManageService.getNotaryRegistDataByInstanceId(processInstanceId);
    	result.put("housNotarizationRegistration", housNotarizationRegistration);
//    	HousBills housBills = loans.get(0);
//    	loanFormInfo.put("financialStaff", getSysUser().getName());//财务专员
//    	loanFormInfo.put("accountHolderName", housBills.getAccountHolderName());
//    	loanFormInfo.put("bankName", housBills.getBankName());
//    	loanFormInfo.put("cardid", housBills.getCardid());
//    	loanFormInfo.put("account", housBills.getAccount());
//    	loanFormInfo.put("thirdBankAccount", housBills.getThirdBankAccount());
//    	loanFormInfo.put("thirdAccountOpening", housBills.getThirdAccountOpening());
//    	loanFormInfo.put("thirdCardNumber", housBills.getThirdCardNumber());
//    	loanFormInfo.put("thirdAccount", housBills.getThirdAccount());
//    	loanFormInfo.put("remark", housBills.getRemark());
//    	loanFormInfo.put("thirdTransferFee", housBills.getThirdTransferFee());
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("processInstanceId", processInstanceId);
    	param.put("type", "loan");

        List<HousBills> lend = housBillsService.getPageListByMap(param);//打款
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("processInstanceId", processInstanceId);
        params1.put("type", "loan");
//    	List<HousBills> loan = housBillsService.getPageListByMap(params1);//打款
    	result.put("loans", loan);//打款单 
    	result.put("lend", lend);
//    	result.put("loanInfo", loanInfo);
    	result.put("housLoanfees", housLoanfees);
    	result.put("loanFormInfo", loanFormInfo);
    	result.put("housPropertyInformation", housPropertyInformation);
    	
    	if(!result.isEmpty()){
			returnMap.put(Constant.RESPONSE_DATA,result);
			returnMap.put(Constant.RESPONSE_CODE,Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}

		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * @description  保存放款单据草稿
     * @param response
     * @param processInstanceId
     * @throws Exception
     * @author fzc
     * @return void
     * @since  1.0.0
     */
    @RequestMapping("/saveLoanBillsInfoDraft.htm")
    public void saveLoanBillsInfoDraft(
    		HttpServletResponse response, 
    		HttpServletRequest request,
    		@RequestParam(value = "params") String params) throws Exception{
    	LoanInfoDataBean loanInfoDataBean = null;
    	//对json对象进行转换
        if (!StringUtils.isEmpty(params)){
        	loanInfoDataBean = JsonUtil.parse(params, LoanInfoDataBean.class);
        }
        loanInfoDataBean.setLoginUserId(getLoginUser(request).getId());
    	loanInfoServiceImpl.insertOrUpdatehousLoanfees(loanInfoDataBean);
    	HousLoanfees housLoanfees = housLoanfeesServiceImpl.getItemInfoByProcessInstanceId(loanInfoDataBean.getProcessInstanceId());
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("processInstanceId", loanInfoDataBean.getProcessInstanceId());
    	param.put("type","loan");
    	List<HousBills> loans = housBillsService.getPageListByMap(param);
    	Map<String, Object> param1 = new HashMap<String, Object>();
    	param1.put("processInstanceId", loanInfoDataBean.getProcessInstanceId());
    	param1.put("type","lend");
    	List<HousBills> lend = housBillsService.getPageListByMap(param1);
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("housLoanfees", housLoanfees);
    	returnMap.put("loans", loans);
    	returnMap.put("lend", lend);
		returnMap.put(Constant.RESPONSE_CODE,Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
    	//返回给页面
        ServletUtils.writeToResponse(response, returnMap);
    }
}
