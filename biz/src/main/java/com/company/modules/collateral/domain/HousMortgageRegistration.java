package com.company.modules.collateral.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 押品登记表实体
 * @author mcwang
 * @version 1.0
 * @since 2016-08-08 04:18:34
 */
public class HousMortgageRegistration implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
    /**
    * 编号id
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
    * 有无抵押
    */
    private Byte mortgage;
    /**
    * 有无查封
    */
    private Byte seizure;
    /**
    * 有无业务占用
    */
    private Byte businessOccupancy;
    /**
    * 有无网签占用
    */
    private Byte netSignedOccupancy;
    /**
    * 卡号
    */
    private String creditCardNumber;
    /**
    * 开户行
    */
    private String bankAccount;
    /**
    * 开户网点
    */
    private String accountOpening;
    /**
    * 押品编号
    */
    private String chargeNumber;
    /**
    * 他项权利证名称(即解押押品名称)
    */
    private String hisRightCertificate;
    /**
    * 抵押权人
    */
    private String mortgageRight;
    /**
    * 委托人
    */
    private String client;
    /**
    * 领取时间(即解押押品入库时间)
    */
    private Date collectionTime;
    /**
    * 登记人(即解押入库人)
    */
    private String registeredPerson;
    /**
    * 押品出库时间
    */
    private Date outboundCollectionTime;
    /**
    * 出库人
    */
    private String outboundPerson;
    /**
    * 备注
    */
    private String remark;
    /**
     * 第三方卡号卡号
     */
     private String thirdCardNumber;
     /**
     * 第三方开户行
     */
     private String thirdAccountOpening;
     /**
     * 第三方开户名
     */
     private String thirdBankAccount;
     /**
      * 出借人
      */
      private String lendersMortgage;
    
     
     
    public String getLendersMortgage() {
		return lendersMortgage;
	}

	public void setLendersMortgage(String lendersMortgage) {
		this.lendersMortgage = lendersMortgage;
	}

	public String getThirdCardNumber() {
		return thirdCardNumber;
	}

	public void setThirdCardNumber(String thirdCardNumber) {
		this.thirdCardNumber = thirdCardNumber;
	}

	public String getThirdAccountOpening() {
		return thirdAccountOpening;
	}

	public void setThirdAccountOpening(String thirdAccountOpening) {
		this.thirdAccountOpening = thirdAccountOpening;
	}

	public String getThirdBankAccount() {
		return thirdBankAccount;
	}

	public void setThirdBankAccount(String thirdBankAccount) {
		this.thirdBankAccount = thirdBankAccount;
	}

	/**
    * 获取编号id
    *
    * @return 编号id
    */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置编号id
     * 
     * @param id 要设置的编号id
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
    * 获取有无抵押
    *
    * @return 有无抵押
    */
    public Byte getMortgage(){
        return mortgage;
    }
    
    /**
     * 设置有无抵押
     * 
     * @param mortgage 要设置的有无抵押
     */
    public void setMortgage(Byte mortgage){
        this.mortgage = mortgage;
    }
    /**
    * 获取有无查封
    *
    * @return 有无查封
    */
    public Byte getSeizure(){
        return seizure;
    }
    
    /**
     * 设置有无查封
     * 
     * @param seizure 要设置的有无查封
     */
    public void setSeizure(Byte seizure){
        this.seizure = seizure;
    }
    /**
    * 获取有无业务占用
    *
    * @return 有无业务占用
    */
    public Byte getBusinessOccupancy(){
        return businessOccupancy;
    }
    
    /**
     * 设置有无业务占用
     * 
     * @param businessOccupancy 要设置的有无业务占用
     */
    public void setBusinessOccupancy(Byte businessOccupancy){
        this.businessOccupancy = businessOccupancy;
    }
    /**
    * 获取有无网签占用
    *
    * @return 有无网签占用
    */
    public Byte getNetSignedOccupancy(){
        return netSignedOccupancy;
    }
    
    /**
     * 设置有无网签占用
     * 
     * @param netSignedOccupancy 要设置的有无网签占用
     */
    public void setNetSignedOccupancy(Byte netSignedOccupancy){
        this.netSignedOccupancy = netSignedOccupancy;
    }
    /**
    * 获取卡号
    *
    * @return 卡号
    */
    public String getCreditCardNumber(){
        return creditCardNumber;
    }
    
    /**
     * 设置卡号
     * 
     * @param creditCardNumber 要设置的卡号
     */
    public void setCreditCardNumber(String creditCardNumber){
        this.creditCardNumber = creditCardNumber;
    }
    /**
    * 获取开户行
    *
    * @return 开户行
    */
    public String getBankAccount(){
        return bankAccount;
    }
    
    /**
     * 设置开户行
     * 
     * @param bankAccount 要设置的开户行
     */
    public void setBankAccount(String bankAccount){
        this.bankAccount = bankAccount;
    }
    /**
    * 获取开户网点
    *
    * @return 开户网点
    */
    public String getAccountOpening(){
        return accountOpening;
    }
    
    /**
     * 设置开户网点
     * 
     * @param accountOpening 要设置的开户网点
     */
    public void setAccountOpening(String accountOpening){
        this.accountOpening = accountOpening;
    }
    /**
    * 获取押品编号
    *
    * @return 押品编号
    */
    public String getChargeNumber(){
        return chargeNumber;
    }
    
    /**
     * 设置押品编号
     * 
     * @param chargeNumber 要设置的押品编号
     */
    public void setChargeNumber(String chargeNumber){
        this.chargeNumber = chargeNumber;
    }
    /**
    * 获取他项权利证名称(即解押押品名称)
    *
    * @return 他项权利证名称(即解押押品名称)
    */
    public String getHisRightCertificate(){
        return hisRightCertificate;
    }
    
    /**
     * 设置他项权利证名称(即解押押品名称)
     * 
     * @param hisRightCertificate 要设置的他项权利证名称(即解押押品名称)
     */
    public void setHisRightCertificate(String hisRightCertificate){
        this.hisRightCertificate = hisRightCertificate;
    }
    /**
    * 获取抵押权人
    *
    * @return 抵押权人
    */
    public String getMortgageRight(){
        return mortgageRight;
    }
    
    /**
     * 设置抵押权人
     * 
     * @param mortgageRight 要设置的抵押权人
     */
    public void setMortgageRight(String mortgageRight){
        this.mortgageRight = mortgageRight;
    }
    /**
    * 获取委托人
    *
    * @return 委托人
    */
    public String getClient(){
        return client;
    }
    
    /**
     * 设置委托人
     * 
     * @param client 要设置的委托人
     */
    public void setClient(String client){
        this.client = client;
    }
    /**
    * 获取领取时间(即解押押品入库时间)
    *
    * @return 领取时间(即解押押品入库时间)
    */
    public Date getCollectionTime(){
        return collectionTime;
    }
    
    /**
     * 设置领取时间(即解押押品入库时间)
     * 
     * @param collectionTime 要设置的领取时间(即解押押品入库时间)
     */
    public void setCollectionTime(Date collectionTime){
        this.collectionTime = collectionTime;
    }
    /**
    * 获取登记人(即解押入库人)
    *
    * @return 登记人(即解押入库人)
    */
    public String getRegisteredPerson(){
        return registeredPerson;
    }
    
    /**
     * 设置登记人(即解押入库人)
     * 
     * @param registeredPerson 要设置的登记人(即解押入库人)
     */
    public void setRegisteredPerson(String registeredPerson){
        this.registeredPerson = registeredPerson;
    }
    /**
    * 获取押品出库时间
    *
    * @return 押品出库时间
    */
    public Date getOutboundCollectionTime(){
        return outboundCollectionTime;
    }
    
    /**
     * 设置押品出库时间
     * 
     * @param outboundCollectionTime 要设置的押品出库时间
     */
    public void setOutboundCollectionTime(Date outboundCollectionTime){
        this.outboundCollectionTime = outboundCollectionTime;
    }
    /**
    * 获取出库人
    *
    * @return 出库人
    */
    public String getOutboundPerson(){
        return outboundPerson;
    }
    
    /**
     * 设置出库人
     * 
     * @param outboundPerson 要设置的出库人
     */
    public void setOutboundPerson(String outboundPerson){
        this.outboundPerson = outboundPerson;
    }
    /**
    * 获取备注
    *
    * @return 备注
    */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置备注
     * 
     * @param remark 要设置的备注
     */
    public void setRemark(String remark){
        this.remark = remark;
    }
}


