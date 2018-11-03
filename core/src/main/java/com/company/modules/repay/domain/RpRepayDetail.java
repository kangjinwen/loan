package com.company.modules.repay.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-09-03
 */
public class RpRepayDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 还款明细表-主键
	 */
	private Integer id;

	/**
	 * 外键：关联rp_repay_setting表
	 */
	private Integer repaySettingId;
	/**
	 * 项目编号
	 */
	private Integer projectId;
	/**
	 * 期次
	 */
	private Integer term;

	/**
	 * 还款日期
	 */
	private Date date;

	/**
	 * 本期本金
	 */
	private Double capital;

	/**
	 * 本期利息
	 */
	private Double interest;

	/**
	 * 本期服务费
	 */
	private Double serviceFee;

	/**
	 * 本期应还
	 */
	private Double totalAmount;

	/**
	 * 本期已还
	 */
	private Double repayInterm;

	/**
	 * 罚息金额
	 */
	private Double penaltyAmount;

	/**
	 * 实际还款日期
	 */
	private Date actualDate;

	/**
	 * 还款状态：prepare：待生成计划；normal：正常还款；overdue；逾期
	 */
	private String repayStatusType;

	/**
	 * 是否还清
	 */
	private Boolean isPayoff;

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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getRepayInterm() {
		return repayInterm;
	}

	public void setRepayInterm(Double repayInterm) {
		this.repayInterm = repayInterm;
	}

	public Double getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public Date getActualDate() {
		return actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public String getRepayStatusType() {
		return repayStatusType;
	}

	public void setRepayStatusType(String repayStatusType) {
		this.repayStatusType = repayStatusType == null ? null : repayStatusType.trim();
	}

	public Boolean getIsPayoff() {
		return isPayoff;
	}

	public void setIsPayoff(Boolean isPayoff) {
		this.isPayoff = isPayoff;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}