package com.company.modules.contract.domain;

import java.io.Serializable;

/**
 * 放款银行信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-15 11:44:43
 */
public class PlBankcard implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 每个银行固有的标识
    */
    private Byte bankFlag;
    /**
    * 银行卡号
    */
    private String bankCardId;
    /**
    * 开户区
    */
    private String bankArea;
    /**
    * 状态0表示正常-1无效
    */
    private Byte status;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 账户名
    */
    private String accountName;
    
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
    * 获取每个银行固有的标识
    *
    * @return 每个银行固有的标识
    */
    public Byte getBankFlag(){
        return bankFlag;
    }
    
    /**
     * 设置每个银行固有的标识
     * 
     * @param bankFlag 要设置的每个银行固有的标识
     */
    public void setBankFlag(Byte bankFlag){
        this.bankFlag = bankFlag;
    }

    /**
    * 获取银行卡号
    *
    * @return 银行卡号
    */
    public String getBankCardId(){
        return bankCardId;
    }
    
    /**
     * 设置银行卡号
     * 
     * @param bankCardId 要设置的银行卡号
     */
    public void setBankCardId(String bankCardId){
        this.bankCardId = bankCardId;
    }

    /**
    * 获取开户区
    *
    * @return 开户区
    */
    public String getBankArea(){
        return bankArea;
    }
    
    /**
     * 设置开户区
     * 
     * @param bankArea 要设置的开户区
     */
    public void setBankArea(String bankArea){
        this.bankArea = bankArea;
    }

    /**
    * 获取状态0表示正常-1无效
    *
    * @return 状态0表示正常-1无效
    */
    public Byte getStatus(){
        return status;
    }
    
    /**
     * 设置状态0表示正常-1无效
     * 
     * @param status 要设置的状态0表示正常-1无效
     */
    public void setStatus(Byte status){
        this.status = status;
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
    * 获取账户名
    *
    * @return 账户名
    */
    public String getAccountName(){
        return accountName;
    }
    
    /**
     * 设置账户名
     * 
     * @param accountName 要设置的账户名
     */
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

}