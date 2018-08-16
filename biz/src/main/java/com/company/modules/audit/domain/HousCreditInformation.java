package com.company.modules.audit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 征信信息表(面审)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-14 01:30:18
 */
public class HousCreditInformation implements Serializable {
	
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
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 项目编号
    */
    private Long projectId;
    /**
    * 征信良好
    */
    private Byte goodCredit;
    /**
    * 当前逾期
    */
    private Byte currentOverdue;
    /**
    * 逾期金额
    */
    private BigDecimal overdueAmounts;
    private Boolean overdueAmountsNullUpdate;
    
    /**
    * 担保性贷款
    */
    private Byte securedLoan;
    /**
    * 担保性贷款金额
    */
    private BigDecimal securedLoanAmounts;
    private Boolean securedLoanAmountsNullUpdate;
    /**
    * 呆账
    */
    private Byte badDebt;
    /**
    * 呆账笔数
    */
    private Long badDebtItems;
    private Boolean badDebtItemsNullUpdate;
    /**
    * 呆账金额
    */
    private BigDecimal badDebtAmounts;
    private Boolean badDebtAmountsNullUpdate;
    /**
    * 近2年连3累6
    */
    private Byte nearlyTwpYears;
    /**
    * 备注
    */
    private String remark;
    /**
    * 证件照片相符(身份证)
    */
    private Byte photoMatchesIdCard;
    /**
    * 证件照片相符(结婚证)
    */
    private Byte photoMatchesMarriageCertificate;
    /**
    * 证件照片相符(离婚证)
    */
    private Byte photoMatchesDivorceCertificate;
    /**
    * 证件信息一致(身份证)
    */
    private Byte identityInformaitonIdCard;
    /**
    * 证件信息一致(结婚证)
    */
    private Byte identityInformaitonMarriageCertificate;
    /**
    * 证件信息一致(离婚证)
    */
    private Byte identityInformaitonDivorceCertificate;
    /**
    * 证件信息一致(户口本)
    */
    private Byte identityInformaitonAccountBook;
    /**
    * 户口与房屋所在地是否一致
    */
    private Byte locationPropertyConsistent;
    /**
    * 抵贷是否一致
    */
    private Byte foreclosedConsistency;
    /**
    * 抵贷实际用款人
    */
    private String realLoanName;
    /**
    * 是否共借
    */
    private Byte whetherTotal;
    /**
    * 是否有保证人
    */
    private Byte guarantor;
    /**
    * 是否家庭名下唯一房产
    */
    private Byte onlyHousing;
    /**
    * 房本是否满五年
    */
    private Byte roomIsFullFiveYears;
    /**
    * 备用房屋地址
    */
    private String alternatePropertyAddressId;
    /**
    * 备用房屋详细地址
    */
    private String alternatePropertyAddress;
    
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
    * 获取征信良好
    *
    * @return 征信良好
    */
    public Byte getGoodCredit(){
        return goodCredit;
    }
    
    /**
     * 设置征信良好
     * 
     * @param goodCredit 要设置的征信良好
     */
    public void setGoodCredit(Byte goodCredit){
        this.goodCredit = goodCredit;
    }

    /**
    * 获取当前逾期
    *
    * @return 当前逾期
    */
    public Byte getCurrentOverdue(){
        return currentOverdue;
    }
    
    /**
     * 设置当前逾期
     * 
     * @param currentOverdue 要设置的当前逾期
     */
    public void setCurrentOverdue(Byte currentOverdue){
        this.currentOverdue = currentOverdue;
    }

    /**
    * 获取逾期金额
    *
    * @return 逾期金额
    */
    public BigDecimal getOverdueAmounts(){
        return overdueAmounts;
    }
    
    /**
     * 设置逾期金额
     * 
     * @param overdueAmounts 要设置的逾期金额
     */
    public void setOverdueAmounts(BigDecimal overdueAmounts){
        this.overdueAmounts = overdueAmounts;
    }

    /**
    * 获取担保性贷款
    *
    * @return 担保性贷款
    */
    public Byte getSecuredLoan(){
        return securedLoan;
    }
    
    /**
     * 设置担保性贷款
     * 
     * @param securedLoan 要设置的担保性贷款
     */
    public void setSecuredLoan(Byte securedLoan){
        this.securedLoan = securedLoan;
    }

    /**
    * 获取担保性贷款金额
    *
    * @return 担保性贷款金额
    */
    public BigDecimal getSecuredLoanAmounts(){
        return securedLoanAmounts;
    }
    
    /**
     * 设置担保性贷款金额
     * 
     * @param securedLoanAmounts 要设置的担保性贷款金额
     */
    public void setSecuredLoanAmounts(BigDecimal securedLoanAmounts){
        this.securedLoanAmounts = securedLoanAmounts;
    }

    /**
    * 获取呆账
    *
    * @return 呆账
    */
    public Byte getBadDebt(){
        return badDebt;
    }
    
    /**
     * 设置呆账
     * 
     * @param badDebt 要设置的呆账
     */
    public void setBadDebt(Byte badDebt){
        this.badDebt = badDebt;
    }

    /**
    * 获取呆账笔数
    *
    * @return 呆账笔数
    */
    public Long getBadDebtItems(){
        return badDebtItems;
    }
    
    /**
     * 设置呆账笔数
     * 
     * @param badDebtItems 要设置的呆账笔数
     */
    public void setBadDebtItems(Long badDebtItems){
        this.badDebtItems = badDebtItems;
    }

    /**
    * 获取呆账金额
    *
    * @return 呆账金额
    */
    public BigDecimal getBadDebtAmounts(){
        return badDebtAmounts;
    }
    
    /**
     * 设置呆账金额
     * 
     * @param badDebtAmounts 要设置的呆账金额
     */
    public void setBadDebtAmounts(BigDecimal badDebtAmounts){
        this.badDebtAmounts = badDebtAmounts;
    }

    /**
    * 获取近2年连3累6
    *
    * @return 近2年连3累6
    */
    public Byte getNearlyTwpYears(){
        return nearlyTwpYears;
    }
    
    /**
     * 设置近2年连3累6
     * 
     * @param nearlyTwpYears 要设置的近2年连3累6
     */
    public void setNearlyTwpYears(Byte nearlyTwpYears){
        this.nearlyTwpYears = nearlyTwpYears;
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
    * 获取证件照片相符(身份证)
    *
    * @return 证件照片相符(身份证)
    */
    public Byte getPhotoMatchesIdCard(){
        return photoMatchesIdCard;
    }
    
    /**
     * 设置证件照片相符(身份证)
     * 
     * @param photoMatchesIdCard 要设置的证件照片相符(身份证)
     */
    public void setPhotoMatchesIdCard(Byte photoMatchesIdCard){
        this.photoMatchesIdCard = photoMatchesIdCard;
    }

    /**
    * 获取证件照片相符(结婚证)
    *
    * @return 证件照片相符(结婚证)
    */
    public Byte getPhotoMatchesMarriageCertificate(){
        return photoMatchesMarriageCertificate;
    }
    
    /**
     * 设置证件照片相符(结婚证)
     * 
     * @param photoMatchesMarriageCertificate 要设置的证件照片相符(结婚证)
     */
    public void setPhotoMatchesMarriageCertificate(Byte photoMatchesMarriageCertificate){
        this.photoMatchesMarriageCertificate = photoMatchesMarriageCertificate;
    }

    /**
    * 获取证件照片相符(离婚证)
    *
    * @return 证件照片相符(离婚证)
    */
    public Byte getPhotoMatchesDivorceCertificate(){
        return photoMatchesDivorceCertificate;
    }
    
    /**
     * 设置证件照片相符(离婚证)
     * 
     * @param photoMatchesDivorceCertificate 要设置的证件照片相符(离婚证)
     */
    public void setPhotoMatchesDivorceCertificate(Byte photoMatchesDivorceCertificate){
        this.photoMatchesDivorceCertificate = photoMatchesDivorceCertificate;
    }

    /**
    * 获取证件信息一致(身份证)
    *
    * @return 证件信息一致(身份证)
    */
    public Byte getIdentityInformaitonIdCard(){
        return identityInformaitonIdCard;
    }
    
    /**
     * 设置证件信息一致(身份证)
     * 
     * @param identityInformaitonIdCard 要设置的证件信息一致(身份证)
     */
    public void setIdentityInformaitonIdCard(Byte identityInformaitonIdCard){
        this.identityInformaitonIdCard = identityInformaitonIdCard;
    }

    /**
    * 获取证件信息一致(结婚证)
    *
    * @return 证件信息一致(结婚证)
    */
    public Byte getIdentityInformaitonMarriageCertificate(){
        return identityInformaitonMarriageCertificate;
    }
    
    /**
     * 设置证件信息一致(结婚证)
     * 
     * @param identityInformaitonMarriageCertificate 要设置的证件信息一致(结婚证)
     */
    public void setIdentityInformaitonMarriageCertificate(Byte identityInformaitonMarriageCertificate){
        this.identityInformaitonMarriageCertificate = identityInformaitonMarriageCertificate;
    }

    /**
    * 获取证件信息一致(离婚证)
    *
    * @return 证件信息一致(离婚证)
    */
    public Byte getIdentityInformaitonDivorceCertificate(){
        return identityInformaitonDivorceCertificate;
    }
    
    /**
     * 设置证件信息一致(离婚证)
     * 
     * @param identityInformaitonDivorceCertificate 要设置的证件信息一致(离婚证)
     */
    public void setIdentityInformaitonDivorceCertificate(Byte identityInformaitonDivorceCertificate){
        this.identityInformaitonDivorceCertificate = identityInformaitonDivorceCertificate;
    }

    /**
    * 获取证件信息一致(户口本)
    *
    * @return 证件信息一致(户口本)
    */
    public Byte getIdentityInformaitonAccountBook(){
        return identityInformaitonAccountBook;
    }
    
    /**
     * 设置证件信息一致(户口本)
     * 
     * @param identityInformaitonAccountBook 要设置的证件信息一致(户口本)
     */
    public void setIdentityInformaitonAccountBook(Byte identityInformaitonAccountBook){
        this.identityInformaitonAccountBook = identityInformaitonAccountBook;
    }

    /**
    * 获取户口与房屋所在地是否一致
    *
    * @return 户口与房屋所在地是否一致
    */
    public Byte getLocationPropertyConsistent(){
        return locationPropertyConsistent;
    }
    
    /**
     * 设置户口与房屋所在地是否一致
     * 
     * @param locationPropertyConsistent 要设置的户口与房屋所在地是否一致
     */
    public void setLocationPropertyConsistent(Byte locationPropertyConsistent){
        this.locationPropertyConsistent = locationPropertyConsistent;
    }

    /**
    * 获取抵贷是否一致
    *
    * @return 抵贷是否一致
    */
    public Byte getForeclosedConsistency(){
        return foreclosedConsistency;
    }
    
    /**
     * 设置抵贷是否一致
     * 
     * @param foreclosedConsistency 要设置的抵贷是否一致
     */
    public void setForeclosedConsistency(Byte foreclosedConsistency){
        this.foreclosedConsistency = foreclosedConsistency;
    }

    /**
    * 获取抵贷实际用款人
    *
    * @return 抵贷实际用款人
    */
    public String getRealLoanName(){
        return realLoanName;
    }
    
    /**
     * 设置抵贷实际用款人
     * 
     * @param realLoanName 要设置的抵贷实际用款人
     */
    public void setRealLoanName(String realLoanName){
        this.realLoanName = realLoanName;
    }

    /**
    * 获取是否共借
    *
    * @return 是否共借
    */
    public Byte getWhetherTotal(){
        return whetherTotal;
    }
    
    /**
     * 设置是否共借
     * 
     * @param whetherTotal 要设置的是否共借
     */
    public void setWhetherTotal(Byte whetherTotal){
        this.whetherTotal = whetherTotal;
    }

    /**
    * 获取是否有保证人
    *
    * @return 是否有保证人
    */
    public Byte getGuarantor(){
        return guarantor;
    }
    
    /**
     * 设置是否有保证人
     * 
     * @param guarantor 要设置的是否有保证人
     */
    public void setGuarantor(Byte guarantor){
        this.guarantor = guarantor;
    }

    /**
    * 获取是否家庭名下唯一房产
    *
    * @return 是否家庭名下唯一房产
    */
    public Byte getOnlyHousing(){
        return onlyHousing;
    }
    
    /**
     * 设置是否家庭名下唯一房产
     * 
     * @param onlyHousing 要设置的是否家庭名下唯一房产
     */
    public void setOnlyHousing(Byte onlyHousing){
        this.onlyHousing = onlyHousing;
    }

    /**
    * 获取房本是否满五年
    *
    * @return 房本是否满五年
    */
    public Byte getRoomIsFullFiveYears(){
        return roomIsFullFiveYears;
    }
    
    /**
     * 设置房本是否满五年
     * 
     * @param roomIsFullFiveYears 要设置的房本是否满五年
     */
    public void setRoomIsFullFiveYears(Byte roomIsFullFiveYears){
        this.roomIsFullFiveYears = roomIsFullFiveYears;
    }

    /**
    * 获取备用房屋地址
    *
    * @return 备用房屋地址
    */
    public String getAlternatePropertyAddressId(){
        return alternatePropertyAddressId;
    }
    
    /**
     * 设置备用房屋地址
     * 
     * @param alternatePropertyAddressId 要设置的备用房屋地址
     */
    public void setAlternatePropertyAddressId(String alternatePropertyAddressId){
        this.alternatePropertyAddressId = alternatePropertyAddressId;
    }

    /**
    * 获取备用房屋详细地址
    *
    * @return 备用房屋详细地址
    */
    public String getAlternatePropertyAddress(){
        return alternatePropertyAddress;
    }
    
    /**
     * 设置备用房屋详细地址
     * 
     * @param alternatePropertyAddress 要设置的备用房屋详细地址
     */
    public void setAlternatePropertyAddress(String alternatePropertyAddress){
        this.alternatePropertyAddress = alternatePropertyAddress;
    }

	public Boolean getOverdueAmountsNullUpdate() {
		return overdueAmountsNullUpdate;
	}

	public void setOverdueAmountsNullUpdate(Boolean overdueAmountsNullUpdate) {
		this.overdueAmountsNullUpdate = overdueAmountsNullUpdate;
	}

	public Boolean getSecuredLoanAmountsNullUpdate() {
		return securedLoanAmountsNullUpdate;
	}

	public void setSecuredLoanAmountsNullUpdate(Boolean securedLoanAmountsNullUpdate) {
		this.securedLoanAmountsNullUpdate = securedLoanAmountsNullUpdate;
	}

	public Boolean getBadDebtItemsNullUpdate() {
		return badDebtItemsNullUpdate;
	}

	public void setBadDebtItemsNullUpdate(Boolean badDebtItemsNullUpdate) {
		this.badDebtItemsNullUpdate = badDebtItemsNullUpdate;
	}

	public Boolean getBadDebtAmountsNullUpdate() {
		return badDebtAmountsNullUpdate;
	}

	public void setBadDebtAmountsNullUpdate(Boolean badDebtAmountsNullUpdate) {
		this.badDebtAmountsNullUpdate = badDebtAmountsNullUpdate;
	}
    
}