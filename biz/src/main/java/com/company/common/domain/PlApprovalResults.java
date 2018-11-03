package com.company.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PlApprovalResults implements Serializable {
    private Integer id;

    private Integer creator;

    private Date createTime;

    private Integer modifier;

    private Date modifyTime;

    private Byte isDelete;

    private String processInstanceId;

    private Integer projectId;

    private Integer consultId;

    private Integer customerId;

    private Integer productId;

    private BigDecimal approvalAccount;

    private Integer approvalTimeLimit;

    private BigDecimal mortgagePrice;

    private String processState;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getConsultId() {
        return consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getApprovalAccount() {
        return approvalAccount;
    }

    public void setApprovalAccount(BigDecimal approvalAccount) {
        this.approvalAccount = approvalAccount;
    }

    public Integer getApprovalTimeLimit() {
        return approvalTimeLimit;
    }

    public void setApprovalTimeLimit(Integer approvalTimeLimit) {
        this.approvalTimeLimit = approvalTimeLimit;
    }

    public BigDecimal getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(BigDecimal mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState == null ? null : processState.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}