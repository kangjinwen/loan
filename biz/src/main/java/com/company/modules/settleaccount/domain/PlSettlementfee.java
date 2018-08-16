package com.company.modules.settleaccount.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算费用表实体
 * @author wgc
 * @version 1.0
 * @since 2015-06-30
 */
public class PlSettlementfee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private Long id;
    /**
     * 
     */
    private String processInstanceId;
    /**
     * 
     */
    private Long projectId;
    /**
     * 本金
     */
    private BigDecimal capital;
    /**
     * 是否还清 0-未还清 1-已还清
     */
    private Byte isPayoff;
    /**
     * 车辆处置金额
     */
    private BigDecimal disposalAmount;
    /**
     * 车辆处置余额
     */
    private BigDecimal disposalBalanceAmount;
    /**
     * 退还处置余额
     */
    private BigDecimal disposalBackAmount;
    /**
     * 借款保证金
     */
    private BigDecimal marginFee;
    /**
     * 是否违约
     */
    private Byte isBreachOfContract;
    /**
     * 退还保证金
     */
    private BigDecimal backMarginFee;
    /**
     * 违章处理费
     */
    private BigDecimal illegalDisposalFee;
    /**
     * 是否违章
     */
    private Byte isIllegal;
    /**
     * 是否违章
     */
    private BigDecimal backIllegalDisposalFee;
    /**
     * gps安装费
     */
    private BigDecimal gpsInstallFee;
    /**
     * 是否拆除
     */
    private Byte isUninstall;
    /**
     * 退还gps安装费
     */
    private BigDecimal backGpsInstallFee;
    /**
     * 退还金额总计
     */
    private BigDecimal totalBackAmount;
    /**
     * 付款行名称(提出行)
     */
    private String presbankName;
    /**
     * 付款行账号(提出行)
     */
    private String presbank;
    /**
     * 退款时间
     */
    private Date presbackTime;
    /**
     * 注释
     */
    private String remark;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户清算行名称
     */
    private String customerCbankName;
    /**
     * 客户银行账号
     */
    private String customerBank;
    /**
     * 客户开户网点名称
     */
    private String customerObankName;
    
    private Byte isSettleaccounts;//是否结清 0-未结清 1-已结清
    
    private String handlerName;//处理人
    
    private Date handlerTime;//处理时间
    
    private BigDecimal backAccountAmount;//退还账户余额
    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Date getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
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
     * 获取
     * 
     * @return 
     */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置
     * 
     * @param processInstanceId 要设置的
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public Long getProjectId(){
        return projectId;
    }
    
    /**
     * 设置
     * 
     * @param projectId 要设置的
     */
    public void setProjectId(Long projectId){
        this.projectId = projectId;
    }
    public BigDecimal getBackAccountAmount() {
        return backAccountAmount;
    }

    public void setBackAccountAmount(BigDecimal backAccountAmount) {
        this.backAccountAmount = backAccountAmount;
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
     * 获取是否还清 0-未还清 1-已还清
     * 
     * @return 是否还清 0-未还清 1-已还清
     */
    public Byte getIsPayoff(){
        return isPayoff;
    }
    
    /**
     * 设置是否还清 0-未还清 1-已还清
     * 
     * @param isPayoff 要设置的是否还清 0-未还清 1-已还清
     */
    public void setIsPayoff(Byte isPayoff){
        this.isPayoff = isPayoff;
    }
    /**
     * 获取车辆处置金额
     * 
     * @return 车辆处置金额
     */
    public BigDecimal getDisposalAmount(){
        return disposalAmount;
    }
    
    /**
     * 设置车辆处置金额
     * 
     * @param disposalAmount 要设置的车辆处置金额
     */
    public void setDisposalAmount(BigDecimal disposalAmount){
        this.disposalAmount = disposalAmount;
    }
    /**
     * 获取车辆处置余额
     * 
     * @return 车辆处置余额
     */
    public BigDecimal getDisposalBalanceAmount(){
        return disposalBalanceAmount;
    }
    
    /**
     * 设置车辆处置余额
     * 
     * @param disposalBalanceAmount 要设置的车辆处置余额
     */
    public void setDisposalBalanceAmount(BigDecimal disposalBalanceAmount){
        this.disposalBalanceAmount = disposalBalanceAmount;
    }
    /**
     * 获取退还处置余额
     * 
     * @return 退还处置余额
     */
    public BigDecimal getDisposalBackAmount(){
        return disposalBackAmount;
    }
    
    /**
     * 设置退还处置余额
     * 
     * @param disposalBackAmount 要设置的退还处置余额
     */
    public void setDisposalBackAmount(BigDecimal disposalBackAmount){
        this.disposalBackAmount = disposalBackAmount;
    }
    /**
     * 获取借款保证金
     * 
     * @return 借款保证金
     */
    public BigDecimal getMarginFee(){
        return marginFee;
    }
    
    /**
     * 设置借款保证金
     * 
     * @param marginFee 要设置的借款保证金
     */
    public void setMarginFee(BigDecimal marginFee){
        this.marginFee = marginFee;
    }
    /**
     * 获取是否违约
     * 
     * @return 是否违约
     */
    public Byte getIsBreachOfContract(){
        return isBreachOfContract;
    }
    
    /**
     * 设置是否违约
     * 
     * @param isBreachOfContract 要设置的是否违约
     */
    public void setIsBreachOfContract(Byte isBreachOfContract){
        this.isBreachOfContract = isBreachOfContract;
    }
    /**
     * 获取退还保证金
     * 
     * @return 退还保证金
     */
    public BigDecimal getBackMarginFee(){
        return backMarginFee;
    }
    
    /**
     * 设置退还保证金
     * 
     * @param backMarginFee 要设置的退还保证金
     */
    public void setBackMarginFee(BigDecimal backMarginFee){
        this.backMarginFee = backMarginFee;
    }
    /**
     * 获取违章处理费
     * 
     * @return 违章处理费
     */
    public BigDecimal getIllegalDisposalFee(){
        return illegalDisposalFee;
    }
    
    /**
     * 设置违章处理费
     * 
     * @param illegalDisposalFee 要设置的违章处理费
     */
    public void setIllegalDisposalFee(BigDecimal illegalDisposalFee){
        this.illegalDisposalFee = illegalDisposalFee;
    }
    /**
     * 获取是否违章
     * 
     * @return 是否违章
     */
    public Byte getIsIllegal(){
        return isIllegal;
    }
    
    /**
     * 设置是否违章
     * 
     * @param isIllegal 要设置的是否违章
     */
    public void setIsIllegal(Byte isIllegal){
        this.isIllegal = isIllegal;
    }
    /**
     * 获取是否违章
     * 
     * @return 是否违章
     */
    public BigDecimal getBackIllegalDisposalFee(){
        return backIllegalDisposalFee;
    }
    
    /**
     * 设置是否违章
     * 
     * @param backIllegalDisposalFee 要设置的是否违章
     */
    public void setBackIllegalDisposalFee(BigDecimal backIllegalDisposalFee){
        this.backIllegalDisposalFee = backIllegalDisposalFee;
    }
    /**
     * 获取gps安装费
     * 
     * @return gps安装费
     */
    public BigDecimal getGpsInstallFee(){
        return gpsInstallFee;
    }
    
    /**
     * 设置gps安装费
     * 
     * @param gpsInstallFee 要设置的gps安装费
     */
    public void setGpsInstallFee(BigDecimal gpsInstallFee){
        this.gpsInstallFee = gpsInstallFee;
    }
    /**
     * 获取是否拆除
     * 
     * @return 是否拆除
     */
    public Byte getIsUninstall(){
        return isUninstall;
    }
    
    /**
     * 设置是否拆除
     * 
     * @param isUninstall 要设置的是否拆除
     */
    public void setIsUninstall(Byte isUninstall){
        this.isUninstall = isUninstall;
    }
    /**
     * 获取退还gps安装费
     * 
     * @return 退还gps安装费
     */
    public BigDecimal getBackGpsInstallFee(){
        return backGpsInstallFee;
    }
    
    /**
     * 设置退还gps安装费
     * 
     * @param backGpsInstallFee 要设置的退还gps安装费
     */
    public void setBackGpsInstallFee(BigDecimal backGpsInstallFee){
        this.backGpsInstallFee = backGpsInstallFee;
    }
    /**
     * 获取退还金额总计
     * 
     * @return 退还金额总计
     */
    public BigDecimal getTotalBackAmount(){
        return totalBackAmount;
    }
    
    /**
     * 设置退还金额总计
     * 
     * @param totalBackAmount 要设置的退还金额总计
     */
    public void setTotalBackAmount(BigDecimal totalBackAmount){
        this.totalBackAmount = totalBackAmount;
    }
    /**
     * 获取付款行名称(提出行)
     * 
     * @return 付款行名称(提出行)
     */
    public String getPresbankName(){
        return presbankName;
    }
    
    /**
     * 设置付款行名称(提出行)
     * 
     * @param presbankName 要设置的付款行名称(提出行)
     */
    public void setPresbankName(String presbankName){
        this.presbankName = presbankName;
    }
    /**
     * 获取付款行账号(提出行)
     * 
     * @return 付款行账号(提出行)
     */
    public String getPresbank(){
        return presbank;
    }
    
    /**
     * 设置付款行账号(提出行)
     * 
     * @param presbank 要设置的付款行账号(提出行)
     */
    public void setPresbank(String presbank){
        this.presbank = presbank;
    }
    /**
     * 获取退款时间
     * 
     * @return 退款时间
     */
    public Date getPresbackTime(){
        return presbackTime;
    }
    
    /**
     * 设置退款时间
     * 
     * @param presbackTime 要设置的退款时间
     */
    public void setPresbackTime(Date presbackTime){
        this.presbackTime = presbackTime;
    }
    /**
     * 获取注释
     * 
     * @return 注释
     */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置注释
     * 
     * @param remark 要设置的注释
     */
    public void setRemark(String remark){
        this.remark = remark;
    }
    /**
     * 获取合同编号
     * 
     * @return 合同编号
     */
    public String getContractNo(){
        return contractNo;
    }
    
    /**
     * 设置合同编号
     * 
     * @param contractNo 要设置的合同编号
     */
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }
    /**
     * 获取客户名称
     * 
     * @return 客户名称
     */
    public String getCustomerName(){
        return customerName;
    }
    
    /**
     * 设置客户名称
     * 
     * @param customerName 要设置的客户名称
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    /**
     * 获取客户清算行名称
     * 
     * @return 客户清算行名称
     */
    public String getCustomerCbankName(){
        return customerCbankName;
    }
    
    /**
     * 设置客户清算行名称
     * 
     * @param customerCbankName 要设置的客户清算行名称
     */
    public void setCustomerCbankName(String customerCbankName){
        this.customerCbankName = customerCbankName;
    }
    /**
     * 获取客户银行账号
     * 
     * @return 客户银行账号
     */
    public String getCustomerBank(){
        return customerBank;
    }
    
    /**
     * 设置客户银行账号
     * 
     * @param customerBank 要设置的客户银行账号
     */
    public void setCustomerBank(String customerBank){
        this.customerBank = customerBank;
    }
    /**
     * 获取客户开户网点名称
     * 
     * @return 客户开户网点名称
     */
    public String getCustomerObankName(){
        return customerObankName;
    }
    
    /**
     * 设置客户开户网点名称
     * 
     * @param customerObankName 要设置的客户开户网点名称
     */
    public void setCustomerObankName(String customerObankName){
        this.customerObankName = customerObankName;
    }

    public Byte getIsSettleaccounts() {
        return isSettleaccounts;
    }

    public void setIsSettleaccounts(Byte isSettleaccounts) {
        this.isSettleaccounts = isSettleaccounts;
    }
    
}


