package com.company.modules.audit.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 面审信息表实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-14 01:28:09
 */
public class HousFaceTrial implements Serializable {
	
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
    * 房产地址
    */
    private String propertyAddressId;
    /**
    * 房产详细地址
    */
    private String propertyAddress;
    /**
    * 房权证号
    */
    private String propertyCertificate;
    /**
    * 房产性质
    */
    private Byte propertyProperties;
    /**
    * 经适房房本时间
    */
    private Date affordableRoomTime;
    /**
    * 经适房购房发票时间
    */
    private Date purchaseInvoicesTime;
    /**
    * 房改房上市证明
    */
    private Byte propertyListedProof;
    /**
    * 规划用途
    */
    private Byte planningPurposes;
    /**
    * 钥匙盘查询
    */
    private Byte keyDiskQuery;
    
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
    * 获取房产地址
    *
    * @return 房产地址
    */
    public String getPropertyAddressId(){
        return propertyAddressId;
    }
    
    /**
     * 设置房产地址
     * 
     * @param propertyAddressId 要设置的房产地址
     */
    public void setPropertyAddressId(String propertyAddressId){
        this.propertyAddressId = propertyAddressId;
    }

    /**
    * 获取房产详细地址
    *
    * @return 房产详细地址
    */
    public String getPropertyAddress(){
        return propertyAddress;
    }
    
    /**
     * 设置房产详细地址
     * 
     * @param propertyAddress 要设置的房产详细地址
     */
    public void setPropertyAddress(String propertyAddress){
        this.propertyAddress = propertyAddress;
    }

    /**
    * 获取房权证号
    *
    * @return 房权证号
    */
    public String getPropertyCertificate(){
        return propertyCertificate;
    }
    
    /**
     * 设置房权证号
     * 
     * @param propertyCertificate 要设置的房权证号
     */
    public void setPropertyCertificate(String propertyCertificate){
        this.propertyCertificate = propertyCertificate;
    }

    /**
    * 获取房产性质
    *
    * @return 房产性质
    */
    public Byte getPropertyProperties(){
        return propertyProperties;
    }
    
    /**
     * 设置房产性质
     * 
     * @param propertyProperties 要设置的房产性质
     */
    public void setPropertyProperties(Byte propertyProperties){
        this.propertyProperties = propertyProperties;
    }

    /**
    * 获取经适房房本时间
    *
    * @return 经适房房本时间
    */
    public Date getAffordableRoomTime(){
        return affordableRoomTime;
    }
    
    /**
     * 设置经适房房本时间
     * 
     * @param affordableRoomTime 要设置的经适房房本时间
     */
    public void setAffordableRoomTime(Date affordableRoomTime){
        this.affordableRoomTime = affordableRoomTime;
    }

    /**
    * 获取经适房购房发票时间
    *
    * @return 经适房购房发票时间
    */
    public Date getPurchaseInvoicesTime(){
        return purchaseInvoicesTime;
    }
    
    /**
     * 设置经适房购房发票时间
     * 
     * @param purchaseInvoicesTime 要设置的经适房购房发票时间
     */
    public void setPurchaseInvoicesTime(Date purchaseInvoicesTime){
        this.purchaseInvoicesTime = purchaseInvoicesTime;
    }

    /**
    * 获取房改房上市证明
    *
    * @return 房改房上市证明
    */
    public Byte getPropertyListedProof(){
        return propertyListedProof;
    }
    
    /**
     * 设置房改房上市证明
     * 
     * @param propertyListedProof 要设置的房改房上市证明
     */
    public void setPropertyListedProof(Byte propertyListedProof){
        this.propertyListedProof = propertyListedProof;
    }

    /**
    * 获取规划用途
    *
    * @return 规划用途
    */
    public Byte getPlanningPurposes(){
        return planningPurposes;
    }
    
    /**
     * 设置规划用途
     * 
     * @param planningPurposes 要设置的规划用途
     */
    public void setPlanningPurposes(Byte planningPurposes){
        this.planningPurposes = planningPurposes;
    }

    /**
    * 获取钥匙盘查询
    *
    * @return 钥匙盘查询
    */
    public Byte getKeyDiskQuery(){
        return keyDiskQuery;
    }
    
    /**
     * 设置钥匙盘查询
     * 
     * @param keyDiskQuery 要设置的钥匙盘查询
     */
    public void setKeyDiskQuery(Byte keyDiskQuery){
        this.keyDiskQuery = keyDiskQuery;
    }

}