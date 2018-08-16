package com.company.modules.warrant.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房屋核行信息表(权证下户)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-10 05:17:32
 */
public class HousQuickInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
    * 学校
    */
    private Byte school;
    /**
    * 医院
    */
    private Byte hospital;
    /**
    * 购物等配套情况
    */
    private Byte shopping;
    /**
    * 银行可否代为提前还款
    */
    private Byte bankPrepayment;
    /**
    * 房屋快出值(元)
    */
    private BigDecimal housingValueFaster;
    /**
    * 房产交易产生的税费(元)
    */
    private BigDecimal propertyTaxes;
    
    /**
     * 银行可否代为领取解押材料
     */
    private Byte bankMaterial;
     /**
      *备注
      * */
    private String remark;
     
     /**
      * 满二或满五
      */
    private Byte twoOrFive;
      /**
       * 尾款
       */
    private BigDecimal balancePayment;
       /**
        * 是否为最高额抵押贷款
        */
    private Byte highestMortgage;
      
      /**
       * 调查员
       */
    private String investigator;
       /**
       * 调查日期
       */
     private Date surveyTime;
     
     
      
  	public String getInvestigator() {
		return investigator;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}

	public Date getSurveyTime() {
		return surveyTime;
	}

	public void setSurveyTime(Date surveyTime) {
		this.surveyTime = surveyTime;
	}

	public Byte getHighestMortgage() {
		return highestMortgage;
	}

	public void setHighestMortgage(Byte highestMortgage) {
		this.highestMortgage = highestMortgage;
	}
      
     
    public BigDecimal getBalancePayment() {
		return balancePayment;
	}

	public void setBalancePayment(BigDecimal balancePayment) {
		this.balancePayment = balancePayment;
	}

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
    * 获取学校
    *
    * @return 学校
    */
    public Byte getSchool(){
        return school;
    }
    
    /**
     * 设置学校
     * 
     * @param school 要设置的学校
     */
    public void setSchool(Byte school){
        this.school = school;
    }

    /**
    * 获取医院
    *
    * @return 医院
    */
    public Byte getHospital(){
        return hospital;
    }
    
    /**
     * 设置医院
     * 
     * @param hospital 要设置的医院
     */
    public void setHospital(Byte hospital){
        this.hospital = hospital;
    }

    /**
    * 获取购物等配套情况
    *
    * @return 购物等配套情况
    */
    public Byte getShopping(){
        return shopping;
    }
    
    /**
     * 设置购物等配套情况
     * 
     * @param shopping 要设置的购物等配套情况
     */
    public void setShopping(Byte shopping){
        this.shopping = shopping;
    }

    /**
    * 获取银行可否代为提前还款
    *
    * @return 银行可否代为提前还款
    */
    public Byte getBankPrepayment(){
        return bankPrepayment;
    }
    
    /**
     * 设置银行可否代为提前还款
     * 
     * @param bankPrepayment 要设置的银行可否代为提前还款
     */
    public void setBankPrepayment(Byte bankPrepayment){
        this.bankPrepayment = bankPrepayment;
    }

    /**
    * 获取房屋快出值(元)
    *
    * @return 房屋快出值(元)
    */
    public BigDecimal getHousingValueFaster(){
        return housingValueFaster;
    }
    
    /**
     * 设置房屋快出值(元)
     * 
     * @param housingValueFaster 要设置的房屋快出值(元)
     */
    public void setHousingValueFaster(BigDecimal housingValueFaster){
        this.housingValueFaster = housingValueFaster;
    }

    /**
    * 获取房产交易产生的税费(元)
    *
    * @return 房产交易产生的税费(元)
    */
    public BigDecimal getPropertyTaxes(){
        return propertyTaxes;
    }
    
    /**
     * 设置房产交易产生的税费(元)
     * 
     * @param propertyTaxes 要设置的房产交易产生的税费(元)
     */
    public void setPropertyTaxes(BigDecimal propertyTaxes){
        this.propertyTaxes = propertyTaxes;
    }

	public Byte getBankMaterial() {
		return bankMaterial;
	}

	public void setBankMaterial(Byte bankMaterial) {
		this.bankMaterial = bankMaterial;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getTwoOrFive() {
		return twoOrFive;
	}

	public void setTwoOrFive(Byte twoOrFive) {
		this.twoOrFive = twoOrFive;
	}

}