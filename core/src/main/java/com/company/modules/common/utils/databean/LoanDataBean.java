package com.company.modules.common.utils.databean;

import java.math.BigDecimal;
import java.util.Date;

public class LoanDataBean extends BasicServiceDataBean {
	/**
	 * 放款金额
	 */
	private BigDecimal loanAmount;
	/**
	 * 账号
	 */
	private String bankAccount;
	/**
	 * 银行标识(数据字典)
	 */
	private Integer bankFlag;
	
	/**
     * 开户银行名称
     */
    private String bankName;
	
	/**
	 * 实际借款金额
	 */
	private BigDecimal realLendAccount;
	/**
	 * 管理费
	 */
	private BigDecimal totalManagementFee;
	/**
	 * 放款时间
	 */
	private Date loanTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 第三方金额
	 */
	private BigDecimal thirdAccount;
	
	/**
	 * 三方转账手续费
	 */
	private BigDecimal thirdTransferFee;
	
	/**
	 * 用户id
	 */
	private Long userId;

	private String customerName;
	
	private String cardid;
	
	private BigDecimal account;
	
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(Integer bankFlag) {
		this.bankFlag = bankFlag;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public BigDecimal getTotalManagementFee() {
		return totalManagementFee;
	}

	public void setTotalManagementFee(BigDecimal totalManagementFee) {
		this.totalManagementFee = totalManagementFee;
	}

	public BigDecimal getRealLendAccount() {
		return realLendAccount;
	}

	public void setRealLendAccount(BigDecimal realLendAccount) {
		this.realLendAccount = realLendAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getThirdAccount() {
		return thirdAccount;
	}

	public void setThirdAccount(BigDecimal thirdAccount) {
		this.thirdAccount = thirdAccount;
	}

	public BigDecimal getThirdTransferFee() {
		return thirdTransferFee;
	}

	public void setThirdTransferFee(BigDecimal thirdTransferFee) {
		this.thirdTransferFee = thirdTransferFee;
	}
	
	
}
