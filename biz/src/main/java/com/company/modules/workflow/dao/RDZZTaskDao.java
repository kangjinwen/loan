package com.company.modules.workflow.dao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.domain.ExportHousOritoInformation;
import com.company.modules.common.domain.HouseCheckBank;

/**
 *	@Description 
 *  @author fzc,fzc@erongdu.com
 *  @CreatTime 2016年8月8日 下午2:58:47
 *  @since version 1.0.0
 */
@RDBatisDao
public interface RDZZTaskDao {

	List<Map<String, Object>> queryUndoHouseholdTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneHouseholdTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneHousAdvanceApplyTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneHousAdvanceNotarizeTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUndoHousAdvanceNotarizeTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneExtensionSurveyTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnNotarizationAssignTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDonehousExcessiveNotarizationTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnhousExcessiveNotarizationTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnhousExcessiveMortgageTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnHouSignExtendedContractTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnHoueExtendedfeeTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnExtensionCollateralAssessTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneExtensionCollateralAssessTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnHoueDeductTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneHoueDeductTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneHoueExtendedfeeTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneHouSignExtendedContractTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnhousExtendedAuditTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDonehousExcessiveMortgageTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryDoneNotarizationAssignTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUnExtensionSurveyTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryUndoHousAdvanceApplyTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneInstanceTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoInstanceTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneHouseholdInvestigateTasks(Map<String, Object> params);
	
	List<HouseCheckBank> queryExportDoneHouseholdInvestigateTasks(Map<String, Object> params);
	
	List<ExportHousOritoInformation> queryExportHousOritoInformationTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoHouseholdInvestigateTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneConnectionTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoConnectionTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneSurveyTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoSurveyTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneConfirmationTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoConfirmationTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneHousLowerCostTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoHousLowerCostTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneOritoConfirmationTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoOritoConfirmationTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneAuditTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoAuditTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneContractTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoContractTasks(Map<String, Object> params);

	List<Map<String, Object>> queryDoneInstanceInfoTasks(Map<String, Object> params);

	List<Map<String, Object>> queryUndoInstanceInfoTasks(Map<String, Object> params);
	
	List<Map<String, Object>> queryNewConsultList(Map<String, Object> params);
	
	List<Map<String, Object>> queryMyTaskStatistic(Map<String, Object> params);
	
	Map<String, Object> queryHistoryIdentityLinkByMap(Map<String,Object> paramMap);

	Map<String, Object> queryHistoryTaskInfo(Map<String, Object> paramMap);
}
