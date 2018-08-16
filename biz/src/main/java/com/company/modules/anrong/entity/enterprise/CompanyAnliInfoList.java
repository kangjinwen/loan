package com.company.modules.anrong.entity.enterprise;

public class CompanyAnliInfoList {
	
  private String  date; //结案时间
  private String casenum; //案号
  private String title; //案件标题
  private String ptype; //当事人类型
  private String casetype; //案件类型
  
  
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCasenum() {
		return casenum;
	}
	public void setCasenum(String casenum) {
		this.casenum = casenum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getCasetype() {
		return casetype;
	}
	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}

}
