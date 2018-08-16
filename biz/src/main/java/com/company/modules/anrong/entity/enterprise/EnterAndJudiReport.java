package com.company.modules.anrong.entity.enterprise;

import java.util.List;

public class EnterAndJudiReport {
	private String error;
	
	private List<CompanyZhixinInfoList>  companyZhixinInfoList;
	private List<CompanyShixinInfoList> companyShixinInfoList;
	private List<CompanyAnliInfoList> companyAnliInfoList;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<CompanyZhixinInfoList> getCompanyZhixinInfoList() {
		return companyZhixinInfoList;
	}
	public void setCompanyZhixinInfoList(List<CompanyZhixinInfoList> companyZhixinInfoList) {
		this.companyZhixinInfoList = companyZhixinInfoList;
	}
	public List<CompanyShixinInfoList> getCompanyShixinInfoList() {
		return companyShixinInfoList;
	}
	public void setCompanyShixinInfoList(List<CompanyShixinInfoList> companyShixinInfoList) {
		this.companyShixinInfoList = companyShixinInfoList;
	}
	public List<CompanyAnliInfoList> getCompanyAnliInfoList() {
		return companyAnliInfoList;
	}
	public void setCompanyAnliInfoList(List<CompanyAnliInfoList> companyAnliInfoList) {
		this.companyAnliInfoList = companyAnliInfoList;
	}

}
