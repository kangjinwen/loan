package com.company.common.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.company.contract.util.ContractUtil;
import com.company.common.context.Constant;
import com.company.common.context.WorkFlowConstant;
import com.company.common.dao.PubProcessBranchingDao;
import com.company.common.domain.PlBorrowRequirement;
import com.company.common.domain.PubProcessBranching;
import com.company.common.service.PlBorrowRequirementService;
import com.company.common.service.PlBorrowService;
import com.company.common.service.PlProductService;
import com.company.common.utils.DateUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.audit.domain.HousBills;
import com.company.modules.audit.domain.HousControlInformation;
import com.company.modules.audit.domain.HousLoanfees;
import com.company.modules.audit.domain.HousMarriageInformation;
import com.company.modules.audit.service.HousBillsService;
import com.company.modules.audit.service.HousControlInformationService;
import com.company.modules.audit.service.HousCreditInformationService;
import com.company.modules.audit.service.HousFaceTrialService;
import com.company.modules.audit.service.HousLoanfeesService;
import com.company.modules.audit.service.HousMarriageInformationService;
import com.company.modules.common.domain.PlConsult;
import com.company.modules.common.domain.PlProduct;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.service.ComboDataSourceService;
import com.company.modules.common.service.PlConsultService;
import com.company.modules.fel.service.FelService;
import com.company.modules.finance.domain.HousLowerCost;
import com.company.modules.finance.service.HousLowerCostService;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.domain.HousPropertyInformation;
import com.company.modules.instance.service.HousAssessmentAgenciesService;
import com.company.modules.instance.service.HousBorrowingBasicsService;
import com.company.modules.instance.service.HousEnquiryInstitutionService;
import com.company.modules.instance.service.HousPropertyInformationService;
import com.company.modules.notary.service.NotaryManageService;
import com.company.modules.rebate.domain.HousRebate;
import com.company.modules.rebate.service.RebateManageService;
import com.company.modules.repayment.dao.PubRepaymentDao;
import com.company.modules.system.domain.SysUser;
import com.company.modules.warrant.domain.HousIntermediaryInformation;
import com.company.modules.warrant.domain.HousOritoInformation;
import com.company.modules.warrant.domain.HousQuickInformation;
import com.company.modules.warrant.service.HousDataListService;
import com.company.modules.warrant.service.HousIntermediaryInformationService;
import com.company.modules.warrant.service.HousOritoInformationService;
import com.company.modules.warrant.service.HousQuickInformationService;
import com.company.modules.workflow.service.RDZZHistoryService;

/**
 * 导出
 * @author JDM
 *
 */
@Controller
@RequestMapping("/modules/common/action/exportAction")
public class ExportAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(ExportAction.class);
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
	private PlProductService plProductService;
	
	/**
	 * 下户信息表(权证下户)的Service
	 */
	@Autowired
	private HousOritoInformationService housOritoInformationService;
	/**
	 * 房屋快出值信息表(权证下户)的Service
	 */
	@Autowired
	private HousQuickInformationService housQuickInformationService;
	/**
	 * 房屋中介信息(权证下户)的Service
	 */
	@Autowired
	private HousIntermediaryInformationService housIntermediaryInformationService;
	/**
	 * 资料清单表(权证下户)的Service
	 */
	@Autowired
	private HousDataListService housDataListService;

	// @Autowired
	// private HouseholdInvestigateService householdInvestigateService;
	@Autowired
	private HousLowerCostService housLowerCostService;
	@Autowired
	private RDZZHistoryService historyService;
	@Autowired
	private ComboDataSourceService comboDataSourceService;
	@Autowired
	private HousMarriageInformationService housMarriageInformationService;
	@Autowired
	private HousLoanfeesService housLoanfeesService;
	/**
	 * 面审信息表的Service
	 */
	@Autowired
	private HousFaceTrialService housFaceTrialService;
	/**
	 * 风控信息表(面审)的Service
	 */
	@Autowired
	private HousControlInformationService housControlInformationService;
	/**
	 * 征信信息表(面审)的Service
	 */
	@Autowired
	private HousCreditInformationService housCreditInformationService;
	// @Autowired
	// private AuditService faceAuditServiceImpl;
	@Autowired
	private PlBorrowService plBorrowServiceImpl;
	@Autowired
	private HousBillsService housBillsService;
	@Autowired
	private NotaryManageService notaryManageService;
	@Autowired
	private PubProcessBranchingDao pubProcessBranchingDao;
	@Autowired
	private RebateManageService rebateManageService;
	@Autowired
	private PubRepaymentDao repaymentDao;
	@Autowired
    private FelService felServiceImpl;
	
	/** 世联 */
	public static final Byte AGENCIE_SHILIAN = 1;
	/** 仁达 */
	public static final Byte AGENCIE_RENDA = 2;
	/** 链家 */
	public static final Byte AGENCIE_LIANJIA = 3;
	
	/** 筛查机构-建委 */
	public static final Byte INSTITUTION_JIANWEI = 1;
	/** 人法 */
	public static final Byte INSTITUTION_RENFA = 2;
	/** 同盾 */
	public static final Byte INSTITUTION_TONGDUN = 3;
	/** 安融 */
	public static final Byte INSTITUTION_ANRONG = 4;
	/** 工商网 */
	public static final Byte INSTITUTION_GONGSHANGWANG = 5;
	
	/** 应还款时间 */
	protected static final String REPAYMENT_TIME = "repaymentTime";
	/** 实际还款时间 */
	protected static final String REALPAYMENT_TIME = "realpaymentTime";
	
	protected static final BigDecimal HUNDRED = new BigDecimal(100);
	
	/**
	 * 下载项目流转单
	 * @param response
	 * @param request
	 * @param filePath
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void downLoadProjectForm(HttpServletResponse response, 
    		HttpServletRequest request,String filePath) throws FileNotFoundException, IOException{
		String rootDir = request.getRealPath("/");// 文件根目录
		String sep = File.separator;
		String file = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(filePath).toString();
		response.setHeader("Content-Disposition", "attachment;filename=" + new File(file).getName());
		FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(file)),response.getOutputStream());
	}
	

	/**
     * 导出项目流程单
     * @param response
     * @param request
     * @param type 类型 1.个人 2.机构
     * @param exportPoint 导出节点
     * @param processInstanceId
     */
    @RequestMapping("/exportProjectForm.htm")
    public void exportProjectForm(HttpServletResponse response, 
    		HttpServletRequest request,
    		@RequestParam(name="type",required=true) String type,
    		@RequestParam(name="exportPoint",required=true) String exportPoint,
    		@RequestParam(name="processInstanceId",required=true) String processInstanceId){
    	response.setContentType("octets/stream");
    	Map<String, Object> result = new HashMap<>();
    	String sep = File.separator;
    	SysUser loginUser = getLoginUser(request);
    	String rootDir = request.getRealPath("/");// 文件根目录
    	String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
    	String baseDestDir = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(dirName).toString();
    	File baseDestDirFile =  new File(baseDestDir);
		if (!baseDestDirFile.exists()) {
			baseDestDirFile.mkdirs();
		}
		String templeteFile = null;
		String destFile = null;
		try {
			switch (type) {
				case "org":// 项目流转单机构模板文件
					templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("project_flow_sheet_org.docx").toString();
					destFile = new StringBuilder(baseDestDir).append(sep).append("project_flow_sheet_org").append(System.currentTimeMillis()).append(".docx").toString();
					break;
		
				case "personal":// 项目流转单个人模板文件
					templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("project_flow_sheet_personal.docx").toString();
					destFile = new StringBuilder(baseDestDir).append(sep).append("project_flow_sheet_personal").append(System.currentTimeMillis()).append(".docx").toString();
					break;
			}
			initProjectResult(result,processInstanceId);
			//ContractUtil.generateWord(templeteFile, destFile, result);
//			response.setHeader("Content-Disposition", "attachment;filename=" + new File(destFile).getName());
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("项目流转单.docx".getBytes("UTF-8"),"ISO-8859-1"));
			FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(destFile)),response.getOutputStream());
			
//			Map<String, Object> res = new HashMap<String, Object>();
//			res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
//			res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//			res.put(Constant.RESPONSE_DATA, StringUtil.getRelativePath(new File(destFile), new File(new StringBuilder(rootDir).append(sep).append("siliandan").toString())));
//			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			Map<String, Object> res = new HashMap<String, Object>();
			res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
			res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			ServletUtils.writeToResponse(response, res);
		}
    	
    }

	private void initProjectResult(Map<String, Object> result,String processInstanceId) throws Exception {
		PlConsult plConsult = plConsultService.getItemInfoByProcessInstanceId(processInstanceId);
		checkDataExist(plConsult);
		PlBorrowRequirement plBorrowRequirement = plBorrowRequirementService.getInfoByProcessInstanceId(processInstanceId);
		checkDataExist(plBorrowRequirement);
		
		List<HousAssessmentAgencies> housAssessmentAgencies = housAssessmentAgenciesService.getItemInfoByProcessInstanceId(processInstanceId);
		HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService.getItemInfoByProcessInstanceId(processInstanceId);
		checkDataExist(housBorrowingBasics);
		HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
		checkDataExist(housPropertyInformation);
		String houseAddress = housPropertyInformationService.getHousAddress(housPropertyInformation.getPropertyAddressId());
		
		List<HousEnquiryInstitution> housEnquiryInstitutionList = housEnquiryInstitutionService.getItemInfoByProcessInstanceId(processInstanceId);
		
//		HousLoanfees housLoanfees = housLoanfeesService.getItemInfoByProcessInstanceId(processInstanceId);
//    	HousDataList housDataList = housDataListService.getItemInfoByProcessInstanceId(processInstanceId);
		// 下户信息
		HousOritoInformation housOritoInformation = housOritoInformationService.getItemInfoByProcessInstanceId(processInstanceId);
		if (housOritoInformation == null) {
			housOritoInformation = new HousOritoInformation();
		}
    	HousQuickInformation housQuickInformation = housQuickInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	if (housQuickInformation == null) {
    		housQuickInformation = new HousQuickInformation();
		}
    	HousIntermediaryInformation housIntermediaryInformation = housIntermediaryInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	if (housIntermediaryInformation == null) {
    		housIntermediaryInformation = new HousIntermediaryInformation();
		}
    	HousLowerCost housLowerCostInfo = housLowerCostService.getItemInfoByProcessInstanceId(processInstanceId);
    	if (housLowerCostInfo == null) {
    		housLowerCostInfo = new HousLowerCost();
		}
    	HousMarriageInformation housMarriageInformation = housMarriageInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	if (housMarriageInformation == null) {
    		housMarriageInformation = new HousMarriageInformation();
		}
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("processInstanceId", processInstanceId);
    	
		// 报单日期
		result.put("bdrq",DateUtil.getFormatDate(plConsult.getCreateTime()));
		// 面审前确认/填写放款单的商务专员
		result.put("swzy","");
		// 机构名称
		result.put("jgmc",plBorrowRequirement.getInstitutionName());
		// 金融顾问
		result.put("jrgw",plBorrowRequirement.getInstitutionName());
		
		// 对接人
		result.put("djr","");
		result.put("phone",plBorrowRequirement.getMobile());
		// 房产地址
		result.put("address",houseAddress + housPropertyInformation.getPropertyAddress());
		result.put("size",housPropertyInformation.getPropertyArea());
		
		// 评估机构-评估值
		result.put("shilian","");
		result.put("renda","");
		result.put("lianjia","");
		if (CollectionUtils.isNotEmpty(housAssessmentAgencies)) {
			for (HousAssessmentAgencies agencies : housAssessmentAgencies) {
				if (agencies.getAssessmentAgencies()!= null) {
					if (agencies.getAssessmentAgencies() == AGENCIE_SHILIAN) {
						result.put("shilian",agencies.getAssessedValue());
					} else if(agencies.getAssessmentAgencies() == AGENCIE_RENDA){
						result.put("renda",agencies.getAssessedValue());
					} else if(agencies.getAssessmentAgencies() == AGENCIE_LIANJIA){
						result.put("lianjia",agencies.getAssessedValue());
					}
				}
			}
		}
		
		// 房产性质
		result.put("fcxz","");
		Map<String, Object> params = new HashMap<String, Object>();
    	params.put("typeCode","PROPERTY_TYPE");
    	List<Map<String, Object>> dictList = comboDataSourceService.queryDic(params);
    	if (CollectionUtils.isNotEmpty(dictList) && housPropertyInformation != null) {
			for (Map<String, Object> map : dictList) {
				if (housPropertyInformation.getPropertyProperties()!= null
						&& map.get("value").equals(housPropertyInformation.getPropertyProperties().toString())) {
					result.put("fcxz",map.get("text"));
				}
			}
		}
		
    	// 婚姻状况
		result.put("hyzk","");
    	params.put("typeCode","MARITAL_STATUS");
    	dictList = comboDataSourceService.queryDic(params);
    	if (CollectionUtils.isNotEmpty(dictList) && housMarriageInformation != null) {
			for (Map<String, Object> map : dictList) {
				if (housMarriageInformation.getMaritalStatus() != null 
						&& map.get("value").equals(housMarriageInformation.getMaritalStatus().toString())) {
					result.put("hyzk",map.get("text"));
				}
			}
		}
    	
    	// 房屋现状
		result.put("fwxz","");
    	params.put("typeCode","HOUSING_STATUS");
    	dictList = comboDataSourceService.queryDic(params);
    	if (CollectionUtils.isNotEmpty(dictList) && housPropertyInformation != null) {
			for (Map<String, Object> map : dictList) {
				if (housPropertyInformation.getPropertySituation()!= null 
						&& map.get("value").equals(housPropertyInformation.getPropertySituation().toString())) {
					result.put("fwxz",map.get("text"));
				}
			}
		}
    	// 一抵
    	if (housPropertyInformation.getWhetherOneContact() != null && housPropertyInformation.getWhetherOneContact() == 0) {
    		result.put("yidi","√");
		} else{
			result.put("yidi","");
		}
    	
    	PlProduct product = plProductService.getItemInfoById(plBorrowRequirement.getProductId());
    	result.put("erdi","");
    	if (product.getName().contains("房产二抵")) {
    		result.put("erdi","√");
		}
    	// TODO 垫资
    	result.put("dianzi","");
    	
    	
    	// 居住情况
		result.put("jzqk","");
    	params.put("typeCode","LIVE_STATE");
    	dictList = comboDataSourceService.queryDic(params);
    	if (CollectionUtils.isNotEmpty(dictList) && housOritoInformation != null) {
			for (Map<String, Object> map : dictList) {
				if (housOritoInformation.getLivingConditions()!= null 
						&& map.get("value").equals(housOritoInformation.getLivingConditions().toString()) ) {
					result.put("jzqk",map.get("text"));
				}
			}
		}
    	
    	
    	// 装修状况
		result.put("zxzk","");
		params.put("typeCode","FURNISHING_STATUS");
    	dictList = comboDataSourceService.queryDic(params);
    	if (CollectionUtils.isNotEmpty(dictList) && housOritoInformation != null) {
			for (Map<String, Object> map : dictList) {
				if (housOritoInformation.getFurnishingStatus()!= null 
						&& map.get("value").equals(housOritoInformation.getFurnishingStatus().toString())) {
					result.put("zxzk",map.get("text"));
				}
			}
		}
		
    	//放弃优先购买权
    	if (housOritoInformation.getPurchasingPower()!= null && housOritoInformation.getPurchasingPower()==0) {
    		result.put("fqyxgmq","已签");
		}else{
			result.put("fqyxgmq","未签");
		}
    	
    	//地址一致
    	if (housOritoInformation.getBuildingBrands()!= null && housOritoInformation.getBuildingBrands()==0) {
    		result.put("dzyz","√");
		}else{
			result.put("dzyz","");
		}
    	
    	
		// 贷款人姓名
		result.put("loanName",housBorrowingBasics.getName());
		result.put("loanIdCard",housBorrowingBasics.getCertNumber());
		result.put("spouse",housBorrowingBasics.getSpouseName());
		result.put("spouseIdCard",housBorrowingBasics.getSpouseCertNumber());
		result.put("gjr",housBorrowingBasics.getTotalBorrowed());
		result.put("gjrIdCard",housBorrowingBasics.getTotalBorrowedCertNumber());
		// 申请金额
		result.put("sqje",plBorrowRequirement.getAccount());
		result.put("jkqx",plBorrowRequirement.getTimeLimit());
		checkRateBigDecimal(result,"cdll",plBorrowRequirement.getSingleRate());
		
		// 服务费率
		checkRateBigDecimal(result,"fwfl",plBorrowRequirement.getCollectionRate());
		
		// 服务费
		result.put("fwf",plBorrowRequirement.getCollectionServiceFee());
		
		
		// 新增申请备注
		result.put("xzsqbz",housBorrowingBasics.getRemark());
		// TODO 意向垫资费用
		result.put("yxdzfy","");
		
		paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_COLLECTASSESSMENTFEE);
    	// 下户费信息
    	Map<String, Object> collectAssessmentProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
		// 下户费收取时间
		result.put("xhfsqsj",collectAssessmentProcessInfo.get("endTime"));
		//缴纳人
		result.put("jnr",housLowerCostInfo.getPayPerson());
		// 房产证号
		result.put("fczh",housPropertyInformation.getHouseNumber());
		
		result.put("rfcx","");
		result.put("anrongbz","");
		result.put("tongdun","");
		result.put("jwbz","");
		if (CollectionUtils.isNotEmpty(housEnquiryInstitutionList)) {
			for (HousEnquiryInstitution housEnquiryInstitution : housEnquiryInstitutionList) {
				if (housEnquiryInstitution.getInstitutionType() == INSTITUTION_RENFA) {
					result.put("rfcx",housEnquiryInstitution.getRemark());
				} else if(housEnquiryInstitution.getInstitutionType() == INSTITUTION_ANRONG){
					result.put("anrongbz",housEnquiryInstitution.getRemark());
				} else if(housEnquiryInstitution.getInstitutionType() == INSTITUTION_TONGDUN){
					result.put("tongdun",housEnquiryInstitution.getRemark());
				} else if(housEnquiryInstitution.getInstitutionType() == INSTITUTION_JIANWEI){
					result.put("jwbz",housEnquiryInstitution.getRemark());
				}
			}
		}
		
		
    	paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_CUSTOMERINVESTIGATE);
    	// 信息筛查信息
    	Map<String, Object> investigateProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
		result.put("xxsczy",investigateProcessInfo.get("name"));
		result.put("xxscrq",investigateProcessInfo.get("endTime"));
		
		// 下户
		paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_HOUSEHOLDINVESTIGATE);
    	// 权证下户信息
    	Map<String, Object> housInvestigateProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
		result.put("xhjssj",housInvestigateProcessInfo.get("endTime"));
		result.put("qzxhr",housInvestigateProcessInfo.get("name"));
		
		// ***** 快出值信息 ******
		result.put("kczbz",housIntermediaryInformation.getRemark());
		result.put("marketValue",housIntermediaryInformation.getNormalPrice());
		result.put("sksq",housIntermediaryInformation.getTaxDetails());
		// 快出值
		result.put("kcz",housIntermediaryInformation.getHousingValueFaster());
		
		
		// 核行信息
		result.put("hehangbz",housQuickInformation.getRemark());
		
		
		
		// 权证核行
		paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_HOUSECHECKBANK);
    	Map<String, Object> houseCheckBankProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
    	if (houseCheckBankProcessInfo != null) {
    		result.put("qzhhr",houseCheckBankProcessInfo.get("name"));
		}else {
			result.put("qzhhr","");
		}
		
		
		// 垫资调档备注
		result.put("dzddbz","");
		result.put("dzddr","");
		
		
		// 面审
		paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_FACE_AUDIT);
    	// 信息筛查信息
    	Map<String, Object> faceAuditProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
		result.put("msjsrq",faceAuditProcessInfo.get("endTime"));
		result.put("zzje",plBorrowRequirement.getAccount());
		result.put("zzqx",plBorrowRequirement.getTimeLimit());
		checkRateBigDecimal(result,"zzddw",plBorrowRequirement.getRepaymentRate());
		checkRateBigDecimal(result,"zzcdll",plBorrowRequirement.getSingleRate());
		
		nullEntryToEmptyString(result);
	}
	
	
	
	private void checkDataExist(Object object) throws ServiceException {
		if (object == null) {
			throw new ServiceException("未找到对应流转单信息");
		}
	}
	
	/**
	 * 下载财务流转单
	 * @param response
	 * @param request
	 * @param filePath
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void downLoadFinancialForm(HttpServletResponse response, 
    		HttpServletRequest request,String filePath) throws FileNotFoundException, IOException{
		String rootDir = request.getRealPath("/");// 文件根目录
		String sep = File.separator;
		String file = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(filePath).toString();
		response.setHeader("Content-Disposition", "attachment;filename=" + new File(file).getName());
		FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(file)),response.getOutputStream());
	}

	/**
	 * 财务内部流转单
	 * @param response
	 * @param request
	 * @param type 类型normal.正常贷款单  extension.展期贷款单
	 * @param exportPoint 导出节点
	 * @param processInstanceId
	 */
	@RequestMapping("/exportFinancialForm.htm")
    public void exportFinancialForm(HttpServletResponse response, 
    		HttpServletRequest request,
    		@RequestParam(name="type",required=true) String type,
    		@RequestParam(name="exportPoint",required=true) String exportPoint,
    		@RequestParam(name="processInstanceId",required=true) String processInstanceId){
		response.setContentType("octets/stream");
    	Map<String, Object> result = new HashMap<>();
    	String sep = File.separator;
    	SysUser loginUser = getLoginUser(request);
    	String rootDir = request.getRealPath("/");// 文件根目录
    	String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
    	String baseDestDir = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append(dirName).toString();
    	File baseDestDirFile =  new File(baseDestDir);
		if (!baseDestDirFile.exists()) {
			baseDestDirFile.mkdirs();
		}
		String templeteFile = null;
		String destFile = null;
		try {
			switch (type) {
				case "normal":// 正常财务流转单模板文件
					templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("financial_internal_transfer_sheet.docx").toString();
					destFile = new StringBuilder(baseDestDir).append(sep).append("financial_internal_transfer_sheet").append(System.currentTimeMillis()).append(".docx").toString();
					break;
		
				case "extension":// 展期财务流转单模板文件
					templeteFile = new StringBuilder(rootDir).append(sep).append("siliandan").append(sep).append("financial_internal_transfer_ex_sheet.docx").toString();
					destFile = new StringBuilder(baseDestDir).append(sep).append("financial_internal_transfer_ex_sheet").append(System.currentTimeMillis()).append(".docx").toString();
					break;
			}
			initFinancialResult(result,exportPoint,processInstanceId);
			//ContractUtil.generateWord(templeteFile, destFile, result);
//			response.setHeader("Content-Disposition", "attachment;filename=" + new File(destFile).getName());
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("财务流转单.docx".getBytes("UTF-8"),"ISO-8859-1"));
			FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(destFile)),response.getOutputStream());
			
//			Map<String, Object> res = new HashMap<String, Object>();
//			res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
//			res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//			res.put(Constant.RESPONSE_DATA, StringUtil.getRelativePath(new File(destFile), new File(new StringBuilder(rootDir).append(sep).append("siliandan").toString())));
//			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			Map<String, Object> res = new HashMap<String, Object>();
			res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
			res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			ServletUtils.writeToResponse(response, res);
		}
    	
    }

	private void initFinancialResult(Map<String, Object> result, String exportPoint, String processInstanceId) throws Exception {
		String branchingProcessId = null;
		if (exportPoint.equals(WorkFlowConstant.PROCESS_STATUS_RETURN_COMMISSION)) {//返佣
			PubProcessBranching pubProcessBranching = pubProcessBranchingDao.getItemInfoByBranchingProcessInstanceId(processInstanceId);
			processInstanceId = pubProcessBranching.getProcessInstanceId();
			branchingProcessId = pubProcessBranching.getBranchingProcessId();
		}
		
		PlBorrowRequirement plBorrowRequirement = plBorrowRequirementService.getInfoByProcessInstanceId(processInstanceId);
		checkDataExist(plBorrowRequirement);
		HousBorrowingBasics housBorrowingBasics = housBorrowingBasicsService.getItemInfoByProcessInstanceId(processInstanceId);
		checkDataExist(housBorrowingBasics);
		HousPropertyInformation housPropertyInformation = housPropertyInformationService.getItemInfoByProcessInstanceId(processInstanceId);
		checkDataExist(housPropertyInformation);
		String houseAddress = housPropertyInformationService.getHousAddress(housPropertyInformation.getPropertyAddressId());
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("type", "loan");
    	// 打款信息
    	List<HousBills> loans = housBillsService.getPageListByMap(paramMap);// 放款卡
    	if (CollectionUtils.isEmpty(loans)) {
    		result.put("fkBankInfoList",new ArrayList<>());
		}else {
			result.put("fkBankInfoList",loans);
		}
    	paramMap.put("type", "lend");
    	List<HousBills> lends = housBillsService.getPageListByMap(paramMap);// 三方卡
		
		HousBills housBills = null;
		if (CollectionUtils.isNotEmpty(lends)) {
			housBills = lends.get(0);
		}
		
		HousLoanfees housLoanfees = housLoanfeesService.getItemInfoByProcessInstanceId(processInstanceId);
		if (housLoanfees == null) {
			housLoanfees = new HousLoanfees();
		}
    	HousMarriageInformation housMarriageInformation = housMarriageInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	if (housMarriageInformation == null) {
    		housMarriageInformation = new HousMarriageInformation();
		}
    	// 风控信息表(面审)实体
    	HousControlInformation housControlInformation = housControlInformationService.getItemInfoByProcessInstanceId(processInstanceId);
    	if (housControlInformation == null) {
    		housControlInformation = new HousControlInformation();
		}
//		PlConsult plConsult = plConsultService.getItemInfoByProcessInstanceId(processInstanceId);
//    	List<HousEnquiryInstitution> housEnquiryInstitutionList = housEnquiryInstitutionService.getItemInfoByProcessInstanceId(processInstanceId);
//    	// 面审信息表实体
//    	HousFaceTrial housFaceTrial = housFaceTrialService.getItemInfoByProcessInstanceId(processInstanceId);
//    	// 下户信息
//		HousOritoInformation housOritoInformation = housOritoInformationService.getItemInfoByProcessInstanceId(processInstanceId);
//		// 快出值
//		HousQuickInformation housQuickInformation = housQuickInformationService.getItemInfoByProcessInstanceId(processInstanceId);
//    	// 房屋中介信息(权证下户)实体
//		HousIntermediaryInformation housIntermediaryInformation = housIntermediaryInformationService.getItemInfoByProcessInstanceId(processInstanceId);
//		List<HousAssessmentAgencies> housAssessmentAgencies = housAssessmentAgenciesService.getItemInfoByProcessInstanceId(processInstanceId);
//    	// 资料清单表(权证下户)实体
//		HousDataList housDataList = housDataListService.getItemInfoByProcessInstanceId(processInstanceId);
//    	// 下户费表实体
//		HousLowerCost housLowerCostInfo = housLowerCostService.getItemInfoByProcessInstanceId(processInstanceId);
//   	 	// 征信信息表(面审)实体
//    	HousCreditInformation housCreditInformation = housCreditInformationService.getItemInfoByProcessInstanceId(processInstanceId);
//   	 	Map<String,Object> borrowInfo = plBorrowServiceImpl.getborrowInfo(processInstanceId);
    	
    	
   	 	Map<String, Object> housNotarizationRegistration = notaryManageService.getNotaryRegistDataByInstanceId(processInstanceId);
   	 	
    	List<Map<String, Object>> dictList = new ArrayList<>();
    	
    	
    	paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_LOAN);
    	// 放款信息
    	Map<String, Object> loanProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
    	
    	Map<String, Object> loanFormInfo  = housLoanfeesService.getLoanFormInfo(processInstanceId);//获取放款表单信息
    	if (loanFormInfo != null) {
    		try {
    			result.put("loanDate",DateUtil.getFormatDate((Date)loanFormInfo.get("loanTime")));
			} catch (Exception e) {
				result.put("loanDate","");
			}
		}else {
			result.put("loanDate","");
		}
    	
		// 填写放款单的商务专员
    	if (loanProcessInfo == null) {
    		result.put("fkdswzy","");
		}else {
			result.put("fkdswzy",loanProcessInfo.get("name"));
		}
		
		// 机构名称
		result.put("jgmc",plBorrowRequirement.getInstitutionName());
		// 金融顾问
		result.put("jrgw",plBorrowRequirement.getSalesman());
		
		// 婚姻状况
		result.put("hyzk","");
		
		paramMap.put("typeCode","MARITAL_STATUS");
    	dictList = comboDataSourceService.queryDic(paramMap);
    	if (CollectionUtils.isNotEmpty(dictList) && housMarriageInformation != null) {
			for (Map<String, Object> map : dictList) {
				System.out.println(map.get("value"));
				if (housMarriageInformation.getMaritalStatus() != null 
						&& map.get("value").equals(housMarriageInformation.getMaritalStatus().toString()) ) {
					result.put("hyzk",map.get("text"));
				}
			}
		}
    	// 贷款人姓名
		result.put("loanName",housBorrowingBasics.getName());
		result.put("loanIdCard",housBorrowingBasics.getCertNumber());
		result.put("phone",plBorrowRequirement.getMobile());
		result.put("sex",housBorrowingBasics.getSex());
		result.put("age",housBorrowingBasics.getAge());
		// 配偶
		result.put("spouse",housBorrowingBasics.getSpouseName());
		result.put("spouseIdCard",housBorrowingBasics.getSpouseCertNumber());
		// 共借人
		result.put("gjr",housBorrowingBasics.getTotalBorrowed());
		result.put("gjrIdCard",housBorrowingBasics.getTotalBorrowedCertNumber());
		
		// 房产地址
		result.put("address",houseAddress + housPropertyInformation.getPropertyAddress());
		// 房产证号
		result.put("fczh",housPropertyInformation.getHouseNumber());
		result.put("size",housPropertyInformation.getPropertyArea());
		
		// 面审-风控信息-评估值
		result.put("pgz",housControlInformation.getAssessedValue());
    	
    	// 一抵
    	if (housPropertyInformation.getWhetherOneContact() != null && housPropertyInformation.getWhetherOneContact() == 0) {
    		result.put("yidi","√");
		} else{
			result.put("yidi","");
		}
    	
    	PlProduct product = plProductService.getItemInfoById(plBorrowRequirement.getProductId());
    	result.put("erdi","");
    	if (product.getName().contains("房产二抵")) {
    		result.put("erdi","√");
		}
    	// 转单
    	result.put("zhuandan","");
    	if (product.getName().contains("转单")) {
    		result.put("zhuandan","√");
		}
    	
    	// 一抵，二抵，转单银行
    	result.put("againstBankName",housControlInformation.getAgainstBankName());
    	result.put("twoAgainstBankName",housControlInformation.getTwoAgainstBankName());
    	result.put("threeAgainstBankName",housControlInformation.getThreeAgainstBankName());
    	
    	
		// 申请金额
		result.put("sqje",plBorrowRequirement.getAccount());
		result.put("jkqx",plBorrowRequirement.getTimeLimit());
		
		checkRateBigDecimal(result,"cdll",plBorrowRequirement.getSingleRate());
		checkRateBigDecimal(result,"ddll",plBorrowRequirement.getRepaymentRate());
		
		// 金额（首期利息）
		result.put("initialInterest",plBorrowRequirement.getAccount().multiply(plBorrowRequirement.getSingleRate()));
		
		// 首期扣款（转账手续费）
		if (housBills != null) {
			result.put("transferFee",housBills.getThirdTransferFee());
		}else {
			result.put("transferFee","");
		}
		
		// 代收服务费
		result.put("dsfwf",plBorrowRequirement.getCollectionServiceFee());
		result.put("dsfwfName",plBorrowRequirement.getCollectionServiceName());
		result.put("dsfwfBankName",plBorrowRequirement.getCollectionServiceBank());
		result.put("dsfwfBankCard",plBorrowRequirement.getCollectionServiceCard());
		
		// ******放款单填写信息*****
    	if (CollectionUtils.isNotEmpty(lends)) {
    		// 放款卡
    		result.put("fkName", lends.get(0).getAccountHolderName());
    		result.put("fkBankName", lends.get(0).getBankName());
    		result.put("fkkh", lends.get(0).getCardid());
    		result.put("fkje", lends.get(0).getAccount());
    		
    		// 三方账户
    		result.put("sfkName", lends.get(0).getThirdBankAccount());
    		result.put("sfkBankName", lends.get(0).getThirdAccountOpening());
    		result.put("sfkh", lends.get(0).getThirdCardNumber());
    		result.put("sfje", lends.get(0).getThirdAccount());
		}else {
			// 放款卡
			result.put("fkName", "");
			result.put("fkBankName", "");
			result.put("fkkh", "");
			result.put("fkje", "");
			
			// 三方账户
			result.put("sfkName", "");
			result.put("sfkBankName", "");
			result.put("sfkh", "");
			result.put("sfje", "");
		}
		
		// TODO 上家账户（垫资）
		result.put("dzsw", "");
		result.put("dzBankName", "");
		result.put("dzkh", "");
		result.put("dzje", "");
		
		// 返费底点利率
		checkRateBigDecimal(result,"ffddll",housLoanfees.getReturnRate());
		
		// 返费金额
		result.put("ffje", housLoanfees.getReturnFee());
		result.put("licha", "");
		// 返费姓名
		result.put("returnFeeName", housLoanfees.getSalesman());
		result.put("returnFeeBank", housLoanfees.getReturnBank());
		result.put("returnFeeBankCard", housLoanfees.getReturnCard());
		
		paramMap.put("processStatus", WorkFlowConstant.PROCESS_STATUS_RETURN_COMMISSION);
		List<HousRebate> housRebates = rebateManageService.getHousRebateListByMap(paramMap);
		if (CollectionUtils.isEmpty(housRebates)) {
			housRebates = new ArrayList<>();
		}
		// 返佣信息
		result.put("returnFeeList",housRebates);
		
		
		// TODO 垫资信息
		result.put("dzje", "");
		result.put("dzqx", "");
		result.put("dzll", "");
		result.put("sjdzfy", "");
		
		
		// 罚息信息
		result.put("dkbj",plBorrowRequirement.getAccount());
		checkRateBigDecimal(result,"fxlv",product.getOverduePenaltyRate());
		// 还款计划详情
		List<Map<String, Object>> repaymentDetailList = repaymentDao.queryAllOverdue(paramMap);
		if (CollectionUtils.isNotEmpty(repaymentDetailList)) {
			for (Map repay : repaymentDetailList) {
				int overdueDay = calculateOverdueDays((Date) repay.get(REPAYMENT_TIME),(Date) repay.get(REALPAYMENT_TIME));
				repay.put("yqts", overdueDay);
				repay.put("sjfxje", repay.get("realInterest"));
			}
			result.put("rpdList", repaymentDetailList);
		}else {
			result.put("rpdList", new ArrayList<>());
		}
		
    	paramMap.put("taskDefKey",WorkFlowConstant.PROCESS_STATUS_WRITELOANINFO);
    	// 放款单填写信息
    	Map<String, Object> writeLoanProcessInfo = historyService.queryHistoryTaskInfo(paramMap);
		// 出借人
		result.put("cjr",housNotarizationRegistration.get("customerName"));
		// 放款单填写的商务专员
		if (writeLoanProcessInfo == null) {
			result.put("fkdswzy","");
		}else {
			result.put("fkdswzy",writeLoanProcessInfo.get("name"));
		}
		
		nullEntryToEmptyString(result);
	}
    
	private void checkRateBigDecimal(Map<String, Object> result, String key, BigDecimal temp) {
		if (temp == null) {
			result.put(key, "");
		}else {
			result.put(key, temp.multiply(HUNDRED));
		}
	}


	private void nullEntryToEmptyString(Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet()) {
        	Object obj = entry.getValue();
    		if (obj == null) {
    			entry.setValue(StringUtils.EMPTY);
    		}
    		if (obj instanceof BigDecimal) {
    			BigDecimal value = (BigDecimal) obj;
                entry.setValue(value.setScale(2, BigDecimal.ROUND_CEILING));
    		}
        }
    }
	
	public BigDecimal getZeroBigDecimal(){
		BigDecimal temp = new BigDecimal(0);
		return temp;
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
