package com.company.modules.anrong.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 安融风控结果实体
 * @author mcwang
 * @version 1.0
 * @since 2016-09-13 03:00:19
 */
public class PubArRiskResult implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 
    */
    private Long creator;
    /**
    * 
    */
    private Date createTime;
    /**
    * 接口参数
    */
    private String riskParam;
    /**
    * 风险结果
    */
    private String riskResult;
    /**
    * 项目id
    */
    private Long projectId;
    /**
    * 咨询id
    */
    private Long consultId;
    /**
    * 附件id
    */
    private Long fileId;
    /**
    * 
    */
    private Long riskType;
    
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
    * 获取
    *
    * @return 
    */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 设置
     * 
     * @param creator 要设置的
     */
    public void setCreator(Long creator){
        this.creator = creator;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置
     * 
     * @param createTime 要设置的
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取接口参数
    *
    * @return 接口参数
    */
    public String getRiskParam(){
        return riskParam;
    }
    
    /**
     * 设置接口参数
     * 
     * @param riskParam 要设置的接口参数
     */
    public void setRiskParam(String riskParam){
        this.riskParam = riskParam;
    }

    /**
    * 获取风险结果
    *
    * @return 风险结果
    */
    public String getRiskResult(){
        return riskResult;
    }
    
    /**
     * 设置风险结果
     * 
     * @param riskResult 要设置的风险结果
     */
    public void setRiskResult(String riskResult){
        this.riskResult = riskResult;
    }

    /**
    * 获取项目id
    *
    * @return 项目id
    */
    public Long getProjectId(){
        return projectId;
    }
    
    /**
     * 设置项目id
     * 
     * @param projectId 要设置的项目id
     */
    public void setProjectId(Long projectId){
        this.projectId = projectId;
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
    * 获取附件id
    *
    * @return 附件id
    */
    public Long getFileId(){
        return fileId;
    }
    
    /**
     * 设置附件id
     * 
     * @param fileId 要设置的附件id
     */
    public void setFileId(Long fileId){
        this.fileId = fileId;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getRiskType(){
        return riskType;
    }
    
    /**
     * 设置
     * 
     * @param riskType 要设置的
     */
    public void setRiskType(Long riskType){
        this.riskType = riskType;
    }

}