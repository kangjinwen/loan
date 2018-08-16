package com.company.modules.anrong.entity.person;

import java.util.List;

public class GeRenGongshang {
	
	private List<EnterOfficeInfor> enterOfficeInfor;
	private List<EnterShareholderInfor> enterShareholderInfor;
	private List<EnterpriseLegalPersonInfor> enterpriseLegalPersonInfor;
	
	private PersonInfo personInfo;
	private String errorDesc;
	private String isSuccess;
	private List<QueryStatus> queryStatus;
	public List<QueryStatus> getQueryStatus() {
		return queryStatus;
	}
	public void setQueryStatus(List<QueryStatus> queryStatus) {
		this.queryStatus = queryStatus;
	}
	public List<EnterOfficeInfor> getEnterOfficeInfor() {
		return enterOfficeInfor;
	}
	public void setEnterOfficeInfor(List<EnterOfficeInfor> enterOfficeInfor) {
		this.enterOfficeInfor = enterOfficeInfor;
	}
	public List<EnterShareholderInfor> getEnterShareholderInfor() {
		return enterShareholderInfor;
	}
	public void setEnterShareholderInfor(List<EnterShareholderInfor> enterShareholderInfor) {
		this.enterShareholderInfor = enterShareholderInfor;
	}
	public List<EnterpriseLegalPersonInfor> getEnterpriseLegalPersonInfor() {
		return enterpriseLegalPersonInfor;
	}
	public void setEnterpriseLegalPersonInfor(List<EnterpriseLegalPersonInfor> enterpriseLegalPersonInfor) {
		this.enterpriseLegalPersonInfor = enterpriseLegalPersonInfor;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	
	
	
}
