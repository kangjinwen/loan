package com.company.modules.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 可退费列表
 * @author wulb
 * @version 1.0
 * @since 2016-08-08 01:01:45
 */
public class PlConsultFee implements Serializable {
	
	private static final Long serialVersionUID = 1L;
	
	/**
    * id
    */
    private Long id;
    /**
    * 产品ID
    */
    private Long productId;
    /**
    * 姓名
    */
    private String projectName;
    /**
     * 客户名称
     */
    private String customerName;
    /**
    * 移动电话
    */
    private String mobile;
    /**
    * 身份证号码
    */
    private String idcard;
    /**
    * 咨询录入者ID
    */
    private Long creator;
    
    private Long curuserId;
    
    
    /**
    * 当前操作者
    */
    private String executor;
    /**
    * 报单人
    */
    private String customerManager;
    /**
    * 创建日期
    */
    private Date createTime;
    /**
    * 流程ID
    */
    private String processInstanceId;
    /**
    * 咨询状态
    */
    private String status;
    /**
    * 修改者
    */
    private Long modifier;
    /**
    * 修改时间
    */
    private Date modifyTime;
    /**
    * 项目编号
    */
    private Long projectId;
    /**
    * 备注
    */
    private String remark;
    /**
    * 成功展期次数
    */
    private Long extensionTime;
    /**
    * 业务来源
    */
    private String businessOrigin;
    /**
     * 1下户费收取成功,2发出退费申请
     */
    private Byte feeSuccess;
    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 退费是否成功，0否，1是
     */
    private Byte applyRefundIsSuccess;
	 /**
	  * 申请退费时间
	  */
    private Date applyRefundTime;
	  /**
	   * 退费申请操作员
	   */
    private String refundOperator;
    /**
	   * 退费申请操作员
	   */
  private String businessOriginText;
  /**
   * 退费申请操作员
   */
private String institutionName;

/**
 * 下一步流程审批人
 */
private String nextAssignee;


    
    public String getNextAssignee() {
	return nextAssignee;
}

public void setNextAssignee(String nextAssignee) {
	this.nextAssignee = nextAssignee;
}

	/**
     * 获取退费申请操作员
     *
     * @return 退费申请操作员
     */
     public String getRefundOperator(){
         return refundOperator;
     }
     
     /**
      * 设置退费申请操作员
      * 
      * @param refundOperator 要设置的退费申请操作员
      */
     public void setRefundOperator(String refundOperator){
         this.refundOperator = refundOperator;
     }
     
     
    public String getBusinessOriginText() {
		return businessOriginText;
	}

	public void setBusinessOriginText(String businessOriginText) {
		this.businessOriginText = businessOriginText;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
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
    * 获取产品ID
    *
    * @return 产品ID
    */
    public Long getProductId(){
        return productId;
    }
    
    /**
     * 设置产品ID
     * 
     * @param productId 要设置的产品ID
     */
    public void setProductId(Long productId){
        this.productId = productId;
    }

    
    public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
    * 获取移动电话
    *
    * @return 移动电话
    */
    public String getMobile(){
        return mobile;
    }
    
    /**
     * 设置移动电话
     * 
     * @param mobile 要设置的移动电话
     */
    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    /**
    * 获取身份证号码
    *
    * @return 身份证号码
    */
    public String getIdcard(){
        return idcard;
    }
    
    /**
     * 设置身份证号码
     * 
     * @param idcard 要设置的身份证号码
     */
    public void setIdcard(String idcard){
        this.idcard = idcard;
    }

    /**
    * 获取咨询录入者ID
    *
    * @return 咨询录入者ID
    */
    public Long getCreator(){
        return creator;
    }
    
    /**
     * 获取申请退费时间
     *
     * @return 申请退费时间
     */
     public Date getApplyRefundTime(){
         return applyRefundTime;
     }
     
     /**
      * 设置申请退费时间
      * 
      * @param applyRefundTime 要设置的申请退费时间
      */
     public void setApplyRefundTime(Date applyRefundTime){
         this.applyRefundTime = applyRefundTime;
     }

    
    /**
     * 设置咨询录入者ID
     * 
     * @param creator 要设置的咨询录入者ID
     */
    public void setCreator(Long creator){
        this.creator = creator;
    }



    /**
    * 获取报单人
    *
    * @return 报单人
    */
    public String getCustomerManager(){
        return customerManager;
    }
    
    /**
     * 设置报单人
     * 
     * @param customerManager 要设置的报单人
     */
    public void setCustomerManager(String customerManager){
        this.customerManager = customerManager;
    }

    /**
    * 获取创建日期
    *
    * @return 创建日期
    */
    public Date getCreateTime(){
        return createTime;
    }
    
    /**
     * 设置创建日期
     * 
     * @param createTime 要设置的创建日期
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
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
    * 获取咨询状态
    *
    * @return 咨询状态
    */
    public String getStatus(){
        return status;
    }
    
    /**
     * 设置咨询状态
     * 
     * @param status 要设置的咨询状态
     */
    public void setStatus(String status){
        this.status = status;
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
    * 获取成功展期次数
    *
    * @return 成功展期次数
    */
    public Long getExtensionTime(){
        return extensionTime;
    }
    
    /**
     * 设置成功展期次数
     * 
     * @param extensionTime 要设置的成功展期次数
     */
    public void setExtensionTime(Long extensionTime){
        this.extensionTime = extensionTime;
    }

    /**
    * 获取业务来源
    *
    * @return 业务来源
    */
    public String getBusinessOrigin(){
        return businessOrigin;
    }
    
    /**
     * 设置业务来源
     * 
     * @param businessOrigin 要设置的业务来源
     */
    public void setBusinessOrigin(String businessOrigin){
        this.businessOrigin = businessOrigin;
    }

	public Byte getFeeSuccess() {
		return feeSuccess;
	}

	public void setFeeSuccess(Byte feeSuccess) {
		this.feeSuccess = feeSuccess;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Byte getApplyRefundIsSuccess() {
		return applyRefundIsSuccess;
	}

	public void setApplyRefundIsSuccess(Byte applyRefundIsSuccess) {
		this.applyRefundIsSuccess = applyRefundIsSuccess;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCuruserId() {
		return curuserId;
	}

	public void setCuruserId(Long curuserId) {
		this.curuserId = curuserId;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}
	
	
}