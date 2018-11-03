package com.company.modules.system.domain;

import java.io.Serializable;

/**
 * 
 * 房产价值评估基本信息表实体类
 * 
 * @date 2018-09-17
 */
public class HousPropertyAssessment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键，自增
	 */
	private Integer id;

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 房产名称
	 */
	private String houseName;

	/**
	 * 房产评估结果:单价
	 */
	private Integer unitPrice;

	/**
	 * 房产评估结果：总价
	 */
	private Integer totalPrice;
	/**
	 * 评估状态：0代表待评估；1代表已评估。
	 */
	private Integer status;

	/**
	 * 上传时间
	 */
	private String createTime;

	/**
	 * 房产评估人员id
	 */
	private Integer assesserId;

	/**
	 * 创建者的id
	 */
	private Integer creatorId;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否删除：0代表未删除，1代表已删除
	 */
	private Integer isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getAssesserId() {
		return assesserId;
	}

	public void setAssesserId(Integer assesserId) {
		this.assesserId = assesserId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}