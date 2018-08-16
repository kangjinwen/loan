package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 放款实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-28 11:07:59
 */
public class PubRepayloaninfo implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 借款id
    */
    private Long borrowid;
    /**
    * 合同编号
    */
    private String contractNo;
    /**
    * 合同id
    */
    private Long contractid;
    /**
    * 客户名称
    */
    private String customerName;
    /**
    * 客户id
    */
    private Long customerid;
    /**
    * 客户账户
    */
    private String customerAccount;
    /**
    * 借款期限
    */
    private Integer timeLimit;
    /**
    * 营业厅
    */
    private String officeid;
    /**
    * 服务费
    */
    private BigDecimal servicefee;
    /**
    * 
    */
    private Long projectid;
    /**
    * 产品id
    */
    private Long productid;
    /**
    * 抵押类型(1移交2GPS)
    */
    private Byte mortgageType;
    /**
    * 担保方式
    */
    private Byte vouchType;
    /**
    * 还款开始时间
    */
    private Date repaymenttime;
    /**
    * 已还本金
    */
    private BigDecimal repaymentCapitalAmount;
    /**
    * 已还利息
    */
    private BigDecimal repaymentInterestAmount;
    /**
    * 应还本金
    */
    private BigDecimal capitalAmount;
    /**
    * 应还利息
    */
    private BigDecimal interestAmount;
    /**
    * 还款中0，结清1，逾期2
    */
    private Byte repaymentStatus;
    /**
    * 还款处理（0正常 1提前 2减免 3强制 5处置 6追回 7拍卖）
    */
    private Byte repaymentProcess;
    /**
    * 是否锁定 0-未锁定  1-锁定
    */
    private Byte islocked;
    /**
    * 车辆处置金额
    */
    private BigDecimal disposalAmount;
    /**
    * 车辆处置余额
    */
    private BigDecimal disposalBalance;
    /**
    * 风险等级
    */
    private Byte riskLevel;
    /**
     * 还款结束时间
     */
     private Date endRepayTime;
    
    /**
    * 获取id
    *
    * @return id
    */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置id
     * 
     * @param Long 要设置的id
     */
    public void setId(Long id){
        this.id = id;
    }
    
    /**
    * 获取借款id
    *
    * @return 借款id
    */
    public Long getBorrowid(){
        return borrowid;
    }
    
    /**
     * 设置借款id
     * 
     * @param borrowid 要设置的借款id
     */
    public void setBorrowid(Long borrowid){
        this.borrowid = borrowid;
    }

    /**
    * 获取合同编号
    *
    * @return 合同编号
    */
    public String getContractNo(){
        return contractNo;
    }
    
    /**
     * 设置合同编号
     * 
     * @param contractNo 要设置的合同编号
     */
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }

    /**
    * 获取合同id
    *
    * @return 合同id
    */
    public Long getContractid(){
        return contractid;
    }
    
    /**
     * 设置合同id
     * 
     * @param contractid 要设置的合同id
     */
    public void setContractid(Long contractid){
        this.contractid = contractid;
    }

    /**
    * 获取客户名称
    *
    * @return 客户名称
    */
    public String getCustomerName(){
        return customerName;
    }
    
    /**
     * 设置客户名称
     * 
     * @param customerName 要设置的客户名称
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    /**
    * 获取客户id
    *
    * @return 客户id
    */
    public Long getCustomerid(){
        return customerid;
    }
    
    /**
     * 设置客户id
     * 
     * @param customerid 要设置的客户id
     */
    public void setCustomerid(Long customerid){
        this.customerid = customerid;
    }

    /**
    * 获取客户账户
    *
    * @return 客户账户
    */
    public String getCustomerAccount(){
        return customerAccount;
    }
    
    /**
     * 设置客户账户
     * 
     * @param customerAccount 要设置的客户账户
     */
    public void setCustomerAccount(String customerAccount){
        this.customerAccount = customerAccount;
    }

    /**
    * 获取借款期限
    *
    * @return 借款期限
    */
    public Integer getTimeLimit(){
        return timeLimit;
    }
    
    /**
     * 设置借款期限
     * 
     * @param timeLimit 要设置的借款期限
     */
    public void setTimeLimit(Integer timeLimit){
        this.timeLimit = timeLimit;
    }

    /**
    * 获取营业厅
    *
    * @return 营业厅
    */
    public String getOfficeid(){
        return officeid;
    }
    
    /**
     * 设置营业厅
     * 
     * @param officeid 要设置的营业厅
     */
    public void setOfficeid(String officeid){
        this.officeid = officeid;
    }

    /**
    * 获取服务费
    *
    * @return 服务费
    */
    public BigDecimal getServicefee(){
        return servicefee;
    }
    
    /**
     * 设置服务费
     * 
     * @param servicefee 要设置的服务费
     */
    public void setServicefee(BigDecimal servicefee){
        this.servicefee = servicefee;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getProjectid(){
        return projectid;
    }
    
    /**
     * 设置
     * 
     * @param projectid 要设置的
     */
    public void setProjectid(Long projectid){
        this.projectid = projectid;
    }

    /**
    * 获取产品id
    *
    * @return 产品id
    */
    public Long getProductid(){
        return productid;
    }
    
    /**
     * 设置产品id
     * 
     * @param productid 要设置的产品id
     */
    public void setProductid(Long productid){
        this.productid = productid;
    }

    /**
    * 获取抵押类型(1移交2GPS)
    *
    * @return 抵押类型(1移交2GPS)
    */
    public Byte getMortgageType(){
        return mortgageType;
    }
    
    /**
     * 设置抵押类型(1移交2GPS)
     * 
     * @param mortgageType 要设置的抵押类型(1移交2GPS)
     */
    public void setMortgageType(Byte mortgageType){
        this.mortgageType = mortgageType;
    }

    /**
    * 获取担保方式
    *
    * @return 担保方式
    */
    public Byte getVouchType(){
        return vouchType;
    }
    
    /**
     * 设置担保方式
     * 
     * @param vouchType 要设置的担保方式
     */
    public void setVouchType(Byte vouchType){
        this.vouchType = vouchType;
    }

    /**
    * 获取还款开始时间
    *
    * @return 还款开始时间
    */
    public Date getRepaymenttime(){
        return repaymenttime;
    }
    
    /**
     * 设置还款开始时间
     * 
     * @param repaymenttime 要设置的还款开始时间
     */
    public void setRepaymenttime(Date repaymenttime){
        this.repaymenttime = repaymenttime;
    }

    /**
    * 获取已还本金
    *
    * @return 已还本金
    */
    public BigDecimal getRepaymentCapitalAmount(){
        return repaymentCapitalAmount;
    }
    
    /**
     * 设置已还本金
     * 
     * @param repaymentCapitalAmount 要设置的已还本金
     */
    public void setRepaymentCapitalAmount(BigDecimal repaymentCapitalAmount){
        this.repaymentCapitalAmount = repaymentCapitalAmount;
    }

    /**
    * 获取已还利息
    *
    * @return 已还利息
    */
    public BigDecimal getRepaymentInterestAmount(){
        return repaymentInterestAmount;
    }
    
    /**
     * 设置已还利息
     * 
     * @param repaymentInterestAmount 要设置的已还利息
     */
    public void setRepaymentInterestAmount(BigDecimal repaymentInterestAmount){
        this.repaymentInterestAmount = repaymentInterestAmount;
    }

    /**
    * 获取应还本金
    *
    * @return 应还本金
    */
    public BigDecimal getCapitalAmount(){
        return capitalAmount;
    }
    
    /**
     * 设置应还本金
     * 
     * @param capitalAmount 要设置的应还本金
     */
    public void setCapitalAmount(BigDecimal capitalAmount){
        this.capitalAmount = capitalAmount;
    }

    /**
    * 获取应还利息
    *
    * @return 应还利息
    */
    public BigDecimal getInterestAmount(){
        return interestAmount;
    }
    
    /**
     * 设置应还利息
     * 
     * @param interestAmount 要设置的应还利息
     */
    public void setInterestAmount(BigDecimal interestAmount){
        this.interestAmount = interestAmount;
    }

    /**
    * 获取还款中0，结清1，逾期2
    *
    * @return 还款中0，结清1，逾期2
    */
    public Byte getRepaymentStatus(){
        return repaymentStatus;
    }
    
    /**
     * 设置还款中0，结清1，逾期2
     * 
     * @param repaymentStatus 要设置的还款中0，结清1，逾期2
     */
    public void setRepaymentStatus(Byte repaymentStatus){
        this.repaymentStatus = repaymentStatus;
    }

    /**
    * 获取还款处理（0正常 1提前 2减免 3强制 5处置 6追回 7拍卖）
    *
    * @return 还款处理（0正常 1提前 2减免 3强制 5处置 6追回 7拍卖）
    */
    public Byte getRepaymentProcess(){
        return repaymentProcess;
    }
    
    /**
     * 设置还款处理（0正常 1提前 2减免 3强制 5处置 6追回 7拍卖）
     * 
     * @param repaymentProcess 要设置的还款处理（0正常 1提前 2减免 3强制 5处置 6追回 7拍卖）
     */
    public void setRepaymentProcess(Byte repaymentProcess){
        this.repaymentProcess = repaymentProcess;
    }

    /**
    * 获取是否锁定 0-未锁定  1-锁定
    *
    * @return 是否锁定 0-未锁定  1-锁定
    */
    public Byte getIslocked(){
        return islocked;
    }
    
    /**
     * 设置是否锁定 0-未锁定  1-锁定
     * 
     * @param islocked 要设置的是否锁定 0-未锁定  1-锁定
     */
    public void setIslocked(Byte islocked){
        this.islocked = islocked;
    }

    /**
    * 获取车辆处置金额
    *
    * @return 车辆处置金额
    */
    public BigDecimal getDisposalAmount(){
        return disposalAmount;
    }
    
    /**
     * 设置车辆处置金额
     * 
     * @param disposalAmount 要设置的车辆处置金额
     */
    public void setDisposalAmount(BigDecimal disposalAmount){
        this.disposalAmount = disposalAmount;
    }

    /**
    * 获取车辆处置余额
    *
    * @return 车辆处置余额
    */
    public BigDecimal getDisposalBalance(){
        return disposalBalance;
    }
    
    /**
     * 设置车辆处置余额
     * 
     * @param disposalBalance 要设置的车辆处置余额
     */
    public void setDisposalBalance(BigDecimal disposalBalance){
        this.disposalBalance = disposalBalance;
    }

    /**
    * 获取风险等级
    *
    * @return 风险等级
    */
    public Byte getRiskLevel(){
        return riskLevel;
    }
    
    /**
     * 设置风险等级
     * 
     * @param riskLevel 要设置的风险等级
     */
    public void setRiskLevel(Byte riskLevel){
        this.riskLevel = riskLevel;
    }

	public Date getEndRepayTime() {
		return endRepayTime;
	}

	public void setEndRepayTime(Date endRepayTime) {
		this.endRepayTime = endRepayTime;
	}
    
}