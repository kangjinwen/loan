package com.company.modules.audit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 风控信息表(面审)实体
 * @author fzc
 * @version 1.0
 * @since 2016-08-14 01:29:02
 */
public class HousControlInformation implements Serializable {
	
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
    * 姓名身份证是否一致
    */
    private Byte nameIdentificationConsistent;
    /**
    * 关联企业数量
    */
    private Long affiliatesNumber;
    /**
    * 关注类企业类别
    */
    private Byte followEnterprisesCategory;
    /**
    * 是否有被执行
    */
    private Byte isExecuted;
    /**
    * 笔数
    */
    private Long items;
    /**
    * 金额
    */
    private BigDecimal money;
    /**
    * 结案
    */
    private String concluded;
    /**
    * 已被占通道
    */
    private Byte occupiedChannel;
    /**
    * 已上最高额抵押
    */
    private Byte maximumMortgage;
    /**
    * 司法查询/行政限制
    */
    private Byte judicialInquiry;
    /**
    * 一抵性质
    */
    private String arrivedNature;
    /**
    * 一抵银行名称
    */
    private String againstBankName;
    /**
    * 一抵金额
    */
    private BigDecimal arrivedAmount;
    /**
    * 一抵利率
    */
    private BigDecimal arrivedRates;
    /**
    * 评估值(元)
    */
    private BigDecimal assessedValue;
    /**
    * 快出价(元)
    */
    private BigDecimal fastBid;
    /**
     * 二抵性质
     */
     private String twoArrivedNature;
     /**
     * 二抵银行名称
     */
     private String twoAgainstBankName;
     /**
     * 二抵金额
     */
     private BigDecimal twoArrivedAmount;
     /**
     * 二抵利率
     */
     private BigDecimal twoArrivedRates;
     /**
      * 三抵性质
      */
      private String threeArrivedNature;
      /**
      * 三抵银行名称
      */
      private String threeAgainstBankName;
      /**
      * 三抵金额
      */
      private BigDecimal threeArrivedAmount;
      /**
      * 三抵利率
      */
      private BigDecimal threeArrivedRates;
    
      
    public String getTwoArrivedNature() {
		return twoArrivedNature;
	}

	public void setTwoArrivedNature(String twoArrivedNature) {
		this.twoArrivedNature = twoArrivedNature;
	}

	public String getTwoAgainstBankName() {
		return twoAgainstBankName;
	}

	public void setTwoAgainstBankName(String twoAgainstBankName) {
		this.twoAgainstBankName = twoAgainstBankName;
	}

	public BigDecimal getTwoArrivedAmount() {
		return twoArrivedAmount;
	}

	public void setTwoArrivedAmount(BigDecimal twoArrivedAmount) {
		this.twoArrivedAmount = twoArrivedAmount;
	}

	public BigDecimal getTwoArrivedRates() {
		return twoArrivedRates;
	}

	public void setTwoArrivedRates(BigDecimal twoArrivedRates) {
		this.twoArrivedRates = twoArrivedRates;
	}

	public String getThreeArrivedNature() {
		return threeArrivedNature;
	}

	public void setThreeArrivedNature(String threeArrivedNature) {
		this.threeArrivedNature = threeArrivedNature;
	}

	public String getThreeAgainstBankName() {
		return threeAgainstBankName;
	}

	public void setThreeAgainstBankName(String threeAgainstBankName) {
		this.threeAgainstBankName = threeAgainstBankName;
	}

	public BigDecimal getThreeArrivedAmount() {
		return threeArrivedAmount;
	}

	public void setThreeArrivedAmount(BigDecimal threeArrivedAmount) {
		this.threeArrivedAmount = threeArrivedAmount;
	}

	public BigDecimal getThreeArrivedRates() {
		return threeArrivedRates;
	}

	public void setThreeArrivedRates(BigDecimal threeArrivedRates) {
		this.threeArrivedRates = threeArrivedRates;
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
    * 获取姓名身份证是否一致
    *
    * @return 姓名身份证是否一致
    */
    public Byte getNameIdentificationConsistent(){
        return nameIdentificationConsistent;
    }
    
    /**
     * 设置姓名身份证是否一致
     * 
     * @param nameIdentificationConsistent 要设置的姓名身份证是否一致
     */
    public void setNameIdentificationConsistent(Byte nameIdentificationConsistent){
        this.nameIdentificationConsistent = nameIdentificationConsistent;
    }

    /**
    * 获取关联企业数量
    *
    * @return 关联企业数量
    */
    public Long getAffiliatesNumber(){
        return affiliatesNumber;
    }
    
    /**
     * 设置关联企业数量
     * 
     * @param affiliatesNumber 要设置的关联企业数量
     */
    public void setAffiliatesNumber(Long affiliatesNumber){
        this.affiliatesNumber = affiliatesNumber;
    }

    /**
    * 获取关注类企业类别
    *
    * @return 关注类企业类别
    */
    public Byte getFollowEnterprisesCategory(){
        return followEnterprisesCategory;
    }
    
    /**
     * 设置关注类企业类别
     * 
     * @param followEnterprisesCategory 要设置的关注类企业类别
     */
    public void setFollowEnterprisesCategory(Byte followEnterprisesCategory){
        this.followEnterprisesCategory = followEnterprisesCategory;
    }

    /**
    * 获取是否有被执行
    *
    * @return 是否有被执行
    */
    public Byte getIsExecuted(){
        return isExecuted;
    }
    
    /**
     * 设置是否有被执行
     * 
     * @param isExecuted 要设置的是否有被执行
     */
    public void setIsExecuted(Byte isExecuted){
        this.isExecuted = isExecuted;
    }

    /**
    * 获取笔数
    *
    * @return 笔数
    */
    public Long getItems(){
        return items;
    }
    
    /**
     * 设置笔数
     * 
     * @param items 要设置的笔数
     */
    public void setItems(Long items){
        this.items = items;
    }

    /**
    * 获取金额
    *
    * @return 金额
    */
    public BigDecimal getMoney(){
        return money;
    }
    
    /**
     * 设置金额
     * 
     * @param money 要设置的金额
     */
    public void setMoney(BigDecimal money){
        this.money = money;
    }

    /**
    * 获取结案
    *
    * @return 结案
    */
    public String getConcluded(){
        return concluded;
    }
    
    /**
     * 设置结案
     * 
     * @param concluded 要设置的结案
     */
    public void setConcluded(String concluded){
        this.concluded = concluded;
    }

    /**
    * 获取已被占通道
    *
    * @return 已被占通道
    */
    public Byte getOccupiedChannel(){
        return occupiedChannel;
    }
    
    /**
     * 设置已被占通道
     * 
     * @param occupiedChannel 要设置的已被占通道
     */
    public void setOccupiedChannel(Byte occupiedChannel){
        this.occupiedChannel = occupiedChannel;
    }

    /**
    * 获取已上最高额抵押
    *
    * @return 已上最高额抵押
    */
    public Byte getMaximumMortgage(){
        return maximumMortgage;
    }
    
    /**
     * 设置已上最高额抵押
     * 
     * @param maximumMortgage 要设置的已上最高额抵押
     */
    public void setMaximumMortgage(Byte maximumMortgage){
        this.maximumMortgage = maximumMortgage;
    }

    /**
    * 获取司法查询/行政限制
    *
    * @return 司法查询/行政限制
    */
    public Byte getJudicialInquiry(){
        return judicialInquiry;
    }
    
    /**
     * 设置司法查询/行政限制
     * 
     * @param judicialInquiry 要设置的司法查询/行政限制
     */
    public void setJudicialInquiry(Byte judicialInquiry){
        this.judicialInquiry = judicialInquiry;
    }

    /**
    * 获取一抵性质
    *
    * @return 一抵性质
    */
    public String getArrivedNature(){
        return arrivedNature;
    }
    
    /**
     * 设置一抵性质
     * 
     * @param arrivedNature 要设置的一抵性质
     */
    public void setArrivedNature(String arrivedNature){
        this.arrivedNature = arrivedNature;
    }

    /**
    * 获取一抵银行名称
    *
    * @return 一抵银行名称
    */
    public String getAgainstBankName(){
        return againstBankName;
    }
    
    /**
     * 设置一抵银行名称
     * 
     * @param againstBankName 要设置的一抵银行名称
     */
    public void setAgainstBankName(String againstBankName){
        this.againstBankName = againstBankName;
    }

    /**
    * 获取一抵金额
    *
    * @return 一抵金额
    */
    public BigDecimal getArrivedAmount(){
        return arrivedAmount;
    }
    
    /**
     * 设置一抵金额
     * 
     * @param arrivedAmount 要设置的一抵金额
     */
    public void setArrivedAmount(BigDecimal arrivedAmount){
        this.arrivedAmount = arrivedAmount;
    }

    /**
    * 获取一抵利率
    *
    * @return 一抵利率
    */
    public BigDecimal getArrivedRates(){
        return arrivedRates;
    }
    
    /**
     * 设置一抵利率
     * 
     * @param arrivedRates 要设置的一抵利率
     */
    public void setArrivedRates(BigDecimal arrivedRates){
        this.arrivedRates = arrivedRates;
    }

    /**
    * 获取评估值(元)
    *
    * @return 评估值(元)
    */
    public BigDecimal getAssessedValue(){
        return assessedValue;
    }
    
    /**
     * 设置评估值(元)
     * 
     * @param assessedValue 要设置的评估值(元)
     */
    public void setAssessedValue(BigDecimal assessedValue){
        this.assessedValue = assessedValue;
    }

    /**
    * 获取快出价(元)
    *
    * @return 快出价(元)
    */
    public BigDecimal getFastBid(){
        return fastBid;
    }
    
    /**
     * 设置快出价(元)
     * 
     * @param fastBid 要设置的快出价(元)
     */
    public void setFastBid(BigDecimal fastBid){
        this.fastBid = fastBid;
    }

}