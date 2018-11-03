package com.company.modules.repay.domain;

import java.io.Serializable;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-09-03
 */
public class RpAheadRepaySetting implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 提前还款表-主键
	 */
	private Integer id;

	/**
	 * 外键：repay表的id
	 */
	private Integer repaySettingId;

	/**
	 * 是否允许提前还款：0代表否；1代表是
	 */
	private Boolean canAheadRepay;

	/**
	 * 提前还款方式:remainingCapital:还剩余本金；remainingInterest：还剩余利息；PenaltyInterest：罚息
	 */
	private String aheadRepayType;

	/**
	 * 免罚息期限
	 */
	private Integer protectTerm;

	/**
	 * 罚息方式：remainingCapital：按剩余本金罚息；remainingInterest：按剩余利息罚息；CapitalAndInterest：按剩余本息罚息
	 */
	private String penaltyInterestType;

	/**
	 * 罚息利率
	 */
	private Double penaltyInterestRate;

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

	public Boolean getCanAheadRepay() {
		return canAheadRepay;
	}

	public void setCanAheadRepay(Boolean canAheadRepay) {
		this.canAheadRepay = canAheadRepay;
	}

	public String getAheadRepayType() {
		return aheadRepayType;
	}

	public void setAheadRepayType(String aheadRepayType) {
		this.aheadRepayType = aheadRepayType == null ? null : aheadRepayType.trim();
	}

	public Integer getProtectTerm() {
		return protectTerm;
	}

	public void setProtectTerm(Integer protectTerm) {
		this.protectTerm = protectTerm;
	}

	public String getPenaltyInterestType() {
		return penaltyInterestType;
	}

	public void setPenaltyInterestType(String penaltyInterestType) {
		this.penaltyInterestType = penaltyInterestType == null ? null : penaltyInterestType.trim();
	}

	public Double getPenaltyInterestRate() {
		return penaltyInterestRate;
	}

	public void setPenaltyInterestRate(Double penaltyInterestRate) {
		this.penaltyInterestRate = penaltyInterestRate;
	}
}