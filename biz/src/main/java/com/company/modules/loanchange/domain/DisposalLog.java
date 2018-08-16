package com.company.modules.loanchange.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抵押品处置登记实体
 * @author wgc
 * @version 1.0
 * @since 2015-05-25
 */
public class DisposalLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改者
     */
    private String modifier;
    /**
     * 修改日期
     */
    private Date modifyTime;
    /**
     * 是否删除0正常 1删除
     */
    private Byte isDelete;
    /**
     * 二手车经纪公司
     */
    private String companyName;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * pricingAmount:核价金额
     *
     * @since 1.0.0
     */
    private BigDecimal pricingAmount;
    /**
     * 处置金额
     */
    private BigDecimal buyAmount;
    /**
     * 交易日期
     */
    private Date transactionDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 处置相关图处
     */
    private String attachIds;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    
    /**
     * 流程实例ID
     */
    private String branchingProcessId;
    
    /**
     * 项目ID
     */
    private Long projectId;
    
    /**
     * reason:车辆处置原因
     *
     * @since 1.0.0
     */
    private String reason;
    
    public String getBranchingProcessId() {
		return branchingProcessId;
	}

	public void setBranchingProcessId(String branchingProcessId) {
		this.branchingProcessId = branchingProcessId;
	}

	/**
     * 获取主键
     * 
     * @return 主键
     */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置主键
     * 
     * @param id 要设置的主键
     */
    public void setId(Long id){
        this.id = id;
    }
    /**
     * 获取创建者
     * 
     * @return 创建者
     */
    public String getCreator(){
        return creator;
    }
    
    /**
     * 设置创建者
     * 
     * @param creator 要设置的创建者
     */
    public void setCreator(String creator){
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
    public String getModifier(){
        return modifier;
    }
    
    /**
     * 设置修改者
     * 
     * @param modifier 要设置的修改者
     */
    public void setModifier(String modifier){
        this.modifier = modifier;
    }
    /**
     * 获取修改日期
     * 
     * @return 修改日期
     */
    public Date getModifyTime(){
        return modifyTime;
    }
    
    /**
     * 设置修改日期
     * 
     * @param modifyTime 要设置的修改日期
     */
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }
    /**
     * 获取是否删除0正常 1删除
     * 
     * @return 是否删除0正常 1删除
     */
    public Byte getIsDelete(){
        return isDelete;
    }
    
    /**
     * 设置是否删除0正常 1删除
     * 
     * @param isDelete 要设置的是否删除0正常 1删除
     */
    public void setIsDelete(Byte isDelete){
        this.isDelete = isDelete;
    }
    /**
     * 获取二手车经纪公司
     * 
     * @return 二手车经纪公司
     */
    public String getCompanyName(){
        return companyName;
    }
    
    /**
     * 设置二手车经纪公司
     * 
     * @param companyName 要设置的二手车经纪公司
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    /**
     * 获取联系电话
     * 
     * @return 联系电话
     */
    public String getMobile(){
        return mobile;
    }
    
    /**
     * 设置联系电话
     * 
     * @param mobile 要设置的联系电话
     */
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    /**
     * 获取处置金额
     * 
     * @return 处置金额
     */
    public BigDecimal getBuyAmount(){
        return buyAmount;
    }
    
    /**
     * 设置处置金额
     * 
     * @param buyAmount 要设置的处置金额
     */
    public void setBuyAmount(BigDecimal buyAmount){
        this.buyAmount = buyAmount;
    }
    /**
     * 获取交易日期
     * 
     * @return 交易日期
     */
    public Date getTransactionDate(){
        return transactionDate;
    }
    
    /**
     * 设置交易日期
     * 
     * @param transactionDate 要设置的交易日期
     */
    public void setTransactionDate(Date transactionDate){
        this.transactionDate = transactionDate;
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
    /**
     * 获取处置相关图处
     * 
     * @return 处置相关图处
     */
    public String getAttachIds(){
        return attachIds;
    }
    
    /**
     * 设置处置相关图处
     * 
     * @param attachIds 要设置的处置相关图处
     */
    public void setAttachIds(String attachIds){
        this.attachIds = attachIds;
    }
    /**
     * 获取流程实例ID
     * 
     * @return 流程实例ID
     */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 设置流程实例ID
     * 
     * @param processInstanceId 要设置的流程实例ID
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }
    /**
     * 获取项目ID
     * 
     * @return 项目ID
     */
    public Long getProjectId(){
        return projectId;
    }
    
    /**
     * 设置项目ID
     * 
     * @param projectId 要设置的项目ID
     */
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    public BigDecimal getPricingAmount() {
        return pricingAmount;
    }

    public void setPricingAmount(BigDecimal pricingAmount) {
        this.pricingAmount = pricingAmount;
    }
    
}


