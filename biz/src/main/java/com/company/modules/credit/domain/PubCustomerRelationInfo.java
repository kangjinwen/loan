package com.company.modules.credit.domain;

import java.io.Serializable;

public class PubCustomerRelationInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 证件类型
	 */
	private Integer certType;
	/**
	 * 证件号码
	 */
	private String certNumber;
	/**
	 * 出生年月
	 */
	private String birthday;
	/**
	 * 联系次数
	 */
	private String contactCount;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 年纪
	 */
	private Integer age;
	/**
	 * 贷款次数
	 */
	private String loans;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getCertType() {
		return certType;
	}
	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	public String getCertNumber() {
		return certNumber;
	}
	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getContactCount() {
		return contactCount;
	}
	public void setContactCount(String contactCount) {
		this.contactCount = contactCount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLoans() {
		return loans;
	}
	public void setLoans(String loans) {
		this.loans = loans;
	}
	
	
}
