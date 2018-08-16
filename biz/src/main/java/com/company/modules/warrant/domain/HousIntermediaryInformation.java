package com.company.modules.warrant.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房屋快出值(权证下户)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-10 05:04:19
 */
public class HousIntermediaryInformation implements Serializable {
	
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
    * 门名名称
    */
    private String doorName;
    /**
    * 负责人姓名
    */
    private String principalName;
    /**
    * 负责人联系方式
    */
    private String principalPhone;
    /**
    * 税款详情
    */
    private String taxDetails;
    /**
    * 正常价格(万)
    */
    private BigDecimal normalPrice;
    /**
    * 快速成交价格(万)
    */
    private BigDecimal fastTransactionPrice;
    /**
     * 房屋快出值(元)
     */
     private BigDecimal housingValueFaster;
     /**
      * 学校
      */
      private Byte school;
      /**
      * 医院
      */
      private Byte hospital;
      /**
      * 购物等配套情况
      */
      private Byte shopping;
      
    public Byte getSchool() {
		return school;
	}


	public void setSchool(Byte school) {
		this.school = school;
	}


	public Byte getHospital() {
		return hospital;
	}


	public void setHospital(Byte hospital) {
		this.hospital = hospital;
	}


	public Byte getShopping() {
		return shopping;
	}


	public void setShopping(Byte shopping) {
		this.shopping = shopping;
	}

	/**
     *备注
     * */
    private String remark;
    
    
    public BigDecimal getHousingValueFaster() {
		return housingValueFaster;
	}


	public void setHousingValueFaster(BigDecimal housingValueFaster) {
		this.housingValueFaster = housingValueFaster;
	}


	/**
    * 获取id
    *
    * @return id
    */
    public Long getId(){
        return id;
    }
    
    
    public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
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
    * 获取门名名称
    *
    * @return 门名名称
    */
    public String getDoorName(){
        return doorName;
    }
    
    /**
     * 设置门名名称
     * 
     * @param doorName 要设置的门名名称
     */
    public void setDoorName(String doorName){
        this.doorName = doorName;
    }

    /**
    * 获取负责人姓名
    *
    * @return 负责人姓名
    */
    public String getPrincipalName(){
        return principalName;
    }
    
    /**
     * 设置负责人姓名
     * 
     * @param principalName 要设置的负责人姓名
     */
    public void setPrincipalName(String principalName){
        this.principalName = principalName;
    }

    /**
    * 获取负责人联系方式
    *
    * @return 负责人联系方式
    */
    public String getPrincipalPhone(){
        return principalPhone;
    }
    
    /**
     * 设置负责人联系方式
     * 
     * @param principalPhone 要设置的负责人联系方式
     */
    public void setPrincipalPhone(String principalPhone){
        this.principalPhone = principalPhone;
    }

    /**
    * 获取税款详情
    *
    * @return 税款详情
    */
    public String getTaxDetails(){
        return taxDetails;
    }
    
    /**
     * 设置税款详情
     * 
     * @param taxDetails 要设置的税款详情
     */
    public void setTaxDetails(String taxDetails){
        this.taxDetails = taxDetails;
    }

    /**
    * 获取正常价格(万)
    *
    * @return 正常价格(万)
    */
    public BigDecimal getNormalPrice(){
        return normalPrice;
    }
    
    /**
     * 设置正常价格(万)
     * 
     * @param normalPrice 要设置的正常价格(万)
     */
    public void setNormalPrice(BigDecimal normalPrice){
        this.normalPrice = normalPrice;
    }

    /**
    * 获取快速成交价格(万)
    *
    * @return 快速成交价格(万)
    */
    public BigDecimal getFastTransactionPrice(){
        return fastTransactionPrice;
    }
    
    /**
     * 设置快速成交价格(万)
     * 
     * @param fastTransactionPrice 要设置的快速成交价格(万)
     */
    public void setFastTransactionPrice(BigDecimal fastTransactionPrice){
        this.fastTransactionPrice = fastTransactionPrice;
    }

}