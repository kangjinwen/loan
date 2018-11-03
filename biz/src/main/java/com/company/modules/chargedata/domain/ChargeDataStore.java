package com.company.modules.chargedata.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.company.common.utils.DateUtil;

/**
 * 押品管理实体
 * 
 * @author JDM
 * @version 1.0
 * @since 2016-12-02 01:41:58
 */
public class ChargeDataStore implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pageNum = 1;
	private Integer pageSize = 10;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户身份证号码
	 */
	private String certNumber;
	/**
	 * 贷款金额
	 */
	private BigDecimal account;
	/**
	 * 借款期限按/月
	 */
	private Byte timeLimit;
	/**
	 * 底点利率
	 */
	private BigDecimal repaymentRate;
	/**
	 * 成单利率
	 */
	private BigDecimal singleRate;
	/**
	 * 业务来源
	 */
	private String businessOrigin;
	/**
	 * 机构名称
	 */
	private String institutionName;
	/**
	 * 报单人
	 */
	private String customerManager;
	/**
	 * 押品状态(1.待入库2.已入库3.待出库4.已出库)
	 */
	private Long chargeStatus;

	public static final long CHARGESTATUS_WAIT_IN_STORE = 1;
	public static final long CHARGESTATUS_IN_STORE = 2;
	public static final long CHARGESTATUS_WAIT_OUT_STORE = 3;
	public static final long CHARGESTATUS_OUT_STORE = 4;

	/**
	 * 入库日期
	 */
	@DateTimeFormat(pattern = DateUtil.DATE_PATTERN)
	private Date inStoreTime;
	/**
	 * 出库日期
	 */
	@DateTimeFormat(pattern = DateUtil.DATE_PATTERN)
	private Date outStoreTime;
	/**
	 * 流程ID
	 */
	private String processInstanceId;

	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 放款日期
	 */
	@DateTimeFormat(pattern = DateUtil.DATE_PATTERN)
	private Date loanDate;
	/**
	 * 出借人
	 */
	private String lender;
	/**
	 * 出借人受托人
	 */
	private String trusteeOfLender;

	private List<Long> chargeDataIds;

	private String remark;
	private String receiptor;

	/**
	 * 获取id
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param Long 要设置的id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取客户名称
	 *
	 * @return 客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * 设置客户名称
	 * 
	 * @param customerName 要设置的客户名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 获取客户身份证号码
	 *
	 * @return 客户身份证号码
	 */
	public String getCertNumber() {
		return certNumber;
	}

	/**
	 * 设置客户身份证号码
	 * 
	 * @param certNumber 要设置的客户身份证号码
	 */
	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

	/**
	 * 获取贷款金额
	 *
	 * @return 贷款金额
	 */
	public BigDecimal getAccount() {
		return account;
	}

	/**
	 * 设置贷款金额
	 * 
	 * @param account 要设置的贷款金额
	 */
	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	/**
	 * 获取借款期限按/月
	 *
	 * @return 借款期限按/月
	 */
	public Byte getTimeLimit() {
		return timeLimit;
	}

	/**
	 * 设置借款期限按/月
	 * 
	 * @param timeLimit 要设置的借款期限按/月
	 */
	public void setTimeLimit(Byte timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * 获取底点利率
	 *
	 * @return 底点利率
	 */
	public BigDecimal getRepaymentRate() {
		return repaymentRate;
	}

	/**
	 * 设置底点利率
	 * 
	 * @param repaymentRate 要设置的底点利率
	 */
	public void setRepaymentRate(BigDecimal repaymentRate) {
		this.repaymentRate = repaymentRate;
	}

	/**
	 * 获取成单利率
	 *
	 * @return 成单利率
	 */
	public BigDecimal getSingleRate() {
		return singleRate;
	}

	/**
	 * 设置成单利率
	 * 
	 * @param singleRate 要设置的成单利率
	 */
	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
	}

	/**
	 * 获取业务来源
	 *
	 * @return 业务来源
	 */
	public String getBusinessOrigin() {
		return businessOrigin;
	}

	/**
	 * 设置业务来源
	 * 
	 * @param businessOrigin 要设置的业务来源
	 */
	public void setBusinessOrigin(String businessOrigin) {
		this.businessOrigin = businessOrigin;
	}

	/**
	 * 获取机构名称
	 *
	 * @return 机构名称
	 */
	public String getInstitutionName() {
		return institutionName;
	}

	/**
	 * 设置机构名称
	 * 
	 * @param institutionName 要设置的机构名称
	 */
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	/**
	 * 获取报单人
	 *
	 * @return 报单人
	 */
	public String getCustomerManager() {
		return customerManager;
	}

	/**
	 * 设置报单人
	 * 
	 * @param customerManager 要设置的报单人
	 */
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	/**
	 * 获取押品状态(1.待入库2.已入库3.待出库4.已出库)
	 *
	 * @return 押品状态(1.待入库2.已入库3.待出库4.已出库)
	 */
	public Long getChargeStatus() {
		return chargeStatus;
	}

	/**
	 * 设置押品状态(1.待入库2.已入库3.待出库4.已出库)
	 * 
	 * @param chargeStatus 要设置的押品状态(1.待入库2.已入库3.待出库4.已出库)
	 */
	public void setChargeStatus(Long chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	/**
	 * 获取入库日期
	 *
	 * @return 入库日期
	 */
	public Date getInStoreTime() {
		return inStoreTime;
	}

	/**
	 * 设置入库日期
	 * 
	 * @param inStoreTime 要设置的入库日期
	 */
	public void setInStoreTime(Date inStoreTime) {
		this.inStoreTime = inStoreTime;
	}

	/**
	 * 获取出库日期
	 *
	 * @return 出库日期
	 */
	public Date getOutStoreTime() {
		return outStoreTime;
	}

	/**
	 * 设置出库日期
	 * 
	 * @param outStoreTime 要设置的出库日期
	 */
	public void setOutStoreTime(Date outStoreTime) {
		this.outStoreTime = outStoreTime;
	}

	/**
	 * 获取流程ID
	 *
	 * @return 流程ID
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * 设置流程ID
	 * 
	 * @param processInstanceId 要设置的流程ID
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * 获取放款日期
	 *
	 * @return 放款日期
	 */
	public Date getLoanDate() {
		return loanDate;
	}

	/**
	 * 设置放款日期
	 * 
	 * @param loanDate 要设置的放款日期
	 */
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	/**
	 * 获取出借人
	 *
	 * @return 出借人
	 */
	public String getLender() {
		return lender;
	}

	/**
	 * 设置出借人
	 * 
	 * @param lender 要设置的出借人
	 */
	public void setLender(String lender) {
		this.lender = lender;
	}

	/**
	 * 获取出借人受托人
	 *
	 * @return 出借人受托人
	 */
	public String getTrusteeOfLender() {
		return trusteeOfLender;
	}

	/**
	 * 设置出借人受托人
	 * 
	 * @param trusteeOfLender 要设置的出借人受托人
	 */
	public void setTrusteeOfLender(String trusteeOfLender) {
		this.trusteeOfLender = trusteeOfLender;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<Long> getChargeDataIds() {
		return chargeDataIds;
	}

	public void setChargeDataIds(List<Long> chargeDataIds) {
		this.chargeDataIds = chargeDataIds;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiptor() {
		return receiptor;
	}

	public void setReceiptor(String receiptor) {
		this.receiptor = receiptor;
	}

}