package com.company.modules.advance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 垫资申请实体
 * @author gaoshan
 * @version 1.0
 * @since 2016-09-14 11:54:13
 */
public class HousAdvanceApply implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 创建时间
    */
    private Date createTime;
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
     * 垫资分支流程ID
     */
     private String newProcessInstanceId;
    /**
    * 项目ID
    */
    private Long projectId;
    /**
    * 垫资申请操作员
    */
    private String advanceApplyOperator;
    /**
    * 申请垫资时间
    */
    private Date advanceApplyTime;
    /**
    * 申请垫资金额
    */
    private BigDecimal advanceApplyAmount;
    /**
    * 垫资费
    */
    private BigDecimal advanceAmount;
    /**
    * 垫资费率
    */
    private BigDecimal advanceRateAmount;
	/**
	 * 开户人姓名
	 */
	private String accountHolder;
	 /**
	  * 银行卡号
	  */
	private String bankCard;
	  /**
	   * 开户行
	   */
	private String openingBank;
    
	/**
	 * 状态
	 */
	private String status;

	/**
	 * 已收取的垫资费
	 */
	private BigDecimal chargedAmount;
	
	/**
	 * 垫资期限
	 */
	private Long timeLimit;
	
	/**
	 * 垫资费收取方式
	 */
	private String chargeWay;
	
	/**
	 * 房屋现状
	 */
	private String houseSituation;
	
	/**
	 * 有无查封
	 */
	private String isClosedDown;
	
	/**
	 * 有无异常
	 */
	private String isAbnormal;
	
	
	/**
	 * 申请备注
	 */
	private String applyRemark;
	
	/**
	 * 建委调档备注
	 */
	private String transferFileRemark;
	
	/**
	 * 垫资状态(1.垫资放款 2.垫资收回)
	 */
	private String advanceStatus;
	
	/**
	 * 垫资收回时间(财务放款时间)
	 */
	private String takeBackTime;
	
	/**
	 * 垫资收回操作人(放款财务姓名)
	 */
	private String takeBackOperator;
//	
//	/**
//	 * 客服确认备注
//	 */
//	private String cusServiceRemark;
//	
//	/**
//	 * 风控总监确认备注
//	 */
//	private String riskControlRemark;
//	
//	/**
//	 * 垫资操作备注
//	 */
//	private String disposeRemark;
//	
	
	
	public static final String USERTASK_ADVANCE_APPLY = "usertask-advance-apply";
	public static final String USERTASK_LOANING_CONFIRM_WARRANTMANAGER = "usertask-loaning-confirm-warrantManager";
	public static final String USERTASK_LOANING_NOTARIZE = "usertask-loaning-notarize";
	public static final String USERTASK_RISKCONTROLLMANAGER_ASSIGNED = "usertask-riskControllManager-assigned";
	public static final String USERTASK_TRANSFER_FILES = "usertask-transfer-files";
	public static final String USERTASK_LOANING_CONFIRM_BUSINESSSTAFF = "usertask-loaning-confirm-businessStaff";
	public static final String USERTASK_LOANING = "usertask-loaning";
	public static final String USERTASK_WARRANTMANAGER_ASSIGNED = "usertask-warrantManager-assigned";
	
	public static final String ADVANCE_STATUS_LOAN = "1";
	
	
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
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
    * 获取流程ID
    *
    * @return 流程ID
    */
    public Long getProjectId(){
        return projectId;
    }
    
    /**
     * 设置流程ID
     * 
     * @param projectId 要设置的流程ID
     */
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }

    /**
    * 获取垫资申请操作员
    *
    * @return 垫资申请操作员
    */
    public String getAdvanceApplyOperator(){
        return advanceApplyOperator;
    }
    
    /**
     * 设置垫资申请操作员
     * 
     * @param advanceApplyOperator 要设置的垫资申请操作员
     */
    public void setAdvanceApplyOperator(String advanceApplyOperator){
        this.advanceApplyOperator = advanceApplyOperator;
    }

    /**
    * 获取申请垫资时间
    *
    * @return 申请垫资时间
    */
    public Date getAdvanceApplyTime(){
        return advanceApplyTime;
    }
    
    /**
     * 设置申请垫资时间
     * 
     * @param advanceApplyTime 要设置的申请垫资时间
     */
    public void setAdvanceApplyTime(Date advanceApplyTime){
        this.advanceApplyTime = advanceApplyTime;
    }

    /**
    * 获取申请垫资金额
    *
    * @return 申请垫资金额
    */
    public BigDecimal getAdvanceApplyAmount(){
        return advanceApplyAmount;
    }
    
    /**
     * 设置申请垫资金额
     * 
     * @param advanceApplyAmount 要设置的申请垫资金额
     */
    public void setAdvanceApplyAmount(BigDecimal advanceApplyAmount){
        this.advanceApplyAmount = advanceApplyAmount;
    }

    /**
    * 获取垫资费
    *
    * @return 垫资费
    */
    public BigDecimal getAdvanceAmount(){
        return advanceAmount;
    }
    
    /**
     * 设置垫资费
     * 
     * @param advanceAmount 要设置的垫资费
     */
    public void setAdvanceAmount(BigDecimal advanceAmount){
        this.advanceAmount = advanceAmount;
    }

    /**
    * 获取垫资费率
    *
    * @return 垫资费率
    */
    public BigDecimal getAdvanceRateAmount(){
        return advanceRateAmount;
    }
    
    /**
     * 设置垫资费率
     * 
     * @param advanceRateAmount 要设置的垫资费率
     */
    public void setAdvanceRateAmount(BigDecimal advanceRateAmount){
        this.advanceRateAmount = advanceRateAmount;
    }

	public String getNewProcessInstanceId() {
		return newProcessInstanceId;
	}

	public void setNewProcessInstanceId(String newProcessInstanceId) {
		this.newProcessInstanceId = newProcessInstanceId;
	}

	public Long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getChargeWay() {
		return chargeWay;
	}

	public void setChargeWay(String chargeWay) {
		this.chargeWay = chargeWay;
	}

	public BigDecimal getChargedAmount() {
		return chargedAmount;
	}

	public void setChargedAmount(BigDecimal chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	public String getHouseSituation() {
		return houseSituation;
	}

	public void setHouseSituation(String houseSituation) {
		this.houseSituation = houseSituation;
	}

	public String getIsClosedDown() {
		return isClosedDown;
	}

	public void setIsClosedDown(String isClosedDown) {
		this.isClosedDown = isClosedDown;
	}

	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}

	public String getTransferFileRemark() {
		return transferFileRemark;
	}

	public void setTransferFileRemark(String transferFileRemark) {
		this.transferFileRemark = transferFileRemark;
	}

	public String getAdvanceStatus() {
		return advanceStatus;
	}

	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	public String getTakeBackTime() {
		return takeBackTime;
	}

	public void setTakeBackTime(String takeBackTime) {
		this.takeBackTime = takeBackTime;
	}

	public String getTakeBackOperator() {
		return takeBackOperator;
	}

	public void setTakeBackOperator(String takeBackOperator) {
		this.takeBackOperator = takeBackOperator;
	}
	
}
