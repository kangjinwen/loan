package com.company.modules.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 垫资申请列表
 * @author wulb
 * @version 1.0
 * @since 2016-08-08 01:01:45
 */
public class PlConsultAdvanceApply implements Serializable {
	
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
     * 咨询id
     */
    private Long consultId;
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
     * 垫资申请时间
     */
    private Date advanceApplyTime;   
    /**
     * 垫资申请操作员
     */
    private String advanceApplyOperator;   
    /**
     * 分支流程id
     */
    private String newProcessInstanceId;  
    /**
     * taskId
     */
    private String taskId;   
    /**
     * 审批流程名字
     */
    private String currentProcessStateCode;   
    /**
     * 1垫资申请列表
     */
    private Byte advanceApply;
    
    private String nextAssignee;
  
    
	public String getNextAssignee() {
		return nextAssignee;
	}

	public void setNextAssignee(String nextAssignee) {
		this.nextAssignee = nextAssignee;
	}

	public String getNewProcessInstanceId() {
		return newProcessInstanceId;
	}

	public void setNewProcessInstanceId(String newProcessInstanceId) {
		this.newProcessInstanceId = newProcessInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Byte getAdvanceApply() {
		return advanceApply;
	}

	public void setAdvanceApply(Byte advanceApply) {
		this.advanceApply = advanceApply;
	}
	
    
    public Long getConsultId() {
		return consultId;
	}

	public void setConsultId(Long consultId) {
		this.consultId = consultId;
	}
	

	public Date getAdvanceApplyTime() {
		return advanceApplyTime;
	}

	public void setAdvanceApplyTime(Date advanceApplyTime) {
		this.advanceApplyTime = advanceApplyTime;
	}

	public String getAdvanceApplyOperator() {
		return advanceApplyOperator;
	}

	public void setAdvanceApplyOperator(String advanceApplyOperator) {
		this.advanceApplyOperator = advanceApplyOperator;
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

	public String getCurrentProcessStateCode() {
		return currentProcessStateCode;
	}

	public void setCurrentProcessStateCode(String currentProcessStateCode) {
		this.currentProcessStateCode = currentProcessStateCode;
	}	
	
}