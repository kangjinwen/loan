package com.company.modules.audit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 返费签单/代收服务费实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-17 03:54:15
 */
public class HousLoanfees implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 报单来源
    */
    private Long projectBelongs;
    /**
    * 金融顾问
    */
    private String financialAdvisers;
    /**
    * 机构名称
    */
    private String institutionName;
    /**
    * 业务员
    */
    private String salesman;
    /**
    * 返费点位
    */
    private BigDecimal returnRate;
    /**
    * 返费金额
    */
    private BigDecimal returnFee;
    /**
    * 返费期限
    */
    private Long returnLimit;
    /**
    * 返费卡号
    */
    private String returnCard;
    /**
    * 返费开户行(数据字典)
    */
    private Long returnBank;
    
    
    /**
     * 返费开户行
     */
     private String returnBankName;
    
    /**
    * 服务费金额
    */
    private BigDecimal serviceFee;
    /**
    * 姓名
    */
    private String serviceName;
    /**
    * 放款卡号
    */
    private String serviceCard;
    /**
    * 开户行(数据字典)
    */
    private Long serviceBank;
    
    /**
     * 开户行
     */
    private String serviceBankName;
    
    /**
     * 首期利息
     */
    private Long firstInterest;
     
     /**
      * 打款总额
      */
    private BigDecimal sumAccount;
      
    /**
    * 
    */
    private String processinstanceid;
    /**
    * 
    */
    private Long projectId;
    /**
    * 
    */
    private Long creator;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Long modifier;
    /**
    * 
    */
    private Date modifyTime;
    
    private BigDecimal collectionServiceFee;    
    private BigDecimal returnServiceFee;    
    private String collectionServiceName;
    private String collectionServiceCard;
    private String collectionServiceBank;
    
    
    
    public BigDecimal getCollectionServiceFee() {
		return collectionServiceFee;
	}

	public void setCollectionServiceFee(BigDecimal collectionServiceFee) {
		this.collectionServiceFee = collectionServiceFee;
	}

	public BigDecimal getReturnServiceFee() {
		return returnServiceFee;
	}

	public void setReturnServiceFee(BigDecimal returnServiceFee) {
		this.returnServiceFee = returnServiceFee;
	}

	public String getCollectionServiceName() {
		return collectionServiceName;
	}

	public void setCollectionServiceName(String collectionServiceName) {
		this.collectionServiceName = collectionServiceName;
	}

	public String getCollectionServiceCard() {
		return collectionServiceCard;
	}

	public void setCollectionServiceCard(String collectionServiceCard) {
		this.collectionServiceCard = collectionServiceCard;
	}

	public String getCollectionServiceBank() {
		return collectionServiceBank;
	}

	public void setCollectionServiceBank(String collectionServiceBank) {
		this.collectionServiceBank = collectionServiceBank;
	}

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

	public Long getProjectBelongs() {
		return projectBelongs;
	}

	public void setProjectBelongs(Long projectBelongs) {
		this.projectBelongs = projectBelongs;
	}

	/**
    * 获取金融顾问
    *
    * @return 金融顾问
    */
    public String getFinancialAdvisers(){
        return financialAdvisers;
    }
    
    /**
     * 设置金融顾问
     * 
     * @param financialAdvisers 要设置的金融顾问
     */
    public void setFinancialAdvisers(String financialAdvisers){
        this.financialAdvisers = financialAdvisers;
    }

    /**
    * 获取机构名称
    *
    * @return 机构名称
    */
    public String getInstitutionName(){
        return institutionName;
    }
    
    /**
     * 设置机构名称
     * 
     * @param institutionName 要设置的机构名称
     */
    public void setInstitutionName(String institutionName){
        this.institutionName = institutionName;
    }

    /**
    * 获取业务员
    *
    * @return 业务员
    */
    public String getSalesman(){
        return salesman;
    }
    
    /**
     * 设置业务员
     * 
     * @param salesman 要设置的业务员
     */
    public void setSalesman(String salesman){
        this.salesman = salesman;
    }


    public BigDecimal getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(BigDecimal returnRate) {
		this.returnRate = returnRate;
	}

	/**
    * 获取返费金额
    *
    * @return 返费金额
    */
    public BigDecimal getReturnFee(){
        return returnFee;
    }
    
    /**
     * 设置返费金额
     * 
     * @param returnFee 要设置的返费金额
     */
    public void setReturnFee(BigDecimal returnFee){
        this.returnFee = returnFee;
    }

    /**
    * 获取返费期限
    *
    * @return 返费期限
    */
    public Long getReturnLimit(){
        return returnLimit;
    }
    
    /**
     * 设置返费期限
     * 
     * @param returnLimit 要设置的返费期限
     */
    public void setReturnLimit(Long returnLimit){
        this.returnLimit = returnLimit;
    }

    /**
    * 获取返费卡号
    *
    * @return 返费卡号
    */
    public String getReturnCard(){
        return returnCard;
    }
    
    /**
     * 设置返费卡号
     * 
     * @param returnCard 要设置的返费卡号
     */
    public void setReturnCard(String returnCard){
        this.returnCard = returnCard;
    }

    /**
    * 获取返费开户行
    *
    * @return 返费开户行
    */
    public Long getReturnBank(){
        return returnBank;
    }
    
    /**
     * 设置返费开户行
     * 
     * @param returnBank 要设置的返费开户行
     */
    public void setReturnBank(Long returnBank){
        this.returnBank = returnBank;
    }

    /**
    * 获取服务费金额
    *
    * @return 服务费金额
    */
    public BigDecimal getServiceFee(){
        return serviceFee;
    }
    
    /**
     * 设置服务费金额
     * 
     * @param serviceFee 要设置的服务费金额
     */
    public void setServiceFee(BigDecimal serviceFee){
        this.serviceFee = serviceFee;
    }

    /**
    * 获取姓名（服务费）
    *
    * @return 姓名（服务费）
    */
    public String getServiceName(){
        return serviceName;
    }
    
    /**
     * 设置姓名（服务费）
     * 
     * @param serviceName 要设置的姓名（服务费）
     */
    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    /**
    * 获取放款卡号
    *
    * @return 放款卡号
    */
    public String getServiceCard(){
        return serviceCard;
    }
    
    /**
     * 设置放款卡号
     * 
     * @param serviceCard 要设置的放款卡号
     */
    public void setServiceCard(String serviceCard){
        this.serviceCard = serviceCard;
    }

    /**
    * 获取开户行（服务费）
    *
    * @return 开户行（服务费）
    */
    public Long getServiceBank(){
        return serviceBank;
    }
    
    /**
     * 设置开户行（服务费）
     * 
     * @param serviceBank 要设置的开户行（服务费）
     */
    public void setServiceBank(Long serviceBank){
        this.serviceBank = serviceBank;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getProcessinstanceid(){
        return processinstanceid;
    }
    
    /**
     * 设置
     * 
     * @param processinstanceid 要设置的
     */
    public void setProcessinstanceid(String processinstanceid){
        this.processinstanceid = processinstanceid;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getProjectId(){
        return projectId;
    }
    
    /**
     * 设置
     * 
     * @param projectId 要设置的
     */
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 设置
     * 
     * @param creator 要设置的
     */
    public void setCreator(Long creator){
        this.creator = creator;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置
     * 
     * @param createTime 要设置的
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getModifier(){
        return modifier;
    }
    
    /**
     * 设置
     * 
     * @param modifier 要设置的
     */
    public void setModifier(Long modifier){
        this.modifier = modifier;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getModifyTime(){
        return modifyTime;
    }
    
    /**
     * 设置
     * 
     * @param modifyTime 要设置的
     */
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }

	public Long getFirstInterest() {
		return firstInterest;
	}

	public void setFirstInterest(Long firstInterest) {
		this.firstInterest = firstInterest;
	}

	public BigDecimal getSumAccount() {
		return sumAccount;
	}

	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}

	public String getServiceBankName() {
		return serviceBankName;
	}

	public void setServiceBankName(String serviceBankName) {
		this.serviceBankName = serviceBankName;
	}

	public String getReturnBankName() {
		return returnBankName;
	}

	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	
	
}