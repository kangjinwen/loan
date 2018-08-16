package com.company.modules.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.company.modules.common.utils.POIIgnore;

/**
 * 导出Excel核行实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-08 01:01:45
 */
public class HouseCheckBank implements Serializable{
	
	/**
	* 项目编号
	*/
	private Long projectCode;
	/**
	* 客户名称
	*/
	private String customerName;
	/**
	* 贷款金额（元）
	*/
	private BigDecimal account;
	/**
	* 贷款期限（月）
	*/
	private Byte timeLimit;
	 /**
    * 贷款人姓名
    */
    private String name;
    /**
    * 贷款人身份证号
    */
    private String certNumber;
    /**
     * 贷款人性别
     */
    private String sex;
    /**
     * 婚姻状况
     */
    private String marryStatus;
    /**
     * 房产地址(转化成中文后的)
     */
    private String address;
	/**
	* 来源
	*/
	private String businessOriginText;
	/**
	* 机构名称
	*/
	private String institutionName;
	/**
	* 报单人
	*/
	private String customerManager;
	/**
	* 报单时间
	*/
	private Date declarationDate;
	/**
	* 流程状态
	*/
	private String currentProcessStateCode;
	/**
	* 任务处理人
	*/
	private String assignee;	
	/**
	* 任务开始时间
	*/
	private Date startTime;
	/**
	* 任务结束时间
	*/
	private Date endTime;
	/**
	* 核行备注
	*/
	private String remark;
	  /**
     * 居住地
     */
	@POIIgnore
     private Long residentialAddressId;
    /**
     * 居住地详细地址
     */
	@POIIgnore
    private String residentialAddress;    

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getResidentialAddressId() {
		return residentialAddressId;
	}

	public void setResidentialAddressId(Long residentialAddressId) {
		this.residentialAddressId = residentialAddressId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Long getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(Long projectCode) {
		this.projectCode = projectCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public Byte getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Byte timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getBusinessOriginText() {
		return businessOriginText;
	}

	public void setBusinessOriginText(String businessOriginText) {
		this.businessOriginText = businessOriginText;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	public String getCurrentProcessStateCode() {
		return currentProcessStateCode;
	}

	public void setCurrentProcessStateCode(String currentProcessStateCode) {
		this.currentProcessStateCode = currentProcessStateCode;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	

}