package com.company.modules.repayment;

public class RepaymentVO {
	
	private Integer pageNum;
	private Integer pageSize;
	/** 项目编号 */
    private String projectCode;
    /** 合同编号 */
    private String contractNo;
    /** 还款状态 */
    private Integer repaymentStatus;
    /** 客户名称 */
    private String customerName;
    /** 机构id */
    private Integer officeId;
    /** 借款期数 */
    private Integer timeLimit;
    /** 执行者id */
    private Integer executorId;
    /** 流程实体id */
    private String processInstanceId;
    /** 还款操作类型 */
    private Integer type;
    /** 真实还款日期 */
    private String realRepaymentDate;
    
    /** 放款开始日期 */
    private String loanStartDate;
    /** 放款结束日期 */
    private String loanEndDate;
    /** 来源 */
    private String businessOriginText;
    /** 机构名称 */
    private String institutionName;
    /** 报单人 */
    private String customerManager; 

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
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Integer getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(Integer repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Integer getExecutorId() {
		return executorId;
	}
	public void setExecutorId(Integer executorId) {
		this.executorId = executorId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRealRepaymentDate() {
		return realRepaymentDate;
	}
	public void setRealRepaymentDate(String realRepaymentDate) {
		this.realRepaymentDate = realRepaymentDate;
	}
	public String getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public String getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	
}
