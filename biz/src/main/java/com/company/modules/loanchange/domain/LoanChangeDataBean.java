package com.company.modules.loanchange.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

public class LoanChangeDataBean extends BasicServiceDataBean implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1155699858244381583L;

	private Long id;
	/** 项目编号 */
	private Long projectId;
	/** 流程id */
	private String processInstanceId;
	
	/** 分支流程类型（0正常 1提前 2减免 3强制 4展期 5处置） */
	private Byte branchingProcessType;

	
	/** 还款详情id */
	private Long repaymentDetailId;
	/**
	 * 还款方式
	 */
	private Byte repaymentType;
	/**
	 * 还款状态 1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中
	 */
	private Byte repaymentStatus;
	/** 还款处理（0正常 1提前 2减免 3强制 4展期 5处置） */
	private Byte repaymentProcess;
	/** 还款日期 */
	private Date repaymentTime;
	/** 实际还款日期 */
	private Date realpaymentTime;
	/** 预约还款时间 */
	private Date makeRepaymentTime;
	/** 实际罚息金额 */
	private BigDecimal realPrepaymentPenalty;
	/** 提前归还本金 */
	private BigDecimal repayCapitalAmount;
	/** 还款期次 目前期数 */
	private Byte period;
	/**
	 * 借款期限/月 借款期数
	 */
	private Byte timeLimit;
	/**
     * 提前还款违约金比例
     */
    private BigDecimal aheadRepayRate;
	
	private BigDecimal reductionPenalty; // 罚息减免
	private BigDecimal breachContract; // 违约金减免
	private BigDecimal allBreaks; // 总减免金额
	private BigDecimal capitalSum;
	private BigDecimal interestSum;
	private BigDecimal penaltySum;
	private BigDecimal defaultInterestSum;
	private BigDecimal needReturnTotalSum;

	/**
	 * 是否删除 0不删除1已删除
	 */
	private Byte isDelete;
	private String remark1; // 贷后变更备注
	private String remark2; // 审批备注
	
	/** 审批处理意见(提前还款、减免、强制、处置等):0-默认，1-同意，2-不同意 */
	private String processingOpinion; 
	/** 分支流程Id */
	private String branchingProcessId;  
	/** 违约情况:1-GPS拆除 2-驶出规定范围 3-其它 */
	private Byte defaultSituation;  
	/** 本期违规违约金 */
	private BigDecimal violationsPenalty;  
	/** 本期违规拖车费 */
	private BigDecimal violationsTruckFee;  
	/** 本期违规差旅费 */
	private BigDecimal violationsTravelFee;  
	/** 本期违规其它费用 */
	private BigDecimal violationsOtherFee;  
	/** 本期违规处罚总额 */
	private BigDecimal violationsTotalAmount;  
	private BigDecimal otherAmount;

	private String assignee;

	// ************************ 展期参数 ************************
	/** 展期次数 */
	private Integer extensionCount;
	/** 展期金额 */
	private BigDecimal extensionAmount;
	/** 展期费 */
	private BigDecimal extensionFee; 
	/** 展期利率 */
	private BigDecimal extensionRate;
	/** 展期返佣点费 */
	private BigDecimal extensionReturnfeeRate;
	/** 展期审批意见 */
	private String extendedFlag;

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Byte getBranchingProcessType() {
		return branchingProcessType;
	}

	public void setBranchingProcessType(Byte branchingProcessType) {
		this.branchingProcessType = branchingProcessType;
	}

	public Long getRepaymentDetailId() {
		return repaymentDetailId;
	}

	public void setRepaymentDetailId(Long repaymentDetailId) {
		this.repaymentDetailId = repaymentDetailId;
	}

	public Byte getDefaultSituation() {
		return defaultSituation;
	}

	public void setDefaultSituation(Byte defaultSituation) {
		this.defaultSituation = defaultSituation;
	}

	public BigDecimal getViolationsPenalty() {
		return violationsPenalty;
	}

	public void setViolationsPenalty(BigDecimal violationsPenalty) {
		this.violationsPenalty = violationsPenalty;
	}

	public BigDecimal getViolationsTruckFee() {
		return violationsTruckFee;
	}

	public void setViolationsTruckFee(BigDecimal violationsTruckFee) {
		this.violationsTruckFee = violationsTruckFee;
	}

	public BigDecimal getViolationsTravelFee() {
		return violationsTravelFee;
	}

	public void setViolationsTravelFee(BigDecimal violationsTravelFee) {
		this.violationsTravelFee = violationsTravelFee;
	}

	public BigDecimal getViolationsOtherFee() {
		return violationsOtherFee;
	}

	public void setViolationsOtherFee(BigDecimal violationsOtherFee) {
		this.violationsOtherFee = violationsOtherFee;
	}

	public BigDecimal getViolationsTotalAmount() {
		return violationsTotalAmount;
	}

	public void setViolationsTotalAmount(BigDecimal violationsTotalAmount) {
		this.violationsTotalAmount = violationsTotalAmount;
	}

	public BigDecimal getReductionPenalty() {
		return reductionPenalty;
	}

	public void setReductionPenalty(BigDecimal reductionPenalty) {
		this.reductionPenalty = reductionPenalty;
	}

	public BigDecimal getBreachContract() {
		return breachContract;
	}

	public void setBreachContract(BigDecimal breachContract) {
		this.breachContract = breachContract;
	}

	public BigDecimal getAllBreaks() {
		return allBreaks;
	}

	public void setAllBreaks(BigDecimal allBreaks) {
		this.allBreaks = allBreaks;
	}

	public String getProcessingOpinion() {
		return processingOpinion;
	}

	public void setProcessingOpinion(String processingOpinion) {
		this.processingOpinion = processingOpinion;
	}

	public String getBranchingProcessId() {
		return branchingProcessId;
	}

	public void setBranchingProcessId(String branchingProcessId) {
		this.branchingProcessId = branchingProcessId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public Date getRealpaymentTime() {
		return realpaymentTime;
	}

	public void setRealpaymentTime(Date realpaymentTime) {
		this.realpaymentTime = realpaymentTime;
	}

	public Byte getPeriod() {
		return period;
	}

	public void setPeriod(Byte period) {
		this.period = period;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public Byte getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Byte repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Byte getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Byte timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Byte getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(Byte repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public Byte getRepaymentProcess() {
		return repaymentProcess;
	}

	public void setRepaymentProcess(Byte repaymentProcess) {
		this.repaymentProcess = repaymentProcess;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Date getMakeRepaymentTime() {
		return makeRepaymentTime;
	}

	public void setMakeRepaymentTime(Date makeRepaymentTime) {
		this.makeRepaymentTime = makeRepaymentTime;
	}

	public Integer getExtensionCount() {
		return extensionCount;
	}

	public void setExtensionCount(Integer extensionCount) {
		this.extensionCount = extensionCount;
	}

	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public BigDecimal getRepayCapitalAmount() {
		return repayCapitalAmount;
	}

	public void setRepayCapitalAmount(BigDecimal repayCapitalAmount) {
		this.repayCapitalAmount = repayCapitalAmount;
	}

	public BigDecimal getExtensionAmount() {
		return extensionAmount;
	}

	public void setExtensionAmount(BigDecimal extensionAmount) {
		this.extensionAmount = extensionAmount;
	}

	public BigDecimal getExtensionFee() {
		return extensionFee;
	}

	public void setExtensionFee(BigDecimal extensionFee) {
		this.extensionFee = extensionFee;
	}

	public BigDecimal getExtensionRate() {
		return extensionRate;
	}

	public void setExtensionRate(BigDecimal extensionRate) {
		this.extensionRate = extensionRate;
	}

	public BigDecimal getExtensionReturnfeeRate() {
		return extensionReturnfeeRate;
	}

	public void setExtensionReturnfeeRate(BigDecimal extensionReturnfeeRate) {
		this.extensionReturnfeeRate = extensionReturnfeeRate;
	}

	public String getExtendedFlag() {
		return extendedFlag;
	}

	public void setExtendedFlag(String extendedFlag) {
		this.extendedFlag = extendedFlag;
	}

	public BigDecimal getRealPrepaymentPenalty() {
		return realPrepaymentPenalty;
	}

	public void setRealPrepaymentPenalty(BigDecimal realPrepaymentPenalty) {
		this.realPrepaymentPenalty = realPrepaymentPenalty;
	}

	public BigDecimal getAheadRepayRate() {
		return aheadRepayRate;
	}

	public void setAheadRepayRate(BigDecimal aheadRepayRate) {
		this.aheadRepayRate = aheadRepayRate;
	}
	
}
