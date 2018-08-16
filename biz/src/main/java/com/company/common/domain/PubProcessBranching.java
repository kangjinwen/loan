package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷后信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-10 05:46:59
 */
public class PubProcessBranching implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 分支流程ID
    */
    private String branchingProcessId;
    /**
    * 分支流程类型 提前1 减免2 强制3 处置5 展期6(分支流程申请提交后即初始化该字段 和

processing_opinion配合使用)
    */
    private Byte branchingProcessType;
    /**
    * 审批处理意见(提前还款、减免、强制、处置等):未审批noprocess,pass 通过 reject驳回，disposalRegisterDone登

记完成
    */
    private String processingOpinion;
    /**
    * 任务编号
    */
    private String taskId;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 违约情况:1-GPS拆除 2-驶出规定范围 3-其它
    */
    private BigDecimal defaultSituation;
    /**
    * 贷后变更备注(减免原因、GPS拆除)
    */
    private String remark1;
    /**
    * 审批备注
    */
    private String remark2;
    /**
    * 预约还款时间
    */
    private Date makeRepaymentTime;
    /**
    * 还款处理（0正常 1提前 2减免 3强制 5处置）
    */
    private Byte repaymentProcess;
    /**
    * 处理期次
    */
    private Byte processPeriod;
    /**
    * 其他金额
    */
    private BigDecimal otherAmount;
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
    * 项目ID
    */
    private Long projectId;
    /**
    * 
    */
    private Byte isActive;
    /**
    * 流程状态
    */
    private String processStatus;
    /**
    * 未还本金
    */
    private BigDecimal capitalSum;
    /**
    * 未还利息
    */
    private BigDecimal interestSum;
    /**
    * 违约金
    */
    private BigDecimal penaltySum;
    /**
    * 罚息
    */
    private BigDecimal defaultinterestSum;
    /**
    * 未还停车费
    */
    private BigDecimal parkingfeeSum;
    /**
    * 未还gps使用费
    */
    private BigDecimal gpsusingfeeSum;
    /**
    * 未缴平台服务费
    */
    private BigDecimal platformfeeSum;
    /**
    * 违规罚款总额
    */
    private BigDecimal violationsTotalAmount;
    /**
    * 起始未还款期次
    */
    private Byte period;
    /**
    * 展期提前还款金额
    */
    private BigDecimal repayCapitalAmount;
    /**
    * 展期期数
    */
    private Integer extensionCount;
    /**
     * 展期费收取财务专员
     */
    private String financeSpecialist;
    
    private BigDecimal extensionAmount;//展期金额
    private BigDecimal extensionFee;//展期费
    private BigDecimal extensionRate;//展期利率
    private BigDecimal extensionReturnfeeRate;//展期返佣点费
    private BigDecimal actualExtensionFee;//实收展期费
    private Date deductTime;
    
    
    public Date getDeductTime() {
		return deductTime;
	}

	public void setDeductTime(Date deductTime) {
		this.deductTime = deductTime;
	}

	/**
     * 展期费收取形式
     */
    private String collectionForm;   
    
    
    public BigDecimal getActualExtensionFee() {
		return actualExtensionFee;
	}

	public void setActualExtensionFee(BigDecimal actualExtensionFee) {
		this.actualExtensionFee = actualExtensionFee;
	}

	public String getCollectionForm() {
		return collectionForm;
	}

	public void setCollectionForm(String collectionForm) {
		this.collectionForm = collectionForm;
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
    
    
    public String getFinanceSpecialist() {
		return financeSpecialist;
	}

	public void setFinanceSpecialist(String financeSpecialist) {
		this.financeSpecialist = financeSpecialist;
	}

	/**
    * 获取分支流程ID
    *
    * @return 分支流程ID
    */
    public String getBranchingProcessId(){
        return branchingProcessId;
    }
    
    /**
     * 设置分支流程ID
     * 
     * @param branchingProcessId 要设置的分支流程ID
     */
    public void setBranchingProcessId(String branchingProcessId){
        this.branchingProcessId = branchingProcessId;
    }

    /**
    * 获取分支流程类型 提前1 减免2 强制3 处置5 展期6(分支流程申请提交后即初始化该字段 和

processing_opinion配合使用)
    *
    * @return 分支流程类型 提前1 减免2 强制3 处置5 展期6(分支流程申请提交后即初始化该字段 和

processing_opinion配合使用)
    */
    public Byte getBranchingProcessType(){
        return branchingProcessType;
    }
    
    /**
     * 设置分支流程类型 提前1 减免2 强制3 处置5 展期6(分支流程申请提交后即初始化该字段 和

processing_opinion配合使用)
     * 
     * @param branchingProcessType 要设置的分支流程类型 提前1 减免2 强制3 处置5 展期6(分支流程申请提交后即初始化该字段 和

processing_opinion配合使用)
     */
    public void setBranchingProcessType(Byte branchingProcessType){
        this.branchingProcessType = branchingProcessType;
    }

    /**
    * 获取审批处理意见(提前还款、减免、强制、处置等):未审批noprocess,pass 通过 reject驳回，disposalRegisterDone登

记完成
    *
    * @return 审批处理意见(提前还款、减免、强制、处置等):未审批noprocess,pass 通过 reject驳回，disposalRegisterDone登

记完成
    */
    public String getProcessingOpinion(){
        return processingOpinion;
    }
    
    /**
     * 设置审批处理意见(提前还款、减免、强制、处置等):未审批noprocess,pass 通过 reject驳回，disposalRegisterDone登

记完成
     * 
     * @param processingOpinion 要设置的审批处理意见(提前还款、减免、强制、处置等):未审批noprocess,pass 通过 reject驳回，disposalRegisterDone登

记完成
     */
    public void setProcessingOpinion(String processingOpinion){
        this.processingOpinion = processingOpinion;
    }

    /**
    * 获取任务编号
    *
    * @return 任务编号
    */
    public String getTaskId(){
        return taskId;
    }
    
    /**
     * 设置任务编号
     * 
     * @param taskId 要设置的任务编号
     */
    public void setTaskId(String taskId){
        this.taskId = taskId;
    }


    public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
    * 获取违约情况:1-GPS拆除 2-驶出规定范围 3-其它
    *
    * @return 违约情况:1-GPS拆除 2-驶出规定范围 3-其它
    */
    public BigDecimal getDefaultSituation(){
        return defaultSituation;
    }
    
    /**
     * 设置违约情况:1-GPS拆除 2-驶出规定范围 3-其它
     * 
     * @param defaultSituation 要设置的违约情况:1-GPS拆除 2-驶出规定范围 3-其它
     */
    public void setDefaultSituation(BigDecimal defaultSituation){
        this.defaultSituation = defaultSituation;
    }

    /**
    * 获取贷后变更备注(减免原因、GPS拆除)
    *
    * @return 贷后变更备注(减免原因、GPS拆除)
    */
    public String getRemark1(){
        return remark1;
    }
    
    /**
     * 设置贷后变更备注(减免原因、GPS拆除)
     * 
     * @param remark1 要设置的贷后变更备注(减免原因、GPS拆除)
     */
    public void setRemark1(String remark1){
        this.remark1 = remark1;
    }

    /**
    * 获取审批备注
    *
    * @return 审批备注
    */
    public String getRemark2(){
        return remark2;
    }
    
    /**
     * 设置审批备注
     * 
     * @param remark2 要设置的审批备注
     */
    public void setRemark2(String remark2){
        this.remark2 = remark2;
    }

    /**
    * 获取预约还款时间
    *
    * @return 预约还款时间
    */
    public Date getMakeRepaymentTime(){
        return makeRepaymentTime;
    }
    
    /**
     * 设置预约还款时间
     * 
     * @param makeRepaymentTime 要设置的预约还款时间
     */
    public void setMakeRepaymentTime(Date makeRepaymentTime){
        this.makeRepaymentTime = makeRepaymentTime;
    }

    /**
    * 获取还款处理（0正常 1提前 2减免 3强制 5处置）
    *
    * @return 还款处理（0正常 1提前 2减免 3强制 5处置）
    */
    public Byte getRepaymentProcess(){
        return repaymentProcess;
    }
    
    /**
     * 设置还款处理（0正常 1提前 2减免 3强制 5处置）
     * 
     * @param repaymentProcess 要设置的还款处理（0正常 1提前 2减免 3强制 5处置）
     */
    public void setRepaymentProcess(Byte repaymentProcess){
        this.repaymentProcess = repaymentProcess;
    }

    /**
    * 获取处理期次
    *
    * @return 处理期次
    */
    public Byte getProcessPeriod(){
        return processPeriod;
    }
    
    /**
     * 设置处理期次
     * 
     * @param processPeriod 要设置的处理期次
     */
    public void setProcessPeriod(Byte processPeriod){
        this.processPeriod = processPeriod;
    }

    /**
    * 获取其他金额
    *
    * @return 其他金额
    */
    public BigDecimal getOtherAmount(){
        return otherAmount;
    }
    
    /**
     * 设置其他金额
     * 
     * @param otherAmount 要设置的其他金额
     */
    public void setOtherAmount(BigDecimal otherAmount){
        this.otherAmount = otherAmount;
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

    /**
    * 获取
    *
    * @return 
    */
    public Byte getIsActive(){
        return isActive;
    }
    
    /**
     * 设置
     * 
     * @param isActive 要设置的
     */
    public void setIsActive(Byte isActive){
        this.isActive = isActive;
    }

    /**
    * 获取流程状态
    *
    * @return 流程状态
    */
    public String getProcessStatus(){
        return processStatus;
    }
    
    /**
     * 设置流程状态
     * 
     * @param processStatus 要设置的流程状态
     */
    public void setProcessStatus(String processStatus){
        this.processStatus = processStatus;
    }

    /**
    * 获取未还本金
    *
    * @return 未还本金
    */
    public BigDecimal getCapitalSum(){
        return capitalSum;
    }
    
    /**
     * 设置未还本金
     * 
     * @param capitalSum 要设置的未还本金
     */
    public void setCapitalSum(BigDecimal capitalSum){
        this.capitalSum = capitalSum;
    }

    /**
    * 获取未还利息
    *
    * @return 未还利息
    */
    public BigDecimal getInterestSum(){
        return interestSum;
    }
    
    /**
     * 设置未还利息
     * 
     * @param interestSum 要设置的未还利息
     */
    public void setInterestSum(BigDecimal interestSum){
        this.interestSum = interestSum;
    }

    /**
    * 获取违约金
    *
    * @return 违约金
    */
    public BigDecimal getPenaltySum(){
        return penaltySum;
    }
    
    /**
     * 设置违约金
     * 
     * @param penaltySum 要设置的违约金
     */
    public void setPenaltySum(BigDecimal penaltySum){
        this.penaltySum = penaltySum;
    }

    /**
    * 获取罚息
    *
    * @return 罚息
    */
    public BigDecimal getDefaultinterestSum(){
        return defaultinterestSum;
    }
    
    /**
     * 设置罚息
     * 
     * @param defaultinterestSum 要设置的罚息
     */
    public void setDefaultinterestSum(BigDecimal defaultinterestSum){
        this.defaultinterestSum = defaultinterestSum;
    }

    /**
    * 获取未还停车费
    *
    * @return 未还停车费
    */
    public BigDecimal getParkingfeeSum(){
        return parkingfeeSum;
    }
    
    /**
     * 设置未还停车费
     * 
     * @param parkingfeeSum 要设置的未还停车费
     */
    public void setParkingfeeSum(BigDecimal parkingfeeSum){
        this.parkingfeeSum = parkingfeeSum;
    }

    /**
    * 获取未还gps使用费
    *
    * @return 未还gps使用费
    */
    public BigDecimal getGpsusingfeeSum(){
        return gpsusingfeeSum;
    }
    
    /**
     * 设置未还gps使用费
     * 
     * @param gpsusingfeeSum 要设置的未还gps使用费
     */
    public void setGpsusingfeeSum(BigDecimal gpsusingfeeSum){
        this.gpsusingfeeSum = gpsusingfeeSum;
    }

    /**
    * 获取未缴平台服务费
    *
    * @return 未缴平台服务费
    */
    public BigDecimal getPlatformfeeSum(){
        return platformfeeSum;
    }
    
    /**
     * 设置未缴平台服务费
     * 
     * @param platformfeeSum 要设置的未缴平台服务费
     */
    public void setPlatformfeeSum(BigDecimal platformfeeSum){
        this.platformfeeSum = platformfeeSum;
    }

    /**
    * 获取违规罚款总额
    *
    * @return 违规罚款总额
    */
    public BigDecimal getViolationsTotalAmount(){
        return violationsTotalAmount;
    }
    
    /**
     * 设置违规罚款总额
     * 
     * @param violationsTotalAmount 要设置的违规罚款总额
     */
    public void setViolationsTotalAmount(BigDecimal violationsTotalAmount){
        this.violationsTotalAmount = violationsTotalAmount;
    }

    public Byte getPeriod() {
		return period;
	}

	public void setPeriod(Byte period) {
		this.period = period;
	}

	/**
    * 获取展期提前还款金额
    *
    * @return 展期提前还款金额
    */
    public BigDecimal getRepayCapitalAmount(){
        return repayCapitalAmount;
    }
    
    /**
     * 设置展期提前还款金额
     * 
     * @param repayCapitalAmount 要设置的展期提前还款金额
     */
    public void setRepayCapitalAmount(BigDecimal repayCapitalAmount){
        this.repayCapitalAmount = repayCapitalAmount;
    }

    /**
    * 获取展期期数
    *
    * @return 展期期数
    */
    public Integer getExtensionCount(){
        return extensionCount;
    }
    
    /**
     * 设置展期期数
     * 
     * @param extensionCount 要设置的展期期数
     */
    public void setExtensionCount(Integer extensionCount){
        this.extensionCount = extensionCount;
    }

	public BigDecimal getExtensionAmount() {
		return extensionAmount;
	}

	public void setExtensionAmount(BigDecimal extensionAmount) {
		this.extensionAmount = extensionAmount;
	}

	public BigDecimal getExtensionFee() {
		return extensionFee;
	}

	public void setExtensionFee(BigDecimal extensionFee) {
		this.extensionFee = extensionFee;
	}

	public BigDecimal getExtensionRate() {
		return extensionRate;
	}

	public void setExtensionRate(BigDecimal extensionRate) {
		this.extensionRate = extensionRate;
	}

	public BigDecimal getExtensionReturnfeeRate() {
		return extensionReturnfeeRate;
	}

	public void setExtensionReturnfeeRate(BigDecimal extensionReturnfeeRate) {
		this.extensionReturnfeeRate = extensionReturnfeeRate;
	}
}