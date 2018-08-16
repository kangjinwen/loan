package com.company.modules.instance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 房产信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-06 02:28:59
 */
public class HousPropertyInformation implements Serializable {
	
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
    * 房产地址
    */
    private Long propertyAddressId;
    /**
    * 房产详细地址
    */
    private String propertyAddress;
    /**
    * 房产面积
    */
    private BigDecimal propertyArea;
    /**
    * 房产性质
    */
    private Byte propertyProperties;
    /**
    * 房产现状
    */
    private Byte propertySituation;
    /**
    * 是否一抵
    */
    private Byte whetherOneContact;
    /**
    * 一抵抵押权人
    */
    private String againstOneMortgagee;
    /**
    * 一抵金额
    */
    private BigDecimal againstOneAmount;
    /**
     * 一抵备注
     */
    private String remarkOne;
     /**
      * 二抵备注
      */
    private String remarkTwo;
    /**
    * 是否二抵
    */
    private Byte whetherTwoContact;
    /**
    * 二抵抵押权人
    */
    private String againstTwoMortgagee;
    /**
    * 二抵金额
    */
    private BigDecimal againstTwoAmount;
    /**
     *房本号码
     */
     private String houseNumber;
     /**
      *房屋抵押情况
      */
     private Byte mortgageSituation;
     /**
      * 钥匙盘查询
      */
      private Byte keyDiskQuery;
      /**
       * 规划用途
       */
       private Byte planningPurposes;
       /**
        * 获取规划用途
        *
        * @return 规划用途
        */
        public Byte getPlanningPurposes(){
            return planningPurposes;
        }
        
        /**
         * 设置规划用途
         * 
         * @param planningPurposes 要设置的规划用途
         */
        public void setPlanningPurposes(Byte planningPurposes){
            this.planningPurposes = planningPurposes;
        }
   
    
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
    * 获取房产地址
    *
    * @return 房产地址
    */
    public Long getPropertyAddressId(){
        return propertyAddressId;
    }
    
    /**
     * 设置房产地址
     * 
     * @param propertyAddressId 要设置的房产地址
     */
    public void setPropertyAddressId(Long propertyAddressId){
        this.propertyAddressId = propertyAddressId;
    }

    /**
    * 获取房产详细地址
    *
    * @return 房产详细地址
    */
    public String getPropertyAddress(){
        return propertyAddress;
    }
    
    /**
     * 设置房产详细地址
     * 
     * @param propertyAddress 要设置的房产详细地址
     */
    public void setPropertyAddress(String propertyAddress){
        this.propertyAddress = propertyAddress;
    }

    /**
    * 获取房产面积
    *
    * @return 房产面积
    */
    public BigDecimal getPropertyArea(){
        return propertyArea;
    }
    
    /**
     * 设置房产面积
     * 
     * @param propertyArea 要设置的房产面积
     */
    public void setPropertyArea(BigDecimal propertyArea){
        this.propertyArea = propertyArea;
    }

    /**
    * 获取房产性质
    *
    * @return 房产性质
    */
    public Byte getPropertyProperties(){
        return propertyProperties;
    }
    
    /**
     * 设置房产性质
     * 
     * @param propertyProperties 要设置的房产性质
     */
    public void setPropertyProperties(Byte propertyProperties){
        this.propertyProperties = propertyProperties;
    }

    /**
    * 获取房产现状
    *
    * @return 房产现状
    */
    public Byte getPropertySituation(){
        return propertySituation;
    }
    
    /**
     * 设置房产现状
     * 
     * @param propertySituation 要设置的房产现状
     */
    public void setPropertySituation(Byte propertySituation){
        this.propertySituation = propertySituation;
    }

    /**
    * 获取是否一抵
    *
    * @return 是否一抵
    */
    public Byte getWhetherOneContact(){
        return whetherOneContact;
    }
    
    /**
     * 设置是否一抵
     * 
     * @param whetherOneContact 要设置的是否一抵
     */
    public void setWhetherOneContact(Byte whetherOneContact){
        this.whetherOneContact = whetherOneContact;
    }

    /**
    * 获取一抵抵押权人
    *
    * @return 一抵抵押权人
    */
    public String getAgainstOneMortgagee(){
        return againstOneMortgagee;
    }
    
    /**
     * 设置一抵抵押权人
     * 
     * @param againstOneMortgagee 要设置的一抵抵押权人
     */
    public void setAgainstOneMortgagee(String againstOneMortgagee){
        this.againstOneMortgagee = againstOneMortgagee;
    }

    /**
    * 获取一抵金额
    *
    * @return 一抵金额
    */
    public BigDecimal getAgainstOneAmount(){
        return againstOneAmount;
    }
    
    /**
     * 设置一抵金额
     * 
     * @param againstOneAmount 要设置的一抵金额
     */
    public void setAgainstOneAmount(BigDecimal againstOneAmount){
        this.againstOneAmount = againstOneAmount;
    }

    /**
    * 获取是否二抵
    *
    * @return 是否二抵
    */
    public Byte getWhetherTwoContact(){
        return whetherTwoContact;
    }
    
    /**
     * 设置是否二抵
     * 
     * @param whetherTwoContact 要设置的是否二抵
     */
    public void setWhetherTwoContact(Byte whetherTwoContact){
        this.whetherTwoContact = whetherTwoContact;
    }

    /**
    * 获取二抵抵押权人
    *
    * @return 二抵抵押权人
    */
    public String getAgainstTwoMortgagee(){
        return againstTwoMortgagee;
    }
    
    /**
     * 设置二抵抵押权人
     * 
     * @param againstTwoMortgagee 要设置的二抵抵押权人
     */
    public void setAgainstTwoMortgagee(String againstTwoMortgagee){
        this.againstTwoMortgagee = againstTwoMortgagee;
    }

    /**
    * 获取二抵金额
    *
    * @return 二抵金额
    */
    public BigDecimal getAgainstTwoAmount(){
        return againstTwoAmount;
    }
    
    /**
     * 设置二抵金额
     * 
     * @param againstTwoAmount 要设置的二抵金额
     */
    public void setAgainstTwoAmount(BigDecimal againstTwoAmount){
        this.againstTwoAmount = againstTwoAmount;
    }

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Byte getMortgageSituation() {
		return mortgageSituation;
	}
	
	

	public String getRemarkOne() {
		return remarkOne;
	}

	public void setRemarkOne(String remarkOne) {
		this.remarkOne = remarkOne;
	}

	public String getRemarkTwo() {
		return remarkTwo;
	}

	public void setRemarkTwo(String remarkTwo) {
		this.remarkTwo = remarkTwo;
	}

	public void setMortgageSituation(Byte mortgageSituation) {
		this.mortgageSituation = mortgageSituation;
	}
    /**
    * 获取钥匙盘查询
    *
    * @return 钥匙盘查询
    */
    public Byte getKeyDiskQuery(){
        return keyDiskQuery;
    }
    
    /**
     * 设置钥匙盘查询
     * 
     * @param keyDiskQuery 要设置的钥匙盘查询
     */
    public void setKeyDiskQuery(Byte keyDiskQuery){
        this.keyDiskQuery = keyDiskQuery;
    }
}