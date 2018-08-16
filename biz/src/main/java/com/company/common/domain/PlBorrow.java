package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 借款信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-15 06:14:42
 */
public class PlBorrow implements Serializable {

	private static final Long serialVersionUID = 1L;

	/**
    * id
    */
    private Long id;
    /**
    * 实际还款本金
    */
    private BigDecimal repaymentYesAccount;
    /**
    * 实际还款利息
    */
    private BigDecimal repaymentYesInterest;
    /**
    * 还款利率 正常按这个利率计算
    */
    private BigDecimal repaymentRate;
    /**
    * 借款期限/月
    */
    //private Byte timeLimit;
    private Integer timeLimit;

    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 记录增加者
    */
    private Long creator;
    /**
    * 修改时间
    */
    private Date modifyTime;
    /**
    * 记录修改者
    */
    private Long modifier;
    /**
    * 是否删除 0不删除1已删除
    */
    private Byte isDelete;
    /**
    * 借款金额
    */
    private BigDecimal account;
    /**
    * 咨询ID
    */
    private Long consultId;
    /**
    * 还款方式
    */
    private Byte repaymentType;
    /**
    * 实际放款日期
    */
    private Date loanTime;
    /**
    * 预计还款日如：20 每月20日还
    */
    private Byte repaymentDay;
    /**
    * 产品ID
    */
    private Long productId;
    /**
    * 逾期罚息利率（M%/天）
    */
    private BigDecimal overduePenaltyRate;
    /**
    * 逾期还款利率  超过还款期按这个计算  [这个字段暂时作费]
    */
    private BigDecimal overdueRepaymentRate;
    /**
    * 提前还款违约比率X%/月
    */
    private BigDecimal repaymentDefault;
    /**
    * 借款说明
    */
    private String remark;
    /**
    * 记录状态 0正常，-1无效
    */
    private Byte status;
    /**
    * 放款人
    */
    private Long lenders;
    /**
    * 放款人所属机构
    */
    private String lendersOffice;
    /**
    * 申请进件人-提交这笔贷款的人
    */
    private Long loanUser;
    /**
    * 期望放款日期
    */
    private Date expectTime;
    /**
    * 还款中0，结清1，逾期2,已展期3
    */
    private byte repaymentStatus;
    /**
    * 项目编号
    */
    private Long projectId;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 申请进件人所属机构
    */
    private String loanOffice;
    /**
    * 借款需求表ID
    */
    private Long requirementId;
    /**
     * 成单利率
     */
     private BigDecimal singleRate;

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
    * 获取实际还款本金
    *
    * @return 实际还款本金
    */
    public BigDecimal getRepaymentYesAccount(){
        return repaymentYesAccount;
    }

    /**
     * 设置实际还款本金
     *
     * @param repaymentYesAccount 要设置的实际还款本金
     */
    public void setRepaymentYesAccount(BigDecimal repaymentYesAccount){
        this.repaymentYesAccount = repaymentYesAccount;
    }

    /**
    * 获取实际还款利息
    *
    * @return 实际还款利息
    */
    public BigDecimal getRepaymentYesInterest(){
        return repaymentYesInterest;
    }

    /**
     * 设置实际还款利息
     *
     * @param repaymentYesInterest 要设置的实际还款利息
     */
    public void setRepaymentYesInterest(BigDecimal repaymentYesInterest){
        this.repaymentYesInterest = repaymentYesInterest;
    }

    /**
    * 获取还款利率 正常按这个利率计算
    *
    * @return 还款利率 正常按这个利率计算
    */
    public BigDecimal getRepaymentRate(){
        return repaymentRate;
    }

    /**
     * 设置还款利率 正常按这个利率计算
     *
     * @param repaymentRate 要设置的还款利率 正常按这个利率计算
     */
    public void setRepaymentRate(BigDecimal repaymentRate){
        this.repaymentRate = repaymentRate;
    }

    /**
    * 获取借款期限/月
    *
    * @return 借款期限/月
    */
    public Integer getTimeLimit(){
        return timeLimit;
    }

    /**
     * 设置借款期限/月
     *
     * @param timeLimit 要设置的借款期限/月
     */
    public void setTimeLimit(Integer timeLimit){
        this.timeLimit = timeLimit;
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
    * 获取记录增加者
    *
    * @return 记录增加者
    */
    public Long getCreator(){
        return creator;
    }

    /**
     * 设置记录增加者
     *
     * @param creator 要设置的记录增加者
     */
    public void setCreator(Long creator){
        this.creator = creator;
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
    * 获取记录修改者
    *
    * @return 记录修改者
    */
    public Long getModifier(){
        return modifier;
    }

    /**
     * 设置记录修改者
     *
     * @param modifier 要设置的记录修改者
     */
    public void setModifier(Long modifier){
        this.modifier = modifier;
    }

    /**
    * 获取是否删除 0不删除1已删除
    *
    * @return 是否删除 0不删除1已删除
    */
    public Byte getIsDelete(){
        return isDelete;
    }

    /**
     * 设置是否删除 0不删除1已删除
     *
     * @param isDelete 要设置的是否删除 0不删除1已删除
     */
    public void setIsDelete(Byte isDelete){
        this.isDelete = isDelete;
    }

    /**
    * 获取借款金额
    *
    * @return 借款金额
    */
    public BigDecimal getAccount(){
        return account;
    }

    /**
     * 设置借款金额
     *
     * @param account 要设置的借款金额
     */
    public void setAccount(BigDecimal account){
        this.account = account;
    }

    /**
    * 获取咨询ID
    *
    * @return 咨询ID
    */
    public Long getConsultId(){
        return consultId;
    }

    /**
     * 设置咨询ID
     *
     * @param consultId 要设置的咨询ID
     */
    public void setConsultId(Long consultId){
        this.consultId = consultId;
    }

    /**
    * 获取还款方式
    *
    * @return 还款方式
    */
    public Byte getRepaymentType(){
        return repaymentType;
    }

    /**
     * 设置还款方式
     *
     * @param repaymentType 要设置的还款方式
     */
    public void setRepaymentType(Byte repaymentType){
        this.repaymentType = repaymentType;
    }

    /**
    * 获取实际放款日期
    *
    * @return 实际放款日期
    */
    public Date getLoanTime(){
        return loanTime;
    }

    /**
     * 设置实际放款日期
     *
     * @param loanTime 要设置的实际放款日期
     */
    public void setLoanTime(Date loanTime){
        this.loanTime = loanTime;
    }

    /**
    * 获取预计还款日如：20 每月20日还
    *
    * @return 预计还款日如：20 每月20日还
    */
    public Byte getRepaymentDay(){
        return repaymentDay;
    }

    /**
     * 设置预计还款日如：20 每月20日还
     *
     * @param repaymentDay 要设置的预计还款日如：20 每月20日还
     */
    public void setRepaymentDay(Byte repaymentDay){
        this.repaymentDay = repaymentDay;
    }

    /**
    * 获取产品ID
    *
    * @return 产品ID
    */
    public Long getProductId(){
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 要设置的产品ID
     */
    public void setProductId(Long productId){
        this.productId = productId;
    }

    /**
    * 获取逾期罚息利率（M%/天）
    *
    * @return 逾期罚息利率（M%/天）
    */
    public BigDecimal getOverduePenaltyRate(){
        return overduePenaltyRate;
    }

    /**
     * 设置逾期罚息利率（M%/天）
     *
     * @param overduePenaltyRate 要设置的逾期罚息利率（M%/天）
     */
    public void setOverduePenaltyRate(BigDecimal overduePenaltyRate){
        this.overduePenaltyRate = overduePenaltyRate;
    }

    /**
    * 获取逾期还款利率  超过还款期按这个计算  [这个字段暂时作费]
    *
    * @return 逾期还款利率  超过还款期按这个计算  [这个字段暂时作费]
    */
    public BigDecimal getOverdueRepaymentRate(){
        return overdueRepaymentRate;
    }

    /**
     * 设置逾期还款利率  超过还款期按这个计算  [这个字段暂时作费]
     *
     * @param overdueRepaymentRate 要设置的逾期还款利率  超过还款期按这个计算  [这个字段暂时作费]
     */
    public void setOverdueRepaymentRate(BigDecimal overdueRepaymentRate){
        this.overdueRepaymentRate = overdueRepaymentRate;
    }

    /**
    * 获取提前还款违约比率X%/月
    *
    * @return 提前还款违约比率X%/月
    */
    public BigDecimal getRepaymentDefault(){
        return repaymentDefault;
    }

    /**
     * 设置提前还款违约比率X%/月
     *
     * @param repaymentDefault 要设置的提前还款违约比率X%/月
     */
    public void setRepaymentDefault(BigDecimal repaymentDefault){
        this.repaymentDefault = repaymentDefault;
    }

    /**
    * 获取借款说明
    *
    * @return 借款说明
    */
    public String getRemark(){
        return remark;
    }

    /**
     * 设置借款说明
     *
     * @param remark 要设置的借款说明
     */
    public void setRemark(String remark){
        this.remark = remark;
    }

    /**
    * 获取记录状态 0正常，-1无效
    *
    * @return 记录状态 0正常，-1无效
    */
    public Byte getStatus(){
        return status;
    }

    /**
     * 设置记录状态 0正常，-1无效
     *
     * @param status 要设置的记录状态 0正常，-1无效
     */
    public void setStatus(Byte status){
        this.status = status;
    }

    /**
    * 获取放款人
    *
    * @return 放款人
    */
    public Long getLenders(){
        return lenders;
    }

    /**
     * 设置放款人
     *
     * @param lenders 要设置的放款人
     */
    public void setLenders(Long lenders){
        this.lenders = lenders;
    }

    /**
    * 获取放款人所属机构
    *
    * @return 放款人所属机构
    */
    public String getLendersOffice(){
        return lendersOffice;
    }

    /**
     * 设置放款人所属机构
     *
     * @param lendersOffice 要设置的放款人所属机构
     */
    public void setLendersOffice(String lendersOffice){
        this.lendersOffice = lendersOffice;
    }

    /**
    * 获取申请进件人-提交这笔贷款的人
    *
    * @return 申请进件人-提交这笔贷款的人
    */
    public Long getLoanUser(){
        return loanUser;
    }

    /**
     * 设置申请进件人-提交这笔贷款的人
     *
     * @param loanUser 要设置的申请进件人-提交这笔贷款的人
     */
    public void setLoanUser(Long loanUser){
        this.loanUser = loanUser;
    }

    /**
    * 获取期望放款日期
    *
    * @return 期望放款日期
    */
    public Date getExpectTime(){
        return expectTime;
    }

    /**
     * 设置期望放款日期
     *
     * @param expectTime 要设置的期望放款日期
     */
    public void setExpectTime(Date expectTime){
        this.expectTime = expectTime;
    }


    public byte getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(byte repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
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
    * 获取申请进件人所属机构
    *
    * @return 申请进件人所属机构
    */
    public String getLoanOffice(){
        return loanOffice;
    }

    /**
     * 设置申请进件人所属机构
     *
     * @param loanOffice 要设置的申请进件人所属机构
     */
    public void setLoanOffice(String loanOffice){
        this.loanOffice = loanOffice;
    }

    /**
    * 获取借款需求表ID
    *
    * @return 借款需求表ID
    */
    public Long getRequirementId(){
        return requirementId;
    }

    /**
     * 设置借款需求表ID
     *
     * @param requirementId 要设置的借款需求表ID
     */
    public void setRequirementId(Long requirementId){
        this.requirementId = requirementId;
    }

	public BigDecimal getSingleRate() {
		return singleRate;
	}

	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}