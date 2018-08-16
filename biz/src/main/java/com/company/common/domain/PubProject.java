package com.company.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-06 11:39:36
 */
public class PubProject implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 项目编号-规则
    */
    private String code;
    /**
    * 项目名称
    */
    private String projectName;
    /**
    * 类型
    */
    private Byte type;
    /**
    * 创建者
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
    * 是否删除
    */
    private Long isDelete;
    
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
    * 获取项目编号-规则
    *
    * @return 项目编号-规则
    */
    public String getCode(){
        return code;
    }
    
    /**
     * 设置项目编号-规则
     * 
     * @param code 要设置的项目编号-规则
     */
    public void setCode(String code){
        this.code = code;
    }

    /**
    * 获取项目名称
    *
    * @return 项目名称
    */
    public String getProjectName(){
        return projectName;
    }
    
    /**
     * 设置项目名称
     * 
     * @param projectName 要设置的项目名称
     */
    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    /**
    * 获取类型
    *
    * @return 类型
    */
    public Byte getType(){
        return type;
    }
    
    /**
     * 设置类型
     * 
     * @param type 要设置的类型
     */
    public void setType(Byte type){
        this.type = type;
    }

    /**
    * 获取创建者
    *
    * @return 创建者
    */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 设置创建者
     * 
     * @param creator 要设置的创建者
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
    * 获取是否删除
    *
    * @return 是否删除
    */
    public Long getIsDelete(){
        return isDelete;
    }
    
    /**
     * 设置是否删除
     * 
     * @param isDelete 要设置的是否删除
     */
    public void setIsDelete(Long isDelete){
        this.isDelete = isDelete;
    }

}