package com.company.modules.system.domain;

import java.io.Serializable;

/**
 * 房产价值评估附件表实体类
 * 
 * 
 * @date 2018-09-17
 */
public class HousPropertyAttachment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id,自增字段
	 */
	private Integer id;

	/**
	 * 外键：关联hous_property_assessment表
	 */
	private Integer propertyAssessmentId;

	/**
	 * 附件状态:0删除,1正常
	 */
	private Integer state;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 原文件名
	 */
	private String fileName;

	/**
	 * 文件大小
	 */
	private long fileSize;

	/**
	 * 上传后相对路径
	 */
	private String filePath;

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

	public Integer getPropertyAssessmentId() {
		return propertyAssessmentId;
	}

	public void setPropertyAssessmentId(Integer propertyAssessmentId) {
		this.propertyAssessmentId = propertyAssessmentId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath == null ? null : filePath.trim();
	}
}