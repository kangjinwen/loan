package com.company.modules.anrong.entity.msp;

public class BlackData  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createDate;
	private String creditAddress;
	private String arrears;
	private String overdueDays;
//	private String overduePeriods;
	private String phone;
	private String email;
	private String residenceAddress;
	private String currentAddress;
	private String lastOverdueDate;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreditAddress() {
		return creditAddress;
	}
	public void setCreditAddress(String creditAddress) {
		this.creditAddress = creditAddress;
	}
	public String getArrears() {
		return arrears;
	}
	public void setArrears(String arrears) {
		this.arrears = arrears;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	
	public String getLastOverdueDate() {
		return lastOverdueDate;
	}
	public void setLastOverdueDate(String lastOverdueDate) {
		this.lastOverdueDate = lastOverdueDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResidenceAddress() {
		return residenceAddress;
	}
	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	
	
}
