package com.company.modules.repay.domain;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-09-03
 */
public class RpRepaySetting implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 还款设置-主键
	 */
	private Integer id;

	/**
	 * projectId
	 */
	private Integer projectId;

	/**
	 * 外键：关联pub_customer表
	 * 
	 */
	private Integer customerId;

	/**
	 * 外键：关联hous_property_information表
	 */
	private Integer housPropertyInformationId;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * 外键：关联pl_borrow_requirement表
	 */
	private Integer borrowRequirementId;

	/**
	 * 期限
	 */
	private Integer term;

	/**
	 * 借款金额
	 */
	private Double account;

	/**
	 * 还款日
	 */
	private Integer repayDay;

	/**
	 * 成本利率
	 */
	private Double repayRate;

	/**
	 * 是否跳过首月
	 */
	private Boolean isSkipFirstMonth;

	/**
	 * 还款方式:firstInterest:先息后本;averageCapitalPlusInterest:等额本息;averageCapital:等额本金;combine:休假;averageCapitalAverageInterest:等本等息
	 */
	private String repayType;

	/**
	 * 服务费收取方式:True:服务费均摊到首12期
	 */
	private Boolean isCalcServiceFee;

	/**
	 * 所处状态;0代表待还款，1代表还款中，2代表已还完
	 */
	private Integer status;

	/**
	 * 提前还款设定
	 */
	private Integer aheadRepaySetting;

	/**
	 * 逾期罚息率
	 */
	private Double overDuePenaltyRate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getHousPropertyInformationId() {
		return housPropertyInformationId;
	}

	public void setHousPropertyInformationId(Integer housPropertyInformationId) {
		this.housPropertyInformationId = housPropertyInformationId;
	}

	public Integer getBorrowRequirementId() {
		return borrowRequirementId;
	}

	public void setBorrowRequirementId(Integer borrowRequirementId) {
		this.borrowRequirementId = borrowRequirementId;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public Integer getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Integer repayDay) {
		this.repayDay = repayDay;
	}

	public Double getRepayRate() {
		return repayRate;
	}

	public void setRepayRate(Double repayRate) {
		this.repayRate = repayRate;
	}

	public Boolean getIsSkipFirstMonth() {
		return isSkipFirstMonth;
	}

	public void setIsSkipFirstMonth(Boolean isSkipFirstMonth) {
		this.isSkipFirstMonth = isSkipFirstMonth;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType == null ? null : repayType.trim();
	}

	public Boolean getIsCalcServiceFee() {
		return isCalcServiceFee;
	}

	public void setIsCalcServiceFee(Boolean isCalcServiceFee) {
		this.isCalcServiceFee = isCalcServiceFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAheadRepaySetting() {
		return aheadRepaySetting;
	}

	public void setAheadRepaySetting(Integer aheadRepaySetting) {
		this.aheadRepaySetting = aheadRepaySetting;
	}

	public Double getOverDuePenaltyRate() {
		return overDuePenaltyRate;
	}

	public void setOverDuePenaltyRate(Double overDuePenaltyRate) {
		this.overDuePenaltyRate = overDuePenaltyRate;
	}
}