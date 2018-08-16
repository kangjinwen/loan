package com.company.modules.credit.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 催收记录表实体
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
public class PubDundetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 催收表ID
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
     * 是否删除 0正常1删除
     */
    private Byte isDelete;
    /**
     * 还款明细ID
     */
    private Long repaymentId;
    /**
     *  催收方式1电话；2上门；3邮件；4短信；等等
     */
    private Byte type;
    /**
     * 催收时间
     */
    private Date dunTime;
    /**
     * 催收备注
     */
    private String remark;
    /**
     * 催收人
     */
    private String userName;
    
    /**
     * 获取催收表ID
     * 
     * @return 催收表ID
     */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置催收表ID
     * 
     * @param id 要设置的催收表ID
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
     * 获取是否删除 0正常1删除
     * 
     * @return 是否删除 0正常1删除
     */
    public Byte getIsDelete(){
        return isDelete;
    }
    
    /**
     * 设置是否删除 0正常1删除
     * 
     * @param isDelete 要设置的是否删除 0正常1删除
     */
    public void setIsDelete(Byte isDelete){
        this.isDelete = isDelete;
    }
    /**
     * 获取还款明细ID
     * 
     * @return 还款明细ID
     */
    public Long getRepaymentId(){
        return repaymentId;
    }
    
    /**
     * 设置还款明细ID
     * 
     * @param repaymentId 要设置的还款明细ID
     */
    public void setRepaymentId(Long repaymentId){
        this.repaymentId = repaymentId;
    }
    /**
     * 获取 催收方式1电话；2上门；3邮件；4短信；等等
     * 
     * @return  催收方式1电话；2上门；3邮件；4短信；等等
     */
    public Byte getType(){
        return type;
    }
    
    /**
     * 设置 催收方式1电话；2上门；3邮件；4短信；等等
     * 
     * @param type 要设置的 催收方式1电话；2上门；3邮件；4短信；等等
     */
    public void setType(Byte type){
        this.type = type;
    }
    /**
     * 获取催收备注
     * 
     * @return 催收备注
     */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置催收备注
     * 
     * @param remark 要设置的催收备注
     */
    public void setRemark(String remark){
        this.remark = remark;
    }

	public Date getDunTime() {
		return dunTime;
	}

	public void setDunTime(Date dunTime) {
		this.dunTime = dunTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
    
}


