package com.company.modules.warrant.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料清单表(权证下户)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-10 05:14:20
 */
public class HousDataList implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 创建人
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
    * 0不删除1已删除
    */
    private Byte isDelete;
    /**
    * 咨询id
    */
    private Long consultId;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 项目编号
    */
    private Long projectId;
    /**
    * 户口本
    */
    private Byte accountBook;
    /**
    * 结婚证
    */
    private Byte marriageCertificate;
    /**
    * 离婚证
    */
    private Byte divorceCertificate;
    /**
    * 离婚协议
    */
    private Byte divorceAgreement;
    /**
    * 民事调解书
    */
    private Byte civilMediation;
    /**
    * 法院判决书
    */
    private Byte courtVerdict;
    /**
    * 是否唯一住房
    */
    private Byte onlyHousing;
    /**
    * 房产证
    */
    private Byte deed;
    /**
    * 购房合同
    */
    private Byte purchaseContract;
    /**
    * 租赁合同
    */
    private Byte leaseContract;
    /**
    * 抵押贷款合同
    */
    private Byte mortgageLoanContract;
    /**
    * 贷款卡
    */
    private Byte loanCard;
    /**
    * 征信报告
    */
    private Byte creditReport;
    /**
     * 满二或满五
     */
     private Byte twoOrFive;
    
    public Byte getTwoOrFive() {
		return twoOrFive;
	}

	public void setTwoOrFive(Byte twoOrFive) {
		this.twoOrFive = twoOrFive;
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
    
    /**
    * 获取创建人
    *
    * @return 创建人
    */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 设置创建人
     * 
     * @param creator 要设置的创建人
     */
    public void setCreator(Long creator){
        this.creator = creator;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置创建时间
     * 
     * @param createTime 要设置的创建时间
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取修改者
    *
    * @return 修改者
    */
    public Long getModifier(){
        return modifier;
    }
    
    /**
     * 设置修改者
     * 
     * @param modifier 要设置的修改者
     */
    public void setModifier(Long modifier){
        this.modifier = modifier;
    }

    /**
    * 获取修改时间
    *
    * @return 修改时间
    */
    public Date getModifyTime(){
        return modifyTime;
    }
    
    /**
     * 设置修改时间
     * 
     * @param modifyTime 要设置的修改时间
     */
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }

    /**
    * 获取0不删除1已删除
    *
    * @return 0不删除1已删除
    */
    public Byte getIsDelete(){
        return isDelete;
    }
    
    /**
     * 设置0不删除1已删除
     * 
     * @param isDelete 要设置的0不删除1已删除
     */
    public void setIsDelete(Byte isDelete){
        this.isDelete = isDelete;
    }

    /**
    * 获取咨询id
    *
    * @return 咨询id
    */
    public Long getConsultId(){
        return consultId;
    }
    
    /**
     * 设置咨询id
     * 
     * @param consultId 要设置的咨询id
     */
    public void setConsultId(Long consultId){
        this.consultId = consultId;
    }

    /**
    * 获取流程ID
    *
    * @return 流程ID
    */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置流程ID
     * 
     * @param processInstanceId 要设置的流程ID
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }

    /**
    * 获取项目编号
    *
    * @return 项目编号
    */
    public Long getProjectId(){
        return projectId;
    }
    
    /**
     * 设置项目编号
     * 
     * @param projectId 要设置的项目编号
     */
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    /**
    * 获取户口本
    *
    * @return 户口本
    */
    public Byte getAccountBook(){
        return accountBook;
    }
    
    /**
     * 设置户口本
     * 
     * @param accountBook 要设置的户口本
     */
    public void setAccountBook(Byte accountBook){
        this.accountBook = accountBook;
    }

    /**
    * 获取结婚证
    *
    * @return 结婚证
    */
    public Byte getMarriageCertificate(){
        return marriageCertificate;
    }
    
    /**
     * 设置结婚证
     * 
     * @param marriageCertificate 要设置的结婚证
     */
    public void setMarriageCertificate(Byte marriageCertificate){
        this.marriageCertificate = marriageCertificate;
    }

    /**
    * 获取离婚证
    *
    * @return 离婚证
    */
    public Byte getDivorceCertificate(){
        return divorceCertificate;
    }
    
    /**
     * 设置离婚证
     * 
     * @param divorceCertificate 要设置的离婚证
     */
    public void setDivorceCertificate(Byte divorceCertificate){
        this.divorceCertificate = divorceCertificate;
    }

    /**
    * 获取离婚协议
    *
    * @return 离婚协议
    */
    public Byte getDivorceAgreement(){
        return divorceAgreement;
    }
    
    /**
     * 设置离婚协议
     * 
     * @param divorceAgreement 要设置的离婚协议
     */
    public void setDivorceAgreement(Byte divorceAgreement){
        this.divorceAgreement = divorceAgreement;
    }

    /**
    * 获取民事调解书
    *
    * @return 民事调解书
    */
    public Byte getCivilMediation(){
        return civilMediation;
    }
    
    /**
     * 设置民事调解书
     * 
     * @param civilMediation 要设置的民事调解书
     */
    public void setCivilMediation(Byte civilMediation){
        this.civilMediation = civilMediation;
    }

    /**
    * 获取法院判决书
    *
    * @return 法院判决书
    */
    public Byte getCourtVerdict(){
        return courtVerdict;
    }
    
    /**
     * 设置法院判决书
     * 
     * @param courtVerdict 要设置的法院判决书
     */
    public void setCourtVerdict(Byte courtVerdict){
        this.courtVerdict = courtVerdict;
    }

    /**
    * 获取是否唯一住房
    *
    * @return 是否唯一住房
    */
    public Byte getOnlyHousing(){
        return onlyHousing;
    }
    
    /**
     * 设置是否唯一住房
     * 
     * @param onlyHousing 要设置的是否唯一住房
     */
    public void setOnlyHousing(Byte onlyHousing){
        this.onlyHousing = onlyHousing;
    }

    /**
    * 获取房产证
    *
    * @return 房产证
    */
    public Byte getDeed(){
        return deed;
    }
    
    /**
     * 设置房产证
     * 
     * @param deed 要设置的房产证
     */
    public void setDeed(Byte deed){
        this.deed = deed;
    }

    /**
    * 获取购房合同
    *
    * @return 购房合同
    */
    public Byte getPurchaseContract(){
        return purchaseContract;
    }
    
    /**
     * 设置购房合同
     * 
     * @param purchaseContract 要设置的购房合同
     */
    public void setPurchaseContract(Byte purchaseContract){
        this.purchaseContract = purchaseContract;
    }

    /**
    * 获取租赁合同
    *
    * @return 租赁合同
    */
    public Byte getLeaseContract(){
        return leaseContract;
    }
    
    /**
     * 设置租赁合同
     * 
     * @param leaseContract 要设置的租赁合同
     */
    public void setLeaseContract(Byte leaseContract){
        this.leaseContract = leaseContract;
    }

    /**
    * 获取抵押贷款合同
    *
    * @return 抵押贷款合同
    */
    public Byte getMortgageLoanContract(){
        return mortgageLoanContract;
    }
    
    /**
     * 设置抵押贷款合同
     * 
     * @param mortgageLoanContract 要设置的抵押贷款合同
     */
    public void setMortgageLoanContract(Byte mortgageLoanContract){
        this.mortgageLoanContract = mortgageLoanContract;
    }

    /**
    * 获取贷款卡
    *
    * @return 贷款卡
    */
    public Byte getLoanCard(){
        return loanCard;
    }
    
    /**
     * 设置贷款卡
     * 
     * @param loanCard 要设置的贷款卡
     */
    public void setLoanCard(Byte loanCard){
        this.loanCard = loanCard;
    }

    /**
    * 获取征信报告
    *
    * @return 征信报告
    */
    public Byte getCreditReport(){
        return creditReport;
    }
    
    /**
     * 设置征信报告
     * 
     * @param creditReport 要设置的征信报告
     */
    public void setCreditReport(Byte creditReport){
        this.creditReport = creditReport;
    }

}