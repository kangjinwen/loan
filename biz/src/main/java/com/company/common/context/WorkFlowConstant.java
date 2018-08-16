package com.company.common.context;

/**
 * 工作流程状态常量
 * @author JDM
 * @date 2016年8月25日
 *
 */
public class WorkFlowConstant {
	// ============== 流程名称 ===============
	/** 提前还款审批流程名称 */
	public static final String PROCESS_NAME_EARLY_REPAYMENT_APPROVAL = "ahead_of_return_loan";
	/** 罚息减免审批流程名称 */
	public static final String PROCESS_NAME_PENALTY_RELIEF_APPROVAL = "breach_of_contract_relief";
	
	
	// ============== 流程状态 ===============
	
	/** 提前还款审批流程状态 */
	public static final String PROCESS_STATUS_EARLY_REPAYMENT_APPROVAL = "usertask-earlyRepaymentApproval";
	/** 罚息减免审批流程状态 */
	public static final String PROCESS_STATUS_PENALTY_RELIEF_APPROVAL = "usertask-penaltyReliefApproval";
	/** 信息筛查 */
	public static final String PROCESS_STATUS_CUSTOMERINVESTIGATE = "usertask-customerInvestigate";
	public static final String PROCESS_STATUS_HOUSEHOLDTASKASSIGN= "usertask-householdTaskAssign";//下户任务分配
	public static final String PROCESS_STATUS_HOUSEHOLDINVESTIGATE= "usertask-householdInvestigate";//权证下户
	public static final String PROCESS_STATUS_HOUSECHECKBANK= "usertask-houseCheckBank";//权证核行
	
	public static final String PROCESS_STATUS_COLLECTASSESSMENTFEE= "usertask-collectAssessmentFee";//下户费收取
	public static final String PROCESS_STATUS_LOANING_NOTARIZE= "usertask-loaning-notarize";//垫资客服确认
	public static final String PROCESS_STATUS_EXTENSION_CUSTOMER_SURVEY= "usertask-extension-customer-survey";//客户调查(展期)
	public static final String PROCESS_STATUS_NOTARIZATION_ASSIGN= "usertask-notarization-assign";//展期公证分配
	public static final String PROCESS_STATUS_EXCESSIVE_NOTARIZATION= "usertask-excessive-notarization";//展期超额公证分配
	public static final String PROCESS_STATUS_EXCESSIVE_MORTGAGE= "usertask-excessive-mortgage";//展期超额抵押
	public static final String PROCESS_STATUS_SIGN_EXTENDEDCONTRACT= "usertask-sign-extendedContract";//展期合同
	public static final String PROCESS_STATUS_GATHER_EXTENDEDFEE= "usertask-gather-extendedfee";//展期费管理
	public static final String PROCESS_STATUS_DEDUCT= "usertask-deduct";//展期费划扣
	public static final String PROCESS_STATUS_EXTENDED_AUDIT= "usertask-extended-audit";//展期审批
	public static final String PROCESS_STATUS_RETURNFEE= "usertask-reutrnFee";//下户费收取
	
	/** 面审 */
	public static final String PROCESS_STATUS_FACE_AUDIT= "usertask-face-audit";
	/** 终审 */
	public static final String PROCESS_STATUS_FINAL_AUDIT= "usertask-final-audit";
	/** 复审 */
	public static final String PROCESS_STATUS_RECHECK= "usertask-recheck";
	/** 放款单据填写 */
	public static final String PROCESS_STATUS_WRITELOANINFO= "usertask-writeLoanInfo";
	/** 放款确认 */
	public static final String PROCESS_STATUS_LOANCONFIRM= "usertask-loanConfirm";
	/** 放款 */
	public static final String PROCESS_STATUS_LOAN= "usertask-loan";
	public static final String PROCESS_STATUS_RECHECK_REFUSE= "usertask-recheck-refuse";//复审驳回
	/** 返佣审核 */
	public static final String PROCESS_STATUS_RETURN_COMMISSION_AUDIT = "usertask-returnCommissionAudit";
	/** 返佣管理 */
	public static final String PROCESS_STATUS_RETURN_COMMISSION = "usertask-returnCommission";
	
	
	// ============== 分支流程类型 ===============
	/** 提前还款 */
	public static final byte BRANCHING_PROCESS_TYPE_EARLY_REPAYMENT = 1;
	/** 罚息减免 */
	public static final byte BRANCHING_PROCESS_TYPE_PENALTY_RELIEF = 2;
	/** 返佣 */
	public static final byte BRANCHING_PROCESS_TYPE_REBATE = 4;
	/** 押品处置 */
	public static final byte BRANCHING_PROCESS_TYPE_CHARGE_DISPOSAL = 5;
	/** 展期 */
	public static final byte BRANCHING_PROCESS_TYPE_EXTENSION = 6;
	
	// ============== 流程意见==================
	/** 通过 */
	public static final String NEXT_STEP_PASS = "pass";
	/** 同意申请 */
	public static final String NEXT_STEP_APPROVE = "approvedApply";
	/** 放款成功 */
	public static final String NEXT_STEP_LOAN_MONEYSUCCESS = "loanMoneySuccess";
	
	
	/** 未审批 */
	public static final String NEXT_STEP_NOPROCESS = "noprocess";
	/** 提交复审 */
	public static final String NEXT_STEP_RECHECK = "recheck";
	
	
	/** 驳回 */
	public static final String NEXT_STEP_REJECT = "reject";
	/** 拒绝*/
	public static final String NEXT_STEP_REFUSE = "refuse";
	/** 拒绝*/
	public static final String NEXT_STEP_REJECTPROCESS = "rejectProcess";
	/** 客户放弃*/
	public static final String NEXT_STEP_CUSTOMERGIVEUP = "customerGiveUp";
	
	public static final String NEXT_STEP_DISPOAL_REGISTER_DONE = "disposalRegisterDone";
	
	// ============== 分支流程状态 ==================
	/** 禁用 */
	public static final String IS_ACTIVE_FALSE = "0";
	/** 激活 */
	public static final String IS_ACTIVE_TRUE = "1";
	/** 返佣 */
	public static final String IS_ACTIVE_RETURN_COMMISSION = "2";
	
	/**
	 * 拒绝、驳回、拒贷
	 * @param stepOpinion
	 * @return
	 */
	public static final boolean refuseStep(String stepOpinion ){
		if (stepOpinion.equals(NEXT_STEP_REFUSE)||stepOpinion.equals(NEXT_STEP_REJECT)||stepOpinion.equals(NEXT_STEP_REJECTPROCESS)) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 通过、同意申请
	 * @param stepOpinion
	 * @return
	 */
	public static final boolean agreeStep(String stepOpinion ){
		if (stepOpinion.equals(NEXT_STEP_PASS)||stepOpinion.equals(NEXT_STEP_APPROVE)||stepOpinion.equals(NEXT_STEP_LOAN_MONEYSUCCESS)) {
			return true;
		}else {
			return false;
		}
	}
	
}
