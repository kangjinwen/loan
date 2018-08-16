package com.company.modules.audit.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 婚姻信息表(面审)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-14 01:28:36
 */
public class HousMarriageInformation implements Serializable {
	
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
    * 婚姻状况
    */
    private Byte maritalStatus;
    /**
    * 证件日期
    */
    private Date documentsTime;
    /**
    * 离婚协议
    */
    private Byte divorceAgreement;
    /**
    * 法院判决
    */
    private Byte courtVerdict;
    /**
    * 房本日期
    */
    private Date roomTime;
    
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
    * 获取婚姻状况
    *
    * @return 婚姻状况
    */
    public Byte getMaritalStatus(){
        return maritalStatus;
    }
    
    /**
     * 设置婚姻状况
     * 
     * @param maritalStatus 要设置的婚姻状况
     */
    public void setMaritalStatus(Byte maritalStatus){
        this.maritalStatus = maritalStatus;
    }

    /**
    * 获取证件日期
    *
    * @return 证件日期
    */
    public Date getDocumentsTime(){
        return documentsTime;
    }
    
    /**
     * 设置证件日期
     * 
     * @param documentsTime 要设置的证件日期
     */
    public void setDocumentsTime(Date documentsTime){
        this.documentsTime = documentsTime;
    }

    /**
    * 获取离婚协议
    *
    * @return 离婚协议
    */
    public Byte getDivorceAgreement(){
        return divorceAgreement;
    }
    
    /**
     * 设置离婚协议
     * 
     * @param divorceAgreement 要设置的离婚协议
     */
    public void setDivorceAgreement(Byte divorceAgreement){
        this.divorceAgreement = divorceAgreement;
    }

    /**
    * 获取法院判决
    *
    * @return 法院判决
    */
    public Byte getCourtVerdict(){
        return courtVerdict;
    }
    
    /**
     * 设置法院判决
     * 
     * @param courtVerdict 要设置的法院判决
     */
    public void setCourtVerdict(Byte courtVerdict){
        this.courtVerdict = courtVerdict;
    }

    /**
    * 获取房本日期
    *
    * @return 房本日期
    */
    public Date getRoomTime(){
        return roomTime;
    }
    
    /**
     * 设置房本日期
     * 
     * @param roomTime 要设置的房本日期
     */
    public void setRoomTime(Date roomTime){
        this.roomTime = roomTime;
    }

}