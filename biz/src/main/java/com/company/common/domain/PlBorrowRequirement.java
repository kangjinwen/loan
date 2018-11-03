package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款需求信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-08 02:01:19
 */
public class PlBorrowRequirement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
    * id
    */
    private Long id;
    /**
    * 创建人
    */
    private Long creator;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改者
    */
    private Long modifier;
    /**
    * 修改时间
    */
    private Date modifyTime;
    /**
    * 0不删除1已删除
    */
    private Byte isDelete;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 项目编号
    */
    private Long projectId;
    /**
    * 咨询ID
    */
    private Long consultId;
    /**
    * 客户ID
    */
    private Long customerId;
    /**
    * 借款需求金额
    */
    private BigDecimal account;
    /**
    * 借款日期
    */
    private Date borrowingTime;
    /**
    * 产品ID
    */
    private Long productId;
    /**
    * 还款方式
    */
    private Byte repaymentType;
    /**
     * 月息
     */
     private BigDecimal repaymentRate;
    /**
    * 借款期限按/月
    */
   // private Byte timeLimit;

    private Integer timeLimit;
    /**
     * 成单利率
     */
    private BigDecimal singleRate;
    /**
    * 项目所属
    */
    private Byte projectBelongs;
    /**
    * 金融顾问
    */
    private String financialAdvisers;
    /**
    * 机构名称
    */
    private String institutionName;
    /**
    * 业务员
    */
    private String salesman;
    /**
    * 借款用途
    */
    private String borrowUse;
    /**
    * 还款来源
    */
    private String repaymentSource;
    /**
    * 借款说明
    */
    private String remark;

    /**
     * 联系电话
     */
     private String phoneNumber;

     /**
      * 联系电话（报单人或报单机构）
      */
     private String mobile;

	/**
	 * 客户类型  0新客户1老客户
	 */
	private byte customerType;

	/**
	 * 抵押类型  0一压1二压
	 */
	private byte mortgageType;


	private BigDecimal collectionServiceFee;
     private Boolean collectionServiceFeeUpdateNull;

     private BigDecimal collectionRate;
     private Boolean collectionRateUpdateNull;
     private String collectionServiceName;
     private String collectionServiceCard;
     private String collectionServiceBank;



	public BigDecimal getCollectionServiceFee() {
		return collectionServiceFee;
	}

	public void setCollectionServiceFee(BigDecimal collectionServiceFee) {
		this.collectionServiceFee = collectionServiceFee;
	}

	public BigDecimal getCollectionRate() {
		return collectionRate;
	}

	public void setCollectionRate(BigDecimal collectionRate) {
		this.collectionRate = collectionRate;
	}

	public String getCollectionServiceName() {
		return collectionServiceName;
	}

	public void setCollectionServiceName(String collectionServiceName) {
		this.collectionServiceName = collectionServiceName;
	}

	public String getCollectionServiceCard() {
		return collectionServiceCard;
	}

	public void setCollectionServiceCard(String collectionServiceCard) {
		this.collectionServiceCard = collectionServiceCard;
	}

	public String getCollectionServiceBank() {
		return collectionServiceBank;
	}

	public void setCollectionServiceBank(String collectionServiceBank) {
		this.collectionServiceBank = collectionServiceBank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getConsultId() {
		return consultId;
	}

	public void setConsultId(Long consultId) {
		this.consultId = consultId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public Date getBorrowingTime() {
		return borrowingTime;
	}

	public void setBorrowingTime(Date borrowingTime) {
		this.borrowingTime = borrowingTime;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Byte getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Byte repaymentType) {
		this.repaymentType = repaymentType;
	}

	public BigDecimal getRepaymentRate() {
		return repaymentRate;
	}

	public void setRepaymentRate(BigDecimal repaymentRate) {
		this.repaymentRate = repaymentRate;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public BigDecimal getSingleRate() {
		return singleRate;
	}

	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
	}

	public Byte getProjectBelongs() {
		return projectBelongs;
	}

	public void setProjectBelongs(Byte projectBelongs) {
		this.projectBelongs = projectBelongs;
	}

	public String getFinancialAdvisers() {
		return financialAdvisers;
	}

	public void setFinancialAdvisers(String financialAdvisers) {
		this.financialAdvisers = financialAdvisers;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getBorrowUse() {
		return borrowUse;
	}

	public void setBorrowUse(String borrowUse) {
		this.borrowUse = borrowUse;
	}

	public String getRepaymentSource() {
		return repaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getCollectionServiceFeeUpdateNull() {
		return collectionServiceFeeUpdateNull;
	}

	public void setCollectionServiceFeeUpdateNull(Boolean collectionServiceFeeUpdateNull) {
		this.collectionServiceFeeUpdateNull = collectionServiceFeeUpdateNull;
	}

	public Boolean getCollectionRateUpdateNull() {
		return collectionRateUpdateNull;
	}

	public void setCollectionRateUpdateNull(Boolean collectionRateUpdateNull) {
		this.collectionRateUpdateNull = collectionRateUpdateNull;
	}

	public byte getCustomerType() {
		return customerType;
	}

	public void setCustomerType(byte customerType) {
		this.customerType = customerType;
	}

	public byte getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(byte mortgageType) {
		this.mortgageType = mortgageType;
	}
}