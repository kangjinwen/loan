package com.company.modules.advance.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款基本信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-09-21 09:24:32
 */
public class HousAdvanceNotarize implements Serializable {
	
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
    * 主流程ID
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
    * 出借人
    */
    private String lender;
    /**
    * 出借人受托人
    */
    private String trusteeOfLender;
    /**
    * 受托人
    */
    private String trustee;
    /**
    * 备注
    */
    private String remark;
    
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
    * 获取主流程ID
    *
    * @return 主流程ID
    */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置主流程ID
     * 
     * @param processInstanceId 要设置的主流程ID
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }

    /**
    * 获取垫资分支流程ID
    *
    * @return 垫资分支流程ID
    */
    public String getNewProcessInstanceId(){
        return newProcessInstanceId;
    }
    
    /**
     * 设置垫资分支流程ID
     * 
     * @param newProcessInstanceId 要设置的垫资分支流程ID
     */
    public void setNewProcessInstanceId(String newProcessInstanceId){
        this.newProcessInstanceId = newProcessInstanceId;
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
    * 获取出借人
    *
    * @return 出借人
    */
    public String getLender(){
        return lender;
    }
    
    /**
     * 设置出借人
     * 
     * @param lender 要设置的出借人
     */
    public void setLender(String lender){
        this.lender = lender;
    }

    /**
    * 获取出借人受托人
    *
    * @return 出借人受托人
    */
    public String getTrusteeOfLender(){
        return trusteeOfLender;
    }
    
    /**
     * 设置出借人受托人
     * 
     * @param trusteeOfLender 要设置的出借人受托人
     */
    public void setTrusteeOfLender(String trusteeOfLender){
        this.trusteeOfLender = trusteeOfLender;
    }

    /**
    * 获取受托人
    *
    * @return 受托人
    */
    public String getTrustee(){
        return trustee;
    }
    
    /**
     * 设置受托人
     * 
     * @param trustee 要设置的受托人
     */
    public void setTrustee(String trustee){
        this.trustee = trustee;
    }

    /**
    * 获取备注
    *
    * @return 备注
    */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置备注
     * 
     * @param remark 要设置的备注
     */
    public void setRemark(String remark){
        this.remark = remark;
    }

}