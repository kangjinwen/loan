package com.company.modules.instance.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增人员类型实体
 * @author wulb
 * @version 1.0
 * @since 2016-11-15 13:47:18
 */
public class HousPersonType implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
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
    * 流程id
    */
    private String processInstanceId;
    /**
    * 备注
    */
    private String remark;
    /**
    * 类型
    */
    private Byte type;
    /**
    * 姓名
    */
    private String personName;
    /**
    * 身份证号
    */
    private String personNumber;
    /**
    * 联系方式
    */
    private String personPhone;
    
    private Long consultId;
    
    
    
    


    public Long getConsultId() {
		return consultId;
	}

	public void setConsultId(Long consultId) {
		this.consultId = consultId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
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
    * 获取流程id
    *
    * @return 流程id
    */
    public String getProcessInstanceId(){
        return processInstanceId;
    }
    
    /**
     * 设置流程id
     * 
     * @param processInstanceId 要设置的流程id
     */
    public void setProcessInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
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
    * 获取姓名
    *
    * @return 姓名
    */
    public String getPersonName(){
        return personName;
    }
    
    /**
     * 设置姓名
     * 
     * @param personName 要设置的姓名
     */
    public void setPersonName(String personName){
        this.personName = personName;
    }

    /**
    * 获取身份证号
    *
    * @return 身份证号
    */
    public String getPersonNumber(){
        return personNumber;
    }
    
    /**
     * 设置身份证号
     * 
     * @param personNumber 要设置的身份证号
     */
    public void setPersonNumber(String personNumber){
        this.personNumber = personNumber;
    }

    /**
    * 获取联系方式
    *
    * @return 联系方式
    */
    public String getPersonPhone(){
        return personPhone;
    }
    
    /**
     * 设置联系方式
     * 
     * @param personPhone 要设置的联系方式
     */
    public void setPersonPhone(String personPhone){
        this.personPhone = personPhone;
    }

}