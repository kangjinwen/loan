package com.company.modules.warrant.util.databean;

import java.io.Serializable;

import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.warrant.domain.HousDataList;
import com.company.modules.warrant.domain.HousIntermediaryInformation;
import com.company.modules.warrant.domain.HousOritoInformation;
import com.company.modules.warrant.domain.HousQuickInformation;

public class HouseholdInvestigateDataBean extends BasicServiceDataBean implements Serializable{

	/**
	 * serialVersionUID:TODO
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	
	private HousDataList housDataList;//资料清单表(权证下户)实体
	private HousIntermediaryInformation housIntermediaryInformation;//房屋中介信息(权证下户)实体
	private HousOritoInformation housOritoInformation;//下户信息表(权证下户)实体
	private HousQuickInformation HousQuickInformation;// 房屋快出值信息表(权证下户)实体
	
	public HousDataList getHousDataList() {
		return housDataList;
	}
	public void setHousDataList(HousDataList housDataList) {
		this.housDataList = housDataList;
	}
	public HousIntermediaryInformation getHousIntermediaryInformation() {
		return housIntermediaryInformation;
	}
	public void setHousIntermediaryInformation(
			HousIntermediaryInformation housIntermediaryInformation) {
		this.housIntermediaryInformation = housIntermediaryInformation;
	}
	public HousOritoInformation getHousOritoInformation() {
		return housOritoInformation;
	}
	public void setHousOritoInformation(HousOritoInformation housOritoInformation) {
		this.housOritoInformation = housOritoInformation;
	}
	public HousQuickInformation getHousQuickInformation() {
		return HousQuickInformation;
	}
	public void setHousQuickInformation(HousQuickInformation housQuickInformation) {
		HousQuickInformation = housQuickInformation;
	}
	
}
