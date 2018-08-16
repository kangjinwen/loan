package com.company.modules.audit.util.databean;

import java.util.List;

import com.company.modules.audit.domain.HousControlInformation;
import com.company.modules.audit.domain.HousCreditInformation;
import com.company.modules.audit.domain.HousFaceTrial;
import com.company.modules.audit.domain.HousMarriageInformation;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;

public class FaceAuditDataBean extends AuditDataBean{

	/**
	 * serialVersionUID:TODO
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	private HousControlInformation housControlInformation;//风控信息表(面审)实体
	private HousCreditInformation housCreditInformation;//征信信息表(面审)实体
	private HousFaceTrial housFaceTrial;//面审信息表实体
	private HousMarriageInformation housMarriageInformation;//婚姻信息表(面审)实体
	private HousBorrowingBasics housBorrowingBasics;//基本信息实体
	private List<HousEnquiryInstitution> housEnquiryInstitution;//信息筛查实体	
	
	
	public HousBorrowingBasics getHousBorrowingBasics() {
		return housBorrowingBasics;
	}
	public void setHousBorrowingBasics(HousBorrowingBasics housBorrowingBasics) {
		this.housBorrowingBasics = housBorrowingBasics;
	}
	
	
	public List<HousEnquiryInstitution> getHousEnquiryInstitution() {
		return housEnquiryInstitution;
	}
	public void setHousEnquiryInstitution(List<HousEnquiryInstitution> housEnquiryInstitution) {
		this.housEnquiryInstitution = housEnquiryInstitution;
	}
	public HousControlInformation getHousControlInformation() {
		return housControlInformation;
	}
	public void setHousControlInformation(
			HousControlInformation housControlInformation) {
		this.housControlInformation = housControlInformation;
	}
	public HousCreditInformation getHousCreditInformation() {
		return housCreditInformation;
	}
	public void setHousCreditInformation(HousCreditInformation housCreditInformation) {
		this.housCreditInformation = housCreditInformation;
	}
	public HousFaceTrial getHousFaceTrial() {
		return housFaceTrial;
	}
	public void setHousFaceTrial(HousFaceTrial housFaceTrial) {
		this.housFaceTrial = housFaceTrial;
	}
	public HousMarriageInformation getHousMarriageInformation() {
		return housMarriageInformation;
	}
	public void setHousMarriageInformation(
			HousMarriageInformation housMarriageInformation) {
		this.housMarriageInformation = housMarriageInformation;
	}
	
	
}
