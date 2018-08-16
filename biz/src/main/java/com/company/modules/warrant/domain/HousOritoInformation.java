package com.company.modules.warrant.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 下户信息表(权证下户)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-10 03:35:36
 */
public class HousOritoInformation implements Serializable {
	
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
    private String propertyAddressId;
    /**
    * 房产详细地址
    */
    private String propertyAddress;
    /**
    * 小区名称
    */
    private String communityName;
    /**
    * 楼牌号是否与房本一致
    */
    private Byte buildingBrands;
    /**
    * 居住人含
    */
    private String livingPeople;
    /**
    * 装修状况
    */
    private Byte furnishingStatus;
    /**
    * 房屋户型(室)
    */
    private Long room;
    /**
    * 房屋户型(厅)
    */
    private Long hall;
    /**
    * 房屋户型(卫)
    */
    private Long guard;
    /**
    * 所在楼层
    */
    private Long floor;
    /**
    * 总楼层
    */
    private Long totalFloors;
    /**
    * 房屋朝向
    */
    private String housingOrientation;
    /**
    * 居住情况
    */
    private Byte livingConditions;
    /**
    * 租户签署放弃优先购买权
    */
    private Byte purchasingPower;
    /**
    * 调查员
    */
    private String investigator;
    /**
    * 调查日期
    */
    private Date surveyTime;
    /**
     * 备注
     */
    private String remark;

    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
    public String getPropertyAddressId(){
        return propertyAddressId;
    }
    
    /**
     * 设置房产地址
     * 
     * @param propertyAddressId 要设置的房产地址
     */
    public void setPropertyAddressId(String propertyAddressId){
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
    * 获取小区名称
    *
    * @return 小区名称
    */
    public String getCommunityName(){
        return communityName;
    }
    
    /**
     * 设置小区名称
     * 
     * @param communityName 要设置的小区名称
     */
    public void setCommunityName(String communityName){
        this.communityName = communityName;
    }

    /**
    * 获取楼牌号是否与房本一致
    *
    * @return 楼牌号是否与房本一致
    */
    public Byte getBuildingBrands(){
        return buildingBrands;
    }
    
    /**
     * 设置楼牌号是否与房本一致
     * 
     * @param buildingBrands 要设置的楼牌号是否与房本一致
     */
    public void setBuildingBrands(Byte buildingBrands){
        this.buildingBrands = buildingBrands;
    }

    /**
    * 获取居住人含
    *
    * @return 居住人含
    */
    public String getLivingPeople(){
        return livingPeople;
    }
    
    /**
     * 设置居住人含
     * 
     * @param livingPeople 要设置的居住人含
     */
    public void setLivingPeople(String livingPeople){
        this.livingPeople = livingPeople;
    }

    /**
    * 获取装修状况
    *
    * @return 装修状况
    */
    public Byte getFurnishingStatus(){
        return furnishingStatus;
    }
    
    /**
     * 设置装修状况
     * 
     * @param furnishingStatus 要设置的装修状况
     */
    public void setFurnishingStatus(Byte furnishingStatus){
        this.furnishingStatus = furnishingStatus;
    }

    /**
    * 获取房屋户型(室)
    *
    * @return 房屋户型(室)
    */
    public Long getRoom(){
        return room;
    }
    
    /**
     * 设置房屋户型(室)
     * 
     * @param room 要设置的房屋户型(室)
     */
    public void setRoom(Long room){
        this.room = room;
    }

    /**
    * 获取房屋户型(厅)
    *
    * @return 房屋户型(厅)
    */
    public Long getHall(){
        return hall;
    }
    
    /**
     * 设置房屋户型(厅)
     * 
     * @param hall 要设置的房屋户型(厅)
     */
    public void setHall(Long hall){
        this.hall = hall;
    }

    /**
    * 获取房屋户型(卫)
    *
    * @return 房屋户型(卫)
    */
    public Long getGuard(){
        return guard;
    }
    
    /**
     * 设置房屋户型(卫)
     * 
     * @param guard 要设置的房屋户型(卫)
     */
    public void setGuard(Long guard){
        this.guard = guard;
    }

    /**
    * 获取所在楼层
    *
    * @return 所在楼层
    */
    public Long getFloor(){
        return floor;
    }
    
    /**
     * 设置所在楼层
     * 
     * @param floor 要设置的所在楼层
     */
    public void setFloor(Long floor){
        this.floor = floor;
    }

    /**
    * 获取总楼层
    *
    * @return 总楼层
    */
    public Long getTotalFloors(){
        return totalFloors;
    }
    
    /**
     * 设置总楼层
     * 
     * @param totalFloors 要设置的总楼层
     */
    public void setTotalFloors(Long totalFloors){
        this.totalFloors = totalFloors;
    }

    /**
    * 获取房屋朝向
    *
    * @return 房屋朝向
    */
    public String getHousingOrientation(){
        return housingOrientation;
    }
    
    /**
     * 设置房屋朝向
     * 
     * @param housingOrientation 要设置的房屋朝向
     */
    public void setHousingOrientation(String housingOrientation){
        this.housingOrientation = housingOrientation;
    }

    /**
    * 获取居住情况
    *
    * @return 居住情况
    */
    public Byte getLivingConditions(){
        return livingConditions;
    }
    
    /**
     * 设置居住情况
     * 
     * @param livingConditions 要设置的居住情况
     */
    public void setLivingConditions(Byte livingConditions){
        this.livingConditions = livingConditions;
    }

    /**
    * 获取租户签署放弃优先购买权
    *
    * @return 租户签署放弃优先购买权
    */
    public Byte getPurchasingPower(){
        return purchasingPower;
    }
    
    /**
     * 设置租户签署放弃优先购买权
     * 
     * @param purchasingPower 要设置的租户签署放弃优先购买权
     */
    public void setPurchasingPower(Byte purchasingPower){
        this.purchasingPower = purchasingPower;
    }

    /**
    * 获取调查员
    *
    * @return 调查员
    */
    public String getInvestigator(){
        return investigator;
    }
    
    /**
     * 设置调查员
     * 
     * @param investigator 要设置的调查员
     */
    public void setInvestigator(String investigator){
        this.investigator = investigator;
    }

    /**
    * 获取调查日期
    *
    * @return 调查日期
    */
    public Date getSurveyTime(){
        return surveyTime;
    }
    
    /**
     * 设置调查日期
     * 
     * @param surveyTime 要设置的调查日期
     */
    public void setSurveyTime(Date surveyTime){
        this.surveyTime = surveyTime;
    }

}