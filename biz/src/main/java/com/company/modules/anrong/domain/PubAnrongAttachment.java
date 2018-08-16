package com.company.modules.anrong.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 安融附件实体
 * @author mcwang
 * @version 1.0
 * @since 2016-09-13 03:01:50
 */
public class PubAnrongAttachment implements Serializable {
	
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
    * 附件类型:MSP|个人关联企业|企业司法
    */
    private String fileType;
    /**
    * 原文件名
    */
    private String fileName;
    /**
    * 文件大小
    */
    private Long fileSize;
    /**
    * 文件目录相对路径
    */
    private String filePath;
    
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
    * 获取附件类型:MSP|个人关联企业|企业司法
    *
    * @return 附件类型:MSP|个人关联企业|企业司法
    */
    public String getFileType(){
        return fileType;
    }
    
    /**
     * 设置附件类型:MSP|个人关联企业|企业司法
     * 
     * @param fileType 要设置的附件类型:MSP|个人关联企业|企业司法
     */
    public void setFileType(String fileType){
        this.fileType = fileType;
    }

    /**
    * 获取原文件名
    *
    * @return 原文件名
    */
    public String getFileName(){
        return fileName;
    }
    
    /**
     * 设置原文件名
     * 
     * @param fileName 要设置的原文件名
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
    * 获取文件大小
    *
    * @return 文件大小
    */
    public Long getFileSize(){
        return fileSize;
    }
    
    /**
     * 设置文件大小
     * 
     * @param fileSize 要设置的文件大小
     */
    public void setFileSize(Long fileSize){
        this.fileSize = fileSize;
    }

    /**
    * 获取文件目录相对路径
    *
    * @return 文件目录相对路径
    */
    public String getFilePath(){
        return filePath;
    }
    
    /**
     * 设置文件目录相对路径
     * 
     * @param filePath 要设置的文件目录相对路径
     */
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

}