package com.company.common.domain;

import java.io.Serializable;

/**
 * 项目流程关联实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-06 12:02:17
 */
public class PubProjectProcess implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 项目ID
    */
    private Long projectId;
    /**
    * 流程编号
    */
    private String processInstanceId;
    /**
    * 展期期数（第几次展期）：0非展期,1第一次展期...
    */
    private Byte extensionNumber;
    /**
    * 流程类型:0主流程(包括展期和非展期流程） 1提前 2减免  3强制 5处置流程
    */
    private Byte processType;
    
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
    * 获取流程编号
    *
    * @return 流程编号
    */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置流程编号
     * 
     * @param processInstanceId 要设置的流程编号
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
    }

    /**
    * 获取展期期数（第几次展期）：0非展期,1第一次展期...
    *
    * @return 展期期数（第几次展期）：0非展期,1第一次展期...
    */
    public Byte getExtensionNumber(){
        return extensionNumber;
    }
    
    /**
     * 设置展期期数（第几次展期）：0非展期,1第一次展期...
     * 
     * @param extensionNumber 要设置的展期期数（第几次展期）：0非展期,1第一次展期...
     */
    public void setExtensionNumber(Byte extensionNumber){
        this.extensionNumber = extensionNumber;
    }

    /**
    * 获取流程类型:0主流程(包括展期和非展期流程） 1提前 2减免  3强制 5处置流程
    *
    * @return 流程类型:0主流程(包括展期和非展期流程） 1提前 2减免  3强制 5处置流程
    */
    public Byte getProcessType(){
        return processType;
    }
    
    /**
     * 设置流程类型:0主流程(包括展期和非展期流程） 1提前 2减免  3强制 5处置流程
     * 
     * @param processType 要设置的流程类型:0主流程(包括展期和非展期流程） 1提前 2减免  3强制 5处置流程
     */
    public void setProcessType(Byte processType){
        this.processType = processType;
    }

}