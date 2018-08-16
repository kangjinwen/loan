package com.company.modules.anrong.entity.msp;

import java.util.List;

public class MSP{
	// 标题和汇总
	private Title title ;
	// 最近6个月申请记录
	private List<ApplyDetail> applyDetails;
	// 正常还款明细
	private List<NormalCreditDetail> normalCreditDetails;
	// 异常还款明细
	private List<AbnormalCreditDetail> abnormalCreditDetails;
	// 查询历史记录
	private List<QueryDetail> queryDetails;
	// 错误信息
	private List<Error> errors;
	// 不良信息记录
	private List<BlackData> blackDatas;

	private String excelPath;
	
	public List<BlackData> getBlackDatas() {
		return blackDatas;
	}
	public void setBlackDatas(List<BlackData> blackDatas) {
		this.blackDatas = blackDatas;
	}
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public List<ApplyDetail> getApplyDetails() {
		return applyDetails;
	}
	public void setApplyDetails(List<ApplyDetail> applyDetails) {
		this.applyDetails = applyDetails;
	}
	public List<NormalCreditDetail> getNormalCreditDetails() {
		return normalCreditDetails;
	}
	public void setNormalCreditDetails(List<NormalCreditDetail> normalCreditDetails) {
		this.normalCreditDetails = normalCreditDetails;
	}
	public List<AbnormalCreditDetail> getAbnormalCreditDetails() {
		return abnormalCreditDetails;
	}
	public void setAbnormalCreditDetails(
			List<AbnormalCreditDetail> abnormalCreditDetails) {
		this.abnormalCreditDetails = abnormalCreditDetails;
	}
	public List<QueryDetail> getQueryDetails() {
		return queryDetails;
	}
	public void setQueryDetails(List<QueryDetail> queryDetails) {
		this.queryDetails = queryDetails;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	public String getExcelPath() {
		return excelPath;
	}
	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}
	
}
