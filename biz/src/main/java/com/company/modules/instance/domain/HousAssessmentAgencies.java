package com.company.modules.instance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 评估机构实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-06 02:33:32
 */
public class HousAssessmentAgencies implements Serializable {
	
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
    * 房产信息表id
    */
    private Long propertyId;
    /**
    * 评估机构
    */
    private Byte assessmentAgencies;
    /**
    * 评估值
    */
    private BigDecimal assessedValue;
    
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
    * 获取房产信息表id
    *
    * @return 房产信息表id
    */
    public Long getPropertyId(){
        return propertyId;
    }
    
    /**
     * 设置房产信息表id
     * 
     * @param propertyId 要设置的房产信息表id
     */
    public void setPropertyId(Long propertyId){
        this.propertyId = propertyId;
    }

    /**
    * 获取评估机构
    *
    * @return 评估机构
    */
    public Byte getAssessmentAgencies(){
        return assessmentAgencies;
    }
    
    /**
     * 设置评估机构
     * 
     * @param assessmentAgencies 要设置的评估机构
     */
    public void setAssessmentAgencies(Byte assessmentAgencies){
        this.assessmentAgencies = assessmentAgencies;
    }

    /**
    * 获取评估值
    *
    * @return 评估值
    */
    public BigDecimal getAssessedValue(){
        return assessedValue;
    }
    
    /**
     * 设置评估值
     * 
     * @param assessedValue 要设置的评估值
     */
    public void setAssessedValue(BigDecimal assessedValue){
        this.assessedValue = assessedValue;
    }

}