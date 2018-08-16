package com.company.modules.contract.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-15 11:21:07
 */
public class PlContract implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 咨询ID
    */
    private Long consultId;
    /**
    * 项目编号
    */
    private Long projectId;
    /**
    * 借款协议编号
    */
    private String contractNo;
    /**
    * 合同内容
    */
    private String qdhtdata;
    /**
    * 首次生成时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date modifyTime;
    /**
    * 合同签属日期
    */
    private Date signatureTime;
    /**
    * 借款开始时间
    */
    private Date borrowStart;
    /**
    * 借款结束时间
    */
    private Date borrowEnd;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 创建者
    */
    private String creator;
    /**
    * 修改者
    */
    private String modifier;
    /**
    * 签属地址
    */
    private String signatureAddress;
    /**
    * 合同金额
    */
    private BigDecimal contractAccount;
    /**
    * 服务费
    */
    private BigDecimal serviceFee;
    /**
    * 记录状态 0未签订,1已签订,2签订失败,3退回重签
    */
    private Byte status;
    
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
    * 获取借款协议编号
    *
    * @return 借款协议编号
    */
    public String getContractNo(){
        return contractNo;
    }
    
    /**
     * 设置借款协议编号
     * 
     * @param contractNo 要设置的借款协议编号
     */
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }

    /**
    * 获取合同内容
    *
    * @return 合同内容
    */
    public String getQdhtdata(){
        return qdhtdata;
    }
    
    /**
     * 设置合同内容
     * 
     * @param qdhtdata 要设置的合同内容
     */
    public void setQdhtdata(String qdhtdata){
        this.qdhtdata = qdhtdata;
    }

    /**
    * 获取首次生成时间
    *
    * @return 首次生成时间
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置首次生成时间
     * 
     * @param createTime 要设置的首次生成时间
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取更新时间
    *
    * @return 更新时间
    */
    public Date getModifyTime(){
        return modifyTime;
    }
    
    /**
     * 设置更新时间
     * 
     * @param modifyTime 要设置的更新时间
     */
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }

    /**
    * 获取合同签属日期
    *
    * @return 合同签属日期
    */
    public Date getSignatureTime(){
        return signatureTime;
    }
    
    /**
     * 设置合同签属日期
     * 
     * @param signatureTime 要设置的合同签属日期
     */
    public void setSignatureTime(Date signatureTime){
        this.signatureTime = signatureTime;
    }

    /**
    * 获取借款开始时间
    *
    * @return 借款开始时间
    */
    public Date getBorrowStart(){
        return borrowStart;
    }
    
    /**
     * 设置借款开始时间
     * 
     * @param borrowStart 要设置的借款开始时间
     */
    public void setBorrowStart(Date borrowStart){
        this.borrowStart = borrowStart;
    }

    /**
    * 获取借款结束时间
    *
    * @return 借款结束时间
    */
    public Date getBorrowEnd(){
        return borrowEnd;
    }
    
    /**
     * 设置借款结束时间
     * 
     * @param borrowEnd 要设置的借款结束时间
     */
    public void setBorrowEnd(Date borrowEnd){
        this.borrowEnd = borrowEnd;
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
    * 获取签属地址
    *
    * @return 签属地址
    */
    public String getSignatureAddress(){
        return signatureAddress;
    }
    
    /**
     * 设置签属地址
     * 
     * @param signatureAddress 要设置的签属地址
     */
    public void setSignatureAddress(String signatureAddress){
        this.signatureAddress = signatureAddress;
    }

    /**
    * 获取合同金额
    *
    * @return 合同金额
    */
    public BigDecimal getContractAccount(){
        return contractAccount;
    }
    
    /**
     * 设置合同金额
     * 
     * @param contractAccount 要设置的合同金额
     */
    public void setContractAccount(BigDecimal contractAccount){
        this.contractAccount = contractAccount;
    }

    /**
    * 获取服务费
    *
    * @return 服务费
    */
    public BigDecimal getServiceFee(){
        return serviceFee;
    }
    
    /**
     * 设置服务费
     * 
     * @param serviceFee 要设置的服务费
     */
    public void setServiceFee(BigDecimal serviceFee){
        this.serviceFee = serviceFee;
    }

    /**
    * 获取记录状态 0未签订,1已签订,2签订失败,3退回重签
    *
    * @return 记录状态 0未签订,1已签订,2签订失败,3退回重签
    */
    public Byte getStatus(){
        return status;
    }
    
    /**
     * 设置记录状态 0未签订,1已签订,2签订失败,3退回重签
     * 
     * @param status 要设置的记录状态 0未签订,1已签订,2签订失败,3退回重签
     */
    public void setStatus(Byte status){
        this.status = status;
    }

}