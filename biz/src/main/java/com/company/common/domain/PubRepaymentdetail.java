package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 放款实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-28 10:30:16
 */
public class PubRepaymentdetail implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 罚息
    */
    private BigDecimal defaultInterest;
    /**
     * 实际罚息
     */
     private BigDecimal realInterest;
    /**
    * 违约金
    */
    private BigDecimal penalty;
    /**
    * 实际还款金额
    */
    private BigDecimal realAccount;
    /**
    * 是否还清 0未还清；1已还清
    */
    private Byte isPayoff;
    /**
    * 客户ID
    */
    private Long customerId;
    /**
    * 本期应还款金额
    */
    private BigDecimal account;
    /**
    * 本金
    */
    private BigDecimal capital;
    /**
    * 利息
    */
    private BigDecimal interest;
    /**
    * gps使用费
    */
    private BigDecimal gpsUseFee;
    /**
    * 其他费用
    */
    private BigDecimal otherFee;
    /**
    * 停车费
    */
    private BigDecimal parkingFee;
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
    * 还款期次
    */
    private Integer period;
    /**
    * 实际还款日期
    */
    private Date realpaymentTime;
    /**
    * 还款方式
    */
    private Byte repaymentType;
    /**
    * 还款日期
    */
    private Date repaymentTime;
    /**
    * 1正常还款；2逾期未还款；3逾期还款；4提前还款；5押品拍卖；0还款中；
    */
    private Byte repaymentStaus;
    /**
    * 流程ID
    */
    private String processinstanceid;
    /**
    * 借款表ID
    */
    private Long borrowId;
    /**
    * 本期违规违约金
    */
    private BigDecimal violationsPenalty;
    /**
    * 本期违规拖车费
    */
    private BigDecimal violationsTruckFee;
    /**
    * 本期违规差旅费
    */
    private BigDecimal violationsTravelFee;
    /**
    * 本期违规其它费用
    */
    private BigDecimal violationsOtherFee;
    /**
    * 本期违规处罚总额
    */
    private BigDecimal violationsTotalAmount;
    /**
    * 罚息减免金额
    */
    private BigDecimal reductionPenalty;
    /**
    * 违约金减免
    */
    private BigDecimal breachContract;
    /**
    * 总减免金额
    */
    private BigDecimal allBreaks;
    /**
    * 取消逾期
    */
    private Byte cancelOverdue;
    /**
    * 已还本期平台服务费
    */
    private BigDecimal repaymentPlatformAmount;
    /**
    * 已还本期停车费
    */
    private BigDecimal repaymentParkingAmount;
    /**
    * 已还本期gps使用费
    */
    private BigDecimal repaymentGpsuseAmount;
    /**
    * 已还本期本金
    */
    private BigDecimal repaymentCapitalAmount;
    /**
    * 已还本期利息
    */
    private BigDecimal repaymentInterestAmount;
    /**
    * 本期账户余额
    */
    private BigDecimal customerBalance;
    /**
    * 本期剩余待还本金
    */
    private BigDecimal needrepayCapital;
    
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
     * 获取实际罚息
     * 
     * @param realInterest 要设置的罚息
     */
    public BigDecimal getRealInterest() {
		return realInterest;
	}
    /**
     * 设置实际罚息
     * 
     * @param realInterest 要设置的罚息
     */
	public void setRealInterest(BigDecimal realInterest) {
		this.realInterest = realInterest;
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

    /**
    * 获取客户ID
    *
    * @return 客户ID
    */
    public Long getCustomerId(){
        return customerId;
    }
    
    /**
     * 设置客户ID
     * 
     * @param customerId 要设置的客户ID
     */
    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }

    /**
    * 获取本期应还款金额
    *
    * @return 本期应还款金额
    */
    public BigDecimal getAccount(){
        return account;
    }
    
    /**
     * 设置本期应还款金额
     * 
     * @param account 要设置的本期应还款金额
     */
    public void setAccount(BigDecimal account){
        this.account = account;
    }

    /**
    * 获取本金
    *
    * @return 本金
    */
    public BigDecimal getCapital(){
        return capital;
    }
    
    /**
     * 设置本金
     * 
     * @param capital 要设置的本金
     */
    public void setCapital(BigDecimal capital){
        this.capital = capital;
    }

    /**
    * 获取利息
    *
    * @return 利息
    */
    public BigDecimal getInterest(){
        return interest;
    }
    
    /**
     * 设置利息
     * 
     * @param interest 要设置的利息
     */
    public void setInterest(BigDecimal interest){
        this.interest = interest;
    }

    /**
    * 获取gps使用费
    *
    * @return gps使用费
    */
    public BigDecimal getGpsUseFee(){
        return gpsUseFee;
    }
    
    /**
     * 设置gps使用费
     * 
     * @param gpsUseFee 要设置的gps使用费
     */
    public void setGpsUseFee(BigDecimal gpsUseFee){
        this.gpsUseFee = gpsUseFee;
    }

    /**
    * 获取其他费用
    *
    * @return 其他费用
    */
    public BigDecimal getOtherFee(){
        return otherFee;
    }
    
    /**
     * 设置其他费用
     * 
     * @param otherFee 要设置的其他费用
     */
    public void setOtherFee(BigDecimal otherFee){
        this.otherFee = otherFee;
    }

    /**
    * 获取停车费
    *
    * @return 停车费
    */
    public BigDecimal getParkingFee(){
        return parkingFee;
    }
    
    /**
     * 设置停车费
     * 
     * @param parkingFee 要设置的停车费
     */
    public void setParkingFee(BigDecimal parkingFee){
        this.parkingFee = parkingFee;
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
    * 获取1正常还款；2逾期未还款；3逾期还款；4提前还款；5押品拍卖；0还款中；
    *
    * @return 1正常还款；2逾期未还款；3逾期还款；4提前还款；5押品拍卖；0还款中；
    */
    public Byte getRepaymentStaus(){
        return repaymentStaus;
    }
    
    /**
     * 设置1正常还款；2逾期未还款；3逾期还款；4提前还款；5押品拍卖；0还款中；
     * 
     * @param repaymentStaus 要设置的1正常还款；2逾期未还款；3逾期还款；4提前还款；5押品拍卖；0还款中；
     */
    public void setRepaymentStaus(Byte repaymentStaus){
        this.repaymentStaus = repaymentStaus;
    }

    /**
    * 获取流程ID
    *
    * @return 流程ID
    */
    public String getProcessinstanceid(){
        return processinstanceid;
    }
    
    /**
     * 设置流程ID
     * 
     * @param processinstanceid 要设置的流程ID
     */
    public void setProcessinstanceid(String processinstanceid){
        this.processinstanceid = processinstanceid;
    }

    /**
    * 获取借款表ID
    *
    * @return 借款表ID
    */
    public Long getBorrowId(){
        return borrowId;
    }
    
    /**
     * 设置借款表ID
     * 
     * @param borrowId 要设置的借款表ID
     */
    public void setBorrowId(Long borrowId){
        this.borrowId = borrowId;
    }

    /**
    * 获取本期违规违约金
    *
    * @return 本期违规违约金
    */
    public BigDecimal getViolationsPenalty(){
        return violationsPenalty;
    }
    
    /**
     * 设置本期违规违约金
     * 
     * @param violationsPenalty 要设置的本期违规违约金
     */
    public void setViolationsPenalty(BigDecimal violationsPenalty){
        this.violationsPenalty = violationsPenalty;
    }

    /**
    * 获取本期违规拖车费
    *
    * @return 本期违规拖车费
    */
    public BigDecimal getViolationsTruckFee(){
        return violationsTruckFee;
    }
    
    /**
     * 设置本期违规拖车费
     * 
     * @param violationsTruckFee 要设置的本期违规拖车费
     */
    public void setViolationsTruckFee(BigDecimal violationsTruckFee){
        this.violationsTruckFee = violationsTruckFee;
    }

    /**
    * 获取本期违规差旅费
    *
    * @return 本期违规差旅费
    */
    public BigDecimal getViolationsTravelFee(){
        return violationsTravelFee;
    }
    
    /**
     * 设置本期违规差旅费
     * 
     * @param violationsTravelFee 要设置的本期违规差旅费
     */
    public void setViolationsTravelFee(BigDecimal violationsTravelFee){
        this.violationsTravelFee = violationsTravelFee;
    }

    /**
    * 获取本期违规其它费用
    *
    * @return 本期违规其它费用
    */
    public BigDecimal getViolationsOtherFee(){
        return violationsOtherFee;
    }
    
    /**
     * 设置本期违规其它费用
     * 
     * @param violationsOtherFee 要设置的本期违规其它费用
     */
    public void setViolationsOtherFee(BigDecimal violationsOtherFee){
        this.violationsOtherFee = violationsOtherFee;
    }

    /**
    * 获取本期违规处罚总额
    *
    * @return 本期违规处罚总额
    */
    public BigDecimal getViolationsTotalAmount(){
        return violationsTotalAmount;
    }
    
    /**
     * 设置本期违规处罚总额
     * 
     * @param violationsTotalAmount 要设置的本期违规处罚总额
     */
    public void setViolationsTotalAmount(BigDecimal violationsTotalAmount){
        this.violationsTotalAmount = violationsTotalAmount;
    }

    /**
    * 获取罚息减免金额
    *
    * @return 罚息减免金额
    */
    public BigDecimal getReductionPenalty(){
        return reductionPenalty;
    }
    
    /**
     * 设置罚息减免金额
     * 
     * @param reductionPenalty 要设置的罚息减免金额
     */
    public void setReductionPenalty(BigDecimal reductionPenalty){
        this.reductionPenalty = reductionPenalty;
    }

    /**
    * 获取违约金减免
    *
    * @return 违约金减免
    */
    public BigDecimal getBreachContract(){
        return breachContract;
    }
    
    /**
     * 设置违约金减免
     * 
     * @param breachContract 要设置的违约金减免
     */
    public void setBreachContract(BigDecimal breachContract){
        this.breachContract = breachContract;
    }

    /**
    * 获取总减免金额
    *
    * @return 总减免金额
    */
    public BigDecimal getAllBreaks(){
        return allBreaks;
    }
    
    /**
     * 设置总减免金额
     * 
     * @param allBreaks 要设置的总减免金额
     */
    public void setAllBreaks(BigDecimal allBreaks){
        this.allBreaks = allBreaks;
    }

    /**
    * 获取取消逾期
    *
    * @return 取消逾期
    */
    public Byte getCancelOverdue(){
        return cancelOverdue;
    }
    
    /**
     * 设置取消逾期
     * 
     * @param cancelOverdue 要设置的取消逾期
     */
    public void setCancelOverdue(Byte cancelOverdue){
        this.cancelOverdue = cancelOverdue;
    }

    /**
    * 获取已还本期平台服务费
    *
    * @return 已还本期平台服务费
    */
    public BigDecimal getRepaymentPlatformAmount(){
        return repaymentPlatformAmount;
    }
    
    /**
     * 设置已还本期平台服务费
     * 
     * @param repaymentPlatformAmount 要设置的已还本期平台服务费
     */
    public void setRepaymentPlatformAmount(BigDecimal repaymentPlatformAmount){
        this.repaymentPlatformAmount = repaymentPlatformAmount;
    }

    /**
    * 获取已还本期停车费
    *
    * @return 已还本期停车费
    */
    public BigDecimal getRepaymentParkingAmount(){
        return repaymentParkingAmount;
    }
    
    /**
     * 设置已还本期停车费
     * 
     * @param repaymentParkingAmount 要设置的已还本期停车费
     */
    public void setRepaymentParkingAmount(BigDecimal repaymentParkingAmount){
        this.repaymentParkingAmount = repaymentParkingAmount;
    }

    /**
    * 获取已还本期gps使用费
    *
    * @return 已还本期gps使用费
    */
    public BigDecimal getRepaymentGpsuseAmount(){
        return repaymentGpsuseAmount;
    }
    
    /**
     * 设置已还本期gps使用费
     * 
     * @param repaymentGpsuseAmount 要设置的已还本期gps使用费
     */
    public void setRepaymentGpsuseAmount(BigDecimal repaymentGpsuseAmount){
        this.repaymentGpsuseAmount = repaymentGpsuseAmount;
    }

    /**
    * 获取已还本期本金
    *
    * @return 已还本期本金
    */
    public BigDecimal getRepaymentCapitalAmount(){
        return repaymentCapitalAmount;
    }
    
    /**
     * 设置已还本期本金
     * 
     * @param repaymentCapitalAmount 要设置的已还本期本金
     */
    public void setRepaymentCapitalAmount(BigDecimal repaymentCapitalAmount){
        this.repaymentCapitalAmount = repaymentCapitalAmount;
    }

    /**
    * 获取已还本期利息
    *
    * @return 已还本期利息
    */
    public BigDecimal getRepaymentInterestAmount(){
        return repaymentInterestAmount;
    }
    
    /**
     * 设置已还本期利息
     * 
     * @param repaymentInterestAmount 要设置的已还本期利息
     */
    public void setRepaymentInterestAmount(BigDecimal repaymentInterestAmount){
        this.repaymentInterestAmount = repaymentInterestAmount;
    }

    /**
    * 获取本期账户余额
    *
    * @return 本期账户余额
    */
    public BigDecimal getCustomerBalance(){
        return customerBalance;
    }
    
    /**
     * 设置本期账户余额
     * 
     * @param customerBalance 要设置的本期账户余额
     */
    public void setCustomerBalance(BigDecimal customerBalance){
        this.customerBalance = customerBalance;
    }

    /**
    * 获取本期剩余待还本金
    *
    * @return 本期剩余待还本金
    */
    public BigDecimal getNeedrepayCapital(){
        return needrepayCapital;
    }
    
    /**
     * 设置本期剩余待还本金
     * 
     * @param needrepayCapital 要设置的本期剩余待还本金
     */
    public void setNeedrepayCapital(BigDecimal needrepayCapital){
        this.needrepayCapital = needrepayCapital;
    }

}