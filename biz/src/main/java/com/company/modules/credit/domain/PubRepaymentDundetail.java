package com.company.modules.credit.domain;

import java.io.Serializable;
import java.util.Date;

public class PubRepaymentDundetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 期数
	 */
	private String period;
	/**
	 * 还款日期
	 */
	private Date repaymentTime;
	/**
	 * 还款状态
	 */
	private String repaymentStatusText;
	/**
	 * 催收次数
	 */
	private String dunCount;
	/**
	 * 应还金额
	 */
	private String account;
	/**
	 * 违约金
	 */
	private String penalty;
	/**
	 * 罚息
	 */
	private String defaultInterest;
	/**
	 * 上次催收日期
	 */
	private Date lastDuntime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	public String getRepaymentStatusText() {
		return repaymentStatusText;
	}
	public void setRepaymentStatusText(String repaymentStatusText) {
		this.repaymentStatusText = repaymentStatusText;
	}
	public String getDunCount() {
		return dunCount;
	}
	public void setDunCount(String dunCount) {
		this.dunCount = dunCount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPenalty() {
		return penalty;
	}
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	public Date getLastDuntime() {
		return lastDuntime;
	}
	public void setLastDuntime(Date lastDuntime) {
		this.lastDuntime = lastDuntime;
	}
	public String getDefaultInterest() {
		return defaultInterest;
	}
	public void setDefaultInterest(String defaultInterest) {
		this.defaultInterest = defaultInterest;
	}
	
}
