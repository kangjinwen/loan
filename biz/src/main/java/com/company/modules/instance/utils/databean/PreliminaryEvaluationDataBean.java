package com.company.modules.instance.utils.databean;

import java.io.Serializable;
import java.util.List;

import com.company.common.domain.PlApprovalResults;
import com.company.common.domain.PlBorrowRequirement;
import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.instance.domain.HousAssessmentAgencies;
import com.company.modules.instance.domain.HousBorrowingBasics;
import com.company.modules.instance.domain.HousEnquiryInstitution;
import com.company.modules.instance.domain.HousPersonType;
import com.company.modules.instance.domain.HousPropertyInformation;

public class PreliminaryEvaluationDataBean extends BasicServiceDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long consultId;
	private List<HousAssessmentAgencies> housAssessmentAgencies;
	private HousBorrowingBasics housBorrowingBasics;
	private HousPropertyInformation housPropertyInformation;
	private PlBorrowRequirement plBorrowRequirement;
	private List<HousEnquiryInstitution> housEnquiryInstitution;
	private List<HousPersonType> housPersonType;
	private int commit = 0;	
	private String branchingProcessId;
	private String customerId;
	public String getCustomerId() {
		return customerId;
	}
	private PlApprovalResults plApprovalResults;


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<HousPersonType> getHousPersonType() {
		return housPersonType;
	}

	public void setHousPersonType(List<HousPersonType> housPersonType) {
		this.housPersonType = housPersonType;
	}

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

	public int getCommit() {
		return commit;
	}

	public void setCommit(int commit) {
		this.commit = commit;
	}

	public String getBranchingProcessId() {
		return branchingProcessId;
	}

	public void setBranchingProcessId(String branchingProcessId) {
		this.branchingProcessId = branchingProcessId;
	}

	public PlApprovalResults getPlApprovalResults() {
		return plApprovalResults;
	}

	public void setPlApprovalResults(PlApprovalResults plApprovalResults) {
		this.plApprovalResults = plApprovalResults;
	}
}