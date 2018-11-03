package com.company.modules.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.domain.PageBean;
import com.company.common.utils.MenuNodeProperties;
import com.company.modules.common.domain.ExportHousOritoInformation;
import com.company.modules.common.domain.HouseCheckBank;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysRoleDao;
import com.company.modules.system.domain.SysRole;
import com.company.modules.workflow.dao.RDZZTaskDao;
import com.company.modules.workflow.service.RDZZTaskService;

@Service
public class RDZZTaskServiceImpl implements RDZZTaskService {
	
	private static final String USERTASK_HOUSEHOLDTASKASSIGN= "usertask-householdTaskAssign";//下户任务分配
	private static final String USERTASK_HOUSEHOLDINVESTIGATE= "usertask-householdInvestigate";//权证下户
	private static final String USERTASK_HOUSEHOLDINVESTIGATE_TWO= "usertask-householdInvestigateTwo";//权证下户
	private static final String USERTASK_HOUSECHECKBANK= "usertask-houseCheckBank";//权证核行
	private static final String USERTASK_HOUSECHECKBANK_TWO= "usertask-houseCheckBankTwo";//权证核行
	private static final String USERTASK_COLLECTASSESSMENTFEE= "usertask-collectAssessmentFee";//下户费收取
	private static final String USERTASK_LOANING_NOTARIZE= "usertask-loaning-notarize";//垫资客服确认
	private static final String USERTASK_EXTENSION_CUSTOMER_SURVEY= "usertask-extension-customer-survey";//客户调查(展期)
	private static final String USERTASK_EXTENSION_CUSTOMER_SURVEYTWO= "usertask-extension-customer-surveyTwo";//客户调查(展期)
	private static final String USERTASK_EXTENSION_COLLATERALASSESS= "usertask-extension-collateralAssess";//展期房产评估
	private static final String USERTASK_NOTARIZATION_ASSIGN= "usertask-notarization-assign";//展期公证分配
	private static final String USERTASK_EXCESSIVE_NOTARIZATION= "usertask-excessive-notarization";//展期超额公证分配
	private static final String USERTASK_EXCESSIVE_MORTGAGE= "usertask-excessive-mortgage";//展期超额抵押
	private static final String USERTASK_SIGN_EXTENDEDCONTRACT= "usertask-sign-extendedContract";//展期合同
	private static final String USERTASK_GATHER_EXTENDEDFEE= "usertask-gather-extendedfee";//展期费管理
	private static final String USERTASK_DEDUCT= "usertask-deduct";//展期费划扣
	private static final String USERTASK_EXTENDED_AUDIT= "usertask-extended-audit";//展期审批
	private static final String USERTASK_RETURNFEE= "usertask-reutrnFee";//下户费收取
	private static final String USERTASK_FACE_AUDIT= "usertask-face-audit";//面审
	private static final String USERTASK_FINAL_AUDIT= "usertask-final-audit";//终审
	private static final String USERTASK_RECHECK= "usertask-recheck";//复审
	private static final String USERTASK_WRITELOANINFO= "usertask-writeLoanInfo";//放款单据填写
	private static final String USERTASK_LOANCONFIRM= "usertask-loanConfirm";//放款确认
	private static final String USERTASK_LOAN= "usertask-loan";//放款确认
	private static final String USERTASK_RECHECK_REFUSE= "usertask-recheck-refuse";//复审驳回
	private static final String USERTASK_FACE_REVIEW="usertask-face-review";//放款复审 (上海房贷)
	private static final String finalAuditStaffID= "272";//终审专员roleid
	private static final String customerServiceStaffBID= "267";//商务专员roleid
	private static final String systemID= "1";//管理员roleid
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private RDZZTaskDao taskDao;
	@Autowired
	private SysMenuService sysMenuService;


	@Override
	public Map<String, Object> getHisTaskInfoByTaskId(String taskId) {
		Map<String, Object> map = taskDao.getHisTaskInfoByTaskId(taskId);
		if (null==map){
			throw new RDRuntimeException("该任务不存在");
		}
		return map;
	}

	@Override
	public int modifyHisTaskAssigneeByAnyKey(String taskId, String userId) {
		int res = taskDao.modifyHisTaskAssigneeByAnyKey(taskId,userId);
		return res;
	}

	public  int modifyTaskAssigneeByAnyKey(String taskId, String userId){
		int res = taskDao.modifyTaskAssigneeByAnyKey(taskId,userId);
		return res;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryHouseholdTasks(long roleId, Map<String, Object> params, boolean isCompleted,int currentPage,int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_HOUSEHOLDTASKASSIGN);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> householdTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				householdTasks = taskDao.queryDoneHouseholdTasks(params);
			} else {
				householdTasks = taskDao.queryUndoHouseholdTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(householdTasks);
		return page;
	}
	
	
	@Override
	public PageInfo<Map<String, Object>> queryHouseholdInvestigate(Long roleId,
			Map<String, Object> params, boolean isCompleted, int currentPage,
			int pageSize) throws ServiceException{
		//params.put("taskDefinition", USERTASK_HOUSEHOLDINVESTIGATE);
		params.put("taskDefinitionArray", new String[]{USERTASK_HOUSEHOLDINVESTIGATE,USERTASK_HOUSEHOLDINVESTIGATE_TWO});
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> householdTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				householdTasks = taskDao.queryDoneHouseholdInvestigateTasks(params);
			} else {
				householdTasks = taskDao.queryUndoHouseholdInvestigateTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(householdTasks);
		return page;
	}
	
	@Override
	public List<ExportHousOritoInformation> queryHouseholdInvestigate(Long roleId,
			Map<String, Object> params) throws ServiceException{
		params.put("taskDefinitionArray", new String[]{USERTASK_HOUSEHOLDINVESTIGATE,USERTASK_HOUSEHOLDINVESTIGATE_TWO});
		String roleName = getRoleName(roleId);
		List<ExportHousOritoInformation> householdTasks = new ArrayList<ExportHousOritoInformation>();
		try {
			params.put("nid", roleName);
			params.put("isCompleted",true);
			householdTasks = taskDao.queryExportHousOritoInformationTasks(params);
//			if (isCompleted) {
//				params.put("isCompleted", isCompleted);
//				householdTasks = taskDao.queryDoneHouseholdInvestigateTasks(params);
//			} else {
//				householdTasks = taskDao.queryUndoHouseholdInvestigateTasks(params);
//			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		return householdTasks;
	}

	@Override
	public PageInfo<Map<String, Object>> queryHouseCheckBank(Long roleId,
			Map<String, Object> params, boolean isCompleted, int currentPage,
			int pageSize) throws ServiceException{
		//params.put("taskDefinition", USERTASK_HOUSECHECKBANK);
		params.put("taskDefinitionArray", new String[]{USERTASK_HOUSECHECKBANK,USERTASK_HOUSECHECKBANK_TWO});
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> householdTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				householdTasks = taskDao.queryDoneHouseholdInvestigateTasks(params);
			} else {
				householdTasks = taskDao.queryUndoHouseholdInvestigateTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(householdTasks);
		return page;
	}
	
	@Override
	public List<HouseCheckBank> queryHouseCheckBank(Long roleId,
			Map<String, Object> params) throws ServiceException{
		params.put("taskDefinitionArray", new String[]{USERTASK_HOUSECHECKBANK,USERTASK_HOUSECHECKBANK_TWO});
		String roleName = getRoleName(roleId);
		List<HouseCheckBank> householdTasks = new ArrayList<HouseCheckBank>();
		try {
			params.put("nid", roleName);
			params.put("isCompleted", true);
			householdTasks = taskDao.queryExportDoneHouseholdInvestigateTasks(params);
//			if (isCompleted) {
//				params.put("isCompleted", isCompleted);
//				householdTasks = taskDao.queryDoneHouseholdInvestigateTasks(params);
//			} else {
//				householdTasks = taskDao.queryUndoHouseholdInvestigateTasks(params);
//			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		return householdTasks;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryInstanceTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryDoneInstanceTasks(params);
			} else {
				params.put("isCompleted", isCompleted);

				result = taskDao.queryUndoInstanceTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryInstanceInfoTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
//			if (isCompleted) {
//				result = taskDao.queryDoneInstanceInfoTasks(params);
//			} else {
//				result = taskDao.queryUndoInstanceInfoTasks(params);
//			}
			result = taskDao.queryNewConsultList(params);
			
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryConnectionTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryDoneConnectionTasks(params);
			} else {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryUndoConnectionTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}
	
	@Override
	public PageInfo<Map<String, Object>> querySurveyTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryDoneSurveyTasks(params);
			} else {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryUndoSurveyTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryConfirmationTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryDoneConfirmationTasks(params);
			} else {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryUndoConfirmationTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryOritoConfirmationTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryDoneOritoConfirmationTasks(params);
			} else {
				result = taskDao.queryUndoOritoConfirmationTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}
	
	@Override
	public PageInfo<Map<String, Object>> queryContractTasks(Map<String, Object> params, int currentPage, int pageSize, boolean isCompleted)
			throws Exception {
		List<Map<String, Object>> result = null;
		try {
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				result = taskDao.queryDoneContractTasks(params);
			} else {
				result = taskDao.queryUndoContractTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(result);
		return page;
	}

	private void setFilterParams(PageBean pageBean, Map<String, Object> params) {
		// 额外的查询参数
		if (pageBean.getArg() != null) {
			params.putAll(pageBean.getArg());
		}
	}

	private String getRoleName(Long roleId) throws ServiceException {
		SysRole role = null;
		try {
			role = sysRoleDao.getByPrimary(roleId);
		} catch (Exception e) {
			throw new ServiceException("角色查询失败:" + e.getMessage(), e);
		}
		return role.getNid();
	}


	@Override
	public PageInfo<Map<String, Object>> queryhousLowerCost(Long roleId,
			Map<String, Object> params, boolean isCompleted, int currentPage,
			int pageSize) throws ServiceException{
		params.put("taskDefinition", USERTASK_COLLECTASSESSMENTFEE);
		params.put("taskReturnFee", USERTASK_RETURNFEE);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> householdTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				householdTasks = taskDao.queryDoneHousLowerCostTasks(params);
			} else {
				householdTasks = taskDao.queryUndoHousLowerCostTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(householdTasks);
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> queryAuditTasks(Long roleId,
			Map<String, Object> params, boolean isCompleted, int currentPage,
			int pageSize, String type) throws ServiceException {
		if(USERTASK_FACE_AUDIT.equals(type)){//面审
			params.put("taskDefinition", USERTASK_FACE_AUDIT);
		}else if(USERTASK_FINAL_AUDIT.equals(type)){//终审
			if(finalAuditStaffID.equals(String.valueOf(roleId))){
				params.put("taskDefinition", USERTASK_FINAL_AUDIT);
			}else if(customerServiceStaffBID.equals(String.valueOf(roleId))){
				params.put("taskDefinition", USERTASK_RECHECK_REFUSE);
			}else if(systemID.equals(String.valueOf(roleId))){
				params.put("taskDefinition", USERTASK_FINAL_AUDIT);
			}			
		}else if(USERTASK_WRITELOANINFO.equals(type)){
			params.put("taskDefinition", USERTASK_WRITELOANINFO);
		}else if(USERTASK_LOANCONFIRM.equals(type)){
			params.put("taskDefinition", USERTASK_LOANCONFIRM);
		}else if(USERTASK_LOAN.equals(type)){
			params.put("taskDefinition", USERTASK_LOAN);
		}else if(USERTASK_FACE_REVIEW.equals(type)){
			params.put("taskDefinition", USERTASK_FACE_REVIEW);
		}else{
			params.put("taskDefinition", USERTASK_RECHECK);
		}
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> householdTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				householdTasks = taskDao.queryDoneAuditTasks(params);
			} else {
				householdTasks = taskDao.queryUndoAuditTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(householdTasks);
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> queryPreSetRepayPlanTasks(Long roleId,
														 Map<String, Object> params, boolean isCompleted, int currentPage,
														 int pageSize, String type) throws ServiceException {
		if(USERTASK_FACE_AUDIT.equals(type)) {//放款管理
			params.put("taskDefinition", USERTASK_LOAN);
		}

		List<Map<String,Object>> householdTasks = new ArrayList<Map<String,Object>>();
		try {

			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				householdTasks = taskDao.queryDoneAuditTasks(params);
			} else {
				householdTasks = taskDao.queryUndoAuditTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(householdTasks);
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> queryHousAdvanceApply(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housAdvanceTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housAdvanceTasks = taskDao.queryDoneHousAdvanceApplyTasks(params);
			} else {
				housAdvanceTasks = taskDao.queryUndoHousAdvanceApplyTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housAdvanceTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryAdvanceNotarizeTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_LOANING_NOTARIZE);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housAdvanceNotarizeTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housAdvanceNotarizeTasks = taskDao.queryDoneHousAdvanceNotarizeTasks(params);
			} else {
				housAdvanceNotarizeTasks = taskDao.queryUndoHousAdvanceNotarizeTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housAdvanceNotarizeTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryHousExtensionSurveyTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		//params.put("taskDefinition", USERTASK_EXTENSION_CUSTOMER_SURVEY);
		params.put("taskDefinitionArray", new String[]{USERTASK_EXTENSION_CUSTOMER_SURVEY,USERTASK_EXTENSION_CUSTOMER_SURVEYTWO});
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housExtensionSurveyTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housExtensionSurveyTasks = taskDao.queryDoneExtensionSurveyTasks(params);
			} else {
				housExtensionSurveyTasks = taskDao.queryUnExtensionSurveyTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housExtensionSurveyTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryHousNotarizationAssignTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_NOTARIZATION_ASSIGN);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housNotarizationAssignTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housNotarizationAssignTasks = taskDao.queryDoneNotarizationAssignTasks(params);
			} else {
				housNotarizationAssignTasks = taskDao.queryUnNotarizationAssignTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housNotarizationAssignTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryHousExcessiveNotarizationTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_EXCESSIVE_NOTARIZATION);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housExcessiveNotarizationTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housExcessiveNotarizationTasks = taskDao.queryDonehousExcessiveNotarizationTasks(params);
			} else {
				housExcessiveNotarizationTasks = taskDao.queryUnhousExcessiveNotarizationTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housExcessiveNotarizationTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryHousExcessiveMortgageTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_EXCESSIVE_MORTGAGE);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housExcessiveMortgageTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housExcessiveMortgageTasks = taskDao.queryDonehousExcessiveMortgageTasks(params);
			} else {
				housExcessiveMortgageTasks = taskDao.queryUnhousExcessiveMortgageTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housExcessiveMortgageTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryHouSignExtendedContractTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_SIGN_EXTENDEDCONTRACT);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> houSignExtendedContractTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				houSignExtendedContractTasks = taskDao.queryDoneHouSignExtendedContractTasks(params);
			} else {
				houSignExtendedContractTasks = taskDao.queryUnHouSignExtendedContractTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(houSignExtendedContractTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryMyTaskStatistic(
			Map<String, Object> params,int currentPage,int pageSize) throws ServiceException
	{
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage, pageSize);
		List<Map<String,Object>> myTask = taskDao.queryMyTaskStatistic(params);
		PageInfo<Map<String, Object>> page = new PageInfo<>(myTask);
		List<Map<String,Object>> pageList = page.getList();
		for(Map taskMap : pageList){
			String processState = (String)taskMap.get("taskNode");
			Map<String, Object> menuInfo = sysMenuService.getRouteInfoByProcessState(processState);
			if (menuInfo != null) {
				taskMap.put("menuIcon", menuInfo.get("menuIcon"));
				taskMap.put("routePath", menuInfo.get("routePath"));
				taskMap.put("routeName", menuInfo.get("routeName"));
				taskMap.put("label", menuInfo.get("menuName"));
			}
		}
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> queryExtendedfeeTasks(long roleId, Map<String, Object> params,
			boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_GATHER_EXTENDEDFEE);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> houeExtendedfeeTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				houeExtendedfeeTasks = taskDao.queryDoneHoueExtendedfeeTasks(params);
			} else {
				houeExtendedfeeTasks = taskDao.queryUnHoueExtendedfeeTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(houeExtendedfeeTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryDeductTasks(long roleId, Map<String, Object> params, boolean isCompleted,
			int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_DEDUCT);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> houeDeductTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				houeDeductTasks = taskDao.queryDoneHoueDeductTasks(params);
			} else {
				houeDeductTasks = taskDao.queryUnHoueDeductTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(houeDeductTasks);
		return page;
	}


	@Override
	public PageInfo<Map<String, Object>> queryHousExtensionCollateralAssessTasks(long roleId,
			Map<String, Object> params, boolean isCompleted, int currentPage, int pageSize) throws ServiceException {
		params.put("taskDefinition", USERTASK_EXTENSION_COLLATERALASSESS);
		String roleName = getRoleName(roleId);
		List<Map<String,Object>> housExtensionCollateralAssessTasks = new ArrayList<Map<String,Object>>();
		try {
			params.put("nid", roleName);
			PageHelper.startPage(currentPage, pageSize);
			if (isCompleted) {
				params.put("isCompleted", isCompleted);
				housExtensionCollateralAssessTasks = taskDao.queryDoneExtensionCollateralAssessTasks(params);
			} else {
				housExtensionCollateralAssessTasks = taskDao.queryUnExtensionCollateralAssessTasks(params);
			}
		} catch (Exception e) {
			throw new ServiceException("任务查询失败:" + e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(housExtensionCollateralAssessTasks);
		return page;
	}

}
