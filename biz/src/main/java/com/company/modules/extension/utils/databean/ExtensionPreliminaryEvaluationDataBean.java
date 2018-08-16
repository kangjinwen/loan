package com.company.modules.extension.utils.databean;

import java.io.Serializable;
import java.util.List;

import com.company.common.domain.PlBorrowRequirement;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.domain.HousPropertyInformation;

public class ExtensionPreliminaryEvaluationDataBean extends BasicServiceDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long consultId;
	private List<HousAssessmentAgencies> housAssessmentAgencies;
	private HousBorrowingBasics housBorrowingBasics;
	private HousPropertyInformation housPropertyInformation;
	private PlBorrowRequirement plBorrowRequirement;
	private List<HousEnquiryInstitution> housEnquiryInstitution;

	public Long getConsultId() {
		return consultId;
	}

	public void setConsultId(Long consultId) {
		this.consultId = consultId;
	}

	public List<HousAssessmentAgencies> getHousAssessmentAgencies() {
		return housAssessmentAgencies;
	}

	public void setHousAssessmentAgencies(List<HousAssessmentAgencies> housAssessmentAgencies) {
		this.housAssessmentAgencies = housAssessmentAgencies;
	}

	public HousBorrowingBasics getHousBorrowingBasics() {
		return housBorrowingBasics;
	}

	public void setHousBorrowingBasics(HousBorrowingBasics housBorrowingBasics) {
		this.housBorrowingBasics = housBorrowingBasics;
	}

	public HousPropertyInformation getHousPropertyInformation() {
		return housPropertyInformation;
	}

	public void setHousPropertyInformation(HousPropertyInformation housPropertyInformation) {
		this.housPropertyInformation = housPropertyInformation;
	}

	public PlBorrowRequirement getPlBorrowRequirement() {
		return plBorrowRequirement;
	}

	public void setPlBorrowRequirement(PlBorrowRequirement plBorrowRequirement) {
		this.plBorrowRequirement = plBorrowRequirement;
	}

	public List<HousEnquiryInstitution> getHousEnquiryInstitution() {
		return housEnquiryInstitution;
	}

	public void setHousEnquiryInstitution(List<HousEnquiryInstitution> housEnquiryInstitution) {
		this.housEnquiryInstitution = housEnquiryInstitution;
	}
}
