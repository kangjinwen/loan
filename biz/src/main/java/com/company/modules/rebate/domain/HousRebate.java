package com.company.modules.rebate.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 返佣实体
 * @author mcwang
 * @version 1.0
 * @since 2016-08-15 02:29:43
 */
public class HousRebate implements Serializable {
	
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
    * 返佣点位
    */
    private BigDecimal rebatePoints;
    /**
    * 返佣金额
    */
    private BigDecimal rebateAmount;
    /**
    * 返佣期限
    */
    private Long rebateDeadline;
    /**
    * 返佣卡号
    */
    private String rebateCard;
    /**
    * 开户行
    */
    private String bankName;
    /**
     * 返费人姓名
     */
    private String salesman;
    
    private Integer period;
    
    private String returnBankName;
    
    public String getSalesman() {
		return salesman;
	}


	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
    
    public String getReturnBankName() {
		return returnBankName;
	}

	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}

	/**
     * 实际返佣金额
     */
     private BigDecimal realRebateAmount;
     
     /**
      * 财务专员
      */
     private Long financialId;
    
    public BigDecimal getRealRebateAmount() {
		return realRebateAmount;
	}

	public Long getFinancialId() {
		return financialId;
	}

	public void setFinancialId(Long financialId) {
		this.financialId = financialId;
	}

	public void setRealRebateAmount(BigDecimal realRebateAmount) {
		this.realRebateAmount = realRebateAmount;
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
    * 获取返佣点位
    *
    * @return 返佣点位
    */
    public BigDecimal getRebatePoints(){
        return rebatePoints;
    }
    
    /**
     * 设置返佣点位
     * 
     * @param rebatePoints 要设置的返佣点位
     */
    public void setRebatePoints(BigDecimal rebatePoints){
        this.rebatePoints = rebatePoints;
    }

    /**
    * 获取返佣金额
    *
    * @return 返佣金额
    */
    public BigDecimal getRebateAmount(){
        return rebateAmount;
    }
    
    /**
     * 设置返佣金额
     * 
     * @param rebateAmount 要设置的返佣金额
     */
    public void setRebateAmount(BigDecimal rebateAmount){
        this.rebateAmount = rebateAmount;
    }

    /**
    * 获取返佣期限
    *
    * @return 返佣期限
    */
    public Long getRebateDeadline(){
        return rebateDeadline;
    }
    
    /**
     * 设置返佣期限
     * 
     * @param rebateDeadline 要设置的返佣期限
     */
    public void setRebateDeadline(Long rebateDeadline){
        this.rebateDeadline = rebateDeadline;
    }

    /**
    * 获取返佣卡号
    *
    * @return 返佣卡号
    */
    public String getRebateCard(){
        return rebateCard;
    }
    
    /**
     * 设置返佣卡号
     * 
     * @param rebateCard 要设置的返佣卡号
     */
    public void setRebateCard(String rebateCard){
        this.rebateCard = rebateCard;
    }

    /**
    * 获取开户行
    *
    * @return 开户行
    */
    public String getBankName(){
        return bankName;
    }
    
    /**
     * 设置开户行
     * 
     * @param bankName 要设置的开户行
     */
    public void setBankName(String bankName){
        this.bankName = bankName;
    }


	public Integer getPeriod() {
		return period;
	}


	public void setPeriod(Integer period) {
		this.period = period;
	}
    
}