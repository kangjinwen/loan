package com.company.modules.instance.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询机构信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-12 01:50:43
 */
public class HousEnquiryInstitution implements Serializable {
	
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
    * 机构类型
    */
    private Byte institutionType;
    /**
    * 有无抵押
    */
    private Byte mortgage;
    /**
    * 有无查封
    */
    private Byte seizure;
    /**
    * 有无业务占用
    */
    private Byte businessOccupancy;
    /**
    * 网签占用
    */
    private Byte netSignedOccupancy;
    /**
    * 是否为最高额抵押
    */
    private Byte hochstbetragshypothek;
    /**
    * 是否有关联企业
    */
    private Byte affiliate;
    /**
    * 是否有进入法律程序的被执行信息
    */
    private Byte legalProcessPerformed;
    /**
    * 备注
    */
    private String remark;
    
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
    * 获取机构类型
    *
    * @return 机构类型
    */
    public Byte getInstitutionType(){
        return institutionType;
    }
    
    /**
     * 设置机构类型
     * 
     * @param institutionType 要设置的机构类型
     */
    public void setInstitutionType(Byte institutionType){
        this.institutionType = institutionType;
    }

    /**
    * 获取有无抵押
    *
    * @return 有无抵押
    */
    public Byte getMortgage(){
        return mortgage;
    }
    
    /**
     * 设置有无抵押
     * 
     * @param mortgage 要设置的有无抵押
     */
    public void setMortgage(Byte mortgage){
        this.mortgage = mortgage;
    }

    /**
    * 获取有无查封
    *
    * @return 有无查封
    */
    public Byte getSeizure(){
        return seizure;
    }
    
    /**
     * 设置有无查封
     * 
     * @param seizure 要设置的有无查封
     */
    public void setSeizure(Byte seizure){
        this.seizure = seizure;
    }

    /**
    * 获取有无业务占用
    *
    * @return 有无业务占用
    */
    public Byte getBusinessOccupancy(){
        return businessOccupancy;
    }
    
    /**
     * 设置有无业务占用
     * 
     * @param businessOccupancy 要设置的有无业务占用
     */
    public void setBusinessOccupancy(Byte businessOccupancy){
        this.businessOccupancy = businessOccupancy;
    }

    /**
    * 获取网签占用
    *
    * @return 网签占用
    */
    public Byte getNetSignedOccupancy(){
        return netSignedOccupancy;
    }
    
    /**
     * 设置网签占用
     * 
     * @param netSignedOccupancy 要设置的网签占用
     */
    public void setNetSignedOccupancy(Byte netSignedOccupancy){
        this.netSignedOccupancy = netSignedOccupancy;
    }

    /**
    * 获取是否为最高额抵押
    *
    * @return 是否为最高额抵押
    */
    public Byte getHochstbetragshypothek(){
        return hochstbetragshypothek;
    }
    
    /**
     * 设置是否为最高额抵押
     * 
     * @param hochstbetragshypothek 要设置的是否为最高额抵押
     */
    public void setHochstbetragshypothek(Byte hochstbetragshypothek){
        this.hochstbetragshypothek = hochstbetragshypothek;
    }

    /**
    * 获取是否有关联企业
    *
    * @return 是否有关联企业
    */
    public Byte getAffiliate(){
        return affiliate;
    }
    
    /**
     * 设置是否有关联企业
     * 
     * @param affiliate 要设置的是否有关联企业
     */
    public void setAffiliate(Byte affiliate){
        this.affiliate = affiliate;
    }

    /**
    * 获取是否有进入法律程序的被执行信息
    *
    * @return 是否有进入法律程序的被执行信息
    */
    public Byte getLegalProcessPerformed(){
        return legalProcessPerformed;
    }
    
    /**
     * 设置是否有进入法律程序的被执行信息
     * 
     * @param legalProcessPerformed 要设置的是否有进入法律程序的被执行信息
     */
    public void setLegalProcessPerformed(Byte legalProcessPerformed){
        this.legalProcessPerformed = legalProcessPerformed;
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

}