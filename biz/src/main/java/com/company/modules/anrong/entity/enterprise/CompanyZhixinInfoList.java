package com.company.modules.anrong.entity.enterprise;

public class CompanyZhixinInfoList {
	
	  private String  putrecord; //立案时间
	  private String casenum; //案号
	  private String court; //执行法院
	  private String statute; //案件状态
	  private String money; //执行标的
	  
		public String getPutrecord() {
			return putrecord;
		}
		public void setPutrecord(String putrecord) {
			this.putrecord = putrecord;
		}
		public String getCasenum() {
			return casenum;
		}
		public void setCasenum(String casenum) {
			this.casenum = casenum;
		}
		public String getCourt() {
			return court;
		}
		public void setCourt(String court) {
			this.court = court;
		}
		public String getStatute() {
			return statute;
		}
		public void setStatute(String statute) {
			this.statute = statute;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}

}
