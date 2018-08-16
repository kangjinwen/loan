package com.company.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户基本信息实体
 * @author wgc
 * @version 1.0
 * @since 2014-12-01
 */
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private Long id;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 性别
     */
    private Byte sex;
    /**
     * 职业
     */
    private String vocation;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 国籍
     */
    private String nationality;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 身份证件类型
     */
    private Byte certType;
    /**
     * 证件号码
     */
    private String certNumber;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 固定电话
     */
    private String fixedPhone;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 地区ID
     */
    private Long areaId;
    /**
     * 现居住详细地址
     */
    private String liveAddress;
    /**
     * 户籍详细地址
     */
    private String householdAddress;
    /**
     * 学历 博士1，硕士2，本科3，专科4，高中及以下5
     */
    private Byte education;
    /**
     * 工作状况 在职1，离职2，退休3，自营4
     */
    private Byte workStatus;
    /**
     * 工作单位名称
     */
    private String company;
    /**
     * 工作单位名称
     */
    private String companyAddr;
    /**
     * 工作单位性质 国有企业1，私营企业2，外资企业3，个体经济4，其他5
     */
    private Byte companyPro;
    /**
     * 工作单位电话
     */
    private String companyPhone;
    /**
     * 工作职位
     */
    private String position;
    /**
     * 工作年限
     */
    private Byte workLife;
    /**
     * 个人年收入
     */
    private Byte yearsIncome;
    /**
     * 客户类型 工薪阶层1，个体工商户2，私营业主3，政府机关4，其他5   
     */
    private Byte type;
    /**
     * 客户信用评分
     */
    private Long creditScore;
    /**
     * 婚姻状况：未婚1，已婚2，离异3，丧偶4
     */
    private Byte marryStatus;
    /**
     * 描述
     */
    private String remark;
    /**
     * 客户状态 0正常用户 1黑名单用户 -1删除状态
     */
    private Byte status;
    /**
     * 贷款次数
     */
    private Byte loans;
    /**
     * 拒贷次数
     */
    private Byte refusings;
    /**
     * 还款状态  0-还清、1-还款中、2-逾期
     */
    private Byte repaymentStatus;
    
    private String uuid; // 用户唯一标识
    
    private Long creator;//錄入人
    
    private Date certValidTime; //证件有效期
    
    private String creatorName;
    
    private Byte   isLocalHouse;      //本人本地名下房产（有、无）
    private Byte   liveState;         //居住情况
    private String otherCase;         //其它说明
    private String liveZipCode;       //现住址邮编
    private String householdZipCode;  //户籍地邮编
    private String companyZipCode;    //公司地址邮编
    private String department;        //所在部门
    private Date   inTime;            //入职时间
    private String customerManager;   //客户经理
    /**
     * 风险等级  1-正常  2-关注  3-次级  4-可疑  5-损失
     */
    private Byte	riskLevel;	
    
    private Date createTime; //客户创建时间
    
    private Byte healthy; //健康状况
    private Byte spouseIskown;//配偶是否知晓
    
    private Byte isHaveChildren;//是否有子女
    private String emergencyContactNumber;//紧急联系号码
    private Byte isSelfSupport;//公司是否自营
    
    
    
    public Byte getHealthy() {
		return healthy;
	}

	public void setHealthy(Byte healthy) {
		this.healthy = healthy;
	}

	public Byte getSpouseIskown() {
		return spouseIskown;
	}

	public void setSpouseIskown(Byte spouseIskown) {
		this.spouseIskown = spouseIskown;
	}

	public Byte getIsHaveChildren() {
		return isHaveChildren;
	}

	public void setIsHaveChildren(Byte isHaveChildren) {
		this.isHaveChildren = isHaveChildren;
	}

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public Byte getIsSelfSupport() {
		return isSelfSupport;
	}

	public void setIsSelfSupport(Byte isSelfSupport) {
		this.isSelfSupport = isSelfSupport;
	}

	/**
     * 获取主键
     * 
     * @return 主键
     */
    public Long getId(){
        return id;
    }
    
    /**
     * 设置主键
     * 
     * @param id 要设置的主键
     */
    public void setId(Long id){
        this.id = id;
    }
    /**
     * 获取真实姓名
     * 
     * @return 真实姓名
     */
    public String getName(){
        return name;
    }
    
    /**
     * 设置真实姓名
     * 
     * @param name 要设置的真实姓名
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * 获取性别
     * 
     * @return 性别
     */
    public Byte getSex(){
        return sex;
    }
    
    /**
     * 设置性别
     * 
     * @param sex 要设置的性别
     */
    public void setSex(Byte sex){
        this.sex = sex;
    }
    /**
     * 获取职业
     * 
     * @return 职业
     */
    public String getVocation(){
        return vocation;
    }
    
    /**
     * 设置职业
     * 
     * @param vocation 要设置的职业
     */
    public void setVocation(String vocation){
        this.vocation = vocation;
    }
    /**
     * 获取出生日期
     * 
     * @return 出生日期
     */
    public Date getBirthday(){
        return birthday;
    }
    
    /**
     * 设置出生日期
     * 
     * @param birthday 要设置的出生日期
     */
    public void setBirthday(Date birthday){
        if(birthday!=null){
            java.sql.Date birthday_ymd=new java.sql.Date(birthday.getTime());
            this.birthday = birthday_ymd;
        }
    }
    /**
     * 获取国籍
     * 
     * @return 国籍
     */
    public String getNationality(){
        return nationality;
    }
    
    /**
     * 设置国籍
     * 
     * @param nationality 要设置的国籍
     */
    public void setNationality(String nationality){
        this.nationality = nationality;
    }
    /**
     * 获取籍贯
     * 
     * @return 籍贯
     */
    public String getNativePlace(){
        return nativePlace;
    }
    
    /**
     * 设置籍贯
     * 
     * @param nativePlace 要设置的籍贯
     */
    public void setNativePlace(String nativePlace){
        this.nativePlace = nativePlace;
    }
    /**
     * 获取身份证件类型
     * 
     * @return 身份证件类型
     */
    public Byte getCertType(){
        return certType;
    }
    
    /**
     * 设置身份证件类型
     * 
     * @param certType 要设置的身份证件类型
     */
    public void setCertType(Byte certType){
        this.certType = certType;
    }
    /**
     * 获取证件号码
     * 
     * @return 证件号码
     */
    public String getCertNumber(){
        return certNumber;
    }
    
    /**
     * 设置证件号码
     * 
     * @param certNumber 要设置的证件号码
     */
    public void setCertNumber(String certNumber){
        this.certNumber = certNumber;
    }
    /**
     * 获取手机号码
     * 
     * @return 手机号码
     */
    public String getMobile(){
        return mobile;
    }
    
    /**
     * 设置手机号码
     * 
     * @param mobile 要设置的手机号码
     */
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    /**
     * 获取固定电话
     * 
     * @return 固定电话
     */
    public String getFixedPhone(){
        return fixedPhone;
    }
    
    /**
     * 设置固定电话
     * 
     * @param fixedPhone 要设置的固定电话
     */
    public void setFixedPhone(String fixedPhone){
        this.fixedPhone = fixedPhone;
    }
    /**
     * 获取电子邮件
     * 
     * @return 电子邮件
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * 设置电子邮件
     * 
     * @param email 要设置的电子邮件
     */
    public void setEmail(String email){
        this.email = email;
    }
    /**
     * 获取地区ID
     * 
     * @return 地区ID
     */
    public Long getAreaId(){
        return areaId;
    }
    
    /**
     * 设置地区ID
     * 
     * @param areaId 要设置的地区ID
     */
    public void setAreaId(Long areaId){
        this.areaId = areaId;
    }
    /**
     * 获取现居住详细地址
     * 
     * @return 现居住详细地址
     */
    public String getLiveAddress(){
        return liveAddress;
    }
    
    /**
     * 设置现居住详细地址
     * 
     * @param liveAddress 要设置的现居住详细地址
     */
    public void setLiveAddress(String liveAddress){
        this.liveAddress = liveAddress;
    }
    /**
     * 获取户籍详细地址
     * 
     * @return 户籍详细地址
     */
    public String getHouseholdAddress(){
        return householdAddress;
    }
    
    /**
     * 设置户籍详细地址
     * 
     * @param householdAddress 要设置的户籍详细地址
     */
    public void setHouseholdAddress(String householdAddress){
        this.householdAddress = householdAddress;
    }
    /**
     * 获取学历 博士1，硕士2，本科3，专科4，高中及以下5
     * 
     * @return 学历 博士1，硕士2，本科3，专科4，高中及以下5
     */
    public Byte getEducation(){
        return education;
    }
    
    /**
     * 设置学历 博士1，硕士2，本科3，专科4，高中及以下5
     * 
     * @param education 要设置的学历 博士1，硕士2，本科3，专科4，高中及以下5
     */
    public void setEducation(Byte education){
        this.education = education;
    }
    /**
     * 获取工作状况 在职1，离职2，退休3，自营4
     * 
     * @return 工作状况 在职1，离职2，退休3，自营4
     */
    public Byte getWorkStatus(){
        return workStatus;
    }
    
    /**
     * 设置工作状况 在职1，离职2，退休3，自营4
     * 
     * @param workStatus 要设置的工作状况 在职1，离职2，退休3，自营4
     */
    public void setWorkStatus(Byte workStatus){
        this.workStatus = workStatus;
    }
    /**
     * 获取工作单位名称
     * 
     * @return 工作单位名称
     */
    public String getCompany(){
        return company;
    }
    
    /**
     * 设置工作单位名称
     * 
     * @param company 要设置的工作单位名称
     */
    public void setCompany(String company){
        this.company = company;
    }
    /**
     * 获取工作单位名称
     * 
     * @return 工作单位名称
     */
    public String getCompanyAddr(){
        return companyAddr;
    }
    
    /**
     * 设置工作单位名称
     * 
     * @param companyAddr 要设置的工作单位名称
     */
    public void setCompanyAddr(String companyAddr){
        this.companyAddr = companyAddr;
    }
    /**
     * 获取工作单位性质 国有企业1，私营企业2，外资企业3，个体经济4，其他5
     * 
     * @return 工作单位性质 国有企业1，私营企业2，外资企业3，个体经济4，其他5
     */
    public Byte getCompanyPro(){
        return companyPro;
    }
    
    /**
     * 设置工作单位性质 国有企业1，私营企业2，外资企业3，个体经济4，其他5
     * 
     * @param companyPro 要设置的工作单位性质 国有企业1，私营企业2，外资企业3，个体经济4，其他5
     */
    public void setCompanyPro(Byte companyPro){
        this.companyPro = companyPro;
    }
    /**
     * 获取工作单位电话
     * 
     * @return 工作单位电话
     */
    public String getCompanyPhone(){
        return companyPhone;
    }
    
    /**
     * 设置工作单位电话
     * 
     * @param companyPhone 要设置的工作单位电话
     */
    public void setCompanyPhone(String companyPhone){
        this.companyPhone = companyPhone;
    }
    /**
     * 获取工作职位
     * 
     * @return 工作职位
     */
    public String getPosition(){
        return position;
    }
    
    /**
     * 设置工作职位
     * 
     * @param position 要设置的工作职位
     */
    public void setPosition(String position){
        this.position = position;
    }
    /**
     * 获取工作年限
     * 
     * @return 工作年限
     */
    public Byte getWorkLife(){
        return workLife;
    }
    
    /**
     * 设置工作年限
     * 
     * @param workLife 要设置的工作年限
     */
    public void setWorkLife(Byte workLife){
        this.workLife = workLife;
    }
    /**
     * 获取个人年收入
     * 
     * @return 个人年收入
     */
    public Byte getYearsIncome(){
        return yearsIncome;
    }
    
    /**
     * 设置个人年收入
     * 
     * @param yearsIncome 要设置的个人年收入
     */
    public void setYearsIncome(Byte yearsIncome){
        this.yearsIncome = yearsIncome;
    }
    /**
     * 获取客户类型 工薪阶层1，个体工商户2，私营业主3，政府机关4，其他5   
     * 
     * @return 客户类型 工薪阶层1，个体工商户2，私营业主3，政府机关4，其他5   
     */
    public Byte getType(){
        return type;
    }
    
    /**
     * 设置客户类型 工薪阶层1，个体工商户2，私营业主3，政府机关4，其他5   
     * 
     * @param type 要设置的客户类型 工薪阶层1，个体工商户2，私营业主3，政府机关4，其他5   
     */
    public void setType(Byte type){
        this.type = type;
    }
    /**
     * 获取客户信用评分
     * 
     * @return 客户信用评分
     */
    public Long getCreditScore(){
        return creditScore;
    }
    
    /**
     * 设置客户信用评分
     * 
     * @param creditScore 要设置的客户信用评分
     */
    public void setCreditScore(Long creditScore){
        this.creditScore = creditScore;
    }
    /**
     * 获取婚姻状况：未婚1，已婚2，离异3，丧偶4
     * 
     * @return 婚姻状况：未婚1，已婚2，离异3，丧偶4
     */
    public Byte getMarryStatus(){
        return marryStatus;
    }
    
    /**
     * 设置婚姻状况：未婚1，已婚2，离异3，丧偶4
     * 
     * @param marryStatus 要设置的婚姻状况：未婚1，已婚2，离异3，丧偶4
     */
    public void setMarryStatus(Byte marryStatus){
        this.marryStatus = marryStatus;
    }
    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getRemark(){
        return remark;
    }
    
    /**
     * 设置描述
     * 
     * @param remark 要设置的描述
     */
    public void setRemark(String remark){
        this.remark = remark;
    }
    /**
     * 获取客户状态 0正常用户 1黑名单用户 -1删除状态
     * 
     * @return 客户状态 0正常用户 1黑名单用户 -1删除状态
     */
    public Byte getStatus(){
        return status;
    }
    
    /**
     * 设置客户状态 0正常用户 1黑名单用户 -1删除状态
     * 
     * @param status 要设置的客户状态 0正常用户 1黑名单用户 -1删除状态
     */
    public void setStatus(Byte status){
        this.status = status;
    }

	public Byte getLoans() {
		return loans;
	}

	public void setLoans(Byte loans) {
		this.loans = loans;
	}

	public Byte getRefusings() {
		return refusings;
	}

	public void setRefusings(Byte refusings) {
		this.refusings = refusings;
	}

	public Byte getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(Byte repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCertValidTime() {
		return certValidTime;
	}

	public void setCertValidTime(Date certValidTime) {
        if(certValidTime!=null){
            java.sql.Date certValidTime_ymd=new java.sql.Date(certValidTime.getTime());
            this.certValidTime = certValidTime_ymd;
        }
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Byte getIsLocalHouse() {
		return isLocalHouse;
	}

	public void setIsLocalHouse(Byte isLocalHouse) {
		this.isLocalHouse = isLocalHouse;
	}

	public Byte getLiveState() {
		return liveState;
	}

	public void setLiveState(Byte liveState) {
		this.liveState = liveState;
	}

	public String getOtherCase() {
		return otherCase;
	}

	public void setOtherCase(String otherCase) {
		this.otherCase = otherCase;
	}

	public String getLiveZipCode() {
		return liveZipCode;
	}

	public void setLiveZipCode(String liveZipCode) {
		this.liveZipCode = liveZipCode;
	}

	public String getHouseholdZipCode() {
		return householdZipCode;
	}

	public void setHouseholdZipCode(String householdZipCode) {
		this.householdZipCode = householdZipCode;
	}

	public String getCompanyZipCode() {
		return companyZipCode;
	}

	public void setCompanyZipCode(String companyZipCode) {
		this.companyZipCode = companyZipCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public String getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	public Byte getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(Byte riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
    
}


