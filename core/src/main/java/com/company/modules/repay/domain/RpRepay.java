package com.company.modules.repay.domain;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-09-03
 */
public class RpRepay implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 还款表——主键
	 */
	private Integer id;

	/**
	 * 外键：关联rp_repay_setting表
	 */
	private Integer repaySettingId;

	/**
	 * 已还利息
	 */
	private Double repayedInterest;

	/**
	 * 已还本金
	 */
	private Double pepayedCapital;

	/**
	 * 服务费
	 */
	private Double serviceFee;

	/**
	 * 还款状态：prepare：待生成计划；normal：正常还款；overdue；逾期
	 */
	private String repayStatusType;

	/**
	 * 逾期金额
	 */
	private Double overDueAmount;

	/**
	 * 逾期天数
	 */
	private Integer overDueDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRepaySettingId() {
		return repaySettingId;
	}

	public void setRepaySettingId(Integer repaySettingId) {
		this.repaySettingId = repaySettingId;
	}

	public Double getRepayedInterest() {
		return repayedInterest;
	}

	public void setRepayedInterest(Double repayedInterest) {
		this.repayedInterest = repayedInterest;
	}

	public Double getPepayedCapital() {
		return pepayedCapital;
	}

	public void setPepayedCapital(Double pepayedCapital) {
		this.pepayedCapital = pepayedCapital;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getRepayStatusType() {
		return repayStatusType;
	}

	public void setRepayStatusType(String repayStatusType) {
		this.repayStatusType = repayStatusType == null ? null : repayStatusType.trim();
	}

	public Double getOverDueAmount() {
		return overDueAmount;
	}

	public void setOverDueAmount(Double overDueAmount) {
		this.overDueAmount = overDueAmount;
	}

	public Integer getOverDueDay() {
		return overDueDay;
	}

	public void setOverDueDay(Integer overDueDay) {
		this.overDueDay = overDueDay;
	}
}