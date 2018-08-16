package com.company.modules.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 下户费表实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-12 10:23:50
 */
public class HousLowerCost implements Serializable {
	
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
    * 财务专员
    */
    private String financeSpecialist;
    /**
    * 应收下户费
    */
    private BigDecimal receivableAccount;
    /**
    * 实收下户费
    */
    private BigDecimal actualFee;
    /**
    * 收取形式
    */
    private Byte collectionForm;
    /**
    * 退费申请操作员
    */
    private String refundOperator;
    /**
    * 申请退费时间
    */
    private Date applyRefundTime;
    /**
    * 申请退费金额(元)
    */
    private BigDecimal applyRefundAmount;
    /**
     * 退费是否成功，0否，1是
     */
     private Byte applyRefundIsSuccess;
     /**
      * 缴纳人
      */
      private String payPerson;
      
    
    public String getPayPerson() {
		return payPerson;
	}

	public void setPayPerson(String payPerson) {
		this.payPerson = payPerson;
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
    * 获取财务专员
    *
    * @return 财务专员
    */
    public String getFinanceSpecialist(){
        return financeSpecialist;
    }
    
    /**
     * 设置财务专员
     * 
     * @param financeSpecialist 要设置的财务专员
     */
    public void setFinanceSpecialist(String financeSpecialist){
        this.financeSpecialist = financeSpecialist;
    }

    /**
    * 获取应收下户费
    *
    * @return 应收下户费
    */
    public BigDecimal getReceivableAccount(){
        return receivableAccount;
    }
    
    /**
     * 设置应收下户费
     * 
     * @param receivableAccount 要设置的应收下户费
     */
    public void setReceivableAccount(BigDecimal receivableAccount){
        this.receivableAccount = receivableAccount;
    }

    /**
    * 获取实收下户费
    *
    * @return 实收下户费
    */
    public BigDecimal getActualFee(){
        return actualFee;
    }
    
    /**
     * 设置实收下户费
     * 
     * @param actualFee 要设置的实收下户费
     */
    public void setActualFee(BigDecimal actualFee){
        this.actualFee = actualFee;
    }

    /**
    * 获取收取形式
    *
    * @return 收取形式
    */
    public Byte getCollectionForm(){
        return collectionForm;
    }
    
    /**
     * 设置收取形式
     * 
     * @param collectionForm 要设置的收取形式
     */
    public void setCollectionForm(Byte collectionForm){
        this.collectionForm = collectionForm;
    }

    /**
    * 获取退费申请操作员
    *
    * @return 退费申请操作员
    */
    public String getRefundOperator(){
        return refundOperator;
    }
    
    /**
     * 设置退费申请操作员
     * 
     * @param refundOperator 要设置的退费申请操作员
     */
    public void setRefundOperator(String refundOperator){
        this.refundOperator = refundOperator;
    }

    /**
    * 获取申请退费时间
    *
    * @return 申请退费时间
    */
    public Date getApplyRefundTime(){
        return applyRefundTime;
    }
    
    /**
     * 设置申请退费时间
     * 
     * @param applyRefundTime 要设置的申请退费时间
     */
    public void setApplyRefundTime(Date applyRefundTime){
        this.applyRefundTime = applyRefundTime;
    }

    /**
    * 获取申请退费金额(元)
    *
    * @return 申请退费金额(元)
    */
    public BigDecimal getApplyRefundAmount(){
        return applyRefundAmount;
    }
    
    /**
     * 设置申请退费金额(元)
     * 
     * @param applyRefundAmount 要设置的申请退费金额(元)
     */
    public void setApplyRefundAmount(BigDecimal applyRefundAmount){
        this.applyRefundAmount = applyRefundAmount;
    }

	public Byte getApplyRefundIsSuccess() {
		return applyRefundIsSuccess;
	}

	public void setApplyRefundIsSuccess(Byte applyRefundIsSuccess) {
		this.applyRefundIsSuccess = applyRefundIsSuccess;
	}
    
    

}