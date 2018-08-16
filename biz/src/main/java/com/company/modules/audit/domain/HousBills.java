package com.company.modules.audit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 放款单/打款单实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-17 03:46:40
 */
/**
 * @author Administrator
 *
 */
public class HousBills implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 创建者
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
	 * 开户人姓名
	 */
	private String accountHolderName;
	/**
	 * 放款卡号/打款卡号
	 */
	private String cardid;
	/**
	 * 开户行(数据字典)
	 */
	private Short bankFlag;

	/**
	 * 开户银行名称
	 */
	private String bankName;

	/**
	 * 放款金额/打款金额
	 */
	private BigDecimal account;
	/**
	 * 流程id
	 */
	private String processInstanceId;
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 读取抵押登记第三方卡号
	 */
	private String thirdCardNumber;
	/**
	 * 读取抵押登记第三方开户名
	 */
	private String thirdBankAccount;
	/**
	 * 读取抵押登记第三方开户行
	 */
	private String thirdAccountOpening;
	/**
	 * 第三方金额
	 */
	private BigDecimal thirdAccount;

	/**
	 * 第三方转账手续费
	 */
	private BigDecimal thirdTransferFee;
	/**
	 * 用途
	 */
	private String purpose;

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getThirdCardNumber() {
		return thirdCardNumber;
	}

	public void setThirdCardNumber(String thirdCardNumber) {
		this.thirdCardNumber = thirdCardNumber;
	}

	public String getThirdBankAccount() {
		return thirdBankAccount;
	}

	public void setThirdBankAccount(String thirdBankAccount) {
		this.thirdBankAccount = thirdBankAccount;
	}

	public String getThirdAccountOpening() {
		return thirdAccountOpening;
	}

	public void setThirdAccountOpening(String thirdAccountOpening) {
		this.thirdAccountOpening = thirdAccountOpening;
	}

	public BigDecimal getThirdAccount() {
		return thirdAccount;
	}

	public void setThirdAccount(BigDecimal thirdAccount) {
		this.thirdAccount = thirdAccount;
	}

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
	 * @param Long
	 *            要设置的id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取创建者
	 *
	 * @return 创建者
	 */
	public Long getCreator() {
		return creator;
	}

	/**
	 * 设置创建者
	 * 
	 * @param creator
	 *            要设置的创建者
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}

	/**
	 * 获取创建时间
	 *
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createTime
	 *            要设置的创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取修改者
	 *
	 * @return 修改者
	 */
	public Long getModifier() {
		return modifier;
	}

	/**
	 * 设置修改者
	 * 
	 * @param modifier
	 *            要设置的修改者
	 */
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	/**
	 * 获取修改时间
	 *
	 * @return 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置修改时间
	 * 
	 * @param modifyTime
	 *            要设置的修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 获取开户人姓名
	 *
	 * @return 开户人姓名
	 */
	public String getAccountHolderName() {
		return accountHolderName;
	}

	/**
	 * 设置开户人姓名
	 * 
	 * @param accountHolderName
	 *            要设置的开户人姓名
	 */
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	/**
	 * 获取放款卡号/打款卡号
	 *
	 * @return 放款卡号/打款卡号
	 */
	public String getCardid() {
		return cardid;
	}

	/**
	 * 设置放款卡号/打款卡号
	 * 
	 * @param cardid
	 *            要设置的放款卡号/打款卡号
	 */
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	/**
	 * 获取开户行
	 *
	 * @return 开户行
	 */
	public Short getBankFlag() {
		return bankFlag;
	}

	/**
	 * 设置开户行
	 * 
	 * @param bankFlag
	 *            要设置的开户行
	 */
	public void setBankFlag(Short bankFlag) {
		this.bankFlag = bankFlag;
	}

	/**
	 * 获取放款金额/打款金额
	 *
	 * @return 放款金额/打款金额
	 */
	public BigDecimal getAccount() {
		return account;
	}

	/**
	 * 设置放款金额/打款金额
	 * 
	 * @param account
	 *            要设置的放款金额/打款金额
	 */
	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * 获取类型
	 *
	 * @return 类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            要设置的类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getThirdTransferFee() {
		return thirdTransferFee;
	}

	public void setThirdTransferFee(BigDecimal thirdTransferFee) {
		this.thirdTransferFee = thirdTransferFee;
	}

}