package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户管理实体
 * @author huy
 * @version 1.0
 * @since 2016-12-08 17:53:10
 */
public class PubCustomer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
    * id
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
    * 证件有效期
    */
    private Date certValidTime;
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
    private Integer areaId;
    /**
    * 现居住详细地址
    */
    private String liveAddress;
    /**
    * 户籍详细地址
    */
    private String householdAddress;
    /**
    * 1高中及以下 2大专 3本科 4硕士及以上 5其他
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
    * 工作单位地址
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
    private Integer creditScore;
    /**
    * 婚姻状况：未婚1，已婚2，离异3，丧偶4
    */
    private Byte marryStatus;
    /**
    * 描述
    */
    private String remark;
    /**
    * 客户状态 0正常用户 1黑名单用户
    */
    private Byte status;
    /**
    * 贷款次数
    */
    private Integer loans;
    /**
    * 拒贷次数
    */
    private Integer refusings;
    /**
    * 还款中0，结清1，逾期2
    */
    private Integer repaymentStatus;
    /**
    * 用户唯一UUID
    */
    private String uuid;
    /**
    * 创建者（录入人）用于权限控制
    */
    private Integer creator;
    /**
    * 创建人
    */
    private String creatorName;
    /**
    * 本人本地名下房产 0无 1有
    */
    private Byte isLocalHouse;
    /**
    * 居住情况:0自有住房 1租凭2与亲属同住3公司宿舍4其它
    */
    private Byte liveState;
    /**
    * 其它说明
    */
    private String otherCase;
    /**
    * 现住址邮编
    */
    private String liveZipCode;
    /**
    * 户籍地邮编
    */
    private String householdZipCode;
    /**
    * 公司地址邮编
    */
    private String companyZipCode;
    /**
    * 所在部门
    */
    private String department;
    /**
    * 入职时间
    */
    private Date inTime;
    /**
    * 客户经理
    */
    private String customerManager;
    /**
    * 风险等级
    */
    private Byte riskLevel;
    /**
    * 照片
    */
    private Integer photoId;
    /**
    * 车产情况
    */
    private Byte carStatus;
    /**
    * 户籍所在地区ID
    */
    private Integer rdHouseholdaddressid;
    /**
    * 户籍详细地址
    */
    private String rdHouseholddetailaddress;
    /**
    * 健康状况：0-良好，1-一般，2-较差
    */
    private Byte healthy;
    /**
    * 配偶是否知晓：0-是，1-否
    */
    private Byte spouseIskown;
    /**
    * 是否有子女：0-是，1-否
    */
    private Byte isHaveChildren;
    /**
    * 紧急联系电话
    */
    private String emergencyContactNumber;
    /**
    * 公司是否自营：0-是，1-否
    */
    private Byte isSelfSupport;
    /**
    * 
    */
    private Date createTime;
    /**
    * 业务员
    */
    private String salesman;
    /**
    * 年龄
    */
    private Integer age;
    /**
    * 子女人数
    */
    private Integer childrenNum;
    /**
    * 供养人数
    */
    private Integer feedNum;
    /**
    * 户籍地址ID
    */
    private Integer householdAreaId;
    /**
    * 家庭月支出
    */
    private BigDecimal familyMonthExpenses;
    /**
    * 现居住地址时间
    */
    private Date liveTime;
    /**
    * 来本市时间
    */
    private Date comeCityDate;
    /**
    * 微信
    */
    private String weixin;
    /**
    * 微博
    */
    private String weibo;
    /**
    * qq
    */
    private String qq;
    /**
    * 客户来源
    */
    private String customerSource;
    /**
    * 职位类型
    */
    private Integer positionType;
    /**
    * 职位级别
    */
    private Integer positionLevel;
    /**
    * 所属行业
    */
    private Byte positionIndustry;
    /**
    * 前单位名称
    */
    private String lastCompany;
    /**
    * 工资发放形式
    */
    private Byte wagesType;
    /**
    * 每月基本薪金
    */
    private BigDecimal salary;
    /**
    * 其他收入
    */
    private BigDecimal otherIn;
    /**
    * 每月发薪日
    */
    private String grantDay;
    /**
    * 经营营类型
    */
    private Integer managementType;
    /**
    * 经营类型后缀名
    */
    private Integer managementName;
    /**
    * 企业成立时间
    */
    private Date companyBuildTime;
    /**
    * 股份占比
    */
    private BigDecimal shareScale;
    /**
    * 员工人数
    */
    private Integer employeeNum;
    /**
    * 经营场所类型
    */
    private Integer managementAddrType;
    /**
    * 单位地址ID
    */
    private Integer companyAreaId;
    /**
    * 团队经理
    */
    private String teamManager;
    
    
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
        this.birthday = birthday;
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
    * 获取证件有效期
    *
    * @return 证件有效期
    */
    public Date getCertValidTime(){
        return certValidTime;
    }
    
    /**
     * 设置证件有效期
     * 
     * @param certValidTime 要设置的证件有效期
     */
    public void setCertValidTime(Date certValidTime){
        this.certValidTime = certValidTime;
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
    public Integer getAreaId(){
        return areaId;
    }
    
    /**
     * 设置地区ID
     * 
     * @param areaId 要设置的地区ID
     */
    public void setAreaId(Integer areaId){
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
    * 获取1高中及以下 2大专 3本科 4硕士及以上 5其他
    *
    * @return 1高中及以下 2大专 3本科 4硕士及以上 5其他
    */
    public Byte getEducation(){
        return education;
    }
    
    /**
     * 设置1高中及以下 2大专 3本科 4硕士及以上 5其他
     * 
     * @param education 要设置的1高中及以下 2大专 3本科 4硕士及以上 5其他
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
    * 获取工作单位地址
    *
    * @return 工作单位地址
    */
    public String getCompanyAddr(){
        return companyAddr;
    }
    
    /**
     * 设置工作单位地址
     * 
     * @param companyAddr 要设置的工作单位地址
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
    public Integer getCreditScore(){
        return creditScore;
    }
    
    /**
     * 设置客户信用评分
     * 
     * @param creditScore 要设置的客户信用评分
     */
    public void setCreditScore(Integer creditScore){
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
    * 获取客户状态 0正常用户 1黑名单用户
    *
    * @return 客户状态 0正常用户 1黑名单用户
    */
    public Byte getStatus(){
        return status;
    }
    
    /**
     * 设置客户状态 0正常用户 1黑名单用户
     * 
     * @param status 要设置的客户状态 0正常用户 1黑名单用户
     */
    public void setStatus(Byte status){
        this.status = status;
    }

    /**
    * 获取贷款次数
    *
    * @return 贷款次数
    */
    public Integer getLoans(){
        return loans;
    }
    
    /**
     * 设置贷款次数
     * 
     * @param loans 要设置的贷款次数
     */
    public void setLoans(Integer loans){
        this.loans = loans;
    }

    /**
    * 获取拒贷次数
    *
    * @return 拒贷次数
    */
    public Integer getRefusings(){
        return refusings;
    }
    
    /**
     * 设置拒贷次数
     * 
     * @param refusings 要设置的拒贷次数
     */
    public void setRefusings(Integer refusings){
        this.refusings = refusings;
    }

    /**
    * 获取还款中0，结清1，逾期2
    *
    * @return 还款中0，结清1，逾期2
    */
    public Integer getRepaymentStatus(){
        return repaymentStatus;
    }
    
    /**
     * 设置还款中0，结清1，逾期2
     * 
     * @param repaymentStatus 要设置的还款中0，结清1，逾期2
     */
    public void setRepaymentStatus(Integer repaymentStatus){
        this.repaymentStatus = repaymentStatus;
    }

    /**
    * 获取用户唯一UUID
    *
    * @return 用户唯一UUID
    */
    public String getUuid(){
        return uuid;
    }
    
    /**
     * 设置用户唯一UUID
     * 
     * @param uuid 要设置的用户唯一UUID
     */
    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    /**
    * 获取创建者（录入人）用于权限控制
    *
    * @return 创建者（录入人）用于权限控制
    */
    public Integer getCreator(){
        return creator;
    }
    
    /**
     * 设置创建者（录入人）用于权限控制
     * 
     * @param creator 要设置的创建者（录入人）用于权限控制
     */
    public void setCreator(Integer creator){
        this.creator = creator;
    }

    /**
    * 获取创建人
    *
    * @return 创建人
    */
    public String getCreatorName(){
        return creatorName;
    }
    
    /**
     * 设置创建人
     * 
     * @param creatorName 要设置的创建人
     */
    public void setCreatorName(String creatorName){
        this.creatorName = creatorName;
    }

    /**
    * 获取本人本地名下房产 0无 1有
    *
    * @return 本人本地名下房产 0无 1有
    */
    public Byte getIsLocalHouse(){
        return isLocalHouse;
    }
    
    /**
     * 设置本人本地名下房产 0无 1有
     * 
     * @param isLocalHouse 要设置的本人本地名下房产 0无 1有
     */
    public void setIsLocalHouse(Byte isLocalHouse){
        this.isLocalHouse = isLocalHouse;
    }

    /**
    * 获取居住情况:0自有住房 1租凭2与亲属同住3公司宿舍4其它
    *
    * @return 居住情况:0自有住房 1租凭2与亲属同住3公司宿舍4其它
    */
    public Byte getLiveState(){
        return liveState;
    }
    
    /**
     * 设置居住情况:0自有住房 1租凭2与亲属同住3公司宿舍4其它
     * 
     * @param liveState 要设置的居住情况:0自有住房 1租凭2与亲属同住3公司宿舍4其它
     */
    public void setLiveState(Byte liveState){
        this.liveState = liveState;
    }

    /**
    * 获取其它说明
    *
    * @return 其它说明
    */
    public String getOtherCase(){
        return otherCase;
    }
    
    /**
     * 设置其它说明
     * 
     * @param otherCase 要设置的其它说明
     */
    public void setOtherCase(String otherCase){
        this.otherCase = otherCase;
    }

    /**
    * 获取现住址邮编
    *
    * @return 现住址邮编
    */
    public String getLiveZipCode(){
        return liveZipCode;
    }
    
    /**
     * 设置现住址邮编
     * 
     * @param liveZipCode 要设置的现住址邮编
     */
    public void setLiveZipCode(String liveZipCode){
        this.liveZipCode = liveZipCode;
    }

    /**
    * 获取户籍地邮编
    *
    * @return 户籍地邮编
    */
    public String getHouseholdZipCode(){
        return householdZipCode;
    }
    
    /**
     * 设置户籍地邮编
     * 
     * @param householdZipCode 要设置的户籍地邮编
     */
    public void setHouseholdZipCode(String householdZipCode){
        this.householdZipCode = householdZipCode;
    }

    /**
    * 获取公司地址邮编
    *
    * @return 公司地址邮编
    */
    public String getCompanyZipCode(){
        return companyZipCode;
    }
    
    /**
     * 设置公司地址邮编
     * 
     * @param companyZipCode 要设置的公司地址邮编
     */
    public void setCompanyZipCode(String companyZipCode){
        this.companyZipCode = companyZipCode;
    }

    /**
    * 获取所在部门
    *
    * @return 所在部门
    */
    public String getDepartment(){
        return department;
    }
    
    /**
     * 设置所在部门
     * 
     * @param department 要设置的所在部门
     */
    public void setDepartment(String department){
        this.department = department;
    }

    /**
    * 获取入职时间
    *
    * @return 入职时间
    */
    public Date getInTime(){
        return inTime;
    }
    
    /**
     * 设置入职时间
     * 
     * @param inTime 要设置的入职时间
     */
    public void setInTime(Date inTime){
        this.inTime = inTime;
    }

    /**
    * 获取客户经理
    *
    * @return 客户经理
    */
    public String getCustomerManager(){
        return customerManager;
    }
    
    /**
     * 设置客户经理
     * 
     * @param customerManager 要设置的客户经理
     */
    public void setCustomerManager(String customerManager){
        this.customerManager = customerManager;
    }

    /**
    * 获取风险等级
    *
    * @return 风险等级
    */
    public Byte getRiskLevel(){
        return riskLevel;
    }
    
    /**
     * 设置风险等级
     * 
     * @param riskLevel 要设置的风险等级
     */
    public void setRiskLevel(Byte riskLevel){
        this.riskLevel = riskLevel;
    }

    /**
    * 获取照片
    *
    * @return 照片
    */
    public Integer getPhotoId(){
        return photoId;
    }
    
    /**
     * 设置照片
     * 
     * @param photoId 要设置的照片
     */
    public void setPhotoId(Integer photoId){
        this.photoId = photoId;
    }

    /**
    * 获取车产情况
    *
    * @return 车产情况
    */
    public Byte getCarStatus(){
        return carStatus;
    }
    
    /**
     * 设置车产情况
     * 
     * @param carStatus 要设置的车产情况
     */
    public void setCarStatus(Byte carStatus){
        this.carStatus = carStatus;
    }

    /**
    * 获取户籍所在地区ID
    *
    * @return 户籍所在地区ID
    */
    public Integer getRdHouseholdaddressid(){
        return rdHouseholdaddressid;
    }
    
    /**
     * 设置户籍所在地区ID
     * 
     * @param rdHouseholdaddressid 要设置的户籍所在地区ID
     */
    public void setRdHouseholdaddressid(Integer rdHouseholdaddressid){
        this.rdHouseholdaddressid = rdHouseholdaddressid;
    }

    /**
    * 获取户籍详细地址
    *
    * @return 户籍详细地址
    */
    public String getRdHouseholddetailaddress(){
        return rdHouseholddetailaddress;
    }
    
    /**
     * 设置户籍详细地址
     * 
     * @param rdHouseholddetailaddress 要设置的户籍详细地址
     */
    public void setRdHouseholddetailaddress(String rdHouseholddetailaddress){
        this.rdHouseholddetailaddress = rdHouseholddetailaddress;
    }

    /**
    * 获取健康状况：0-良好，1-一般，2-较差
    *
    * @return 健康状况：0-良好，1-一般，2-较差
    */
    public Byte getHealthy(){
        return healthy;
    }
    
    /**
     * 设置健康状况：0-良好，1-一般，2-较差
     * 
     * @param healthy 要设置的健康状况：0-良好，1-一般，2-较差
     */
    public void setHealthy(Byte healthy){
        this.healthy = healthy;
    }

    /**
    * 获取配偶是否知晓：0-是，1-否
    *
    * @return 配偶是否知晓：0-是，1-否
    */
    public Byte getSpouseIskown(){
        return spouseIskown;
    }
    
    /**
     * 设置配偶是否知晓：0-是，1-否
     * 
     * @param spouseIskown 要设置的配偶是否知晓：0-是，1-否
     */
    public void setSpouseIskown(Byte spouseIskown){
        this.spouseIskown = spouseIskown;
    }

    /**
    * 获取是否有子女：0-是，1-否
    *
    * @return 是否有子女：0-是，1-否
    */
    public Byte getIsHaveChildren(){
        return isHaveChildren;
    }
    
    /**
     * 设置是否有子女：0-是，1-否
     * 
     * @param isHaveChildren 要设置的是否有子女：0-是，1-否
     */
    public void setIsHaveChildren(Byte isHaveChildren){
        this.isHaveChildren = isHaveChildren;
    }

    /**
    * 获取紧急联系电话
    *
    * @return 紧急联系电话
    */
    public String getEmergencyContactNumber(){
        return emergencyContactNumber;
    }
    
    /**
     * 设置紧急联系电话
     * 
     * @param emergencyContactNumber 要设置的紧急联系电话
     */
    public void setEmergencyContactNumber(String emergencyContactNumber){
        this.emergencyContactNumber = emergencyContactNumber;
    }

    /**
    * 获取公司是否自营：0-是，1-否
    *
    * @return 公司是否自营：0-是，1-否
    */
    public Byte getIsSelfSupport(){
        return isSelfSupport;
    }
    
    /**
     * 设置公司是否自营：0-是，1-否
     * 
     * @param isSelfSupport 要设置的公司是否自营：0-是，1-否
     */
    public void setIsSelfSupport(Byte isSelfSupport){
        this.isSelfSupport = isSelfSupport;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置
     * 
     * @param createTime 要设置的
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取业务员
    *
    * @return 业务员
    */
    public String getSalesman(){
        return salesman;
    }
    
    /**
     * 设置业务员
     * 
     * @param salesman 要设置的业务员
     */
    public void setSalesman(String salesman){
        this.salesman = salesman;
    }

    /**
    * 获取年龄
    *
    * @return 年龄
    */
    public Integer getAge(){
        return age;
    }
    
    /**
     * 设置年龄
     * 
     * @param age 要设置的年龄
     */
    public void setAge(Integer age){
        this.age = age;
    }

    /**
    * 获取子女人数
    *
    * @return 子女人数
    */
    public Integer getChildrenNum(){
        return childrenNum;
    }
    
    /**
     * 设置子女人数
     * 
     * @param childrenNum 要设置的子女人数
     */
    public void setChildrenNum(Integer childrenNum){
        this.childrenNum = childrenNum;
    }

    /**
    * 获取供养人数
    *
    * @return 供养人数
    */
    public Integer getFeedNum(){
        return feedNum;
    }
    
    /**
     * 设置供养人数
     * 
     * @param feedNum 要设置的供养人数
     */
    public void setFeedNum(Integer feedNum){
        this.feedNum = feedNum;
    }

    /**
    * 获取户籍地址ID
    *
    * @return 户籍地址ID
    */
    public Integer getHouseholdAreaId(){
        return householdAreaId;
    }
    
    /**
     * 设置户籍地址ID
     * 
     * @param householdAreaId 要设置的户籍地址ID
     */
    public void setHouseholdAreaId(Integer householdAreaId){
        this.householdAreaId = householdAreaId;
    }

    /**
    * 获取家庭月支出
    *
    * @return 家庭月支出
    */
    public BigDecimal getFamilyMonthExpenses(){
        return familyMonthExpenses;
    }
    
    /**
     * 设置家庭月支出
     * 
     * @param familyMonthExpenses 要设置的家庭月支出
     */
    public void setFamilyMonthExpenses(BigDecimal familyMonthExpenses){
        this.familyMonthExpenses = familyMonthExpenses;
    }

    /**
    * 获取现居住地址时间
    *
    * @return 现居住地址时间
    */
    public Date getLiveTime(){
        return liveTime;
    }
    
    /**
     * 设置现居住地址时间
     * 
     * @param liveTime 要设置的现居住地址时间
     */
    public void setLiveTime(Date liveTime){
        this.liveTime = liveTime;
    }

    /**
    * 获取来本市时间
    *
    * @return 来本市时间
    */
    public Date getComeCityDate(){
        return comeCityDate;
    }
    
    /**
     * 设置来本市时间
     * 
     * @param comeCityDate 要设置的来本市时间
     */
    public void setComeCityDate(Date comeCityDate){
        this.comeCityDate = comeCityDate;
    }

    /**
    * 获取微信
    *
    * @return 微信
    */
    public String getWeixin(){
        return weixin;
    }
    
    /**
     * 设置微信
     * 
     * @param weixin 要设置的微信
     */
    public void setWeixin(String weixin){
        this.weixin = weixin;
    }

    /**
    * 获取微博
    *
    * @return 微博
    */
    public String getWeibo(){
        return weibo;
    }
    
    /**
     * 设置微博
     * 
     * @param weibo 要设置的微博
     */
    public void setWeibo(String weibo){
        this.weibo = weibo;
    }

    /**
    * 获取qq
    *
    * @return qq
    */
    public String getQq(){
        return qq;
    }
    
    /**
     * 设置qq
     * 
     * @param qq 要设置的qq
     */
    public void setQq(String qq){
        this.qq = qq;
    }

    /**
    * 获取客户来源
    *
    * @return 客户来源
    */
    public String getCustomerSource(){
        return customerSource;
    }
    
    /**
     * 设置客户来源
     * 
     * @param customerSource 要设置的客户来源
     */
    public void setCustomerSource(String customerSource){
        this.customerSource = customerSource;
    }

    /**
    * 获取职位类型
    *
    * @return 职位类型
    */
    public Integer getPositionType(){
        return positionType;
    }
    
    /**
     * 设置职位类型
     * 
     * @param positionType 要设置的职位类型
     */
    public void setPositionType(Integer positionType){
        this.positionType = positionType;
    }

    /**
    * 获取职位级别
    *
    * @return 职位级别
    */
    public Integer getPositionLevel(){
        return positionLevel;
    }
    
    /**
     * 设置职位级别
     * 
     * @param positionLevel 要设置的职位级别
     */
    public void setPositionLevel(Integer positionLevel){
        this.positionLevel = positionLevel;
    }

    /**
    * 获取所属行业
    *
    * @return 所属行业
    */
    public Byte getPositionIndustry(){
        return positionIndustry;
    }
    
    /**
     * 设置所属行业
     * 
     * @param positionIndustry 要设置的所属行业
     */
    public void setPositionIndustry(Byte positionIndustry){
        this.positionIndustry = positionIndustry;
    }

    /**
    * 获取前单位名称
    *
    * @return 前单位名称
    */
    public String getLastCompany(){
        return lastCompany;
    }
    
    /**
     * 设置前单位名称
     * 
     * @param lastCompany 要设置的前单位名称
     */
    public void setLastCompany(String lastCompany){
        this.lastCompany = lastCompany;
    }

    /**
    * 获取工资发放形式
    *
    * @return 工资发放形式
    */
    public Byte getWagesType(){
        return wagesType;
    }
    
    /**
     * 设置工资发放形式
     * 
     * @param wagesType 要设置的工资发放形式
     */
    public void setWagesType(Byte wagesType){
        this.wagesType = wagesType;
    }

    /**
    * 获取每月基本薪金
    *
    * @return 每月基本薪金
    */
    public BigDecimal getSalary(){
        return salary;
    }
    
    /**
     * 设置每月基本薪金
     * 
     * @param salary 要设置的每月基本薪金
     */
    public void setSalary(BigDecimal salary){
        this.salary = salary;
    }

    /**
    * 获取其他收入
    *
    * @return 其他收入
    */
    public BigDecimal getOtherIn(){
        return otherIn;
    }
    
    /**
     * 设置其他收入
     * 
     * @param otherIn 要设置的其他收入
     */
    public void setOtherIn(BigDecimal otherIn){
        this.otherIn = otherIn;
    }

    /**
    * 获取每月发薪日
    *
    * @return 每月发薪日
    */
    public String getGrantDay(){
        return grantDay;
    }
    
    /**
     * 设置每月发薪日
     * 
     * @param grantDay 要设置的每月发薪日
     */
    public void setGrantDay(String grantDay){
        this.grantDay = grantDay;
    }

    /**
    * 获取经营营类型
    *
    * @return 经营营类型
    */
    public Integer getManagementType(){
        return managementType;
    }
    
    /**
     * 设置经营营类型
     * 
     * @param managementType 要设置的经营营类型
     */
    public void setManagementType(Integer managementType){
        this.managementType = managementType;
    }

    /**
    * 获取经营类型后缀名
    *
    * @return 经营类型后缀名
    */
    public Integer getManagementName(){
        return managementName;
    }
    
    /**
     * 设置经营类型后缀名
     * 
     * @param managementName 要设置的经营类型后缀名
     */
    public void setManagementName(Integer managementName){
        this.managementName = managementName;
    }

    /**
    * 获取企业成立时间
    *
    * @return 企业成立时间
    */
    public Date getCompanyBuildTime(){
        return companyBuildTime;
    }
    
    /**
     * 设置企业成立时间
     * 
     * @param companyBuildTime 要设置的企业成立时间
     */
    public void setCompanyBuildTime(Date companyBuildTime){
        this.companyBuildTime = companyBuildTime;
    }

    /**
    * 获取股份占比
    *
    * @return 股份占比
    */
    public BigDecimal getShareScale(){
        return shareScale;
    }
    
    /**
     * 设置股份占比
     * 
     * @param shareScale 要设置的股份占比
     */
    public void setShareScale(BigDecimal shareScale){
        this.shareScale = shareScale;
    }

    /**
    * 获取员工人数
    *
    * @return 员工人数
    */
    public Integer getEmployeeNum(){
        return employeeNum;
    }
    
    /**
     * 设置员工人数
     * 
     * @param employeeNum 要设置的员工人数
     */
    public void setEmployeeNum(Integer employeeNum){
        this.employeeNum = employeeNum;
    }

    /**
    * 获取经营场所类型
    *
    * @return 经营场所类型
    */
    public Integer getManagementAddrType(){
        return managementAddrType;
    }
    
    /**
     * 设置经营场所类型
     * 
     * @param managementAddrType 要设置的经营场所类型
     */
    public void setManagementAddrType(Integer managementAddrType){
        this.managementAddrType = managementAddrType;
    }

    /**
    * 获取单位地址ID
    *
    * @return 单位地址ID
    */
    public Integer getCompanyAreaId(){
        return companyAreaId;
    }
    
    /**
     * 设置单位地址ID
     * 
     * @param companyAreaId 要设置的单位地址ID
     */
    public void setCompanyAreaId(Integer companyAreaId){
        this.companyAreaId = companyAreaId;
    }

    /**
    * 获取团队经理
    *
    * @return 团队经理
    */
    public String getTeamManager(){
        return teamManager;
    }
    
    /**
     * 设置团队经理
     * 
     * @param teamManager 要设置的团队经理
     */
    public void setTeamManager(String teamManager){
        this.teamManager = teamManager;
    }

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}