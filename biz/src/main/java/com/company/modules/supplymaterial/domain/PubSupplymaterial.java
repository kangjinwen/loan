package com.company.modules.supplymaterial.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 补充资料实体
 * 
 * @author JDM
 * @version 1.0
 * @since 2016-08-17 11:31:42
 */
public class PubSupplymaterial implements Serializable {

	private static final Long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 附件状态:0删除,1正常
	 */
	private Long state;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 操作者id
	 */
	private Long operatorId;
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
	 * 他项权利证名称
	 */
	private String otherMaterialName;
	
	private Boolean otherFileUploaded;
	
	private Boolean notarizationFileUploaded;
	
	/**
	 * 获取id
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param Long
	 *            要设置的id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取附件状态:0删除,1正常
	 *
	 * @return 附件状态:0删除,1正常
	 */
	public Long getState() {
		return state;
	}

	/**
	 * 设置附件状态:0删除,1正常
	 * 
	 * @param state
	 *            要设置的附件状态:0删除,1正常
	 */
	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * 获取创建时间
	 *
	 * @return 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createTime
	 *            要设置的创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取操作者id
	 *
	 * @return 操作者id
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * 设置操作者id
	 * 
	 * @param operatorId
	 *            要设置的操作者id
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 获取咨询id
	 *
	 * @return 咨询id
	 */
	public Long getConsultId() {
		return consultId;
	}

	/**
	 * 设置咨询id
	 * 
	 * @param consultId
	 *            要设置的咨询id
	 */
	public void setConsultId(Long consultId) {
		this.consultId = consultId;
	}

	/**
	 * 获取流程ID
	 *
	 * @return 流程ID
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * 设置流程ID
	 * 
	 * @param processInstanceId
	 *            要设置的流程ID
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * 获取项目编号
	 *
	 * @return 项目编号
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * 设置项目编号
	 * 
	 * @param projectId
	 *            要设置的项目编号
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * 获取他项权利证名称
	 *
	 * @return 他项权利证名称
	 */
	public String getOtherMaterialName() {
		return otherMaterialName;
	}

	/**
	 * 设置他项权利证名称
	 * 
	 * @param otherMaterialName
	 *            要设置的他项权利证名称
	 */
	public void setOtherMaterialName(String otherMaterialName) {
		this.otherMaterialName = otherMaterialName;
	}

	public Boolean getOtherFileUploaded() {
		return otherFileUploaded;
	}

	public void setOtherFileUploaded(Boolean otherFileUploaded) {
		this.otherFileUploaded = otherFileUploaded;
	}

	public Boolean getNotarizationFileUploaded() {
		return notarizationFileUploaded;
	}

	public void setNotarizationFileUploaded(Boolean notarizationFileUploaded) {
		this.notarizationFileUploaded = notarizationFileUploaded;
	}

}