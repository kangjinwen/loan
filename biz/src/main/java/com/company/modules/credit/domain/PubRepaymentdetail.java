package com.company.modules.credit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款明细表实体
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
public class PubRepaymentdetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 还款明细表ID
     */
    private Long id;
    /**
     * 创建者
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
     * 0正常1删除
     */
    private Byte isDelete;
    /**
     * 流程ID
     */
    private String processInstanceId;
    /**
     * 还款日期
     */
    private Date repaymentTime;
    /**
     * 实际还款日期
     */
    private Date realpaymentTime;
    /**
     * 还款期次
     */
    private Integer period;
    /**
     * 
     */
    private BigDecimal account;
    /**
     * 实际还款金额
     */
    private BigDecimal realAccount;
    /**
     * 违约金
     */
    private BigDecimal penalty;
    /**
     * 罚息
     */
    private BigDecimal defaultInterest;
    /**
     * 还款方式
     */
    private Byte repaymentType;
    /**
     * 1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中；
     */
    private Byte repaymentStaus;
    /**
     * 是否还清 0未还清；1已还清
     */
    private Byte isPayoff;
    /**
     * 本金
     */
    private BigDecimal capital;
    /**
     * 利息
     */
    private BigDecimal interest;
    /**
     * 客户ID
     */
    private Long customerId;
    
    private Long borrowId;//借款需求表ID
    
    private BigDecimal reductionPenalty; 	//罚息减免金额
    private BigDecimal breachContract;	//违约金减免金额
    private BigDecimal allBreaks;		//总减免
    
    private 	BigDecimal violationsPenalty;         //本期违规违约金  
    private 	BigDecimal violationsTruckFee;        //本期违规拖车费  
    private 	BigDecimal violationsTravelFee;       //本期违规差旅费  
    private 	BigDecimal violationsOtherFee;        //本期违规其它费用
    private 	BigDecimal violationsTotalAmount;     //本期违规处罚总额

    private     BigDecimal parkingFee;//停车费
    private     BigDecimal gpsUsingFee;//gps使用费
    private     BigDecimal otherFee;//平台服务费
    private     Byte cancelOverdue;//取消预期 0-不取消  1-取消
    
    private BigDecimal repaymentPlatformAmount;//已还本期平台服务费
    private BigDecimal repaymentGPSuseAmount;//已还本期gps使用费
    private BigDecimal repaymentCapitalAmount;//已还本期本金
    private BigDecimal repaymentInterestAmount;//已还本期利息
    private BigDecimal customerBalance;//本期账户余额
    private BigDecimal needrepayCapital;//本期剩余待还本金
    private BigDecimal repaymentParkingAmount;//已还本期停车费
    
    
    
    
	public BigDecimal getRepaymentParkingAmount() {
        return repaymentParkingAmount;
    }

    public void setRepaymentParkingAmount(BigDecimal repaymentParkingAmount) {
        this.repaymentParkingAmount = repaymentParkingAmount;
    }

    public BigDecimal getRepaymentPlatformAmount() {
        return repaymentPlatformAmount;
    }

    public void setRepaymentPlatformAmount(BigDecimal repaymentPlatformAmount) {
        this.repaymentPlatformAmount = repaymentPlatformAmount;
    }

    public BigDecimal getRepaymentGPSuseAmount() {
        return repaymentGPSuseAmount;
    }

    public void setRepaymentGPSuseAmount(BigDecimal repaymentGPSuseAmount) {
        this.repaymentGPSuseAmount = repaymentGPSuseAmount;
    }

    public BigDecimal getRepaymentCapitalAmount() {
        return repaymentCapitalAmount;
    }

    public void setRepaymentCapitalAmount(BigDecimal repaymentCapitalAmount) {
        this.repaymentCapitalAmount = repaymentCapitalAmount;
    }

    public BigDecimal getRepaymentInterestAmount() {
        return repaymentInterestAmount;
    }

    public void setRepaymentInterestAmount(BigDecimal repaymentInterestAmount) {
        this.repaymentInterestAmount = repaymentInterestAmount;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
    }

    public BigDecimal getNeedrepayCapital() {
        return needrepayCapital;
    }

    public void setNeedrepayCapital(BigDecimal needrepayCapital) {
        this.needrepayCapital = needrepayCapital;
    }

    public BigDecimal getReductionPenalty() {
		return reductionPenalty;
	}

	public void setReductionPenalty(BigDecimal reductionPenalty) {
		this.reductionPenalty = reductionPenalty;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
     * 获取还款明细表ID
     * 
     * @return 还款明细表ID
     */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置还款明细表ID
     * 
     * @param id 要设置的还款明细表ID
     */
    public void setId(Long id){
        this.id = id;
    }
    /**
     * 获取创建者
     * 
     * @return 创建者
     */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 设置创建者
     * 
     * @param creator 要设置的创建者
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
     * 获取0正常1删除
     * 
     * @return 0正常1删除
     */
    public Byte getIsDelete(){
        return isDelete;
    }
    
    /**
     * 设置0正常1删除
     * 
     * @param isDelete 要设置的0正常1删除
     */
    public void setIsDelete(Byte isDelete){
        this.isDelete = isDelete;
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
     * 获取还款日期
     * 
     * @return 还款日期
     */
    public Date getRepaymentTime(){
        return repaymentTime;
    }
    
    /**
     * 设置还款日期
     * 
     * @param repaymentTime 要设置的还款日期
     */
    public void setRepaymentTime(Date repaymentTime){
        this.repaymentTime = repaymentTime;
    }
    /**
     * 获取实际还款日期
     * 
     * @return 实际还款日期
     */
    public Date getRealpaymentTime(){
        return realpaymentTime;
    }
    
    /**
     * 设置实际还款日期
     * 
     * @param realpaymentTime 要设置的实际还款日期
     */
    public void setRealpaymentTime(Date realpaymentTime){
        this.realpaymentTime = realpaymentTime;
    }
    /**
     * 获取还款期次
     * 
     * @return 还款期次
     */
    public Integer getPeriod(){
        return period;
    }
    
    /**
     * 设置还款期次
     * 
     * @param period 要设置的还款期次
     */
    public void setPeriod(Integer period){
        this.period = period;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public BigDecimal getAccount(){
        return account;
    }
    
    /**
     * 设置
     * 
     * @param account 要设置的
     */
    public void setAccount(BigDecimal account){
        this.account = account;
    }
    /**
     * 获取实际还款金额
     * 
     * @return 实际还款金额
     */
    public BigDecimal getRealAccount(){
        return realAccount;
    }
    
    /**
     * 设置实际还款金额
     * 
     * @param realAccount 要设置的实际还款金额
     */
    public void setRealAccount(BigDecimal realAccount){
        this.realAccount = realAccount;
    }
    /**
     * 获取违约金
     * 
     * @return 违约金
     */
    public BigDecimal getPenalty(){
        return penalty;
    }
    
    /**
     * 设置违约金
     * 
     * @param penalty 要设置的违约金
     */
    public void setPenalty(BigDecimal penalty){
        this.penalty = penalty;
    }
    /**
     * 获取罚息
     * 
     * @return 罚息
     */
    public BigDecimal getDefaultInterest(){
        return defaultInterest;
    }
    
    /**
     * 设置罚息
     * 
     * @param defaultInterest 要设置的罚息
     */
    public void setDefaultInterest(BigDecimal defaultInterest){
        this.defaultInterest = defaultInterest;
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
     * 获取1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中；
     * 
     * @return 1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中；
     */
    public Byte getRepaymentStaus(){
        return repaymentStaus;
    }
    
    /**
     * 设置1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中；
     * 
     * @param repaymentStaus 要设置的1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中；
     */
    public void setRepaymentStaus(Byte repaymentStaus){
        this.repaymentStaus = repaymentStaus;
    }
    /**
     * 获取是否还清 0未还清；1已还清
     * 
     * @return 是否还清 0未还清；1已还清
     */
    public Byte getIsPayoff(){
        return isPayoff;
    }
    
    /**
     * 设置是否还清 0未还清；1已还清
     * 
     * @param isPayoff 要设置的是否还清 0未还清；1已还清
     */
    public void setIsPayoff(Byte isPayoff){
        this.isPayoff = isPayoff;
    }

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}

	public BigDecimal getViolationsPenalty() {
		return violationsPenalty;
	}

	public void setViolationsPenalty(BigDecimal violationsPenalty) {
		this.violationsPenalty = violationsPenalty;
	}

	public BigDecimal getViolationsTruckFee() {
		return violationsTruckFee;
	}

	public void setViolationsTruckFee(BigDecimal violationsTruckFee) {
		this.violationsTruckFee = violationsTruckFee;
	}

	public BigDecimal getViolationsTravelFee() {
		return violationsTravelFee;
	}

	public void setViolationsTravelFee(BigDecimal violationsTravelFee) {
		this.violationsTravelFee = violationsTravelFee;
	}

	public BigDecimal getViolationsOtherFee() {
		return violationsOtherFee;
	}

	public void setViolationsOtherFee(BigDecimal violationsOtherFee) {
		this.violationsOtherFee = violationsOtherFee;
	}

	public BigDecimal getViolationsTotalAmount() {
		return violationsTotalAmount;
	}

	public void setViolationsTotalAmount(BigDecimal violationsTotalAmount) {
		this.violationsTotalAmount = violationsTotalAmount;
	}

    public BigDecimal getBreachContract() {
        return breachContract;
    }

    public void setBreachContract(BigDecimal breachContract) {
        this.breachContract = breachContract;
    }

    public BigDecimal getAllBreaks() {
        return allBreaks;
    }

    public void setAllBreaks(BigDecimal allBreaks) {
        this.allBreaks = allBreaks;
    }

    public BigDecimal getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(BigDecimal parkingFee) {
        this.parkingFee = parkingFee;
    }

    public BigDecimal getGpsUsingFee() {
        return gpsUsingFee;
    }

    public void setGpsUsingFee(BigDecimal gpsUsingFee) {
        this.gpsUsingFee = gpsUsingFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public Byte getCancelOverdue() {
        return cancelOverdue;
    }

    public void setCancelOverdue(Byte cancelOverdue) {
        this.cancelOverdue = cancelOverdue;
    }
	
	
	
    
}


