package com.company.modules.credit.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 联系明细管理实体
 * @version 1.0
 * @since 2016-12-12 10:29:58
 */
public class PubCustomerRelation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
    * 是否删除 0正常1删除
    */
    private Byte isDelete;
    /**
    * 客户表ID
    */
    private Integer customerId;
    /**
    * 贷款次数
    */
    private String loanCount;
    /**
    * 联系人
    */
    private String userName;
    /**
    * 联系方式1电话；2上门；3邮件；4短信；
    */
    private Byte type;
    /**
    * 联系时间
    */
    private Date contactTime;
    /**
    * 联系备注
    */
    private String remark;
    
    /**
    * 获取id
    *
    * @return id
    */
    public long getId(){
        return id;
    }
    
    /**
     * 设置id
     * 
     * @param long 要设置的id
     */
    public void setId(long id){
        this.id = id;
    }

    public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
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
    * 获取客户表ID
    *
    * @return 客户表ID
    */
    public Integer getCustomerId(){
        return customerId;
    }
    
    /**
     * 设置客户表ID
     * 
     * @param customerId 要设置的客户表ID
     */
    public void setCustomerId(Integer customerId){
        this.customerId = customerId;
    }

    /**
    * 获取贷款次数
    *
    * @return 贷款次数
    */
    public String getLoanCount(){
        return loanCount;
    }
    
    /**
     * 设置贷款次数
     * 
     * @param loanCount 要设置的贷款次数
     */
    public void setLoanCount(String loanCount){
        this.loanCount = loanCount;
    }

    /**
    * 获取联系人
    *
    * @return 联系人
    */
    public String getUserName(){
        return userName;
    }
    
    /**
     * 设置联系人
     * 
     * @param userName 要设置的联系人
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
    * 获取联系方式1电话；2上门；3邮件；4短信；
    *
    * @return 联系方式1电话；2上门；3邮件；4短信；
    */
    public Byte getType(){
        return type;
    }
    
    /**
     * 设置联系方式1电话；2上门；3邮件；4短信；
     * 
     * @param type 要设置的联系方式1电话；2上门；3邮件；4短信；
     */
    public void setType(Byte type){
        this.type = type;
    }

    /**
    * 获取联系时间
    *
    * @return 联系时间
    */
    public Date getContactTime(){
        return contactTime;
    }
    
    /**
     * 设置联系时间
     * 
     * @param contactTime 要设置的联系时间
     */
    public void setContactTime(Date contactTime){
        this.contactTime = contactTime;
    }

    /**
    * 获取联系备注
    *
    * @return 联系备注
    */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置联系备注
     * 
     * @param remark 要设置的联系备注
     */
    public void setRemark(String remark){
        this.remark = remark;
    }

}