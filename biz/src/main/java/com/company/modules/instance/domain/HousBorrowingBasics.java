package com.company.modules.instance.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款基本信息实体
 * @author wulb
 * @version 1.0
 * @since 2016-08-06 02:36:08
 */
public class HousBorrowingBasics implements Serializable {
	
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
    * 贷款人姓名
    */
    private String name;
    /**
    * 贷款人身份证号
    */
    private String certNumber;
    /**
    * 贷款人联系电话
    */
    private String phone;
    /**
    * 婚姻状况
    */
    private Byte marryStatus;
    /**
    * 配偶姓名
    */
    private String spouseName;
    /**
    * 配偶身份证号
    */
    private String spouseCertNumber;
    /**
    * 配偶联系电话
    */
    private String spousePhone;
    /**
    * 共借人
    */
    private String totalBorrowed;
    /**
    * 共借人身份证号
    */
    private String totalBorrowedCertNumber;
    /**
    * 居住地
    */
    private Long residentialAddressId;
    /**
    * 居住地详细地址
    */
    private String residentialAddress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 贷款人父亲姓名
     */
    private String fatherName;
    /**
     * 贷款人母亲姓名
     */
    private String motherName;
    /**
     * 贷款人父亲身份证号
     */
    private String fatherNumber;
    /**
     * 贷款人母亲身份证号
     */
    private String motherNumber;
    /**
     * 贷款人性别
     */
     private Byte sex;
     /**
      * 贷款人年龄
      */
     private String age;     
    
    
    public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherNumber() {
		return fatherNumber;
	}

	public void setFatherNumber(String fatherNumber) {
		this.fatherNumber = fatherNumber;
	}

	public String getMotherNumber() {
		return motherNumber;
	}

	public void setMotherNumber(String motherNumber) {
		this.motherNumber = motherNumber;
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
    * 获取贷款人姓名
    *
    * @return 贷款人姓名
    */
    public String getName(){
        return name;
    }
    
    /**
     * 设置贷款人姓名
     * 
     * @param name 要设置的贷款人姓名
     */
    public void setName(String name){
        this.name = name;
    }

    /**
    * 获取贷款人身份证号
    *
    * @return 贷款人身份证号
    */
    public String getCertNumber(){
        return certNumber;
    }
    
    /**
     * 设置贷款人身份证号
     * 
     * @param certNumber 要设置的贷款人身份证号
     */
    public void setCertNumber(String certNumber){
        this.certNumber = certNumber;
    }

    /**
    * 获取贷款人联系电话
    *
    * @return 贷款人联系电话
    */
    public String getPhone(){
        return phone;
    }
    
    /**
     * 设置贷款人联系电话
     * 
     * @param phone 要设置的贷款人联系电话
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
    * 获取婚姻状况
    *
    * @return 婚姻状况
    */
    public Byte getMarryStatus(){
        return marryStatus;
    }
    
    /**
     * 设置婚姻状况
     * 
     * @param marryStatus 要设置的婚姻状况
     */
    public void setMarryStatus(Byte marryStatus){
        this.marryStatus = marryStatus;
    }

    /**
    * 获取配偶姓名
    *
    * @return 配偶姓名
    */
    public String getSpouseName(){
        return spouseName;
    }
    
    /**
     * 设置配偶姓名
     * 
     * @param spouseName 要设置的配偶姓名
     */
    public void setSpouseName(String spouseName){
        this.spouseName = spouseName;
    }

    /**
    * 获取配偶身份证号
    *
    * @return 配偶身份证号
    */
    public String getSpouseCertNumber(){
        return spouseCertNumber;
    }
    
    /**
     * 设置配偶身份证号
     * 
     * @param spouseCertNumber 要设置的配偶身份证号
     */
    public void setSpouseCertNumber(String spouseCertNumber){
        this.spouseCertNumber = spouseCertNumber;
    }

    /**
    * 获取配偶联系电话
    *
    * @return 配偶联系电话
    */
    public String getSpousePhone(){
        return spousePhone;
    }
    
    /**
     * 设置配偶联系电话
     * 
     * @param spousePhone 要设置的配偶联系电话
     */
    public void setSpousePhone(String spousePhone){
        this.spousePhone = spousePhone;
    }

    /**
    * 获取共借人
    *
    * @return 共借人
    */
    public String getTotalBorrowed(){
        return totalBorrowed;
    }
    
    /**
     * 设置共借人
     * 
     * @param totalBorrowed 要设置的共借人
     */
    public void setTotalBorrowed(String totalBorrowed){
        this.totalBorrowed = totalBorrowed;
    }

    /**
    * 获取共借人身份证号
    *
    * @return 共借人身份证号
    */
    public String getTotalBorrowedCertNumber(){
        return totalBorrowedCertNumber;
    }
    
    /**
     * 设置共借人身份证号
     * 
     * @param totalBorrowedCertNumber 要设置的共借人身份证号
     */
    public void setTotalBorrowedCertNumber(String totalBorrowedCertNumber){
        this.totalBorrowedCertNumber = totalBorrowedCertNumber;
    }

    /**
    * 获取居住地
    *
    * @return 居住地
    */
    public Long getResidentialAddressId(){
        return residentialAddressId;
    }
    
    /**
     * 设置居住地
     * 
     * @param residentialAddressId 要设置的居住地
     */
    public void setResidentialAddressId(Long residentialAddressId){
        this.residentialAddressId = residentialAddressId;
    }

    /**
    * 获取居住地详细地址
    *
    * @return 居住地详细地址
    */
    public String getResidentialAddress(){
        return residentialAddress;
    }
    
    /**
     * 设置居住地详细地址
     * 
     * @param residentialAddress 要设置的居住地详细地址
     */
    public void setResidentialAddress(String residentialAddress){
        this.residentialAddress = residentialAddress;
    }

    /**
    * 获取备注
    *
    * @return 备注
    */
	public String getRemark() {
		return remark;
	}

    /**
     * 设置备注
     * 
     * @param residentialAddress 要设置的备注
     */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}